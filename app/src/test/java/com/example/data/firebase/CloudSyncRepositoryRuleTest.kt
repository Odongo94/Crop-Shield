package com.example.data.firebase

import com.example.base.FirestoreEmulatorTestBase
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
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

    @Test
    fun `owner can create profile, upsert farm records, list, update, and delete`() = runBlocking {
        val uid = signInTestUser("farmer1@cropshield.test")
        val profile = withTimeout(5000L) {
            repo.ensureFarmerProfile(
                displayName = "Odongo Vincent",
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
    fun `unauthenticated and cross-user writes are rejected with PERMISSION_DENIED`() = runBlocking {
        val uid1 = signInTestUser("owner1@cropshield.test")
        withTimeout(5000L) {
            repo.ensureFarmerProfile("Farmer One", "Shamba 1", "Kiambu", "EN")
            repo.upsertFarmRecord("rec_${UUID.randomUUID().toString().take(8)}", "FIELD_NOTE", "Maize Scout", "Maize", "Clean", "{}")
        }

        // Unauthenticated
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

        // Cross-user attempt
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
