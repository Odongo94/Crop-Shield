package com.example.model.soil

import androidx.compose.ui.graphics.Color

/**
 * Method used to determine Cation Exchange Capacity (CEC).
 */
enum class CecInputMode(val title: String, val subtitle: String) {
    LAB_CATIONS("Laboratory Soil Test", "Enter exchangeable Ca, Mg, K, Na, and Acidity in meq/100g (cmol/kg)"),
    TEXTURE_ESTIMATE("Colloidal Estimate", "Estimate CEC from Sand %, Silt %, Clay %, and Organic Matter %")
}

/**
 * CEC rating classification based on soil science benchmarks.
 */
enum class CecRatingCategory(
    val title: String,
    val rangeText: String,
    val color: Color,
    val nutrientHoldingAbility: String,
    val leachingRisk: String,
    val fertilizerStrategy: String
) {
    VERY_LOW(
        title = "Very Low CEC",
        rangeText = "< 6 meq/100g",
        color = Color(0xFFD9534F),
        nutrientHoldingAbility = "Extremely poor colloidal retention; almost pure coarse sand or heavily degraded subsoil.",
        leachingRisk = "Severe: Soluble nitrogen and potassium leach within hours of heavy rain or irrigation.",
        fertilizerStrategy = "Never apply single heavy basal fertilizer doses; apply frequent micro-doses via fertigation; add compost and biochar."
    ),
    LOW(
        title = "Low CEC",
        rangeText = "6 - 12 meq/100g",
        color = Color(0xFFF2A65A),
        nutrientHoldingAbility = "Low buffering capacity; common in sandy loams and weathered Ferralsols.",
        leachingRisk = "Moderate to High: Cations easily displaced by heavy rainfall; lime requires split applications.",
        fertilizerStrategy = "Split topdressing into 3–4 applications; maintain organic mulches to build colloidal humus."
    ),
    MODERATE(
        title = "Moderate CEC",
        rangeText = "12 - 20 meq/100g",
        color = Color(0xFF4E9F3D),
        nutrientHoldingAbility = "Good nutrient bank; typical of healthy loams, silt loams, and volcanic Nitisols.",
        leachingRisk = "Low: Well-buffered against sudden pH swings; retains potassium and magnesium effectively.",
        fertilizerStrategy = "Standard basal plus 1–2 topdressing splits are highly effective; responsive to balanced NPK."
    ),
    HIGH(
        title = "High CEC",
        rangeText = "20 - 30 meq/100g",
        color = Color(0xFF2E7D32),
        nutrientHoldingAbility = "Excellent colloidal reservoir; clay loams, silty clays, and rich organic soils.",
        leachingRisk = "Very Low: High resistance to leaching; holds large reserves of calcium and magnesium.",
        fertilizerStrategy = "Can accept full basal charges of P, K, and Ca; monitor for physical compaction and aeration."
    ),
    VERY_HIGH(
        title = "Very High CEC",
        rangeText = "> 30 meq/100g",
        color = Color(0xFF1B5E20),
        nutrientHoldingAbility = "Colossal cation reservoir; Vertisols (Black Cotton soils) and heavy smectite clays.",
        leachingRisk = "Negligible leaching; high swelling and shrinkage dynamics.",
        fertilizerStrategy = "High buffering means soil requires larger quantities of lime or sulfur to shift pH; monitor Ca:Mg ratio."
    )
}

/**
 * Recommended soil chemical amendment to rebalance cation ratios.
 */
data class SoilAmendmentRecommendation(
    val amendmentName: String,
    val targetDeficit: String,
    val primaryFunction: String,
    val rateKgPerHectare: Double,
    val rateKgPerAcre: Double,
    val applicationTimingAndMethod: String,
    val precautions: String
)

/**
 * Full output result of the Cation Exchange Capacity and Cation Saturation calculation.
 */
data class CecCalculationResult(
    val totalCec: Double,
    val baseSaturationPercent: Double,
    val caSaturationPercent: Double,
    val mgSaturationPercent: Double,
    val kSaturationPercent: Double,
    val naSaturationPercent: Double,
    val aciditySaturationPercent: Double,
    val caToMgRatio: Double,
    val mgToKRatio: Double,
    val rating: CecRatingCategory,
    val soilConditionSummary: String,
    val stepByStepDerivations: List<String>,
    val cationEvaluationNotes: List<String>,
    val amendmentRecommendations: List<SoilAmendmentRecommendation>,
    val organicMatterImprovementAdvice: String
)
