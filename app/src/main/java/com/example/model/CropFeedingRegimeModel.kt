package com.example.model

import androidx.compose.ui.graphics.Color

/**
 * Detailed feeding and fertigation schedule for a specific phenological growth stage.
 */
data class CropFeedingStage(
    val stageName: String,
    val timingDaysAfterPlanting: String,
    val nutritionalGoal: String,
    val primaryNutrientsNeeded: String, // e.g., "High P, Starter N, Zinc"
    val standardSoilApplication: String, // e.g., "DAP 50 kg/acre or NPK 17-17-17 75 kg/acre"
    val fertigationRecipeDrip: String, // e.g., "MAP (12-61-0) 2.5 kg/ha/day + Calcium Nitrate 3.0 kg/ha/day"
    val targetFertigationEC: String, // e.g., "1.2 – 1.5 mS/cm"
    val targetFertigationPH: String, // e.g., "5.8 – 6.2"
    val foliarBoosterSpray: String, // e.g., "Zinc + Boron amino acid chelate (2 ml/L)"
    val agronomicPrecautions: String
)

/**
 * Complete seasonal feeding regime and nutrient extraction model for a crop species.
 */
data class CropFeedingRegime(
    val cropId: String,
    val cropName: String,
    val category: String,
    val targetYieldPerHectare: String,
    // Nutrient removal per metric ton of saleable harvest
    val nitrogenRemovalPerTon: Double, // kg N / MT
    val phosphateRemovalPerTon: Double, // kg P2O5 / MT
    val potashRemovalPerTon: Double, // kg K2O / MT
    val calciumRemovalPerTon: Double, // kg CaO / MT
    val magnesiumRemovalPerTon: Double, // kg MgO / MT
    val optimalSoilPhRange: String,
    val maxTolerableSoilEC: String,
    val stages: List<CropFeedingStage>,
    val deficiencySymptomsAndCure: List<String>,
    val agronomistProTips: String
)
