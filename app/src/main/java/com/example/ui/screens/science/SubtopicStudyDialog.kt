package com.example.ui.screens.science

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.science.CurriculumSubtopic

@Composable
fun SubtopicStudyDialog(
    subtopic: CurriculumSubtopic,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .testTag("subtopic_study_dialog_${subtopic.id}"),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Header bar
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Text(
                                    text = subtopic.code,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = subtopic.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Detailed Academic & Field Master Guide",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }

                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close Dialog")
                        }
                    }
                }

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 1. Core Definition
                    StudySectionCard(
                        title = "1. Core Scientific Definition",
                        icon = Icons.Default.Science,
                        accentColor = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = subtopic.coreDefinition,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                    }

                    // 2. Biological & Chemical Foundation
                    if (subtopic.biologicalAndChemicalDetails.isNotEmpty()) {
                        StudySectionCard(
                            title = "2. Biological & Chemical Foundation",
                            icon = Icons.Default.Science,
                            accentColor = Color(0xFF00796B)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                subtopic.biologicalAndChemicalDetails.forEach { detail ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text("• ", fontWeight = FontWeight.Bold, color = Color(0xFF00796B), fontSize = 14.sp)
                                        Text(text = detail, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 19.sp)
                                    }
                                }
                            }
                        }
                    }

                    // 3. Key Mechanisms & Formulas
                    if (subtopic.keyMechanismsAndFormulas.isNotEmpty()) {
                        StudySectionCard(
                            title = "3. Key Mechanisms, Pathways & Chemical Reactions",
                            icon = Icons.Default.Calculate,
                            accentColor = Color(0xFF1565C0)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                subtopic.keyMechanismsAndFormulas.forEach { formula ->
                                    Surface(
                                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = formula,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(10.dp),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 4. In-Depth Scientific Elaboration
                    StudySectionCard(
                        title = "4. In-Depth Comprehensive Elaboration",
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        accentColor = Color(0xFF6A1B9A)
                    ) {
                        Text(
                            text = subtopic.detailedElaboration,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 20.sp
                        )
                    }

                    // 5. Step-by-Step Field Protocols & SOPs
                    if (subtopic.stepByStepFieldProtocols.isNotEmpty()) {
                        StudySectionCard(
                            title = "5. Practical Field Protocols & SOPs",
                            icon = Icons.Default.PrecisionManufacturing,
                            accentColor = Color(0xFFE65100)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                subtopic.stepByStepFieldProtocols.forEachIndexed { idx, protocol ->
                                    Surface(
                                        color = Color(0xFFFFF3E0),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, Color(0xFFFFB74D).copy(alpha = 0.5f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                                            Surface(
                                                shape = RoundedCornerShape(4.dp),
                                                color = Color(0xFFE65100)
                                            ) {
                                                Text(
                                                    text = "SOP ${idx + 1}",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(
                                                text = protocol,
                                                fontSize = 12.sp,
                                                color = Color(0xFF3E2723),
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // 6. Clinical & Diagnostic Signs
                    if (subtopic.clinicalOrDiagnosticSigns.isNotEmpty()) {
                        StudySectionCard(
                            title = "6. Clinical & Diagnostic Indicators",
                            icon = Icons.Default.Healing,
                            accentColor = Color(0xFFC2185B)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                subtopic.clinicalOrDiagnosticSigns.forEach { sign ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text("🔍 ", fontSize = 13.sp)
                                        Text(text = sign, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp)
                                    }
                                }
                            }
                        }
                    }

                    // 7. Solved Board Exam Worked Problems
                    if (subtopic.boardExamWorkedProblems.isNotEmpty()) {
                        StudySectionCard(
                            title = "7. Solved Board Exam Calculations & Worked Problems",
                            icon = Icons.Default.Calculate,
                            accentColor = Color(0xFF2E7D32)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                subtopic.boardExamWorkedProblems.forEach { problem ->
                                    Surface(
                                        color = Color(0xFFE8F5E9),
                                        shape = RoundedCornerShape(8.dp),
                                        border = BorderStroke(1.dp, Color(0xFF81C784)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = problem,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF1B5E20),
                                            modifier = Modifier.padding(10.dp),
                                            lineHeight = 18.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 8. Licensure Exam Review Questions
                    if (subtopic.commonExamQuestions.isNotEmpty()) {
                        StudySectionCard(
                            title = "8. Licensure & Academic Exam Review Questions",
                            icon = Icons.Default.School,
                            accentColor = Color(0xFF0277BD)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                subtopic.commonExamQuestions.forEachIndexed { i, q ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text(
                                            text = "Q${i + 1}: ",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0277BD)
                                        )
                                        Text(text = q, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 17.sp)
                                    }
                                }
                            }
                        }
                    }

                    // 9. Practical Field Takeaway
                    Surface(
                        color = Color(0xFF2E7D32).copy(alpha = 0.12f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Practical Agronomist Field Takeaway:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32)
                                )
                                Text(
                                    text = subtopic.practicalTakeaway,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 17.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }

                    // 10. Cited Textbook Reference
                    if (subtopic.authoritativeTextbookRef.isNotEmpty()) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Authoritative Textbook Reference:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = subtopic.authoritativeTextbookRef,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Bottom Close button
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Done Reading")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StudySectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = accentColor.copy(alpha = 0.15f),
                    modifier = Modifier.size(26.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
