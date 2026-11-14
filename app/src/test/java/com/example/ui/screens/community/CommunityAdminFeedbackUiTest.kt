package com.example.ui.screens.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.data.AgronomyCatalog
import com.example.model.cloud.AdminEventType
import com.example.model.cloud.AdminNotification
import com.example.model.cloud.AppFeedback
import com.example.model.cloud.CommunityMessage
import com.example.model.cloud.DirectThread
import com.example.model.cloud.FarmerRegistryEntry
import com.example.model.cloud.FeedbackFeatureSource
import com.example.ui.components.AuthSignUpCard
import com.example.ui.components.InlineHelpfulnessFeedbackBar
import com.example.ui.screens.admin.AdminConsoleScreen
import com.example.ui.screens.science.AgronomyDegreePackView
import com.example.ui.theme.CropShieldTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w412dp-h915dp")
class CommunityAdminFeedbackUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `auth signup card displays locality inputs, email privacy notice, and sign up button`() {
        composeTestRule.setContent {
            CropShieldTheme {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    AuthSignUpCard(
                        selectedCounty = AgronomyCatalog.kenyanCounties.first(),
                        onAuthCompleted = { _, _ -> }
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("auth_signup_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tab_auth_sign_up").assertIsDisplayed()
        composeTestRule.onNodeWithTag("tab_auth_sign_in").assertIsDisplayed()
        composeTestRule.onNodeWithTag("input_signup_locality").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithTag("btn_capture_locality_gps").performScrollTo().assertIsDisplayed()
        composeTestRule.onNodeWithText("other farmers never see your email", substring = true)
            .performScrollTo()
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag("btn_google_sign_in").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `inline feedback bar captures helpfulness vote and improvement suggestion`() {
        var capturedHelpful: Boolean? = null
        var capturedArea = ""
        var capturedSuggestion = ""

        composeTestRule.setContent {
            CropShieldTheme {
                InlineHelpfulnessFeedbackBar(
                    questionOrTopic = "Maize Fall Armyworm dosage",
                    testTagPrefix = "test_fb",
                    onSubmitFeedback = { helpful, area, suggestion ->
                        capturedHelpful = helpful
                        capturedArea = area
                        capturedSuggestion = suggestion
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag("test_fb_bar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("test_fb_helpful_no").performClick()
        composeTestRule.onNodeWithTag("test_fb_suggestion_input").performTextInput("Add Swahili audio examples")
        composeTestRule.onNodeWithTag("test_fb_submit_btn").performClick()

        assertEquals(false, capturedHelpful)
        assertTrue(capturedArea.isNotBlank())
        assertEquals("Add Swahili audio examples", capturedSuggestion)
    }

    @Test
    fun `community screen beta feedback tab submits under-development feedback`() {
        var submittedTopic = ""
        var submittedHelpful = true
        var submittedSuggestion = ""

        composeTestRule.setContent {
            CropShieldTheme {
                CommunityAndSupportScreen(
                    currentUser = null,
                    isSuperAdmin = false,
                    selectedCounty = AgronomyCatalog.kenyanCounties.first(),
                    communityMessages = emptyList(),
                    directMessages = emptyList(),
                    feedbackList = emptyList(),
                    onAuthCompleted = { _, _ -> },
                    onSendCommunityMessage = { _, _ -> },
                    onTogglePinSolution = { _, _ -> },
                    onDeleteCommunityMessage = {},
                    onSendDirectMessage = {},
                    onSubmitFeedback = { _, topic, helpful, _, suggestion ->
                        submittedTopic = topic
                        submittedHelpful = helpful
                        submittedSuggestion = suggestion
                    },
                    initialTab = 2
                )
            }
        }

        composeTestRule.onNodeWithTag("beta_vote_not_helpful").performClick()
        composeTestRule.onNodeWithTag("beta_feedback_topic_input").performTextInput("Bean Rust Diagnosis")
        composeTestRule.onNodeWithTag("beta_feedback_suggestion_input").performTextInput("Add local agrovet price guide")
        composeTestRule.onNodeWithTag("beta_feedback_submit_btn").performScrollTo().performClick()

        assertEquals("Bean Rust Diagnosis", submittedTopic)
        assertFalse(submittedHelpful)
        assertEquals("Add local agrovet price guide", submittedSuggestion)
    }

    @Test
    fun `non-admin community and support tabs hide super admin identity and email`() {
        val sampleMessages = listOf(
            CommunityMessage(
                id = "ai1",
                authorId = "bot",
                authorName = "Shamba AI Agronomist",
                authorRole = "ai_advisor",
                county = "Nakuru",
                topic = "Disease Help",
                messageText = "Use Ridomil Gold @ 50g/16L with 7 days PHI."
            )
        )

        // 1. Community tab with isSuperAdmin = false
        composeTestRule.setContent {
            CropShieldTheme {
                CommunityAndSupportScreen(
                    currentUser = null,
                    isSuperAdmin = false,
                    selectedCounty = AgronomyCatalog.kenyanCounties.first(),
                    communityMessages = sampleMessages,
                    directMessages = emptyList(),
                    feedbackList = emptyList(),
                    onAuthCompleted = { _, _ -> },
                    onSendCommunityMessage = { _, _ -> },
                    onTogglePinSolution = { _, _ -> },
                    onDeleteCommunityMessage = {},
                    onSendDirectMessage = {},
                    onSubmitFeedback = { _, _, _, _, _ -> },
                    initialTab = 0
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Ask Support").assertIsDisplayed()
        composeTestRule.onNodeWithText("Super Admin", substring = true, ignoreCase = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("odongovincento@gmail.com", substring = true, ignoreCase = true).assertDoesNotExist()

        // 2. Ask Support tab with isSuperAdmin = false
        composeTestRule.onNodeWithTag("tab_direct_admin").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Super Admin", substring = true, ignoreCase = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("odongovincento@gmail.com", substring = true, ignoreCase = true).assertDoesNotExist()
    }

    @Test
    fun `restricted admin console view hides super admin identity and email`() {
        composeTestRule.setContent {
            CropShieldTheme {
                AdminConsoleScreen(
                    isSuperAdmin = false,
                    farmerRegistry = emptyList(),
                    notifications = emptyList(),
                    directThreads = emptyList(),
                    activeDirectFarmerId = null,
                    directMessages = emptyList(),
                    feedbackList = emptyList(),
                    onSelectDirectFarmer = {},
                    onSendDirectReply = { _, _, _, _, _ -> },
                    onMarkNotificationRead = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("admin_access_restricted").assertIsDisplayed()
        composeTestRule.onNodeWithText("Super Admin", substring = true, ignoreCase = true).assertDoesNotExist()
        composeTestRule.onNodeWithText("odongovincento@gmail.com", substring = true, ignoreCase = true).assertDoesNotExist()
    }

    @Test
    fun `agronomy degree pack view displays all 3 volumes and filters subjects`() {
        composeTestRule.setContent {
            CropShieldTheme {
                AgronomyDegreePackView()
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("agronomy_degree_pack_view").assertIsDisplayed()
        composeTestRule.onNodeWithText("AGRONOMY — COMPLETE PACK (YEAR 1 TO YEAR 3)").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pack_filter_y1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pack_filter_y2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("pack_filter_y3").assertIsDisplayed()
        composeTestRule.onNodeWithText("Crop Production").assertIsDisplayed()

        // Filter to Year 3 and verify Climate-Smart Agriculture is shown
        composeTestRule.onNodeWithTag("pack_filter_y3").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Climate-Smart Agriculture").assertIsDisplayed()
    }

    @Test
    fun `admin console restricts non-admin and displays locality map and farmer emails to super admin`() {
        val isSuperAdminState = androidx.compose.runtime.mutableStateOf(false)
        val sampleFarmers = listOf(
            FarmerRegistryEntry(
                id = "f1",
                uid = "f1",
                email = "kamau.farm@example.com",
                displayName = "Peter Kamau",
                farmName = "Njoro Highlands Shamba",
                county = "Nakuru (Rift Valley)",
                locality = "Njoro",
                latitude = -0.33,
                longitude = 35.94,
                primaryCrops = "Maize, Potato",
                signInCount = 3L
            )
        )
        val sampleNotifs = listOf(
            AdminNotification(
                id = "n1",
                farmerId = "f1",
                farmerName = "Peter Kamau",
                farmerEmail = "kamau.farm@example.com",
                county = "Nakuru (Rift Valley)",
                locality = "Njoro",
                eventType = AdminEventType.NEW_FARMER.code,
                summary = "New farmer signed up: Peter Kamau (kamau.farm@example.com)"
            )
        )
        val sampleFeedback = listOf(
            AppFeedback(
                id = "fb1",
                userId = "f1",
                farmerName = "Peter Kamau",
                county = "Nakuru",
                featureSource = FeedbackFeatureSource.AI_AGRONOMIST.code,
                questionOrTopic = "Late blight spray",
                wasHelpful = true,
                improvementArea = "AI Agronomist",
                improvementSuggestion = "Great dosage calculator"
            )
        )

        composeTestRule.setContent {
            CropShieldTheme {
                AdminConsoleScreen(
                    isSuperAdmin = isSuperAdminState.value,
                    farmerRegistry = sampleFarmers,
                    notifications = sampleNotifs,
                    directThreads = listOf(
                        DirectThread(
                            id = "f1",
                            farmerId = "f1",
                            farmerName = "Peter Kamau",
                            county = "Nakuru",
                            locality = "Njoro",
                            lastMessagePreview = "Need advice on tomato blight"
                        )
                    ),
                    activeDirectFarmerId = "f1",
                    directMessages = emptyList(),
                    feedbackList = sampleFeedback,
                    onSelectDirectFarmer = {},
                    onSendDirectReply = { _, _, _, _, _ -> },
                    onMarkNotificationRead = {}
                )
            }
        }
        composeTestRule.onNodeWithTag("admin_access_restricted").assertIsDisplayed()

        isSuperAdminState.value = true
        composeTestRule.waitForIdle()

        // Map & Selected Farmer card with email visible to Super Admin
        composeTestRule.onNodeWithTag("admin_console_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("admin_farmer_map_canvas").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email: kamau.farm@example.com").assertIsDisplayed()

        // Switch to Farmer Emails DB tab
        composeTestRule.onNodeWithTag("admin_tab_database").performClick()
        composeTestRule.onNodeWithText("kamau.farm@example.com").assertIsDisplayed()

        // Switch to Email Alerts tab
        composeTestRule.onNodeWithTag("admin_tab_alerts").performClick()
        composeTestRule.onNodeWithTag("admin_alert_n1").assertIsDisplayed()
    }
}
