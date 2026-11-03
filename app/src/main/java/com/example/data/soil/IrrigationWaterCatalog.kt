package com.example.data.soil

import androidx.compose.ui.graphics.Color
import com.example.model.soil.CropGrowthStage
import com.example.model.soil.CropIrrigationProfile
import com.example.model.soil.IrrigationCalculationResult
import com.example.model.soil.IrrigationMethod
import java.util.Locale
import kotlin.math.max
import kotlin.math.roundToInt

object IrrigationWaterCatalog {

    val crops: List<CropIrrigationProfile> = listOf(
        CropIrrigationProfile(
            id = "irrig_tomato",
            cropName = "Tomatoes (Lycopersicon esculentum)",
            category = "Solanaceous",
            kcInitial = 0.60,
            kcDevelopment = 0.85,
            kcMidSeason = 1.15,
            kcLateSeason = 0.80,
            rootZoneDepthMeters = 0.70,
            allowableDepletionFractionP = 0.40,
            soilSalinityThresholdECe = 2.5, // dS/m threshold for 100% yield
            salinitySlopeB = 9.9, // 9.9% yield loss per dS/m above threshold
            optimalSoilPhMin = 6.0,
            optimalSoilPhMax = 6.8,
            standardSpacingMetersSq = 0.27, // 60cm x 45cm = 0.27 m²
            droughtVulnerabilityNotes = "Moisture stress at flowering causes blossom drop and Blossom End Rot (BER) due to disrupted calcium transport."
        ),
        CropIrrigationProfile(
            id = "irrig_potato",
            cropName = "Irish Potatoes (Solanum tuberosum)",
            category = "Tubers",
            kcInitial = 0.50,
            kcDevelopment = 0.75,
            kcMidSeason = 1.15,
            kcLateSeason = 0.75,
            rootZoneDepthMeters = 0.60,
            allowableDepletionFractionP = 0.35, // Very sensitive to moisture swings
            soilSalinityThresholdECe = 1.7,
            salinitySlopeB = 12.0,
            optimalSoilPhMin = 5.2,
            optimalSoilPhMax = 6.4,
            standardSpacingMetersSq = 0.225, // 75cm x 30cm
            droughtVulnerabilityNotes = "Moisture fluctuations during tuber bulking trigger secondary growth, knobbiness, growth cracks, and internal hollow heart."
        ),
        CropIrrigationProfile(
            id = "irrig_maize",
            cropName = "Hybrid Maize / Corn (Zea mays)",
            category = "Cereals",
            kcInitial = 0.40,
            kcDevelopment = 0.80,
            kcMidSeason = 1.20,
            kcLateSeason = 0.60,
            rootZoneDepthMeters = 1.20,
            allowableDepletionFractionP = 0.55,
            soilSalinityThresholdECe = 1.7,
            salinitySlopeB = 12.0,
            optimalSoilPhMin = 5.8,
            optimalSoilPhMax = 7.0,
            standardSpacingMetersSq = 0.1875, // 75cm x 25cm
            droughtVulnerabilityNotes = "Moisture stress during tasseling and silking (2 weeks before to 2 weeks after pollination) causes catastrophic desiccation of silk and severe grain barrenness."
        ),
        CropIrrigationProfile(
            id = "irrig_beans",
            cropName = "Common Dry Beans (Phaseolus vulgaris)",
            category = "Legumes",
            kcInitial = 0.40,
            kcDevelopment = 0.70,
            kcMidSeason = 1.15,
            kcLateSeason = 0.35,
            rootZoneDepthMeters = 0.60,
            allowableDepletionFractionP = 0.45,
            soilSalinityThresholdECe = 1.0, // Highly salt sensitive
            salinitySlopeB = 19.0, // Very steep yield loss per dS/m
            optimalSoilPhMin = 6.0,
            optimalSoilPhMax = 6.8,
            standardSpacingMetersSq = 0.0675, // 45cm x 15cm
            droughtVulnerabilityNotes = "Extremely sensitive to salinity (ECe > 1.0 dS/m) and waterlogging; moisture deficit during flowering triggers total pod abortion."
        ),
        CropIrrigationProfile(
            id = "irrig_cabbage",
            cropName = "Cabbages & Brassicas (Brassica oleracea)",
            category = "Leafy Vegetables",
            kcInitial = 0.70,
            kcDevelopment = 0.85,
            kcMidSeason = 1.05,
            kcLateSeason = 0.95,
            rootZoneDepthMeters = 0.50,
            allowableDepletionFractionP = 0.45,
            soilSalinityThresholdECe = 1.8,
            salinitySlopeB = 9.7,
            optimalSoilPhMin = 6.0,
            optimalSoilPhMax = 7.2,
            standardSpacingMetersSq = 0.30, // 60cm x 50cm
            droughtVulnerabilityNotes = "Requires continuous uniform moisture; water stress followed by sudden heavy irrigation causes mature heads to burst/split."
        ),
        CropIrrigationProfile(
            id = "irrig_onion",
            cropName = "Bulb Onions (Allium cepa)",
            category = "Bulb Vegetables",
            kcInitial = 0.70,
            kcDevelopment = 0.85,
            kcMidSeason = 1.05,
            kcLateSeason = 0.75,
            rootZoneDepthMeters = 0.40, // Shallow root system
            allowableDepletionFractionP = 0.30,
            soilSalinityThresholdECe = 1.2,
            salinitySlopeB = 16.0,
            optimalSoilPhMin = 6.0,
            optimalSoilPhMax = 6.8,
            standardSpacingMetersSq = 0.03, // 30cm x 10cm
            droughtVulnerabilityNotes = "Shallow fibrous roots demand frequent, light irrigations. Cut off irrigation 2–3 weeks before harvest to promote neck drying and prevent bulb rot."
        ),
        CropIrrigationProfile(
            id = "irrig_pepper",
            cropName = "Bell Peppers / Capsicum (Capsicum annuum)",
            category = "Solanaceous",
            kcInitial = 0.60,
            kcDevelopment = 0.85,
            kcMidSeason = 1.15,
            kcLateSeason = 0.85,
            rootZoneDepthMeters = 0.70,
            allowableDepletionFractionP = 0.35,
            soilSalinityThresholdECe = 1.5,
            salinitySlopeB = 14.0,
            optimalSoilPhMin = 6.0,
            optimalSoilPhMax = 6.8,
            standardSpacingMetersSq = 0.25, // 50cm x 50cm
            droughtVulnerabilityNotes = "Moisture deficit at fruit set causes blossom-end rot and sunscald from sparse canopy foliage."
        ),
        CropIrrigationProfile(
            id = "irrig_avocado",
            cropName = "Hass Avocado (Perennial Orchard)",
            category = "Fruit Trees",
            kcInitial = 0.60,
            kcDevelopment = 0.70,
            kcMidSeason = 0.85,
            kcLateSeason = 0.75,
            rootZoneDepthMeters = 1.00,
            allowableDepletionFractionP = 0.50,
            soilSalinityThresholdECe = 1.3, // Extremely sensitive to chloride and sodium
            salinitySlopeB = 11.0,
            optimalSoilPhMin = 5.5,
            optimalSoilPhMax = 6.5,
            standardSpacingMetersSq = 36.0, // 6m x 6m
            droughtVulnerabilityNotes = "Shallow feeder roots are asphyxiated by poor drainage (triggers Phytophthora cinnamomi root rot); chloride in saline water causes severe leaf tip burn."
        ),
        CropIrrigationProfile(
            id = "irrig_coffee",
            cropName = "Arabica Coffee (Coffea arabica)",
            category = "Cash Crops",
            kcInitial = 0.90,
            kcDevelopment = 0.95,
            kcMidSeason = 1.05,
            kcLateSeason = 0.90,
            rootZoneDepthMeters = 1.50,
            allowableDepletionFractionP = 0.50,
            soilSalinityThresholdECe = 1.5,
            salinitySlopeB = 10.0,
            optimalSoilPhMin = 5.5,
            optimalSoilPhMax = 6.5,
            standardSpacingMetersSq = 7.56, // 2.75m x 2.75m
            droughtVulnerabilityNotes = "Requires a distinct 2–3 month moisture deficit to synchronize flower bud dormancy, followed by water application to trigger uniform synchronous blossom flush."
        ),
        CropIrrigationProfile(
            id = "irrig_banana",
            cropName = "Highland Bananas / Plantains (Musa spp.)",
            category = "Fruit Crops",
            kcInitial = 0.90,
            kcDevelopment = 1.00,
            kcMidSeason = 1.20,
            kcLateSeason = 1.10,
            rootZoneDepthMeters = 0.75,
            allowableDepletionFractionP = 0.35, // High water consumer
            soilSalinityThresholdECe = 1.0,
            salinitySlopeB = 18.0,
            optimalSoilPhMin = 5.8,
            optimalSoilPhMax = 6.8,
            standardSpacingMetersSq = 9.0, // 3m x 3m
            droughtVulnerabilityNotes = "Massive leaf area results in extreme transpiration; any moisture stress reduces bunch weight and delays ratoon harvest cycle."
        ),
        CropIrrigationProfile(
            id = "irrig_roses",
            cropName = "Commercial Cut Roses (Rosa hybrida)",
            category = "Floriculture",
            kcInitial = 0.70,
            kcDevelopment = 0.90,
            kcMidSeason = 1.15,
            kcLateSeason = 1.05,
            rootZoneDepthMeters = 0.45,
            allowableDepletionFractionP = 0.25, // Intensive substrate/greenhouse
            soilSalinityThresholdECe = 1.5,
            salinitySlopeB = 15.0,
            optimalSoilPhMin = 5.5,
            optimalSoilPhMax = 6.2,
            standardSpacingMetersSq = 0.12, // ~8 plants/m²
            droughtVulnerabilityNotes = "Requires tight EC (1.4–1.8 mS/cm) and pH (5.6–6.0) control in dripper line to ensure long stem length, large bud head, and zero petal necrosis."
        )
    )

    /**
     * Calculates crop water requirement, leaching fraction, and gross irrigation volume in line with EC and pH.
     */
    fun calculateIrrigationRequirement(
        crop: CropIrrigationProfile,
        growthStage: CropGrowthStage,
        referenceET0MmDay: Double = 5.0,
        effectiveRainfallMmDay: Double = 0.0,
        irrigationMethod: IrrigationMethod = IrrigationMethod.DRIP_LINE,
        soilECe: Double = 1.5, // Soil electrical conductivity (dS/m)
        waterECw: Double = 0.8, // Irrigation water electrical conductivity (dS/m)
        soilPh: Double = 6.5,
        waterPh: Double = 7.4,
        waterBicarbonatesMeqL: Double = 2.5 // Bicarbonates in meq/L
    ): IrrigationCalculationResult {
        val et0 = max(1.0, referenceET0MmDay)
        val peff = max(0.0, effectiveRainfallMmDay)

        // Select crop coefficient Kc based on growth stage
        val kc = when (growthStage) {
            CropGrowthStage.INITIAL -> crop.kcInitial
            CropGrowthStage.DEVELOPMENT -> crop.kcDevelopment
            CropGrowthStage.MID_SEASON -> crop.kcMidSeason
            CropGrowthStage.LATE_SEASON -> crop.kcLateSeason
        }

        // Crop Evapotranspiration: ETc = ET0 * Kc
        val etc = et0 * kc
        val netIrrigationMm = max(0.0, etc - peff)

        // Leaching Fraction (LF) according to FAO-29 (Ayers & Westcot):
        // LF = ECw / (5 * ECe - ECw)
        val denom = 5.0 * crop.soilSalinityThresholdECe - waterECw
        val rawLf = if (denom > 0.01) waterECw / denom else 0.40
        // Leaching fraction practical bounds: minimum 5% to prevent salt accumulation, max 45%
        val lf = rawLf.coerceIn(0.05, 0.45)
        val lfPercent = lf * 100.0

        // Gross Irrigation Depth (mm/day) = Net / [(1 - LF) * Efficiency]
        val efficiency = irrigationMethod.efficiencyPercent / 100.0
        val grossDepthMm = if (1.0 - lf > 0.05) netIrrigationMm / ((1.0 - lf) * efficiency) else netIrrigationMm / 0.50

        // Volumetric conversions:
        // 1 mm depth over 1 hectare (10,000 m²) = 10 m³ of water
        val volumeM3Ha = grossDepthMm * 10.0
        val volumeM3Acre = volumeM3Ha * 0.4047

        // Liters per plant per day:
        // Volume per plant (m³) = (grossDepthMm / 1000 m) * AreaPerPlant (m²)
        // Liters = Volume (m³) * 1000 = grossDepthMm * AreaPerPlant (m²)
        val litersPerPlantDay = grossDepthMm * crop.standardSpacingMetersSq

        // Irrigation Interval (Days between waterings) based on soil readily available water:
        // Total Available Water in loam ≈ 140 mm/m depth
        val tawMm = 140.0 * crop.rootZoneDepthMeters
        val rawMm = tawMm * crop.allowableDepletionFractionP
        val intervalDays = max(1, (rawMm / etc).roundToInt())

        // Salinity assessment
        val (salinityText, salinityColor) = when {
            soilECe <= crop.soilSalinityThresholdECe && waterECw <= 0.7 ->
                Pair("Non-Saline / Optimal: Salinity is safely below crop threshold (${f(crop.soilSalinityThresholdECe)} dS/m). 100% potential yield achievable with baseline 5% leaching fraction.", Color(0xFF2E7D32))
            soilECe <= crop.soilSalinityThresholdECe + 1.0 ->
                Pair("Slight to Moderate Salinity Hazard: Soil EC (${f(soilECe)} dS/m) approaches crop threshold. Requires ${f(lfPercent)}% leaching fraction to flush accumulated salts below root zone.", Color(0xFFF2A65A))
            else -> {
                val excess = soilECe - crop.soilSalinityThresholdECe
                val yieldLoss = (excess * crop.salinitySlopeB).coerceAtMost(100.0)
                Pair("Severe Salinity Hazard: Soil EC exceeds threshold by ${f(excess)} dS/m. Estimated yield penalty without heavy leaching: -${f(yieldLoss)}%. Immediate flushing required.", Color(0xFFD9534F))
            }
        }

        // pH Interaction and Nutrient Availability Matrix
        val phNotes = mutableListOf<String>()
        if (soilPh < 5.5) {
            phNotes.add("Soil Acidity Alert (pH ${f(soilPh)} < 5.5): Severe phosphorus fixation by active iron (Fe) and aluminum (Al) oxides; toxic free aluminum inhibits root tip cell division. Apply agricultural lime (CaCO₃).")
        } else if (soilPh in 5.8..6.8) {
            phNotes.add("Optimal Soil pH (${f(soilPh)}): Maximum biological availability of Nitrogen, Phosphorus, Potassium, Calcium, Magnesium, and Sulfur; active soil microbial respiration.")
        } else if (soilPh > 7.5) {
            phNotes.add("Soil Alkalinity / Calcareous Alert (pH ${f(soilPh)} > 7.5): Phosphorus precipitates as insoluble calcium phosphate; severe micronutrient lockup (Iron, Zinc, Manganese chlorosis).")
        }

        if (waterPh > 7.2 && waterBicarbonatesMeqL > 1.5) {
            phNotes.add("Irrigation Water Bicarbonate Hazard: Water pH is ${f(waterPh)} with high bicarbonates (${f(waterBicarbonatesMeqL)} meq/L). Bicarbonates react with calcium and magnesium to form white lime scale (CaCO₃) that clogs drip emitters and raises soil pH.")
        }

        // Acidification Recipe for Irrigation Water
        // To neutralize bicarbonates to safe residual of 0.5 meq/L:
        val excessBicarb = max(0.0, waterBicarbonatesMeqL - 0.5)
        val acidRecipe = if (excessBicarb > 0.1 && waterPh > 7.0) {
            // 85% Phosphoric acid: ~70 ml / m³ per 1 meq/L bicarb
            // 68% Nitric acid: ~65 ml / m³ per 1 meq/L bicarb
            val phosAcidMlM3 = excessBicarb * 70.0
            val nitricAcidMlM3 = excessBicarb * 65.0
            val citricAcidGm3 = excessBicarb * 64.0
            "Acidification Recipe per 1,000 Liters (1 m³) of Irrigation Water to reach pH 5.8–6.0:\n" +
            "• Option A (Phosphoric Acid 85%): Inject ${f(phosAcidMlM3)} ml/m³ (also supplies ${f(excessBicarb * 42.0)} g of P₂O₅).\n" +
            "• Option B (Nitric Acid 68%): Inject ${f(nitricAcidMlM3)} ml/m³ (supplies ${f(excessBicarb * 14.0)} g of N).\n" +
            "• Option C (Citric Acid - Organic): Dissolve ${f(citricAcidGm3)} grams/m³ of food-grade anhydrous citric acid."
        } else {
            "Water pH (${f(waterPh)}) and bicarbonates (${f(waterBicarbonatesMeqL)} meq/L) are well-balanced. No chemical acidification required."
        }

        val stepByStep = listOf(
            "1. Crop Evapotranspiration (ETc):",
            "   ETc = Reference ET0 (${f(et0)} mm/day) × Crop Coefficient Kc (${f(kc)} for ${growthStage.title})",
            "   ETc = ${f(et0)} × ${f(kc)} = ${f(etc)} mm / day.",
            "2. Net Irrigation Requirement (Inet):",
            "   Inet = ETc (${f(etc)} mm) - Effective Rainfall (${f(peff)} mm) = ${f(netIrrigationMm)} mm / day.",
            "3. Salinity Leaching Fraction (LF) Formula (FAO-29):",
            "   LF = ECw ÷ (5 × ECe - ECw) = ${f(waterECw)} ÷ (5 × ${f(crop.soilSalinityThresholdECe)} - ${f(waterECw)}) = ${f(lf)} (${f(lfPercent)}%).",
            "   (A ${f(lfPercent)}% water volume excess is added to prevent toxic root-zone salt buildup).",
            "4. Gross Irrigation Depth Formula:",
            "   Gross Depth = Inet ÷ [(1 - LF) × Efficiency]",
            "   Gross Depth = ${f(netIrrigationMm)} ÷ [(1 - ${f(lf)}) × (${irrigationMethod.efficiencyPercent}/100)] = ${f(grossDepthMm)} mm / day.",
            "5. Volumetric Water Budget:",
            "   • Daily Hectare Requirement: ${f(grossDepthMm)} mm × 10 = ${f(volumeM3Ha)} m³ / hectare / day (${(volumeM3Ha * 1000).roundToInt()} Liters/ha/day).",
            "   • Daily Acre Requirement: ${f(volumeM3Acre)} m³ / acre / day (${(volumeM3Acre * 1000).roundToInt()} Liters/acre/day).",
            "   • Per Plant Delivery: ${f(grossDepthMm)} mm × ${crop.standardSpacingMetersSq} m² = ${f(litersPerPlantDay)} Liters / plant / day."
        )

        val takeaways = listOf(
            "Drip Irrigation Timing: Apply the ${f(litersPerPlantDay)} Liters/plant/day in early morning (6:00 AM – 9:00 AM) to minimize midday evaporative losses.",
            "Salinity Flushing: If soil ECe rises above ${f(crop.soilSalinityThresholdECe)} dS/m, run an extra continuous 2-hour leaching irrigation with water to push salts into deep subsoil drainage.",
            "Emitter Clogging Prevention: High water pH (${f(waterPh)}) triggers calcium carbonate scale in drippers; inject dilute phosphoric acid monthly to dissolve mineral scale.",
            "Mulching Bonus: Spreading 5 cm of organic straw mulch reduces surface soil evaporation by 30%, saving ~${f(volumeM3Ha * 0.30)} m³ of water per hectare daily."
        )

        return IrrigationCalculationResult(
            cropName = crop.cropName,
            growthStage = growthStage,
            irrigationMethod = irrigationMethod,
            referenceET0MmDay = et0,
            cropCoefficientKc = kc,
            cropEvapotranspirationETcMmDay = etc,
            effectiveRainfallMmDay = peff,
            leachingFractionPercent = lfPercent,
            grossIrrigationDepthMmDay = grossDepthMm,
            grossVolumeM3PerHaPerDay = volumeM3Ha,
            grossVolumeM3PerAcrePerDay = volumeM3Acre,
            litersPerPlantPerDay = litersPerPlantDay,
            recommendedIntervalDays = intervalDays,
            soilSalinityAssessment = salinityText,
            soilSalinityColor = salinityColor,
            phInteractionAndNutrientAvailability = phNotes,
            waterAcidificationRecipe = acidRecipe,
            stepByStepMathFormulas = stepByStep,
            practicalFarmerTakeaways = takeaways
        )
    }

    private fun f(value: Double): String = String.format(Locale.US, "%.1f", value)
}
