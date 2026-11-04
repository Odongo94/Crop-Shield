package com.example.model.poultry

/**
 * Data models for Poultry Management:
 * 1. Comprehensive Chick Vaccination Calendar & Disease Control
 * 2. Age-Stratified Precision Feeding Program & Nutritional Specifications
 */

enum class PoultryFlockType(val displayName: String, val typicalLifespanWeeks: Int) {
    LAYERS("Commercial Layers (Egg Production)", 76),
    BROILERS("Commercial Broilers (Meat Production)", 6),
    INDIGENOUS_IMPROVED("Improved Kienyeji / Dual-Purpose (KARI, Kuroiler, Sasso)", 52)
}

enum class VaccineAdministrationRoute(val displayName: String, val techniqueGuidance: String) {
    EYE_DROP(
        "Intraocular (Eye Drop)",
        "Hold chick horizontally. Administer exactly one calibrated drop (approx. 0.03 ml) into open eye. Allow chick to blink before release to ensure full ocular absorption."
    ),
    DRINKING_WATER(
        "Drinking Water",
        "Withdraw drinking water for 1.5 - 2 hours prior to vaccination so birds are thirsty. Add skimmed milk powder (2.5 g/liter) or commercial chlorine-neutralizing dye to protect viral titer. Entire volume must be consumed within 90 - 120 minutes away from direct sunlight."
    ),
    WING_WEB_STAB(
        "Wing Web Puncture",
        "Dip two-pronged grooved needle into reconstituted vaccine. Spread the wing and pierce through the membranous web, avoiding blood vessels, bones, and feathers. Check for 'take' nodule on day 7."
    ),
    SUBCUTANEOUS_INJECTION(
        "Subcutaneous (Under Neck Skin)",
        "Lift the skin at the lower third of the back of the neck forming a tent. Insert 19-gauge needle pointing away from the head to prevent spine/muscle puncture. Inject 0.2 - 0.5 ml."
    ),
    INTRAMUSCULAR_INJECTION(
        "Intramuscular (Breast / Thigh Muscle)",
        "Inject into the thickest part of the pectoral (breast) muscle or outer thigh muscle at a 45-degree angle, taking care not to hit the keel bone or femoral nerve."
    ),
    COARSE_SPRAY(
        "Coarse Spray (Hatchery / Day-Old)",
        "Use specialized droplet sprayer (100 - 150 microns) held 30 cm above chick boxes. Chicks inhale droplets and preen droplets off feathers, stimulating Upper Respiratory Tract immunity."
    )
}

data class ChickVaccineEntry(
    val id: String,
    val targetFlockType: Set<PoultryFlockType>,
    val ageDays: Int,
    val ageDisplay: String, // e.g. "Day 1 (Hatchery)", "Day 7", "Day 14", "Day 21", "Week 6"
    val targetDisease: String, // e.g. "Marek's Disease & Infectious Bronchitis (IB)"
    val vaccineNameAndStrain: String, // e.g. "HVT + Rispens & IB Primer / Ma5"
    val route: VaccineAdministrationRoute,
    val importanceAndPathology: String,
    val preparationAndHandlingSop: String,
    val contraindicationsAndAlerts: String
)

data class PoultryFeedAgeStage(
    val id: String,
    val targetFlockType: PoultryFlockType,
    val stageName: String, // e.g. "Pre-Starter Crumbs", "Starter Mash", "Grower Pellets", "Finisher", "Layer Phase 1"
    val ageStartDays: Int,
    val ageEndDays: Int,
    val ageDisplay: String, // e.g. "Day 1 - 10", "Day 11 - 28 (Week 2 - 4)", "Week 5 - 8"
    val crudeProteinPercent: Double, // CP %
    val metabolizableEnergyKcalKg: Double, // ME kcal/kg
    val calciumPercent: Double,
    val availablePhosphorusPercent: Double,
    val lysinePercent: Double,
    val methioninePercent: Double,
    val dailyFeedGramsPerBird: Double, // Expected daily intake
    val cumulativeFeedKgPerBird: Double, // Cumulative consumed up to end of this stage
    val dailyWaterMlPerBird: Double, // Approx 2 - 2.5x feed weight
    val targetBodyWeightGrams: Double, // Standard reference bodyweight
    val broodingTemperatureTargetCelsius: String, // e.g. "33 - 35°C (Day 1 - 3), reduce 2°C/week"
    val lightingHoursPerDay: String, // e.g. "23 hrs (Day 1-3) -> 12 hrs natural"
    val nutritionalRoleAndFocus: String,
    val managementAndFeederHygieneTips: List<String>
)

data class PoultryDailyPlanSummary(
    val flockType: PoultryFlockType,
    val flockSizeBirds: Int,
    val ageDays: Int,
    val currentFeedStage: PoultryFeedAgeStage,
    val dailyFeedTotalKg: Double,
    val dailyFeed50kgBags: Double,
    val cumulativeFeedConsumedKg: Double,
    val dailyWaterRequiredLiters: Double,
    val currentBroodingTemperature: String,
    val currentLightingProgram: String,
    val targetBodyWeightGrams: Double,
    val upcomingVaccinesNext7Days: List<ChickVaccineEntry>,
    val nextScheduledVaccine: ChickVaccineEntry?
)
