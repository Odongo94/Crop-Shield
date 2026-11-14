package com.example.ui.screens.admin

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.DeveloperInfo
import com.example.model.MapLayerType
import com.example.model.cloud.AdminNotification
import com.example.model.cloud.AppFeedback
import com.example.model.cloud.DirectMessage
import com.example.model.cloud.DirectThread
import com.example.model.cloud.FarmerRegistryEntry
import com.example.ui.components.AdminFarmerMapCanvas

@Composable
fun AdminConsoleScreen(
    isSuperAdmin: Boolean,
    farmerRegistry: List<FarmerRegistryEntry>,
    notifications: List<AdminNotification>,
    directThreads: List<DirectThread>,
    activeDirectFarmerId: String?,
    directMessages: List<DirectMessage>,
    feedbackList: List<AppFeedback>,
    onSelectDirectFarmer: (String) -> Unit,
    onSendDirectReply: (farmerId: String, farmerName: String, county: String, locality: String, text: String) -> Unit,
    onMarkNotificationRead: (String) -> Unit,
    onOpenCommunityChat: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if (!isSuperAdmin) {
        BoxAccessRestricted(modifier = modifier)
        return
    }

    var activeTab by remember { mutableIntStateOf(0) }
    val unreadAlerts = remember(notifications) { notifications.count { !it.isRead } }
    val helpfulPercent = remember(feedbackList) {
        if (feedbackList.isEmpty()) 100 else (feedbackList.count { it.wasHelpful } * 100) / feedbackList.size
    }

    fun launchEmailDigest() {
        val body = buildString {
            appendLine("CropShield IPM — Super Admin Farmer & Activity Digest")
            appendLine("Total Registered Farmers: ${farmerRegistry.size}")
            appendLine("Unread Notifications: $unreadAlerts")
            appendLine("Feedback Helpfulness: $helpfulPercent% (${feedbackList.size} responses)")
            appendLine()
            appendLine("--- REGISTERED FARMERS (EMAIL & LOCALITY) ---")
            farmerRegistry.forEach { f ->
                appendLine("• ${f.displayName} | ${f.email} | ${f.locality}, ${f.county} | Crops: ${f.primaryCrops} | Sign-ins: ${f.signInCount}")
            }
            appendLine()
            appendLine("--- RECENT ALERTS ---")
            notifications.take(15).forEach { n ->
                appendLine("• [${n.eventType}] ${n.summary}")
            }
        }
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${DeveloperInfo.EMAIL}")
            putExtra(Intent.EXTRA_SUBJECT, "CropShield Farmers Digest (${farmerRegistry.size} farmers, $unreadAlerts alerts)")
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "No email app found to send digest", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_console_screen")
    ) {
        // Super Admin KPI Strip
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Super Admin Console (${DeveloperInfo.NAME})",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${farmerRegistry.size} Farmers • $unreadAlerts Unread Alerts • $helpfulPercent% Helpful",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = { launchEmailDigest() },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("btn_admin_email_digest")
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Email Digest")
                    }
                }
            }
        }

        ScrollableTabRow(
            selectedTabIndex = activeTab,
            edgePadding = 8.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                icon = { Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Farmers Map (${farmerRegistry.size})") },
                modifier = Modifier.testTag("admin_tab_map")
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                icon = { Icon(Icons.Default.People, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Farmer Emails DB") },
                modifier = Modifier.testTag("admin_tab_database")
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                icon = { Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Email Alerts ($unreadAlerts)") },
                modifier = Modifier.testTag("admin_tab_alerts")
            )
            Tab(
                selected = activeTab == 3,
                onClick = { activeTab = 3 },
                icon = { Icon(Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Direct & Feedback") },
                modifier = Modifier.testTag("admin_tab_inbox_feedback")
            )
        }

        when (activeTab) {
            0 -> AdminFarmersMapTab(
                farmers = farmerRegistry,
                onOpenDirectThread = { farmer ->
                    onSelectDirectFarmer(farmer.uid.ifBlank { farmer.id })
                    activeTab = 3
                }
            )

            1 -> AdminFarmerDatabaseTab(
                farmers = farmerRegistry,
                onOpenDirectThread = { farmer ->
                    onSelectDirectFarmer(farmer.uid.ifBlank { farmer.id })
                    activeTab = 3
                }
            )

            2 -> AdminAlertsTab(
                notifications = notifications,
                onMarkRead = onMarkNotificationRead,
                onSendDigestEmail = { launchEmailDigest() }
            )

            else -> AdminInboxAndFeedbackTab(
                directThreads = directThreads,
                activeFarmerId = activeDirectFarmerId,
                directMessages = directMessages,
                feedbackList = feedbackList,
                helpfulPercent = helpfulPercent,
                onSelectThread = onSelectDirectFarmer,
                onSendDirectReply = onSendDirectReply,
                onOpenCommunityChat = onOpenCommunityChat
            )
        }
    }
}

@Composable
private fun BoxAccessRestricted(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("admin_access_restricted"),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Restricted Access",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(56.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Restricted Advisory Console",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This management console is restricted to authorized administrators.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AdminFarmersMapTab(
    farmers: List<FarmerRegistryEntry>,
    onOpenDirectThread: (FarmerRegistryEntry) -> Unit
) {
    val context = LocalContext.current
    var selectedCountyFilter by remember { mutableStateOf<String?>(null) }
    var selectedFarmer by remember(farmers) { mutableStateOf<FarmerRegistryEntry?>(farmers.firstOrNull()) }
    var centerLat by remember { mutableDoubleStateOf(-0.25) }
    var centerLng by remember { mutableDoubleStateOf(36.9) }
    var zoomLevel by remember { mutableIntStateOf(6) }
    var layerType by remember { mutableStateOf(MapLayerType.GOOGLE_SATELLITE) }

    val counties = remember(farmers) {
        farmers.map { it.county }.filter { it.isNotBlank() }.distinct().sorted()
    }
    val visibleFarmers = remember(farmers, selectedCountyFilter) {
        if (selectedCountyFilter == null) farmers else farmers.filter { it.county == selectedCountyFilter }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (counties.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedCountyFilter == null,
                        onClick = {
                            selectedCountyFilter = null
                            centerLat = -0.25
                            centerLng = 36.9
                            zoomLevel = 6
                        },
                        label = { Text("All Areas (${farmers.size})") }
                    )
                    counties.forEach { county ->
                        FilterChip(
                            selected = selectedCountyFilter == county,
                            onClick = {
                                selectedCountyFilter = county
                                farmers.firstOrNull { it.county == county }?.let { f ->
                                    centerLat = f.latitude
                                    centerLng = f.longitude
                                    zoomLevel = 9
                                    selectedFarmer = f
                                }
                            },
                            label = { Text(county.substringBefore(" (")) }
                        )
                    }
                }
            }
        }

        item {
            AdminFarmerMapCanvas(
                farmers = visibleFarmers,
                selectedFarmerId = selectedFarmer?.uid,
                centerLat = centerLat,
                centerLng = centerLng,
                zoomLevel = zoomLevel,
                layerType = layerType,
                onCenterChanged = { lat, lng ->
                    centerLat = lat
                    centerLng = lng
                },
                onZoomChanged = { zoomLevel = it },
                onLayerChanged = { layerType = it },
                onResetToKenya = {
                    centerLat = -0.25
                    centerLng = 36.9
                    zoomLevel = 6
                },
                onFarmerSelected = { f ->
                    selectedFarmer = f
                    centerLat = f.latitude
                    centerLng = f.longitude
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )
        }

        item {
            selectedFarmer?.let { f ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f)),
                    modifier = Modifier.fillMaxWidth().testTag("admin_selected_farmer_card")
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${f.displayName} — ${f.farmName.ifBlank { "Shamba" }}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text("Email: ${f.email}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        Text("Locality: ${f.locality}, ${f.county}", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "Coordinates: ${String.format("%.4f, %.4f", f.latitude, f.longitude)} • Sign-ins: ${f.signInCount}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        if (f.primaryCrops.isNotBlank()) {
                            Text("Crops: ${f.primaryCrops}", style = MaterialTheme.typography.bodySmall)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onOpenDirectThread(f) },
                                modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                            ) {
                                Text("Direct Chat")
                            }
                            OutlinedButton(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${f.email}")).apply {
                                        putExtra(Intent.EXTRA_SUBJECT, "CropShield Advisory for ${f.displayName}")
                                    }
                                    try {
                                        context.startActivity(intent)
                                    } catch (_: Exception) {
                                        Toast.makeText(context, "Email app unavailable", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                            ) {
                                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Email")
                            }
                        }
                    }
                }
            } ?: OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "No registered farmers yet. Farmers appear here automatically as soon as they sign up with their email.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
private fun AdminFarmerDatabaseTab(
    farmers: List<FarmerRegistryEntry>,
    onOpenDirectThread: (FarmerRegistryEntry) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val filtered = remember(farmers, searchQuery) {
        val q = searchQuery.trim()
        if (q.isBlank()) farmers
        else farmers.filter {
            it.displayName.contains(q, true) ||
                it.email.contains(q, true) ||
                it.locality.contains(q, true) ||
                it.county.contains(q, true) ||
                it.primaryCrops.contains(q, true)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                label = { Text("Search farmers by email, name, locality, or crop") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("input_admin_db_search")
            )
        }

        item {
            Text(
                text = "Private Super Admin Farmer Email Registry (${filtered.size})",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        items(filtered, key = { "reg_${it.uid.ifBlank { it.id }}" }) { farmer ->
            OutlinedCard(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().testTag("farmer_registry_row_${farmer.uid}")
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(farmer.displayName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                            Text(
                                text = "${farmer.signInCount} sign-ins",
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = farmer.email,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${farmer.farmName} • ${farmer.locality}, ${farmer.county}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (farmer.primaryCrops.isNotBlank()) {
                        Text(
                            text = "Crops: ${farmer.primaryCrops}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 4.dp)) {
                        OutlinedButton(
                            onClick = { onOpenDirectThread(farmer) },
                            modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                        ) {
                            Text("Direct Chat")
                        }
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${farmer.email}"))
                                try {
                                    context.startActivity(intent)
                                } catch (_: Exception) {
                                    Toast.makeText(context, "Email app unavailable", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                        ) {
                            Text("Email Farmer")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminAlertsTab(
    notifications: List<AdminNotification>,
    onMarkRead: (String) -> Unit,
    onSendDigestEmail: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Super Admin Sign-Up & Activity Notifications",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Every farmer sign-up, sign-in, direct chat, and feedback item is logged in real time here. Tap below to email a full farmer digest to ${DeveloperInfo.EMAIL}.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(
                        onClick = onSendDigestEmail,
                        modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Email Full Farmer Activity Digest")
                    }
                }
            }
        }

        if (notifications.isEmpty()) {
            item {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No activity notifications yet.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        } else {
            items(notifications, key = { "notif_${it.id}" }) { notif ->
                OutlinedCard(
                    border = if (!notif.isRead) BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary) else BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth().testTag("admin_alert_${notif.id}")
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (!notif.isRead) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = notif.eventType,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (!notif.isRead) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }
                            if (!notif.isRead) {
                                IconButton(
                                    onClick = { onMarkRead(notif.id) },
                                    modifier = Modifier.minimumInteractiveComponentSize()
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = "Mark Alert Read", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                        Text(notif.summary, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                        Text(
                            text = "${notif.farmerName} (${notif.farmerEmail}) • ${notif.locality}, ${notif.county}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AdminInboxAndFeedbackTab(
    directThreads: List<DirectThread>,
    activeFarmerId: String?,
    directMessages: List<DirectMessage>,
    feedbackList: List<AppFeedback>,
    helpfulPercent: Int,
    onSelectThread: (String) -> Unit,
    onSendDirectReply: (farmerId: String, farmerName: String, county: String, locality: String, text: String) -> Unit,
    onOpenCommunityChat: () -> Unit
) {
    var subMode by remember { mutableIntStateOf(0) } // 0 = Direct Inbox, 1 = Feedback Analytics
    var replyText by remember { mutableStateOf("") }
    val selectedThread = remember(directThreads, activeFarmerId) {
        directThreads.firstOrNull { it.farmerId == activeFarmerId || it.id == activeFarmerId } ?: directThreads.firstOrNull()
    }

    Column(modifier = Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = subMode == 0,
                onClick = { subMode = 0 },
                label = { Text("Direct Inbox (${directThreads.size})") },
                modifier = Modifier.weight(1f).testTag("admin_sub_direct_inbox")
            )
            FilterChip(
                selected = subMode == 1,
                onClick = { subMode = 1 },
                leadingIcon = { Icon(Icons.Default.Feedback, contentDescription = null, modifier = Modifier.size(16.dp)) },
                label = { Text("Feedback (${feedbackList.size})") },
                modifier = Modifier.weight(1f).testTag("admin_sub_feedback")
            )
            OutlinedButton(onClick = onOpenCommunityChat, modifier = Modifier.minimumInteractiveComponentSize()) {
                Text("Community")
            }
        }

        if (subMode == 0) {
            if (directThreads.isEmpty()) {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No direct 1-on-1 farmer threads yet. Farmers can message you from Community -> Direct Admin.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                // Thread Selector Chips
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    directThreads.forEach { thread ->
                        val isSelected = selectedThread?.farmerId == thread.farmerId
                        FilterChip(
                            selected = isSelected,
                            onClick = { onSelectThread(thread.farmerId) },
                            label = {
                                Text(
                                    text = "${thread.farmerName}${if (thread.unreadByAdmin) " • New" else ""}",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }

                selectedThread?.let { thread ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Chatting with ${thread.farmerName} (${thread.locality}, ${thread.county})",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Latest: ${thread.lastMessagePreview}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(directMessages, key = { it.id }) { msg ->
                            val isAdminMsg = msg.senderRole == "super_admin"
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isAdminMsg) Arrangement.End else Arrangement.Start
                            ) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isAdminMsg) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = msg.senderName,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isAdminMsg) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f) else MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = msg.text,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = if (isAdminMsg) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = replyText,
                            onValueChange = { replyText = it },
                            placeholder = { Text("Reply to ${thread.farmerName}...") },
                            modifier = Modifier.weight(1f).testTag("input_admin_direct_reply")
                        )
                        IconButton(
                            onClick = {
                                if (replyText.isNotBlank()) {
                                    onSendDirectReply(thread.farmerId, thread.farmerName, thread.county, thread.locality, replyText)
                                    replyText = ""
                                }
                            },
                            enabled = replyText.isNotBlank(),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .clip(CircleShape)
                                .background(
                                    if (replyText.isNotBlank()) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .testTag("btn_admin_send_direct_reply")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Admin Reply",
                                tint = if (replyText.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        } else {
            // Feedback Analytics & Submissions
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Farmer Helpfulness Score: $helpfulPercent% (${feedbackList.size} submissions)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            val topArea = feedbackList.groupingBy { it.improvementArea }.eachCount().maxByOrNull { it.value }?.key
                            if (topArea != null) {
                                Text("Top Requested Improvement Area: $topArea", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                items(feedbackList, key = { "afb_${it.id}" }) { fb ->
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${fb.farmerName} (${fb.county.ifBlank { "Kenya" }})",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (fb.wasHelpful) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                                ) {
                                    Text(
                                        text = if (fb.wasHelpful) "👍 Helpful" else "👎 Needs Work",
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text("${fb.featureSource} • ${fb.improvementArea}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            if (fb.questionOrTopic.isNotBlank()) {
                                Text("Asked: ${fb.questionOrTopic}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            }
                            Text(fb.improvementSuggestion, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
