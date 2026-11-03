package com.example.ui.screens.soil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.soil.IrrigationWaterCatalog
import com.example.model.soil.CropGrowthStage
import com.example.model.soil.IrrigationMethod
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IrrigationRequirementCalculatorView(
    modifier: Modifier = Modifier
) {
    var selectedCropIndex by remember { mutableIntStateOf(0) }
    var selectedStage by remember { mutableStateOf(CropGrowthStage.MID_SEASON) }
    var selectedMethod by remember { mutableStateOf(IrrigationMethod.DRIP_LINE) }

    // Environmental parameters
    var referenceET0 by remember { mutableDoubleStateOf(5.0) } // mm/day
    var effectiveRainfall by remember { mutableDoubleStateOf(0.0) } // mm/day

    // Soil and Water EC
    var soilECe by remember { mutableDoubleStateOf(1.8) } // dS/m
    var waterECw by remember { mutableDoubleStateOf(0.8) } // dS/m

    // pH & Bicarbonates
    var soilPh by remember { mutableDoubleStateOf(6.4) }
    var waterPh by remember { mutableDoubleStateOf(7.6) }
    var waterBicarbonates by remember { mutableDoubleStateOf(2.5) } // meq/L

    var showFormulas by remember { mutableStateOf(false) }

    val crop = IrrigationWaterCatalog.crops[selectedCropIndex.coerceIn(0, IrrigationWaterCatalog.crops.size - 1)]

    val result = remember(
        crop, selectedStage, referenceET0, effectiveRainfall, selectedMethod,
        soilECe, waterECw, soilPh, waterPh, waterBicarbonates
    ) {
        IrrigationWaterCatalog.calculateIrrigationRequirement(
            crop = crop,
            growthStage = selectedStage,
            referenceET0MmDay = referenceET0,
            effectiveRainfallMmDay = effectiveRainfall,
            irrigationMethod = selectedMethod,
            soilECe = soilECe,
            waterECw = waterECw,
            soilPh = soilPh,
            waterPh = waterPh,
            waterBicarbonatesMeqL = waterBicarbonates
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("irrigation_calculator_view"),
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
                        Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Irrigation Regime & Water Budget Calculator",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "FAO-56 ETc Model in line with Soil/Water EC Salinity & pH",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
                Text(
                    text = "Determines exact daily crop water requirement (mm/day and Liters/plant/day) adjusted for salt leaching fraction (LF) and irrigation water pH/bicarbonate acidification.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Crop Selection
        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "1. Select Crop Enterprise:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                FlowRow(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    IrrigationWaterCatalog.crops.forEachIndexed { index, c ->
                        FilterChip(
                            selected = selectedCropIndex == index,
                            onClick = { selectedCropIndex = index },
                            label = { Text(c.cropName.substringBefore("(").trim(), fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
                Text(
                    text = "Salinity Threshold (ECe): ${crop.soilSalinityThresholdECe} dS/m | Root Depth: ${crop.rootZoneDepthMeters}m | Optimal pH: ${crop.optimalSoilPhMin}–${crop.optimalSoilPhMax}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // Phenological Stage & Irrigation Method
        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "2. Growth Stage & Application Method:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Phenological Growth Stage:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 6.dp)
                )
                FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    CropGrowthStage.values().forEach { stage ->
                        FilterChip(
                            selected = selectedStage == stage,
                            onClick = { selectedStage = stage },
                            label = { Text(stage.title.substringBefore("/").trim(), fontSize = 11.sp) }
                        )
                    }
                }

                Text(
                    text = "Irrigation System & Efficiency:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 10.dp)
                )
                FlowRow(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    IrrigationMethod.values().forEach { method ->
                        FilterChip(
                            selected = selectedMethod == method,
                            onClick = { selectedMethod = method },
                            label = { Text("${method.title.substringBefore("/")} (${method.efficiencyPercent.roundToInt()}%)", fontSize = 11.sp) }
                        )
                    }
                }
            }
        }

        // Environmental, EC & pH Sliders
        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "3. Environmental Evaporation, Salinity (EC) & pH Inputs:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Reference ET0
                IrrigSliderRow(
                    label = "Reference Evapotranspiration (ET₀)",
                    value = referenceET0,
                    range = 2.0f..10.0f,
                    unit = "mm/day",
                    onValueChange = { referenceET0 = it }
                )

                // Effective Rainfall
                IrrigSliderRow(
                    label = "Effective Daily Rainfall (P_eff)",
                    value = effectiveRainfall,
                    range = 0.0f..15.0f,
                    unit = "mm/day",
                    onValueChange = { effectiveRainfall = it }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Soil ECe
                IrrigSliderRow(
                    label = "Soil Paste Salinity (EC_e)",
                    value = soilECe,
                    range = 0.2f..6.0f,
                    unit = "dS/m",
                    onValueChange = { soilECe = it }
                )

                // Water ECw
                IrrigSliderRow(
                    label = "Irrigation Water Salinity (EC_w)",
                    value = waterECw,
                    range = 0.1f..4.0f,
                    unit = "dS/m",
                    onValueChange = { waterECw = it }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Soil pH
                IrrigSliderRow(
                    label = "Soil pH",
                    value = soilPh,
                    range = 4.5f..8.5f,
                    unit = "pH",
                    onValueChange = { soilPh = it }
                )

                // Water pH
                IrrigSliderRow(
                    label = "Irrigation Water pH",
                    value = waterPh,
                    range = 6.0f..9.0f,
                    unit = "pH",
                    onValueChange = { waterPh = it }
                )

                // Water Bicarbonates
                IrrigSliderRow(
                    label = "Water Bicarbonates (HCO₃⁻)",
                    value = waterBicarbonates,
                    range = 0.2f..6.0f,
                    unit = "meq/L",
                    onValueChange = { waterBicarbonates = it }
                )
            }
        }

        // Primary Calculation Dashboard
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "GROSS IRRIGATION WATER REQUIREMENT (WITH LEACHING FRACTION)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = String.format(Locale.US, "%.1f", result.grossIrrigationDepthMmDay),
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = " mm / day",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                        Text(
                            text = "Crop ETc: ${String.format(Locale.US, "%.1f", result.cropEvapotranspirationETcMmDay)} mm/day (Kc = ${String.format(Locale.US, "%.2f", result.cropCoefficientKc)})",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${String.format(Locale.US, "%.2f", result.litersPerPlantPerDay)} L",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "per plant/day",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Field volumetric units
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Volume per Hectare:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = "${String.format(Locale.US, "%.1f", result.grossVolumeM3PerHaPerDay)} m³/ha/day",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(text = "(${(result.grossVolumeM3PerHaPerDay * 1000).roundToInt()} Liters)", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column {
                        Text(text = "Volume per Acre:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = "${String.format(Locale.US, "%.1f", result.grossVolumeM3PerAcrePerDay)} m³/acre/day",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(text = "(${(result.grossVolumeM3PerAcrePerDay * 1000).roundToInt()} Liters)", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Column {
                        Text(text = "Irrigation Interval:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            text = "Every ${result.recommendedIntervalDays} day(s)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Salinity & Leaching Fraction Evaluation
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = result.soilSalinityColor.copy(alpha = 0.12f)),
            border = BorderStroke(1.dp, result.soilSalinityColor.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Speed, contentDescription = null, tint = result.soilSalinityColor, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Salinity & Leaching Fraction (LF)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = result.soilSalinityColor
                        )
                    }
                    Surface(
                        color = result.soilSalinityColor,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "LF = ${String.format(Locale.US, "%.1f", result.leachingFractionPercent)}%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    text = result.soilSalinityAssessment,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // pH Interaction & Nutrient Availability Matrix
        OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Science, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Soil & Water pH Interaction with Nutrient Uptake:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(modifier = Modifier.padding(top = 6.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    result.phInteractionAndNutrientAvailability.forEach { note ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(text = note, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 15.sp)
                        }
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // Acidification Recipe
                Text(
                    text = "Drip Line Water Acidification & Scale Prevention:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
                Text(
                    text = result.waterAcidificationRecipe,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Mathematical Derivations Toggle
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Show Water Requirement Math (FAO-56)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Button(onClick = { showFormulas = !showFormulas }, modifier = Modifier.height(30.dp)) {
                        Text(if (showFormulas) "Hide" else "Show", fontSize = 10.sp)
                    }
                }
                AnimatedVisibility(visible = showFormulas) {
                    Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        result.stepByStepMathFormulas.forEach { step ->
                            Text(
                                text = step,
                                fontSize = 11.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IrrigSliderRow(
    label: String,
    value: Double,
    range: ClosedFloatingPointRange<Float>,
    unit: String,
    onValueChange: (Double) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 3.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(
                text = "${String.format(Locale.US, "%.1f", value)} $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toDouble()) },
            valueRange = range,
            modifier = Modifier.height(26.dp)
        )
    }
}
