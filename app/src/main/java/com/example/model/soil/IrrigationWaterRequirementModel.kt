package com.example.model.soil

import androidx.compose.ui.graphics.Color

/**
 * Irrigation application method and engineering application efficiency (FAO-56).
 */
enum class IrrigationMethod(val title: String, val efficiencyPercent: Double, val description: String) {
    DRIP_LINE(
        title = "Drip / Trickle Irrigation",
        efficiencyPercent = 90.0,
        description = "High precision, localized root zone application; minimal evaporation and zero wind drift losses."
    ),
    MICRO_SPRINKLER(
        title = "Micro-Sprinklers / Under-Tree",
        efficiencyPercent = 85.0,
        description = "Low-pressure uniform under-canopy wetting; suitable for orchards (Avocado, Citrus)."
    ),
    OVERHEAD_SPRINKLER(
        title = "Overhead Sprinkler System",
        efficiencyPercent = 75.0,
        description = "Wets entire canopy; vulnerable to wind drift and aerial evaporation in hot midday conditions."
    ),
    FURROW_FLOOD(
        title = "Surface Furrow / Basin Flooding",
        efficiencyPercent = 55.0,
        description = "Traditional gravity flow; high deep percolation and headland tailwater runoff losses."
    )
}

/**
 * Phenological growth stage for irrigation scheduling.
 */
enum class CropGrowthStage(val title: String, val description: String) {
    INITIAL("Initial / Germination & Seedling", "Germination to 10% ground cover; shallow roots."),
    DEVELOPMENT("Crop Development / Early Vegetative", "10% to 70–80% ground cover; rapid canopy and root expansion."),
    MID_SEASON("Mid-Season / Flowering & Fruit Set", "Peak water consumption; critical pollination and yield formation."),
    LATE_SEASON("Late-Season / Maturation & Ripening", "From grain filling / fruit ripening to harvest senescence.")
}

/**
 * Crop specific water requirement and salinity tolerance parameters according to FAO-56.
 */
data class CropIrrigationProfile(
    val id: String,
    val cropName: String,
    val category: String,
    val kcInitial: Double,
    val kcDevelopment: Double,
    val kcMidSeason: Double,
    val kcLateSeason: Double,
    val rootZoneDepthMeters: Double,
    val allowableDepletionFractionP: Double, // Fraction of available soil water that can be depleted without stress
    val soilSalinityThresholdECe: Double, // dS/m threshold for 100% yield potential (Maas-Hoffman model)
    val salinitySlopeB: Double, // % yield reduction per dS/m increase above threshold
    val optimalSoilPhMin: Double,
    val optimalSoilPhMax: Double,
    val standardSpacingMetersSq: Double, // Area per plant in m²
    val droughtVulnerabilityNotes: String
)

/**
 * Full output result of the Irrigation Water Requirement calculation in line with EC and pH.
 */
data class IrrigationCalculationResult(
    val cropName: String,
    val growthStage: CropGrowthStage,
    val irrigationMethod: IrrigationMethod,
    val referenceET0MmDay: Double,
    val cropCoefficientKc: Double,
    val cropEvapotranspirationETcMmDay: Double,
    val effectiveRainfallMmDay: Double,
    val leachingFractionPercent: Double,
    val grossIrrigationDepthMmDay: Double,
    val grossVolumeM3PerHaPerDay: Double,
    val grossVolumeM3PerAcrePerDay: Double,
    val litersPerPlantPerDay: Double,
    val recommendedIntervalDays: Int,
    val soilSalinityAssessment: String,
    val soilSalinityColor: Color,
    val phInteractionAndNutrientAvailability: List<String>,
    val waterAcidificationRecipe: String,
    val stepByStepMathFormulas: List<String>,
    val practicalFarmerTakeaways: List<String>
)
