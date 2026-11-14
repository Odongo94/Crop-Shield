package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object FeedbackImprovementAreas {
    val areas = listOf(
        "Pest & Disease Accuracy",
        "AI Agronomist",
        "Local Languages (SW/LG/LUO)",
        "Dosage & Calculators",
        "Photo Diagnosis",
        "Maps & Locality",
        "Offline Speed & UI"
    )
}

@Composable
fun InlineHelpfulnessFeedbackBar(
    questionOrTopic: String,
    testTagPrefix: String = "feedback",
    onSubmitFeedback: (wasHelpful: Boolean, improvementArea: String, suggestion: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedVote by remember { mutableStateOf<Boolean?>(null) }
    var selectedArea by remember { mutableStateOf(FeedbackImprovementAreas.areas.first()) }
    var suggestionText by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
        border = BorderStroke(0.8.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier
            .fillMaxWidth()
            .testTag("${testTagPrefix}_bar")
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (submitted) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Asante! Your feedback helps us improve CropShield.",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Text(
                    text = "App still under development — was this helpful?",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { selectedVote = true },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("${testTagPrefix}_helpful_yes")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Yes, helpful",
                            tint = if (selectedVote == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Helpful", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = { selectedVote = false },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("${testTagPrefix}_helpful_no")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbDown,
                            contentDescription = "Needs work",
                            tint = if (selectedVote == false) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Needs Work", style = MaterialTheme.typography.labelSmall)
                    }
                }

                AnimatedVisibility(visible = selectedVote != null) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = if (selectedVote == true) {
                                "Great! Anything we can still improve for future updates?"
                            } else {
                                "Sorry it fell short — where should we improve?"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FeedbackImprovementAreas.areas.forEach { area ->
                                FilterChip(
                                    selected = selectedArea == area,
                                    onClick = { selectedArea = area },
                                    label = { Text(area, style = MaterialTheme.typography.labelSmall) }
                                )
                            }
                        }

                        OutlinedTextField(
                            value = suggestionText,
                            onValueChange = { suggestionText = it },
                            label = { Text("What should we improve? (optional)") },
                            placeholder = { Text("e.g. More Kiswahili examples or clearer spray rates") },
                            minLines = 2,
                            maxLines = 3,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("${testTagPrefix}_suggestion_input")
                        )

                        Button(
                            onClick = {
                                val helpful = selectedVote ?: true
                                val finalSuggestion = suggestionText.ifBlank {
                                    if (helpful) "Marked helpful on: ${questionOrTopic.take(80)}"
                                    else "Marked needs work on: ${questionOrTopic.take(80)}"
                                }
                                onSubmitFeedback(helpful, selectedArea, finalSuggestion)
                                submitted = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("${testTagPrefix}_submit_btn")
                        ) {
                            Text("Send Development Feedback", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
