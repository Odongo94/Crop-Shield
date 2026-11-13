package com.example.ui.screens.alerts

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.data.alerts.FarmNotificationHelper
import com.example.data.alerts.FarmReminderEngine
import com.example.data.voice.AgriculturalLanguagePack
import com.example.data.voice.AppLanguage
import com.example.model.alerts.AlertUrgency
import com.example.model.alerts.PhiSafetyStatus
import com.example.model.alerts.PoultryBatchTracker
import com.example.model.alerts.SprayPhiLog
import com.example.model.poultry.PoultryFlockType
import com.example.viewmodel.WeatherUiState
import java.util.concurrent.TimeUnit

@Composable
fun SmartAlertsView(
    phiLogs: List<SprayPhiLog>,
    poultryBatches: List<PoultryBatchTracker>,
    weatherState: WeatherUiState,
    appLanguage: AppLanguage,
    onAddPhiLog: (SprayPhiLog) -> Unit,
    onDeletePhiLog: (String) -> Unit,
    onAddPoultryBatch: (PoultryBatchTracker) -> Unit,
    onDeletePoultryBatch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showAddPhiDialog by remember { mutableStateOf(false) }
    var showAddFlockDialog by remember { mutableStateOf(false) }

    val phiStatuses = remember(phiLogs) {
        phiLogs.map { FarmReminderEngine.evaluatePhiAlert(it) }
    }
    val vaccineAlerts = remember(poultryBatches) {
        poultryBatches.flatMap { FarmReminderEngine.evaluatePoultryBatchAlerts(it) }
            .filter { it.urgency != AlertUrgency.COMPLETED }
            .sortedBy { it.dueInDays }
    }
    val weatherAlerts = remember(weatherState) {
        val result = (weatherState as? WeatherUiState.Success)?.result
        FarmReminderEngine.evaluateWeatherAlerts(result)
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val topPhi = phiStatuses.firstOrNull { it.safetyStatus != PhiSafetyStatus.SAFE_TO_HARVEST }
            val body = topPhi?.let {
                "${it.log.cropName} (${it.log.fieldBlock}): ${it.daysRemaining} days remaining before harvest after ${it.log.productName}."
            } ?: "CropShield smart reminders enabled for PHI countdowns, poultry vaccines, and blight alerts."
            FarmNotificationHelper.postFarmAlertNotification(context, 1001, "CropShield Safety Reminder", body)
            Toast.makeText(context, "Alert notification sent!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Notification permission denied. Alerts remain visible in-app.", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("smart_alerts_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Hero Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.NotificationsActive,
                            contentDescription = "Harvest Safety and Farm Alerts",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Harvest Safety (PHI) & Smart Farm Alerts",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = AgriculturalLanguagePack.uiLabels["phi_warning"]?.textFor(appLanguage)
                                    ?: "Track safe harvest dates, chick vaccinations, and weather triggers.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { showAddPhiDialog = true },
                            modifier = Modifier.testTag("btn_log_phi_spray")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Log Spray PHI")
                        }
                        OutlinedButton(
                            onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                    !FarmNotificationHelper.hasNotificationPermission(context)
                                ) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    val topPhi = phiStatuses.firstOrNull { it.safetyStatus != PhiSafetyStatus.SAFE_TO_HARVEST }
                                    val body = topPhi?.let {
                                        "${it.log.cropName} (${it.log.fieldBlock}): Wait ${it.daysRemaining}d (Safe ${it.safeDateFormatted})."
                                    } ?: "All logged plots are safe to harvest. Check poultry & weather cards below."
                                    FarmNotificationHelper.postFarmAlertNotification(
                                        context,
                                        1001,
                                        "CropShield PHI & Farm Alert",
                                        body
                                    )
                                    Toast.makeText(context, "Alert pushed to notification tray", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.testTag("btn_push_farm_notification")
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Send Alert")
                        }
                    }
                }
            }
        }

        // Section 1: PHI Countdown Cards
        item {
            Text(
                text = "1. Pre-Harvest Interval (PHI) Countdown (${phiStatuses.size})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        items(phiStatuses, key = { it.log.id }) { item ->
            val badgeColor = when (item.safetyStatus) {
                PhiSafetyStatus.DO_NOT_HARVEST -> MaterialTheme.colorScheme.error
                PhiSafetyStatus.CAUTION_NEAR_SAFE -> Color(0xFFE65100)
                PhiSafetyStatus.SAFE_TO_HARVEST -> Color(0xFF2E7D32)
            }
            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.2.dp, badgeColor.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth().testTag("phi_card_${item.log.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = badgeColor.copy(alpha = 0.14f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (item.safetyStatus == PhiSafetyStatus.SAFE_TO_HARVEST) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    contentDescription = item.safetyStatus.label,
                                    tint = badgeColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = item.safetyStatus.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = badgeColor
                                )
                            }
                        }
                        IconButton(
                            onClick = { onDeletePhiLog(item.log.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove PHI log", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${item.log.cropName} • ${item.log.fieldBlock}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sprayed: ${item.log.productName} (${item.log.activeIngredient}) • ${item.log.phiDays}d PHI",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (item.daysRemaining > 0) "${item.daysRemaining} days left (${item.hoursRemaining} hrs)" else "Cleared for market harvest",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = badgeColor
                        )
                        Text(
                            text = "Safe Date: ${item.safeDateFormatted}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Section 2: Poultry Batch Vaccine Reminders
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2. Chick Vaccination Due Dates",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                TextButton(
                    onClick = { showAddFlockDialog = true },
                    modifier = Modifier.testTag("btn_add_poultry_batch")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Add Flock")
                }
            }
        }

        items(poultryBatches, key = { it.id }) { batch ->
            val batchAlerts = vaccineAlerts.filter { it.batchId == batch.id }.take(3)
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Egg, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(batch.batchName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text("${batch.birdCount} birds • ${batch.flockType}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        IconButton(onClick = { onDeletePoultryBatch(batch.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete flock", modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    batchAlerts.forEach { vax ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${vax.targetDisease} (Day ${vax.targetAgeDays})",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${vax.vaccineName} • ${vax.route}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (vax.dueInDays <= 1) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.secondaryContainer
                            ) {
                                Text(
                                    text = when {
                                        vax.dueInDays <= 0 -> "DUE NOW"
                                        else -> "In ${vax.dueInDays}d (${vax.dueDateFormatted})"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Weather-Triggered Alerts
        item {
            Text(
                text = "3. Live Weather Spray & Blight Triggers",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        if (weatherAlerts.isEmpty()) {
            item {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Cloud, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Open Farm Weather tab to refresh live county forecast alerts.", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        } else {
            items(weatherAlerts, key = { it.id }) { alert ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (alert.severity == AlertUrgency.CRITICAL)
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.55f)
                        else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (alert.severity == AlertUrgency.CRITICAL) Icons.Default.Warning else Icons.Default.Schedule,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(alert.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(alert.recommendation, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }

    if (showAddPhiDialog) {
        AddPhiLogDialog(
            onDismiss = { showAddPhiDialog = false },
            onSave = { log ->
                onAddPhiLog(log)
                showAddPhiDialog = false
            }
        )
    }

    if (showAddFlockDialog) {
        AddPoultryBatchDialog(
            onDismiss = { showAddFlockDialog = false },
            onSave = { batch ->
                onAddPoultryBatch(batch)
                showAddFlockDialog = false
            }
        )
    }
}

@Composable
private fun AddPhiLogDialog(
    onDismiss: () -> Unit,
    onSave: (SprayPhiLog) -> Unit
) {
    var crop by remember { mutableStateOf("Tomato") }
    var block by remember { mutableStateOf("Block A") }
    var product by remember { mutableStateOf("Ridomil Gold MZ 68 WG") }
    var ingredient by remember { mutableStateOf("Metalaxyl-M + Mancozeb") }
    var phiDaysText by remember { mutableStateOf("7") }
    var daysAgoText by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Pesticide Spray (PHI)", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = crop, onValueChange = { crop = it }, label = { Text("Crop") }, singleLine = true)
                OutlinedTextField(value = block, onValueChange = { block = it }, label = { Text("Field / Block") }, singleLine = true)
                OutlinedTextField(value = product, onValueChange = { product = it }, label = { Text("Product Name") }, singleLine = true)
                OutlinedTextField(value = ingredient, onValueChange = { ingredient = it }, label = { Text("Active Ingredient") }, singleLine = true)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = phiDaysText,
                        onValueChange = { phiDaysText = it },
                        label = { Text("PHI (Days)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = daysAgoText,
                        onValueChange = { daysAgoText = it },
                        label = { Text("Days Ago") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val phi = phiDaysText.toIntOrNull() ?: 7
                val ago = daysAgoText.toLongOrNull() ?: 0L
                onSave(
                    SprayPhiLog(
                        id = "phi_${System.currentTimeMillis()}",
                        cropName = crop.ifBlank { "Crop" },
                        fieldBlock = block.ifBlank { "Main Plot" },
                        productName = product.ifBlank { "Fungicide" },
                        activeIngredient = ingredient.ifBlank { "Active" },
                        phiDays = phi.coerceAtLeast(0),
                        sprayedAtMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(ago.coerceAtLeast(0))
                    )
                )
            }) { Text("Save PHI Log") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
private fun AddPoultryBatchDialog(
    onDismiss: () -> Unit,
    onSave: (PoultryBatchTracker) -> Unit
) {
    var batchName by remember { mutableStateOf("Layers Batch #2") }
    var flockType by remember { mutableStateOf(PoultryFlockType.LAYERS) }
    var countText by remember { mutableStateOf("200") }
    var ageDaysText by remember { mutableStateOf("5") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Track Poultry Batch Vaccines", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = batchName, onValueChange = { batchName = it }, label = { Text("Flock Batch Name") }, singleLine = true)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    PoultryFlockType.values().forEach { type ->
                        FilterChip(
                            selected = flockType == type,
                            onClick = { flockType = type },
                            label = { Text(type.name.take(7)) }
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = countText,
                        onValueChange = { countText = it },
                        label = { Text("Bird Count") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = ageDaysText,
                        onValueChange = { ageDaysText = it },
                        label = { Text("Current Age (Days)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val count = countText.toIntOrNull() ?: 100
                val age = ageDaysText.toLongOrNull() ?: 1L
                onSave(
                    PoultryBatchTracker(
                        id = "flock_${System.currentTimeMillis()}",
                        batchName = batchName.ifBlank { "Poultry Batch" },
                        flockType = flockType.name,
                        birdCount = count.coerceAtLeast(1),
                        hatchDateMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(age.coerceAtLeast(0))
                    )
                )
            }) { Text("Start Tracking") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
