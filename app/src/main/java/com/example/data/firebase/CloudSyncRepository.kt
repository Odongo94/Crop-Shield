package com.example.data.firebase

import android.content.Context
import com.example.R
import com.example.model.cloud.CloudFarmRecord
import com.example.model.cloud.ExtensionReport
import com.example.model.cloud.FarmerProfile
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

class CloudSyncRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

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

    fun observeFarmRecords(): Flow<List<CloudFarmRecord>> {
        val uid = auth.currentUser?.uid ?: return flowOf(emptyList())
        val path = "users/$uid/farm_records"
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
        val uid = auth.currentUser?.uid
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
        val uid = auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to delete report.")
        val path = "extension_reports/$reportId"
        try {
            firestore.collection("extension_reports").document(reportId).delete().await()
        } catch (e: Exception) {
            FirestoreErrorHandler.handleFirestoreError(e, OperationType.DELETE, path, auth)
        }
    }

    companion object {
        fun create(context: Context): CloudSyncRepository {
            val dbId = context.getString(R.string.firestore_database_id)
            val firestore = FirebaseFirestore.getInstance(dbId)
            return CloudSyncRepository(firestore = firestore, auth = Firebase.auth)
        }
    }
}
