package com.example.ui.screens.economics

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
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.TrendingUp
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
import com.example.data.economics.FarmEconomicsCalculator
import com.example.model.economics.FarmEconomicsInput

@Composable
fun FarmEconomicsScreen(
    initialHectares: Double = 1.0,
    onSaveBudgetSummary: ((title: String, crop: String, summary: String) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var selectedPreset by remember { mutableStateOf(FarmEconomicsCalculator.cropPresets.first()) }
    var areaHaText by remember { mutableStateOf(String.format("%.2f", initialHectares)) }
    var bagPriceText by remember { mutableStateOf("3500") }
    var expectedYieldText by remember { mutableStateOf(selectedPreset.expectedYieldKgPerHa.toInt().toString()) }
    var marketPriceText by remember { mutableStateOf(selectedPreset.typicalMarketPriceKesPerKg.toInt().toString()) }
    var chemBudgetText by remember { mutableStateOf(selectedPreset.chemicalSprayCostKesPerHa.toInt().toString()) }
    var laborBudgetText by remember { mutableStateOf(selectedPreset.laborAndOpsCostKesPerHa.toInt().toString()) }

    val input = remember(
        selectedPreset, areaHaText, bagPriceText, expectedYieldText, marketPriceText, chemBudgetText, laborBudgetText
    ) {
        FarmEconomicsInput(
            cropPreset = selectedPreset,
            areaHectares = (areaHaText.toDoubleOrNull() ?: 1.0).coerceAtLeast(0.05),
            seedCostPerHa = selectedPreset.seedCostKesPerHa,
            fertilizerBagPriceKes = (bagPriceText.toDoubleOrNull() ?: 3500.0).coerceAtLeast(0.0),
            chemicalBudgetPerHa = (chemBudgetText.toDoubleOrNull() ?: selectedPreset.chemicalSprayCostKesPerHa).coerceAtLeast(0.0),
            manureCostPerTonne = 2200.0,
            laborCostPerHa = (laborBudgetText.toDoubleOrNull() ?: selectedPreset.laborAndOpsCostKesPerHa).coerceAtLeast(0.0),
            irrigationCostPerHa = selectedPreset.irrigationCostKesPerHa,
            expectedYieldKgPerHa = (expectedYieldText.toDoubleOrNull() ?: selectedPreset.expectedYieldKgPerHa).coerceAtLeast(1.0),
            marketPriceKesPerKg = (marketPriceText.toDoubleOrNull() ?: selectedPreset.typicalMarketPriceKesPerKg).coerceAtLeast(1.0)
        )
    }

    val result = remember(input) { FarmEconomicsCalculator.calculateBudget(input) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("farm_economics_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.TrendingUp, contentDescription = "Farm ROI", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Farm Economics, Input Budget & ROI",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Calculate profit margin, break-even price/kg, and agro-dealer retail packs.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Crop Preset Chips
        Text("Select Crop Enterprise:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FarmEconomicsCalculator.cropPresets.forEach { preset ->
                FilterChip(
                    selected = selectedPreset.id == preset.id,
                    onClick = {
                        selectedPreset = preset
                        expectedYieldText = preset.expectedYieldKgPerHa.toInt().toString()
                        marketPriceText = preset.typicalMarketPriceKesPerKg.toInt().toString()
                        chemBudgetText = preset.chemicalSprayCostKesPerHa.toInt().toString()
                        laborBudgetText = preset.laborAndOpsCostKesPerHa.toInt().toString()
                    },
                    label = { Text(preset.cropName) },
                    modifier = Modifier.testTag("econ_crop_${preset.id}")
                )
            }
        }

        // Parameter Inputs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = areaHaText,
                onValueChange = { areaHaText = it },
                label = { Text("Area (Hectares)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.weight(1f).testTag("input_econ_hectares")
            )
            OutlinedTextField(
                value = bagPriceText,
                onValueChange = { bagPriceText = it },
                label = { Text("50kg Fert Bag (KES)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = expectedYieldText,
                onValueChange = { expectedYieldText = it },
                label = { Text("Yield (kg/ha)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = marketPriceText,
                onValueChange = { marketPriceText = it },
                label = { Text("Price (KES/kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = chemBudgetText,
                onValueChange = { chemBudgetText = it },
                label = { Text("Sprays/ha (KES)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = laborBudgetText,
                onValueChange = { laborBudgetText = it },
                label = { Text("Labor/ha (KES)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        // Financial Summary Card
        val profitColor = if (result.netProfitKes >= 0) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.5.dp, profitColor.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Projected Financial Summary", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Surface(shape = RoundedCornerShape(8.dp), color = profitColor.copy(alpha = 0.14f)) {
                        Text(
                            text = "ROI: ${String.format("%.1f", result.roiPercent)}%",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = profitColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                HorizontalDivider()
                BudgetStatRow("Total Input Cost", "KES ${String.format("%,.0f", result.totalInputCostKes)}")
                BudgetStatRow("Expected Harvest", "${String.format("%,.0f", result.totalExpectedYieldKg)} kg (${String.format("%.2f", result.areaAcres)} acres)")
                BudgetStatRow("Gross Revenue", "KES ${String.format("%,.0f", result.expectedGrossRevenueKes)}")
                BudgetStatRow("Estimated Net Margin", "KES ${String.format("%,.0f", result.netProfitKes)}", highlightColor = profitColor)
                HorizontalDivider()
                BudgetStatRow("Break-Even Price", "KES ${String.format("%.2f", result.breakEvenPriceKesPerKg)} / kg")
                BudgetStatRow("Break-Even Yield", "${String.format("%,.0f", result.breakEvenYieldKgTotal)} kg total")
            }
        }

        // Agro-Dealer Shopping List
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Agro-Dealer Retail Pack Shopping List", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
        }

        result.shoppingList.forEach { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.itemName, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        Text("${item.category} • ${item.quantityText}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(
                        text = "KES ${String.format("%,.0f", item.estimatedCostKes)}",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (onSaveBudgetSummary != null) {
            androidx.compose.material3.Button(
                onClick = {
                    onSaveBudgetSummary(
                        "${selectedPreset.cropName} Budget (${String.format("%.2f", result.areaHectares)} ha)",
                        selectedPreset.cropName,
                        "Cost KES ${String.format("%,.0f", result.totalInputCostKes)} | Net KES ${String.format("%,.0f", result.netProfitKes)} | ROI ${String.format("%.1f", result.roiPercent)}% | Break-even KES ${String.format("%.2f", result.breakEvenPriceKesPerKg)}/kg"
                    )
                },
                modifier = Modifier.fillMaxWidth().testTag("btn_save_budget_cloud")
            ) {
                Icon(Icons.Default.AttachMoney, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Save Budget Snapshot to Farm Records")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BudgetStatRow(label: String, value: String, highlightColor: Color? = null) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = highlightColor ?: MaterialTheme.colorScheme.onSurface
        )
    }
}
