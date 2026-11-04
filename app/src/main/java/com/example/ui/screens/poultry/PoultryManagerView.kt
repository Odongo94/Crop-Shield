package com.example.ui.screens.poultry

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
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.poultry.PoultryManagementCatalog
import com.example.model.poultry.ChickVaccineEntry
import com.example.model.poultry.PoultryFeedAgeStage
import com.example.model.poultry.PoultryFlockType
import com.example.model.poultry.VaccineAdministrationRoute

/**
 * Interactive Poultry Management Suite:
 * 1. Comprehensive Chick Vaccination Calendar with target diseases and SOPs
 * 2. Age-Stratified Precision Feeding Program and Nutritional Specifications
 * 3. Daily Flock Feed, Water, Brooding, and Vaccine Schedule Calculator
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PoultryManagerView(
    modifier: Modifier = Modifier
) {
    var primaryTab by remember { mutableIntStateOf(0) } // 0: Vaccination Calendar, 1: Feeding Program, 2: Flock Calculator
    var selectedFlockType by remember { mutableStateOf(PoultryFlockType.LAYERS) }
    var flockSizeInput by remember { mutableStateOf("500") }
    var chickAgeDays by remember { mutableIntStateOf(14) }
    var expandedVaccineIds by remember { mutableStateOf(setOf<String>()) }
    var expandedFeedStageIds by remember { mutableStateOf(setOf<String>()) }

    val flockPlan by remember(selectedFlockType, flockSizeInput, chickAgeDays) {
        derivedStateOf {
            val size = flockSizeInput.toIntOrNull() ?: 500
            PoultryManagementCatalog.calculateDailyFlockPlan(
                flockType = selectedFlockType,
                flockSizeBirds = size,
                ageDays = chickAgeDays
            )
        }
    }

    val allVaccines = PoultryManagementCatalog.vaccinationSchedule
    val filteredVaccines = remember(selectedFlockType) {
        allVaccines.filter { it.targetFlockType.contains(selectedFlockType) }
    }

    val allFeedStages = PoultryManagementCatalog.feedingPrograms
    val filteredFeedStages = remember(selectedFlockType) {
        allFeedStages.filter { it.targetFlockType == selectedFlockType }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("poultry_manager_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Egg,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Poultry Management & Health Suite",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Complete chick vaccination schedules with SOPs and cold-chain protection, age-stratified feeding programs (CP% and ME kcal/kg), and flock feed/water daily budgeting.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        // Primary Tabs
        PrimaryTabRow(
            selectedTabIndex = primaryTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth().testTag("poultry_primary_tab_row")
        ) {
            Tab(
                selected = primaryTab == 0,
                onClick = { primaryTab = 0 },
                text = { Text("Vaccination Calendar", style = MaterialTheme.typography.labelSmall) },
                icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = primaryTab == 1,
                onClick = { primaryTab = 1 },
                text = { Text("Feeding Programs", style = MaterialTheme.typography.labelSmall) },
                icon = { Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = primaryTab == 2,
                onClick = { primaryTab = 2 },
                text = { Text("Flock Calculator", style = MaterialTheme.typography.labelSmall) },
                icon = { Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        // Flock Type Selector (Global across all 3 tabs)
        OutlinedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PoultryFlockType.values().forEach { type ->
                    FilterChip(
                        selected = selectedFlockType == type,
                        onClick = { selectedFlockType = type },
                        label = { Text(type.displayName, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.testTag("chip_poultry_type_${type.name}")
                    )
                }
            }
        }

        // ==========================================================
        // TAB 0: VACCINATION CALENDAR
        // ==========================================================
        if (primaryTab == 0) {
            // Skimmed Milk & Chlorine SOP Callout
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalPharmacy,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Vaccine Administration Standard Operating Procedure",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = "1. Maintain cold chain (2 - 8°C). 2. Never use chlorinated water; add non-fat skimmed milk powder (2.5 g/L) to protect live viral titer. 3. Withdraw water 1.5 - 2 hrs prior so birds consume entire vaccine within 90 mins.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Text(
                text = "Chronological Vaccination Schedule (${filteredVaccines.size} Events)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            filteredVaccines.forEach { vaccine ->
                val isExpanded = expandedVaccineIds.contains(vaccine.id)

                OutlinedCard(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth().testTag("card_vaccine_${vaccine.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = vaccine.ageDisplay,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = vaccine.route.displayName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = vaccine.targetDisease,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Vaccine: ${vaccine.vaccineNameAndStrain}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = vaccine.importanceAndPathology,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedVaccineIds = if (isExpanded) expandedVaccineIds - vaccine.id else expandedVaccineIds + vaccine.id
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpanded) "Hide Preparation Protocol" else "View Preparation SOP & Contraindications",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier.padding(top = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                HorizontalDivider()

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text("Administration Technique & SOP:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        Text(vaccine.route.techniqueGuidance, style = MaterialTheme.typography.bodySmall)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Reconstitution & Handling:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                        Text(vaccine.preparationAndHandlingSop, style = MaterialTheme.typography.bodySmall)
                                    }
                                }

                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp).padding(top = 2.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(vaccine.contraindicationsAndAlerts, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }

        // ==========================================================
        // TAB 1: FEEDING PROGRAM & NUTRITION SPECIFICATIONS
        // ==========================================================
        if (primaryTab == 1) {
            Text(
                text = "Age-Stratified Nutritional Feeding Program (${selectedFlockType.displayName})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            filteredFeedStages.forEach { stage ->
                val isExpanded = expandedFeedStageIds.contains(stage.id)

                OutlinedCard(
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth().testTag("card_feed_stage_${stage.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stage.stageName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Text(
                                    text = stage.ageDisplay,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stage.nutritionalRoleAndFocus,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        // Nutrient Specifications Grid
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Crude Protein: ${"%.1f".format(stage.crudeProteinPercent)}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Energy: ${"%.0f".format(stage.metabolizableEnergyKcalKg)} kcal/kg", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Calcium: ${"%.2f".format(stage.calciumPercent)}%", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Avail P: ${"%.2f".format(stage.availablePhosphorusPercent)}%", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Daily Feed: ${"%.0f".format(stage.dailyFeedGramsPerBird)} g/bird", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                            Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)) {
                                Text("Daily Water: ${"%.0f".format(stage.dailyWaterMlPerBird)} ml/bird", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedFeedStageIds = if (isExpanded) expandedFeedStageIds - stage.id else expandedFeedStageIds + stage.id
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpanded) "Hide Environment & Feeder Hygiene Tips" else "View Brooding Temperature & Feeder Hygiene",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        AnimatedVisibility(visible = isExpanded) {
                            Column(
                                modifier = Modifier.padding(top = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                HorizontalDivider()

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Thermostat, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Brooding Temperature Target: ${stage.broodingTemperatureTargetCelsius}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Lighting Program: ${stage.lightingHoursPerDay}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                }

                                Text("Feeder Hygiene & Best Practices:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                stage.managementAndFeederHygieneTips.forEach { tip ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(tip, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // ==========================================================
        // TAB 2: FLOCK CALCULATOR (Feed, Water, Bags, Upcoming Vax)
        // ==========================================================
        if (primaryTab == 2) {
            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Flock Parameter Inputs",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = flockSizeInput,
                            onValueChange = { flockSizeInput = it },
                            label = { Text("Flock Size (Birds)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.weight(1f).testTag("input_flock_size")
                        )

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Chick Age: $chickAgeDays Days (Week ${chickAgeDays / 7 + 1})", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            Slider(
                                value = chickAgeDays.toFloat(),
                                onValueChange = { chickAgeDays = it.toInt() },
                                valueRange = 1f..150f,
                                modifier = Modifier.fillMaxWidth().testTag("slider_chick_age")
                            )
                        }
                    }
                }
            }

            // Results Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Daily Flock Budget (${flockPlan.flockSizeBirds} Birds at Day ${flockPlan.ageDays})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Current Stage: ${flockPlan.currentFeedStage.stageName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Feed and Water KPI Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Daily Feed Required", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${"%.1f".format(flockPlan.dailyFeedTotalKg)} kg", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text("${"%.2f".format(flockPlan.dailyFeed50kgBags)} bags (50kg)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }

                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Daily Clean Water", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${"%.1f".format(flockPlan.dailyWaterRequiredLiters)} L", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                Text("2.0 - 2.5x feed weight", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Brooding Temperature", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(flockPlan.currentBroodingTemperature, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                            }
                        }

                        Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surface, modifier = Modifier.weight(1f)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("Target Bodyweight", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("${"%.0f".format(flockPlan.targetBodyWeightGrams)} g / bird", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                    }

                    // Upcoming Vaccines Banner
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
                        else MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) Icons.Default.Warning else Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) "VACCINE ALERT: Due in Next 7 Days" else "Next Scheduled Vaccination",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                                )
                            }
                            val nextVax = flockPlan.nextScheduledVaccine
                            if (flockPlan.upcomingVaccinesNext7Days.isNotEmpty()) {
                                flockPlan.upcomingVaccinesNext7Days.forEach { vax ->
                                    Text(
                                        text = "• ${vax.ageDisplay}: ${vax.targetDisease} (${vax.route.displayName})",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            } else if (nextVax != null) {
                                Text(
                                    text = "Scheduled at ${nextVax.ageDisplay}: ${nextVax.targetDisease} via ${nextVax.route.displayName}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Text(
                                    text = "Flock has completed primary rearing vaccination schedule.",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
