package com.example.ui.screens.science

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import com.example.data.science.curriculum.AgriCurriculumBooksCatalog
import com.example.ui.components.GoogleSearchMode
import com.example.ui.components.buildGoogleReferenceUrl
import com.example.ui.theme.CropShieldTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w412dp-h915dp")
class AgriBooksInAppBrowserTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `google reference URL builder constructs Search, Scholar, and Books URLs accurately`() {
        val searchUrl = buildGoogleReferenceUrl("soil pH liming", GoogleSearchMode.GOOGLE_SEARCH)
        assertTrue(searchUrl.startsWith("https://www.google.com/search?q="))

        val scholarUrl = buildGoogleReferenceUrl("Taiz Zeiger", GoogleSearchMode.GOOGLE_SCHOLAR)
        assertTrue(scholarUrl.startsWith("https://scholar.google.com/scholar?q="))

        val booksUrl = buildGoogleReferenceUrl("Agrios Plant Pathology", GoogleSearchMode.GOOGLE_BOOKS)
        assertTrue(booksUrl.startsWith("https://www.google.com/search?tbm=bks&q="))

        val directUrl = buildGoogleReferenceUrl("https://www.fao.org/3/x0490e/x0490e00.htm", GoogleSearchMode.GOOGLE_SEARCH)
        assertTrue(directUrl.startsWith("https://www.fao.org"))
    }

    @Test
    fun `books catalog contains enriched textbooks with factual notes and formulas`() {
        assertTrue("Catalog must include expanded volumes", AgriCurriculumBooksCatalog.books.size >= 20)
        assertTrue(AgriCurriculumBooksCatalog.books.all { it.isbn.isNotBlank() })
        assertTrue(AgriCurriculumBooksCatalog.books.all { it.keyTakeaways.isNotEmpty() })
        assertTrue(AgriCurriculumBooksCatalog.books.all { it.keyFormulasOrPrinciples.isNotEmpty() })
    }

    @Test
    fun `agri books library expands factual notes and opens in-app google browser dialog`() {
        composeTestRule.setContent {
            CropShieldTheme {
                AgriBooksLibraryView()
            }
        }

        composeTestRule.waitForIdle()

        // Top Google Research Hub card is displayed
        composeTestRule.onNodeWithTag("books_google_hub_card").assertIsDisplayed()
        composeTestRule.onNodeWithTag("btn_hub_open_google").assertIsDisplayed()

        // Filter to a textbook by ISBN or formula
        composeTestRule.onNodeWithTag("books_search_field").performTextInput("Pedigo")
        composeTestRule.waitForIdle()

        val bookId = "book_pedigo_entomology_pest_mgmt"
        composeTestRule.onNodeWithTag("book_card_$bookId").assertIsDisplayed()

        // Tap "Google Info" to launch In-App Google Reference Browser
        composeTestRule.onNodeWithTag("btn_book_google_$bookId").assertIsDisplayed().performClick()
        composeTestRule.waitForIdle()

        // In-App Browser Dialog is displayed with all 3 Google mode tabs & URL input
        composeTestRule.onNodeWithTag("in_app_browser_dialog").assertExists()
        composeTestRule.onNodeWithTag("browser_tab_google").assertExists()
        composeTestRule.onNodeWithTag("browser_tab_scholar").assertExists()
        composeTestRule.onNodeWithTag("browser_tab_books").assertExists()
        composeTestRule.onNodeWithTag("browser_url_input").assertExists()

        // Switch mode to Google Scholar and verify URL updates
        composeTestRule.onNodeWithTag("browser_tab_scholar").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("https://scholar.google.com/scholar?q=", substring = true).assertExists()

        // Close dialog cleanly
        composeTestRule.onNodeWithTag("browser_close_btn").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("in_app_browser_dialog").assertDoesNotExist()

        // Expand study notes & formulas on the textbook card
        composeTestRule.onNodeWithTag("btn_toggle_book_notes_$bookId").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("book_notes_$bookId").assertExists()
        composeTestRule.onNodeWithTag("book_formulas_$bookId").assertExists()
    }
}
