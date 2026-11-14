package com.example.data.firebase

import android.content.Context
import com.example.R
import com.example.model.DeveloperInfo
import com.example.model.cloud.AdminEventType
import com.example.model.cloud.AdminNotification
import com.example.model.cloud.AppFeedback
import com.example.model.cloud.CloudFarmRecord
import com.example.model.cloud.CommunityMessage
import com.example.model.cloud.DirectMessage
import com.example.model.cloud.DirectThread
import com.example.model.cloud.ExtensionReport
import com.example.model.cloud.FarmerProfile
import com.example.model.cloud.FarmerRegistryEntry
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import java.util.UUID

class CloudSyncRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    fun isCurrentUserSuperAdmin(): Boolean {
        val user = auth.currentUser ?: return false
        return user.isEmailVerified && user.email.equals(DeveloperInfo.EMAIL, ignoreCase = true)
    }

    fun observeAuthState(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        trySend(auth.currentUser)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    suspend fun ensureFarmerProfile(
        displayName: String,
        farmName: String,
        county: String,
        preferredLanguage: String
    ): FarmerProfile {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to manage farmer profile.")
        val path = "users/$uid"
        val docRef = firestore.collection("users").document(uid)
        return try {
            val existing = docRef.get().await()
            if (!existing.exists()) {
                val data = hashMapOf<String, Any>(
                    "uid" to uid,
                    "displayName" to displayName.ifBlank { "CropShield Farmer" }.take(100),
                    "farmName" to farmName.take(100),
                    "county" to county.take(80),
                    "preferredLanguage" to preferredLanguage.ifBlank { "EN" },
                    "role" to "farmer",
                    "createdAt" to FieldValue.serverTimestamp()
                )
                docRef.set(data).await()
            }
            val snap = docRef.get().await()
            snap.toObject(FarmerProfile::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                ?: FarmerProfile(
                    id = uid,
                    uid = uid,
                    displayName = displayName,
                    farmName = farmName,
                    county = county,
                    preferredLanguage = preferredLanguage
                )
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.WRITE, path, auth)
        }
    }

    suspend fun updateFarmerProfile(
        displayName: String,
        farmName: String,
        county: String,
        preferredLanguage: String
    ) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to update profile.")
        val path = "users/$uid"
        try {
            val updates = hashMapOf<String, Any>(
                "displayName" to displayName.ifBlank { "CropShield Farmer" }.take(100),
                "farmName" to farmName.take(100),
                "county" to county.take(80),
                "preferredLanguage" to preferredLanguage
            )
            firestore.collection("users").document(uid).update(updates).await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.UPDATE, path, auth)
        }
    }

    /**
     * Records or updates the authenticated user in the private Super-Admin-only farmer registry
     * and creates an AdminNotification event so the super admin is notified of every farmer sign-up/sign-in.
     * Returns true if this is a brand-new farmer registration.
     */
    suspend fun registerOrCheckInFarmer(
        displayName: String,
        farmName: String,
        county: String,
        locality: String,
        latitude: Double,
        longitude: Double,
        primaryCrops: String
    ): Boolean {
        val user = auth.currentUser
            ?: throw IllegalStateException("Must be signed in to register farmer.")
        val uid = user.uid
        val email = user.email?.take(150)?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("Verified Google email is required.")
        val safeName = displayName.ifBlank { user.displayName ?: "CropShield Farmer" }.take(100)
        val safeFarm = farmName.ifBlank { "${county.ifBlank { "Kenya" }} Shamba" }.take(100)
        val safeCounty = county.take(80)
        val safeLocality = locality.ifBlank { safeCounty.ifBlank { "Kenya" } }.take(120)
        val safeLat = latitude.coerceIn(-90.0, 90.0)
        val safeLng = longitude.coerceIn(-180.0, 180.0)
        val safeCrops = primaryCrops.take(150)

        val regRef = firestore.collection("farmer_registry").document(uid)
        return try {
            val existing = regRef.get().await()
            val isNewFarmer = !existing.exists()
            if (isNewFarmer) {
                val data = hashMapOf<String, Any>(
                    "uid" to uid,
                    "email" to email,
                    "displayName" to safeName,
                    "farmName" to safeFarm,
                    "county" to safeCounty,
                    "locality" to safeLocality,
                    "latitude" to safeLat,
                    "longitude" to safeLng,
                    "primaryCrops" to safeCrops,
                    "signInCount" to 1L,
                    "lastActiveAt" to FieldValue.serverTimestamp(),
                    "createdAt" to FieldValue.serverTimestamp()
                )
                regRef.set(data).await()
            } else {
                val updates = hashMapOf<String, Any>(
                    "displayName" to safeName,
                    "farmName" to safeFarm,
                    "county" to safeCounty,
                    "locality" to safeLocality,
                    "latitude" to safeLat,
                    "longitude" to safeLng,
                    "primaryCrops" to safeCrops,
                    "signInCount" to FieldValue.increment(1L),
                    "lastActiveAt" to FieldValue.serverTimestamp()
                )
                regRef.update(updates).await()
            }

            // Emit super-admin notification alert
            createAdminNotification(
                farmerName = safeName,
                farmerEmail = email,
                county = safeCounty,
                locality = safeLocality,
                eventType = if (isNewFarmer) AdminEventType.NEW_FARMER else AdminEventType.SIGN_IN,
                summary = if (isNewFarmer) {
                    "New farmer signed up: $safeName ($email) at $safeLocality, $safeCounty • Crops: ${safeCrops.ifBlank { "Mixed" }}"
                } else {
                    "Farmer signed in: $safeName ($email) active in $safeLocality, $safeCounty"
                }
            )
            isNewFarmer
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.WRITE, "farmer_registry/$uid", auth)
        }
    }

    suspend fun createAdminNotification(
        farmerName: String,
        farmerEmail: String,
        county: String,
        locality: String,
        eventType: AdminEventType,
        summary: String
    ) {
        val user = auth.currentUser
            ?: throw IllegalStateException("Must be signed in to log admin alert.")
        val uid = user.uid
        val verifiedEmail = user.email?.take(150)?.takeIf { it.isNotBlank() } ?: farmerEmail.take(150)
        val notifId = "notif_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val path = "admin_notifications/$notifId"
        try {
            val payload = hashMapOf<String, Any>(
                "farmerId" to uid,
                "farmerName" to farmerName.ifBlank { "CropShield Farmer" }.take(100),
                "farmerEmail" to verifiedEmail,
                "county" to county.take(80),
                "locality" to locality.take(120),
                "eventType" to eventType.code,
                "summary" to summary.ifBlank { eventType.label }.take(600),
                "isRead" to false,
                "createdAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("admin_notifications").document(notifId).set(payload).await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.CREATE, path, auth)
        }
    }

    /**
     * Super-admin-only stream of registered farmers (contains emails and GPS/locality coordinates).
     */
    fun observeAllFarmerRegistry(): Flow<List<FarmerRegistryEntry>> {
        if (!isCurrentUserSuperAdmin()) return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("farmer_registry")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(FarmerRegistryEntry::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedByDescending { it.lastActiveAt?.seconds ?: 0L } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    /**
     * Super-admin-only stream of farmer activity notifications.
     */
    fun observeAdminNotifications(): Flow<List<AdminNotification>> {
        if (!isCurrentUserSuperAdmin()) return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("admin_notifications")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(AdminNotification::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedByDescending { it.createdAt?.seconds ?: 0L } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    suspend fun markNotificationRead(notifId: String) {
        val path = "admin_notifications/$notifId"
        try {
            firestore.collection("admin_notifications").document(notifId)
                .update("isRead", true)
                .await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.UPDATE, path, auth)
        }
    }

    // =========================================================================
    // COMMUNITY PROBLEM-SOLVING CHAT (FARMERS + SUPER ADMIN)
    // =========================================================================

    fun observeCommunityMessages(): Flow<List<CommunityMessage>> {
        auth.currentUser?.uid ?: return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("community_messages")
                .whereEqualTo("roomId", COMMUNITY_ROOM_ID)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(CommunityMessage::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedBy { it.createdAt?.seconds ?: Long.MAX_VALUE } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    suspend fun sendCommunityMessage(
        authorName: String,
        county: String,
        topic: String,
        messageText: String
    ): String {
        val user = auth.currentUser
            ?: throw IllegalStateException("Sign in to participate in the Community Chat.")
        val uid = user.uid
        val msgId = "cmsg_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val path = "community_messages/$msgId"
        val isAdmin = isCurrentUserSuperAdmin()
        val role = if (isAdmin) "super_admin" else "farmer"
        val safeAuthorName = if (isAdmin) {
            "Agronomy Advisor"
        } else {
            authorName.ifBlank { user.displayName ?: "CropShield Farmer" }
        }
        try {
            val data = hashMapOf<String, Any>(
                "roomId" to COMMUNITY_ROOM_ID,
                "authorId" to uid,
                "authorName" to safeAuthorName.take(100),
                "authorRole" to role,
                "county" to if (isAdmin) "National Advisory" else county.take(80),
                "topic" to topic.ifBlank { "General" }.take(60),
                "messageText" to messageText.trim().take(1500),
                "isSolutionPinned" to false,
                "createdAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("community_messages").document(msgId).set(data).await()
            return msgId
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.CREATE, path, auth)
        }
    }

    /**
     * Posts an automated Shamba AI Agronomist reply or fact-check correction to the community room
     * using a deterministic document ID so concurrent clients do not post duplicate AI replies.
     */
    suspend fun sendCommunityAiMessage(
        docId: String,
        topic: String,
        messageText: String
    ): Boolean {
        val user = auth.currentUser ?: return false
        val cleanDocId = docId.replace(Regex("[^a-zA-Z0-9_-]"), "_").take(120)
        if (cleanDocId.isBlank() || messageText.isBlank()) return false
        val path = "community_messages/$cleanDocId"
        val docRef = firestore.collection("community_messages").document(cleanDocId)
        return try {
            val existing = docRef.get().await()
            if (existing.exists()) {
                false
            } else {
                val data = hashMapOf<String, Any>(
                    "roomId" to COMMUNITY_ROOM_ID,
                    "authorId" to user.uid,
                    "authorName" to com.example.data.ai.CommunityAiModerator.AI_AUTHOR_NAME,
                    "authorRole" to com.example.data.ai.CommunityAiModerator.AI_AUTHOR_ROLE,
                    "county" to "AI Agronomist",
                    "topic" to topic.ifBlank { "General" }.take(60),
                    "messageText" to messageText.trim().take(1500),
                    "isSolutionPinned" to false,
                    "createdAt" to FieldValue.serverTimestamp()
                )
                docRef.set(data).await()
                true
            }
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.WRITE, path, auth)
        }
    }

    suspend fun toggleCommunitySolutionPin(messageId: String, pinned: Boolean) {
        val path = "community_messages/$messageId"
        try {
            firestore.collection("community_messages").document(messageId)
                .update("isSolutionPinned", pinned)
                .await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.UPDATE, path, auth)
        }
    }

    suspend fun deleteCommunityMessage(messageId: String) {
        val path = "community_messages/$messageId"
        try {
            firestore.collection("community_messages").document(messageId).delete().await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.DELETE, path, auth)
        }
    }

    // =========================================================================
    // DIRECT 1-ON-1 SUPPORT CHAT
    // =========================================================================

    fun observeAllDirectThreadsForAdmin(): Flow<List<DirectThread>> {
        if (!isCurrentUserSuperAdmin()) return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("direct_threads")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(DirectThread::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedByDescending { it.updatedAt?.seconds ?: 0L } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    fun observeDirectMessages(farmerId: String): Flow<List<DirectMessage>> {
        auth.currentUser?.uid ?: return flowOf(emptyList())
        if (farmerId.isBlank()) return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("direct_threads")
                .document(farmerId)
                .collection("messages")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(DirectMessage::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedBy { it.createdAt?.seconds ?: Long.MAX_VALUE } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    suspend fun sendDirectMessage(
        farmerId: String,
        farmerName: String,
        county: String,
        locality: String,
        messageText: String
    ): String {
        val user = auth.currentUser
            ?: throw IllegalStateException("Must be signed in to send a direct message.")
        val senderUid = user.uid
        val isAdmin = isCurrentUserSuperAdmin()
        val role = if (isAdmin) "super_admin" else "farmer"
        val senderName = if (isAdmin) "Agronomy Advisor" else farmerName.ifBlank { user.displayName ?: "CropShield Farmer" }
        val cleanText = messageText.trim().take(1500)

        val threadRef = firestore.collection("direct_threads").document(farmerId)
        val msgId = "dmsg_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val msgRef = threadRef.collection("messages").document(msgId)

        try {
            val threadSnap = threadRef.get().await()
            if (!threadSnap.exists()) {
                val threadData = hashMapOf<String, Any>(
                    "farmerId" to farmerId,
                    "farmerName" to farmerName.ifBlank { "CropShield Farmer" }.take(100),
                    "county" to county.take(80),
                    "locality" to locality.take(120),
                    "lastMessagePreview" to cleanText.take(300),
                    "lastSenderRole" to role,
                    "unreadByAdmin" to !isAdmin,
                    "unreadByFarmer" to isAdmin,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                threadRef.set(threadData).await()
            } else {
                val updates = hashMapOf<String, Any>(
                    "farmerName" to farmerName.ifBlank { threadSnap.getString("farmerName") ?: "CropShield Farmer" }.take(100),
                    "county" to county.take(80),
                    "locality" to locality.take(120),
                    "lastMessagePreview" to cleanText.take(300),
                    "lastSenderRole" to role,
                    "unreadByAdmin" to !isAdmin,
                    "unreadByFarmer" to isAdmin,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                threadRef.update(updates).await()
            }

            val msgData = hashMapOf<String, Any>(
                "farmerId" to farmerId,
                "senderId" to senderUid,
                "senderName" to senderName.take(100),
                "senderRole" to role,
                "text" to cleanText,
                "createdAt" to FieldValue.serverTimestamp()
            )
            msgRef.set(msgData).await()

            if (!isAdmin && !user.email.isNullOrBlank()) {
                createAdminNotification(
                    farmerName = farmerName.ifBlank { user.displayName ?: "Farmer" },
                    farmerEmail = user.email!!,
                    county = county,
                    locality = locality,
                    eventType = AdminEventType.DIRECT_MESSAGE,
                    summary = "Direct chat from $farmerName ($locality): ${cleanText.take(140)}"
                )
            }
            return msgId
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.WRITE, msgRef.path, auth)
        }
    }

    // =========================================================================
    // APP FEEDBACK ("WAS THIS HELPFUL?" + IMPROVEMENT SUGGESTIONS)
    // =========================================================================

    fun observeFeedback(): Flow<List<AppFeedback>> {
        val uid = auth.currentUser?.uid ?: return flowOf(emptyList())
        val baseQuery: Query = if (isCurrentUserSuperAdmin()) {
            firestore.collection("app_feedback")
        } else {
            firestore.collection("app_feedback").whereEqualTo("userId", uid)
        }
        return callbackFlow {
            val registration = baseQuery.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val list = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(AppFeedback::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                }?.sortedByDescending { it.createdAt?.seconds ?: 0L } ?: emptyList()
                trySend(list)
            }
            awaitClose { registration.remove() }
        }
    }

    suspend fun submitAppFeedback(
        farmerName: String,
        county: String,
        locality: String,
        featureSource: String,
        questionOrTopic: String,
        wasHelpful: Boolean,
        improvementArea: String,
        improvementSuggestion: String
    ): String {
        val user = auth.currentUser
            ?: throw IllegalStateException("Sign in to submit feedback to cloud.")
        val uid = user.uid
        val fbId = "fb_${UUID.randomUUID().toString().replace("-", "").take(16)}"
        val path = "app_feedback/$fbId"
        try {
            val payload = hashMapOf<String, Any>(
                "userId" to uid,
                "farmerName" to farmerName.ifBlank { user.displayName ?: "CropShield Farmer" }.take(100),
                "county" to county.take(80),
                "featureSource" to featureSource,
                "questionOrTopic" to questionOrTopic.take(250),
                "wasHelpful" to wasHelpful,
                "improvementArea" to improvementArea.take(100),
                "improvementSuggestion" to improvementSuggestion.take(1200),
                "createdAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("app_feedback").document(fbId).set(payload).await()

            if (!user.email.isNullOrBlank()) {
                val helpfulLabel = if (wasHelpful) "Helpful 👍" else "Needs Improvement 👎"
                createAdminNotification(
                    farmerName = farmerName.ifBlank { user.displayName ?: "Farmer" },
                    farmerEmail = user.email!!,
                    county = county,
                    locality = locality,
                    eventType = AdminEventType.FEEDBACK,
                    summary = "[$helpfulLabel • $featureSource] ${improvementArea.ifBlank { "General" }}: ${improvementSuggestion.take(140)}"
                )
            }
            return fbId
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.CREATE, path, auth)
        }
    }

    // =========================================================================
    // EXISTING FARM RECORDS & EXTENSION REPORTS
    // =========================================================================

    fun observeFarmRecords(): Flow<List<CloudFarmRecord>> {
        val uid = auth.currentUser?.uid ?: return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("users")
                .document(uid)
                .collection("farm_records")
                .orderBy("updatedAt", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(CloudFarmRecord::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    suspend fun upsertFarmRecord(
        recordId: String,
        recordType: String,
        title: String,
        cropOrSpecies: String,
        summary: String,
        payloadJson: String
    ) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to sync farm records.")
        val cleanId = recordId.replace(Regex("[^a-zA-Z0-9_-]"), "_").take(120).ifBlank {
            "rec_${System.currentTimeMillis()}"
        }
        val path = "users/$uid/farm_records/$cleanId"
        val docRef = firestore.collection("users").document(uid).collection("farm_records").document(cleanId)
        try {
            val snap = docRef.get().await()
            if (snap.exists()) {
                val updates = hashMapOf<String, Any>(
                    "recordType" to recordType,
                    "title" to title.ifBlank { "Farm Record" }.take(150),
                    "cropOrSpecies" to cropOrSpecies.take(80),
                    "summary" to summary.take(1200),
                    "payloadJson" to payloadJson.take(4000),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.update(updates).await()
            } else {
                val data = hashMapOf<String, Any>(
                    "ownerId" to uid,
                    "recordType" to recordType,
                    "title" to title.ifBlank { "Farm Record" }.take(150),
                    "cropOrSpecies" to cropOrSpecies.take(80),
                    "summary" to summary.take(1200),
                    "payloadJson" to payloadJson.take(4000),
                    "createdAt" to FieldValue.serverTimestamp(),
                    "updatedAt" to FieldValue.serverTimestamp()
                )
                docRef.set(data).await()
            }
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.WRITE, path, auth)
        }
    }

    suspend fun deleteFarmRecord(recordId: String) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to delete records.")
        val path = "users/$uid/farm_records/$recordId"
        try {
            firestore.collection("users").document(uid)
                .collection("farm_records").document(recordId)
                .delete().await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.DELETE, path, auth)
        }
    }

    fun observeMyExtensionReports(): Flow<List<ExtensionReport>> {
        val uid = auth.currentUser?.uid ?: return flowOf(emptyList())
        return callbackFlow {
            val registration = firestore.collection("extension_reports")
                .whereEqualTo("farmerId", uid)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(ExtensionReport::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedByDescending { it.updatedAt?.seconds ?: 0L } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    fun observeAdvisorInboxReports(): Flow<List<ExtensionReport>> {
        val user = auth.currentUser
        val email = user?.email
        if (user == null || email.isNullOrBlank() || !user.isEmailVerified) {
            return flowOf(emptyList())
        }
        return callbackFlow {
            val registration = firestore.collection("extension_reports")
                .whereEqualTo("advisorEmail", email)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    val list = snapshot?.documents?.mapNotNull { doc ->
                        doc.toObject(ExtensionReport::class.java, DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)
                    }?.sortedByDescending { it.updatedAt?.seconds ?: 0L } ?: emptyList()
                    trySend(list)
                }
            awaitClose { registration.remove() }
        }
    }

    suspend fun createExtensionReport(
        reportId: String,
        farmerName: String,
        county: String,
        title: String,
        summary: String,
        advisorEmail: String
    ) {
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to submit an extension report.")
        val cleanId = reportId.replace(Regex("[^a-zA-Z0-9_-]"), "_").take(120).ifBlank {
            "rep_${System.currentTimeMillis()}"
        }
        val path = "extension_reports/$cleanId"
        try {
            val data = hashMapOf<String, Any>(
                "farmerId" to uid,
                "farmerName" to farmerName.ifBlank { "CropShield Farmer" }.take(100),
                "county" to county.take(80),
                "title" to title.ifBlank { "Field Agronomy Report" }.take(150),
                "summary" to summary.ifBlank { "Field inspection summary" }.take(2000),
                "advisorEmail" to advisorEmail.trim().take(150),
                "advisorNotes" to "",
                "status" to "OPEN",
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("extension_reports").document(cleanId).set(data).await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.CREATE, path, auth)
        }
    }

    suspend fun submitAdvisorReview(
        reportId: String,
        advisorNotes: String,
        newStatus: String = "REVIEWED"
    ) {
        auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in as verified advisor.")
        val path = "extension_reports/$reportId"
        try {
            val updates = hashMapOf<String, Any>(
                "advisorNotes" to advisorNotes.take(1500),
                "status" to newStatus,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            firestore.collection("extension_reports").document(reportId).update(updates).await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.UPDATE, path, auth)
        }
    }

    suspend fun deleteExtensionReport(reportId: String) {
        auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to delete report.")
        val path = "extension_reports/$reportId"
        try {
            firestore.collection("extension_reports").document(reportId).delete().await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.DELETE, path, auth)
        }
    }

    companion object {
        const val COMMUNITY_ROOM_ID = "global_shamba_community"

        fun create(context: Context): CloudSyncRepository {
            val dbId = context.getString(R.string.firestore_database_id)
            val firestore = FirebaseFirestore.getInstance(dbId)
            return CloudSyncRepository(firestore = firestore, auth = Firebase.auth)
        }
    }
}
