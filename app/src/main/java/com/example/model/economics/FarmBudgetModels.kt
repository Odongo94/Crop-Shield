package com.example.model.economics

data class CropEconomicsPreset(
    val id: String,
    val cropName: String,
    val expectedYieldKgPerHa: Double,
    val typicalMarketPriceKesPerKg: Double,
    val seedCostKesPerHa: Double,
    val basalBags50KgPerHa: Double,
    val topdressBags50KgPerHa: Double,
    val chemicalSprayCostKesPerHa: Double,
    val organicManureTonnesPerHa: Double,
    val laborAndOpsCostKesPerHa: Double,
    val irrigationCostKesPerHa: Double,
    val notes: String
)

data class AgroDealerLineItem(
    val itemName: String,
    val quantityText: String,
    val estimatedCostKes: Double,
    val category: String
)

data class FarmEconomicsInput(
    val cropPreset: CropEconomicsPreset,
    val areaHectares: Double,
    val seedCostPerHa: Double,
    val fertilizerBagPriceKes: Double,
    val chemicalBudgetPerHa: Double,
    val manureCostPerTonne: Double,
    val laborCostPerHa: Double,
    val irrigationCostPerHa: Double,
    val expectedYieldKgPerHa: Double,
    val marketPriceKesPerKg: Double
)

data class FarmEconomicsResult(
    val areaHectares: Double,
    val areaAcres: Double,
    val totalSeedCostKes: Double,
    val totalFertilizerCostKes: Double,
    val totalChemicalCostKes: Double,
    val totalManureCostKes: Double,
    val totalLaborCostKes: Double,
    val totalIrrigationCostKes: Double,
    val totalInputCostKes: Double,
    val totalExpectedYieldKg: Double,
    val expectedGrossRevenueKes: Double,
    val netProfitKes: Double,
    val roiPercent: Double,
    val breakEvenPriceKesPerKg: Double,
    val breakEvenYieldKgTotal: Double,
    val shoppingList: List<AgroDealerLineItem>
)
