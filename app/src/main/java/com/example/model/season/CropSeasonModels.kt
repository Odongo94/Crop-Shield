package com.example.model.season

data class CropGrowthStageRule(
    val stageName: String,
    val startDap: Int,
    val endDap: Int,
    val keyActions: List<String>,
    val scoutingFocus: List<String>,
    val irrigationAndFeedingTip: String
)

data class CropLifecycleProfile(
    val cropName: String,
    val totalDaysToMaturity: Int,
    val stages: List<CropGrowthStageRule>
)

data class ActiveCropSeason(
    val id: String,
    val fieldName: String,
    val cropName: String,
    val variety: String,
    val areaHectares: Double,
    val plantedAtMillis: Long
)

data class CropSeasonStatus(
    val season: ActiveCropSeason,
    val daysAfterPlanting: Int,
    val totalCycleDays: Int,
    val progressFraction: Float,
    val currentStage: CropGrowthStageRule,
    val nextStageName: String?,
    val daysUntilNextStage: Int,
    val estimatedHarvestDateFormatted: String
)
