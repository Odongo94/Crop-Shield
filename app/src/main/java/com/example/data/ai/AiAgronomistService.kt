package com.example.data.ai

import android.graphics.Bitmap
import android.util.Log
import com.example.data.AgronomyCatalog
import com.example.data.ThreatCatalog
import com.example.data.quarantine.QuarantinePestCatalog
import com.example.data.scanner.PhotoDiseaseScanner
import com.example.data.science.curriculum.AgronomyDegreePackCatalog
import com.example.model.ai.AiChatMessage
import com.example.model.ai.AiQuickPrompt
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Content
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

/**
 * Intelligent AI Agronomist Engine for Farmers.
 * Routes online queries and multimodal crop/pest photos through Firebase AI Logic
 * (`Firebase.ai`, model `gemini-3.8-flash` + App Check) with a deep catalog-backed
 * offline agronomic knowledge fallback.
 */
object AiAgronomistService {

    private const val TAG = "AiAgronomistService"
    private const val GEMINI_MODEL_NAME = "gemini-3.8-flash"
    private const val AI_TIMEOUT_MS = 60_000L

    private const val AGRONOMIST_SYSTEM_PROMPT =
        "You are 'CropShield AI Agronomist', an expert tropical, East African, and international Senior Agronomist, Plant Pathologist, Entomologist, Soil Scientist, and Livestock Advisor. " +
            "You serve smallholder and commercial farmers, extension officers, and field scouts. " +
            "Always provide clear, authoritative, practical, and mathematically precise advice. " +
            "When an agricultural photo is attached, inspect it carefully: distinguish strictly between insect pests/feeding damage and fungal/bacterial/viral plant diseases or nutrient deficiencies, state confidence and visual cues, and never confuse a pest for a disease. " +
            "When answering pest or disease questions: (1) identify organism and symptoms, (2) give cultural & organic IPM controls, (3) give biological controls (CABI BioProtection / Bt / Neem / parasitoids), (4) give PCPB-registered products with exact knapsack rates per 16L/20L tank and Pre-Harvest Interval (PHI). " +
            "When answering soil, fertilizer, or irrigation questions: provide exact NPK/lime rates (kg/ha, bags/acre) or FAO-56 ETc calculations. " +
            "When answering poultry or livestock questions: provide exact vaccination timelines, chlorination neutralization (2.5 g/L skimmed milk), and feeding rates. " +
            "Keep tone encouraging, well-structured with markdown headings and bullet points. Sign off technically under Lead Agronomist Odongo Vincent (CropShield IPM)."

    private val agronomistModel by lazy {
        Firebase.ai.generativeModel(
            modelName = GEMINI_MODEL_NAME,
            generationConfig = generationConfig {
                temperature = 0.3f
                maxOutputTokens = 2048
            },
            systemInstruction = content {
                text(AGRONOMIST_SYSTEM_PROMPT)
            }
        )
    }

    val quickPrompts: List<AiQuickPrompt> = listOf(
        AiQuickPrompt(
            id = "qp_maize_faw",
            iconEmoji = "🌽",
            category = "Pest Control",
            shortLabel = "Fall Armyworm in Maize",
            fullPrompt = "How do I identify and control Fall Armyworm in my 3-week-old maize crop using both organic IPM and PCPB registered chemicals with knapsack dosage?"
        ),
        AiQuickPrompt(
            id = "qp_tomato_blight",
            iconEmoji = "🍅",
            category = "Disease Management",
            shortLabel = "Tomato Late Blight vs Wilt",
            fullPrompt = "How can I distinguish Late Blight from Bacterial Wilt in tomatoes, and what is the exact fungicide or management protocol for each?"
        ),
        AiQuickPrompt(
            id = "qp_poultry_vax",
            iconEmoji = "🐔",
            category = "Poultry Health",
            shortLabel = "Chick Vaccine Calendar (Day 1-28)",
            fullPrompt = "What is the complete vaccination schedule for 500 layer chicks from Day 1 to Week 4, and how do I prepare the drinking water with skimmed milk?"
        ),
        AiQuickPrompt(
            id = "qp_soil_lime_cec",
            iconEmoji = "🧪",
            category = "Soil Fertility",
            shortLabel = "Soil pH 4.8 & Liming Dose",
            fullPrompt = "My soil test shows pH 4.8, low CEC, and high exchangeable aluminum. How much agricultural lime should I apply per acre, and how do I rebalance Ca:Mg ratios?"
        ),
        AiQuickPrompt(
            id = "qp_drip_irrig",
            iconEmoji = "💧",
            category = "Irrigation Science",
            shortLabel = "Drip Water & EC Requirement",
            fullPrompt = "How do I calculate the daily irrigation water requirement in Liters per plant for tomatoes during flowering when reference ET0 is 5.0 mm/day and water EC is 0.8 mS/cm?"
        ),
        AiQuickPrompt(
            id = "qp_compost_manure",
            iconEmoji = "🌿",
            category = "Organic Farming",
            shortLabel = "18-Day Berkeley Hot Compost",
            fullPrompt = "What are the step-by-step procedures to make 18-day Berkeley hot compost, and what are the ideal C:N ratios for brown and green materials?"
        ),
        AiQuickPrompt(
            id = "qp_fcm_quarantine",
            iconEmoji = "🚨",
            category = "Quarantine Biosecurity",
            shortLabel = "False Codling Moth EU Rules",
            fullPrompt = "What are the KEPHIS and EU export quarantine requirements for False Codling Moth (FCM) on cut roses, avocados, and chillies, including cold treatment?"
        ),
        AiQuickPrompt(
            id = "qp_nozzle_spray",
            iconEmoji = "🎯",
            category = "Sprayer Calibration",
            shortLabel = "Nozzle Discharge & Wear Check",
            fullPrompt = "How do I calculate spray nozzle discharge rate from ISO color and pressure, and how do I verify if my nozzle orifice is worn out using the 10% rule?"
        ),
        AiQuickPrompt(
            id = "qp_audit_mrl",
            iconEmoji = "📋",
            category = "Compliance & Audits",
            shortLabel = "GlobalG.A.P. & KS 1758 MRL Rules",
            fullPrompt = "What are the daily compliance requirements for Pre-Harvest Intervals (PHI), empty container triple-rinsing, and worker PPE under GlobalG.A.P. IFA v6 and KS 1758?"
        )
    )

    /**
     * Ask a question (optionally with an uploaded crop/pest/soil/livestock photo) to the AI Agronomist.
     * Uses Firebase AI Logic (`gemini-3.8-flash`) online; falls back seamlessly to the rich
     * CropShield catalog engine if offline or if AI Logic encounters a transient/timeout error.
     */
    suspend fun askAgricultureAi(
        userQuestion: String,
        conversationHistory: List<AiChatMessage> = emptyList(),
        imageBitmap: Bitmap? = null
    ): AiChatMessage = withContext(Dispatchers.IO) {
        val effectiveQuestion = userQuestion.trim().ifBlank {
            if (imageBitmap != null) {
                "Analyze this uploaded agricultural photo carefully. Distinguish whether it shows an insect pest, plant disease, nutrient deficiency, weed species, soil condition, or livestock issue; estimate severity; and provide organic/biocontrol and PCPB-registered knapsack recommendations with Pre-Harvest Intervals."
            } else {
                ""
            }
        }

        if (effectiveQuestion.isBlank() && imageBitmap == null) {
            return@withContext AiChatMessage(
                text = "Please type your farming question or attach a crop photo so Shamba AI can help.",
                isUser = false,
                isOnlineGemini = false,
                topicCategory = "Input Required"
            )
        }

        var onlineFailureNote: String? = null
        try {
            // Ensure FirebaseApp exists before touching Firebase.ai (guards pure JVM unit tests)
            FirebaseApp.getInstance()
            val geminiResponse = callFirebaseAiAgronomist(
                question = effectiveQuestion,
                history = conversationHistory,
                imageBitmap = imageBitmap
            )
            if (!geminiResponse.isNullOrBlank()) {
                return@withContext AiChatMessage(
                    text = geminiResponse.trim(),
                    isUser = false,
                    isOnlineGemini = true,
                    topicCategory = if (imageBitmap != null) "Photo Vision Diagnosis" else detectTopicCategory(effectiveQuestion),
                    suggestedFollowUps = generateFollowUps("$effectiveQuestion $geminiResponse"),
                    relatedCalculatorScreen = detectRelatedCalculator("$effectiveQuestion $geminiResponse")
                )
            }
            onlineFailureNote = "Gemini returned an empty response; displaying verified CropShield catalog advisory."
        } catch (e: TimeoutCancellationException) {
            safeLogE(TAG, "Firebase AI agronomist request timed out after ${AI_TIMEOUT_MS}ms", e)
            onlineFailureNote = "Online Gemini timed out after 60s; showing verified CropShield catalog advisory."
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            safeLogE(TAG, "Firebase AI agronomist call failed: ${e.message}", e)
            onlineFailureNote = "Offline / fallback advisory active (${e.localizedMessage ?: "network unavailable"})."
        }

        // Resilient Catalog-Grounded Offline Fallback
        val offlineResponse = if (imageBitmap != null) {
            generateOfflineImageAnalysisResponse(effectiveQuestion, imageBitmap)
        } else {
            generateOfflineAgronomicResponse(effectiveQuestion)
        }
        val responseWithNotice = if (!onlineFailureNote.isNullOrBlank() && imageBitmap == null) {
            "$offlineResponse\n\n_Status: ${onlineFailureNote}_"
        } else {
            offlineResponse
        }

        AiChatMessage(
            text = responseWithNotice,
            isUser = false,
            isOnlineGemini = false,
            topicCategory = if (imageBitmap != null) "Offline Photo Diagnosis" else detectTopicCategory(effectiveQuestion),
            suggestedFollowUps = generateFollowUps("$effectiveQuestion $offlineResponse"),
            relatedCalculatorScreen = detectRelatedCalculator("$effectiveQuestion $offlineResponse")
        )
    }

    private suspend fun callFirebaseAiAgronomist(
        question: String,
        history: List<AiChatMessage>,
        imageBitmap: Bitmap?
    ): String? {
        val cleanHistory = mutableListOf<Content>()
        var expectedRole = "user"
        for (msg in history.takeLast(8)) {
            val role = if (msg.isUser) "user" else "model"
            val textBody = (if (msg.attachedImage != null) "[Attached Photo] " else "") + msg.text.trim()
            if (textBody.isBlank()) continue
            if (role == expectedRole) {
                cleanHistory += content(role) { text(textBody.take(2000)) }
                expectedRole = if (expectedRole == "user") "model" else "user"
            }
        }
        if (cleanHistory.lastOrNull()?.role == "user") {
            cleanHistory.removeAt(cleanHistory.lastIndex)
        }

        val scaledBitmap = imageBitmap?.let { scaleBitmapForInference(it, 1024) }
        val userContent = content("user") {
            if (scaledBitmap != null) {
                image(scaledBitmap)
            }
            text(question)
        }

        val response = withTimeout(AI_TIMEOUT_MS) {
            if (cleanHistory.isNotEmpty()) {
                val chat = agronomistModel.startChat(history = cleanHistory)
                chat.sendMessage(userContent)
            } else {
                agronomistModel.generateContent(userContent)
            }
        }
        return response.text
    }

    private fun scaleBitmapForInference(bitmap: Bitmap, maxDimension: Int): Bitmap {
        if (bitmap.width <= maxDimension && bitmap.height <= maxDimension) return bitmap
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
        val newWidth: Int
        val newHeight: Int
        if (ratio > 1f) {
            newWidth = maxDimension
            newHeight = (maxDimension / ratio).toInt().coerceAtLeast(1)
        } else {
            newHeight = maxDimension
            newWidth = (maxDimension * ratio).toInt().coerceAtLeast(1)
        }
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private suspend fun generateOfflineImageAnalysisResponse(
        question: String,
        imageBitmap: Bitmap
    ): String {
        val cropHint = detectCropHintFromQuery(question)
        val diagnosis = PhotoDiseaseScanner.analyzeCropPhoto(
            bitmap = imageBitmap,
            cropHint = cropHint,
            categoryFilter = null
        )
        return buildString {
            append("### 📸 Shamba AI Visual Photo Analysis\n\n")
            if (question.isNotBlank()) {
                append("**Farmer Note:** *\"${question.take(180)}\"*\n\n")
            }
            append("**1. Identified Condition:** ${diagnosis.diseaseName} (*${diagnosis.scientificName}*)\n")
            append("• **Category:** ${diagnosis.diagnosticCategory.label}\n")
            append("• **Crop / Context:** ${diagnosis.crop}\n")
            append("• **Confidence:** ${diagnosis.confidencePercent}% • **Severity:** ${diagnosis.severity}\n\n")

            if (diagnosis.visualSymptomsDetected.isNotEmpty()) {
                append("**2. Visual Symptoms & Diagnostic Signals:**\n")
                diagnosis.visualSymptomsDetected.forEach { symptom ->
                    append("• $symptom\n")
                }
                append("\n")
            }

            append("**3. Cultural & Organic IPM Action:**\n")
            append("• ${diagnosis.recommendedCulturalAction}\n")
            if (diagnosis.infonetOrganicAction.isNotBlank()) {
                append("• **Infonet-Biovision Practice:** ${diagnosis.infonetOrganicAction}\n")
            }
            if (diagnosis.cabiBiocontrolSolution.isNotBlank()) {
                append("• **Biological Control (CABI):** ${diagnosis.cabiBiocontrolSolution}\n")
            }
            append("\n")

            if (diagnosis.recommendedChemicals.isNotEmpty()) {
                append("**4. Registered Agrochemical & Knapsack Guidance:**\n")
                diagnosis.recommendedChemicals.forEach { chem ->
                    append("• $chem\n")
                }
                diagnosis.recommendedChemicalObject?.let { rec ->
                    append("• **Safety Check:** PHI ${rec.phiDays} days • REI ${rec.reiHours} hours • ${rec.chemicalGroup}\n")
                }
                append("\n")
            }

            append("💡 *${diagnosis.detailedAnalysisText}*\n\n")
            append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
        }
    }

    private fun detectCropHintFromQuery(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("tomato") -> "Tomato"
            q.contains("maize") || q.contains("corn") -> "Maize / Cereals"
            q.contains("potato") -> "Potato"
            q.contains("rose") -> "Roses (Cut Flowers)"
            q.contains("bean") -> "French Beans / Legumes"
            q.contains("cabbage") || q.contains("kale") || q.contains("sukuma") -> "Cabbage / Brassicas"
            q.contains("onion") || q.contains("garlic") -> "Onion & Garlic"
            q.contains("pepper") || q.contains("chilli") || q.contains("capsicum") -> "Pepper"
            q.contains("wheat") || q.contains("barley") -> "Wheat & Barley"
            else -> "Auto-Detect"
        }
    }

    /**
     * Resilient Offline Agricultural Knowledge Engine.
     * Evaluates intents, keywords, and dynamically queries all CropShield catalogs
     * (`ThreatCatalog`, `AgronomyCatalog`, `QuarantinePestCatalog`, `AgronomyDegreePackCatalog`)
     * to formulate comprehensive, factual prescriptions even with zero network connectivity.
     */
    fun generateOfflineAgronomicResponse(query: String): String {
        val q = query.lowercase()

        // 1. FALL ARMYWORM
        if (q.contains("armyworm") || q.contains("faw") || (q.contains("maize") && q.contains("caterpillar"))) {
            return buildString {
                append("### 🌽 Fall Armyworm (*Spodoptera frugiperda*) Management\n\n")
                append("**1. Identification & Symptoms:**\n")
                append("• Ragged, windowpaned feeding holes on maize leaves and central whorl.\n")
                append("• Accumulation of coarse, moist sawdust-like frass inside the whorl.\n")
                append("• Inverted Y-shape marking on head capsule and 4 dark spots arranged in a square on the 8th abdominal segment.\n\n")
                append("**2. Cultural & Organic IPM Controls:**\n")
                append("• **Push-Pull Strategy**: Intercrop maize with Silverleaf Desmodium (*Desmodium uncinatum*) to repel moths and plant Brachiaria / Napier grass as a perimeter trap.\n")
                append("• **Handpicking & Crushing**: Crush egg masses and young larvae in small plots.\n")
                append("• **Wood Ash / Dry Sand**: Place half a teaspoon of dry fine sand or sieved wood ash mixed with chili powder into the central whorl of young maize.\n\n")
                append("**3. Biological Controls:**\n")
                append("• *Bacillus thuringiensis* (Bt) formulations (e.g. Dipel DF @ 30g per 16L tank).\n")
                append("• Entomopathogenic fungi: *Beauveria bassiana* or *Metarhizium anisopliae*.\n\n")
                append("**4. PCPB Registered Agrochemicals & Knapsack Dilution:**\n")
                append("• **Emamectin Benzoate 5% WDG** (e.g. Prove / Tracer): **8 - 10 grams per 16L - 20L knapsack tank**.\n")
                append("• **Chlorantraniliprole 200 g/L SC** (e.g. Coragen 20SC): **3 - 4 ml per 16L tank** (spray early morning or dusk directing nozzle into whorls; PHI: 14 days).\n")
                append("• **Indoxacarb 150 g/L SC**: **10 ml per 16L tank**.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 2. TOMATO LATE BLIGHT VS BACTERIAL WILT
        if (q.contains("late blight") || (q.contains("tomato") && (q.contains("blight") || q.contains("wilt")))) {
            return buildString {
                append("### 🍅 Tomato Late Blight vs. Bacterial Wilt Diagnosis\n\n")
                append("**Key Diagnostic Differentiation:**\n")
                append("• **Late Blight (*Phytophthora infestans*)**: Fungal/oomycete disease favored by cool, foggy, humid weather (15 - 20°C). Leaves show dark, water-soaked expanding lesions with a pale yellow halo and delicate white sporulating down on leaf undersides. Stem lesions are greasy dark brown. Controlled with fungicides.\n")
                append("• **Bacterial Wilt (*Ralstonia solanacearum*)**: Soil-borne bacterium favored by warm temperatures (> 25°C). Plants undergo **rapid wilting of green leaves during hot afternoons** without initial yellowing or leaf spots. Suspending a cut lower stem in clear water reveals milky white bacterial threads streaming within 2 minutes. **NO FUNGICIDE CURES BACTERIAL WILT**.\n\n")
                append("**Late Blight Treatment Protocol:**\n")
                append("• *Preventative*: Mancozeb 80% WP @ 50g per 16L knapsack or Copper Oxychloride 50% WP @ 50g per 16L.\n")
                append("• *Curative / Eradicant*: Metalaxyl 8% + Mancozeb 64% WP (Ridomil Gold) @ 50g per 16L tank or Dimethomorph + Cymoxanil.\n\n")
                append("**Bacterial Wilt Management Protocol:**\n")
                append("• Rogue and burn infected plants immediately (do not compost).\n")
                append("• Minimum 5-year crop rotation with non-Solanaceous crops (Maize, Rhodes Grass, Brassicas).\n")
                append("• Plant resistant grafted rootstocks or certified wilt-tolerant hybrid seeds.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 3. POULTRY VACCINATION & FEEDING
        if (q.contains("poultry") || q.contains("chick") || q.contains("vaccin") || q.contains("gumboro") || q.contains("marek") || q.contains("newcastle")) {
            return buildString {
                append("### 🐔 Precision Poultry Vaccination & Feeding Schedule\n\n")
                append("**Core Chick Vaccination Schedule (Day 1 - Week 18):**\n")
                append("• **Day 1 (Hatchery)**: Marek's Disease (HVT s/c) & Infectious Bronchitis (IB H120 coarse spray).\n")
                append("• **Day 7**: Newcastle Disease (ND HB1) + IB via **Eye Drop** (1 drop in eye).\n")
                append("• **Day 14**: Gumboro / IBD Intermediate strain in drinking water.\n")
                append("• **Day 21**: Gumboro / IBD Booster (Intermediate Plus strain) in drinking water.\n")
                append("• **Day 28 (Week 4)**: Newcastle Disease (LaSota strain) in drinking water.\n")
                append("• **Week 6**: Fowl Pox via **Wing Web Stab** puncture (verify 'take' scab at Day 7).\n")
                append("• **Week 8**: Fowl Typhoid (*Salmonella gallinarum* 9R strain) s/c injection.\n")
                append("• **Week 10**: Deworming (Piperazine/Levamisole) in drinking water.\n")
                append("• **Week 12**: Infectious Coryza inactivated bacterin injection.\n")
                append("• **Week 18**: Egg Drop Syndrome (EDS-76) + ND + IB triple oil vaccine before point of lay.\n\n")
                append("**Vaccine Drinking Water Protocol:**\n")
                append("1. Withdraw water 1.5 - 2 hours prior to vaccination so birds drink eagerly.\n")
                append("2. Add **2.5 g non-fat skimmed milk powder per 1 Liter of water** to neutralize residual chlorine.\n")
                append("3. Ensure entire flock consumes vaccine within 90 - 120 minutes out of direct sunlight.\n\n")
                append("**Feeding Program & Nutritional Focus:**\n")
                append("• *Day 1 - 10 (Pre-Starter)*: 20.5% CP, 2950 kcal ME/kg (15 g/bird/day). Brooding at 33 - 35°C.\n")
                append("• *Week 2 - 6 (Starter Mash)*: 19.5% CP, 2850 kcal ME/kg (32 g/bird/day).\n")
                append("• *Week 7 - 16 (Grower Mash)*: 16.0% CP, 2750 kcal ME/kg (68 g/bird/day).\n")
                append("• *Week 17 - 18 (Pre-Lay)*: 17.5% CP, 2.5% Calcium (builds medullary bone calcium reserves).\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 4. SOIL PH, LIMING, CEC
        if (q.contains("lime") || q.contains("cec") || q.contains("ph ") || q.contains("soil ph") || q.contains("acidity")) {
            return buildString {
                append("### 🧪 Soil Acidity Management, Liming & CEC Rebalancing\n\n")
                append("**1. Diagnosis of Strongly Acidic Soil (pH < 5.5):**\n")
                append("• Soil pH below 5.5 triggers **Aluminum (Al³⁺) and Manganese (Mn²⁺) toxicity**, burning root tips and stunting root elongation.\n")
                append("• Causes severe **Phosphorus fixation** into insoluble aluminum-phosphate complexes.\n")
                append("• Induces deficiencies of Calcium (Ca²⁺), Magnesium (Mg²⁺), and Molybdenum (Mo).\n\n")
                append("**2. Agricultural Liming Prescription:**\n")
                append("• To raise pH from ~4.8 to target 6.2 on loamy clay soils, apply **1.5 to 2.5 metric tonnes of Agricultural Lime (CaCO₃) per Hectare (approx. 600 - 1,000 kg per Acre)**.\n")
                append("• **Dolomitic Limestone (CaMg(CO₃)₂)** is required if soil test reveals Magnesium saturation < 10%.\n")
                append("• Broadcast lime evenly across dry soil 4 - 6 weeks before planting and incorporate into the top 15 - 20 cm.\n\n")
                append("**3. Albrecht Base Cation Saturation Ratio (BCSR) Targets:**\n")
                append("• **Calcium Saturation**: 65% - 75% • **Magnesium**: 10% - 15% • **Potassium**: 3% - 5%\n")
                append("• **Target Ca:Mg Ratio**: 5:1 to 7:1.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 5. IRRIGATION & WATER REQUIREMENT
        if (q.contains("irrigat") || q.contains("et0") || q.contains("drip") || q.contains("leaching")) {
            return buildString {
                append("### 💧 FAO-56 Irrigation Water Requirement & Salinity Management\n\n")
                append("**1. Crop Evapotranspiration Formula:**\n")
                append("• `ETc = ET0 × Kc` (where ET0 is 4.5 - 6.0 mm/day in tropical zones).\n")
                append("• For Tomatoes/Capsicums at mid-season peak flowering, `Kc = 1.15`.\n")
                append("• Example at `ET0 = 5.0 mm/day`: `ETc = 5.75 mm/day` (57.5 m³/ha/day).\n\n")
                append("**2. Water Application per Plant:**\n")
                append("• At 60 cm × 50 cm spacing (0.30 m²/plant), net demand = **1.73 L/plant/day**; with 90% drip efficiency = **1.95 - 2.1 L/plant/day**.\n\n")
                append("**3. Salinity & Leaching Fraction (LF):**\n")
                append("• `LF = ECw / (5 × ECe - ECw)`. When `ECw > 1.0 mS/cm`, apply 10% - 15% extra leaching water.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 6. COMPOST & MANURE
        if (q.contains("compost") || q.contains("manure") || q.contains("berkeley") || q.contains("c:n")) {
            return buildString {
                append("### 🌿 Organic Manure Masterclass & 18-Day Berkeley Composting\n\n")
                append("**1. The 18-Day Berkeley Hot Composting Method:**\n")
                append("• Layer **Browns (Carbon)** and **Greens (Nitrogen)** at an initial C:N ratio of 25:1 to 30:1 in a 1m × 1m × 1.5m pile.\n")
                append("• Turn on Day 4, then every 2 days (Days 6, 8, 10, 12, 14, 16, 18) maintaining core temperature at 55 - 65°C.\n\n")
                append("**2. Field Application Tonnage:**\n")
                append("• Commercial Vegetables: **10 - 15 tonnes/ha** or 200 - 400g well-cured manure per planting hole.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 7. SPRAY NOZZLE & CALIBRATION
        if (q.contains("nozzle") || q.contains("spray time") || q.contains("discharge") || q.contains("orifice")) {
            return buildString {
                append("### 🎯 Spray Nozzle Discharge, Pressure & Calibration\n\n")
                append("• **Pressure Law:** `q = q0 × sqrt(P / 3.0 bar)`. A Yellow 02 nozzle (`0.80 L/min` at 3.0 bar) delivers `0.653 L/min` at 2.0 bar and `0.924 L/min` at 4.0 bar.\n")
                append("• **Tank Emptying Time:** 16L knapsack at 3.0 bar = **20 minutes**; 20L tank = **25 minutes**.\n")
                append("• **Wear Rule:** Replace nozzle if 60-second output exceeds nominal flow by **> 10%**.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 8. AUDIT & COMPLIANCE
        if (q.contains("audit") || q.contains("compliance") || q.contains("globalgap") || q.contains("ks 1758") || q.contains("mrl")) {
            return buildString {
                append("### 📋 GlobalG.A.P. IFA v6 & KS 1758 Agricultural Compliance Protocols\n\n")
                append("1. **PHI & MRLs:** Strictly log application date + statutory Pre-Harvest Interval before harvesting.\n")
                append("2. **Triple-Rinsing:** Fill 1/4 empty bottle with clean water, shake 30s, empty into tank (repeat 3×), puncture and store locked.\n")
                append("3. **Worker Safety:** Certified PPE, annual cholinesterase testing for OP/carbamate handlers, clean water within 500m.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9. DYNAMIC QUARANTINE, THREAT, WEED, FERTILIZER & CURRICULUM CATALOG SEARCH
        val tokens = q.split(Regex("[^a-z0-9]+"))
            .filter { it.length >= 3 && it !in STOP_WORDS }
            .distinct()

        // 9a. Fertilizer Intent Check (run before ThreatCatalog so "Tomato fertilizer plan" returns nutrition rather than Tomato pests)
        val asksAboutFertilizer = q.contains("fertiliz") || q.contains("topdress") || q.contains("basal") ||
            q.contains("npk") || q.contains("dap") || q.contains("can ") || q.contains("urea") ||
            q.contains("nutrient") || q.contains("bags per ha")
        val matchedCropPlan = AgronomyCatalog.cropFertilizerPlans.firstOrNull { plan ->
            val planLower = plan.cropName.lowercase()
            q.contains(planLower) || (asksAboutFertilizer && tokens.any { tok -> tok.length >= 4 && planLower.contains(tok) })
        }
        val matchedFert = AgronomyCatalog.fertilizerProducts.firstOrNull { fert ->
            val fertLower = fert.name.lowercase()
            q.contains(fertLower) || (asksAboutFertilizer && tokens.any { tok -> tok.length >= 4 && fertLower.contains(tok) })
        }
        if (asksAboutFertilizer && (matchedCropPlan != null || matchedFert != null)) {
            return buildString {
                append("### 🌱 Crop Nutrition & Fertilizer Prescription\n\n")
                matchedCropPlan?.let { plan ->
                    append("**Crop Plan: ${plan.cropName}**\n")
                    append("• **Target Nutrients:** ${plan.targetNPerHa} N - ${plan.targetPPerHa} P2O5 - ${plan.targetKPerHa} K2O kg/ha\n")
                    append("• **Basal Planting:** ${plan.recommendedBasalBrand} @ ${plan.basalRateKgPerHa} kg/ha\n")
                    append("• **Topdressing:** ${plan.recommendedTopdressBrand} @ ${plan.topdressRateKgPerHa} kg/ha\n")
                    if (plan.splitSchedule.isNotEmpty()) {
                        append("• **Split Schedule:** ${plan.splitSchedule.joinToString("; ")}\n")
                    }
                    append("• **Soil Acidity Note:** ${plan.acidityAlert}\n")
                    plan.agronomicGuidance.take(3).forEach { tip ->
                        append("• $tip\n")
                    }
                    append("\n")
                }
                matchedFert?.let { f ->
                    append("**Product Profile: ${f.brand} ${f.name} (${f.npkRatio})**\n")
                    append("• **Standard Rate:** ${f.standardRatePerHa}\n")
                    append("• **Soil pH Effect:** ${f.soilAcidImpact}\n")
                    append("• **Decision Rule:** ${f.decisionRule}\n\n")
                }
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9b. Quarantine Pests (e.g., False Codling Moth, Fruit Fly, Tuta quarantine)
        val matchedQuarantine = QuarantinePestCatalog.quarantinePests.firstOrNull { qp ->
            q.contains(qp.commonName.lowercase()) ||
                q.contains(qp.scientificName.lowercase()) ||
                (q.contains("fcm") && qp.id == "qp_fcm") ||
                tokens.count { tok ->
                    qp.commonName.lowercase().contains(tok) ||
                        qp.reasonForQuarantineListing.lowercase().contains(tok)
                } >= 2
        }
        if (matchedQuarantine != null) {
            return buildString {
                append("### 🚨 ${matchedQuarantine.commonName} (*${matchedQuarantine.scientificName}*)\n\n")
                append("**Regulatory Status:** ${matchedQuarantine.regulatoryStatus.badgeLabel}\n")
                append("**Reason:** ${matchedQuarantine.reasonForQuarantineListing}\n\n")
                append("**Identification & Biology:** ${matchedQuarantine.biologyAndIdentification}\n\n")
                if (matchedQuarantine.diagnosticDamageSymptoms.isNotEmpty()) {
                    append("**Diagnostic Damage Symptoms:**\n")
                    matchedQuarantine.diagnosticDamageSymptoms.forEach { append("• $it\n") }
                    append("\n")
                }
                if (matchedQuarantine.preHarvestBiosecurityControls.isNotEmpty()) {
                    append("**Pre-Harvest Biosecurity Controls:**\n")
                    matchedQuarantine.preHarvestBiosecurityControls.take(4).forEach { append("• $it\n") }
                    append("\n")
                }
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9b-2. Agronomy Degree Pack Exact/Syllabus Match (before ThreatCatalog so "plant" or "crop" in course titles doesn't trigger ThreatCatalog)
        val asksAboutCurriculum = q.contains("syllabus") || q.contains("curriculum") ||
            q.contains("degree") || q.contains("bachelor") || q.contains("year 1") ||
            q.contains("year 2") || q.contains("year 3") || q.contains("course") || q.contains("module")
        val exactCurriculumMatch = AgronomyDegreePackCatalog.allSubjects.firstOrNull { subj ->
            q.contains(subj.subjectName.lowercase()) ||
                q.contains(subj.courseCode.lowercase()) ||
                (asksAboutCurriculum && tokens.count { tok ->
                    subj.subjectName.lowercase().contains(tok) ||
                        subj.executiveSummary.lowercase().contains(tok)
                } >= 2)
        }
        if (exactCurriculumMatch != null) {
            return formatDegreeSubjectResponse(exactCurriculumMatch)
        }

        // 9c. CropShield ThreatCatalog (Pests, Diseases, Weeds, Soils)
        val scoredThreats = ThreatCatalog.allThreats.map { threat ->
            val nameLower = threat.name.lowercase()
            val sciLower = threat.scientificName.lowercase()
            val cropsLower = threat.targetCrops.joinToString(" ").lowercase()
            val keysLower = threat.diagnosticKeys.joinToString(" ").lowercase()
            var score = 0
            if (q.contains(nameLower)) score += 20
            if (q.contains(sciLower)) score += 20
            tokens.forEach { tok ->
                if (nameLower.contains(tok)) score += 6
                if (cropsLower.contains(tok)) score += 3
                if (keysLower.contains(tok) || threat.summary.lowercase().contains(tok)) score += 2
            }
            if (q.contains("pest") || q.contains("insect") || q.contains("bug") || q.contains("worm") || q.contains("mite")) {
                if (threat.type.name == "PEST") score += 2
            }
            if (q.contains("disease") || q.contains("spot") || q.contains("rot") || q.contains("rust") || q.contains("mildew")) {
                if (threat.type.name == "DISEASE") score += 2
            }
            threat to score
        }.filter { it.second >= 5 }.sortedByDescending { it.second }

        if (scoredThreats.isNotEmpty()) {
            val topThreats = scoredThreats.take(2).map { it.first }
            return buildString {
                val primary = topThreats.first()
                append("### 🌾 ${primary.name} (*${primary.scientificName}*) — ${primary.type.label}\n\n")
                append("**Summary:** ${primary.summary}\n")
                append("**Target Crops:** ${primary.targetCrops.joinToString(", ")}\n\n")
                append("**Key Diagnostic Signs:**\n")
                primary.diagnosticKeys.take(4).forEach { append("• $it\n") }
                append("\n**Cultural & Organic IPM Controls:**\n")
                primary.ipmPlan.culturalControls.take(3).forEach { append("• $it\n") }
                primary.cabiBioProtections.firstOrNull()?.let { bio ->
                    append("• **Biological Control (CABI):** ${bio.tradeName} (*${bio.activeAgent}*) — ${bio.modeOfAction}\n")
                }
                if (primary.pcpbChemicals.isNotEmpty() || primary.ipmPlan.chemicalControls.isNotEmpty()) {
                    append("\n**PCPB Registered Chemicals & Knapsack Rates:**\n")
                    primary.pcpbChemicals.take(3).forEach { chem ->
                        append("• **${chem.tradeName}** (${chem.activeIngredient}) — ${chem.knapsackDosage} (PHI: ${chem.phiDays} days)\n")
                    }
                    if (primary.pcpbChemicals.isEmpty()) {
                        primary.ipmPlan.chemicalControls.take(3).forEach { chem ->
                            append("• **${chem.tradeName.ifBlank { chem.activeIngredient }}** (${chem.activeIngredient}) @ ${chem.dosagePerKnapsack}, PHI ${chem.phiDays} days\n")
                        }
                    }
                }
                if (topThreats.size > 1) {
                    val alt = topThreats[1]
                    append("\n**Also Consider (${alt.type.label}):** *${alt.name}* (${alt.scientificName}) — ${alt.diagnosticKeys.firstOrNull() ?: alt.summary}")
                }
                append("\n\n*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9d. Weed Catalog
        val matchedWeed = AgronomyCatalog.kenyanWeeds.firstOrNull { weed ->
            q.contains(weed.commonName.lowercase()) ||
                q.contains(weed.botanicalName.lowercase()) ||
                weed.localKenyanNames.any { q.contains(it.lowercase()) }
        }
        if (matchedWeed != null) {
            return buildString {
                append("### 🌿 Weed Management: ${matchedWeed.commonName} (*${matchedWeed.botanicalName}*)\n\n")
                append("• **Classification:** ${matchedWeed.type.displayName} • **Crops Attacked:** ${matchedWeed.cropsAttacked.joinToString(", ")}\n")
                append("• **Agronomic Impact:** ${matchedWeed.competitiveImpact}\n")
                append("• **Cultural & Mechanical Control:** ${matchedWeed.culturalManagement.take(3).joinToString("; ")}\n")
                if (matchedWeed.registeredHerbicides.isNotEmpty()) {
                    append("\n**Registered Herbicides:**\n")
                    matchedWeed.registeredHerbicides.take(3).forEach { h ->
                        append("• **${h.tradeName}** (${h.activeIngredient}) — ${h.dosagePerKnapsack} [${h.timing.displayName}]\n")
                    }
                }
                append("\n*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9e. Secondary Fertilizer Catch-all
        if (matchedCropPlan != null || matchedFert != null) {
            return buildString {
                append("### 🌱 Crop Nutrition & Fertilizer Prescription\n\n")
                matchedCropPlan?.let { plan ->
                    append("**Crop Plan: ${plan.cropName}**\n")
                    append("• **Target Nutrients:** ${plan.targetNPerHa} N - ${plan.targetPPerHa} P2O5 - ${plan.targetKPerHa} K2O kg/ha\n")
                    append("• **Basal Planting:** ${plan.recommendedBasalBrand} @ ${plan.basalRateKgPerHa} kg/ha\n")
                    append("• **Topdressing:** ${plan.recommendedTopdressBrand} @ ${plan.topdressRateKgPerHa} kg/ha\n")
                }
                matchedFert?.let { f ->
                    append("**Product Profile: ${f.brand} ${f.name} (${f.npkRatio})**\n")
                    append("• **Standard Rate:** ${f.standardRatePerHa}\n")
                }
                append("\n*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 9f. Agronomy Degree Pack Subjects fallback (token-only match)
        val matchedSubject = AgronomyDegreePackCatalog.allSubjects.firstOrNull { subj ->
            tokens.count { tok ->
                subj.subjectName.lowercase().contains(tok) ||
                    subj.executiveSummary.lowercase().contains(tok)
            } >= 2
        }
        if (matchedSubject != null) {
            return formatDegreeSubjectResponse(matchedSubject)
        }

        // DEFAULT AGRONOMIC INTELLIGENCE ANSWER
        return buildString {
            append("### 🌾 CropShield Agricultural Advisory\n\n")
            append("Regarding your inquiry: **\"$query\"**\n\n")
            append("**Agronomic Best Practice Principles:**\n")
            append("1. **Diagnostic Soil & Crop Scouting**: Inspect 20 representative plants across a W-pattern in the field—check leaf undersides for insects/eggs/spores, growing whorls, and roots.\n")
            append("2. **Integrated Pest Management (IPM)**: Distinguish between insect pest feeding damage (holes, frass, mines, sucking colonies) and fungal/bacterial diseases before choosing a spray.\n")
            append("3. **Balanced Plant Nutrition**: Match basal phosphorus at planting, nitrogen topdressing during vegetative growth, and potassium/calcium during flowering & fruiting.\n")
            append("4. **Safe Chemical Stewardship**: Follow exact knapsack rates (per 16L/20L tank), Pre-Harvest Intervals (PHI), and PPE rules.\n\n")
            append("💡 *Tip: Mention the crop name + symptom (e.g. 'tomato whiteflies' or 'beans rust') or attach a photo for an instant specific prescription.*\n\n")
            append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
        }
    }

    private fun formatDegreeSubjectResponse(
        subj: com.example.data.science.curriculum.AgronomyPackSubject
    ): String = buildString {
        append("### 🎓 ${subj.courseCode}: ${subj.subjectName} (${subj.volume.yearLabel})\n\n")
        append("${subj.executiveSummary}\n\n")
        append("**Core Principles:**\n")
        subj.coreConcepts.take(4).forEach { append("• $it\n") }
        if (subj.keyFormulasAndMetrics.isNotEmpty()) {
            append("\n**Key Formulas & Metrics:**\n")
            subj.keyFormulasAndMetrics.take(3).forEach { append("• $it\n") }
        }
        append("\n*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
    }

    private val STOP_WORDS = setOf(
        "the", "and", "for", "with", "that", "this", "from", "have", "has", "how", "what",
        "why", "when", "where", "which", "can", "you", "your", "crop", "crops", "farm",
        "farmer", "help", "please", "need", "about", "into", "are", "was", "were"
    )

    private fun safeLogE(tag: String, message: String, tr: Throwable? = null) {
        try {
            Log.e(tag, message, tr)
        } catch (_: Throwable) {
            // Ignored when running under plain JVM unit tests without Android Log
        }
    }

    private fun detectTopicCategory(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("pest") || q.contains("insect") || q.contains("caterpillar") || q.contains("worm") || q.contains("mite") || q.contains("thrip") || q.contains("whitefl") || q.contains("aphid") -> "Pest Management"
            q.contains("disease") || q.contains("blight") || q.contains("fung") || q.contains("bacter") || q.contains("wilt") || q.contains("rust") || q.contains("mildew") -> "Crop Pathology"
            q.contains("poultry") || q.contains("chick") || q.contains("layer") || q.contains("broiler") || q.contains("vaccin") -> "Poultry Health"
            q.contains("soil") || q.contains("lime") || q.contains("cec") || q.contains("ph") || q.contains("manure") || q.contains("compost") -> "Soil Science"
            q.contains("water") || q.contains("irrigat") || q.contains("drip") || q.contains("et0") -> "Irrigation Science"
            q.contains("nozzle") || q.contains("spray") || q.contains("knapsack") || q.contains("pressure") -> "Sprayer Calibration"
            q.contains("audit") || q.contains("compliance") || q.contains("globalgap") || q.contains("ks 1758") -> "Farm Compliance"
            else -> "General Agronomy"
        }
    }

    private fun detectRelatedCalculator(query: String): String? {
        val q = query.lowercase()
        return when {
            q.contains("nozzle") || q.contains("discharge") || q.contains("spray time") -> "NOZZLE"
            q.contains("soil") || q.contains("cec") || q.contains("lime") || q.contains("manure") || q.contains("texture") -> "SOIL_CALCULATOR"
            q.contains("water") || q.contains("irrigat") || q.contains("et0") || q.contains("drip") -> "SOIL_CALCULATOR"
            q.contains("poultry") || q.contains("chick") || q.contains("vaccin") -> "POULTRY"
            q.contains("audit") || q.contains("globalgap") || q.contains("ks 1758") || q.contains("compliance") -> "AUDIT"
            q.contains("fertilizer") || q.contains("knapsack") || q.contains("pesticide") -> "CALCULATOR"
            else -> null
        }
    }

    private fun generateFollowUps(query: String): List<String> {
        val q = query.lowercase()
        return when {
            q.contains("armyworm") -> listOf(
                "What is the knapsack dosage for Coragen or Emamectin Benzoate?",
                "How does the Push-Pull system with Desmodium work?",
                "What is the economic threshold for spraying Fall Armyworm?"
            )
            q.contains("tomato") || q.contains("blight") -> listOf(
                "How often should I spray Ridomil Gold in rainy weather?",
                "Can I spray copper oxychloride during flowering?",
                "What crop rotation breaks the Solanaceae blight cycle?"
            )
            q.contains("poultry") || q.contains("chick") -> listOf(
                "Why must skimmed milk be added to drinking water vaccines?",
                "What is the feed consumption per bird for layers at 18 weeks?",
                "How do I prevent Gumboro disease in day-old chicks?"
            )
            q.contains("soil") || q.contains("lime") || q.contains("ph") -> listOf(
                "What is the difference between Agricultural Lime and Gypsum?",
                "How do I balance the Ca:Mg ratio in red clay soil?",
                "How many tonnes of farmyard manure do I need per acre?"
            )
            q.contains("irrigat") || q.contains("water") -> listOf(
                "How do I calculate leaching fraction if water EC is high?",
                "How do I acidify irrigation water with phosphoric acid?",
                "What is the best irrigation interval for sandy loam soil?"
            )
            else -> listOf(
                "What are the best crop rotation practices for my field?",
                "How do I calibrate my knapsack sprayer nozzle?",
                "What are the GlobalG.A.P. chemical storage rules?"
            )
        }
    }
}
