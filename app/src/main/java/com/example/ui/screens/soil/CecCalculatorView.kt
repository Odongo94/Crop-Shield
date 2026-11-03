package com.example.ui.screens.soil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.example.data.soil.CecRebalanceCalculator
import com.example.model.soil.CecInputMode
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun CecCalculatorView(
    modifier: Modifier = Modifier
) {
    var inputMode by remember { mutableStateOf(CecInputMode.LAB_CATIONS) }

    // Lab cations in meq/100g (cmol/kg)
    var calciumMeq by remember { mutableDoubleStateOf(10.5) }
    var magnesiumMeq by remember { mutableDoubleStateOf(2.2) }
    var potassiumMeq by remember { mutableDoubleStateOf(0.6) }
    var sodiumMeq by remember { mutableDoubleStateOf(0.2) }
    var exchangeableAcidityMeq by remember { mutableDoubleStateOf(1.5) }
    var soilPh by remember { mutableDoubleStateOf(6.2) }

    // Texture inputs for estimate
    var sandPercent by remember { mutableDoubleStateOf(45.0) }
    var siltPercent by remember { mutableDoubleStateOf(35.0) }
    var clayPercent by remember { mutableDoubleStateOf(20.0) }
    var organicMatterPercent by remember { mutableDoubleStateOf(2.5) }

    var showFormulas by remember { mutableStateOf(false) }

    val result = remember(
        inputMode, calciumMeq, magnesiumMeq, potassiumMeq, sodiumMeq, exchangeableAcidityMeq, soilPh,
        sandPercent, siltPercent, clayPercent, organicMatterPercent
    ) {
        if (inputMode == CecInputMode.LAB_CATIONS) {
            CecRebalanceCalculator.calculateFromLabCations(
                calciumMeq = calciumMeq,
                magnesiumMeq = magnesiumMeq,
                potassiumMeq = potassiumMeq,
                sodiumMeq = sodiumMeq,
                exchangeableAcidityMeq = exchangeableAcidityMeq,
                soilPh = soilPh
            )
        } else {
            CecRebalanceCalculator.estimateFromSoilTexture(
                sandPercent = sandPercent,
                siltPercent = siltPercent,
                clayPercent = clayPercent,
                organicMatterPercent = organicMatterPercent,
                soilPh = soilPh
            )
        }
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("cec_calculator_view"),
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
                        Icons.Default.Science,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CEC & Cation Balance Calculator",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Colloidal Nutrient Reservoir & Albrecht BCSR Rebalancing",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
                Text(
                    text = "Cation Exchange Capacity (CEC) measures the soil's negative electrical charge capacity to hold and release positively charged mineral nutrients (Ca²⁺, Mg²⁺, K⁺) against leaching rains.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Input Mode Selector Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CecInputMode.values().forEach { mode ->
                FilterChip(
                    selected = inputMode == mode,
                    onClick = { inputMode = mode },
                    label = { Text(mode.title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Input Panel Card
        OutlinedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = if (inputMode == CecInputMode.LAB_CATIONS) "Laboratory Exchangeable Cations (meq/100g)" else "Soil Texture & Organic Matter Fractions",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = inputMode.subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (inputMode == CecInputMode.LAB_CATIONS) {
                    // Calcium Slider
                    CationSliderRow(
                        label = "Calcium (Ca²⁺)",
                        value = calciumMeq,
                        range = 0.5f..35.0f,
                        unit = "meq/100g",
                        color = Color(0xFF2E7D32),
                        onValueChange = { calciumMeq = it }
                    )
                    // Magnesium Slider
                    CationSliderRow(
                        label = "Magnesium (Mg²⁺)",
                        value = magnesiumMeq,
                        range = 0.2f..15.0f,
                        unit = "meq/100g",
                        color = Color(0xFF1565C0),
                        onValueChange = { magnesiumMeq = it }
                    )
                    // Potassium Slider
                    CationSliderRow(
                        label = "Potassium (K⁺)",
                        value = potassiumMeq,
                        range = 0.1f..6.0f,
                        unit = "meq/100g",
                        color = Color(0xFFE65100),
                        onValueChange = { potassiumMeq = it }
                    )
                    // Sodium Slider
                    CationSliderRow(
                        label = "Sodium (Na⁺)",
                        value = sodiumMeq,
                        range = 0.0f..6.0f,
                        unit = "meq/100g",
                        color = Color(0xFFD9534F),
                        onValueChange = { sodiumMeq = it }
                    )
                    // Exchangeable Acidity (Al + H)
                    CationSliderRow(
                        label = "Acidity (Al³⁺ + H⁺)",
                        value = exchangeableAcidityMeq,
                        range = 0.0f..10.0f,
                        unit = "meq/100g",
                        color = Color(0xFF7B1FA2),
                        onValueChange = { exchangeableAcidityMeq = it }
                    )
                } else {
                    // Texture sliders
                    CationSliderRow(
                        label = "Clay Content",
                        value = clayPercent,
                        range = 0.0f..80.0f,
                        unit = "%",
                        color = Color(0xFFD9534F),
                        onValueChange = { clayPercent = it }
                    )
                    CationSliderRow(
                        label = "Silt Content",
                        value = siltPercent,
                        range = 0.0f..80.0f,
                        unit = "%",
                        color = Color(0xFFE07A5F),
                        onValueChange = { siltPercent = it }
                    )
                    CationSliderRow(
                        label = "Sand Content",
                        value = sandPercent,
                        range = 0.0f..95.0f,
                        unit = "%",
                        color = Color(0xFFC9A227),
                        onValueChange = { sandPercent = it }
                    )
                    CationSliderRow(
                        label = "Soil Organic Matter (SOM)",
                        value = organicMatterPercent,
                        range = 0.2f..10.0f,
                        unit = "%",
                        color = Color(0xFF2E7D32),
                        onValueChange = { organicMatterPercent = it }
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // Soil pH Slider
                CationSliderRow(
                    label = "Soil pH (Water 1:2.5)",
                    value = soilPh,
                    range = 4.0f..8.5f,
                    unit = "pH",
                    color = if (soilPh < 5.5) Color(0xFFD9534F) else if (soilPh in 5.8..6.8) Color(0xFF2E7D32) else Color(0xFFF2A65A),
                    onValueChange = { soilPh = it }
                )
            }
        }

        // Primary Results Dashboard
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Main CEC Badge Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TOTAL CATION EXCHANGE CAPACITY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = String.format(Locale.US, "%.1f", result.totalCec),
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = result.rating.color
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "meq/100g (cmol/kg)",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }
                    }

                    Surface(
                        color = result.rating.color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, result.rating.color)
                    ) {
                        Text(
                            text = result.rating.title,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = result.rating.color,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                // Base Saturation % Meter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Base Saturation (BS%):",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${String.format(Locale.US, "%.1f", result.baseSaturationPercent)}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (result.baseSaturationPercent < 60) Color(0xFFD9534F) else Color(0xFF2E7D32)
                    )
                }

                LinearProgressIndicator(
                    progress = { (result.baseSaturationPercent / 100.0).toFloat().coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .padding(top = 4.dp),
                    color = if (result.baseSaturationPercent < 60) Color(0xFFD9534F) else Color(0xFF2E7D32),
                    trackColor = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    text = result.soilConditionSummary,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Cation Saturation Balance Breakdown (Albrecht System)
        OutlinedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Basic Cation Saturation Ratios (BCSR)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Comparison against Albrecht agronomic target ranges",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                CationBarItem(
                    name = "Calcium (Ca²⁺)",
                    actualPercent = result.caSaturationPercent,
                    idealMin = 65.0,
                    idealMax = 75.0,
                    barColor = Color(0xFF2E7D32)
                )

                CationBarItem(
                    name = "Magnesium (Mg²⁺)",
                    actualPercent = result.mgSaturationPercent,
                    idealMin = 10.0,
                    idealMax = 15.0,
                    barColor = Color(0xFF1565C0)
                )

                CationBarItem(
                    name = "Potassium (K⁺)",
                    actualPercent = result.kSaturationPercent,
                    idealMin = 3.0,
                    idealMax = 5.0,
                    barColor = Color(0xFFE65100)
                )

                CationBarItem(
                    name = "Sodium (Na⁺ - ESP)",
                    actualPercent = result.naSaturationPercent,
                    idealMin = 0.5,
                    idealMax = 3.0,
                    barColor = if (result.naSaturationPercent > 4.0) Color(0xFFD9534F) else Color(0xFF757575)
                )

                CationBarItem(
                    name = "Exchangeable Acidity",
                    actualPercent = result.aciditySaturationPercent,
                    idealMin = 0.0,
                    idealMax = 5.0,
                    barColor = if (result.aciditySaturationPercent > 10.0) Color(0xFFD9534F) else Color(0xFF7B1FA2)
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                // Ratio badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RatioBadge(
                        label = "Ca : Mg Ratio",
                        ratio = result.caToMgRatio,
                        idealText = "Ideal: 5.0 – 7.0 : 1",
                        isGood = result.caToMgRatio in 4.5..7.5
                    )
                    RatioBadge(
                        label = "Mg : K Ratio",
                        ratio = result.mgToKRatio,
                        idealText = "Ideal: 2.0 – 3.0 : 1",
                        isGood = result.mgToKRatio in 1.8..3.5
                    )
                }

                // Cation evaluation notes
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    result.cationEvaluationNotes.forEach { note ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(
                                text = note,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Soil Improvement & Rebalancing Prescription
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Healing,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Soil Rebalancing Prescription & Amendments",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "Specific chemical and biological amendments calculated to shift cations into the ideal Albrecht zone:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 10.dp)
                )

                if (result.amendmentRecommendations.isEmpty()) {
                    Text(
                        text = "Your soil cation ratios are well balanced! No corrective mineral liming or gypsum amendments required.",
                        fontSize = 12.sp,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Medium
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        result.amendmentRecommendations.forEach { rec ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(
                                            text = rec.amendmentName,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Surface(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "${String.format(Locale.US, "%.0f", rec.rateKgPerHectare)} kg/ha",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Equivalent Rate: ${String.format(Locale.US, "%.0f", rec.rateKgPerAcre)} kg / acre",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )

                                    Text(
                                        text = rec.targetDeficit,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )

                                    Text(
                                        text = rec.primaryFunction,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 15.sp,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )

                                    Text(
                                        text = "Timing: ${rec.applicationTimingAndMethod}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Organic Matter Strategy Banner
                Surface(
                    color = Color(0xFF2E7D32).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.3f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.Eco,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Organic Matter & Biochar Strategy:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            Text(
                                text = result.organicMatterImprovementAdvice,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 15.sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        // Expandable Step-by-Step Mathematical Derivations
        Surface(
            shape = RoundedCornerShape(10.dp),
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Calculate,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Show Mathematical Derivations & Formulas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(
                        onClick = { showFormulas = !showFormulas },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(if (showFormulas) "Hide" else "Show", fontSize = 11.sp)
                    }
                }

                AnimatedVisibility(visible = showFormulas) {
                    Column(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        result.stepByStepDerivations.forEach { step ->
                            Text(
                                text = step,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 15.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CationSliderRow(
    label: String,
    value: Double,
    range: ClosedFloatingPointRange<Float>,
    unit: String,
    color: Color,
    onValueChange: (Double) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(
                text = "${String.format(Locale.US, "%.1f", value)} $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toDouble()) },
            valueRange = range,
            modifier = Modifier.height(28.dp)
        )
    }
}

@Composable
private fun CationBarItem(
    name: String,
    actualPercent: Double,
    idealMin: Double,
    idealMax: Double,
    barColor: Color
) {
    val isOptimal = actualPercent in idealMin..idealMax
    val statusText = when {
        actualPercent < idealMin -> "Low (${String.format(Locale.US, "%.1f", actualPercent)}%)"
        actualPercent > idealMax -> "High (${String.format(Locale.US, "%.1f", actualPercent)}%)"
        else -> "Optimal (${String.format(Locale.US, "%.1f", actualPercent)}%)"
    }

    Column(modifier = Modifier.padding(vertical = 3.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(
                text = "$statusText | Ideal: ${idealMin.roundToInt()}%–${idealMax.roundToInt()}%",
                fontSize = 11.sp,
                fontWeight = if (isOptimal) FontWeight.Bold else FontWeight.Normal,
                color = if (isOptimal) Color(0xFF2E7D32) else if (actualPercent < idealMin) Color(0xFFF2A65A) else Color(0xFFD9534F)
            )
        }
        LinearProgressIndicator(
            progress = { (actualPercent / 100.0).toFloat().coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .padding(top = 2.dp),
            color = barColor,
            trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun RatioBadge(
    label: String,
    ratio: Double,
    idealText: String,
    isGood: Boolean
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = if (isGood) Color(0xFF2E7D32).copy(alpha = 0.1f) else Color(0xFFD9534F).copy(alpha = 0.1f),
        border = BorderStroke(1.dp, if (isGood) Color(0xFF2E7D32) else Color(0xFFD9534F))
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = "${String.format(Locale.US, "%.1f", ratio)} : 1",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isGood) Color(0xFF2E7D32) else Color(0xFFD9534F)
            )
            Text(text = idealText, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
