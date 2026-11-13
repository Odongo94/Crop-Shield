package com.example.ui.screens.ai

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.ai.AiAgronomistService
import com.example.data.voice.AgriculturalLanguagePack
import com.example.data.voice.AppLanguage
import com.example.data.voice.FarmTextToSpeechManager
import com.example.model.ai.AiChatMessage
import kotlinx.coroutines.launch

/**
 * Interactive Shamba AI Agronomist Screen.
 * Allows farmers and scouts to ask questions pertaining to agriculture,
 * crop pathology, pest control, poultry, soil rebalancing, and irrigation.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiAgronomistScreen(
    appLanguage: AppLanguage = AppLanguage.EN,
    onLanguageSelected: (AppLanguage) -> Unit = {},
    onNavigateToCalculator: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputQuestion by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val ttsManager = remember(context) { FarmTextToSpeechManager(context) }
    val isSpeaking by ttsManager.isSpeaking.collectAsStateWithLifecycle()
    val speakingMessageId by ttsManager.speakingMessageId.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        onDispose { ttsManager.shutdown() }
    }

    val voiceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spoken = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            if (!spoken.isNullOrBlank()) {
                inputQuestion = spoken
            }
        }
    }

    // Initial welcome message from AI Agronomist
    var messages by remember {
        mutableStateOf(
            listOf(
                AiChatMessage(
                    text = "Habari! I am your **CropShield AI Agronomist**, powered by Google Gemini and East African agricultural databases (KALRO, ICIPE, PCPB Kenya, and GlobalG.A.P.).\n\nAsk me any question pertaining to your crops, pest and disease identification, knapsack chemical dosing, poultry vaccination, soil liming, or irrigation budgeting!",
                    isUser = false,
                    isOnlineGemini = true,
                    topicCategory = "Welcome & Advisory"
                )
            )
        )
    }

    // Scroll to latest message
    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val quickPrompts = AiAgronomistService.quickPrompts

    fun sendQuestion(question: String) {
        val q = question.trim()
        if (q.isBlank() || isLoading) return

        val userMessage = AiChatMessage(
            text = q,
            isUser = true
        )
        messages = messages + userMessage
        inputQuestion = ""
        isLoading = true

        coroutineScope.launch {
            try {
                val promptWithLanguage = AgriculturalLanguagePack.buildSpeechPromptPrefix(appLanguage) + q
                val aiResponse = AiAgronomistService.askAgricultureAi(
                    userQuestion = promptWithLanguage,
                    conversationHistory = messages
                )
                messages = messages + aiResponse
            } catch (e: Exception) {
                val fallbackResponse = AiChatMessage(
                    text = "Unable to process request right now. Please try again or consult the offline agronomy guides.",
                    isUser = false,
                    isOnlineGemini = false
                )
                messages = messages + fallbackResponse
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("ai_agronomist_screen")
    ) {
        // Top Header Banner
        Surface(
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Ask Shamba AI Agronomist",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Gemini 2.5 Flash + Offline Knowledge Base",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            messages = listOf(
                                AiChatMessage(
                                    text = "Chat conversation cleared. How may I assist your farm today?",
                                    isUser = false,
                                    isOnlineGemini = true
                                )
                            )
                        },
                        modifier = Modifier.testTag("button_clear_ai_chat")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CleaningServices,
                            contentDescription = "Clear Chat",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Local Language Switcher Chips (EN / SW / LG / LUO)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppLanguage.values().forEach { lang ->
                        FilterChip(
                            selected = appLanguage == lang,
                            onClick = { onLanguageSelected(lang) },
                            label = { Text("${lang.code}: ${lang.nativeLabel}", style = MaterialTheme.typography.labelSmall) },
                            modifier = Modifier.testTag("ai_lang_${lang.code.lowercase()}")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Quick Prompt Chips (translated in selected language)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AgriculturalLanguagePack.quickVoicePrompts.forEach { localized ->
                        FilterChip(
                            selected = false,
                            onClick = { sendQuestion(localized.textFor(appLanguage)) },
                            leadingIcon = { Text("🎙️", fontSize = 13.sp) },
                            label = { Text("${localized.topic} (${appLanguage.code})", style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
                            )
                        )
                    }
                    quickPrompts.forEach { prompt ->
                        FilterChip(
                            selected = false,
                            onClick = { sendQuestion(prompt.fullPrompt) },
                            leadingIcon = { Text(prompt.iconEmoji, fontSize = 14.sp) },
                            label = { Text(prompt.shortLabel, style = MaterialTheme.typography.labelSmall) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.testTag("quick_prompt_${prompt.id}")
                        )
                    }
                }
            }
        }

        // Messages LazyColumn
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            items(messages, key = { it.id }) { message ->
                AiMessageBubble(
                    message = message,
                    isThisSpeaking = isSpeaking && speakingMessageId == message.id,
                    onSpeakToggle = {
                        if (isSpeaking && speakingMessageId == message.id) {
                            ttsManager.stop()
                        } else {
                            ttsManager.speak(message.text, appLanguage, message.id)
                        }
                    },
                    onNavigateToCalculator = onNavigateToCalculator,
                    onCopyText = { text ->
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Agronomic Advice", text))
                        Toast.makeText(context, "Copied advice to clipboard", Toast.LENGTH_SHORT).show()
                    },
                    onSelectFollowUp = { followUp -> sendQuestion(followUp) }
                )
            }

            if (isLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.5.dp,
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "AI Agronomist is analyzing agricultural databases...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        // Bottom Input Row
        Surface(
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputQuestion,
                    onValueChange = { inputQuestion = it },
                    placeholder = {
                        Text(
                            text = AgriculturalLanguagePack.uiLabels["voice_tip"]?.textFor(appLanguage)
                                ?: "Ask about crops, pests, dosing, poultry, soil...",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    },
                    singleLine = false,
                    maxLines = 3,
                    shape = RoundedCornerShape(24.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { sendQuestion(inputQuestion) }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_ai_question")
                )

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = {
                        try {
                            voiceLauncher.launch(FarmTextToSpeechManager.buildVoiceRecognitionIntent(appLanguage))
                        } catch (_: Exception) {
                            Toast.makeText(context, "Speech recognition unavailable on this device", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .testTag("button_voice_input_ai")
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Input (${appLanguage.displayName})",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = { sendQuestion(inputQuestion) },
                    enabled = inputQuestion.isNotBlank() && !isLoading,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (inputQuestion.isNotBlank() && !isLoading) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .testTag("button_send_ai_question")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = if (inputQuestion.isNotBlank() && !isLoading) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AiMessageBubble(
    message: AiChatMessage,
    isThisSpeaking: Boolean = false,
    onSpeakToggle: () -> Unit = {},
    onNavigateToCalculator: (String) -> Unit,
    onCopyText: (String) -> Unit,
    onSelectFollowUp: (String) -> Unit
) {
    val isUser = message.isUser

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        if (!isUser) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Card(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
            ),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Header badge for AI response
                if (!isUser) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = if (message.isOnlineGemini) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = if (message.isOnlineGemini) "⚡ Gemini AI" else "🛡️ Offline Knowledge",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (message.isOnlineGemini) MaterialTheme.colorScheme.onPrimaryContainer
                                else MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(
                                onClick = onSpeakToggle,
                                modifier = Modifier.size(28.dp).testTag("btn_tts_${message.id}")
                            ) {
                                Icon(
                                    imageVector = if (isThisSpeaking) Icons.Default.Stop else Icons.Default.VolumeUp,
                                    contentDescription = if (isThisSpeaking) "Stop Reading" else "Read Aloud",
                                    tint = if (isThisSpeaking) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            IconButton(
                                onClick = { onCopyText(message.text) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy Advice",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // Message Text
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )

                // Timestamp
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isUser) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.End)
                )

                // Related In-App Action Shortcut
                if (!isUser && message.relatedCalculatorScreen != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToCalculator(message.relatedCalculatorScreen) }
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = when (message.relatedCalculatorScreen) {
                                    "NOZZLE" -> Icons.Default.Speed
                                    "SOIL_CALCULATOR" -> Icons.Default.Terrain
                                    "POULTRY" -> Icons.Default.Egg
                                    "AUDIT" -> Icons.Default.HealthAndSafety
                                    else -> Icons.Default.Calculate
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (message.relatedCalculatorScreen) {
                                    "NOZZLE" -> "Open Nozzle Discharge Calculator"
                                    "SOIL_CALCULATOR" -> "Open Soil Texture & CEC Calculator"
                                    "POULTRY" -> "Open Poultry Manager & Vaccination"
                                    "AUDIT" -> "Open Agricultural Compliance Hub"
                                    else -> "Open Knapsack Dosing Calculator"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                // Suggested Follow-Up Questions
                if (!isUser && message.suggestedFollowUps.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Related Follow-up Questions:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        message.suggestedFollowUps.forEach { followUp ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surface,
                                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
                                modifier = Modifier.clickable { onSelectFollowUp(followUp) }
                            ) {
                                Text(
                                    text = "• $followUp",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
