package com.example.data.ai

import android.util.Log
import com.example.BuildConfig
import com.example.data.AgronomyCatalog
import com.example.data.ThreatCatalog
import com.example.data.compliance.AgriculturalAuditCatalog
import com.example.data.poultry.PoultryManagementCatalog
import com.example.data.quarantine.QuarantinePestCatalog
import com.example.data.science.WeedSoilScienceCatalog
import com.example.data.soil.CecRebalanceCalculator
import com.example.data.soil.IrrigationWaterCatalog
import com.example.data.soil.OrganicManureCatalog
import com.example.data.soil.SoilClassificationCatalog
import com.example.model.DeveloperInfo
import com.example.model.ai.AiChatMessage
import com.example.model.ai.AiQuickPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Intelligent AI Agronomist Engine for Farmers.
 * Combines online Google Gemini 2.5 Flash API with an extensive
 * offline expert agricultural knowledge fallback.
 */
object AiAgronomistService {

    private const val TAG = "AiAgronomistService"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(25, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .writeTimeout(25, TimeUnit.SECONDS)
        .build()

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
     * Ask a question to the AI Agronomist.
     * Uses Gemini 2.5 Flash online when available; falls back to offline engine seamlessly.
     */
    suspend fun askAgricultureAi(
        userQuestion: String,
        conversationHistory: List<AiChatMessage> = emptyList()
    ): AiChatMessage = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY.trim()
        val hasValidKey = apiKey.isNotBlank() &&
            !apiKey.equals("MY_GEMINI_API_KEY", ignoreCase = true) &&
            !apiKey.contains("placeholder", ignoreCase = true)

        if (hasValidKey) {
            try {
                val geminiResponse = callGeminiAgronomistApi(userQuestion, conversationHistory, apiKey)
                if (geminiResponse != null && geminiResponse.isNotBlank()) {
                    return@withContext AiChatMessage(
                        text = geminiResponse,
                        isUser = false,
                        isOnlineGemini = true,
                        topicCategory = detectTopicCategory(userQuestion),
                        suggestedFollowUps = generateFollowUps(userQuestion),
                        relatedCalculatorScreen = detectRelatedCalculator(userQuestion)
                    )
                }
            } catch (e: Exception) {
                Log.w(TAG, "Online Gemini API call failed, switching to offline agronomic engine: ${e.message}")
            }
        }

        // Offline Fallback
        val offlineResponse = generateOfflineAgronomicResponse(userQuestion)
        AiChatMessage(
            text = offlineResponse,
            isUser = false,
            isOnlineGemini = false,
            topicCategory = detectTopicCategory(userQuestion),
            suggestedFollowUps = generateFollowUps(userQuestion),
            relatedCalculatorScreen = detectRelatedCalculator(userQuestion)
        )
    }

    private fun callGeminiAgronomistApi(
        question: String,
        history: List<AiChatMessage>,
        apiKey: String
    ): String? {
        val systemPrompt = buildString {
            append("You are 'CropShield AI Agronomist', an expert tropical, East African, and international Senior Agronomist, Plant Pathologist, Soil Scientist, and Livestock Advisor. ")
            append("You serve smallholder and commercial farmers, extension officers, and field scouts. ")
            append("Always provide clear, authoritative, practical, and mathematically precise advice. ")
            append("When answering pest or disease questions: ")
            append("1. Clearly identify the organism and characteristic damage symptoms. ")
            append("2. Provide Cultural and Organic IPM controls (crop rotation, spacing, sanitation, push-pull, resistant varieties). ")
            append("3. Provide Biological controls (CABI BioProtection registered products, parasitoids, entomopathogens like Metarhizium/Beauveria/Bt). ")
            append("4. Provide PCPB Kenya registered agrochemicals with exact knapsack dilution rates (e.g. mL or grams per 16L or 20L backpack tank) and mandatory Pre-Harvest Intervals (PHI). ")
            append("When answering soil, fertilizer, or irrigation questions: ")
            append("Provide exact basal/topdressing fertilizer rates (kg/ha and 50kg bags), liming requirements based on pH and CEC, or FAO-56 irrigation depths (mm/day and liters/plant/day). ")
            append("When answering poultry questions: ")
            append("Detail specific age-based vaccination schedules, administration routes (eye drop, drinking water with 2.5 g/L skimmed milk, wing web), and CP%/energy feeding programs. ")
            append("When answering pest, disease, or portal inquiries: ")
            append("Draw directly from and cite the 11 authoritative international portals (AHR Australia, NPPC Bhutan, CABI PlantwisePlus Knowledge Bank, Bayer CropScience Agronomy ID, EPPO Global Quarantine A1/A2 Lists, Penn State Extension, Gardener's Supply Encyclopedia, Minnesota MDA Index, TAAT Africa Technologies, Corson Maize Pests & Diseases, and iNaturalist) as well as the NUFLIP / JICA Northern Uganda Vegetable Factsheet Guide (22 pests, 22 diseases, 8 physiological disorders like Blossom End Rot, Sunburn, Potassium/Magnesium deficiency, Flooding damage, and Watermelon pollination failure) with IRAC & FRAC MoA rotation and Bronopol plant activator. ")
            append("Sign off advice technically under the authority of Crop Scientist Odongo Vincent (Lead Agronomist & Developer). Keep tone encouraging, professional, and formatted with clear markdown headers and bullet points.")
        }

        val contentsArray = JSONArray()

        // Include recent history (up to last 4 messages for conversational context)
        val recentHistory = history.takeLast(4)
        recentHistory.forEach { msg ->
            val role = if (msg.isUser) "user" else "model"
            contentsArray.put(JSONObject().apply {
                put("role", role)
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", msg.text) })
                })
            })
        }

        // Current user message with system instruction context
        contentsArray.put(JSONObject().apply {
            put("role", "user")
            put("parts", JSONArray().apply {
                put(JSONObject().apply {
                    put("text", "System Instruction: $systemPrompt\n\nFarmer Question: $question")
                })
            })
        })

        val jsonBody = JSONObject().apply {
            put("contents", contentsArray)
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.3)
                put("maxOutputTokens", 2048)
            })
        }

        val requestUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
        val request = Request.Builder()
            .url(requestUrl)
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = okHttpClient.newCall(request).execute()
        if (!response.isSuccessful) {
            val err = response.body?.string() ?: ""
            Log.e(TAG, "Gemini chat error ${response.code}: $err")
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

        return parts.getJSONObject(0).optString("text", "")
    }

    /**
     * Resilient Offline Agricultural Knowledge Engine.
     * Evaluates intents, keywords, and domain catalogs to formulate
     * comprehensive agronomic prescriptions even with zero network connectivity.
     */
    fun generateOfflineAgronomicResponse(query: String): String {
        val q = query.lowercase()

        // 1. FALL ARMYWORM
        if (q.contains("armyworm") || q.contains("faw") || (q.contains("maize") && q.contains("caterpillar"))) {
            val faw = ThreatCatalog.getThreatById("fall_armyworm")
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
            val blight = ThreatCatalog.getThreatById("late_blight")
            val wilt = ThreatCatalog.getThreatById("bacterial_wilt")
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
        if (q.contains("soil") || q.contains("lime") || q.contains("cec") || q.contains("ph") || q.contains("acidity")) {
            return buildString {
                append("### 🧪 Soil Acidity Management, Liming & CEC Rebalancing\n\n")
                append("**1. Diagnosis of Soil pH 4.8 (Strongly Acidic):**\n")
                append("• Soil pH below 5.5 triggers **Aluminum (Al³⁺) and Manganese (Mn²⁺) toxicity**, which burns root tips and stunts root elongation.\n")
                append("• Causes severe **Phosphorus fixation** into insoluble aluminum-phosphate complexes.\n")
                append("• Induced deficiencies of Calcium (Ca²⁺), Magnesium (Mg²⁺), and Molybdenum (Mo).\n\n")
                append("**2. Agricultural Liming Prescription:**\n")
                append("• To raise pH from 4.8 to target 6.2 on loamy clay soils, apply **1.5 to 2.5 metric tonnes of Agricultural Lime (CaCO₃) per Hectare (approx. 600 - 1,000 kg per Acre)**.\n")
                append("• **Dolomitic Limestone (CaMg(CO₃)₂)** is required if soil test reveals Magnesium saturation < 10%.\n")
                append("• Broadcast lime evenly across dry soil 4 - 6 weeks before planting and incorporate thoroughly into the top 15 - 20 cm.\n\n")
                append("**3. Albrecht Base Cation Saturation Ratio (BCSR) Targets:**\n")
                append("• **Calcium Saturation**: 65% - 75%\n")
                append("• **Magnesium Saturation**: 10% - 15%\n")
                append("• **Potassium Saturation**: 3% - 5%\n")
                append("• **Target Ca:Mg Ratio**: 5:1 to 7:1 (ratios < 3:1 cause soil crusting and dispersion).\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 5. IRRIGATION & WATER REQUIREMENT
        if (q.contains("irrigat") || q.contains("water") || q.contains("et0") || q.contains("drip") || q.contains("leaching")) {
            return buildString {
                append("### 💧 FAO-56 Irrigation Water Requirement & Salinity Management\n\n")
                append("**1. Crop Evapotranspiration Formula:**\n")
                append("ETc = ET0 × Kc\n")
                append("• Where ET0 is reference evapotranspiration (typically 4.5 - 6.0 mm/day in tropical zones) and Kc is crop coefficient.\n")
                append("• For Tomatoes/Capsicums at mid-season peak flowering, Kc = 1.15.\n")
                append("• ETc = 5.0 mm/day × 1.15 = 5.75 mm/day (57.5 m³/ha/day).\n\n")
                append("**2. Water Application per Plant:**\n")
                append("• At plant spacing of 60 cm × 50 cm (0.30 m² per plant), net water demand = 5.75 mm × 0.30 = **1.73 Liters/plant/day**.\n")
                append("• Accounting for 90% drip system efficiency: **Gross Application = 1.95 - 2.1 Liters per plant per day**.\n\n")
                append("**3. Salinity & Leaching Fraction (LF):**\n")
                append("LF = ECw / (5 × ECe - ECw)\n")
                append("• When irrigation water EC (ECw) exceeds 1.0 mS/cm, apply 10% - 15% extra water volume to leach salts below root zones and prevent tip burn.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 6. COMPOST & MANURE
        if (q.contains("compost") || q.contains("manure") || q.contains("organic") || q.contains("c:n")) {
            return buildString {
                append("### 🌿 Organic Manure Masterclass & 18-Day Berkeley Composting\n\n")
                append("**1. The 18-Day Berkeley Hot Composting Method:**\n")
                append("• Build pile with alternating layers of **Browns (Carbon)** and **Greens (Nitrogen)** to reach an initial C:N ratio of 25:1 to 30:1.\n")
                append("• Pile dimensions: Minimum 1 meter × 1 meter × 1.5 meters high.\n")
                append("• **Turning Schedule**: Let sit for 4 days $\\rightarrow$ Turn on Day 4 $\\rightarrow$ Turn every 2 days on Days 6, 8, 10, 12, 14.\n")
                append("• Thermophilic core temperature reaches 55 - 65°C, pasteurizing all weed seeds and pathogens.\n\n")
                append("**2. Raw Material Balancing (Browns vs Greens):**\n")
                append("• **Browns (Carbon)**: Dry maize stalks (C:N 60:1), dry straw (80:1), sawdust (400:1), dry leaves (50:1).\n")
                append("• **Greens (Nitrogen)**: Cattle manure (18:1), Poultry manure (10:1), Fresh grass clippings (18:1), Legume vines (15:1).\n\n")
                append("**3. Field Application Tonnage:**\n")
                append("• Commercial Vegetables (Tomatoes, Cabbages): **10 - 15 tonnes/ha (approx. 200 - 300 bags of 50kg/ha)** or 1 - 2 handfuls (200 - 400g) per planting hole thoroughly mixed with topsoil.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 7. SPRAY NOZZLE & CALIBRATION
        if (q.contains("nozzle") || q.contains("spray time") || q.contains("pressure") || q.contains("discharge") || q.contains("knapsack")) {
            return buildString {
                append("### 🎯 Spray Nozzle Discharge, Pressure & Spray Time Calibration\n\n")
                append("**1. Physical Nozzle Law:**\n")
                append("$$q = q_0 \\times \\sqrt{\\frac{P}{3.0\\text{ bar}}}$$\n")
                append("• Standard Yellow 02 nozzle delivers $0.80\\text{ L/min}$ at $3.0\\text{ bar}$.\n")
                append("• At $2.0\\text{ bar}$, discharge = $0.80 \\times \\sqrt{2.0 / 3.0} = \\mathbf{0.653\\text{ L/min}}$ ($39.2\\text{ L/h}$).\n")
                append("• At $4.0\\text{ bar}$, discharge = $0.80 \\times \\sqrt{4.0 / 3.0} = \\mathbf{0.924\\text{ L/min}}$ ($55.4\\text{ L/h}$).\n\n")
                append("**2. Tank Emptying Times:**\n")
                append("• 16L Knapsack at 3.0 bar: $16.0 / 0.80 = \\mathbf{20.0\\text{ minutes}}$ continuous spraying.\n")
                append("• 20L Knapsack at 3.0 bar: $20.0 / 0.80 = \\mathbf{25.0\\text{ minutes}}$ continuous spraying.\n\n")
                append("**3. Orifice Wear 10% Rule:**\n")
                append("• Catch fluid in measuring cylinder for exactly 60 seconds at operating pressure.\n")
                append("• If captured volume exceeds nominal flow by $> 10\\%$, the nozzle is worn out and must be replaced immediately to avert chemical overdosing and crop scorching.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // 8. AUDIT & COMPLIANCE
        if (q.contains("audit") || q.contains("compliance") || q.contains("globalgap") || q.contains("ks 1758") || q.contains("phi") || q.contains("mrl")) {
            return buildString {
                append("### 📋 GlobalG.A.P. IFA v6 & KS 1758 Agricultural Compliance Protocols\n\n")
                append("**1. Pre-Harvest Intervals (PHI) & MRLs:**\n")
                append("• All chemical applications must respect statutory PHIs before harvest sign-off.\n")
                append("• Prominently display warning flags at field block entry with application date, chemical name, and earliest harvest clearance hour.\n\n")
                append("**2. Empty Agrochemical Container Triple-Rinsing SOP:**\n")
                append("1. Fill 1/4 of container with clean water.\n")
                append("2. Shake vigorously for 30 seconds.\n")
                append("3. Drain rinse water into spray tank for 30 seconds. Repeat 3 times.\n")
                append("4. Puncture bottom to render unusable and lock in designated empty container store awaiting NEMA-licensed disposal.\n\n")
                append("**3. Worker Health & Welfare:**\n")
                append("• Complete certified PPE provided free of charge.\n")
                append("• Annual blood Cholinesterase testing for organophosphate/carbamate handlers.\n")
                append("• Clean field sanitation latrines and potable drinking water within 500m of all work stations.\n\n")
                append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
            }
        }

        // DEFAULT AGRONOMIC INTELLIGENCE ANSWER
        return buildString {
            append("### 🌾 CropShield Agricultural Advisory\n\n")
            append("Regarding your inquiry: **\"$query\"**\n\n")
            append("**Agronomic Best Practice Principles:**\n")
            append("1. **Diagnostic Soil & Crop Scouting**: Systematically inspect 20 representative plants across a W-pattern in the field. Assess leaf undersides, growing tips, and root health.\n")
            append("2. **Integrated Pest Management (IPM)**: Employ cultural methods (crop rotation, certified seeds, clean weeding) before resorting to synthetic chemicals.\n")
            append("3. **Balanced Plant Nutrition**: Apply fertilizers based on plant phenological stage—high phosphorus at planting for root development, balanced nitrogen during vegetative canopy, and potassium during flowering and fruit bulking.\n")
            append("4. **Safe Chemical Stewardship**: Always read product labels for dosage per 16L/20L knapsack tank, respect Pre-Harvest Intervals (PHI), wear certified PPE, and spray during calm hours (early morning or dusk).\n\n")
            append("💡 *Tip: For exact dosing, open the **Knapsack Calculator**, **Soil & CEC Calculator**, or **Nozzle Discharge** tabs from the menu.*\n\n")
            append("*Signed off by Lead Agronomist Odongo Vincent (CropShield IPM)*")
        }
    }

    private fun detectTopicCategory(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("pest") || q.contains("insect") || q.contains("caterpillar") || q.contains("worm") || q.contains("mite") || q.contains("thrip") -> "Pest Management"
            q.contains("disease") || q.contains("blight") || q.contains("fung") || q.contains("bacter") || q.contains("wilt") || q.contains("rust") -> "Crop Pathology"
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
