package com.example.data.firebase

import com.example.base.FirestoreEmulatorTestBase
import com.example.model.DeveloperInfo
import com.example.model.cloud.FeedbackFeatureSource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Before
import org.junit.Test
import java.util.UUID

class CloudSyncRepositoryRuleTest : FirestoreEmulatorTestBase() {

    private lateinit var repo: CloudSyncRepository

    @Before
    fun initRepo() {
        assumeTrue("Firestore/Auth emulators must be running on 8085/9099", isEmulatorRunning())
        clearEmulatorFirestore()
        clearEmulatorAuth()
        repo = CloudSyncRepository(firestore = db, auth = auth)
    }

    private suspend fun signInVerifiedSuperAdmin(): String {
        val uid = signInTestUser(DeveloperInfo.EMAIL)
        verifyEmailInEmulator(uid)
        val user = checkNotNull(auth.currentUser)
        user.getIdToken(true).await()
        user.reload().await()
        return uid
    }

    @Test
    fun `owner can create profile, upsert farm records, list, update, and delete`() = runBlocking {
        val uid = signInTestUser("farmer1@cropshield.test")
        val profile = withTimeout(5000L) {
            repo.ensureFarmerProfile(
                displayName = "Grace Achieng",
                farmName = "Nakuru Ridge",
                county = "Nakuru",
                preferredLanguage = "SW"
            )
        }
        assertEquals(uid, profile.uid)
        assertEquals("farmer", profile.role)

        val recordId = "phi_tomato_${UUID.randomUUID().toString().take(8)}"
        withTimeout(5000L) {
            repo.upsertFarmRecord(
                recordId = recordId,
                recordType = "PHI_SPRAY",
                title = "Tomato Late Blight Spray",
                cropOrSpecies = "Tomato",
                summary = "Ridomil Gold 7d PHI",
                payloadJson = "{\"phiDays\":7}"
            )
        }

        val records = withTimeout(5000L) {
            repo.observeFarmRecords().first { it.isNotEmpty() }
        }
        assertEquals(1, records.size)
        assertEquals(recordId, records.first().id)

        withTimeout(5000L) { repo.deleteFarmRecord(recordId) }
        val afterDelete = withTimeout(5000L) { repo.observeFarmRecords().first { it.isEmpty() } }
        assertTrue(afterDelete.isEmpty())
    }

    @Test
    fun `farmer registry emails and admin notifications are strictly isolated to super admin`() = runBlocking {
        val farmer1Uid = signInTestUser("wanjiku@cropshield.test")
        val isNew = withTimeout(5000L) {
            repo.registerOrCheckInFarmer(
                displayName = "Wanjiku Kamau",
                farmName = "Limuru Greens",
                county = "Kiambu (Thika / Limuru)",
                locality = "Limuru Town",
                latitude = -1.1069,
                longitude = 36.6425,
                primaryCrops = "Roses, Kale"
            )
        }
        assertTrue(isNew)

        // Regular farmer cannot list farmer_registry or read admin_notifications
        val farmer2Uid = signInTestUser("otieno@cropshield.test")
        assertTrue(farmer2Uid != farmer1Uid)

        var listRegistryError: Throwable? = null
        try {
            withTimeout(5000L) { db.collection("farmer_registry").get().await() }
        } catch (t: Throwable) {
            listRegistryError = t
        }
        assertEquals(
            FirebaseFirestoreException.Code.PERMISSION_DENIED,
            listRegistryError?.unwrapFirestoreException()?.code
        )

        var readNotifError: Throwable? = null
        try {
            withTimeout(5000L) { db.collection("admin_notifications").get().await() }
        } catch (t: Throwable) {
            readNotifError = t
        }
        assertEquals(
            FirebaseFirestoreException.Code.PERMISSION_DENIED,
            readNotifError?.unwrapFirestoreException()?.code
        )

        // Verified Super Admin can read all farmer emails, localities, and notifications
        signInVerifiedSuperAdmin()
        assertTrue(repo.isCurrentUserSuperAdmin())

        val registry = withTimeout(5000L) { repo.observeAllFarmerRegistry().first { it.isNotEmpty() } }
        assertEquals(1, registry.size)
        assertEquals("wanjiku@cropshield.test", registry.first().email)
        assertEquals("Limuru Town", registry.first().locality)

        val notifications = withTimeout(5000L) { repo.observeAdminNotifications().first { it.isNotEmpty() } }
        assertEquals(1, notifications.size)
        assertFalse(notifications.first().isRead)
    }

    @Test
    fun `community chat, direct admin chat, and feedback enforce role and thread isolation`() = runBlocking {
        val f1Uid = signInTestUser("farmer_chat@cropshield.test")

        withTimeout(5000L) {
            repo.sendCommunityMessage(
                authorName = "Kamau",
                county = "Nyeri",
                topic = "Pests & IPM",
                messageText = "Best trap for tomato leafminer?"
            )
            repo.sendDirectMessage(
                farmerId = f1Uid,
                farmerName = "Kamau",
                county = "Nyeri",
                locality = "Karatina",
                messageText = "Need direct advice on Tuta absoluta."
            )
            repo.submitAppFeedback(
                farmerName = "Kamau",
                county = "Nyeri",
                locality = "Karatina",
                featureSource = FeedbackFeatureSource.AI_AGRONOMIST.code,
                questionOrTopic = "Tuta control",
                wasHelpful = true,
                improvementArea = "Photo Accuracy",
                improvementSuggestion = "Add Swahili voice guide for traps."
            )
        }

        // Stranger cannot read farmer1's direct messages or feedback
        signInTestUser("stranger@cropshield.test")
        var crossDirectErr: Throwable? = null
        try {
            withTimeout(5000L) {
                repo.observeDirectMessages(f1Uid).first()
            }
        } catch (t: Throwable) {
            crossDirectErr = t
        }
        assertEquals(
            FirebaseFirestoreException.Code.PERMISSION_DENIED,
            crossDirectErr?.unwrapFirestoreException()?.code
        )

        // Verified Super Admin sees community message, direct thread, replies directly, and sees feedback
        signInVerifiedSuperAdmin()
        val community = withTimeout(5000L) { repo.observeCommunityMessages().first { it.isNotEmpty() } }
        assertEquals("farmer", community.first().authorRole)

        val threads = withTimeout(5000L) { repo.observeAllDirectThreadsForAdmin().first { it.isNotEmpty() } }
        assertEquals(f1Uid, threads.first().farmerId)

        withTimeout(5000L) {
            repo.sendDirectMessage(
                farmerId = f1Uid,
                farmerName = "Kamau",
                county = "Nyeri",
                locality = "Karatina",
                messageText = "Use Tutrack pheromone trap + Coragen 20SC."
            )
        }
        val dMsgs = withTimeout(5000L) { repo.observeDirectMessages(f1Uid).first { it.size >= 2 } }
        assertEquals("super_admin", dMsgs.last().senderRole)

        val feedbacks = withTimeout(5000L) { repo.observeFeedback().first { it.isNotEmpty() } }
        assertEquals("Photo Accuracy", feedbacks.first().improvementArea)
    }

    @Test
    fun `unauthenticated and cross-user writes are rejected with PERMISSION_DENIED`() = runBlocking {
        val uid1 = signInTestUser("owner1@cropshield.test")
        withTimeout(5000L) {
            repo.ensureFarmerProfile("Farmer One", "Shamba 1", "Kiambu", "EN")
            repo.upsertFarmRecord("rec_${UUID.randomUUID().toString().take(8)}", "FIELD_NOTE", "Maize Scout", "Maize", "Clean", "{}")
        }

        auth.signOut()
        var unauthError: Throwable? = null
        try {
            withTimeout(5000L) {
                db.collection("users").document(uid1).collection("farm_records").get().await()
            }
        } catch (t: Throwable) {
            unauthError = t
        }
        assertEquals(
            FirebaseFirestoreException.Code.PERMISSION_DENIED,
            unauthError?.unwrapFirestoreException()?.code
        )

        signInTestUser("attacker@cropshield.test")
        var crossUserError: Throwable? = null
        try {
            withTimeout(5000L) {
                db.collection("users").document(uid1).collection("farm_records").document("hack")
                    .set(
                        mapOf(
                            "ownerId" to uid1,
                            "recordType" to "FIELD_NOTE",
                            "title" to "Hack",
                            "cropOrSpecies" to "Maize",
                            "summary" to "X",
                            "payloadJson" to "{}"
                        )
                    )
                    .await()
            }
        } catch (t: Throwable) {
            crossUserError = t
        }
        assertEquals(
            FirebaseFirestoreException.Code.PERMISSION_DENIED,
            crossUserError?.unwrapFirestoreException()?.code
        )
    }

    @Test
    fun `farmer can create extension report and delete it`() = runBlocking {
        signInTestUser("farmer_rep@cropshield.test")
        val reportId = "rep_field_${UUID.randomUUID().toString().take(8)}"
        withTimeout(5000L) {
            repo.createExtensionReport(
                reportId = reportId,
                farmerName = "Wanjiku",
                county = "Nyeri",
                title = "Coffee Berry Disease Check",
                summary = "Need verification on copper spray rate.",
                advisorEmail = "advisor@kalro.org"
            )
        }

        val myReports = withTimeout(5000L) {
            repo.observeMyExtensionReports().first { it.isNotEmpty() }
        }
        assertEquals(1, myReports.size)
        assertEquals("OPEN", myReports.first().status)

        withTimeout(5000L) { repo.deleteExtensionReport(reportId) }
        val remaining = withTimeout(5000L) { repo.observeMyExtensionReports().first { it.isEmpty() } }
        assertTrue(remaining.isEmpty())
    }
}
