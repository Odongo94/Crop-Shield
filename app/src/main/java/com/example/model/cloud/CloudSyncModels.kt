package com.example.model.cloud

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FarmerProfile(
    @DocumentId val id: String = "",
    val uid: String = "",
    val displayName: String = "",
    val farmName: String = "",
    val county: String = "",
    val preferredLanguage: String = "EN",
    val role: String = "farmer",
    @ServerTimestamp val createdAt: Timestamp? = null
)

/**
 * Private farmer registry entry containing verified email and farm locality coordinates.
 * Readable ONLY by the farmer themselves and the verified Super Admin (odongovincento@gmail.com).
 */
@IgnoreExtraProperties
data class FarmerRegistryEntry(
    @DocumentId val id: String = "",
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val farmName: String = "",
    val county: String = "",
    val locality: String = "",
    val latitude: Double = -0.3031,
    val longitude: Double = 36.0800,
    val primaryCrops: String = "",
    val signInCount: Long = 1L,
    @ServerTimestamp val lastActiveAt: Timestamp? = null,
    @ServerTimestamp val createdAt: Timestamp? = null
)

enum class AdminEventType(val code: String, val label: String) {
    NEW_FARMER("NEW_FARMER", "New Farmer Sign-Up"),
    SIGN_IN("SIGN_IN", "Farmer Sign-In"),
    DIRECT_MESSAGE("DIRECT_MESSAGE", "Direct Message"),
    FEEDBACK("FEEDBACK", "App Feedback")
}

@IgnoreExtraProperties
data class AdminNotification(
    @DocumentId val id: String = "",
    val farmerId: String = "",
    val farmerName: String = "",
    val farmerEmail: String = "",
    val county: String = "",
    val locality: String = "",
    val eventType: String = AdminEventType.NEW_FARMER.code,
    val summary: String = "",
    val isRead: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class CommunityMessage(
    @DocumentId val id: String = "",
    val roomId: String = "global_shamba_community",
    val authorId: String = "",
    val authorName: String = "",
    val authorRole: String = "farmer",
    val county: String = "",
    val topic: String = "General",
    val messageText: String = "",
    val isSolutionPinned: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class DirectThread(
    @DocumentId val id: String = "",
    val farmerId: String = "",
    val farmerName: String = "",
    val county: String = "",
    val locality: String = "",
    val lastMessagePreview: String = "",
    val lastSenderRole: String = "farmer",
    val unreadByAdmin: Boolean = false,
    val unreadByFarmer: Boolean = false,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class DirectMessage(
    @DocumentId val id: String = "",
    val farmerId: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val senderRole: String = "farmer",
    val text: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

enum class FeedbackFeatureSource(val code: String, val label: String) {
    AI_AGRONOMIST("AI_AGRONOMIST", "AI Agronomist"),
    PHOTO_SCANNER("PHOTO_SCANNER", "Photo Scanner"),
    DIAGNOSE("DIAGNOSE", "Symptom Diagnosis"),
    COMMUNITY_CHAT("COMMUNITY_CHAT", "Community Chat"),
    DIRECT_ADMIN("DIRECT_ADMIN", "Direct Admin Chat"),
    GENERAL_APP("GENERAL_APP", "General App")
}

@IgnoreExtraProperties
data class AppFeedback(
    @DocumentId val id: String = "",
    val userId: String = "",
    val farmerName: String = "",
    val county: String = "",
    val featureSource: String = FeedbackFeatureSource.GENERAL_APP.code,
    val questionOrTopic: String = "",
    val wasHelpful: Boolean = true,
    val improvementArea: String = "",
    val improvementSuggestion: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

enum class CloudRecordType(val code: String, val label: String) {
    FIELD_NOTE("FIELD_NOTE", "Field Note"),
    PHI_SPRAY("PHI_SPRAY", "Spray PHI Log"),
    CROP_SEASON("CROP_SEASON", "Crop Season"),
    LIVESTOCK("LIVESTOCK", "Livestock Log"),
    BUDGET("BUDGET", "Farm Budget"),
    STORAGE_LOT("STORAGE_LOT", "Storage Lot")
}

@IgnoreExtraProperties
data class CloudFarmRecord(
    @DocumentId val id: String = "",
    val ownerId: String = "",
    val recordType: String = CloudRecordType.FIELD_NOTE.code,
    val title: String = "",
    val cropOrSpecies: String = "",
    val summary: String = "",
    val payloadJson: String = "{}",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

enum class ExtensionReportStatus(val code: String, val label: String) {
    OPEN("OPEN", "Awaiting Advisor"),
    REVIEWED("REVIEWED", "Advisor Reviewed"),
    RESOLVED("RESOLVED", "Resolved")
}

@IgnoreExtraProperties
data class ExtensionReport(
    @DocumentId val id: String = "",
    val farmerId: String = "",
    val farmerName: String = "",
    val county: String = "",
    val title: String = "",
    val summary: String = "",
    val advisorEmail: String = "",
    val advisorNotes: String = "",
    val status: String = ExtensionReportStatus.OPEN.code,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
