package com.example.data.alerts

import com.example.data.poultry.PoultryManagementCatalog
import com.example.data.weather.CompleteWeatherResult
import com.example.model.SprayStatus
import com.example.model.alerts.AlertUrgency
import com.example.model.alerts.PhiSafetyStatus
import com.example.model.alerts.PoultryBatchTracker
import com.example.model.alerts.PoultryVaccineAlert
import com.example.model.alerts.SprayPhiAlertStatus
import com.example.model.alerts.SprayPhiLog
import com.example.model.alerts.WeatherFarmAlert
import com.example.model.poultry.PoultryFlockType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.ceil

object FarmReminderEngine {

    private const val DAY_MS = 24L * 60L * 60L * 1000L

    fun formatDate(millis: Long): String {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return sdf.format(Date(millis))
    }

    fun evaluatePhiAlert(
        log: SprayPhiLog,
        nowMillis: Long = System.currentTimeMillis()
    ): SprayPhiAlertStatus {
        val safeAt = log.sprayedAtMillis + log.phiDays.coerceAtLeast(0) * DAY_MS
        val diffMs = safeAt - nowMillis
        val hoursLeft = if (diffMs <= 0L) 0L else ceil(diffMs.toDouble() / (60.0 * 60.0 * 1000.0)).toLong()
        val daysLeft = if (diffMs <= 0L) 0 else ceil(diffMs.toDouble() / DAY_MS.toDouble()).toInt()

        val status = when {
            daysLeft <= 0 -> PhiSafetyStatus.SAFE_TO_HARVEST
            daysLeft <= 2 -> PhiSafetyStatus.CAUTION_NEAR_SAFE
            else -> PhiSafetyStatus.DO_NOT_HARVEST
        }

        return SprayPhiAlertStatus(
            log = log,
            safeHarvestAtMillis = safeAt,
            daysRemaining = daysLeft,
            hoursRemaining = hoursLeft,
            safetyStatus = status,
            safeDateFormatted = formatDate(safeAt)
        )
    }

    fun evaluatePoultryBatchAlerts(
        batch: PoultryBatchTracker,
        nowMillis: Long = System.currentTimeMillis()
    ): List<PoultryVaccineAlert> {
        val flockEnum = try {
            PoultryFlockType.valueOf(batch.flockType)
        } catch (_: Exception) {
            PoultryFlockType.INDIGENOUS_IMPROVED
        }

        val currentAgeDays = ((nowMillis - batch.hatchDateMillis).coerceAtLeast(0L) / DAY_MS).toInt()
        val matchingSchedule = PoultryManagementCatalog.vaccinationSchedule.filter { entry ->
            entry.targetFlockType.contains(flockEnum)
        }

        return matchingSchedule.map { vax ->
            val dueIn = vax.ageDays - currentAgeDays
            val dueDateMs = batch.hatchDateMillis + vax.ageDays * DAY_MS
            val urgency = when {
                dueIn < -2 -> AlertUrgency.COMPLETED
                dueIn in -2..0 -> AlertUrgency.DUE_TODAY
                dueIn in 1..4 -> AlertUrgency.CRITICAL
                else -> AlertUrgency.UPCOMING
            }
            PoultryVaccineAlert(
                batchId = batch.id,
                batchName = batch.batchName,
                ageDays = currentAgeDays,
                targetAgeDays = vax.ageDays,
                dueInDays = dueIn,
                targetDisease = vax.targetDisease,
                vaccineName = vax.vaccineNameAndStrain,
                route = vax.route.displayName,
                urgency = urgency,
                dueDateFormatted = formatDate(dueDateMs)
            )
        }
    }

    fun evaluateWeatherAlerts(weather: CompleteWeatherResult?): List<WeatherFarmAlert> {
        if (weather == null) return emptyList()
        val alerts = mutableListOf<WeatherFarmAlert>()
        val cur = weather.currentWeather

        if (cur.relativeHumidity >= 78 && cur.temperatureCelsius in 14.0..26.0) {
            alerts.add(
                WeatherFarmAlert(
                    id = "wx_blight_risk",
                    title = "High Late Blight & Downy Mildew Risk (${weather.countyName})",
                    category = "Disease Trigger",
                    severity = AlertUrgency.CRITICAL,
                    recommendation = "Humidity is ${cur.relativeHumidity}% at ${cur.temperatureCelsius}°C. Scout Tomatoes/Potatoes/Onions at dawn and apply preventative Copper or Mancozeb before rain."
                )
            )
        }

        if (weather.advisory.sprayStatus == SprayStatus.HAZARDOUS || cur.windSpeedKmh > 15.0 || cur.precipitationMm > 0.5) {
            alerts.add(
                WeatherFarmAlert(
                    id = "wx_spray_hold",
                    title = "Hold Foliar & Herbicide Spraying",
                    category = "Spray Window",
                    severity = AlertUrgency.CRITICAL,
                    recommendation = "Wind ${cur.windSpeedKmh} km/h or active rain (${cur.precipitationMm} mm). Avoid drift and wash-off losses; wait for calm morning/evening window."
                )
            )
        } else {
            alerts.add(
                WeatherFarmAlert(
                    id = "wx_spray_good",
                    title = "Favorable Spraying Window Open",
                    category = "Spray Window",
                    severity = AlertUrgency.UPCOMING,
                    recommendation = "Low wind (${cur.windSpeedKmh} km/h) and dry canopy in ${weather.countyName}. Safe for precision knapsack spraying with AI/flat-fan nozzles."
                )
            )
        }

        if (cur.temperatureCelsius >= 29.0 && cur.relativeHumidity < 45) {
            alerts.add(
                WeatherFarmAlert(
                    id = "wx_heat_mites",
                    title = "Hot & Dry: Spider Mite / Thrips Surge & Heat Stress",
                    category = "Pest & Irrigation",
                    severity = AlertUrgency.DUE_TODAY,
                    recommendation = "High evaporation (${cur.temperatureCelsius}°C, ${cur.relativeHumidity}% RH). Irrigate early morning, mulch rows, and check undersides of leaves for Spider Mites."
                )
            )
        }

        return alerts
    }

    val defaultSamplePhiLogs: List<SprayPhiLog>
        get() {
            val now = System.currentTimeMillis()
            return listOf(
                SprayPhiLog(
                    id = "phi_sample_1",
                    cropName = "Tomato",
                    fieldBlock = "Greenhouse Block A",
                    productName = "Ridomil Gold MZ 68 WG",
                    activeIngredient = "Metalaxyl-M + Mancozeb",
                    phiDays = 7,
                    sprayedAtMillis = now - TimeUnit.DAYS.toMillis(4)
                ),
                SprayPhiLog(
                    id = "phi_sample_2",
                    cropName = "French Beans",
                    fieldBlock = "Plot B2 (Export)",
                    productName = "Thunder 145 OD",
                    activeIngredient = "Imidacloprid + Beta-Cyfluthrin",
                    phiDays = 14,
                    sprayedAtMillis = now - TimeUnit.DAYS.toMillis(5)
                )
            )
        }

    val defaultSamplePoultryBatches: List<PoultryBatchTracker>
        get() {
            val now = System.currentTimeMillis()
            return listOf(
                PoultryBatchTracker(
                    id = "flock_1",
                    batchName = "Kuroiler Batch #1",
                    flockType = PoultryFlockType.INDIGENOUS_IMPROVED.name,
                    birdCount = 250,
                    hatchDateMillis = now - TimeUnit.DAYS.toMillis(13)
                )
            )
        }
}
