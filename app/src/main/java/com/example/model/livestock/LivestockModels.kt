package com.example.model.livestock

enum class LivestockSpecies(val displayName: String, val gestationDays: Int, val dryOffDaysBeforeBirth: Int) {
    DAIRY_CATTLE("Dairy Cow (Friesian/Ayrshire/Cross)", 283, 60),
    BEEF_CATTLE("Beef Cattle (Boran/Zebu)", 283, 0),
    GOAT("Dairy & Meat Goat (Toggenburg/Galla)", 150, 45),
    SHEEP("Sheep (Dorper/Red Maasai)", 147, 40),
    SWINE("Swine / Pig (Large White/Landrace)", 114, 14)
}

enum class LivestockStage(val displayName: String) {
    LACTATING_HIGH("Lactating / High Production"),
    PREGNANT_DRY("Pregnant / Steaming Up"),
    GROWER_FINISHER("Grower / Finisher"),
    MAINTENANCE("Dry / Maintenance")
}

data class LivestockRationResult(
    val species: LivestockSpecies,
    val stage: LivestockStage,
    val bodyWeightKg: Double,
    val milkYieldLiters: Double,
    val dryMatterIntakeKgPerDay: Double,
    val freshForageKgPerDay: Double,
    val dairyMealOrConcentrateKgPerDay: Double,
    val mineralSupplementGramsPerDay: Double,
    val waterLitersPerDay: Double,
    val rationNotes: List<String>
)

data class LivestockHealthProtocol(
    val id: String,
    val species: Set<LivestockSpecies>,
    val category: String, // "Tick Control / Dipping", "Deworming", "Vaccination"
    val title: String,
    val intervalDays: Int,
    val activeDrugOrVaccine: String,
    val dosageAndRoute: String,
    val withdrawalPeriodNotes: String
)

data class LivestockBreedingRecord(
    val id: String,
    val animalNameOrTag: String,
    val species: LivestockSpecies,
    val serviceDateMillis: Long,
    val expectedDueDateMillis: Long,
    val dryOffOrSteamingDateMillis: Long,
    val daysRemaining: Int
)
