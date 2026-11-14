package com.example.ui.screens.cloud

import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Forum
import com.example.data.AgronomyCatalog
import com.example.data.firebase.GoogleAuthHelper
import com.example.data.voice.AppLanguage
import com.example.model.KenyaAgriculturalCounty
import com.example.model.cloud.CloudFarmRecord
import com.example.model.cloud.ExtensionReport
import com.example.ui.components.AuthSignUpCard
import com.example.ui.components.FarmerRegistrationInput
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@Composable
fun CloudSyncScreen(
    currentUser: FirebaseUser?,
    cloudRecords: List<CloudFarmRecord>,
    myReports: List<ExtensionReport>,
    advisorInboxReports: List<ExtensionReport>,
    appLanguage: AppLanguage,
    selectedCountyName: String,
    isSyncing: Boolean,
    syncStatusMessage: String?,
    onLanguageSelected: (AppLanguage) -> Unit,
    onSignedIn: (FirebaseUser) -> Unit,
    onBackupAllToCloud: () -> Unit,
    onDeleteCloudRecord: (String) -> Unit,
    onSubmitExtensionReport: (title: String, summary: String, advisorEmail: String) -> Unit,
    onReviewExtensionReport: (reportId: String, notes: String, status: String) -> Unit,
    onDeleteExtensionReport: (String) -> Unit,
    selectedCounty: KenyaAgriculturalCounty = AgronomyCatalog.kenyanCounties.first(),
    isSuperAdmin: Boolean = false,
    onAuthWithRegistration: (FirebaseUser, FarmerRegistrationInput) -> Unit = { u, _ -> onSignedIn(u) },
    onOpenCommunity: () -> Unit = {},
    onOpenAdminConsole: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var showShareReportDialog by remember { mutableStateOf(false) }
    var reviewingReport by remember { mutableStateOf<ExtensionReport?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("cloud_sync_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(4.dp)) }

        // Language Bar (available offline & online)
        item {
            OutlinedCard(shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Voice & Farm Advisory Language",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppLanguage.values().forEach { lang ->
                            FilterChip(
                                selected = appLanguage == lang,
                                onClick = { onLanguageSelected(lang) },
                                label = { Text("${lang.displayName} (${lang.nativeLabel})") },
                                modifier = Modifier.testTag("lang_chip_${lang.code.lowercase()}")
                            )
                        }
                    }
                }
            }
        }

        if (currentUser == null) {
            // Strict Auth Gate: NO Firestore calls or queries until Google Email Sign-Up / Sign-In completes
            item {
                AuthSignUpCard(
                    selectedCounty = selectedCounty,
                    onAuthCompleted = { user, reg ->
                        onAuthWithRegistration(user, reg)
                    }
                )
            }
        } else {
            // Signed-In Cloud Dashboard
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(Icons.Default.CloudDone, contentDescription = "Synced", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = currentUser.displayName ?: "CropShield Farmer",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${currentUser.email ?: "Google Account"} • $selectedCountyName",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                    )
                                }
                            }
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        GoogleAuthHelper.signOut(context)
                                        Toast.makeText(context, "Signed out of Cloud Sync", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.testTag("btn_google_sign_out")
                            ) {
                                Icon(Icons.Default.Logout, contentDescription = "Sign Out", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Sign Out")
                            }
                        }

                        syncStatusMessage?.let { msg ->
                            Text(msg, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onBackupAllToCloud,
                                enabled = !isSyncing,
                                modifier = Modifier.weight(1f).testTag("btn_backup_to_cloud")
                            ) {
                                Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isSyncing) "Syncing..." else "Back Up All Data")
                            }
                            OutlinedButton(
                                onClick = { showShareReportDialog = true },
                                modifier = Modifier.weight(1f).testTag("btn_share_extension_report")
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share Report")
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = onOpenCommunity,
                                modifier = Modifier.weight(1f).testTag("btn_cloud_open_community")
                            ) {
                                Icon(Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Community & Direct Chat")
                            }
                            if (isSuperAdmin) {
                                Button(
                                    onClick = onOpenAdminConsole,
                                    modifier = Modifier.weight(1f).testTag("btn_cloud_open_admin")
                                ) {
                                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Admin Map & DB")
                                }
                            }
                        }
                    }
                }
            }

            // Extension Officer Reports Shared by Me
            item {
                Text(
                    text = "Shared Extension Officer Reports (${myReports.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            if (myReports.isEmpty()) {
                item {
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "No extension reports shared yet. Tap 'Share Report' to send a field status summary to your agronomist or cooperative officer.",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            } else {
                items(myReports, key = { "rep_${it.id}" }) { rep ->
                    OutlinedCard(
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.secondaryContainer) {
                                    Text(
                                        text = rep.status,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                                IconButton(onClick = { onDeleteExtensionReport(rep.id) }, modifier = Modifier.size(28.dp)) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete report", modifier = Modifier.size(16.dp))
                                }
                            }
                            Text(rep.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(rep.summary, style = MaterialTheme.typography.bodySmall)
                            if (rep.advisorEmail.isNotBlank()) {
                                Text("Advisor: ${rep.advisorEmail}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            }
                            if (rep.advisorNotes.isNotBlank()) {
                                Surface(shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)) {
                                    Text(
                                        text = "Advisor Feedback: ${rep.advisorNotes}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Advisor Review Inbox (for verified advisors receiving shared farmer reports)
            if (advisorInboxReports.isNotEmpty()) {
                item {
                    Text(
                        text = "Extension Officer Review Inbox (${advisorInboxReports.size})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(advisorInboxReports, key = { "adv_${it.id}" }) { rep ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.45f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("${rep.title} • ${rep.farmerName} (${rep.county})", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(rep.summary, style = MaterialTheme.typography.bodySmall)
                            Button(onClick = { reviewingReport = rep }) {
                                Icon(Icons.Default.RateReview, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Add Advisor Recommendation")
                            }
                        }
                    }
                }
            }

            // Cloud Backed-up Records List
            item {
                Text(
                    text = "Backed-Up Farm Records (${cloudRecords.size})",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            items(cloudRecords, key = { "rec_${it.id}" }) { rec ->
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
                            Text("[${rec.recordType}] ${rec.title}", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            Text("${rec.cropOrSpecies} • ${rec.summary}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        IconButton(onClick = { onDeleteCloudRecord(rec.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete synced record", modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }

    if (showShareReportDialog) {
        var title by remember { mutableStateOf("Weekly Field & IPM Inspection") }
        var summary by remember { mutableStateOf("Requesting agronomist review on current field stage, spray PHI status, and soil liming plan.") }
        var advisorEmail by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showShareReportDialog = false },
            title = { Text("Share Extension Report", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Report Title") }, singleLine = true)
                    OutlinedTextField(value = advisorEmail, onValueChange = { advisorEmail = it }, label = { Text("Extension Officer Email (optional)") }, singleLine = true)
                    OutlinedTextField(value = summary, onValueChange = { summary = it }, label = { Text("Field Findings & Request") }, minLines = 3)
                }
            },
            confirmButton = {
                Button(onClick = {
                    onSubmitExtensionReport(title, summary, advisorEmail)
                    showShareReportDialog = false
                }) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Send Report")
                }
            },
            dismissButton = { TextButton(onClick = { showShareReportDialog = false }) { Text("Cancel") } }
        )
    }

    reviewingReport?.let { rep ->
        var notes by remember { mutableStateOf(rep.advisorNotes) }
        AlertDialog(
            onDismissRequest = { reviewingReport = null },
            title = { Text("Advisor Review: ${rep.title}", fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Agronomist Recommendation") },
                    minLines = 3
                )
            },
            confirmButton = {
                Button(onClick = {
                    onReviewExtensionReport(rep.id, notes, "REVIEWED")
                    reviewingReport = null
                }) { Text("Submit Review") }
            },
            dismissButton = { TextButton(onClick = { reviewingReport = null }) { Text("Cancel") } }
        )
    }
}
