package com.example.model.postharvest

data class CropStorageStandard(
    val id: String,
    val cropName: String,
    val maxSafeMoisturePercent: Double,
    val typicalHarvestMoisturePercent: Double,
    val bulkDensityKgPerCubicMeter: Double,
    val dryingGuide: String,
    val aflatoxinRiskLevel: String
)

data class DryingCalculationResult(
    val cropName: String,
    val wetWeightKg: Double,
    val initialMoisturePercent: Double,
    val targetMoisturePercent: Double,
    val finalDryWeightKg: Double,
    val waterLossKg: Double,
    val shrinkagePercent: Double,
    val pics90KgBagsNeeded: Int,
    val metalSilo500KgNeeded: Int,
    val isSafeToStore: Boolean,
    val advisoryMessage: String
)

data class StoragePestProfile(
    val id: String,
    val commonName: String,
    val scientificName: String,
    val targetCommodities: List<String>,
    val damageDescription: String,
    val culturalAndHermeticControl: String,
    val chemicalFumigationSop: String
)
