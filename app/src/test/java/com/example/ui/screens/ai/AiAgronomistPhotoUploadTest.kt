package com.example.ui.screens.ai

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.data.ai.AiAgronomistService
import com.example.ui.theme.CropShieldTheme
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AiAgronomistPhotoUploadTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `agronomist screen exposes upload photo and camera capture controls`() {
        composeTestRule.setContent {
            CropShieldTheme {
                AiAgronomistScreen()
            }
        }

        composeTestRule.onNodeWithTag("ai_agronomist_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("quick_prompt_upload_photo").assertIsDisplayed()
        composeTestRule.onNodeWithTag("button_upload_photo_ai").assertIsDisplayed()
        composeTestRule.onNodeWithTag("button_camera_photo_ai").assertIsDisplayed()
        composeTestRule.onNodeWithTag("button_send_ai_question").assertIsDisplayed()
    }

    @Test
    fun `ai agronomist analyzes uploaded crop bitmap even without typed question`() = runBlocking {
        val sampleLeafBitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.rgb(65, 110, 48))
        }

        val response = AiAgronomistService.askAgricultureAi(
            userQuestion = "",
            imageBitmap = sampleLeafBitmap
        )

        assertTrue("AI response text must not be blank", response.text.isNotBlank())
        assertTrue(
            "AI response must include agricultural diagnosis and recommendations",
            response.text.contains("Identified Condition", ignoreCase = true) ||
                response.text.contains("IPM", ignoreCase = true) ||
                response.text.contains("Crop", ignoreCase = true)
        )
    }
}
