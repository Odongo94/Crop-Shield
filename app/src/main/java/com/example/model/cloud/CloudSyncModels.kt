package com.example.model.cloud

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

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

enum class CloudRecordType(val code: String, val label: String) {
    FIELD_NOTE("FIELD_NOTE", "Field Note"),
    PHI_SPRAY("PHI_SPRAY", "Spray PHI Log"),
    CROP_SEASON("CROP_SEASON", "Crop Season"),
    LIVESTOCK("LIVESTOCK", "Livestock Log"),
    BUDGET("BUDGET", "Farm Budget"),
    STORAGE_LOT("STORAGE_LOT", "Storage Lot")
}

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
