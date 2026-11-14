package com.example.model.ai

import android.graphics.Bitmap
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Data models for the AI Agronomist Chat and Agricultural Q&A Engine.
 */

data class AiChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
    val isOnlineGemini: Boolean = false,
    val topicCategory: String = "General Agronomy",
    val suggestedFollowUps: List<String> = emptyList(),
    val relatedCalculatorScreen: String? = null, // e.g. "CALCULATOR", "SOIL_CALCULATOR", "NOZZLE", "POULTRY", "AUDIT"
    val attachedImage: Bitmap? = null,
    val imageCaption: String? = null
)

data class AiQuickPrompt(
    val id: String,
    val iconEmoji: String,
    val category: String,
    val shortLabel: String,
    val fullPrompt: String
)
