package com.example.data.scanner

import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import android.util.Base64
import android.util.Log
import com.example.data.ThreatCatalog
import com.example.model.ChemicalRecommendation
import com.example.model.CropThreat
import com.example.model.ThreatType
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.Schema
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ScanDiagnosisResult(
    val threat: CropThreat?,
    val threatId: String,
    val diseaseName: String,
    val scientificName: String,
    val crop: String,
    val confidencePercent: Int,
    val severity: String, // Mild, Moderate, Severe, Extreme
    val visualSymptomsDetected: List<String>,
    val recommendedCulturalAction: String,
    val recommendedChemicals: List<String>,
    val recommendedChemicalObject: ChemicalRecommendation?,
    val isOnlineAiAnalysis: Boolean,
    val detailedAnalysisText: String,
    val cabiBiocontrolSolution: String = "",
    val infonetOrganicAction: String = "",
    val pcpbRegisteredSolution: String = "",
    val diagnosticCategory: ThreatType = threat?.type ?: ThreatType.DISEASE,
    val identifiedName: String = diseaseName,
    val targetCropsList: List<String> = threat?.targetCrops ?: (if (crop.isNotBlank()) listOf(crop) else emptyList()),
    val vulnerableCropStages: String = threat?.vulnerableCropStages ?: "Vegetative through harvest",
    val yieldLossPotential: String = threat?.yieldLossPotential ?: "30% - 70% if unmanaged",
    val economicThresholdInfo: String = threat?.economicThresholdInfo ?: (threat?.ipmPlan?.economicThreshold ?: "Intervene upon first symptom detection"),
    val mechanicalControlAction: String = threat?.mechanicalControls?.firstOrNull() ?: (threat?.ipmPlan?.physicalMechanicalControls?.firstOrNull() ?: "Hand rogue or cultivate"),
    val realTimeTimestamp: String = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault()).format(Date()),
    val analysisNotice: String? = null
)

object PhotoDiseaseScanner {

    private const val TAG = "PhotoDiseaseScanner"

    private val scanResponseSchema by lazy {
        Schema.obj(
            properties = mapOf(
                "threatId" to Schema.string(
                    description = "Exact CropShield catalog threat ID if matching, or 'custom_pest', 'custom_disease', 'custom_weed', or 'custom_soil'."
                ),
                "threatType" to Schema.enumeration(
                    values = listOf("PEST", "DISEASE", "WEED", "SOIL"),
                    description = "Strict category: PEST for insects, caterpillars, mites, thrips, aphids, whiteflies, beetles, slugs, or insect feeding/chewing/mining damage; DISEASE for fungal, oomycete, bacterial, or viral pathogens; WEED for invasive/parasitic weeds; SOIL for soil profile/mineral deficiencies."
                ),
                "diseaseName" to Schema.string(description = "Common name of the identified pest, disease, weed, or soil condition."),
                "scientificName" to Schema.string(description = "Scientific Latin name of the causal insect, pathogen, weed, or soil order."),
                "crop" to Schema.string(description = "Host crop identified or 'General Field Crop'."),
                "confidence" to Schema.integer(description = "Diagnostic confidence percentage between 55 and 99."),
                "severity" to Schema.enumeration(
                    values = listOf("Mild", "Moderate", "Severe", "Extreme"),
                    description = "Observed infestation or infection severity."
                ),
                "symptoms" to Schema.array(
                    items = Schema.string(),
                    description = "3 to 5 specific visual symptoms observed directly in the photo."
                ),
                "culturalAction" to Schema.string(description = "Infonet-Biovision cultural or organic IPM practice."),
                "mechanicalAction" to Schema.string(description = "Physical or mechanical intervention step."),
                "recommendedChemicals" to Schema.array(
                    items = Schema.string(),
                    description = "Registered agrochemical active ingredients and dosages matching the identified category."
                ),
                "summary" to Schema.string(description = "Concise visual explanation justifying why this is classified as PEST vs DISEASE vs WEED vs SOIL."),
                "cabiBiocontrol" to Schema.string(description = "CABI BioProtection biological control agent or biopesticide."),
                "infonetPractice" to Schema.string(description = "Infonet-Biovision organic field practice."),
                "pcpbChemical" to Schema.string(description = "PCPB registered control product with FRAC/IRAC code and PHI days.")
            )
        )
    }

    private val scannerModel by lazy {
        Firebase.ai.generativeModel(
            modelName = "gemini-3.8-flash",
            generationConfig = generationConfig {
                responseMimeType = "application/json"
                responseSchema = scanResponseSchema
                temperature = 0.2f
            }
        )
    }

    private fun safeLogE(message: String, throwable: Throwable? = null) {
        try {
            if (throwable != null) {
                Log.e(TAG, message, throwable)
            } else {
                Log.e(TAG, message)
            }
        } catch (_: Throwable) {
            // Ignored on plain JVM unit tests without Robolectric
        }
    }

    fun scaleBitmapForInference(bitmap: Bitmap, maxDimension: Int = 1024): Bitmap {
        return if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
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
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }
    }

    /**
     * Converts a bitmap into a Base64 JPEG string (downscaling if necessary for efficiency).
     */
    fun bitmapToBase64(bitmap: Bitmap, maxDimension: Int = 1024): String {
        val scaledBitmap = scaleBitmapForInference(bitmap, maxDimension)
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * Performs real-time multimodal scan analysis on a crop, pest, weed, or soil photograph
     * via Firebase AI Logic (gemini-3.8-flash) with App Check attestation.
     * Falls back transparently to the domain-aware offline optical symptom engine on network/service error.
     */
    suspend fun analyzeCropPhoto(
        bitmap: Bitmap,
        cropHint: String = "Auto-Detect",
        categoryFilter: ThreatType? = null
    ): ScanDiagnosisResult = withContext(Dispatchers.IO) {
        var fallbackNotice: String? = null
        try {
            val onlineResult = withTimeout(60_000L) {
                callFirebaseAiVision(bitmap, cropHint, categoryFilter)
            }
            if (onlineResult != null) {
                return@withContext onlineResult
            }
            fallbackNotice = "Gemini Vision returned an empty response; showing offline optical pre-screening."
        } catch (e: TimeoutCancellationException) {
            safeLogE("Gemini Vision analysis timed out after 60s", e)
            fallbackNotice = "Gemini Vision timed out after 60s; showing offline optical pre-screening."
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            safeLogE("Gemini Vision AI failed, falling back to offline optical engine: ${e.message}", e)
            fallbackNotice = "Online Vision AI unavailable (${e.localizedMessage ?: "network/App Check error"}); showing offline optical pre-screening."
        }

        analyzeOfflineOpticalPatterns(
            bitmap = bitmap,
            cropHint = cropHint,
            categoryFilter = categoryFilter,
            analysisNotice = fallbackNotice
        )
    }

    private suspend fun callFirebaseAiVision(
        bitmap: Bitmap,
        cropHint: String,
        categoryFilter: ThreatType?
    ): ScanDiagnosisResult? {
        val scaledBitmap = scaleBitmapForInference(bitmap, 1024)
        val prompt = buildVisionPrompt(cropHint, categoryFilter)

        val response = scannerModel.generateContent(
            content {
                text(prompt)
                image(scaledBitmap)
            }
        )
        val rawText = response.text ?: return null
        if (rawText.isBlank()) return null

        return parseVisionJsonResponse(rawText, cropHint, categoryFilter)
    }

    private fun buildVisionPrompt(cropHint: String, categoryFilter: ThreatType?): String = buildString {
        appendLine("You are an expert plant pathologist, agricultural entomologist, weed scientist, and pedologist diagnosing crop threats and soil profiles.")
        appendLine("Use Infonet-Biovision (cultural/organic IPM), CABI BioProtection (biocontrol), and PCPB Kenya (registered agrochemicals) standards.")
        appendLine()
        appendLine("CRITICAL DISTINCTION (DO NOT CONFUSE PESTS AND DISEASES):")
        appendLine("- Classify strictly as threatType='PEST' whenever you see:")
        appendLine("  * Any insect, caterpillar, larva, pupa, moth, beetle, aphid, whitefly, thrips, mite, or leafhopper.")
        appendLine("  * Ragged chewed holes, notched leaf margins, 'windowpaning', translucent blotch mines or serpentine tunnels inside leaves, sawdust/granular frass, bored fruit/stem holes, deadheart shoots, silk webbing, honeydew, sooty mold on sucking colonies, or silvery thrips scarring.")
        appendLine("  * Even if secondary yellowing/necrosis surrounds chewed or mined areas, insect feeding damage is ALWAYS threatType='PEST', NEVER 'DISEASE'.")
        appendLine("- Classify strictly as threatType='DISEASE' ONLY when pathogen symptoms dominate with no primary insect signs:")
        appendLine("  * Water-soaked spreading dark blight with pale sporulation ring (Late Blight), concentric target rings (Early Blight), bright orange/brown powdery pustules (Rust), uniform talcum-white mycelium coating (Powdery Mildew), angular vein-bound purple lesions (Downy Mildew), green vascular wilt (Bacterial Wilt), or sunken pod cankers (Anthracnose).")
        appendLine("- Classify as threatType='WEED' for parasitic or invasive weeds (e.g. Striga, Purple Nutsedge).")
        appendLine("- Classify as threatType='SOIL' for soil profiles, texture, cracking clays, red volcanic nitisols, or pure abiotic soil scenes.")
        appendLine()
        if (cropHint != "Auto-Detect") {
            appendLine("Farmer's stated crop context: $cropHint.")
        }
        if (categoryFilter != null) {
            appendLine("The farmer explicitly filtered to domain: ${categoryFilter.name} (${categoryFilter.label}). Stay strictly within ${categoryFilter.name}.")
        }
        appendLine()
        appendLine("Known CropShield catalog IDs (use exact threatId only if it truly matches the photo; otherwise use 'custom_pest', 'custom_disease', 'custom_weed', or 'custom_soil'):")
        appendLine("PESTS: fall_armyworm, tuta_absoluta, african_bollworm, spider_mites, western_flower_thrips, aphids, maize_stalk_borer, maize_stem_borer, diamondback_moth, whiteflies, maruca_pod_borer, bean_fly")
        appendLine("DISEASES: late_blight, early_blight, bacterial_wilt, bean_rust, cereal_rust, flower_rust, chrysanthemum_white_rust, rose_powdery_mildew, rose_downy_mildew, botrytis_blight, bean_anthracnose, mlnd, black_rot, purple_blotch_onion")
        appendLine("WEEDS: weed_striga, weed_purple_nutsedge")
        appendLine("SOIL: soil_humic_nitisol, soil_pellic_vertisol")
    }

    internal fun parseVisionJsonResponse(
        rawText: String,
        cropHint: String,
        categoryFilter: ThreatType?
    ): ScanDiagnosisResult {
        val parsedJson = JSONObject(rawText)
        val rawThreatId = parsedJson.optString("threatId", "").trim()
        val rawTypeStr = parsedJson.optString("threatType", "").trim().uppercase(Locale.ROOT)
        val returnedName = parsedJson.optString("diseaseName", "").trim()
        val returnedScientific = parsedJson.optString("scientificName", "").trim()
        val returnedSummary = parsedJson.optString("summary", "").trim()

        val resolvedCategory = resolveThreatType(
            rawTypeStr = rawTypeStr,
            categoryFilter = categoryFilter,
            rawThreatId = rawThreatId,
            returnedName = returnedName,
            summary = returnedSummary
        )

        // Match ONLY within the resolved domain so a PEST is NEVER mapped to late_blight (DISEASE)
        val domainCatalog = ThreatCatalog.allThreats.filter { it.type == resolvedCategory }
        val matchedThreat = domainCatalog.find { candidate ->
            rawThreatId.isNotBlank() && candidate.id.equals(rawThreatId, ignoreCase = true)
        } ?: domainCatalog.find { candidate ->
            returnedName.isNotBlank() && (
                candidate.name.contains(returnedName, ignoreCase = true) ||
                    returnedName.contains(candidate.name, ignoreCase = true) ||
                    (returnedScientific.isNotBlank() && candidate.scientificName.contains(returnedScientific, ignoreCase = true))
                )
        }

        val symptomsList = mutableListOf<String>()
        val symptomsArray = parsedJson.optJSONArray("symptoms")
        if (symptomsArray != null) {
            for (i in 0 until symptomsArray.length()) {
                val item = symptomsArray.optString(i, "").trim()
                if (item.isNotBlank()) symptomsList.add(item)
            }
        }
        if (symptomsList.isEmpty() && matchedThreat != null) {
            symptomsList.addAll(matchedThreat.diagnosticKeys.take(3))
        } else if (symptomsList.isEmpty()) {
            symptomsList.add("Visual anomaly inspected under ${resolvedCategory.label} domain")
        }

        val chemicalsList = mutableListOf<String>()
        val chemArray = parsedJson.optJSONArray("recommendedChemicals")
        if (chemArray != null) {
            for (i in 0 until chemArray.length()) {
                val chem = chemArray.optString(i, "").trim()
                if (chem.isNotBlank()) chemicalsList.add(chem)
            }
        }
        if (chemicalsList.isEmpty() && matchedThreat != null) {
            matchedThreat.pcpbChemicals.forEach { chem ->
                chemicalsList.add("${chem.tradeName} (${chem.activeIngredient}) - ${chem.knapsackDosage}")
            }
        }

        val selectedChem = matchedThreat?.ipmPlan?.chemicalControls?.firstOrNull()
        val displayName = returnedName.ifBlank {
            matchedThreat?.name ?: defaultDomainLabel(resolvedCategory)
        }
        val displayScientific = returnedScientific.ifBlank {
            matchedThreat?.scientificName ?: "Unverified field species"
        }
        val displayCrop = parsedJson.optString("crop", "").ifBlank {
            if (cropHint != "Auto-Detect") cropHint else matchedThreat?.targetCrops?.firstOrNull() ?: "General Field Crop"
        }

        val cabiBiocontrol = parsedJson.optString("cabiBiocontrol", "").ifBlank {
            matchedThreat?.cabiBioProtections?.firstOrNull()?.let {
                "${it.tradeName} (${it.activeAgent}) - ${it.modeOfAction}"
            } ?: defaultCabiHint(resolvedCategory)
        }

        val infonetPractice = parsedJson.optString("infonetPractice", "").ifBlank {
            matchedThreat?.infonetPractices?.firstOrNull()?.let {
                "${it.title}: ${it.description}"
            } ?: defaultCulturalHint(resolvedCategory)
        }

        val pcpbChem = parsedJson.optString("pcpbChemical", "").ifBlank {
            matchedThreat?.pcpbChemicals?.firstOrNull()?.let {
                "${it.tradeName} [${it.pcpbRegNo}] - ${it.activeIngredient}. Knapsack: ${it.knapsackDosage}"
            } ?: chemicalsList.firstOrNull().orEmpty()
        }

        return ScanDiagnosisResult(
            threat = matchedThreat,
            threatId = matchedThreat?.id ?: rawThreatId.ifBlank { "custom_${resolvedCategory.name.lowercase(Locale.ROOT)}" },
            diseaseName = displayName,
            scientificName = displayScientific,
            crop = displayCrop,
            confidencePercent = parsedJson.optInt("confidence", 90).coerceIn(50, 99),
            severity = parsedJson.optString("severity", "").ifBlank { matchedThreat?.severityLevel ?: "Moderate" },
            visualSymptomsDetected = symptomsList,
            recommendedCulturalAction = parsedJson.optString("culturalAction", "").ifBlank {
                matchedThreat?.ipmPlan?.culturalControls?.firstOrNull() ?: defaultCulturalHint(resolvedCategory)
            },
            recommendedChemicals = chemicalsList,
            recommendedChemicalObject = selectedChem,
            isOnlineAiAnalysis = true,
            detailedAnalysisText = returnedSummary.ifBlank {
                matchedThreat?.summary ?: "Diagnosed by Gemini Vision AI within ${resolvedCategory.label} domain."
            },
            cabiBiocontrolSolution = cabiBiocontrol,
            infonetOrganicAction = infonetPractice,
            pcpbRegisteredSolution = pcpbChem,
            diagnosticCategory = resolvedCategory,
            identifiedName = displayName,
            targetCropsList = matchedThreat?.targetCrops ?: listOf(displayCrop),
            vulnerableCropStages = matchedThreat?.vulnerableCropStages ?: "Active vegetative and reproductive stages",
            yieldLossPotential = matchedThreat?.yieldLossPotential ?: "Variable depending on scouting threshold",
            economicThresholdInfo = matchedThreat?.economicThresholdInfo ?: "Scout 20 plants per plot and intervene before threshold breach",
            mechanicalControlAction = parsedJson.optString("mechanicalAction", "").ifBlank {
                matchedThreat?.mechanicalControls?.firstOrNull()
                    ?: matchedThreat?.ipmPlan?.physicalMechanicalControls?.firstOrNull()
                    ?: "Hand-remove affected material and install traps"
            }
        )
    }

    private fun resolveThreatType(
        rawTypeStr: String,
        categoryFilter: ThreatType?,
        rawThreatId: String,
        returnedName: String,
        summary: String
    ): ThreatType {
        if (categoryFilter != null) return categoryFilter

        when (rawTypeStr) {
            "PEST" -> return ThreatType.PEST
            "DISEASE" -> return ThreatType.DISEASE
            "WEED" -> return ThreatType.WEED
            "SOIL" -> return ThreatType.SOIL
        }

        // Check catalog match if model omitted threatType
        val catalogHit = ThreatCatalog.allThreats.find { it.id.equals(rawThreatId, ignoreCase = true) }
        if (catalogHit != null) return catalogHit.type

        val text = "$returnedName $summary".lowercase(Locale.ROOT)
        val pestSignals = listOf(
            "worm", "caterpillar", "larva", "moth", "borer", "aphid", "mite", "thrips",
            "whitefly", "whiteflies", "beetle", "weevil", "fly", "leafminer", "locust",
            "hopper", "chew", "frass", "webbing", "insect", "pest"
        )
        return when {
            pestSignals.any { text.contains(it) } -> ThreatType.PEST
            text.contains("weed") || text.contains("striga") || text.contains("sedge") -> ThreatType.WEED
            text.contains("soil") || text.contains("nitisol") || text.contains("vertisol") -> ThreatType.SOIL
            else -> ThreatType.DISEASE
        }
    }

    private fun findCatalogThreat(id: String, fallbackType: ThreatType): CropThreat {
        return ThreatCatalog.allThreats.find { it.id == id && it.type == fallbackType }
            ?: ThreatCatalog.allThreats.first { it.type == fallbackType }
    }

    /**
     * Resilient Offline Optical Symptom Engine.
     * Distinguishes bright background/sky from genuine foliar white powdery mildew (requiring nearby green pixels),
     * tracks foliar chew/perforation contrast edges, and strictly respects [categoryFilter].
     */
    fun analyzeOfflineOpticalPatterns(
        bitmap: Bitmap,
        cropHint: String = "Auto-Detect",
        categoryFilter: ThreatType? = null,
        analysisNotice: String? = null
    ): ScanDiagnosisResult {
        val sampleWidth = 80
        val sampleHeight = 80
        val scaled = Bitmap.createScaledBitmap(bitmap, sampleWidth, sampleHeight, false)
        val pixels = IntArray(sampleWidth * sampleHeight)
        scaled.getPixels(pixels, 0, sampleWidth, 0, 0, sampleWidth, sampleHeight)

        fun isGreenPixel(pixel: Int): Boolean {
            val r = AndroidColor.red(pixel)
            val g = AndroidColor.green(pixel)
            val b = AndroidColor.blue(pixel)
            return g > r + 18 && g > b + 15 && g > 50
        }

        fun hasAdjacentGreen(x: Int, y: Int): Boolean {
            for (dx in -2..2) {
                for (dy in -2..2) {
                    if (dx == 0 && dy == 0) continue
                    val nx = x + dx
                    val ny = y + dy
                    if (nx in 0 until sampleWidth && ny in 0 until sampleHeight) {
                        if (isGreenPixel(pixels[ny * sampleWidth + nx])) return true
                    }
                }
            }
            return false
        }

        var darkNecroticCount = 0
        var yellowChloroticCount = 0
        var greenHealthyCount = 0
        var foliarWhiteMildewCount = 0
        var brightBackdropCount = 0
        var reddishPurpleCount = 0
        var rustOrangeCount = 0
        var redSoilCount = 0
        var darkClayCount = 0
        var insectChewEdgeCount = 0

        for (y in 0 until sampleHeight) {
            for (x in 0 until sampleWidth) {
                val pixel = pixels[y * sampleWidth + x]
                val r = AndroidColor.red(pixel)
                val g = AndroidColor.green(pixel)
                val b = AndroidColor.blue(pixel)
                val brightness = (r + g + b) / 3
                val chromaSpread = maxOf(r, g, b) - minOf(r, g, b)

                // Check contrast boundaries next to green foliage (chewed holes / ragged cuts / leaf mines)
                val greenHere = isGreenPixel(pixel)
                if (x + 1 < sampleWidth) {
                    val rightPixel = pixels[y * sampleWidth + (x + 1)]
                    if (greenHere != isGreenPixel(rightPixel)) {
                        val lum2 = (AndroidColor.red(rightPixel) + AndroidColor.green(rightPixel) + AndroidColor.blue(rightPixel)) / 3
                        if (kotlin.math.abs(brightness - lum2) > 55) {
                            insectChewEdgeCount++
                        }
                    }
                }
                if (y + 1 < sampleHeight) {
                    val downPixel = pixels[(y + 1) * sampleWidth + x]
                    if (greenHere != isGreenPixel(downPixel)) {
                        val lum2 = (AndroidColor.red(downPixel) + AndroidColor.green(downPixel) + AndroidColor.blue(downPixel)) / 3
                        if (kotlin.math.abs(brightness - lum2) > 55) {
                            insectChewEdgeCount++
                        }
                    }
                }

                when {
                    // Red volcanic soil (Humic Nitisol)
                    r in 110..195 && g in 40..95 && b in 20..75 && r > g * 1.3f -> {
                        redSoilCount++
                    }
                    // Dark cracking clay soil (Pellic Vertisol)
                    brightness in 20..85 && chromaSpread < 20 -> {
                        darkClayCount++
                    }
                    // Dark greasy lesions / necrotic tissue
                    brightness < 75 && r > g * 0.8f -> {
                        darkNecroticCount++
                    }
                    // Rust reddish-orange / cinnamon pustules
                    r > 130 && g in 45..130 && b < 80 && r > g * 1.25f -> {
                        rustOrangeCount++
                    }
                    // Matte whitish fungal mycelium vs blown-out white sky/paper/chewed holes
                    r > 180 && g > 180 && b > 180 && chromaSpread < 28 -> {
                        val isPureBackdropWhite = r > 238 && g > 238 && b > 238
                        if (!isPureBackdropWhite && hasAdjacentGreen(x, y)) {
                            foliarWhiteMildewCount++
                        } else {
                            brightBackdropCount++
                        }
                    }
                    // Yellow halo / chlorosis / stippling
                    r > 160 && g > 150 && b < 100 -> {
                        yellowChloroticCount++
                    }
                    // Purplish angular spots (Downy Mildew / Striga)
                    r > 110 && b > 90 && g < 70 -> {
                        reddishPurpleCount++
                    }
                    // Healthy green foliage
                    isGreenPixel(pixel) -> {
                        greenHealthyCount++
                    }
                }
            }
        }

        val totalPixels = (sampleWidth * sampleHeight).toFloat()
        val subjectPixels = (totalPixels - brightBackdropCount).coerceAtLeast(1f)

        val necroticRatio = darkNecroticCount / subjectPixels
        val yellowRatio = yellowChloroticCount / subjectPixels
        val foliarWhiteRatio = foliarWhiteMildewCount / subjectPixels
        val purpleRatio = reddishPurpleCount / subjectPixels
        val rustRatio = rustOrangeCount / subjectPixels
        val redSoilRatio = redSoilCount / totalPixels
        val darkClayRatio = darkClayCount / totalPixels
        val chewEdgeRatio = insectChewEdgeCount / subjectPixels

        val cropLower = cropHint.lowercase(Locale.ROOT)
        val isMaizeOrCereal = cropLower.contains("maize") || cropLower.contains("cereal") || cropLower.contains("corn") ||
            cropLower.contains("sorghum") || cropLower.contains("wheat") || cropLower.contains("barley")
        val isTomatoOrPepper = cropLower.contains("tomato") || cropLower.contains("nyanya") ||
            cropLower.contains("pepper") || cropLower.contains("eggplant") || cropLower.contains("potato") || cropLower.contains("viazi")
        val isBrassica = cropLower.contains("cabbage") || cropLower.contains("kale") ||
            cropLower.contains("sukuma") || cropLower.contains("broccoli") || cropLower.contains("brassica")
        val isCutFlower = cropLower.contains("rose") || cropLower.contains("carnation") ||
            cropLower.contains("flower") || cropLower.contains("chrysanthemum") || cropLower.contains("ornamental")
        val isLegume = cropLower.contains("bean") || cropLower.contains("legume") ||
            cropLower.contains("pea") || cropLower.contains("cowpea")

        val matchedThreat: CropThreat
        val severity: String
        val symptomsDetected = mutableListOf<String>()

        when {
            // 1. Soil Profile Diagnostics
            categoryFilter == ThreatType.SOIL || (categoryFilter == null && (redSoilRatio > 0.35f || darkClayRatio > 0.35f)) -> {
                if (redSoilRatio >= darkClayRatio) {
                    matchedThreat = findCatalogThreat("soil_humic_nitisol", ThreatType.SOIL)
                    severity = "Moderate"
                    symptomsDetected.add("Deep dusky red to dark reddish-brown friable clay loam profile (Nitisol)")
                    symptomsDetected.add("High phosphorus fixation hazard due to iron/aluminum sesquioxides")
                    symptomsDetected.add("Recommended: Dolomitic lime & rock phosphate enrichment")
                } else {
                    matchedThreat = findCatalogThreat("soil_pellic_vertisol", ThreatType.SOIL)
                    severity = "Severe"
                    symptomsDetected.add("Dark grey/black swelling-shrinking montmorillonite clay profile (Vertisol)")
                    symptomsDetected.add("Surface desiccation cracks and drainage restriction hazard")
                    symptomsDetected.add("Recommended: Raised camber beds & gypsum soil conditioning")
                }
            }

            // 2. Invasive & Indicator Weeds
            categoryFilter == ThreatType.WEED || cropLower.contains("weed") || cropLower.contains("striga") || cropLower.contains("nutsedge") -> {
                if (cropLower.contains("striga") || isMaizeOrCereal || purpleRatio > 0.02f) {
                    matchedThreat = findCatalogThreat("weed_striga", ThreatType.WEED)
                    severity = "Extreme"
                    symptomsDetected.add("Pink-purple parasitic flowers beside stunted cereal stems")
                    symptomsDetected.add("Edaphic indicator: Low soil nitrogen (N) and organic carbon")
                } else {
                    matchedThreat = findCatalogThreat("weed_purple_nutsedge", ThreatType.WEED)
                    severity = "High"
                    symptomsDetected.add("Triangular sedge stems with clustered reddish-purple spikelets")
                    symptomsDetected.add("Edaphic indicator: Subsoil compaction and moisture retention")
                }
            }

            // 3. Explicit PEST Filter (NEVER return a DISEASE!)
            categoryFilter == ThreatType.PEST -> {
                when {
                    isMaizeOrCereal -> {
                        if (chewEdgeRatio > 0.04f || necroticRatio > 0.06f || yellowRatio > 0.05f) {
                            matchedThreat = findCatalogThreat("fall_armyworm", ThreatType.PEST)
                            severity = "Extreme"
                            symptomsDetected.add("Ragged whorl chewing with sawdust-like larval frass")
                            symptomsDetected.add("Window-paning and skeletonization across unfolding corn blades")
                        } else {
                            matchedThreat = findCatalogThreat("maize_stem_borer", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Shot-hole perforations across leaves and central deadheart")
                            symptomsDetected.add("Internal stem tunneling by stalk borer larvae")
                        }
                    }
                    isTomatoOrPepper -> {
                        if (chewEdgeRatio > 0.05f || yellowRatio > 0.05f) {
                            matchedThreat = findCatalogThreat("tuta_absoluta", ThreatType.PEST)
                            severity = "Extreme"
                            symptomsDetected.add("Translucent papery blotch mines in tomato leaflets with dark frass")
                            symptomsDetected.add("Pinhead boreholes beneath green fruit calyxes")
                        } else if (foliarWhiteRatio > 0.05f) {
                            matchedThreat = findCatalogThreat("whiteflies", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("White piercing-sucking adults fluttering on leaf undersides")
                            symptomsDetected.add("Honeydew secretion and upward leaf curling")
                        } else {
                            matchedThreat = findCatalogThreat("african_bollworm", ThreatType.PEST)
                            severity = "Severe"
                            symptomsDetected.add("Circular caterpillar entry holes into fruits and flower buds")
                            symptomsDetected.add("Internal locule feeding with frass deposits")
                        }
                    }
                    isBrassica -> {
                        if (yellowRatio > 0.10f && chewEdgeRatio < 0.06f) {
                            matchedThreat = findCatalogThreat("aphids", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Dense waxy grey-green aphid colonies curling young brassica leaves")
                            symptomsDetected.add("Honeydew accumulation attracting secondary sooty mold")
                        } else {
                            matchedThreat = findCatalogThreat("diamondback_moth", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Translucent 'window-paning' perforations in cabbage/kale epidermis")
                            symptomsDetected.add("Pale green caterpillars wriggling when disturbed")
                        }
                    }
                    isCutFlower -> {
                        if (yellowRatio > 0.07f) {
                            matchedThreat = findCatalogThreat("spider_mites", ThreatType.PEST)
                            severity = "Extreme"
                            symptomsDetected.add("Chlorotic stippling speckles and fine webbing across foliage")
                            symptomsDetected.add("Bronzed desiccated leaves with mite colonies underneath")
                        } else if (purpleRatio > 0.03f || necroticRatio > 0.05f || chewEdgeRatio > 0.04f) {
                            matchedThreat = findCatalogThreat("western_flower_thrips", ThreatType.PEST)
                            severity = "Extreme"
                            symptomsDetected.add("Silvery scarred petals with black tar fecal specks")
                            symptomsDetected.add("Deformed, puckered buds from rasping-sucking feeding")
                        } else {
                            matchedThreat = findCatalogThreat("aphids", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Soft-bodied aphid clusters along tender shoots and flower buds")
                        }
                    }
                    isLegume -> {
                        if (necroticRatio > 0.08f || chewEdgeRatio > 0.05f) {
                            matchedThreat = findCatalogThreat("maruca_pod_borer", ThreatType.PEST)
                            severity = "Severe"
                            symptomsDetected.add("Webbed flower clusters and bored holes in developing pods")
                        } else {
                            matchedThreat = findCatalogThreat("african_bollworm", ThreatType.PEST)
                            severity = "Severe"
                            symptomsDetected.add("Larval feeding cavities in legume pods and blossoms")
                        }
                    }
                    else -> {
                        if (chewEdgeRatio > 0.05f) {
                            matchedThreat = findCatalogThreat("fall_armyworm", ThreatType.PEST)
                            severity = "Severe"
                            symptomsDetected.add("Ragged chewed leaf margins and perforated lamina characteristic of caterpillars")
                        } else if (yellowRatio > 0.12f) {
                            matchedThreat = findCatalogThreat("aphids", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Clustering sap-sucking colonies with leaf curl and honeydew")
                        } else if (yellowRatio > 0.06f) {
                            matchedThreat = findCatalogThreat("spider_mites", ThreatType.PEST)
                            severity = "High"
                            symptomsDetected.add("Chlorotic stippling flecks and fine webbing on leaf undersides")
                        } else {
                            matchedThreat = findCatalogThreat("african_bollworm", ThreatType.PEST)
                            severity = "Moderate"
                            symptomsDetected.add("Localized chewing and larval feeding cavities on foliage/fruit")
                        }
                    }
                }
            }

            // 4. Explicit DISEASE Filter (NEVER return a PEST!)
            categoryFilter == ThreatType.DISEASE -> {
                when {
                    isMaizeOrCereal -> {
                        matchedThreat = findCatalogThreat(if (rustRatio > 0.02f) "cereal_rust" else "mlnd", ThreatType.DISEASE)
                        severity = "High"
                        symptomsDetected.add("Pathogenic leaf streaks/pustules aligned along cereal vascular veins")
                    }
                    isCutFlower && foliarWhiteRatio > 0.04f -> {
                        matchedThreat = findCatalogThreat("rose_powdery_mildew", ThreatType.DISEASE)
                        severity = "Severe"
                        symptomsDetected.add("Talcum-white fungal mycelium patches on leaves and rose buds")
                    }
                    isCutFlower && purpleRatio > 0.03f -> {
                        matchedThreat = findCatalogThreat("rose_downy_mildew", ThreatType.DISEASE)
                        severity = "Extreme"
                        symptomsDetected.add("Angular purplish-red lesions bounded by leaf veins")
                    }
                    isCutFlower && rustRatio > 0.02f -> {
                        matchedThreat = findCatalogThreat("flower_rust", ThreatType.DISEASE)
                        severity = "Severe"
                        symptomsDetected.add("Cinnamon-brown rust pustules erupting on foliage and stems")
                    }
                    isLegume && rustRatio > 0.02f -> {
                        matchedThreat = findCatalogThreat("bean_rust", ThreatType.DISEASE)
                        severity = "Severe"
                        symptomsDetected.add("Reddish-brown powdery uredinia pustules encircled by yellow halos")
                    }
                    isLegume -> {
                        matchedThreat = findCatalogThreat("bean_anthracnose", ThreatType.DISEASE)
                        severity = "Severe"
                        symptomsDetected.add("Dark sunken cankers with salmon spore masses along pods and veins")
                    }
                    else -> {
                        if (necroticRatio > 0.14f || foliarWhiteRatio > 0.06f) {
                            matchedThreat = findCatalogThreat("late_blight", ThreatType.DISEASE)
                            severity = "Severe"
                            symptomsDetected.add("Water-soaked spreading necrotic blight with pale sporulation ring")
                        } else if (rustRatio > 0.02f) {
                            matchedThreat = findCatalogThreat("bean_rust", ThreatType.DISEASE)
                            severity = "High"
                            symptomsDetected.add("Powdery rust pustules on foliar epidermis")
                        } else {
                            matchedThreat = findCatalogThreat("early_blight", ThreatType.DISEASE)
                            severity = "Moderate"
                            symptomsDetected.add("Concentric target-ring brown lesions on older lower leaves")
                        }
                    }
                }
            }

            // 5. AUTO-DETECT MODE: Balanced Pest vs Disease Discrimination
            // Cereals / Maize
            isMaizeOrCereal && rustRatio > 0.035f -> {
                matchedThreat = findCatalogThreat("cereal_rust", ThreatType.DISEASE)
                severity = "Extreme"
                symptomsDetected.add("Golden-brown to reddish-orange powdery rust pustules on cereal leaves")
            }
            isMaizeOrCereal -> {
                matchedThreat = findCatalogThreat("fall_armyworm", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Window-paning foliage with ragged chewed whorl & sawdust frass")
            }

            // Brassicas
            isBrassica && yellowRatio > 0.12f && chewEdgeRatio < 0.06f -> {
                matchedThreat = findCatalogThreat("aphids", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Waxy aphid colonies clustering on curled brassica foliage")
            }
            isBrassica -> {
                matchedThreat = findCatalogThreat("diamondback_moth", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Translucent window-paning holes and skeletonized leaves")
            }

            // Cut Flowers
            isCutFlower && foliarWhiteRatio > 0.06f -> {
                matchedThreat = findCatalogThreat("rose_powdery_mildew", ThreatType.DISEASE)
                severity = "Severe"
                symptomsDetected.add("White powdery conidial patches on live green rose leaves")
            }
            isCutFlower && purpleRatio > 0.04f -> {
                matchedThreat = findCatalogThreat("rose_downy_mildew", ThreatType.DISEASE)
                severity = "Extreme"
                symptomsDetected.add("Angular purplish-red vein-delimited lesions")
            }
            isCutFlower && rustRatio > 0.025f -> {
                matchedThreat = findCatalogThreat("flower_rust", ThreatType.DISEASE)
                severity = "Extreme"
                symptomsDetected.add("Chocolate-brown rust pustules on stems and foliage")
            }
            isCutFlower && yellowRatio > 0.07f -> {
                matchedThreat = findCatalogThreat("spider_mites", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Chlorotic stippling flecks and fine webbing on leaf undersides")
            }
            isCutFlower -> {
                matchedThreat = findCatalogThreat("western_flower_thrips", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Silvery bleached scarring on petals and buds with black fecal specks")
            }

            // Tomato / Solanaceae: prioritize chewed/mined edge contrast as PEST before blight
            isTomatoOrPepper && chewEdgeRatio > 0.07f -> {
                matchedThreat = findCatalogThreat("tuta_absoluta", ThreatType.PEST)
                severity = "Extreme"
                symptomsDetected.add("High-contrast translucent blotch mines and ragged holes typical of Tomato Leafminer")
                symptomsDetected.add("Confirm lack of water-soaked fungal stem collars to rule out Late Blight")
            }
            isTomatoOrPepper && ((foliarWhiteRatio > 0.06f && necroticRatio > 0.08f) || (necroticRatio > 0.18f && yellowRatio < 0.05f)) -> {
                matchedThreat = findCatalogThreat("late_blight", ThreatType.DISEASE)
                severity = "Severe"
                symptomsDetected.add("Water-soaked spreading necrotic blight with white sporulation margin on green tissue")
            }
            isTomatoOrPepper && yellowRatio in 0.06f..0.14f -> {
                matchedThreat = findCatalogThreat("tuta_absoluta", ThreatType.PEST)
                severity = "Extreme"
                symptomsDetected.add("Translucent papery blotch mines between leaf epidermises")
            }
            isTomatoOrPepper -> {
                matchedThreat = findCatalogThreat("african_bollworm", ThreatType.PEST)
                severity = "Severe"
                symptomsDetected.add("Localized chewing and larval boreholes on tomato fruits/buds")
            }

            // Legumes
            isLegume && rustRatio > 0.025f -> {
                matchedThreat = findCatalogThreat("bean_rust", ThreatType.DISEASE)
                severity = "Severe"
                symptomsDetected.add("Reddish-brown powdery rust pustules encircled by yellow halos")
            }
            isLegume && (necroticRatio > 0.08f || chewEdgeRatio > 0.06f) -> {
                matchedThreat = findCatalogThreat("maruca_pod_borer", ThreatType.PEST)
                severity = "Severe"
                symptomsDetected.add("Webbed flowers and bored bean pods with frass")
            }
            isLegume -> {
                matchedThreat = findCatalogThreat("spider_mites", ThreatType.PEST)
                severity = "Moderate"
                symptomsDetected.add("Stippling speckles on bean leaves and underside webbing")
            }

            // General Auto-Detect Fallbacks (Pest patterns checked cleanly vs genuine foliar fungal signals)
            rustRatio > 0.025f -> {
                matchedThreat = findCatalogThreat("cereal_rust", ThreatType.DISEASE)
                severity = "High"
                symptomsDetected.add("Powdery reddish-orange pustules rupturing leaf epidermis")
            }
            chewEdgeRatio > 0.05f -> {
                matchedThreat = findCatalogThreat("fall_armyworm", ThreatType.PEST)
                severity = "Severe"
                symptomsDetected.add("Sharp chewed perforation borders across green foliage indicative of insect feeding")
            }
            foliarWhiteRatio > 0.07f -> {
                matchedThreat = findCatalogThreat("rose_powdery_mildew", ThreatType.DISEASE)
                severity = "Moderate"
                symptomsDetected.add("White powdery fungal coating adhered directly to green foliar tissue")
            }
            yellowRatio > 0.14f -> {
                matchedThreat = findCatalogThreat("aphids", ThreatType.PEST)
                severity = "Moderate"
                symptomsDetected.add("Leaf curling with chlorotic stress and piercing-sucking colony patterns")
            }
            yellowRatio in 0.06f..0.14f -> {
                matchedThreat = findCatalogThreat("spider_mites", ThreatType.PEST)
                severity = "High"
                symptomsDetected.add("Chlorotic stippling flecks and bronzing on upper leaf lamina")
            }
            necroticRatio > 0.16f -> {
                matchedThreat = findCatalogThreat("late_blight", ThreatType.DISEASE)
                severity = "Severe"
                symptomsDetected.add("Extensive water-soaked dark necrotic blight along leaf margins")
            }
            else -> {
                matchedThreat = findCatalogThreat("african_bollworm", ThreatType.PEST)
                severity = "Moderate"
                symptomsDetected.add("Localized foliar chewing / feeding perforation patterns")
            }
        }

        val confidence = 68
        val selectedChem = matchedThreat.ipmPlan.chemicalControls.firstOrNull()

        val pcpbChem = matchedThreat.pcpbChemicals.firstOrNull()?.let {
            "${it.tradeName} [${it.pcpbRegNo}]: ${it.activeIngredient}. Knapsack: ${it.knapsackDosage}"
        } ?: ""

        val cabiBiocontrol = matchedThreat.cabiBioProtections.firstOrNull()?.let {
            "${it.tradeName} (${it.activeAgent}): ${it.modeOfAction}"
        } ?: ""

        val infonetAction = matchedThreat.infonetPractices.firstOrNull()?.let {
            "${it.title} - ${it.description}"
        } ?: ""

        val analysisText = "Offline Optical Pre-Screening (${matchedThreat.type.label}): " +
            "Green Foliage ${(greenHealthyCount * 100f / subjectPixels).toInt()}%, " +
            "Chlorosis ${(yellowRatio * 100f).toInt()}%, " +
            "Necrosis ${(necroticRatio * 100f).toInt()}%, " +
            "Chew/Edge Contrast ${(chewEdgeRatio * 100f).toInt()}%. " +
            "Candidate: ${matchedThreat.name} (${matchedThreat.scientificName}). " +
            "Confirm physical field signs (${symptomsDetected.joinToString("; ")}) or select the Pest/Disease domain filter above."

        return ScanDiagnosisResult(
            threat = matchedThreat,
            threatId = matchedThreat.id,
            diseaseName = matchedThreat.name,
            scientificName = matchedThreat.scientificName,
            crop = if (cropHint != "Auto-Detect") cropHint else matchedThreat.targetCrops.firstOrNull() ?: "General Field Crop",
            confidencePercent = confidence,
            severity = severity,
            visualSymptomsDetected = symptomsDetected,
            recommendedCulturalAction = matchedThreat.ipmPlan.culturalControls.firstOrNull()
                ?: "Inspect plot perimeter and remove severely affected tissue.",
            recommendedChemicals = matchedThreat.pcpbChemicals.map { "${it.tradeName} (${it.activeIngredient}) - ${it.knapsackDosage}" },
            recommendedChemicalObject = selectedChem,
            isOnlineAiAnalysis = false,
            detailedAnalysisText = analysisText,
            cabiBiocontrolSolution = cabiBiocontrol,
            infonetOrganicAction = infonetAction,
            pcpbRegisteredSolution = pcpbChem,
            diagnosticCategory = matchedThreat.type,
            identifiedName = matchedThreat.name,
            targetCropsList = matchedThreat.targetCrops,
            vulnerableCropStages = matchedThreat.vulnerableCropStages,
            yieldLossPotential = matchedThreat.yieldLossPotential,
            economicThresholdInfo = matchedThreat.economicThresholdInfo,
            mechanicalControlAction = matchedThreat.mechanicalControls.firstOrNull()
                ?: (matchedThreat.ipmPlan.physicalMechanicalControls.firstOrNull() ?: "Hand rogue or cultivate"),
            analysisNotice = analysisNotice
        )
    }

    private fun defaultDomainLabel(category: ThreatType): String = when (category) {
        ThreatType.PEST -> "Unclassified Insect / Mite Pest"
        ThreatType.DISEASE -> "Unclassified Crop Disease"
        ThreatType.WEED -> "Unclassified Invasive Weed"
        ThreatType.SOIL -> "Field Soil Profile"
    }

    private fun defaultCabiHint(category: ThreatType): String = when (category) {
        ThreatType.PEST -> "Evaluate Beauveria bassiana, Bacillus thuringiensis (Bt), Azadirachtin, or predatory phytoseiid mites."
        ThreatType.DISEASE -> "Apply Trichoderma asperellum or Bacillus amyloliquefaciens biopesticides preventatively."
        ThreatType.WEED -> "Promote legume cover crops (Desmodium push-pull) and competitive crop canopy."
        ThreatType.SOIL -> "Inoculate with Rhizobium/Mycorrhizal biofertilizers and aged compost."
    }

    private fun defaultCulturalHint(category: ThreatType): String = when (category) {
        ThreatType.PEST -> "Handpick larvae/egg masses, install yellow/blue sticky traps or pheromone lures, and maintain weed-free borders."
        ThreatType.DISEASE -> "Rogue infected plants cleanly, switch from overhead sprinklers to drip irrigation, and rotate non-host crops."
        ThreatType.WEED -> "Rogue before flowering/seed set and apply organic mulch or shallow cultivation."
        ThreatType.SOIL -> "Test soil pH, incorporate well-decomposed compost, and avoid wet tillage."
    }
}
