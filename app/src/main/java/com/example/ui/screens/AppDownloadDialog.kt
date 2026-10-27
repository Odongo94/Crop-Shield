package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Launch
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SystemUpdate
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Dialog facilitating direct download of CropShield APK from GitHub releases,
 * providing 404 troubleshooting, customizable repository path, and update flow.
 */
@Composable
fun AppDownloadDialog(
    onDismiss: () -> Unit,
    currentVersion: String = "v4.0.0 (v4)"
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("cropshield_github_config", Context.MODE_PRIVATE) }
    
    var repoPath by remember { 
        mutableStateOf(sharedPrefs.getString("repo_path", "odongovincento/cropshield-ipm") ?: "odongovincento/cropshield-ipm") 
    }
    var isEditingRepo by remember { mutableStateOf(false) }
    var tempRepoInput by remember { mutableStateOf(repoPath) }
    var show404Help by remember { mutableStateOf(true) }

    // Dynamically generated links based on configured repository
    val allReleasesUrl = "https://github.com/$repoPath/releases"
    val v4DirectUrl = "https://github.com/$repoPath/releases/download/v4/CropShield-v4.apk"
    val v4DebugUrl = "https://github.com/$repoPath/releases/download/v4/app-debug.apk"
    val v3DirectUrl = "https://github.com/$repoPath/releases/download/v3/CropShield-v3.apk"
    val v3DebugUrl = "https://github.com/$repoPath/releases/download/v3/app-debug.apk"
    val latestApkUrl = "https://github.com/$repoPath/releases/latest/download/CropShield-latest.apk"
    val repoUrl = "https://github.com/$repoPath"

    val shareText = """
        🌾 Download CropShield IPM Android App (v4):
        
        GitHub Releases Page (All Versions):
        $allReleasesUrl
        
        Direct v4 APK Download:
        $v4DirectUrl
        
        Features:
        • Soil Classification & USDA Texture Triangle
        • Crop Rotation Sustainability Designer
        • 16L–2000L Knapsack & Tractor Tank Dosing
        • Offline Google Satellite Farm GIS Mapping
        • IPM Entomology Pest & Pathology Scanner
        • Google Scholar Agricultural Science Journals
        
        Signed off by Crop Scientist Odongo Vincent.
    """.trimIndent()

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.SystemUpdate,
                contentDescription = "App Updates & Download",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Download CropShield APK",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Release Version: $currentVersion",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Version Release Badge Card
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "v4",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Column {
                            Text(
                                text = "Active Build: CropShield v4 (Release v4.0.0)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "GitHub Repo: github.com/$repoPath",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // GitHub Repository Settings (Editable if username or repo name differs)
                OutlinedCard(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Repository: $repoPath",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            TextButton(onClick = { isEditingRepo = !isEditingRepo }) {
                                Icon(
                                    imageVector = if (isEditingRepo) Icons.Default.Check else Icons.Default.Edit,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(if (isEditingRepo) "Done" else "Change", fontSize = 11.sp)
                            }
                        }

                        if (isEditingRepo) {
                            OutlinedTextField(
                                value = tempRepoInput,
                                onValueChange = { tempRepoInput = it },
                                label = { Text("username/repository", fontSize = 11.sp) },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                textStyle = MaterialTheme.typography.bodySmall
                            )
                            Button(
                                onClick = {
                                    val cleaned = tempRepoInput.trim().removePrefix("https://github.com/").removeSuffix("/")
                                    if (cleaned.isNotBlank()) {
                                        repoPath = cleaned
                                        sharedPrefs.edit().putString("repo_path", cleaned).apply()
                                    }
                                    isEditingRepo = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Save Repository Path")
                            }
                        }
                    }
                }

                // 404 EXPLANATION & FIX CARD (Directly addresses the screenshot error)
                ElevatedCard(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "404 Error Guide",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Why did GitHub show '404'?",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                            IconButton(onClick = { show404Help = !show404Help }) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = "Toggle Help",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        AnimatedVisibility(visible = show404Help) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "1. GitHub releases only package source code (.zip) by default. An APK must be attached to the release.\n" +
                                           "2. If release v4 hasn't been created yet, use the 'Releases Page' or 'v3 APK' buttons below.\n" +
                                           "3. To attach the APK in 30 seconds on GitHub: Open your release > Edit > Drag & drop CropShield-v4.apk into 'Attach binaries' > Save.",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // 1. SAFEST ACTION: Open Releases Page (Guaranteed NEVER to 404 as long as repo exists)
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(allReleasesUrl))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_open_all_releases")
                ) {
                    Icon(imageVector = Icons.Default.Launch, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Open GitHub Releases Page (Safe)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                // 2. Direct Download v4 APK
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(v4DirectUrl))
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_direct_download_v4")
                ) {
                    Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download v4 APK (CropShield-v4.apk)")
                }

                // 3. Direct Download v3 APK (Previous Version)
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(v3DirectUrl))
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_direct_download_v3")
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download v3 APK (Previous Release)")
                }

                // 4. Share Link with Team
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share CropShield Download Link"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share Download Link with Team")
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Step-by-Step Installation Instructions
                Text(
                    text = "How to Install & Update on Android:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                InstallStepRow(
                    stepNum = "1",
                    title = "Tap Releases Page or APK Link",
                    description = "Tap 'Open GitHub Releases Page' to view published builds, or tap 'Download v4 APK'. If Chrome warns 'File might be harmful', select 'Download anyway'."
                )

                InstallStepRow(
                    stepNum = "2",
                    title = "Allow Install from Browser",
                    description = "When the APK download completes, tap Open. If prompted, toggle 'Allow from this source'."
                )

                InstallStepRow(
                    stepNum = "3",
                    title = "Install or Update",
                    description = "Tap 'Install' (or 'Update'). When updating from v3 to v4, all your saved scouting records, farm boundaries, and field notes are 100% preserved!"
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
private fun InstallStepRow(stepNum: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNum,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
