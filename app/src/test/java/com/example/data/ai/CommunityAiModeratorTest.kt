package com.example.data.ai

import com.example.model.cloud.CommunityMessage
import com.google.firebase.Timestamp
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CommunityAiModeratorTest {

    private fun ts(ms: Long): Timestamp = Timestamp(ms / 1000L, ((ms % 1000L) * 1_000_000L).toInt())

    @Test
    fun `evaluateUnansweredQuestions enforces 5-minute interval and skips replied or already-answered questions`() {
        val nowMs = 1_700_000_600_000L
        val fiveMinMs = CommunityAiModerator.DEFAULT_RESPONSE_DELAY_MS
        assertEquals(300_000L, fiveMinMs)

        // 1. Old unanswered question (6 minutes ago) -> should be ready
        val oldUnanswered = CommunityMessage(
            id = "q_old",
            authorId = "farmer_1",
            authorName = "Wanjiku",
            authorRole = "farmer",
            topic = "Pest Outbreak",
            messageText = "How do I control Fall Armyworm in V6 maize?",
            createdAt = ts(nowMs - 360_000L)
        )

        // 2. Recent unanswered question (2 minutes ago) -> should NOT be ready, nextDueDelayMs ~ 180_000L
        val recentUnanswered = CommunityMessage(
            id = "q_recent",
            authorId = "farmer_2",
            authorName = "Otieno",
            authorRole = "farmer",
            topic = "Disease Help",
            messageText = "Why are my tomato leaves showing dark water-soaked blight?",
            createdAt = ts(nowMs - 120_000L)
        )

        val result = CommunityAiModerator.evaluateUnansweredQuestions(
            messages = listOf(oldUnanswered, recentUnanswered),
            nowMs = nowMs
        )
        assertEquals(listOf("q_old"), result.readyQuestions.map { it.id })
        assertNotNull(result.nextDueDelayMs)
        assertEquals(180_000L, result.nextDueDelayMs)

        // 3. Once another farmer replies after q_old, q_old is no longer unanswered
        val peerReply = CommunityMessage(
            id = "r_peer",
            authorId = "farmer_3",
            authorName = "Kamau",
            authorRole = "farmer",
            topic = "Pest Outbreak",
            messageText = "Scout the whorls and spray Bt or Neem early morning.",
            createdAt = ts(nowMs - 200_000L)
        )
        val afterPeerReply = CommunityAiModerator.evaluateUnansweredQuestions(
            messages = listOf(oldUnanswered, peerReply),
            nowMs = nowMs
        )
        assertTrue(afterPeerReply.readyQuestions.isEmpty())
        assertNull(afterPeerReply.nextDueDelayMs)

        // 4. Deterministic AI response docId prevents duplicate AI replies
        val existingAiReply = CommunityMessage(
            id = CommunityAiModerator.aiResponseDocId("q_old"),
            authorId = "system_bot",
            authorName = CommunityAiModerator.AI_AUTHOR_NAME,
            authorRole = CommunityAiModerator.AI_AUTHOR_ROLE,
            topic = "Pest Outbreak",
            messageText = "Grounded IPM guidance...",
            createdAt = ts(nowMs - 10_000L)
        )
        val afterAiReply = CommunityAiModerator.evaluateUnansweredQuestions(
            messages = listOf(oldUnanswered, existingAiReply),
            nowMs = nowMs
        )
        assertTrue(afterAiReply.readyQuestions.isEmpty())
    }

    @Test
    fun `detectInvalidFarmerResponse catches unsafe, mismatched, and invalid agricultural advice`() = runBlocking {
        fun farmerMsg(id: String, text: String) = CommunityMessage(
            id = id,
            authorId = "farmer_x",
            authorName = "Test Farmer",
            authorRole = "farmer",
            topic = "General",
            messageText = text
        )

        // 1. Petroleum / bleach spray
        val dieselClaim = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m1", "Just mix diesel with water and spray the maize to kill stalk borers.")
        )
        assertNotNull(dieselClaim)
        assertEquals("Unsafe Substance Warning", dieselClaim?.category)

        // 2. Zero PHI claim
        val zeroPhi = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m2", "After spraying Coragen you can harvest immediately with zero phi.")
        )
        assertNotNull(zeroPhi)
        assertEquals("Food Safety & PHI Correction", zeroPhi?.category)

        // 3. Fungicide on Bacterial Wilt
        val bwFungicide = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m3", "Spray Ridomil Gold fungicide to cure Bacterial Wilt in tomatoes.")
        )
        assertNotNull(bwFungicide)
        assertEquals("Pathogen Mismatch Correction", bwFungicide?.category)

        // 4. Fungicide on Viral disease
        val virusFungicide = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m4", "Use Mancozeb fungicide to treat Maize Lethal Necrosis (MLN) virus.")
        )
        assertNotNull(virusFungicide)
        assertEquals("Viral Disease Correction", virusFungicide?.category)

        // 5. Insecticide on Late Blight (fungal/oomycete)
        val blightInsecticide = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m5", "Apply Coragen insecticide on tomato Late Blight lesions.")
        )
        assertNotNull(blightInsecticide)
        assertEquals("Pesticide Mode-of-Action Correction", blightInsecticide?.category)

        // 6. Fungicide on Fall Armyworm (insect pest)
        val fawFungicide = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m6", "Spray Mancozeb fungicide inside the whorl to kill Fall Armyworm caterpillars.")
        )
        assertNotNull(fawFungicide)
        assertEquals("Pest vs Disease Product Correction", fawFungicide?.category)

        // 7. DAP/Urea to raise soil pH
        val dapAcid = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m7", "Topdress with DAP 18:46:0 to raise pH and neutralize acid soil.")
        )
        assertNotNull(dapAcid)
        assertEquals("Soil Chemistry Correction", dapAcid?.category)

        // 8. Solanaceae rotation error
        val solanaceae = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m8", "You should plant tomato after potato on the same ridge every season.")
        )
        assertNotNull(solanaceae)
        assertEquals("Crop Rotation Correction", solanaceae?.category)

        // 9. Untreated tap water for poultry vaccines
        val poultryWater = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m9", "Mix Newcastle vaccine directly in municipal tap water without any additive.")
        )
        assertNotNull(poultryWater)
        assertEquals("Poultry Vaccination Correction", poultryWater?.category)

        // Verify buildFactCheckCorrection formats deterministic correction without exposing admin info
        val correctionAction = CommunityAiModerator.buildFactCheckCorrection(
            farmerMsg("m3", "Spray Ridomil Gold fungicide to cure Bacterial Wilt in tomatoes."),
            bwFungicide!!
        )
        assertEquals("ai_corr_m3", correctionAction.docId)
        assertEquals(CommunityAiActionType.FACT_CORRECTION, correctionAction.actionType)
        assertTrue(correctionAction.messageText.contains("Fact-Check"))
        assertTrue(!correctionAction.messageText.contains("odongovincento@gmail.com"))
        assertTrue(!correctionAction.messageText.contains("Super Admin", ignoreCase = true))

        // 10. Valid advice should return null
        val validAdvice = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m10", "Use agricultural lime 4 weeks before planting to raise acidic soil pH above 5.8.")
        )
        assertNull(validAdvice)

        // 11. Pure question should return null
        val pureQuestion = CommunityAiModerator.detectInvalidFarmerResponse(
            farmerMsg("m11", "Is Bacterial Wilt cured by Ridomil or do I uproot?")
        )
        assertNull(pureQuestion)
    }
}
