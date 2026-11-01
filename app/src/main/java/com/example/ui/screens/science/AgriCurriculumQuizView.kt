package com.example.ui.screens.science

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.science.curriculum.AgriCurriculumBooksCatalog
import com.example.data.science.curriculum.CurriculumQuizCatalog
import com.example.model.science.CurriculumPillarType
import com.example.model.science.CurriculumQuizItem

@Composable
fun AgriCurriculumQuizView(
    modifier: Modifier = Modifier,
    onNavigateToBook: (String) -> Unit = {}
) {
    var selectedPillar by remember { mutableStateOf<CurriculumPillarType?>(null) }
    // Map of question ID to selected option index
    val selectedAnswers = remember { mutableStateMapOf<String, Int>() }
    // Set of submitted question IDs
    val submittedQuestions = remember { mutableStateMapOf<String, Boolean>() }

    val filteredQuizzes = remember(selectedPillar) {
        if (selectedPillar == null) {
            CurriculumQuizCatalog.quizItems
        } else {
            CurriculumQuizCatalog.quizItems.filter { it.pillar == selectedPillar }
        }
    }

    val totalAnswered = submittedQuestions.size
    val totalCorrect = submittedQuestions.count { (id, isSubmitted) ->
        if (!isSubmitted) false else {
            val q = CurriculumQuizCatalog.quizItems.find { it.id == id }
            val chosen = selectedAnswers[id]
            q != null && chosen == q.correctIndex
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("agri_curriculum_quiz_view")
    ) {
        // Pillar filter row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedPillar == null,
                onClick = { selectedPillar = null },
                label = { Text("All 6 Pillars (${CurriculumQuizCatalog.quizItems.size})") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
            CurriculumPillarType.values().forEach { pillar ->
                val count = CurriculumQuizCatalog.quizItems.count { it.pillar == pillar }
                FilterChip(
                    selected = selectedPillar == pillar,
                    onClick = { selectedPillar = if (selectedPillar == pillar) null else pillar },
                    label = { Text("${pillar.shortName} ($count)") },
                    leadingIcon = {
                        Icon(pillar.icon, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                )
            }
        }

        // Score summary banner
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Licensure & Board Exam Practice",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Score: $totalCorrect / $totalAnswered submitted",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }

                if (submittedQuestions.isNotEmpty()) {
                    OutlinedButton(
                        onClick = {
                            selectedAnswers.clear()
                            submittedQuestions.clear()
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset", fontSize = 11.sp)
                    }
                }
            }
        }

        // Quiz items
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            itemsIndexed(filteredQuizzes, key = { _, item -> item.id }) { index, item ->
                QuizItemCard(
                    item = item,
                    questionNumber = index + 1,
                    selectedOption = selectedAnswers[item.id],
                    isSubmitted = submittedQuestions[item.id] == true,
                    onSelectOption = { optionIdx ->
                        if (submittedQuestions[item.id] != true) {
                            selectedAnswers[item.id] = optionIdx
                        }
                    },
                    onSubmit = {
                        submittedQuestions[item.id] = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun QuizItemCard(
    item: CurriculumQuizItem,
    questionNumber: Int,
    selectedOption: Int?,
    isSubmitted: Boolean,
    onSelectOption: (Int) -> Unit,
    onSubmit: () -> Unit
) {
    val bookRef = remember(item.referencedBookId) {
        AgriCurriculumBooksCatalog.books.find { it.id == item.referencedBookId }
    }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quiz_card_${item.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Q$questionNumber • ${item.pillar.title} (${item.topicCode})",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                if (isSubmitted) {
                    val isCorrect = selectedOption == item.correctIndex
                    Surface(
                        color = if (isCorrect) Color(0xFF2E7D32).copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (isCorrect) "✓ CORRECT" else "✗ INCORRECT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCorrect) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Question text
            Text(
                text = item.question,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 21.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                item.options.forEachIndexed { optIdx, optText ->
                    val isChosen = selectedOption == optIdx
                    val isCorrectAnswer = optIdx == item.correctIndex

                    val borderColor = when {
                        !isSubmitted && isChosen -> MaterialTheme.colorScheme.primary
                        isSubmitted && isCorrectAnswer -> Color(0xFF2E7D32)
                        isSubmitted && isChosen && !isCorrectAnswer -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    }

                    val containerColor = when {
                        !isSubmitted && isChosen -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
                        isSubmitted && isCorrectAnswer -> Color(0xFF2E7D32).copy(alpha = 0.12f)
                        isSubmitted && isChosen && !isCorrectAnswer -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.35f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, borderColor),
                        color = containerColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !isSubmitted) { onSelectOption(optIdx) }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isChosen,
                                onClick = { if (!isSubmitted) onSelectOption(optIdx) },
                                enabled = !isSubmitted,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = optText,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Submit Button
            if (!isSubmitted) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onSubmit,
                    enabled = selectedOption != null,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Submit Answer")
                }
            }

            // Explanation & Book reference when submitted
            if (isSubmitted) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Scientific Explanation:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = item.explanation,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        if (bookRef != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "📖 Cited Reference: ${bookRef.title} (${bookRef.authors})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}
