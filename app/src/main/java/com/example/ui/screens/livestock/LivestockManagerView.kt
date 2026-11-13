package com.example.ui.screens.livestock

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.livestock.LivestockRuminantSwineCatalog
import com.example.model.livestock.LivestockBreedingRecord
import com.example.model.livestock.LivestockSpecies
import com.example.model.livestock.LivestockStage
import java.util.concurrent.TimeUnit

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun LivestockManagerView(
    breedingRecords: List<LivestockBreedingRecord>,
    onAddBreedingRecord: (LivestockBreedingRecord) -> Unit,
    onDeleteBreedingRecord: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubTab by remember { mutableIntStateOf(0) } // 0: Ration, 1: Breeding Calendar, 2: Dipping & Health
    var selectedSpecies by remember { mutableStateOf(LivestockSpecies.DAIRY_CATTLE) }
    var selectedStage by remember { mutableStateOf(LivestockStage.LACTATING_HIGH) }
    var bodyWeightText by remember { mutableStateOf("450") }
    var milkYieldText by remember { mutableStateOf("18") }
    var showAddBreedingDialog by remember { mutableStateOf(false) }

    val rationResult = remember(selectedSpecies, selectedStage, bodyWeightText, milkYieldText) {
        LivestockRuminantSwineCatalog.calculateDailyRation(
            species = selectedSpecies,
            stage = selectedStage,
            bodyWeightKg = bodyWeightText.toDoubleOrNull() ?: 400.0,
            milkYieldLiters = milkYieldText.toDoubleOrNull() ?: 12.0
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("livestock_manager_screen")
    ) {
        PrimaryTabRow(selectedTabIndex = selectedSubTab) {
            Tab(selected = selectedSubTab == 0, onClick = { selectedSubTab = 0 }, text = { Text("Feed Ration") })
            Tab(selected = selectedSubTab == 1, onClick = { selectedSubTab = 1 }, text = { Text("Gestation") })
            Tab(selected = selectedSubTab == 2, onClick = { selectedSubTab = 2 }, text = { Text("Dip & Health") })
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Species Filter Chips (shared across tabs)
            Text("Livestock Species:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LivestockSpecies.values().forEach { sp ->
                    FilterChip(
                        selected = selectedSpecies == sp,
                        onClick = {
                            selectedSpecies = sp
                            bodyWeightText = when (sp) {
                                LivestockSpecies.DAIRY_CATTLE -> "450"
                                LivestockSpecies.BEEF_CATTLE -> "380"
                                LivestockSpecies.GOAT -> "45"
                                LivestockSpecies.SHEEP -> "40"
                                LivestockSpecies.SWINE -> "70"
                            }
                            milkYieldText = when (sp) {
                                LivestockSpecies.DAIRY_CATTLE -> "18"
                                LivestockSpecies.GOAT -> "2.5"
                                else -> "0"
                            }
                        },
                        label = { Text(sp.name.replace("_", " ")) },
                        modifier = Modifier.testTag("species_${sp.name.lowercase()}")
                    )
                }
            }

            when (selectedSubTab) {
                0 -> {
                    // Stage chips
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LivestockStage.values().forEach { st ->
                            FilterChip(
                                selected = selectedStage == st,
                                onClick = { selectedStage = st },
                                label = { Text(st.displayName) }
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = bodyWeightText,
                            onValueChange = { bodyWeightText = it },
                            label = { Text("Body Weight (kg)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        if (selectedSpecies == LivestockSpecies.DAIRY_CATTLE || selectedSpecies == LivestockSpecies.GOAT) {
                            OutlinedTextField(
                                value = milkYieldText,
                                onValueChange = { milkYieldText = it },
                                label = { Text("Milk (Liters/day)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    OutlinedCard(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Pets, contentDescription = "Daily Ration", tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Daily Feed Ration • ${selectedSpecies.displayName}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            HorizontalDivider()
                            RationRow("Dry Matter Intake (DMI)", "${String.format("%.1f", rationResult.dryMatterIntakeKgPerDay)} kg DM/day")
                            RationRow("Fresh Chopped Forage / Greens", "${String.format("%.1f", rationResult.freshForageKgPerDay)} kg/day")
                            RationRow("Dairy Meal / Concentrate", "${String.format("%.1f", rationResult.dairyMealOrConcentrateKgPerDay)} kg/day")
                            RationRow("Mineral Salt / DCP", "${String.format("%.0f", rationResult.mineralSupplementGramsPerDay)} g/day")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.WaterDrop, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Clean Drinking Water: ${String.format("%.0f", rationResult.waterLitersPerDay)} Liters/day",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            HorizontalDivider()
                            rationResult.rationNotes.forEach { note ->
                                Text("• $note", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                1 -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Breeding & Gestation Calendar", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Button(onClick = { showAddBreedingDialog = true }, modifier = Modifier.testTag("btn_add_breeding")) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Log Service")
                        }
                    }

                    breedingRecords.forEach { rec ->
                        OutlinedCard(shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("${rec.animalNameOrTag} (${rec.species.name})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    }
                                    IconButton(onClick = { onDeleteBreedingRecord(rec.id) }, modifier = Modifier.size(30.dp)) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete breeding record", modifier = Modifier.size(18.dp))
                                    }
                                }
                                Text(
                                    text = "Expected Birth: ${LivestockRuminantSwineCatalog.formatDate(rec.expectedDueDateMillis)} (${rec.daysRemaining} days left)",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Serviced: ${LivestockRuminantSwineCatalog.formatDate(rec.serviceDateMillis)} • Dry-off/Steaming Start: ${LivestockRuminantSwineCatalog.formatDate(rec.dryOffOrSteamingDateMillis)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                else -> {
                    val protocols = LivestockRuminantSwineCatalog.healthProtocols.filter {
                        it.species.contains(selectedSpecies)
                    }
                    protocols.forEach { p ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.MedicalServices, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                                        Text(
                                            text = "${p.category} • Every ${p.intervalDays}d",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(p.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text("Drug/Vaccine: ${p.activeDrugOrVaccine}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                Text("SOP: ${p.dosageAndRoute}", style = MaterialTheme.typography.bodySmall)
                                Text("Withdrawal/Alert: ${p.withdrawalPeriodNotes}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showAddBreedingDialog) {
        var tag by remember { mutableStateOf("Cow / Doe #07") }
        var sp by remember { mutableStateOf(selectedSpecies) }
        var daysPregnantText by remember { mutableStateOf("30") }

        AlertDialog(
            onDismissRequest = { showAddBreedingDialog = false },
            title = { Text("Log Breeding / AI Service", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = tag, onValueChange = { tag = it }, label = { Text("Animal Name / Ear Tag") }, singleLine = true)
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        LivestockSpecies.values().forEach { s ->
                            FilterChip(selected = sp == s, onClick = { sp = s }, label = { Text(s.name.take(6)) })
                        }
                    }
                    OutlinedTextField(
                        value = daysPregnantText,
                        onValueChange = { daysPregnantText = it },
                        label = { Text("Days Since Service (AI/Mating)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val daysAgo = daysPregnantText.toLongOrNull() ?: 0L
                    val serviceMs = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(daysAgo.coerceAtLeast(0L))
                    onAddBreedingRecord(
                        LivestockRuminantSwineCatalog.calculateBreedingDates(
                            id = "breed_${System.currentTimeMillis()}",
                            animalNameOrTag = tag.ifBlank { "Animal" },
                            species = sp,
                            serviceDateMillis = serviceMs
                        )
                    )
                    showAddBreedingDialog = false
                }) { Text("Save") }
            },
            dismissButton = { TextButton(onClick = { showAddBreedingDialog = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun RationRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
