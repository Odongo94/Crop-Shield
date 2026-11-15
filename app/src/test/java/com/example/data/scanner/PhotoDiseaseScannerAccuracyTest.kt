package com.example.data.scanner

import android.graphics.Bitmap
import android.graphics.Color
import com.example.data.ai.AiAgronomistService
import com.example.model.ThreatType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PhotoDiseaseScannerAccuracyTest {

    private fun createLeafWithWhiteBackdropAndChewHoles(): Bitmap {
        val bmp = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888)
        for (y in 0 until 80) {
            for (x in 0 until 80) {
                val color = when {
                    // White paper/sky border around outside (25%+ bright white)
                    x < 12 || x >= 68 || y < 12 || y >= 68 -> Color.rgb(245, 245, 245)
                    // Jagged insect-chewed holes inside leaf exposing bright background
                    (x in 25..31 && y in 25..31) || (x in 45..52 && y in 40..47) -> Color.rgb(245, 245, 245)
                    // Healthy green foliage
                    else -> Color.rgb(34, 139, 34)
                }
                bmp.setPixel(x, y, color)
            }
        }
        return bmp
    }

    @Test
    fun `explicit PEST filter never misclassifies pest sample as a plant disease`() {
        val bitmap = createLeafWithWhiteBackdropAndChewHoles()

        val result = PhotoDiseaseScanner.analyzeOfflineOpticalPatterns(
            bitmap = bitmap,
            cropHint = "Tomato",
            categoryFilter = ThreatType.PEST
        )

        assertEquals(ThreatType.PEST, result.diagnosticCategory)
        assertNotEquals("late_blight", result.threatId)
        assertNotEquals("rose_powdery_mildew", result.threatId)
    }

    @Test
    fun `auto-detect does not confuse bright background margins on pest leaf for powdery mildew`() {
        val bitmap = createLeafWithWhiteBackdropAndChewHoles()

        val tomatoResult = PhotoDiseaseScanner.analyzeOfflineOpticalPatterns(
            bitmap = bitmap,
            cropHint = "Tomato",
            categoryFilter = null
        )
        assertEquals(
            "Chewed high-contrast leaf borders on white backdrop should diagnose as PEST, not Disease",
            ThreatType.PEST,
            tomatoResult.diagnosticCategory
        )

        val autoResult = PhotoDiseaseScanner.analyzeOfflineOpticalPatterns(
            bitmap = bitmap,
            cropHint = "Auto-Detect",
            categoryFilter = null
        )
        assertEquals(
            "Crop-agnostic Auto-Detect on chewed leaf with bright margins should diagnose as PEST, not Powdery Mildew",
            ThreatType.PEST,
            autoResult.diagnosticCategory
        )
    }

    @Test
    fun `parseVisionJsonResponse preserves custom non-catalog pest without falling back to late_blight`() {
        val rawJson = """
            {
              "threatId": "custom_pest",
              "threatType": "PEST",
              "diseaseName": "Green Cabbage Looper",
              "scientificName": "Trichoplusia ni",
              "crop": "Cabbage",
              "confidence": 93,
              "severity": "Severe",
              "symptoms": ["Ragged holes chewed between leaf veins", "Green caterpillar pellets near whorl"],
              "culturalAction": "Handpick larvae and install pheromone traps",
              "mechanicalAction": "Crush egg clusters on leaf undersides",
              "recommendedChemicals": ["Indoxacarb 150 SC - 10ml/20L"],
              "summary": "Active chewing caterpillar visible on cabbage leaf margin.",
              "cabiBiocontrol": "Bacillus thuringiensis kurstaki (Bt)",
              "infonetPractice": "Intercrop brassicas with aromatic marigold",
              "pcpbChemical": "Bt / Indoxacarb rotation"
            }
        """.trimIndent()

        val result = PhotoDiseaseScanner.parseVisionJsonResponse(
            rawText = rawJson,
            cropHint = "Auto-Detect",
            categoryFilter = null
        )

        assertEquals(ThreatType.PEST, result.diagnosticCategory)
        assertEquals("Green Cabbage Looper", result.diseaseName)
        assertNull("Unmatched custom pest must NOT force-attach a wrong disease Threat", result.threat)
        assertNotEquals("late_blight", result.threatId)
    }

    @Test
    fun `ai agronomist offline engine dynamically resolves quarantine pests, weeds, fertilizers, and degree modules`() {
        val fcmReply = AiAgronomistService.generateOfflineAgronomicResponse("How do I stop False Codling Moth on export avocado?")
        assertTrue(fcmReply.contains("False Codling Moth", ignoreCase = true))

        val strigaReply = AiAgronomistService.generateOfflineAgronomicResponse("How do I manage Striga hermonthica in maize?")
        assertTrue(strigaReply.contains("Striga", ignoreCase = true) || strigaReply.contains("Witchweed", ignoreCase = true))

        val tomatoFert = AiAgronomistService.generateOfflineAgronomicResponse("What fertilizer plan should I use for Tomato?")
        assertTrue(tomatoFert.contains("Basal", ignoreCase = true))

        val degreeReply = AiAgronomistService.generateOfflineAgronomicResponse("Tell me about Genetics & Plant Breeding syllabus")
        assertTrue(degreeReply.contains("Plant Breeding", ignoreCase = true) || degreeReply.contains("Genetics", ignoreCase = true))
    }
}
