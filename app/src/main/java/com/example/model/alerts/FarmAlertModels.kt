package com.example.model.alerts

enum class PhiSafetyStatus(val label: String) {
    DO_NOT_HARVEST("DO NOT HARVEST"),
    CAUTION_NEAR_SAFE("ALMOST SAFE (1-2 DAYS)"),
    SAFE_TO_HARVEST("SAFE TO HARVEST")
}

data class SprayPhiLog(
    val id: String,
    val cropName: String,
    val fieldBlock: String,
    val productName: String,
    val activeIngredient: String,
    val phiDays: Int,
    val sprayedAtMillis: Long
)

data class SprayPhiAlertStatus(
    val log: SprayPhiLog,
    val safeHarvestAtMillis: Long,
    val daysRemaining: Int,
    val hoursRemaining: Long,
    val safetyStatus: PhiSafetyStatus,
    val safeDateFormatted: String
)

data class PoultryBatchTracker(
    val id: String,
    val batchName: String,
    val flockType: String, // "LAYERS", "BROILERS", "INDIGENOUS_IMPROVED"
    val birdCount: Int,
    val hatchDateMillis: Long
)

enum class AlertUrgency(val label: String) {
    CRITICAL("CRITICAL"),
    DUE_TODAY("DUE TODAY"),
    UPCOMING("UPCOMING"),
    COMPLETED("DONE")
}

data class PoultryVaccineAlert(
    val batchId: String,
    val batchName: String,
    val ageDays: Int,
    val targetAgeDays: Int,
    val dueInDays: Int,
    val targetDisease: String,
    val vaccineName: String,
    val route: String,
    val urgency: AlertUrgency,
    val dueDateFormatted: String
)

data class WeatherFarmAlert(
    val id: String,
    val title: String,
    val category: String,
    val severity: AlertUrgency,
    val recommendation: String
)
