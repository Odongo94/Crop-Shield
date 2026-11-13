package com.example.ui.screens.season

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.local.entities.MappedFieldEntity
import com.example.data.season.CropStageEngine
import com.example.model.season.ActiveCropSeason
import java.util.concurrent.TimeUnit

@Composable
fun CropSeasonTrackerView(
    seasons: List<ActiveCropSeason>,
    mappedFields: List<MappedFieldEntity>,
    onAddSeason: (ActiveCropSeason) -> Unit,
    onDeleteSeason: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    val statuses = remember(seasons) {
        seasons.map { CropStageEngine.evaluateSeason(it) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("crop_season_tracker_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Spa, contentDescription = "Crop Season Tracker", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Active Crop Season & Growth-Stage Tracker",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Track Days After Planting (DAP), growth milestones, and scouting triggers per mapped plot.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { showAddDialog = true },
                        modifier = Modifier.testTag("btn_add_crop_season")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Start New Crop Season")
                    }
                }
            }
        }

        items(statuses, key = { it.season.id }) { status ->
            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth().testTag("season_card_${status.season.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${status.season.cropName} (${status.season.variety})",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${status.season.fieldName} • ${String.format("%.2f", status.season.areaHectares)} ha",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "Day ${status.daysAfterPlanting} / ${status.totalCycleDays}",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            IconButton(onClick = { onDeleteSeason(status.season.id) }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Delete, contentDescription = "Remove season", modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    LinearProgressIndicator(
                        progress = { status.progressFraction },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Stage: ${status.currentStage.stageName}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Est. Harvest: ${status.estimatedHarvestDateFormatted}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    if (status.nextStageName != null) {
                        Text(
                            text = "Next: ${status.nextStageName} in ~${status.daysUntilNextStage} days",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    // Key actions
                    status.currentStage.keyActions.forEach { action ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(action, style = MaterialTheme.typography.bodySmall)
                        }
                    }

                    // Scouting trigger pests
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.BugReport, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Scout Watchlist: ${status.currentStage.scoutingFocus.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Water & feeding tip
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.WaterDrop, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(status.currentStage.irrigationAndFeedingTip, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }

    if (showAddDialog) {
        AddCropSeasonDialog(
            mappedFields = mappedFields,
            onDismiss = { showAddDialog = false },
            onSave = { season ->
                onAddSeason(season)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun AddCropSeasonDialog(
    mappedFields: List<MappedFieldEntity>,
    onDismiss: () -> Unit,
    onSave: (ActiveCropSeason) -> Unit
) {
    val firstField = mappedFields.firstOrNull()
    var fieldName by remember { mutableStateOf(firstField?.name ?: "Block B - Main Shamba") }
    var cropName by remember { mutableStateOf(firstField?.cropName ?: "Maize") }
    var variety by remember { mutableStateOf("H614D / Local Certified") }
    var areaHaText by remember { mutableStateOf(firstField?.hectares?.toString() ?: "1.0") }
    var dapText by remember { mutableStateOf("14") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Track Crop Season", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (mappedFields.isNotEmpty()) {
                    Text("Pick Mapped Field (optional):", style = MaterialTheme.typography.labelSmall)
                    Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        mappedFields.forEach { mf ->
                            FilterChip(
                                selected = fieldName == mf.name,
                                onClick = {
                                    fieldName = mf.name
                                    cropName = mf.cropName
                                    areaHaText = String.format("%.2f", mf.hectares)
                                },
                                label = { Text(mf.name.take(18)) }
                            )
                        }
                    }
                }
                OutlinedTextField(value = fieldName, onValueChange = { fieldName = it }, label = { Text("Plot / Field Name") }, singleLine = true)
                Text("Crop Profile:", style = MaterialTheme.typography.labelSmall)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    CropStageEngine.cropProfiles.forEach { p ->
                        FilterChip(
                            selected = cropName.equals(p.cropName, ignoreCase = true),
                            onClick = { cropName = p.cropName },
                            label = { Text(p.cropName) }
                        )
                    }
                }
                OutlinedTextField(value = variety, onValueChange = { variety = it }, label = { Text("Variety") }, singleLine = true)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = areaHaText,
                        onValueChange = { areaHaText = it },
                        label = { Text("Area (ha)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = dapText,
                        onValueChange = { dapText = it },
                        label = { Text("Days Since Planted") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val ha = areaHaText.toDoubleOrNull() ?: 1.0
                val dap = dapText.toLongOrNull() ?: 0L
                onSave(
                    ActiveCropSeason(
                        id = "season_${System.currentTimeMillis()}",
                        fieldName = fieldName.ifBlank { "Shamba Plot" },
                        cropName = cropName.ifBlank { "Maize" },
                        variety = variety.ifBlank { "Standard" },
                        areaHectares = ha.coerceAtLeast(0.05),
                        plantedAtMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(dap.coerceAtLeast(0L))
                    )
                )
            }) { Text("Start Tracker") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
