package com.example.ui.screens.soil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.soil.OrganicManureCatalog
import com.example.model.soil.ManureType
import java.util.Locale
import kotlin.math.roundToInt

enum class ManureSubTab(val title: String) {
    TONNAGE_CALC("Tonnage Calculator"),
    METHODS_SOP("Preparation Methods"),
    RAW_MATERIALS("Raw Materials (C:N)"),
    MANAGEMENT("Moisture & Quality")
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OrganicManureMasterclassView(
    modifier: Modifier = Modifier
) {
    var activeSubTab by remember { mutableStateOf(ManureSubTab.TONNAGE_CALC) }

    // Tonnage Calculator states
    var selectedCropIndex by remember { mutableIntStateOf(0) }
    var selectedManureType by remember { mutableStateOf(ManureType.COW_FARMYARD) }
    var farmAreaHectares by remember { mutableDoubleStateOf(1.0) }
    var soilFertilityFactor by remember { mutableDoubleStateOf(1.0) } // 0.8 high OM, 1.2 depleted
    var showMathDerivation by remember { mutableStateOf(false) }

    val cropSpec = OrganicManureCatalog.cropManureSpecs[selectedCropIndex.coerceIn(0, OrganicManureCatalog.cropManureSpecs.size - 1)]

    val tonnageResult = remember(cropSpec, selectedManureType, farmAreaHectares, soilFertilityFactor) {
        OrganicManureCatalog.calculateTonnage(
            cropSpec = cropSpec,
            manureType = selectedManureType,
            areaHectares = farmAreaHectares,
            organicFertilityAdjustment = soilFertilityFactor
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("organic_manure_masterclass_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Eco,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Organic Manure & Composting Masterclass",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Text(
                            text = "Scientific C:N Ratios, Aerobic Preparation & Tonnage Calculator",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
                Text(
                    text = "High-quality organic manure transforms soil biology, adds permanent colloidal humus, and supplies plant-available N-P-K, calcium, and trace minerals over multiple cropping seasons.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f),
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Sub-Navigation Tabs
        SecondaryTabRow(
            selectedTabIndex = activeSubTab.ordinal,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            ManureSubTab.values().forEach { tab ->
                Tab(
                    selected = activeSubTab == tab,
                    onClick = { activeSubTab = tab },
                    text = { Text(tab.title, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        when (activeSubTab) {
            ManureSubTab.TONNAGE_CALC -> {
                // Crop Selector
                OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "1. Select Target Crop Enterprise:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        FlowRow(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            OrganicManureCatalog.cropManureSpecs.forEachIndexed { index, crop ->
                                FilterChip(
                                    selected = selectedCropIndex == index,
                                    onClick = { selectedCropIndex = index },
                                    label = { Text(crop.cropName, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                        }
                        Text(
                            text = "Target Yield: ${cropSpec.targetYieldPerHectare} | Spacing: ${cropSpec.standardSpacingCm}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }

                // Manure Type Selector
                OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "2. Select Organic Manure Source:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        FlowRow(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            ManureType.values().forEach { mType ->
                                FilterChip(
                                    selected = selectedManureType == mType,
                                    onClick = { selectedManureType = mType },
                                    label = { Text(mType.title, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = mType.chartColor,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                        Text(
                            text = "${selectedManureType.description} Best for: ${selectedManureType.bestSuitedFor}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }

                // Farm Area Slider
                OutlinedCard(shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "3. Farm Land Size:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${String.format(Locale.US, "%.1f", farmAreaHectares)} Hectares (${String.format(Locale.US, "%.1f", farmAreaHectares * 2.471)} Acres)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Slider(
                            value = farmAreaHectares.toFloat(),
                            onValueChange = { farmAreaHectares = it.toDouble() },
                            valueRange = 0.1f..10.0f,
                            steps = 98,
                            modifier = Modifier.height(30.dp)
                        )
                    }
                }

                // Calculation Results Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "RECOMMENDED ORGANIC MANURE TONNAGE",
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
                                        text = String.format(Locale.US, "%.1f", tonnageResult.totalTonsRequiredHectare),
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = " Metric Tons / ha",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                                Text(
                                    text = "(${String.format(Locale.US, "%.1f", tonnageResult.totalTonsRequiredAcre)} Tons / acre)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${tonnageResult.total50kgBagsHectare}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Text(
                                        text = "50-kg Sacks",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                        // Per hole and per m2 rates
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "Rate per Planting Hole:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    text = "${String.format(Locale.US, "%.2f", tonnageResult.rateKgPerPlantHole)} kg / hole",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Column {
                                Text(text = "Rate per Bed m²:", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(
                                    text = "${String.format(Locale.US, "%.2f", tonnageResult.rateKgPerSquareMeter)} kg / m²",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Nutrient Delivery Badges
                        Text(
                            text = "Plant-Available Nutrients Delivered in Year 1:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            NutrientMiniBadge("N", "${String.format(Locale.US, "%.1f", tonnageResult.availableNSuppliedKgHa)} kg/ha", Color(0xFF2E7D32), Modifier.weight(1f))
                            NutrientMiniBadge("P₂O₅", "${String.format(Locale.US, "%.1f", tonnageResult.availableP2O5SuppliedKgHa)} kg/ha", Color(0xFFE65100), Modifier.weight(1f))
                            NutrientMiniBadge("K₂O", "${String.format(Locale.US, "%.1f", tonnageResult.availableK2OSuppliedKgHa)} kg/ha", Color(0xFF1565C0), Modifier.weight(1f))
                        }
                    }
                }

                // Application Tips
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Agronomic Application Guidelines for ${cropSpec.cropName}:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Method: ${cropSpec.recommendedApplicationMethod}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        tonnageResult.agronomicApplicationTips.forEach { tip ->
                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                                Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Text(text = tip, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 15.sp)
                            }
                        }
                    }
                }

                // Mathematical Derivation Toggle
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
                                text = "Show Tonnage Mathematical Derivation",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Button(onClick = { showMathDerivation = !showMathDerivation }, modifier = Modifier.height(30.dp)) {
                                Text(if (showMathDerivation) "Hide" else "Show", fontSize = 10.sp)
                            }
                        }
                        AnimatedVisibility(visible = showMathDerivation) {
                            Column(modifier = Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                tonnageResult.calculationSteps.forEach { step ->
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

            ManureSubTab.METHODS_SOP -> {
                Text(
                    text = "Standard Composting Methods & Engineering Protocols",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                OrganicManureCatalog.compostingMethods.forEach { method ->
                    CompostingMethodCard(method)
                }
            }

            ManureSubTab.RAW_MATERIALS -> {
                Text(
                    text = "Composting Raw Materials & C:N Ratio Directory",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Ideal Composting C:N Target = 25:1 to 30:1 (Achieved by blending 2 to 3 parts Brown with 1 part Green by volume).",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OrganicManureCatalog.rawMaterials.forEach { material ->
                    RawMaterialCard(material)
                }
            }

            ManureSubTab.MANAGEMENT -> {
                CompostManagementMasterclass()
            }
        }
    }
}

@Composable
private fun NutrientMiniBadge(label: String, value: String, color: Color, modifier: Modifier) {
    Surface(
        color = color.copy(alpha = 0.12f),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, color.copy(alpha = 0.4f)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = color)
        }
    }
}

@Composable
private fun CompostingMethodCard(method: com.example.model.soil.CompostingMethodGuide) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = method.methodName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.padding(top = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "⏱ ${method.durationText}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "🔥 ${method.targetTemperatureRange}",
                            fontSize = 11.sp,
                            color = Color(0xFFD9534F)
                        )
                    }
                }
                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
                    Text(
                        text = "Turning Schedule: ${method.turningSchedule}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Text(
                        text = "Step-by-Step SOP:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    method.stepByStepProtocol.forEach { step ->
                        Text(
                            text = step,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 15.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Maturity Verification Checklist:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    method.maturityChecklist.forEach { check ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = check, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RawMaterialCard(material: com.example.model.soil.CompostRawMaterial) {
    val isBrown = material.category.startsWith("Brown")
    val badgeColor = if (isBrown) Color(0xFF795548) else Color(0xFF2E7D32)

    OutlinedCard(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = material.name,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = badgeColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "C:N ${material.cnRatio}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "Category: ${material.category}", fontSize = 11.sp, color = badgeColor, fontWeight = FontWeight.SemiBold)
                Text(text = "Moisture: ${material.moistureContent}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Text(
                text = material.roleAndPrecaution,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 15.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
private fun CompostManagementMasterclass() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Moisture Management (The Fist Test)
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Opacity, contentDescription = null, tint = Color(0xFF1565C0), modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1. Moisture Management: The Fist Squeeze Test (55%–60%)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                }
                Text(
                    text = "Take a handful of compost from 30 cm inside the pile and squeeze hard:\n" +
                    "• Perfect Moisture: One or two drops of water appear between your knuckles, and the clump stays together like a damp wrung-out sponge.\n" +
                    "• Too Dry (<45%): No water drops; clump crumbles apart when hand opens. Microbes enter dormancy; pile fails to heat up. Add water while turning.\n" +
                    "• Too Wet (>65%): Water pours out in a stream; clump feels soggy and anaerobic. Pores are waterlogged; pile turns foul. Turn immediately and add dry stover or straw.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // Temperature Curve
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Thermostat, contentDescription = null, tint = Color(0xFFD9534F), modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "2. Temperature Phases & Sanitation (55°C – 65°C)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD9534F)
                    )
                }
                Text(
                    text = "• Mesophilic Phase (Days 1–3, 20°C–40°C): Fungi and acid-producing bacteria break down soluble sugars and starch.\n" +
                    "• Thermophilic Peak (Days 4–10, 55°C–65°C): Heat-loving Bacillus and actinomycetes consume cellulose. Maintaining ≥55°C for 3 consecutive days is MANDATORY to kill pathogenic Salmonella, E. coli, and all weed seeds.\n" +
                    "• Lethal Threshold (>68°C): If pile reaches 70°C, turn immediately! Overheating volatilizes nitrogen as ammonia gas and destroys beneficial microbes.\n" +
                    "• Cooling & Curing (Weeks 3–8, 30°C–35°C): Earthworms and mycorrhizae colonize, building humic colloids.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        // Maturity Testing
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "3. Maturity & Curing Quality Verification",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }
                Text(
                    text = "• The Jar Bag Test: Seal 2 cups of damp compost in an airtight ziploc bag at room temperature for 48 hours. Open and smell: if it smells like sweet forest earth (geosmin), it is mature. If sour or ammonia-like, curing is incomplete.\n" +
                    "• Seedling Bio-Assay: Sow 20 cress or radish seeds in 100% compost and 20 in moist paper towels. If germination in compost is >85% of control without root tip burning, compost is fully cured.\n" +
                    "• Carbon:Nitrogen Ratio: Cured compost stabilises at C:N 12:1 to 15:1.",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }
    }
}
