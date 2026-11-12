package com.example.ui.screens.science

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Coronavirus
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.nuflip.FracClassificationEntry
import com.example.data.nuflip.IracClassificationEntry
import com.example.data.nuflip.NuflipCategory
import com.example.data.nuflip.NuflipFactsheetEntry
import com.example.data.nuflip.NuflipFieldFactsheetCatalog

/**
 * Tab displaying NUFLIP / JICA Northern Uganda Vegetable Factsheets,
 * including 22 pests, 22 diseases, 8 physiological disorders,
 * IRAC MoA codes, and FRAC tables with Bronopol.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NuflipFactsheetsTab(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var selectedFactsheet by remember { mutableStateOf<NuflipFactsheetEntry?>(null) }
    var showIracTable by remember { mutableStateOf(false) }
    var showFracTable by remember { mutableStateOf(false) }

    val allFactsheets = NuflipFieldFactsheetCatalog.factsheets
    val filteredFactsheets = allFactsheets.filter { entry ->
        val matchesCategory = when (selectedCategoryFilter) {
            "PEST" -> entry.category == NuflipCategory.PEST
            "DISEASE" -> entry.category == NuflipCategory.DISEASE
            "DISORDER" -> entry.category == NuflipCategory.PHYSIOLOGICAL_DISORDER
            else -> true
        }
        val q = searchQuery.trim().lowercase()
        val matchesSearch = q.isBlank() ||
            entry.name.lowercase().contains(q) ||
            entry.scientificName.lowercase().contains(q) ||
            entry.hostCrops.any { it.lowercase().contains(q) } ||
            entry.symptomsAndDamage.lowercase().contains(q)

        matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // NUFLIP Project Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "NUFLIP Vegetable Factsheets",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Text(
                                text = "JICA / MAAIF Northern Uganda Farmers Project",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Demonstration field factsheets detailing 22 vegetable pests, 22 diseases, and 8 physiological disorders (Blossom End Rot, Potassium/Magnesium deficiency, Sunburn, Flooding, Pollination failure) with IRAC & FRAC resistance management.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF1B5E20)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { showIracTable = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("IRAC MoA Table", fontSize = 11.sp)
                        }
                        Button(
                            onClick = { showFracTable = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("FRAC Fungicides", fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_nuflip_search"),
                placeholder = { Text("Search pests, diseases, disorders (e.g. Blossom End Rot)...") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Category Filter Chips
        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip(
                    selected = selectedCategoryFilter == "ALL",
                    onClick = { selectedCategoryFilter = "ALL" },
                    label = { Text("All (${allFactsheets.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )
                FilterChip(
                    selected = selectedCategoryFilter == "PEST",
                    onClick = { selectedCategoryFilter = "PEST" },
                    label = { Text("Insect Pests (22)") }
                )
                FilterChip(
                    selected = selectedCategoryFilter == "DISEASE",
                    onClick = { selectedCategoryFilter = "DISEASE" },
                    label = { Text("Diseases (22)") }
                )
                FilterChip(
                    selected = selectedCategoryFilter == "DISORDER",
                    onClick = { selectedCategoryFilter = "DISORDER" },
                    label = { Text("Physiological Disorders (8)") }
                )
            }
        }

        // Factsheet items
        items(filteredFactsheets) { entry ->
            OutlinedCard(
                onClick = { selectedFactsheet = entry },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("nuflip_entry_${entry.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            val icon = when (entry.category) {
                                NuflipCategory.PEST -> Icons.Default.BugReport
                                NuflipCategory.DISEASE -> Icons.Default.Coronavirus
                                NuflipCategory.PHYSIOLOGICAL_DISORDER -> Icons.Default.Thermostat
                            }
                            val tint = when (entry.category) {
                                NuflipCategory.PEST -> Color(0xFFD32F2F)
                                NuflipCategory.DISEASE -> Color(0xFFF57C00)
                                NuflipCategory.PHYSIOLOGICAL_DISORDER -> Color(0xFF1976D2)
                            }
                            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = entry.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = entry.scientificName,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when (entry.category) {
                                NuflipCategory.PEST -> Color(0xFFFFEBEE)
                                NuflipCategory.DISEASE -> Color(0xFFFFF3E0)
                                NuflipCategory.PHYSIOLOGICAL_DISORDER -> Color(0xFFE3F2FD)
                            }
                        ) {
                            Text(
                                text = entry.category.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                color = when (entry.category) {
                                    NuflipCategory.PEST -> Color(0xFFC62828)
                                    NuflipCategory.DISEASE -> Color(0xFFEF6C00)
                                    NuflipCategory.PHYSIOLOGICAL_DISORDER -> Color(0xFF1565C0)
                                },
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hosts: ${entry.hostCrops.joinToString(", ")}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = entry.symptomsAndDamage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Tap to view full factsheet & control →",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }

    // Detail Dialog
    selectedFactsheet?.let { fs ->
        AlertDialog(
            onDismissRequest = { selectedFactsheet = null },
            title = {
                Column {
                    Text(fs.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(fs.scientificName, style = MaterialTheme.typography.bodySmall, fontStyle = FontStyle.Italic)
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Host Crops: ${fs.hostCrops.joinToString(", ")}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)

                    HorizontalDivider()

                    Text("Symptoms & Damage:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(fs.symptomsAndDamage, style = MaterialTheme.typography.bodySmall)

                    Text("Favorable Conditions:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(fs.favorableConditions, style = MaterialTheme.typography.bodySmall)

                    Text("Biological Features:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(fs.biologicalFeatures, style = MaterialTheme.typography.bodySmall)

                    Text("Field Control Measures:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Text(fs.controlMeasures, style = MaterialTheme.typography.bodySmall)

                    if (fs.chemicalRotations.isNotEmpty()) {
                        Text("Chemical Rotations & Ingredients:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        fs.chemicalRotations.forEach { chem ->
                            Text("• $chem", style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Field Pictorial: ${fs.imageDescription}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { selectedFactsheet = null }) {
                    Text("Close")
                }
            }
        )
    }

    // IRAC MoA Dialog
    if (showIracTable) {
        AlertDialog(
            onDismissRequest = { showIracTable = false },
            title = {
                Text("IRAC Mode of Action (MoA) Insecticide Classification", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "Rotate between different numeric MoA groups across successive generations to prevent insect resistance development.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    NuflipFieldFactsheetCatalog.iracInsecticideList.forEach { irac ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text("Group ${irac.moaGroup} - ${irac.chemicalClass}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text("Actives: ${irac.activeIngredients.joinToString(", ")}", style = MaterialTheme.typography.labelSmall)
                                Text(irac.modeOfActionDescription, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showIracTable = false }) {
                    Text("Done")
                }
            }
        )
    }

    // FRAC Table Dialog
    if (showFracTable) {
        AlertDialog(
            onDismissRequest = { showFracTable = false },
            title = {
                Text("FRAC Fungicide Classification & Bronopol", fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "FRAC codes indicate target biochemical sites. Mix or alternate single-site systemic fungicides with multi-site protective fungicides (M1, M2, M3).",
                        style = MaterialTheme.typography.bodySmall
                    )
                    NuflipFieldFactsheetCatalog.fracFungicideList.forEach { frac ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("FRAC ${frac.fracCode} - ${frac.chemicalGroup}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Text(frac.applicationTiming, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                                Text("Actives: ${frac.activeIngredients.joinToString(", ")}", style = MaterialTheme.typography.labelSmall)
                                Text("Resistance Risk: ${frac.riskLevel}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showFracTable = false }) {
                    Text("Done")
                }
            }
        )
    }
}
