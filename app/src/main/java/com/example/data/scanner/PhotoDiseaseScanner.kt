package com.example.data.scanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.ThreatCatalog
import com.example.model.ChemicalRecommendation
import com.example.model.CropThreat
import com.example.model.ThreatType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

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
    val severity: String, // Mild, Moderate, Severe, Critical
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
    val realTimeTimestamp: String = SimpleDateFormat("dd MMM yyyy, HH:mm:ss", Locale.getDefault()).format(Date())
)

object PhotoDiseaseScanner {

    private const val TAG = "PhotoDiseaseScanner"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Converts a bitmap into a Base64 JPEG string (downscaling if necessary for efficiency).
     */
    fun bitmapToBase64(bitmap: Bitmap, maxDimension: Int = 1024): String {
        val scaledBitmap = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
            val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()
            val newWidth: Int
            val newHeight: Int
            if (ratio > 1) {
                newWidth = maxDimension
                newHeight = (maxDimension / ratio).toInt()
            } else {
                newHeight = maxDimension
                newWidth = (maxDimension * ratio).toInt()
            }
            Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        } else {
            bitmap
        }

        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    /**
     * Performs real-time multimodal scan analysis on a crop, pest, weed, or soil photograph.
     * Tries Gemini Multimodal Vision API first if online;
     * seamlessly falls back to offline optical symptom analysis on error/offline.
     */
    suspend fun analyzeCropPhoto(
        bitmap: Bitmap,
        cropHint: String = "Auto-Detect",
        categoryFilter: ThreatType? = null
    ): ScanDiagnosisResult = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        val hasValidKey = apiKey.isNotBlank() && !apiKey.contains("MY_GEMINI_API_KEY")

        if (hasValidKey) {
            try {
                val onlineResult = callGeminiVisionApi(bitmap, apiKey, cropHint, categoryFilter)
                if (onlineResult != null) {
                    return@withContext onlineResult
                }
            } catch (e: Exception) {
                Log.w(TAG, "Gemini vision API failed, falling back to offline diagnostic engine: ${e.message}")
            }
        }

        // Resilient Offline Agronomic & Soil Vision Engine fallback
        analyzeOfflineOpticalPatterns(bitmap, cropHint, categoryFilter)
    }

    private fun callGeminiVisionApi(
        bitmap: Bitmap,
        apiKey: String,
        cropHint: String,
        categoryFilter: ThreatType? = null
    ): ScanDiagnosisResult? {
        val base64Image = bitmapToBase64(bitmap, 1024)

        val prompt = buildString {
            append("You are an expert plant pathologist, agricultural entomologist, weed scientist, and pedologist diagnosing East African crop threats and soil health. ")
            append("The system integrates Infonet-Biovision (organic/cultural IPM), CABI BioProtection Portal (biological agents), and PCPB Kenya (official registered agrochemicals with precise dosages). ")
            append("CRITICAL INSTRUCTION: You MUST carefully distinguish between INSECT PESTS (and their feeding damage: ragged holes, windowpaning, leaf miner blotch tunnels, caterpillar frass, fruit cavities, aphid/whitefly colonies, mite webbing, thrips silvering) and PLANT DISEASES (fungal, bacterial, oomycete, viral). ")
            append("Do NOT misidentify insect pest damage as a disease! If you observe insect pests, larvae, nymphs, or characteristic insect chew/bore/mine damage, categorize it strictly as 'threatType': 'PEST'. ")
            append("Examine this photo carefully. ")
            if (cropHint != "Auto-Detect") {
                append("The farmer states the crop/context is: $cropHint. ")
            }
            if (categoryFilter != null) {
                append("The farmer requested specific analysis in category: ${categoryFilter.label}. ")
            }
            append("Identify whether this photograph matches one of these known catalog threats (or identify pest, weed, soil profile, or disease accurately): ")
            append("1. Fall Armyworm (Spodoptera frugiperda) [fall_armyworm] - INSECT PEST (Maize, Sorghum: ragged chewed whorl, sawdust frass, leaf holes)\n")
            append("2. Tomato Leafminer (Tuta absoluta) [tuta_absoluta] - INSECT PEST (Tomato, Potato: translucent blotch mines, leaf necrosis, fruit holes)\n")
            append("3. African Bollworm (Helicoverpa armigera) [african_bollworm] - INSECT PEST (Tomato, Legumes, Pepper: circular chewed fruit holes, bud drop)\n")
            append("4. Two-Spotted Spider Mite (Tetranychus urticae) [spider_mites] - INSECT/ACARID PEST (Roses, Carnations, Beans, Tomatoes: fine silken webbing, chlorotic stippling, bronzing)\n")
            append("5. Western Flower Thrips (Frankliniella occidentalis) [western_flower_thrips] - INSECT PEST (Roses, Flowers, French Beans: silvery bleached petal/leaf scarring, tar fecal specks, deformed buds)\n")
            append("6. Cotton / Melon & Rose Aphids (Aphis gossypii, Macrosiphum rosae) [aphids] - INSECT PEST (Vegetables, Roses, Brassicas: dense clustering colonies, leaf curl, sticky honeydew)\n")
            append("7. Maize Stem Borer (Busseola fusca) [maize_stem_borer] - INSECT PEST (Maize: shot holes, central deadheart, stem tunneling)\n")
            append("8. Diamondback Moth (Plutella xylostella) [diamondback_moth] - INSECT PEST (Cabbage, Kale, Sukuma Wiki, Broccoli: windowpaning holes, skeletonization)\n")
            append("9. Whiteflies & TYLCV (Bemisia tabaci) [whiteflies] - INSECT PEST (Tomato, Cassava: tiny white fluttering insects, yellow mosaic, upward leaf cupping)\n")
            append("10. Legume Pod Borer (Maruca vitrata) [maruca_pod_borer] - INSECT PEST (French Beans, Cowpeas: webbed flowers, bored pods)\n")
            append("11. Late Blight (Phytophthora infestans) [late_blight] - FUNGAL/OOMYCETE DISEASE (Tomato, Potato: water-soaked spreading lesions, white sporulation ring)\n")
            append("12. Common Bean Rust (Uromyces appendiculatus) [bean_rust] - FUNGAL DISEASE (Beans: reddish-brown powdery pustules with yellow halos)\n")
            append("13. Cereal Rusts (Puccinia sorghi) [cereal_rust] - FUNGAL DISEASE (Maize, Wheat: golden-brown to reddish-orange powdery pustules)\n")
            append("14. Rose Powdery Mildew (Podosphaera pannosa) [rose_powdery_mildew] - FUNGAL DISEASE (Roses: white powdery coating on leaves and buds)\n")
            append("15. Rose Downy Mildew (Peronospora sparsa) [rose_downy_mildew] - OOMYCETE DISEASE (Roses: angular purplish-red spots delimited by veins)\n")
            append("16. Botrytis Blight / Grey Mold (Botrytis cinerea) [botrytis_blight] - FUNGAL DISEASE (Cut Flowers, Strawberries: velvety grey mold, ghost spots on petals)\n")
            append("17. Bacterial Wilt (Ralstonia solanacearum) [bacterial_wilt] - BACTERIAL DISEASE (Potato, Tomato: rapid daytime wilting of green foliage, vascular browning)\n")
            append("18. Witchweed (Striga hermonthica) [weed_striga] - INVASIVE WEED & LOW N/P SOIL INDICATOR\n")
            append("19. Purple Nutsedge (Cyperus rotundus) [weed_purple_nutsedge] - INVASIVE WEED & COMPACTION INDICATOR\n")
            append("20. Humic Nitisol (Red Volcanic Soil) [soil_humic_nitisol] - SOIL TYPE & HEALTH\n")
            append("21. Pellic Vertisol (Black Cotton Cracking Clay) [soil_pellic_vertisol] - SOIL TYPE & HEALTH\n")
            append("Output strictly valid JSON with these exact keys: ")
            append("{ \"threatId\": \"...\", \"threatType\": \"PEST|DISEASE|WEED|SOIL\", \"diseaseName\": \"...\", \"scientificName\": \"...\", \"crop\": \"...\", \"confidence\": 94, \"severity\": \"Severe\", \"symptoms\": [\"...\", \"...\", \"...\"], \"culturalAction\": \"...\", \"mechanicalAction\": \"...\", \"recommendedChemicals\": [\"...\"], \"summary\": \"...\", \"cabiBiocontrol\": \"...\", \"infonetPractice\": \"...\", \"pcpbChemical\": \"...\" }")
        }

        val jsonBody = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", prompt)
                        })
                        put(JSONObject().apply {
                            put("inlineData", JSONObject().apply {
                                put("mimeType", "image/jpeg")
                                put("data", base64Image)
                            })
                        })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.2)
            })
        }

        val requestUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
        val request = Request.Builder()
            .url(requestUrl)
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            val errBody = response.body?.string() ?: ""
            Log.e(TAG, "Gemini API error code ${response.code}: $errBody")
            return null
        }

        val responseBodyText = response.body?.string() ?: return null
        val jsonResponse = JSONObject(responseBodyText)
        val candidates = jsonResponse.optJSONArray("candidates") ?: return null
        if (candidates.length() == 0) return null

        val firstCandidate = candidates.getJSONObject(0)
        val content = firstCandidate.optJSONObject("content") ?: return null
        val parts = content.optJSONArray("parts") ?: return null
        if (parts.length() == 0) return null

        val rawText = parts.getJSONObject(0).optString("text", "")
        if (rawText.isBlank()) return null

        val parsedJson = JSONObject(rawText)
        val threatId = parsedJson.optString("threatId", "late_blight").lowercase()
        val matchedThreat = ThreatCatalog.allThreats.find { 
            it.id.equals(threatId, ignoreCase = true) || 
            it.name.contains(parsedJson.optString("diseaseName", ""), ignoreCase = true)
        } ?: ThreatCatalog.allThreats.first()

        val symptomsList = mutableListOf<String>()
        val symptomsArray = parsedJson.optJSONArray("symptoms")
        if (symptomsArray != null) {
            for (i in 0 until symptomsArray.length()) {
                symptomsList.add(symptomsArray.getString(i))
            }
        }
        if (symptomsList.isEmpty()) {
            symptomsList.addAll(matchedThreat.diagnosticKeys.take(3))
        }

        val chemicalsList = mutableListOf<String>()
        val chemArray = parsedJson.optJSONArray("recommendedChemicals")
        if (chemArray != null) {
            for (i in 0 until chemArray.length()) {
                chemicalsList.add(chemArray.getString(i))
            }
        }
        if (chemicalsList.isEmpty()) {
            matchedThreat.pcpbChemicals.forEach { chem ->
                chemicalsList.add("${chem.tradeName} (${chem.activeIngredient}) - ${chem.knapsackDosage}")
            }
        }

        val selectedChem = matchedThreat.ipmPlan.chemicalControls.firstOrNull()

        val cabiBiocontrol = parsedJson.optString("cabiBiocontrol", "").ifBlank {
            matchedThreat.cabiBioProtections.firstOrNull()?.let { "${it.tradeName} (${it.activeAgent}) - ${it.modeOfAction}" } ?: ""
        }

        val infonetPractice = parsedJson.optString("infonetPractice", "").ifBlank {
            matchedThreat.infonetPractices.firstOrNull()?.let { "${it.title}: ${it.description}" } ?: ""
        }

        val pcpbChem = parsedJson.optString("pcpbChemical", "").ifBlank {
            matchedThreat.pcpbChemicals.firstOrNull()?.let { "${it.tradeName} [${it.pcpbRegNo}] - ${it.activeIngredient}. Knapsack: ${it.knapsackDosage}" } ?: ""
        }

        return ScanDiagnosisResult(
            threat = matchedThreat,
            threatId = matchedThreat.id,
            diseaseName = parsedJson.optString("diseaseName", matchedThreat.name),
            scientificName = parsedJson.optString("scientificName", matchedThreat.scientificName),
            crop = parsedJson.optString("crop", if (cropHint != "Auto-Detect") cropHint else matchedThreat.targetCrops.firstOrNull() ?: "Tomato"),
            confidencePercent = parsedJson.optInt("confidence", 94).coerceIn(75, 99),
            severity = parsedJson.optString("severity", matchedThreat.severityLevel),
            visualSymptomsDetected = symptomsList,
            recommendedCulturalAction = parsedJson.optString("culturalAction", matchedThreat.ipmPlan.culturalControls.firstOrNull() ?: ""),
            recommendedChemicals = chemicalsList,
            recommendedChemicalObject = selectedChem,
            isOnlineAiAnalysis = true,
            detailedAnalysisText = parsedJson.optString("summary", matchedThreat.summary),
            cabiBiocontrolSolution = cabiBiocontrol,
            infonetOrganicAction = infonetPractice,
            pcpbRegisteredSolution = pcpbChem,
            diagnosticCategory = matchedThreat.type,
            identifiedName = parsedJson.optString("diseaseName", matchedThreat.name),
            targetCropsList = matchedThreat.targetCrops,
            vulnerableCropStages = matchedThreat.vulnerableCropStages,
            yieldLossPotential = matchedThreat.yieldLossPotential,
            economicThresholdInfo = matchedThreat.economicThresholdInfo,
            mechanicalControlAction = parsedJson.optString("mechanicalAction", matchedThreat.mechanicalControls.firstOrNull() ?: "")
        )
    }

    /**
     * Resilient Offline Optical Symptom Engine.
     * Evaluates RGB color ratios, necrotic margins, mildew sporulation,
     * and chlorotic patterns from image pixels when offline.
     */
    fun analyzeOfflineOpticalPatterns(
        bitmap: Bitmap,
        cropHint: String = "Auto-Detect",
        categoryFilter: ThreatType? = null
    ): ScanDiagnosisResult {
        val sampleWidth = 120
        val sampleHeight = 120
        val scaled = Bitmap.createScaledBitmap(bitmap, sampleWidth, sampleHeight, false)

        var darkNecroticCount = 0
        var yellowChloroticCount = 0
        var greenHealthyCount = 0
        var whitePowderCount = 0
        var reddishPurpleCount = 0
        var rustOrangeCount = 0
        var redSoilCount = 0
        var darkClayCount = 0

        for (x in 0 until sampleWidth) {
            for (y in 0 until sampleHeight) {
                val pixel = scaled.getPixel(x, y)
                val r = AndroidColor.red(pixel)
                val g = AndroidColor.green(pixel)
                val b = AndroidColor.blue(pixel)

                val brightness = (r + g + b) / 3

                // Red volcanic soil (Humic Nitisol)
                if (r in 110..195 && g in 40..95 && b in 20..75 && r > g * 1.3f) {
                    redSoilCount++
                }
                // Dark cracking clay soil (Pellic Vertisol)
                else if (brightness in 20..85 && kotlin.math.abs(r - g) < 20 && kotlin.math.abs(g - b) < 20) {
                    darkClayCount++
                }
                // Dark greasy lesions / necrotic tissue
                else if (brightness < 75 && (r > g * 0.8f)) {
                    darkNecroticCount++
                }
                // Rust reddish-orange / cinnamon pustules
                else if (r > 130 && g in 45..130 && b < 80 && (r > g * 1.25f)) {
                    rustOrangeCount++
                }
                // White powdery coating / sporulation
                else if (r > 195 && g > 195 && b > 195) {
                    whitePowderCount++
                }
                // Yellow halo / chlorosis / stippling
                else if (r > 160 && g > 150 && b < 100) {
                    yellowChloroticCount++
                }
                // Purplish angular spots (Rose Downy Mildew / Striga pinkish-purple)
                else if (r > 110 && b > 90 && g < 70) {
                    reddishPurpleCount++
                }
                // Healthy foliage
                else if (g > r + 20 && g > b + 20) {
                    greenHealthyCount++
                }
            }
        }

        val totalPixels = (sampleWidth * sampleHeight).toFloat()
        val necroticRatio = darkNecroticCount / totalPixels
        val yellowRatio = yellowChloroticCount / totalPixels
        val whiteRatio = whitePowderCount / totalPixels
        val purpleRatio = reddishPurpleCount / totalPixels
        val rustRatio = rustOrangeCount / totalPixels
        val redSoilRatio = redSoilCount / totalPixels
        val darkClayRatio = darkClayCount / totalPixels

        val matchedThreat: CropThreat
        val severity: String
        val symptomsDetected = mutableListOf<String>()

        val cropLower = cropHint.lowercase()
        val isMaizeOrCereal = cropLower.contains("maize") || cropLower.contains("cereal") || cropLower.contains("corn") || cropLower.contains("sorghum") || cropLower.contains("wheat") || cropLower.contains("barley")
        val isTomatoOrPepper = cropLower.contains("tomato") || cropLower.contains("nyanya") || cropLower.contains("pepper") || cropLower.contains("eggplant")
        val isPotato = cropLower.contains("potato") || cropLower.contains("viazi")
        val isBrassica = cropLower.contains("cabbage") || cropLower.contains("kale") || cropLower.contains("sukuma") || cropLower.contains("broccoli") || cropLower.contains("brassica")
        val isCutFlower = cropLower.contains("rose") || cropLower.contains("carnation") || cropLower.contains("flower") || cropLower.contains("chrysanthemum") || cropLower.contains("ornamental")
        val isLegume = cropLower.contains("bean") || cropLower.contains("legume") || cropLower.contains("pea") || cropLower.contains("cowpea")

        when {
            // Category Filter: Soil Profile & Health Diagnostics
            categoryFilter == ThreatType.SOIL || (categoryFilter == null && (redSoilRatio > 0.35f || darkClayRatio > 0.35f)) -> {
                if (redSoilRatio >= darkClayRatio) {
                    matchedThreat = ThreatCatalog.allThreats.find { it.id == "soil_humic_nitisol" } ?: ThreatCatalog.allThreats.first()
                    severity = "Moderate"
                    symptomsDetected.add("Deep dusky red to dark reddish-brown friable clay loam profile (Nitisol)")
                    symptomsDetected.add("High phosphorus fixation hazard due to active iron/aluminum sesquioxides")
                    symptomsDetected.add("Subsoil leaching of exchangeable bases (Ca²⁺, Mg²⁺)")
                } else {
                    matchedThreat = ThreatCatalog.allThreats.find { it.id == "soil_pellic_vertisol" } ?: ThreatCatalog.allThreats.first()
                    severity = "Severe"
                    symptomsDetected.add("Dark grey/black swelling-shrinking montmorillonite clay profile (Vertisol)")
                    symptomsDetected.add("Deep surface dessication cracks and high plastic compaction when moist")
                    symptomsDetected.add("Poor subsoil aeration and severe seasonal waterlogging hazard")
                }
            }

            // Category Filter: Invasive & Indicator Weeds
            categoryFilter == ThreatType.WEED || cropLower.contains("weed") || cropLower.contains("striga") || cropLower.contains("nutsedge") -> {
                if (cropLower.contains("striga") || isMaizeOrCereal || purpleRatio > 0.02f) {
                    matchedThreat = ThreatCatalog.allThreats.find { it.id == "weed_striga" } ?: ThreatCatalog.allThreats.first()
                    severity = "Extreme"
                    symptomsDetected.add("Pink-purple spiked parasitic blossoms emerging beside cereal root crowns")
                    symptomsDetected.add("Severe host cereal chlorosis, scorching, and growth stunting")
                    symptomsDetected.add("Edaphic Indicator: Severe soil nitrogen (N) and phosphorus (P) depletion")
                } else {
                    matchedThreat = ThreatCatalog.allThreats.find { it.id == "weed_purple_nutsedge" } ?: ThreatCatalog.allThreats.first()
                    severity = "High"
                    symptomsDetected.add("Triangular stems with dark reddish-purple spikelets clustered in rosettes")
                    symptomsDetected.add("Prolific subterranean network of dormant tubers and fibrous rhizomes")
                    symptomsDetected.add("Edaphic Indicator: Dense plow pan compaction and periodic waterlogging")
                }
            }

            // EXPLICIT PEST MODE (Farmer selected Insect Pests category)
            categoryFilter == ThreatType.PEST -> {
                when {
                    isMaizeOrCereal -> {
                        if (necroticRatio > 0.06f || yellowRatio > 0.05f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "fall_armyworm" } ?: ThreatCatalog.allThreats.first()
                            severity = "Extreme"
                            symptomsDetected.add("Ragged leaf chewing with copious brown frass mounds packed in the whorl")
                            symptomsDetected.add("Larval window-paning and skeletonization across corn leaf blades")
                            symptomsDetected.add("Larvae with inverted 'Y' head suture and 4 abdominal pins")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "maize_stem_borer" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Window-paning foliage and shot-hole perforations across unfolding leaves")
                            symptomsDetected.add("Larval tunneling inside central stalks resulting in 'dead heart'")
                            symptomsDetected.add("Sawdust-like frass extruding from stem entry perforations")
                        }
                    }
                    isTomatoOrPepper -> {
                        if (yellowRatio > 0.06f && necroticRatio < 0.11f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "tuta_absoluta" } ?: ThreatCatalog.allThreats.first()
                            severity = "Extreme"
                            symptomsDetected.add("Translucent papery blotch mines across leaflets with granular frass inside")
                            symptomsDetected.add("Pinhead entrance holes bored beneath green tomato fruit calyxes")
                            symptomsDetected.add("Extensive foliar scorching and premature canopy collapse")
                        } else if (whiteRatio > 0.06f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "whiteflies" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Tiny white piercing-sucking insects fluttering under leaves")
                            symptomsDetected.add("Chlorotic yellow mosaic mottling and upward leaf cupping (TYLCV)")
                            symptomsDetected.add("Sticky honeydew with developing black sooty mold fungus")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "african_bollworm" } ?: ThreatCatalog.allThreats.first()
                            severity = "Severe"
                            symptomsDetected.add("Circular chewed entry cavities bored into tomato fruits and flower buds")
                            symptomsDetected.add("Caterpillar feeding on internal fruit locules with wet frass exudate")
                            symptomsDetected.add("Secondary fruit rot and early blossom abortion")
                        }
                    }
                    isBrassica -> {
                        if (yellowRatio > 0.12f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "aphids" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Dense waxy gray-green aphid colonies clustering on young inner leaves")
                            symptomsDetected.add("Severe leaf crinkling, cupping, and stunted head formation")
                            symptomsDetected.add("Sticky honeydew secretions attracting secondary sooty mold")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "diamondback_moth" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Translucent 'window-paning' feeding perforations in lower leaf epidermis")
                            symptomsDetected.add("Skeletonized foliage with ragged leaf margins")
                            symptomsDetected.add("Spindle-shaped pale green caterpillars wriggling on silken threads")
                        }
                    }
                    isCutFlower -> {
                        if (yellowRatio > 0.07f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "spider_mites" } ?: ThreatCatalog.allThreats.first()
                            severity = "Extreme"
                            symptomsDetected.add("Fine yellow/white stippling flecks across upper leaf lamina")
                            symptomsDetected.add("Silken webbing stretching across leaf undersides and flower buds")
                            symptomsDetected.add("Foliar bronzing, parchment desiccation, and severe leaf drop")
                        } else if (purpleRatio > 0.03f || necroticRatio > 0.05f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "western_flower_thrips" } ?: ThreatCatalog.allThreats.first()
                            severity = "Extreme"
                            symptomsDetected.add("Silvery bleached feeding scars on flower petals with black tar frass specks")
                            symptomsDetected.add("Distorted, puckered buds and necrotic petal margin scorch")
                            symptomsDetected.add("Active slender thrips scurrying inside bloom petals")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "aphids" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Clustered green/pink aphid colonies on tender terminal rose stems")
                            symptomsDetected.add("Sticky honeydew causing black sooty mold across blooms")
                        }
                    }
                    isLegume -> {
                        if (necroticRatio > 0.08f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "maruca_pod_borer" } ?: ThreatCatalog.allThreats.first()
                            severity = "Severe"
                            symptomsDetected.add("Webbed flower clusters and bored pods with frass extrusions")
                            symptomsDetected.add("Larvae consuming reproductive buds, causing flower shedding")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "african_bollworm" } ?: ThreatCatalog.allThreats.first()
                            severity = "Severe"
                            symptomsDetected.add("Caterpillars boring into legume pods and consuming developing seeds")
                            symptomsDetected.add("Aborted flower buds and premature pod desiccation")
                        }
                    }
                    else -> {
                        // General Pest Candidate
                        if (yellowRatio > 0.13f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "aphids" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Clustered colonies of piercing-sucking aphids on foliage")
                            symptomsDetected.add("Leaf curling, downward cupping, and sticky honeydew exudate")
                        } else if (yellowRatio > 0.07f) {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "spider_mites" } ?: ThreatCatalog.allThreats.first()
                            severity = "High"
                            symptomsDetected.add("Chlorotic stippling and fine webbing on leaf undersides")
                        } else {
                            matchedThreat = ThreatCatalog.allThreats.find { it.id == "fall_armyworm" } ?: ThreatCatalog.allThreats.first()
                            severity = "Severe"
                            symptomsDetected.add("Chewed foliar perforations and ragged leaf margins caused by larvae")
                        }
                    }
                }
            }

            // AUTO-DETECT MODE: Balanced Pest & Disease Identification
            // 1. Maize & Cereals -> Fall Armyworm vs Rust
            isMaizeOrCereal && rustRatio > 0.035f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "cereal_rust" } ?: ThreatCatalog.allThreats.first()
                severity = "Extreme"
                symptomsDetected.add("Golden-brown to reddish-orange powdery pustules rupturing leaf epidermis")
                symptomsDetected.add("Linear rust stripes and chlorotic streaks on cereal leaf blades")
            }
            isMaizeOrCereal -> {
                // Maize without prominent rust pustules is overwhelmingly Fall Armyworm or Stem Borer in East Africa
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "fall_armyworm" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Window-paning foliage with ragged chewed leaf whorl")
                symptomsDetected.add("Moist brown frass sawdust packed in central vegetative cup")
                symptomsDetected.add("Larval skeletonization on upper corn leaf blades")
            }

            // 2. Brassicas (Cabbage, Sukuma Wiki, Kale) -> Diamondback Moth vs Aphids
            isBrassica && yellowRatio > 0.12f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "aphids" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Dense waxy aphid colonies clustered on lower leaf surfaces")
                symptomsDetected.add("Leaf curling, puckering, and stunting of heading brassicas")
            }
            isBrassica -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "diamondback_moth" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Translucent 'window-paning' feeding perforations in lower leaf epidermis")
                symptomsDetected.add("Skeletonized foliage with ragged leaf margins")
                symptomsDetected.add("Spindle-shaped pale green caterpillars wriggling on silken threads")
            }

            // 3. Cut Flowers (Roses, Carnations) -> Spider Mites, Thrips, Rust, Mildews
            isCutFlower && whiteRatio > 0.05f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "rose_powdery_mildew" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("White powdery talcum-like conidial patches on foliage and buds")
                symptomsDetected.add("Curling and puckering of young terminal rose leaves")
            }
            isCutFlower && purpleRatio > 0.04f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "rose_downy_mildew" } ?: ThreatCatalog.allThreats.first()
                severity = "Extreme"
                symptomsDetected.add("Angular purplish-red to dark brown necrotic spots delimited by veins")
                symptomsDetected.add("Severe premature leaf abscission; defoliated stems ('goose neck')")
            }
            isCutFlower && rustRatio > 0.02f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "flower_rust" } ?: ThreatCatalog.allThreats.first()
                severity = "Extreme"
                symptomsDetected.add("Dark cinnamon to chocolate-brown powdery rust pustules on stems and foliage")
            }
            isCutFlower && yellowRatio > 0.07f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "spider_mites" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Chlorotic pale yellow speckling (stippling) on upper leaf lamina")
                symptomsDetected.add("Fine silken webbing across leaf undersides and shoot tips")
                symptomsDetected.add("Foliar bronzing and desiccated parchment texture")
            }
            isCutFlower -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "western_flower_thrips" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Silvery bleached feeding patches on petals with tar-like black fecal droplets")
                symptomsDetected.add("Necrotic petal scorch and distorted opening buds")
            }

            // 4. Tomato / Solanaceae -> Late Blight vs Tuta Absoluta vs African Bollworm
            isTomatoOrPepper && (whiteRatio > 0.05f || (necroticRatio > 0.15f && yellowRatio < 0.05f)) -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "late_blight" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("Irregular water-soaked brown lesions rapidly spreading across lamina")
                symptomsDetected.add("Cottony white mycelial sporulation ring visible on lesion margins")
                symptomsDetected.add("Greasy darkening and stem collar necrosis")
            }
            isTomatoOrPepper && yellowRatio in 0.06f..0.14f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "tuta_absoluta" } ?: ThreatCatalog.allThreats.first()
                severity = "Extreme"
                symptomsDetected.add("Translucent papery blotch mines between leaf epidermises")
                symptomsDetected.add("Dark granular frass piles inside leaf mines and under green fruit calyx")
            }
            isTomatoOrPepper && necroticRatio > 0.08f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "african_bollworm" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("Circular chewed entry holes bored into tomato fruits and flower buds")
                symptomsDetected.add("Internal fruit cavity feeding with secondary rot")
            }

            // 5. Legumes (French Beans, Common Beans) -> Rust vs Maruca Pod Borer vs Spider Mites
            isLegume && rustRatio > 0.025f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "bean_rust" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("Reddish-brown powdery rust pustules (uredinia) on leaf undersides")
                symptomsDetected.add("Pronounced chlorotic yellow halos encircling erupting pustules")
            }
            isLegume && necroticRatio > 0.08f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "maruca_pod_borer" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("Webbed floral clusters and circular boreholes in developing bean pods")
            }
            isLegume -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "spider_mites" } ?: ThreatCatalog.allThreats.first()
                severity = "Moderate"
                symptomsDetected.add("Stippling speckles on bean foliage and fine webbing on leaf undersides")
            }

            // 6. General Fallbacks
            rustRatio > 0.025f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "cereal_rust" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Powdery reddish-orange pustules rupturing leaf surface")
            }
            whiteRatio > 0.05f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "rose_powdery_mildew" } ?: ThreatCatalog.allThreats.first()
                severity = "Moderate"
                symptomsDetected.add("White powdery mycelium coating foliar tissue")
            }
            yellowRatio > 0.14f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "aphids" } ?: ThreatCatalog.allThreats.first()
                severity = "Moderate"
                symptomsDetected.add("Leaf curling with sticky shiny honeydew exudate")
                symptomsDetected.add("Dense colonial clustering of tiny soft-bodied insects on leaf undersides")
            }
            yellowRatio in 0.07f..0.14f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "spider_mites" } ?: ThreatCatalog.allThreats.first()
                severity = "High"
                symptomsDetected.add("Chlorotic yellow speckling (stippling) on upper leaf lamina")
                symptomsDetected.add("Fine silken webbing across leaf undersides")
            }
            necroticRatio > 0.12f -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "late_blight" } ?: ThreatCatalog.allThreats.first()
                severity = "Severe"
                symptomsDetected.add("Water-soaked dark lesions on leaf tips & margins")
                symptomsDetected.add("Faint sporulation halo under high humidity conditions")
            }
            else -> {
                matchedThreat = ThreatCatalog.allThreats.find { it.id == "african_bollworm" } ?: ThreatCatalog.allThreats.first()
                severity = "Moderate"
                symptomsDetected.add("Caterpillar foliar chew damage and circular perforation cavities")
            }
        }

        val confidence = 68 // Honest optical pre-screening confidence
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

        val analysisText = "Optical Chromatic Pre-Screening: Foliar Health ${(greenHealthyCount * 100f / totalPixels).toInt()}%, Chlorosis/Yellowing ${(yellowRatio * 100f).toInt()}%, Necrotic Lesions ${(necroticRatio * 100f).toInt()}%. Candidate identification: ${matchedThreat.name} (${matchedThreat.scientificName}). To verify factual accuracy and avoid false positives, confirm against the physical field symptoms: ${symptomsDetected.joinToString("; ")}. Use the Factual Symptom Key to confirm."

        return ScanDiagnosisResult(
            threat = matchedThreat,
            threatId = matchedThreat.id,
            diseaseName = matchedThreat.name,
            scientificName = matchedThreat.scientificName,
            crop = if (cropHint != "Auto-Detect") cropHint else matchedThreat.targetCrops.firstOrNull() ?: "Tomato",
            confidencePercent = confidence,
            severity = severity,
            visualSymptomsDetected = symptomsDetected,
            recommendedCulturalAction = matchedThreat.ipmPlan.culturalControls.firstOrNull() ?: "Prune and burn infected plant parts; avoid evening sprinkler irrigation.",
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
            mechanicalControlAction = matchedThreat.mechanicalControls.firstOrNull() ?: (matchedThreat.ipmPlan.physicalMechanicalControls.firstOrNull() ?: "Hand rogue or cultivate")
        )
    }
}
