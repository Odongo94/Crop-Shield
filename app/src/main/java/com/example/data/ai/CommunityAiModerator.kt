package com.example.data.ai

import com.example.data.AgronomyCatalog
import com.example.data.ThreatCatalog
import com.example.model.PathogenClass
import com.example.model.ThreatType
import com.example.model.cloud.CommunityMessage

enum class CommunityAiActionType {
    AUTO_RESPONSE,
    FACT_CORRECTION
}

data class CommunityAiAction(
    val docId: String,
    val topic: String,
    val messageText: String,
    val actionType: CommunityAiActionType
)

data class InvalidClaimResult(
    val category: String,
    val detectedClaim: String,
    val authoritativeCorrection: String,
    val citedSource: String
)

data class UnansweredEvaluationResult(
    val readyQuestions: List<CommunityMessage>,
    val nextDueDelayMs: Long?
)

/**
 * Automated Community AI Moderator & Agronomic Fact-Checker.
 * 1. Auto-responds to unanswered farmer questions after a 5-minute (300,000 ms) interval
 *    if no other farmer or advisor has replied.
 * 2. Detects and corrects invalid, unsafe, or agronomically incorrect farmer replies using
 *    CropShield's verified ThreatCatalog, AgronomyCatalog, and Soil/Poultry databases.
 */
object CommunityAiModerator {

    const val AI_AUTHOR_NAME = "Shamba AI Agronomist"
    const val AI_AUTHOR_ROLE = "ai_advisor"
    const val DEFAULT_RESPONSE_DELAY_MS: Long = 5L * 60L * 1000L // 5 minutes

    fun aiResponseDocId(questionId: String): String =
        "ai_resp_${questionId.replace(Regex("[^a-zA-Z0-9_-]"), "_").take(90)}"

    fun aiCorrectionDocId(messageId: String): String =
        "ai_corr_${messageId.replace(Regex("[^a-zA-Z0-9_-]"), "_").take(90)}"

    fun messageTimestampMs(msg: CommunityMessage): Long {
        val ts = msg.createdAt ?: return 0L
        return ts.seconds * 1000L + (ts.nanoseconds / 1_000_000L)
    }

    fun looksLikeFarmerQuestion(text: String): Boolean {
        val q = text.trim().lowercase()
        if (q.length < 8) return false
        if (q.contains("?")) return true

        val interrogativeOrHelpSignals = listOf(
            "how ", "what ", "which ", "why ", "when ", "where ", "who ",
            "can i ", "should i ", "could i ", "do i ", "is it ", "is there ",
            "anyone know", "does anyone", "please help", "need help", "help me",
            "recommend ", "advice on", "advise on", "what is the best", "how to "
        )
        if (interrogativeOrHelpSignals.any { q.contains(it) }) return true

        val prescriptiveAdviceStarters = listOf(
            "use ", "spray ", "apply ", "scout ", "mix ", "uproot ", "rogue ",
            "prune ", "try ", "add ", "avoid ", "never ", "always ", "water "
        )
        if (prescriptiveAdviceStarters.any { q.startsWith(it) }) return false

        val symptomReportSignals = listOf(
            "my crop", "my maize", "my tomato", "my beans", "my chicken", "my cow",
            "yellowing", "wilting", "dying", "rotting", "spots on", "holes in"
        )
        return symptomReportSignals.any { q.contains(it) }
    }

    /**
     * Evaluates community messages chronologically and returns:
     * - farmer questions that have waited >= [delayMs] (default 5 minutes) with no reply
     * - the shortest remaining wait duration (in ms) for newer unanswered questions
     */
    fun evaluateUnansweredQuestions(
        messages: List<CommunityMessage>,
        nowMs: Long = System.currentTimeMillis(),
        delayMs: Long = DEFAULT_RESPONSE_DELAY_MS
    ): UnansweredEvaluationResult {
        if (messages.isEmpty()) return UnansweredEvaluationResult(emptyList(), null)

        val ordered = messages.sortedWith(
            compareBy<CommunityMessage> { messageTimestampMs(it) }.thenBy { it.id }
        )
        val existingIds = ordered.map { it.id }.toSet()
        val ready = mutableListOf<CommunityMessage>()
        var minRemainingMs: Long? = null

        ordered.forEachIndexed { index, msg ->
            if (msg.authorRole != "farmer" || msg.id.isBlank()) return@forEachIndexed
            if (!looksLikeFarmerQuestion(msg.messageText)) return@forEachIndexed

            val expectedAiDocId = aiResponseDocId(msg.id)
            if (expectedAiDocId in existingIds) return@forEachIndexed

            val hasSubsequentReply = ordered.drop(index + 1).any { later ->
                when (later.authorRole) {
                    "super_admin" -> later.topic == msg.topic ||
                        later.messageText.contains(msg.authorName, ignoreCase = true) ||
                        !later.messageText.trim().endsWith("?")
                    AI_AUTHOR_ROLE -> later.id == expectedAiDocId
                    "farmer" -> {
                        if (later.authorId == msg.authorId) {
                            false
                        } else {
                            val sameTopicOrMentionsAuthor = later.topic == msg.topic ||
                                (msg.authorName.isNotBlank() && later.messageText.contains(msg.authorName, ignoreCase = true))
                            val isStandaloneNewQuestion = later.messageText.trim().endsWith("?") &&
                                looksLikeFarmerQuestion(later.messageText) &&
                                !(msg.authorName.isNotBlank() && later.messageText.contains(msg.authorName, ignoreCase = true))
                            sameTopicOrMentionsAuthor && !isStandaloneNewQuestion
                        }
                    }
                    else -> false
                }
            }
            if (hasSubsequentReply) return@forEachIndexed

            val msgTimeMs = messageTimestampMs(msg)
            if (msgTimeMs <= 0L) return@forEachIndexed

            val elapsedMs = (nowMs - msgTimeMs).coerceAtLeast(0L)
            if (elapsedMs >= delayMs) {
                ready.add(msg)
            } else {
                val remaining = (delayMs - elapsedMs).coerceAtLeast(1000L)
                minRemainingMs = minRemainingMs?.let { minOf(it, remaining) } ?: remaining
            }
        }

        return UnansweredEvaluationResult(
            readyQuestions = ready,
            nextDueDelayMs = minRemainingMs
        )
    }

    /**
     * Checks a farmer's community message for factually invalid, mismatched, or unsafe
     * agricultural claims against CropShield's existing offline databases.
     */
    fun detectInvalidFarmerResponse(message: CommunityMessage): InvalidClaimResult? {
        if (message.authorRole != "farmer") return null
        val text = message.messageText.trim()
        if (text.length < 10) return null
        val q = text.lowercase()

        // Skip pure questions when they aren't asserting treatment advice
        val isPureQuestion = text.endsWith("?") &&
            !q.contains("use ") && !q.contains("spray ") && !q.contains("apply ") &&
            !q.contains("just ") && !q.contains("no waiting") && !q.contains("cures ")

        // 1. Hazardous household / petroleum substances applied to crops or livestock
        val hazardousTokens = listOf("diesel", "petrol", "kerosene", "paraffin", "engine oil", "motor oil", "bleach", "jik", "battery acid")
        val matchedHazard = hazardousTokens.firstOrNull { q.contains(it) }
        if (matchedHazard != null && (q.contains("spray") || q.contains("pour") || q.contains("use") || q.contains("mix") || q.contains("kill") || q.contains("treat"))) {
            return InvalidClaimResult(
                category = "Unsafe Substance Warning",
                detectedClaim = "Suggested applying '$matchedHazard' on crops/soil",
                authoritativeCorrection = "Never apply petroleum fuels ($matchedHazard), motor oil, or household bleach on crops or soil—they cause severe phytotoxicity, kill beneficial soil microbes, and contaminate food & groundwater. Use PCPB-registered botanicals like Neem Oil (Azadirachtin 0.03% EC @ 30–50 mL/16L), Potassium Salts of Fatty Acids, or Bt.",
                citedSource = "CropShield Safety & PCPB BioProtection Standard"
            )
        }

        // 2. Zero PHI / eat-immediately-after-spraying claims
        val unsafePhiClaim = q.contains("harvest immediately") ||
            q.contains("harvest same day") ||
            q.contains("eat immediately") ||
            q.contains("no waiting period") ||
            q.contains("0 day phi") ||
            q.contains("zero phi") ||
            q.contains("harvest right after")
        if (unsafePhiClaim) {
            return InvalidClaimResult(
                category = "Food Safety & PHI Correction",
                detectedClaim = "Claimed crops can be harvested immediately with zero Pre-Harvest Interval (PHI)",
                authoritativeCorrection = "Every registered synthetic pesticide has a mandatory Pre-Harvest Interval (PHI) before consumption or sale. For example, Mancozeb / Metalaxyl-M requires 7 days PHI, Chlorantraniliprole (Coragen) requires 14 days, and Emamectin Benzoate requires 7–14 days. Always follow the product's label PHI to prevent toxic residues and MRL rejection.",
                citedSource = "ThreatCatalog & GlobalG.A.P. / PCPB MRL Standards"
            )
        }

        if (isPureQuestion) return null

        // 3. Bacterial Wilt treated with fungicide claim
        val mentionsBacterialWilt = q.contains("bacterial wilt") || q.contains("ralstonia")
        val fungicideKeywords = listOf("ridomil", "mancozeb", "metalaxyl", "azoxystrobin", "ortiva", "amistar", "fungicide", "carbendazim", "score", "propiconazole")
        val matchedFungicide = fungicideKeywords.firstOrNull { q.contains(it) }
        if (mentionsBacterialWilt && matchedFungicide != null && (q.contains("cure") || q.contains("spray") || q.contains("use") || q.contains("control") || q.contains("treat"))) {
            val bwThreat = ThreatCatalog.getThreatById("bacterial_wilt")
            val culturalTip = bwThreat?.ipmPlan?.culturalControls?.take(2)?.joinToString("; ")
                ?: "Rogue and burn infected plants immediately; rotate 4–5 years with non-Solanaceous crops like maize or grasses."
            return InvalidClaimResult(
                category = "Pathogen Mismatch Correction",
                detectedClaim = "Recommended fungicide ($matchedFungicide) for Bacterial Wilt",
                authoritativeCorrection = "Bacterial Wilt (Ralstonia solanacearum) is caused by a soil-borne bacterium blocking xylem vessels—NO fungicide ($matchedFungicide) can cure it. Recommended action from CropShield Database: $culturalTip",
                citedSource = "ThreatCatalog: Bacterial Wilt (Ralstonia solanacearum)"
            )
        }

        // 4. Viral disease treated/cured with fungicide
        val viralKeywords = listOf("mln", "lethal necrosis", "mosaic", "leaf curl", "tylcv", "viral", "virus")
        val matchedVirus = viralKeywords.firstOrNull { q.contains(it) }
        if (matchedVirus != null && matchedFungicide != null) {
            return InvalidClaimResult(
                category = "Viral Disease Correction",
                detectedClaim = "Recommended fungicide ($matchedFungicide) to cure a viral disease ($matchedVirus)",
                authoritativeCorrection = "Fungicides like $matchedFungicide cannot cure plant viruses ($matchedVirus). Management requires: (1) roguing infected plants early, (2) controlling insect vectors (whiteflies, aphids, or thrips) with Azadirachtin or Imidacloprid/Thiamethoxam per PHI rules, and (3) planting certified virus-tolerant varieties.",
                citedSource = "ThreatCatalog: Viral Plant Pathogens"
            )
        }

        // 5. Using Fungicide to kill an Insect Pest OR Insecticide to kill a Fungal Disease
        val insecticideKeywords = listOf("coragen", "emamectin", "chlorantraniliprole", "imidacloprid", "cypermethrin", "karate", "lambda", "abamectin", "indoxacarb", "insecticide", "dudu")
        val matchedInsecticide = insecticideKeywords.firstOrNull { q.contains(it) }

        val fungalDiseases = ThreatCatalog.allThreats.filter {
            it.type == ThreatType.DISEASE && it.pathogenClass == PathogenClass.FUNGAL
        }
        val matchedFungal = fungalDiseases.firstOrNull { threat ->
            q.contains(threat.name.lowercase()) ||
                (threat.id == "late_blight" && q.contains("late blight")) ||
                (threat.id == "powdery_mildew" && q.contains("powdery mildew"))
        }
        if (matchedFungal != null && matchedInsecticide != null) {
            val validChem = matchedFungal.ipmPlan.chemicalControls.firstOrNull()
            val chemAdvice = validChem?.let {
                "${it.tradeName.ifBlank { it.activeIngredient }} (${it.activeIngredient}) @ ${it.dosagePerKnapsack}, PHI ${it.phiDays} days"
            } ?: "Mancozeb or Copper-based protectant fungicide"
            return InvalidClaimResult(
                category = "Pesticide Mode-of-Action Correction",
                detectedClaim = "Suggested insecticide ($matchedInsecticide) for ${matchedFungal.name}",
                authoritativeCorrection = "${matchedFungal.name} (${matchedFungal.scientificName}) is a fungal/oomycete disease, so insecticides ($matchedInsecticide) will not stop it. Use registered fungicides such as $chemAdvice, paired with ${matchedFungal.ipmPlan.culturalControls.firstOrNull() ?: "canopy aeration & sanitation"}.",
                citedSource = "ThreatCatalog: ${matchedFungal.name}"
            )
        }

        val insectPests = ThreatCatalog.allThreats.filter {
            it.type == ThreatType.PEST && (it.pathogenClass == PathogenClass.INSECT_PEST || it.pathogenClass == PathogenClass.MITE_PEST)
        }
        val matchedPest = insectPests.firstOrNull { threat ->
            q.contains(threat.name.lowercase()) ||
                (threat.id == "fall_armyworm" && (q.contains("armyworm") || q.contains("faw"))) ||
                (threat.id == "tuta_absoluta" && q.contains("tuta")) ||
                (threat.id == "red_spider_mite" && q.contains("spider mite"))
        }
        if (matchedPest != null && matchedFungicide != null && !q.contains("blight") && !q.contains("mildew") && !q.contains("rust")) {
            val validChem = matchedPest.ipmPlan.chemicalControls.firstOrNull()
            val chemAdvice = validChem?.let {
                "${it.tradeName.ifBlank { it.activeIngredient }} (${it.activeIngredient}) @ ${it.dosagePerKnapsack}, PHI ${it.phiDays} days"
            } ?: "registered IPM insecticide or Bt/Neem biocontrol"
            return InvalidClaimResult(
                category = "Pest vs Disease Product Correction",
                detectedClaim = "Suggested fungicide ($matchedFungicide) to kill ${matchedPest.name}",
                authoritativeCorrection = "${matchedPest.name} (${matchedPest.scientificName}) is an arthropod pest, not a fungus—fungicides ($matchedFungicide) have no insecticidal effect. Valid control in CropShield: ${matchedPest.ipmPlan.biologicalControls.firstOrNull() ?: "Biological IPM"} or $chemAdvice.",
                citedSource = "ThreatCatalog: ${matchedPest.name}"
            )
        }

        // 6. Soil acidity misconception: using DAP or Urea to raise pH / cure acidity
        val mentionsRaisingPh = q.contains("raise ph") || q.contains("reduce acidity") || q.contains("neutralize acid") || q.contains("neutralise acid") || q.contains("fix acid soil")
        if (mentionsRaisingPh && (q.contains("dap") || q.contains("urea") || q.contains("18:46:0"))) {
            val dapInfo = AgronomyCatalog.fertilizerProducts.firstOrNull { it.id == "mea_dap" }?.soilAcidImpact
                ?: "Strongly Acidifying (releases H+ ions during nitrification)"
            return InvalidClaimResult(
                category = "Soil Chemistry Correction",
                detectedClaim = "Suggested DAP/Urea to reduce soil acidity or raise soil pH",
                authoritativeCorrection = "DAP (18:46:0) and Urea do NOT raise soil pH—in fact, DAP is $dapInfo, which lowers soil pH further. To raise acidic soil pH (pH < 5.5), incorporate Agricultural Lime (CaCO3) or Dolomitic Lime 4–6 weeks before planting (typically 1.5–2.5 t/ha depending on CEC & buffer pH).",
                citedSource = "AgronomyCatalog & SoilClassificationCatalog"
            )
        }

        // 7. Solanaceae rotation error (tomato immediately after potato or vice versa)
        val sameFamilyRotation = (q.contains("plant tomato after potato") || q.contains("plant potato after tomato") || q.contains("rotate tomato with potato") || q.contains("rotate potato with tomato")) &&
            !q.contains("not ") && !q.contains("don't") && !q.contains("avoid")
        if (sameFamilyRotation) {
            return InvalidClaimResult(
                category = "Crop Rotation Correction",
                detectedClaim = "Suggested rotating Tomatoes directly with Potatoes",
                authoritativeCorrection = "Tomatoes, potatoes, peppers, and eggplants all belong to the Solanaceae family and share Late Blight (Phytophthora infestans), Bacterial Wilt (Ralstonia solanacearum), and Root-Knot Nematodes. Rotate Solanaceous crops for 3–5 seasons with Maize, Beans, Onions, Brassicas, or Rhodes grass.",
                citedSource = "ThreatCatalog & Crop Rotation Planner"
            )
        }

        // 8. Poultry chlorinated tap water vaccination myth
        if ((q.contains("vaccine") || q.contains("vaccinat")) && q.contains("tap water") && !q.contains("milk") && !q.contains("chlorine") && q.contains("direct")) {
            return InvalidClaimResult(
                category = "Poultry Vaccination Correction",
                detectedClaim = "Recommended mixing live poultry vaccine directly in untreated tap water",
                authoritativeCorrection = "Residual chlorine in municipal/tap water inactivates live Newcastle (LaSota) and Gumboro vaccines. Always neutralize water with 2.5 g of non-fat skimmed milk powder per 1 Liter of clean water 15 minutes before adding the vaccine.",
                citedSource = "PoultryManagementCatalog"
            )
        }

        return null
    }

    /**
     * Generates a community AI response grounded in CropShield's existing databases
     * (and uses Gemini online when configured, anchored to catalog facts).
     */
    suspend fun buildGroundedAiReply(question: CommunityMessage): CommunityAiAction {
        val catalogFacts = gatherRelevantCatalogFacts(question.messageText, question.topic)
        val prompt = buildString {
            appendLine("You are Shamba AI Agronomist responding in the CropShield farmer community forum after 5 minutes with no peer reply.")
            appendLine("Keep the reply concise (under 1100 characters), practical, respectful, and strictly grounded in the verified CropShield data below.")
            appendLine("NEVER mention Super Admin or any developer email address.")
            appendLine()
            appendLine("Topic: ${question.topic} | Farmer County: ${question.county.ifBlank { "Kenya" }}")
            appendLine("Farmer Question: ${question.messageText}")
            if (catalogFacts.isNotBlank()) {
                appendLine()
                appendLine("Verified CropShield Database Facts:")
                appendLine(catalogFacts)
            }
        }

        val aiReply = AiAgronomistService.askAgricultureAi(
            userQuestion = prompt,
            conversationHistory = emptyList(),
            imageBitmap = null
        )

        val cleanedText = sanitizeCommunityAiOutput(
            raw = aiReply.text,
            fallback = formatOfflineAutoReply(question.messageText, catalogFacts)
        )

        return CommunityAiAction(
            docId = aiResponseDocId(question.id),
            topic = question.topic.ifBlank { "General" },
            messageText = cleanedText,
            actionType = CommunityAiActionType.AUTO_RESPONSE
        )
    }

    /**
     * Generates a polite, evidence-backed community fact-check message correcting
     * an invalid or unsafe farmer response using existing CropShield catalog data.
     */
    suspend fun buildFactCheckCorrection(
        badMessage: CommunityMessage,
        issue: InvalidClaimResult
    ): CommunityAiAction {
        val text = buildString {
            append("Fact-Check (${issue.category}): ")
            append("Regarding ${badMessage.authorName.ifBlank { "the earlier" }} note (${issue.detectedClaim}) — ")
            append(issue.authoritativeCorrection)
            append(" [Source: ${issue.citedSource}]")
        }.take(1450)

        return CommunityAiAction(
            docId = aiCorrectionDocId(badMessage.id),
            topic = badMessage.topic.ifBlank { "General" },
            messageText = text,
            actionType = CommunityAiActionType.FACT_CORRECTION
        )
    }

    private fun gatherRelevantCatalogFacts(query: String, topic: String): String {
        val q = "$query $topic".lowercase()
        val matchedThreats = ThreatCatalog.allThreats.filter { threat ->
            q.contains(threat.name.lowercase()) ||
                threat.targetCrops.any { crop -> q.contains(crop.lowercase().substringBefore(" (")) }
        }.take(2)

        return buildString {
            matchedThreats.forEach { t ->
                appendLine("• ${t.name} (${t.scientificName}, ${t.type.name}): ${t.summary}")
                t.ipmPlan.culturalControls.firstOrNull()?.let { appendLine("  Cultural: $it") }
                t.ipmPlan.biologicalControls.firstOrNull()?.let { appendLine("  Biocontrol: $it") }
                t.ipmPlan.chemicalControls.firstOrNull()?.let { c ->
                    appendLine("  Chemical: ${c.tradeName.ifBlank { c.activeIngredient }} (${c.activeIngredient}) @ ${c.dosagePerKnapsack}, PHI ${c.phiDays} days")
                }
            }
            if (q.contains("fertilizer") || q.contains("dap") || q.contains("can") || q.contains("soil") || q.contains("lime")) {
                AgronomyCatalog.fertilizerProducts.filter {
                    q.contains(it.name.lowercase()) || q.contains(it.brand.lowercase())
                }.take(2).forEach { f ->
                    appendLine("• ${f.brand} ${f.name} (${f.npkRatio}): ${f.standardRatePerHa}. ${f.decisionRule}")
                }
            }
        }.trim()
    }

    private fun formatOfflineAutoReply(questionText: String, catalogFacts: String): String {
        return buildString {
            append("Auto-Advisory (5-min unanswered check): ")
            if (catalogFacts.isNotBlank()) {
                append(catalogFacts)
            } else {
                append(
                    AiAgronomistService.generateOfflineAgronomicResponse(questionText)
                        .replace("### ", "")
                        .replace("**", "")
                )
            }
        }.take(1450)
    }

    private fun sanitizeCommunityAiOutput(raw: String, fallback: String): String {
        val cleaned = raw
            .replace(Regex("(?i)Signed off by.*?(\n|$)"), "")
            .replace(Regex("(?i)odongovincento@gmail\\.com"), "")
            .replace(Regex("(?i)super\\s*admin"), "Agronomy Advisor")
            .trim()
        return if (cleaned.length >= 25) cleaned.take(1450) else fallback.take(1450)
    }
}
