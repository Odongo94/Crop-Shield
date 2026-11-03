package com.example.model.soil

import androidx.compose.ui.graphics.Color

/**
 * Common agricultural animal and compost manure types with average nutritional profiles.
 */
enum class ManureType(
    val title: String,
    val dryMatterPercent: Double,
    val totalNPercentDry: Double,
    val totalP2O5PercentDry: Double,
    val totalK2OPercentDry: Double,
    val year1NAvailabilityPercent: Double,
    val year1P2O5AvailabilityPercent: Double,
    val year1K2OAvailabilityPercent: Double,
    val averageCnRatio: Double,
    val chartColor: Color,
    val description: String,
    val bestSuitedFor: String
) {
    POULTRY_LAYER(
        title = "Poultry Layer Manure",
        dryMatterPercent = 65.0,
        totalNPercentDry = 4.0,
        totalP2O5PercentDry = 3.5,
        totalK2OPercentDry = 2.2,
        year1NAvailabilityPercent = 55.0,
        year1P2O5AvailabilityPercent = 70.0,
        year1K2OAvailabilityPercent = 85.0,
        averageCnRatio = 10.0,
        chartColor = Color(0xFFE65100),
        description = "High-potency organic source exceptionally rich in nitrogen, phosphorus, and calcium (from oyster shell feed). Decomposes rapidly.",
        bestSuitedFor = "Heavy nitrogen feeders like Brassicas (Cabbage, Kale), leafy vegetables, and basal dressing for maize and onions."
    ),
    BROILER_LITTER(
        title = "Broiler Litter (with Shavings)",
        dryMatterPercent = 75.0,
        totalNPercentDry = 3.2,
        totalP2O5PercentDry = 2.8,
        totalK2OPercentDry = 2.0,
        year1NAvailabilityPercent = 50.0,
        year1P2O5AvailabilityPercent = 65.0,
        year1K2OAvailabilityPercent = 80.0,
        averageCnRatio = 14.0,
        chartColor = Color(0xFFF57C00),
        description = "Contains bedding pine shavings or rice husks blended with droppings. Good balance of active carbon and fast-release nitrogen.",
        bestSuitedFor = "General arable crops, cereal basal dressing, and establishing perennial orchards."
    ),
    COW_FARMYARD(
        title = "Cattle / Dairy Farmyard Manure (FYM)",
        dryMatterPercent = 40.0,
        totalNPercentDry = 1.6,
        totalP2O5PercentDry = 0.9,
        totalK2OPercentDry = 1.8,
        year1NAvailabilityPercent = 30.0,
        year1P2O5AvailabilityPercent = 60.0,
        year1K2OAvailabilityPercent = 85.0,
        averageCnRatio = 20.0,
        chartColor = Color(0xFF5D4037),
        description = "Classic decomposed cattle manure with bedding straw. Outstanding builder of long-term soil structure, moisture-holding capacity, and beneficial mycorrhizae.",
        bestSuitedFor = "Solanaceous vegetables (Potatoes, Tomatoes), fruit trees (Avocado, Banana), and rehabilitating sandy or degraded soils."
    ),
    GOAT_SHEEP(
        title = "Goat & Sheep Manure Pellets",
        dryMatterPercent = 55.0,
        totalNPercentDry = 2.5,
        totalP2O5PercentDry = 1.4,
        totalK2OPercentDry = 2.8,
        year1NAvailabilityPercent = 35.0,
        year1P2O5AvailabilityPercent = 65.0,
        year1K2OAvailabilityPercent = 85.0,
        averageCnRatio = 16.0,
        chartColor = Color(0xFF795548),
        description = "Dry, pelletized manure rich in potassium. Sweeter and less acidic than cattle dung; lower moisture makes it easier to transport.",
        bestSuitedFor = "Potassium-demanding crops like Potatoes, Bananas, Coffee, Cut Flowers, and bulb vegetables."
    ),
    SWINE_PIG(
        title = "Swine / Pig Manure",
        dryMatterPercent = 30.0,
        totalNPercentDry = 2.8,
        totalP2O5PercentDry = 2.4,
        totalK2OPercentDry = 1.5,
        year1NAvailabilityPercent = 45.0,
        year1P2O5AvailabilityPercent = 65.0,
        year1K2OAvailabilityPercent = 80.0,
        averageCnRatio = 12.0,
        chartColor = Color(0xFF8D6E63),
        description = "High phosphorus and nitrogen; cold wet manure requiring thorough thermophilic composting to kill internal parasites.",
        bestSuitedFor = "Forage grasses (Napier, Rhodes grass), corn, and pre-plant incorporation in vegetable beds."
    ),
    VERMICOMPOST(
        title = "Vermicompost (Worm Castings)",
        dryMatterPercent = 50.0,
        totalNPercentDry = 2.2,
        totalP2O5PercentDry = 1.8,
        totalK2OPercentDry = 1.6,
        year1NAvailabilityPercent = 40.0,
        year1P2O5AvailabilityPercent = 75.0,
        year1K2OAvailabilityPercent = 90.0,
        averageCnRatio = 12.0,
        chartColor = Color(0xFF33691E),
        description = "Biological gold standard: passed through the gut of Red Wigglers (Eisenia fetida). Loaded with plant growth hormones (auxins, gibberellins) and disease-suppressive microbes.",
        bestSuitedFor = "Seedling nurseries, transplanting holes, greenhouse horticulture, strawberries, and commercial cut flowers."
    ),
    AEROBIC_COMPOST(
        title = "Thermal Aerobic Farm Compost",
        dryMatterPercent = 60.0,
        totalNPercentDry = 1.8,
        totalP2O5PercentDry = 1.0,
        totalK2OPercentDry = 1.5,
        year1NAvailabilityPercent = 25.0,
        year1P2O5AvailabilityPercent = 55.0,
        year1K2OAvailabilityPercent = 80.0,
        averageCnRatio = 15.0,
        chartColor = Color(0xFF2E7D32),
        description = "Fully cured, humified plant biomass (cereal straw + green legumes + manure starter). Generates stable colloidal humus that persists for multiple seasons.",
        bestSuitedFor = "Annual baseline soil fertility renewal, sandy soil moisture retention, and zero-chemical organic farming."
    ),
    BOKASHI(
        title = "Fermented Bokashi EM Compost",
        dryMatterPercent = 55.0,
        totalNPercentDry = 2.4,
        totalP2O5PercentDry = 1.5,
        totalK2OPercentDry = 1.8,
        year1NAvailabilityPercent = 50.0,
        year1P2O5AvailabilityPercent = 70.0,
        year1K2OAvailabilityPercent = 85.0,
        averageCnRatio = 14.0,
        chartColor = Color(0xFF1565C0),
        description = "Anaerobically fermented organic matter with Effective Microorganisms (Lactic acid bacteria, yeast, photosynthetic bacteria). Fast nutrient mineralizer with minimal ammonia loss.",
        bestSuitedFor = "Intensive vegetable market gardening, quick soil microflora restoration, and heavy root-feeder fertilization."
    )
}

/**
 * Raw organic material used in compost heaps with Carbon:Nitrogen ratios and descriptions.
 */
data class CompostRawMaterial(
    val name: String,
    val category: String, // "Brown (Carbon-Rich)" or "Green (Nitrogen-Rich)"
    val cnRatio: String,
    val moistureContent: String,
    val decompositionSpeed: String,
    val roleAndPrecaution: String
)

/**
 * Standard Operating Procedure (SOP) for a specific composting method.
 */
data class CompostingMethodGuide(
    val id: String,
    val methodName: String,
    val durationText: String,
    val difficultyLevel: String,
    val targetTemperatureRange: String,
    val turningSchedule: String,
    val stepByStepProtocol: List<String>,
    val managementRules: List<String>,
    val maturityChecklist: List<String>
)

/**
 * Crop organic manure target recommendation data.
 */
data class CropManureSpec(
    val cropId: String,
    val cropName: String,
    val category: String,
    val targetYieldPerHectare: String,
    val baseTargetNitrogenKgPerHa: Double,
    val baseTargetP2O5KgPerHa: Double,
    val baseTargetK2OKgPerHa: Double,
    val standardSpacingCm: String,
    val plantPopulationPerHa: Int,
    val recommendedApplicationMethod: String
)

/**
 * Output calculation result of organic manure tonnage required.
 */
data class ManureTonnageResult(
    val cropName: String,
    val manureType: ManureType,
    val areaHectares: Double,
    val areaAcres: Double,
    val totalTonsRequiredHectare: Double,
    val totalTonsRequiredAcre: Double,
    val total50kgBagsHectare: Int,
    val total50kgBagsAcre: Int,
    val rateKgPerPlantHole: Double,
    val rateKgPerSquareMeter: Double,
    val availableNSuppliedKgHa: Double,
    val availableP2O5SuppliedKgHa: Double,
    val availableK2OSuppliedKgHa: Double,
    val totalDryMatterAddedTons: Double,
    val calculationSteps: List<String>,
    val agronomicApplicationTips: List<String>
)
