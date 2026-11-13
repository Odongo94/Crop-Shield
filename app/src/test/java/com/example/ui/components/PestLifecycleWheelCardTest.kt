package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.example.data.ThreatCatalog
import com.example.ui.theme.CropShieldTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PestLifecycleWheelCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `tapping through egg, larva, pupa, and adult nodes reveals stage details`() {
        val fallArmyworm = ThreatCatalog.getThreatById("fall_armyworm")!!

        composeTestRule.setContent {
            CropShieldTheme {
                Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    PestLifecycleWheelCard(threat = fallArmyworm)
                }
            }
        }

        // Initial Egg stage visible
        composeTestRule.onNodeWithTag("pest_lifecycle_wheel_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pest_stage_node_egg").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pest_stage_node_larva").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pest_stage_node_pupa").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pest_stage_node_adult").assertIsDisplayed()
        composeTestRule.onNodeWithText("Egg Masses Under Scales").performScrollTo().assertIsDisplayed()

        // Tap Larva node directly
        composeTestRule.onNodeWithTag("pest_stage_node_larva").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Instars 1–3 'Window Pane' Feeding").performScrollTo().assertIsDisplayed()

        // Tap Pupa node directly
        composeTestRule.onNodeWithTag("pest_stage_node_pupa").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Pupation in Soil Cocoon").performScrollTo().assertIsDisplayed()

        // Tap Adult node directly
        composeTestRule.onNodeWithTag("pest_stage_node_adult").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Adult Night-Flying Moth").performScrollTo().assertIsDisplayed()

        // Tap Next button to cycle back to Egg
        composeTestRule.onNodeWithTag("pest_stage_next_btn").performScrollTo().performClick()
        composeTestRule.onNodeWithText("Egg Masses Under Scales").performScrollTo().assertIsDisplayed()
    }
}
