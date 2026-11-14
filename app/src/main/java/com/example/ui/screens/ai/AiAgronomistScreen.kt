package com.example.ui.screens.ai

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.AssistChip
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
import androidx.compose.material3.minimumInteractiveComponentSize
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.ai.AiAgronomistService
import com.example.data.voice.AgriculturalLanguagePack
import com.example.data.voice.AppLanguage
import com.example.data.voice.FarmTextToSpeechManager
import com.example.model.ai.AiChatMessage
import com.example.ui.components.InlineHelpfulnessFeedbackBar
import kotlinx.coroutines.launch

/**
 * Interactive Shamba AI Agronomist Screen.
 * Supports text, voice input, and uploading/capturing field photos for multimodal Gemini & offline optical analysis.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AiAgronomistScreen(
    appLanguage: AppLanguage = AppLanguage.EN,
    onLanguageSelected: (AppLanguage) -> Unit = {},
    onNavigateToCalculator: (String) -> Unit = {},
    onSubmitFeedback: (topic: String, wasHelpful: Boolean, area: String, suggestion: String) -> Unit = { _, _, _, _ -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputQuestion by remember { mutableStateOf("") }
    var pendingBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var pendingImageSourceLabel by remember { mutableStateOf<String?>(null) }
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

    // Zero-permission Android Photo Picker for gallery image uploads
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val decoded = decodeScaledBitmapFromUri(context, uri, maxDim = 1024)
            if (decoded != null) {
                pendingBitmap = decoded
                pendingImageSourceLabel = "Uploaded Field Photo"
            } else {
                Toast.makeText(context, "Could not load selected image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Live camera capture launcher
    val cameraCaptureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            pendingBitmap = bitmap
            pendingImageSourceLabel = "Live Camera Photo"
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            cameraCaptureLauncher.launch(null)
        } else {
            Toast.makeText(
                context,
                "Camera permission denied. You can still upload photos from your gallery.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun launchGalleryUpload() {
        photoPickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }

    fun launchCameraCapture() {
        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        if (granted) {
            cameraCaptureLauncher.launch(null)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Initial welcome message from AI Agronomist
    var messages by remember {
        mutableStateOf(
            listOf(
                AiChatMessage(
                    text = "Habari! I am your **Shamba AI Agronomist**, powered by Google Gemini Vision and East African agricultural databases (KALRO, ICIPE, PCPB Kenya, and GlobalG.A.P.).\n\nAsk any question OR **upload/snap a photo** of your crops, pests, leaves, soil, or weeds for instant identification and dosing guidance!",
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

    fun sendQuestion(question: String, imageOverride: Bitmap? = pendingBitmap, labelOverride: String? = pendingImageSourceLabel) {
        val q = question.trim()
        val img = imageOverride
        if ((q.isBlank() && img == null) || isLoading) return

        val displayQuestion = q.ifBlank { "Analyze this field photo and recommend exact IPM & chemical controls." }
        val userMessage = AiChatMessage(
            text = displayQuestion,
            isUser = true,
            attachedImage = img,
            imageCaption = if (img != null) (labelOverride ?: "Uploaded Photo") else null
        )
        val historySnapshot = messages
        messages = messages + userMessage
        inputQuestion = ""
        pendingBitmap = null
        pendingImageSourceLabel = null
        isLoading = true

        coroutineScope.launch {
            try {
                val promptWithLanguage = AgriculturalLanguagePack.buildSpeechPromptPrefix(appLanguage) + displayQuestion
                val aiResponse = AiAgronomistService.askAgricultureAi(
                    userQuestion = promptWithLanguage,
                    conversationHistory = historySnapshot,
                    imageBitmap = img
                )
                messages = messages + aiResponse
            } catch (_: Exception) {
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
                                    text = "Gemini Vision + Offline Agronomic Diagnostics",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = {
                            pendingBitmap = null
                            pendingImageSourceLabel = null
                            messages = listOf(
                                AiChatMessage(
                                    text = "Chat conversation cleared. Ask a question or upload a field photo to begin!",
                                    isUser = false,
                                    isOnlineGemini = true
                                )
                            )
                        },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("button_clear_ai_chat")
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

                // Quick Prompt + Photo Upload Action Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { launchGalleryUpload() },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        label = { Text("Upload Photo to Analyze", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold) },
                        modifier = Modifier.testTag("quick_prompt_upload_photo")
                    )
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
                    onSelectFollowUp = { followUp -> sendQuestion(followUp) },
                    onSubmitFeedback = { topic, helpful, area, suggestion ->
                        onSubmitFeedback(topic, helpful, area, suggestion)
                    }
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
                            text = "Shamba AI Agronomist is analyzing photo & farm databases...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        // Bottom Input Composer + Pending Image Attachment Bar
        Surface(
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                AnimatedVisibility(visible = pendingBitmap != null) {
                    pendingBitmap?.let { bitmap ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                                .testTag("pending_image_preview_bar")
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "Attached crop photo preview",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = pendingImageSourceLabel ?: "Attached Photo",
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Tap Send to analyze now, or add a note below.",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            pendingBitmap = null
                                            pendingImageSourceLabel = null
                                        },
                                        modifier = Modifier
                                            .minimumInteractiveComponentSize()
                                            .testTag("button_remove_pending_image")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Remove attached photo",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val visionIntents = listOf(
                                        "Identify pest or disease",
                                        "Check nutrient deficiency",
                                        "Identify weed or soil type",
                                        "Recommend knapsack spray"
                                    )
                                    visionIntents.forEach { intentPrompt ->
                                        AssistChip(
                                            onClick = { inputQuestion = intentPrompt },
                                            label = { Text(intentPrompt, style = MaterialTheme.typography.labelSmall) }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Upload Photo from Gallery (Zero-Permission Android Photo Picker)
                    IconButton(
                        onClick = { launchGalleryUpload() },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .testTag("button_upload_photo_ai")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddPhotoAlternate,
                            contentDescription = "Upload crop or pest photo from gallery",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Take Live Camera Photo
                    IconButton(
                        onClick = { launchCameraCapture() },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .testTag("button_camera_photo_ai")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Capture live crop photo with camera",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    OutlinedTextField(
                        value = inputQuestion,
                        onValueChange = { inputQuestion = it },
                        placeholder = {
                            Text(
                                text = if (pendingBitmap != null) {
                                    "Add note about this photo..."
                                } else {
                                    AgriculturalLanguagePack.uiLabels["voice_tip"]?.textFor(appLanguage)
                                        ?: "Ask or upload photo..."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
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
                            .minimumInteractiveComponentSize()
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

                    val canSend = (inputQuestion.isNotBlank() || pendingBitmap != null) && !isLoading
                    IconButton(
                        onClick = { sendQuestion(inputQuestion) },
                        enabled = canSend,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(CircleShape)
                            .background(
                                if (canSend) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .testTag("button_send_ai_question")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send question or analyze photo",
                            tint = if (canSend) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun decodeScaledBitmapFromUri(context: Context, uri: Uri, maxDim: Int = 1024): Bitmap? {
    return try {
        val boundsOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, boundsOptions)
        }
        val origW = boundsOptions.outWidth
        val origH = boundsOptions.outHeight
        var sampleSize = 1
        if (origW > maxDim || origH > maxDim) {
            val halfW = origW / 2
            val halfH = origH / 2
            while ((halfW / sampleSize) >= maxDim && (halfH / sampleSize) >= maxDim) {
                sampleSize *= 2
            }
        }
        val decodeOptions = BitmapFactory.Options().apply { inSampleSize = sampleSize }
        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, decodeOptions)
        }
    } catch (_: Exception) {
        null
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
    onSelectFollowUp: (String) -> Unit,
    onSubmitFeedback: (topic: String, wasHelpful: Boolean, area: String, suggestion: String) -> Unit = { _, _, _, _ -> }
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
                                    imageVector = if (isThisSpeaking) Icons.Default.Stop else Icons.AutoMirrored.Filled.VolumeUp,
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

                // Attached photo preview inside chat message bubble
                message.attachedImage?.let { img ->
                    Column(modifier = Modifier.padding(bottom = 8.dp)) {
                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = message.imageCaption ?: "Uploaded agricultural photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp, max = 190.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        message.imageCaption?.let { cap ->
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "📷 $cap",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isUser) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
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

                // Inline helpfulness & improvement feedback for actual AI answers
                if (!isUser && message.topicCategory != "Welcome & Advisory") {
                    Spacer(modifier = Modifier.height(10.dp))
                    InlineHelpfulnessFeedbackBar(
                        questionOrTopic = message.topicCategory ?: message.text.take(80),
                        testTagPrefix = "ai_feedback_${message.id}",
                        onSubmitFeedback = { helpful, area, suggestion ->
                            onSubmitFeedback(
                                message.topicCategory ?: message.text.take(80),
                                helpful,
                                area,
                                suggestion
                            )
                        }
                    )
                }
            }
        }
    }
}
