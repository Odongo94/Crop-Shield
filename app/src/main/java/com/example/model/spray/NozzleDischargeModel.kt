package com.example.model.spray

/**
 * Data models for Spray Nozzle Discharge, Operating Pressure,
 * Tabular Discharge Matrix, and Field Spray Time Calibration.
 */

enum class IsoNozzleColor(
    val code: String, // e.g. "01", "02", "03"
    val colorName: String,
    val hexColor: Long,
    val nominalDischarge3BarLMin: Double, // Flow rate at 3.0 bar reference standard (ISO 10625)
    val typicalApplication: String
) {
    ORANGE_01(
        code = "01",
        colorName = "Orange",
        hexColor = 0xFFFFA500,
        nominalDischarge3BarLMin = 0.40,
        typicalApplication = "Ultra-fine droplet insecticides & specialized foliar fungicides"
    ),
    GREEN_015(
        code = "015",
        colorName = "Green",
        hexColor = 0xFF2E7D32,
        nominalDischarge3BarLMin = 0.60,
        typicalApplication = "Foliar fungicides & contact insecticides; low volume spraying"
    ),
    YELLOW_02(
        code = "02",
        colorName = "Yellow",
        hexColor = 0xFFFBC02D,
        nominalDischarge3BarLMin = 0.80,
        typicalApplication = "Standard knapsack spraying for horticulture, post-emergence herbicides"
    ),
    LILAC_025(
        code = "025",
        colorName = "Lilac / Purple",
        hexColor = 0xFFBA68C8,
        nominalDischarge3BarLMin = 1.00,
        typicalApplication = "Intermediate droplet systemic fungicides & horticultural sprays"
    ),
    BLUE_03(
        code = "03",
        colorName = "Blue",
        hexColor = 0xFF1976D2,
        nominalDischarge3BarLMin = 1.20,
        typicalApplication = "Most common universal nozzle for broadacre cereals, herbicides, and fungicides"
    ),
    RED_04(
        code = "04",
        colorName = "Red",
        hexColor = 0xFFD32F2F,
        nominalDischarge3BarLMin = 1.60,
        typicalApplication = "High volume systemic herbicides, liquid foliar fertilizers, soil pre-emergents"
    ),
    BROWN_05(
        code = "05",
        colorName = "Brown",
        hexColor = 0xFF795548,
        nominalDischarge3BarLMin = 2.00,
        typicalApplication = "High-volume pre-emergence soil herbicides & soil drenching"
    ),
    GRAY_06(
        code = "06",
        colorName = "Gray",
        hexColor = 0xFF757575,
        nominalDischarge3BarLMin = 2.40,
        typicalApplication = "Very high volume field sprayers, orchard air-blast rigs, soil conditioning"
    ),
    WHITE_08(
        code = "08",
        colorName = "White",
        hexColor = 0xFFB0BEC5,
        nominalDischarge3BarLMin = 3.20,
        typicalApplication = "Liquid fertilizer stream bars, heavy carrier volumes, industrial orchards"
    ),
    LIGHT_BLUE_10(
        code = "10",
        colorName = "Light Blue",
        hexColor = 0xFF0288D1,
        nominalDischarge3BarLMin = 4.00,
        typicalApplication = "Extreme high-volume flood spraying & liquid slurry topdressing"
    )
}

enum class SprayNozzleType(
    val title: String,
    val dropletSpectrum: String,
    val driftRisk: String,
    val bestUses: String
) {
    STANDARD_FLAT_FAN("Standard Flat Fan (110° / 80°)", "Medium to Fine", "Moderate", "Universal foliar fungicide, insecticide, and contact herbicides"),
    AIR_INDUCTION_LOW_DRIFT("Air Induction / Low Drift (AI)", "Coarse to Very Coarse (Air-filled)", "Extremely Low", "Systemic herbicides (Glyphosate, 2,4-D), windy field spraying"),
    HOLLOW_CONE("Hollow Cone (Disc & Core)", "Fine to Very Fine", "High if windy", "Dense crop canopies (Tomatoes, Coffee, Citrus, Potatoes) for 360° coverage"),
    FLOOD_DEFLECTOR("Flood / Deflector Jet", "Very Coarse", "Low", "Low-pressure soil herbicides, band spraying between rows")
}

data class NozzlePressureDischargeRow(
    val pressureBars: Double,
    val flowRateLitersPerMin: Double,
    val flowRateLitersPerHour: Double,
    val volumeInSpecifiedMinutes: Double,
    val volumeInSpecifiedHours: Double,
    val minutesToEmpty20LTank: Double,
    val minutesToEmpty16LTank: Double
)

data class NozzleDischargeResult(
    val nozzle: IsoNozzleColor,
    val nozzleType: SprayNozzleType,
    val selectedPressureBars: Double,
    val currentFlowRateLMin: Double,
    val currentFlowRateLHour: Double,
    val customDurationMinutes: Double,
    val volumeInMinutesLiters: Double,
    val customDurationHours: Double,
    val volumeInHoursLiters: Double,
    val numberOfNozzlesOnBoom: Int,
    val totalBoomDischargeLMin: Double,
    val totalBoomDischargeLHour: Double,
    val walkingSpeedKmH: Double,
    val nozzleSpacingMeters: Double,
    val applicationRateLitersPerHectare: Double,
    val applicationRateLitersPerAcre: Double,
    val minutesToSprayOneHectare: Double,
    val minutesToSprayOneAcre: Double,
    val dischargeTable: List<NozzlePressureDischargeRow>,
    val mathematicalFormulaSteps: List<String>
)
