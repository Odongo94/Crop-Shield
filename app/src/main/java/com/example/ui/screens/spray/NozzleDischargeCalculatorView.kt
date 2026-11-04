package com.example.ui.screens.spray

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.spray.NozzleCalibrationCalculator
import com.example.model.spray.IsoNozzleColor
import com.example.model.spray.SprayNozzleType

/**
 * Interactive Spray Nozzle Discharge, Operating Pressure, Tabular Flow Matrix,
 * and Field Spray Time Calibration View.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NozzleDischargeCalculatorView(
    modifier: Modifier = Modifier
) {
    var selectedNozzle by remember { mutableStateOf(IsoNozzleColor.YELLOW_02) }
    var selectedNozzleType by remember { mutableStateOf(SprayNozzleType.STANDARD_FLAT_FAN) }
    var pressureBars by remember { mutableDoubleStateOf(3.0) }
    var durationMinutes by remember { mutableDoubleStateOf(15.0) }
    var durationHours by remember { mutableDoubleStateOf(1.0) }
    var numberOfNozzles by remember { mutableIntStateOf(1) } // 1 for knapsack lance, 4 - 48 for tractor boom
    var walkingSpeedKmH by remember { mutableDoubleStateOf(4.0) }
    var nozzleSpacingMeters by remember { mutableDoubleStateOf(0.50) }

    // Calibration wear check state
    var measuredMlInMinute by remember { mutableStateOf("800") }
    var showMathDerivation by remember { mutableStateOf(false) }

    val result by remember(
        selectedNozzle,
        selectedNozzleType,
        pressureBars,
        durationMinutes,
        durationHours,
        numberOfNozzles,
        walkingSpeedKmH,
        nozzleSpacingMeters
    ) {
        derivedStateOf {
            NozzleCalibrationCalculator.calculateCompleteNozzleDischarge(
                nozzle = selectedNozzle,
                nozzleType = selectedNozzleType,
                selectedPressureBars = pressureBars,
                customDurationMinutes = durationMinutes,
                customDurationHours = durationHours,
                numberOfNozzlesOnBoom = numberOfNozzles,
                walkingSpeedKmH = walkingSpeedKmH,
                nozzleSpacingMeters = nozzleSpacingMeters
            )
        }
    }

    val wearCheckResult by remember(selectedNozzle, pressureBars, measuredMlInMinute) {
        derivedStateOf {
            val ml = measuredMlInMinute.toDoubleOrNull() ?: 0.0
            NozzleCalibrationCalculator.checkNozzleWear(
                nozzle = selectedNozzle,
                pressureBars = pressureBars,
                measuredMlInOneMinute = ml
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("nozzle_discharge_calculator_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Spray Nozzle Discharge & Time Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Calculate exact nozzle discharge in L/min & L/hour at variable operating pressures (1.5 - 5.0 bar), estimate spray times for 16L/20L knapsacks and tractor booms, and verify nozzle orifice wear.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        // Section 1: ISO Nozzle Color Selection
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "1. Select ISO 10625 Nozzle Size",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Standard color coding indicates nominal discharge rate at 3.0 bar reference pressure.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IsoNozzleColor.values().forEach { nozzle ->
                        val isSelected = selectedNozzle == nozzle
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Color(nozzle.hexColor)
                            ),
                            modifier = Modifier
                                .clickable { selectedNozzle = nozzle }
                                .testTag("nozzle_chip_${nozzle.code}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(Color(nozzle.hexColor))
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${nozzle.colorName} (${nozzle.code})",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                // Selected Nozzle Info banner
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "${selectedNozzle.colorName} ${selectedNozzle.code} (Nominal: ${"%.2f".format(selectedNozzle.nominalDischarge3BarLMin)} L/min at 3.0 bar)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = selectedNozzle.typicalApplication,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Operating Pressure (bars) & Duration Sliders
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "2. Operating Pressure & Spray Time Inputs",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Pressure Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Operating Pressure (Bars):",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "${"%.1f".format(pressureBars)} bar (${"%.0f".format(pressureBars * 14.5038)} PSI)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                Slider(
                    value = pressureBars.toFloat(),
                    onValueChange = { pressureBars = (it * 2).toInt() / 2.0 },
                    valueRange = 1.0f..6.0f,
                    steps = 9,
                    modifier = Modifier.fillMaxWidth().testTag("slider_pressure_bars")
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1.0 bar (Low drift)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("3.0 bar (ISO Standard)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("6.0 bar (High atomization)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(14.dp))

                // Custom Time Inputs: Minutes and Hours
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Discharge Time (Minutes):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${"%.0f".format(durationMinutes)} min",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Slider(
                            value = durationMinutes.toFloat(),
                            onValueChange = { durationMinutes = it.toDouble() },
                            valueRange = 1.0f..60.0f,
                            modifier = Modifier.fillMaxWidth().testTag("slider_duration_minutes")
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Discharge Time (Hours):",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "${"%.1f".format(durationHours)} hr",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Slider(
                            value = durationHours.toFloat(),
                            onValueChange = { durationHours = it.toDouble() },
                            valueRange = 0.5f..8.0f,
                            steps = 14,
                            modifier = Modifier.fillMaxWidth().testTag("slider_duration_hours")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                // Boom Nozzles & Speed Settings
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        1 to "Single Knapsack (1 Nozzle)",
                        4 to "4-Nozzle Boom",
                        8 to "8-Nozzle Boom",
                        24 to "24-Nozzle Boom (12m)"
                    ).forEach { (count, label) ->
                        FilterChip(
                            selected = numberOfNozzles == count,
                            onClick = { numberOfNozzles = count },
                            label = { Text(if (count == 1) "1 (Knapsack)" else "$count Nozzles", style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        }

        // Section 3: Primary Calculation KPIs
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Discharge & Field Spray Time Results",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                // KPI Grid Row 1: Flow Rates
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Single Nozzle Flow",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${"%.3f".format(result.currentFlowRateLMin)} L/min",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${"%.1f".format(result.currentFlowRateLHour)} Liters / hour",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = if (numberOfNozzles > 1) "Total Boom ($numberOfNozzles Nozzles)" else "Tank Empty Time",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            if (numberOfNozzles > 1) {
                                Text(
                                    text = "${"%.2f".format(result.totalBoomDischargeLMin)} L/min",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "${"%.1f".format(result.totalBoomDischargeLHour)} Liters / hour",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            } else {
                                Text(
                                    text = "${"%.1f".format(20.0 / result.currentFlowRateLMin)} min (20L)",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = "${"%.1f".format(16.0 / result.currentFlowRateLMin)} min to empty 16L",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // KPI Grid Row 2: Volume in User-specified Minutes and Hours
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Timer, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "In ${"%.0f".format(durationMinutes)} Minutes",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${"%.2f".format(result.volumeInMinutesLiters * numberOfNozzles)} L",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Discharged volume",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "In ${"%.1f".format(durationHours)} Hour(s)",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${"%.1f".format(result.volumeInHoursLiters * numberOfNozzles)} L",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = "Discharged volume",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Field Area Coverage
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Field Application Rate & Spraying Time (at ${"%.1f".format(walkingSpeedKmH)} km/h speed)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Application Rate:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${"%.1f".format(result.applicationRateLitersPerHectare)} L/ha (${"%.1f".format(result.applicationRateLitersPerAcre)} L/acre)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Spray Time (1 Hectare):", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${"%.1f".format(result.minutesToSprayOneHectare)} min (${"%.2f".format(result.minutesToSprayOneHectare / 60.0)} hrs)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }

        // Section 4: Tabular Discharge Matrix (Requested by User)
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TableChart,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pressure Discharge Matrix Table",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "Discharge table for ${selectedNozzle.colorName} (${selectedNozzle.code}) across standard pressures, showing volume in minutes and hours to help in calculating field spray time.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable Table
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                        .horizontalScroll(rememberScrollState())
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        // Header Row
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                                .padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Pressure", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(70.dp))
                            Text("Discharge (L/min)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(105.dp))
                            Text("Discharge (L/h)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(95.dp))
                            Text("In ${"%.0f".format(durationMinutes)}m (L)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(95.dp))
                            Text("In ${"%.1f".format(durationHours)}h (L)", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(95.dp))
                            Text("Empty 20L", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(85.dp))
                            Text("Empty 16L", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.width(85.dp))
                        }

                        // Data Rows
                        result.dischargeTable.forEachIndexed { index, row ->
                            val isCurrentSelectedPressure = kotlin.math.abs(row.pressureBars - pressureBars) < 0.1
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isCurrentSelectedPressure) MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                        else if (index % 2 == 0) Color.Transparent
                                        else MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${"%.1f".format(row.pressureBars)} bar",
                                    fontWeight = if (isCurrentSelectedPressure) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCurrentSelectedPressure) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.width(70.dp)
                                )
                                Text(
                                    text = "${"%.3f".format(row.flowRateLitersPerMin)}",
                                    fontWeight = if (isCurrentSelectedPressure) FontWeight.Bold else FontWeight.Normal,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.width(105.dp)
                                )
                                Text(
                                    text = "${"%.1f".format(row.flowRateLitersPerHour)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.width(95.dp)
                                )
                                Text(
                                    text = "${"%.2f".format(row.volumeInSpecifiedMinutes)}",
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.width(95.dp)
                                )
                                Text(
                                    text = "${"%.1f".format(row.volumeInSpecifiedHours)}",
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.width(95.dp)
                                )
                                Text(
                                    text = "${"%.1f".format(row.minutesToEmpty20LTank)} min",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.width(85.dp)
                                )
                                Text(
                                    text = "${"%.1f".format(row.minutesToEmpty16LTank)} min",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.width(85.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 5: Nozzle Calibration & Orifice Wear Check
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Build,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nozzle Orifice Wear Check (ISO 10% Rule)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Text(
                    text = "Catch water from your sprayer nozzle in a measuring cylinder for exactly 60 seconds at operating pressure (${"%.1f".format(pressureBars)} bar). Enter measured volume below to detect nozzle wear.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = measuredMlInMinute,
                        onValueChange = { measuredMlInMinute = it },
                        label = { Text("Measured Volume (mL / min)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f).testTag("input_measured_ml")
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (wearCheckResult.isWorn) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text("Wear Deviation", style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = "${if (wearCheckResult.differencePercent >= 0) "+" else ""}${"%.1f".format(wearCheckResult.differencePercent)}%",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (wearCheckResult.isWorn) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                // Recommendation Box
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (wearCheckResult.isWorn) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (wearCheckResult.isWorn) Icons.Default.Warning else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (wearCheckResult.isWorn) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = wearCheckResult.recommendation,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (wearCheckResult.isWorn) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section 6: Mathematical Formula Derivation Dropdown
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showMathDerivation = !showMathDerivation },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Engineering Formulas & Mathematical Proofs",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Icon(
                        imageVector = if (showMathDerivation) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                AnimatedVisibility(visible = showMathDerivation) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        result.mathematicalFormulaSteps.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
