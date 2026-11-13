package com.example.ui.screens.postharvest

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
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.postharvest.PostHarvestCatalog
import com.example.data.voice.AgriculturalLanguagePack
import com.example.data.voice.AppLanguage

@Composable
fun PostHarvestStorageView(
    appLanguage: AppLanguage = AppLanguage.EN,
    modifier: Modifier = Modifier
) {
    var selectedStandard by remember { mutableStateOf(PostHarvestCatalog.cropStandards.first()) }
    var wetWeightText by remember { mutableStateOf("1800") }
    var currentMoistureText by remember { mutableStateOf(selectedStandard.typicalHarvestMoisturePercent.toString()) }

    val calcResult = remember(selectedStandard, wetWeightText, currentMoistureText) {
        PostHarvestCatalog.calculateDryingAndStorage(
            standard = selectedStandard,
            wetWeightKg = wetWeightText.toDoubleOrNull() ?: 1000.0,
            initialMoisturePercent = currentMoistureText.toDoubleOrNull() ?: selectedStandard.typicalHarvestMoisturePercent
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("post_harvest_storage_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Inventory2, contentDescription = "Post Harvest Storage", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Post-Harvest Drying, Storage & Aflatoxin",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = AgriculturalLanguagePack.uiLabels["aflatoxin_dry"]?.textFor(appLanguage)
                                ?: "Moisture checker, PICS bag calculator, and storage pest protection.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Crop Standard Selector
        Text("Select Harvested Commodity:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PostHarvestCatalog.cropStandards.forEach { std ->
                FilterChip(
                    selected = selectedStandard.id == std.id,
                    onClick = {
                        selectedStandard = std
                        currentMoistureText = std.typicalHarvestMoisturePercent.toString()
                    },
                    label = { Text(std.cropName) },
                    modifier = Modifier.testTag("ph_crop_${std.id}")
                )
            }
        }

        // Inputs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = wetWeightText,
                onValueChange = { wetWeightText = it },
                label = { Text("Harvest Weight (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(1f).testTag("input_ph_weight")
            )
            OutlinedTextField(
                value = currentMoistureText,
                onValueChange = { currentMoistureText = it },
                label = { Text("Current Moisture (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(1f).testTag("input_ph_moisture")
            )
        }

        // Results Card
        val statusColor = if (calcResult.isSafeToStore) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, statusColor.copy(alpha = 0.55f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (calcResult.isSafeToStore) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = statusColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Safe Moisture Limit: <= ${selectedStandard.maxSafeMoisturePercent}%",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
                Text(calcResult.advisoryMessage, style = MaterialTheme.typography.bodySmall)
                HorizontalDivider()
                StorageRow("Dry Weight After Drying", "${String.format("%,.1f", calcResult.finalDryWeightKg)} kg")
                StorageRow("Water Evaporated (Shrinkage)", "${String.format("%,.1f", calcResult.waterLossKg)} kg (${String.format("%.1f", calcResult.shrinkagePercent)}%)")
                StorageRow("90 kg PICS Hermetic Bags Needed", "${calcResult.pics90KgBagsNeeded} bags")
                StorageRow("500 kg Metal Silos Needed", "${calcResult.metalSilo500KgNeeded} silos")
                HorizontalDivider()
                Text("Drying SOP: ${selectedStandard.dryingGuide}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Aflatoxin Alert: ${selectedStandard.aflatoxinRiskLevel}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
            }
        }

        // Storage Pests Catalog
        Text("Storage Pests & Hermetic / Safe Control", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        PostHarvestCatalog.storagePests.forEach { pest ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.BugReport, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(pest.commonName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    }
                    Text("${pest.scientificName} • Crops: ${pest.targetCommodities.joinToString(", ")}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                    Text(pest.damageDescription, style = MaterialTheme.typography.bodySmall)
                    Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)) {
                        Text(
                            text = "Hermetic/Cultural: ${pest.culturalAndHermeticControl}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Text("Chemical/Store SOP: ${pest.chemicalFumigationSop}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StorageRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}
