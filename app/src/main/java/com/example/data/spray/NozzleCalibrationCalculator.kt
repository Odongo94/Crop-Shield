package com.example.data.spray

import com.example.model.spray.IsoNozzleColor
import com.example.model.spray.NozzleDischargeResult
import com.example.model.spray.NozzlePressureDischargeRow
import com.example.model.spray.SprayNozzleType
import kotlin.math.sqrt

/**
 * Precision Mathematical Engine for Spray Nozzle Discharge, Operating Pressure,
 * Multi-Pressure Tabular Discharge Matrix, and Field Spray Time Calibration.
 */
object NozzleCalibrationCalculator {

    val standardPressuresBars: List<Double> = listOf(1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 5.0)

    /**
     * Compute nozzle flow rate at any pressure P (bar) from ISO nominal rating at 3.0 bar.
     * Formula: q = q0 * sqrt(P / 3.0)
     */
    fun calculateFlowRate(nozzle: IsoNozzleColor, pressureBars: Double): Double {
        if (pressureBars <= 0.0) return 0.0
        val pressureRatio = pressureBars / 3.0
        return nozzle.nominalDischarge3BarLMin * sqrt(pressureRatio)
    }

    /**
     * Build the multi-pressure comparison table for the selected nozzle and user time intervals.
     */
    fun generateDischargeTable(
        nozzle: IsoNozzleColor,
        customDurationMinutes: Double = 15.0,
        customDurationHours: Double = 1.0
    ): List<NozzlePressureDischargeRow> {
        return standardPressuresBars.map { pressure ->
            val flowLMin = calculateFlowRate(nozzle, pressure)
            val flowLHour = flowLMin * 60.0
            val volMins = flowLMin * customDurationMinutes
            val volHours = flowLHour * customDurationHours
            val timeToEmpty20L = if (flowLMin > 0) 20.0 / flowLMin else 0.0
            val timeToEmpty16L = if (flowLMin > 0) 16.0 / flowLMin else 0.0

            NozzlePressureDischargeRow(
                pressureBars = pressure,
                flowRateLitersPerMin = flowLMin,
                flowRateLitersPerHour = flowLHour,
                volumeInSpecifiedMinutes = volMins,
                volumeInSpecifiedHours = volHours,
                minutesToEmpty20LTank = timeToEmpty20L,
                minutesToEmpty16LTank = timeToEmpty16L
            )
        }
    }

    /**
     * Complete full calibration calculation including boom volume, application rate (L/ha, L/acre),
     * and total spray time required for field area.
     */
    fun calculateCompleteNozzleDischarge(
        nozzle: IsoNozzleColor = IsoNozzleColor.YELLOW_02,
        nozzleType: SprayNozzleType = SprayNozzleType.STANDARD_FLAT_FAN,
        selectedPressureBars: Double = 3.0,
        customDurationMinutes: Double = 15.0,
        customDurationHours: Double = 1.0,
        numberOfNozzlesOnBoom: Int = 1, // 1 for single knapsack lance, 4 - 48 for tractor boom
        walkingSpeedKmH: Double = 4.0, // Typical comfortable walking speed 1.1 m/s = 4.0 km/h
        nozzleSpacingMeters: Double = 0.50 // Standard 50 cm nozzle spacing
    ): NozzleDischargeResult {
        val singleFlowLMin = calculateFlowRate(nozzle, selectedPressureBars)
        val singleFlowLHour = singleFlowLMin * 60.0

        val volMins = singleFlowLMin * customDurationMinutes
        val volHours = singleFlowLHour * customDurationHours

        val totalBoomDischargeLMin = singleFlowLMin * numberOfNozzlesOnBoom
        val totalBoomDischargeLHour = singleFlowLHour * numberOfNozzlesOnBoom

        // Application rate formula: L/ha = (q_nozzle_L_min * 600) / (Speed_km_h * Spacing_m)
        val applicationRateLHa = if (walkingSpeedKmH > 0.0 && nozzleSpacingMeters > 0.0) {
            (singleFlowLMin * 600.0) / (walkingSpeedKmH * nozzleSpacingMeters)
        } else {
            0.0
        }
        val applicationRateLAcre = applicationRateLHa / 2.47105

        // Spray time to cover 1 hectare = Total volume required / Boom discharge per minute
        val minutesPerHectare = if (totalBoomDischargeLMin > 0.0) {
            applicationRateLHa / totalBoomDischargeLMin
        } else {
            0.0
        }
        val minutesPerAcre = minutesPerHectare / 2.47105

        val table = generateDischargeTable(
            nozzle = nozzle,
            customDurationMinutes = customDurationMinutes,
            customDurationHours = customDurationHours
        )

        val mathSteps = listOf(
            "1. Physical Orifice Flow Law: q = q₀ × √(P / P₀) where P₀ = 3.0 bar",
            "   Calculated q = ${"%.2f".format(nozzle.nominalDischarge3BarLMin)} × √(${"%.1f".format(selectedPressureBars)} / 3.0) = ${"%.3f".format(singleFlowLMin)} L/min (${"%.2f".format(singleFlowLHour)} L/h)",
            "2. Total Boom Discharge ($numberOfNozzlesOnBoom nozzle(s)): Q_boom = ${"%.3f".format(totalBoomDischargeLMin)} L/min (${"%.2f".format(totalBoomDischargeLHour)} L/h)",
            "3. Time Volume Discharged: In ${"%.1f".format(customDurationMinutes)} min = ${"%.2f".format(volMins)} Liters | In ${"%.1f".format(customDurationHours)} hr = ${"%.2f".format(volHours)} Liters",
            "4. Field Application Rate: Application Rate = (q × 600) / (Speed × Spacing)",
            "   Application Rate = (${"%.3f".format(singleFlowLMin)} × 600) / (${"%.1f".format(walkingSpeedKmH)} km/h × ${"%.2f".format(nozzleSpacingMeters)} m) = ${"%.1f".format(applicationRateLHa)} L/ha (${"%.1f".format(applicationRateLAcre)} L/acre)",
            "5. Field Spray Time: 1 Hectare requires ${"%.1f".format(minutesPerHectare)} minutes (${"%.2f".format(minutesPerHectare / 60.0)} hrs) | 1 Acre requires ${"%.1f".format(minutesPerAcre)} minutes"
        )

        return NozzleDischargeResult(
            nozzle = nozzle,
            nozzleType = nozzleType,
            selectedPressureBars = selectedPressureBars,
            currentFlowRateLMin = singleFlowLMin,
            currentFlowRateLHour = singleFlowLHour,
            customDurationMinutes = customDurationMinutes,
            volumeInMinutesLiters = volMins,
            customDurationHours = customDurationHours,
            volumeInHoursLiters = volHours,
            numberOfNozzlesOnBoom = numberOfNozzlesOnBoom,
            totalBoomDischargeLMin = totalBoomDischargeLMin,
            totalBoomDischargeLHour = totalBoomDischargeLHour,
            walkingSpeedKmH = walkingSpeedKmH,
            nozzleSpacingMeters = nozzleSpacingMeters,
            applicationRateLitersPerHectare = applicationRateLHa,
            applicationRateLitersPerAcre = applicationRateLAcre,
            minutesToSprayOneHectare = minutesPerHectare,
            minutesToSprayOneAcre = minutesPerAcre,
            dischargeTable = table,
            mathematicalFormulaSteps = mathSteps
        )
    }

    /**
     * Calibration wear check: Evaluate whether a physical nozzle is worn out.
     * International ISO / BCPC standard: Replace nozzle if flow exceeds nominal by > 10%.
     */
    data class NozzleWearCheckResult(
        val nominalLMin: Double,
        val measuredLMin: Double,
        val differencePercent: Double,
        val isWorn: Boolean,
        val recommendation: String
    )

    fun checkNozzleWear(
        nozzle: IsoNozzleColor,
        pressureBars: Double,
        measuredMlInOneMinute: Double
    ): NozzleWearCheckResult {
        val nominalLMin = calculateFlowRate(nozzle, pressureBars)
        val measuredLMin = measuredMlInOneMinute / 1000.0
        val diffPercent = if (nominalLMin > 0.0) {
            ((measuredLMin - nominalLMin) / nominalLMin) * 100.0
        } else 0.0

        val isWorn = diffPercent > 10.0
        val recommendation = when {
            diffPercent > 10.0 -> "REPLACE NOZZLE IMMEDIATELY: Orifice is worn out (+${"%.1f".format(diffPercent)}% above nominal). This causes chemical overdosing, scorch risk, streak patterns, and pesticide wastage."
            diffPercent < -10.0 -> "CLOGGED / BLOCKED NOZZLE: Flow is ${"%.1f".format(diffPercent)}% below nominal. Clean nozzle tip using a soft toothbrush and clean water (never use a wire or pin which damages orifice)."
            else -> "NOZZLE IN GOOD CONDITION: Discharge is within ±10% acceptable calibration tolerance (${"%.1f".format(diffPercent)}% deviation)."
        }

        return NozzleWearCheckResult(
            nominalLMin = nominalLMin,
            measuredLMin = measuredLMin,
            differencePercent = diffPercent,
            isWorn = isWorn,
            recommendation = recommendation
        )
    }
}
