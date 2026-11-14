package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.Role
import com.example.ui.components.UniversalSearchDialog
import com.example.ui.theme.AppThemeMode
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.ThreatCatalog
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.Terrain
import com.example.ui.screens.AgronomistProfileDialog
import com.example.ui.screens.AgronomyScreen
import com.example.ui.screens.CalculatorScreen
import com.example.ui.screens.DiagnoseScreen
import com.example.ui.screens.FieldMapScreen
import com.example.ui.screens.FieldNotesScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.LifecycleLabScreen
import com.example.ui.screens.PhotoDiseaseScannerScreen
import com.example.ui.screens.ScoutLogScreen
import com.example.ui.screens.ScoutingSheetScreen
import com.example.ui.screens.ThreatDetailScreen
import com.example.ui.theme.CropShieldTheme
import com.example.viewmodel.AppScreen
import com.example.viewmodel.CropShieldViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CropShieldViewModel = viewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            CropShieldTheme(themeMode = themeMode) {
                CropShieldApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropShieldApp(viewModel: CropShieldViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val allThreats by viewModel.allThreats.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedThreat by viewModel.selectedThreat.collectAsStateWithLifecycle()
    val selectedChemical by viewModel.selectedChemical.collectAsStateWithLifecycle()
    val prefilledScoutThreat by viewModel.prefilledScoutThreat.collectAsStateWithLifecycle()
    val scoutRecords by viewModel.scoutRecords.collectAsStateWithLifecycle()
    val fieldNotes by viewModel.fieldNotes.collectAsStateWithLifecycle()
    val prefilledNoteCrop by viewModel.prefilledNoteCrop.collectAsStateWithLifecycle()
    val prefilledNotePest by viewModel.prefilledNotePest.collectAsStateWithLifecycle()

    // Theme and Global Search States
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val isGlobalSearchOpen by viewModel.isGlobalSearchOpen.collectAsStateWithLifecycle()
    val globalSearchQuery by viewModel.globalSearchQuery.collectAsStateWithLifecycle()
    val globalSearchCategory by viewModel.globalSearchCategory.collectAsStateWithLifecycle()
    val unifiedSearchResults by viewModel.unifiedSearchResults.collectAsStateWithLifecycle()

    // New Farmer Features State
    val appLanguage by viewModel.appLanguage.collectAsStateWithLifecycle()
    val phiLogs by viewModel.phiLogs.collectAsStateWithLifecycle()
    val poultryBatches by viewModel.poultryBatches.collectAsStateWithLifecycle()
    val activeSeasons by viewModel.activeSeasons.collectAsStateWithLifecycle()
    val livestockBreeding by viewModel.livestockBreeding.collectAsStateWithLifecycle()
    val savedFields by viewModel.savedFields.collectAsStateWithLifecycle()
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    val selectedCounty by viewModel.selectedCounty.collectAsStateWithLifecycle()
    val farmSizeHectares by viewModel.farmSizeHectares.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val isSuperAdmin by viewModel.isSuperAdmin.collectAsStateWithLifecycle()
    val cloudRecords by viewModel.cloudRecords.collectAsStateWithLifecycle()
    val myExtensionReports by viewModel.myExtensionReports.collectAsStateWithLifecycle()
    val advisorInboxReports by viewModel.advisorInboxReports.collectAsStateWithLifecycle()
    val isCloudSyncing by viewModel.isCloudSyncing.collectAsStateWithLifecycle()
    val cloudSyncMessage by viewModel.cloudSyncMessage.collectAsStateWithLifecycle()
    val farmerRegistry by viewModel.farmerRegistry.collectAsStateWithLifecycle()
    val adminNotifications by viewModel.adminNotifications.collectAsStateWithLifecycle()
    val communityMessages by viewModel.communityMessages.collectAsStateWithLifecycle()
    val directThreads by viewModel.directThreads.collectAsStateWithLifecycle()
    val activeDirectFarmerId by viewModel.activeDirectThreadFarmerId.collectAsStateWithLifecycle()
    val directMessages by viewModel.directMessages.collectAsStateWithLifecycle()
    val appFeedbackList by viewModel.appFeedbackList.collectAsStateWithLifecycle()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showAgronomistDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = currentScreen != AppScreen.LIBRARY) {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            if (currentScreen != AppScreen.THREAT_DETAIL && currentScreen != AppScreen.FIELD_MAP && currentScreen != AppScreen.SCOUTING_SHEETS) {
                TopAppBar(
                    title = {
                        Text(
                            text = when (currentScreen) {
                                AppScreen.LIBRARY -> "CropShield IPM"
                                AppScreen.SCANNER -> "Photo AI Disease Scanner"
                                AppScreen.AGRONOMY -> "Agronomy & Farm Suite"
                                AppScreen.AGRONOMY_PACK -> "Agronomy Y1–Y3 Complete Pack"
                                AppScreen.FIELD_MAP -> "Offline Field & Zone Mapper"
                                AppScreen.DIAGNOSE -> "Symptom Diagnosis"
                                AppScreen.LIFECYCLE_LAB -> "Lifecycle Lab"
                                AppScreen.CALCULATOR -> "Knapsack Fertilizer Calculator"
                                AppScreen.SOIL_CALCULATOR -> "Soil Texture & Rotation Calculator"
                                AppScreen.FIELD_NOTES -> "Field Agronomy Notebook"
                                AppScreen.SCOUT_LOG -> "Field Scouting Logs"
                                AppScreen.SCOUTING_SHEETS -> "Scouting Sheets & Protocols"
                                AppScreen.AI_AGRONOMIST -> "Ask AI Agronomist"
                                AppScreen.BIOSCIENCE_SKILLS -> "Bioscience Skills & Web Apps"
                                AppScreen.SMART_ALERTS -> "PHI & Smart Farm Alerts"
                                AppScreen.ECONOMICS -> "Farm Economics & ROI"
                                AppScreen.CROP_SEASONS -> "Active Crop Seasons"
                                AppScreen.LIVESTOCK -> "Livestock Ration & Breeding"
                                AppScreen.POST_HARVEST -> "Post-Harvest & Storage"
                                AppScreen.CLOUD_SYNC -> "Cloud Backup & Advisor Sync"
                                AppScreen.COMMUNITY_HUB -> "Shamba Community & Support"
                                AppScreen.ADMIN_CONSOLE -> "Admin Map & Database"
                                AppScreen.THREAT_DETAIL -> ""
                            },
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        if (isSuperAdmin) {
                            IconButton(
                                onClick = { viewModel.navigateTo(AppScreen.ADMIN_CONSOLE) },
                                modifier = Modifier.testTag("action_open_admin_console")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = "Admin Console & Farmers Map",
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.COMMUNITY_HUB) },
                            modifier = Modifier.testTag("action_open_community_hub")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Forum,
                                contentDescription = "Community & Support Chat",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.SMART_ALERTS) },
                            modifier = Modifier.testTag("action_open_smart_alerts")
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Smart Farm Alerts & PHI",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.CLOUD_SYNC) },
                            modifier = Modifier.testTag("action_open_cloud_sync")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudSync,
                                contentDescription = "Cloud Backup & Sync",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        // 0. Bioscience Skills Action Button (Flask Icon)
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.BIOSCIENCE_SKILLS) },
                            modifier = Modifier.testTag("action_open_bioscience_skills")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = "Bioscience Skills with Flask Icon",
                                tint = if (currentScreen == AppScreen.BIOSCIENCE_SKILLS) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary
                            )
                        }

                        // 1. AI Agronomist Action Button
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.AI_AGRONOMIST) },
                            modifier = Modifier.testTag("action_open_ai_agronomist")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = "Ask AI Agronomist",
                                tint = if (currentScreen == AppScreen.AI_AGRONOMIST) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary
                            )
                        }

                        // 2. Universal Search Action (Search disease, pest, weed, crop)
                        IconButton(
                            onClick = { viewModel.openGlobalSearch() },
                            modifier = Modifier.testTag("action_global_search")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Universal Search",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        // 2. Offline Field Boundary & Agro-Zone Map Action
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.FIELD_MAP) },
                            modifier = Modifier.testTag("action_open_field_map")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Terrain,
                                contentDescription = "Field & Zone Map",
                                tint = if (currentScreen == AppScreen.FIELD_MAP) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 3. Theme Mode Switcher (System Default, Light, Dark)
                        IconButton(
                            onClick = { showThemeDialog = true },
                            modifier = Modifier.testTag("action_theme_switcher")
                        ) {
                            Icon(
                                imageVector = when (themeMode) {
                                    AppThemeMode.SYSTEM -> Icons.Default.BrightnessAuto
                                    AppThemeMode.LIGHT -> Icons.Default.LightMode
                                    AppThemeMode.DARK -> Icons.Default.DarkMode
                                },
                                contentDescription = "Theme: ${themeMode.title}",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 4. Scouting Sheets Table (Pests & Diseases Tables & CSV)
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.SCOUTING_SHEETS) },
                            modifier = Modifier.testTag("action_open_scouting_sheets")
                        ) {
                            Icon(
                                imageVector = Icons.Default.TableChart,
                                contentDescription = "Scouting Sheets & Protocols",
                                tint = if (currentScreen == AppScreen.SCOUTING_SHEETS) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 5. Lead Agronomist & Developer Profile (Odongo Vincent)
                        IconButton(
                            onClick = { showAgronomistDialog = true },
                            modifier = Modifier.testTag("action_developer_profile")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Lead Agronomist & Developer Odongo Vincent",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        },
        bottomBar = {
            if (currentScreen != AppScreen.THREAT_DETAIL && currentScreen != AppScreen.FIELD_MAP && currentScreen != AppScreen.SCOUTING_SHEETS) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar")
                ) {
                    NavigationBarItem(
                        selected = currentScreen == AppScreen.LIBRARY,
                        onClick = { viewModel.navigateTo(AppScreen.LIBRARY) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Eco,
                                contentDescription = "Library",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Catalog", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_library")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.SCANNER,
                        onClick = { viewModel.navigateTo(AppScreen.SCANNER) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "AI Scanner",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("AI Scan", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_scanner")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.AGRONOMY,
                        onClick = { viewModel.navigateTo(AppScreen.AGRONOMY) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Agriculture,
                                contentDescription = "Agronomy",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Agronomy", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_agronomy")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.DIAGNOSE,
                        onClick = { viewModel.navigateTo(AppScreen.DIAGNOSE) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Diagnose",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Diagnose", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_diagnose")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.CALCULATOR,
                        onClick = { viewModel.navigateTo(AppScreen.CALCULATOR) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = "Knapsack Calculator",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Knapsack", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_calculator")
                    )

                    NavigationBarItem(
                        selected = currentScreen == AppScreen.FIELD_NOTES,
                        onClick = { viewModel.navigateTo(AppScreen.FIELD_NOTES) },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = "Field Agronomy Notebook",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text("Notebook", style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_field_notes")
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    AppScreen.LIBRARY -> {
                        LibraryScreen(
                            threats = searchResults,
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                            onThreatClick = { threat -> viewModel.openThreatDetail(threat) },
                            onNavigateToDiagnose = { viewModel.navigateTo(AppScreen.DIAGNOSE) },
                            onOpenFeature = { route ->
                                when (route) {
                                    "AGRONOMY_PACK" -> viewModel.navigateTo(AppScreen.AGRONOMY_PACK)
                                    "COMMUNITY_HUB" -> viewModel.navigateTo(AppScreen.COMMUNITY_HUB)
                                    "ADMIN_CONSOLE" -> viewModel.navigateTo(AppScreen.ADMIN_CONSOLE)
                                    "SMART_ALERTS" -> viewModel.navigateTo(AppScreen.SMART_ALERTS)
                                    "CROP_SEASONS" -> viewModel.navigateTo(AppScreen.CROP_SEASONS)
                                    "ECONOMICS" -> viewModel.navigateTo(AppScreen.ECONOMICS)
                                    "LIVESTOCK" -> viewModel.navigateTo(AppScreen.LIVESTOCK)
                                    "POST_HARVEST" -> viewModel.navigateTo(AppScreen.POST_HARVEST)
                                    "AI_AGRONOMIST" -> viewModel.navigateTo(AppScreen.AI_AGRONOMIST)
                                    "CLOUD_SYNC" -> viewModel.navigateTo(AppScreen.CLOUD_SYNC)
                                }
                            },
                            isSuperAdmin = isSuperAdmin
                        )
                    }

                    AppScreen.AGRONOMY_PACK -> {
                        com.example.ui.screens.science.AgronomyDegreePackView()
                    }

                    AppScreen.SCANNER -> {
                        val scanBitmap by viewModel.scanBitmap.collectAsStateWithLifecycle()
                        val isScanning by viewModel.isScanning.collectAsStateWithLifecycle()
                        val scanResult by viewModel.scanResult.collectAsStateWithLifecycle()
                        val scanError by viewModel.scanError.collectAsStateWithLifecycle()
                        val selectedCropHint by viewModel.selectedCropHint.collectAsStateWithLifecycle()
                        val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsStateWithLifecycle()

                        PhotoDiseaseScannerScreen(
                            scannedBitmap = scanBitmap,
                            isScanning = isScanning,
                            scanResult = scanResult,
                            scanError = scanError,
                            selectedCropHint = selectedCropHint,
                            onCropHintSelected = { hint -> viewModel.setSelectedCropHint(hint) },
                            onPhotoCaptured = { bitmap -> viewModel.onPhotoCaptured(bitmap) },
                            onResetScan = { viewModel.resetScan() },
                            onOpenThreatDetail = { threat -> viewModel.openThreatDetail(threat) },
                            onOpenCalculator = { threat, chem -> viewModel.openCalculator(threat, chem) },
                            onSaveToFieldNotes = { crop, pest -> viewModel.openFieldNotes(crop, pest) },
                            selectedCategoryFilter = selectedCategoryFilter,
                            onCategoryFilterSelected = { cat -> viewModel.setSelectedCategoryFilter(cat) },
                            onSubmitFeedback = { topic, helpful, area, suggestion ->
                                viewModel.submitAppFeedback("PHOTO_SCANNER", topic, helpful, area, suggestion)
                            }
                        )
                    }

                    AppScreen.AGRONOMY -> {
                        val selectedCropPlan by viewModel.selectedCropPlan.collectAsStateWithLifecycle()
                        val selectedBasalProduct by viewModel.selectedBasalProduct.collectAsStateWithLifecycle()
                        val selectedTopdressProduct by viewModel.selectedTopdressProduct.collectAsStateWithLifecycle()
                        val fertilizerResult by viewModel.fertilizerResult.collectAsStateWithLifecycle()
                        val filteredWeeds by viewModel.filteredWeeds.collectAsStateWithLifecycle()
                        val weedQuery by viewModel.weedQuery.collectAsStateWithLifecycle()
                        val selectedWeedType by viewModel.selectedWeedType.collectAsStateWithLifecycle()

                        AgronomyScreen(
                            farmSizeHectares = farmSizeHectares,
                            selectedCropPlan = selectedCropPlan,
                            selectedBasalProduct = selectedBasalProduct,
                            selectedTopdressProduct = selectedTopdressProduct,
                            fertilizerResult = fertilizerResult,
                            weedList = filteredWeeds,
                            weedQuery = weedQuery,
                            selectedWeedType = selectedWeedType,
                            selectedCounty = selectedCounty,
                            weatherState = weatherState,
                            onHectaresChanged = { viewModel.setFarmHectares(it) },
                            onCropPlanSelected = { viewModel.setCropPlan(it) },
                            onBasalProductSelected = { viewModel.setBasalProduct(it) },
                            onTopdressProductSelected = { viewModel.setTopdressProduct(it) },
                            onWeedQueryChanged = { viewModel.setWeedSearchQuery(it) },
                            onWeedTypeSelected = { viewModel.setWeedTypeFilter(it) },
                            onCountySelected = { viewModel.fetchWeatherForCounty(it) },
                            onAutoDetectLocation = { viewModel.autoDetectFarmLocationAndFetchWeather() },
                            onNavigateToFieldMap = { viewModel.navigateTo(AppScreen.FIELD_MAP) },
                            phiLogs = phiLogs,
                            poultryBatches = poultryBatches,
                            activeSeasons = activeSeasons,
                            mappedFields = savedFields,
                            livestockBreeding = livestockBreeding,
                            appLanguage = appLanguage,
                            currentUser = currentUser,
                            cloudRecords = cloudRecords,
                            myReports = myExtensionReports,
                            advisorInboxReports = advisorInboxReports,
                            isCloudSyncing = isCloudSyncing,
                            cloudSyncMessage = cloudSyncMessage,
                            onLanguageSelected = { viewModel.setAppLanguage(it) },
                            onAddPhiLog = { viewModel.addPhiLog(it) },
                            onDeletePhiLog = { viewModel.deletePhiLog(it) },
                            onAddPoultryBatch = { viewModel.addPoultryBatch(it) },
                            onDeletePoultryBatch = { viewModel.deletePoultryBatch(it) },
                            onAddCropSeason = { viewModel.addCropSeason(it) },
                            onDeleteCropSeason = { viewModel.deleteCropSeason(it) },
                            onAddBreedingRecord = { viewModel.addBreedingRecord(it) },
                            onDeleteBreedingRecord = { viewModel.deleteBreedingRecord(it) },
                            onSaveBudgetSummary = { t, c, s -> viewModel.saveBudgetToCloud(t, c, s) },
                            onGoogleSignedIn = { viewModel.onGoogleSignedIn(it) },
                            onBackupAllToCloud = { viewModel.backupAllFarmDataToCloud() },
                            onDeleteCloudRecord = { viewModel.deleteCloudRecord(it) },
                            onSubmitExtensionReport = { t, s, e -> viewModel.submitExtensionReport(t, s, e) },
                            onReviewExtensionReport = { id, n, st -> viewModel.submitAdvisorReview(id, n, st) },
                            onDeleteExtensionReport = { viewModel.deleteExtensionReport(it) },
                            isSuperAdmin = isSuperAdmin,
                            onGoogleAuthCompleted = { user, reg -> viewModel.onGoogleSignedIn(user, reg) },
                            onSubmitAiFeedback = { topic, helpful, area, suggestion ->
                                viewModel.submitAppFeedback("AI_AGRONOMIST", topic, helpful, area, suggestion)
                            },
                            onOpenCommunity = { viewModel.navigateTo(AppScreen.COMMUNITY_HUB) },
                            onOpenAdminConsole = { viewModel.navigateTo(AppScreen.ADMIN_CONSOLE) }
                        )
                    }

                    AppScreen.FIELD_MAP -> {
                        val savedFields by viewModel.savedFields.collectAsStateWithLifecycle()
                        FieldMapScreen(
                            savedFields = savedFields,
                            onSaveField = { field -> viewModel.saveMappedField(field) },
                            onDeleteField = { field -> viewModel.deleteMappedField(field) },
                            onNavigateBack = { viewModel.navigateBack() },
                            onCalculateFertilizerForField = { hectares, cropName ->
                                viewModel.openInKnapsackCalculator(hectares, cropName)
                            }
                        )
                    }

                    AppScreen.DIAGNOSE -> {
                        DiagnoseScreen(
                            onThreatClick = { threat -> viewModel.openThreatDetail(threat) }
                        )
                    }

                    AppScreen.LIFECYCLE_LAB -> {
                        LifecycleLabScreen(
                            onNavigateToThreatDetail = { threat -> viewModel.openThreatDetail(threat) },
                            onNavigateToCalculator = { threat -> viewModel.openCalculator(threat) }
                        )
                    }

                    AppScreen.CALCULATOR -> {
                        val farmSizeHectares by viewModel.farmSizeHectares.collectAsStateWithLifecycle()
                        val selectedCropPlan by viewModel.selectedCropPlan.collectAsStateWithLifecycle()
                        val selectedKnapsackLiters by viewModel.selectedKnapsackLiters.collectAsStateWithLifecycle()
                        val selectedBasalProduct by viewModel.selectedBasalProduct.collectAsStateWithLifecycle()
                        val selectedTopdressProduct by viewModel.selectedTopdressProduct.collectAsStateWithLifecycle()
                        val selectedFoliarProduct by viewModel.selectedFoliarProduct.collectAsStateWithLifecycle()
                        val knapsackResult by viewModel.knapsackFertilizerResult.collectAsStateWithLifecycle()

                        CalculatorScreen(
                            farmSizeHectares = farmSizeHectares,
                            selectedCropPlan = selectedCropPlan,
                            selectedKnapsackLiters = selectedKnapsackLiters,
                            selectedBasalProduct = selectedBasalProduct,
                            selectedTopdressProduct = selectedTopdressProduct,
                            selectedFoliarProduct = selectedFoliarProduct,
                            knapsackResult = knapsackResult,
                            onHectaresChanged = { viewModel.setFarmHectares(it) },
                            onCropPlanSelected = { viewModel.setCropPlan(it) },
                            onKnapsackLitersSelected = { viewModel.setKnapsackTankLiters(it) },
                            onBasalProductSelected = { viewModel.setBasalProduct(it) },
                            onTopdressProductSelected = { viewModel.setTopdressProduct(it) },
                            onFoliarProductSelected = { viewModel.setFoliarProduct(it) },
                            initialThreat = selectedThreat,
                            initialChemical = selectedChemical,
                            onNavigateToFieldMap = { viewModel.navigateTo(AppScreen.FIELD_MAP) }
                        )
                    }

                    AppScreen.SOIL_CALCULATOR -> {
                        com.example.ui.screens.soil.SoilClassificationScreen(
                            onNavigateBack = { viewModel.navigateBack() }
                        )
                    }

                    AppScreen.FIELD_NOTES -> {
                        FieldNotesScreen(
                            notes = fieldNotes,
                            onSaveNote = { note -> viewModel.saveFieldNote(note) },
                            onDeleteNote = { note -> viewModel.deleteFieldNote(note) },
                            prefilledCrop = prefilledNoteCrop,
                            prefilledPest = prefilledNotePest
                        )
                    }

                    AppScreen.SCOUT_LOG -> {
                        ScoutLogScreen(
                            records = scoutRecords,
                            onAddRecord = { record -> viewModel.saveScoutRecord(record) },
                            onDeleteRecord = { record -> viewModel.deleteScoutRecord(record) },
                            prefilledThreat = prefilledScoutThreat,
                            onPrefillConsumed = { viewModel.clearPrefilledScoutThreat() },
                            onNavigateToScoutingSheets = { viewModel.navigateTo(AppScreen.SCOUTING_SHEETS) }
                        )
                    }

                    AppScreen.SCOUTING_SHEETS -> {
                        ScoutingSheetScreen(
                            onNavigateBack = { viewModel.navigateBack() }
                        )
                    }

                    AppScreen.THREAT_DETAIL -> {
                        val threat = selectedThreat ?: ThreatCatalog.allThreats.first()
                        ThreatDetailScreen(
                            threat = threat,
                            onBackClick = { viewModel.navigateBack() },
                            onNavigateToCalculator = { t, c -> viewModel.openCalculator(t, c) },
                            onAddScoutLog = { t -> viewModel.openAddScout(t) },
                            onAddFieldNote = { crop, pest -> viewModel.openFieldNotes(crop, pest) }
                        )
                    }

                    AppScreen.AI_AGRONOMIST -> {
                        com.example.ui.screens.ai.AiAgronomistScreen(
                            appLanguage = appLanguage,
                            onLanguageSelected = { viewModel.setAppLanguage(it) },
                            onNavigateToCalculator = { screenCode ->
                                when (screenCode) {
                                    "SOIL_CALCULATOR" -> viewModel.navigateTo(AppScreen.SOIL_CALCULATOR)
                                    "CALCULATOR", "NOZZLE" -> viewModel.navigateTo(AppScreen.CALCULATOR)
                                    "POULTRY", "AUDIT" -> viewModel.navigateTo(AppScreen.AGRONOMY)
                                    else -> viewModel.navigateTo(AppScreen.CALCULATOR)
                                }
                            },
                            onSubmitFeedback = { topic, helpful, area, suggestion ->
                                viewModel.submitAppFeedback("AI_AGRONOMIST", topic, helpful, area, suggestion)
                            }
                        )
                    }

                    AppScreen.BIOSCIENCE_SKILLS -> {
                        com.example.ui.screens.science.BioscienceSkillsScreen(
                            onNavigateBack = { viewModel.navigateBack() }
                        )
                    }

                    AppScreen.SMART_ALERTS -> {
                        com.example.ui.screens.alerts.SmartAlertsView(
                            phiLogs = phiLogs,
                            poultryBatches = poultryBatches,
                            weatherState = weatherState,
                            appLanguage = appLanguage,
                            onAddPhiLog = { viewModel.addPhiLog(it) },
                            onDeletePhiLog = { viewModel.deletePhiLog(it) },
                            onAddPoultryBatch = { viewModel.addPoultryBatch(it) },
                            onDeletePoultryBatch = { viewModel.deletePoultryBatch(it) }
                        )
                    }

                    AppScreen.ECONOMICS -> {
                        com.example.ui.screens.economics.FarmEconomicsScreen(
                            initialHectares = farmSizeHectares,
                            onSaveBudgetSummary = { t, c, s -> viewModel.saveBudgetToCloud(t, c, s) }
                        )
                    }

                    AppScreen.CROP_SEASONS -> {
                        com.example.ui.screens.season.CropSeasonTrackerView(
                            seasons = activeSeasons,
                            mappedFields = savedFields,
                            onAddSeason = { viewModel.addCropSeason(it) },
                            onDeleteSeason = { viewModel.deleteCropSeason(it) }
                        )
                    }

                    AppScreen.LIVESTOCK -> {
                        com.example.ui.screens.livestock.LivestockManagerView(
                            breedingRecords = livestockBreeding,
                            onAddBreedingRecord = { viewModel.addBreedingRecord(it) },
                            onDeleteBreedingRecord = { viewModel.deleteBreedingRecord(it) }
                        )
                    }

                    AppScreen.POST_HARVEST -> {
                        com.example.ui.screens.postharvest.PostHarvestStorageView(
                            appLanguage = appLanguage
                        )
                    }

                    AppScreen.CLOUD_SYNC -> {
                        com.example.ui.screens.cloud.CloudSyncScreen(
                            currentUser = currentUser,
                            cloudRecords = cloudRecords,
                            myReports = myExtensionReports,
                            advisorInboxReports = advisorInboxReports,
                            appLanguage = appLanguage,
                            selectedCountyName = selectedCounty.name,
                            isSyncing = isCloudSyncing,
                            syncStatusMessage = cloudSyncMessage,
                            onLanguageSelected = { viewModel.setAppLanguage(it) },
                            onSignedIn = { viewModel.onGoogleSignedIn(it) },
                            onBackupAllToCloud = { viewModel.backupAllFarmDataToCloud() },
                            onDeleteCloudRecord = { viewModel.deleteCloudRecord(it) },
                            onSubmitExtensionReport = { t, s, e -> viewModel.submitExtensionReport(t, s, e) },
                            onReviewExtensionReport = { id, n, st -> viewModel.submitAdvisorReview(id, n, st) },
                            onDeleteExtensionReport = { viewModel.deleteExtensionReport(it) },
                            selectedCounty = selectedCounty,
                            isSuperAdmin = isSuperAdmin,
                            onAuthWithRegistration = { user, reg -> viewModel.onGoogleSignedIn(user, reg) },
                            onOpenCommunity = { viewModel.navigateTo(AppScreen.COMMUNITY_HUB) },
                            onOpenAdminConsole = { viewModel.navigateTo(AppScreen.ADMIN_CONSOLE) }
                        )
                    }

                    AppScreen.COMMUNITY_HUB -> {
                        com.example.ui.screens.community.CommunityAndSupportScreen(
                            currentUser = currentUser,
                            isSuperAdmin = isSuperAdmin,
                            selectedCounty = selectedCounty,
                            communityMessages = communityMessages,
                            directMessages = directMessages,
                            feedbackList = appFeedbackList,
                            onAuthCompleted = { user, reg -> viewModel.onGoogleSignedIn(user, reg) },
                            onSendCommunityMessage = { topic, text -> viewModel.sendCommunityMessage(topic, text) },
                            onTogglePinSolution = { id, pinned -> viewModel.toggleCommunitySolutionPin(id, pinned) },
                            onDeleteCommunityMessage = { id -> viewModel.deleteCommunityMessage(id) },
                            onSendDirectMessage = { text -> viewModel.sendDirectMessageToAdmin(text) },
                            onSubmitFeedback = { source, topic, helpful, area, suggestion ->
                                viewModel.submitAppFeedback(source, topic, helpful, area, suggestion)
                            },
                            onOpenAdminConsole = { viewModel.navigateTo(AppScreen.ADMIN_CONSOLE) }
                        )
                    }

                    AppScreen.ADMIN_CONSOLE -> {
                        com.example.ui.screens.admin.AdminConsoleScreen(
                            isSuperAdmin = isSuperAdmin,
                            farmerRegistry = farmerRegistry,
                            notifications = adminNotifications,
                            directThreads = directThreads,
                            activeDirectFarmerId = activeDirectFarmerId,
                            directMessages = directMessages,
                            feedbackList = appFeedbackList,
                            onSelectDirectFarmer = { farmerId -> viewModel.selectDirectThreadFarmer(farmerId) },
                            onSendDirectReply = { farmerId, farmerName, county, locality, text ->
                                viewModel.sendAdminDirectReply(farmerId, farmerName, county, locality, text)
                            },
                            onMarkNotificationRead = { notifId -> viewModel.markAdminNotificationRead(notifId) },
                            onOpenCommunityChat = { viewModel.navigateTo(AppScreen.COMMUNITY_HUB) }
                        )
                    }
                }
            }
        }
    }

    // Universal Multi-Category Search Dialog (Disease, Pest, Weed, Crop, Fertilizer)
    if (isGlobalSearchOpen) {
        UniversalSearchDialog(
            searchQuery = globalSearchQuery,
            selectedCategory = globalSearchCategory,
            results = unifiedSearchResults,
            onQueryChange = { viewModel.setGlobalSearchQuery(it) },
            onCategoryChange = { viewModel.setGlobalSearchCategory(it) },
            onDismiss = { viewModel.closeGlobalSearch() },
            onSelectThreat = { threat -> viewModel.openThreatDetail(threat) },
            onSelectWeed = { weed ->
                viewModel.setWeedSearchQuery(weed.commonName)
                viewModel.navigateTo(AppScreen.AGRONOMY)
            },
            onSelectCrop = { crop -> viewModel.openCropInCalculator(crop) },
            onSelectFertilizer = { fert -> viewModel.openFertilizerInCalculator(fert) }
        )
    }

    // Theme Mode Selection Dialog (Default / Light / Dark)
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = {
                Text(
                    text = "Display Appearance Mode",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Choose light, dark, or system default mode. The app updates immediately.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    AppThemeMode.values().forEach { mode ->
                        val selected = themeMode == mode
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selected,
                                    onClick = {
                                        viewModel.setThemeMode(mode)
                                        showThemeDialog = false
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selected,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = mode.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = mode.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showThemeDialog = false }) {
                    Text("Done")
                }
            }
        )
    }

    // Lead Agronomist & Developer Profile Dialog (Odongo Vincent)
    if (showAgronomistDialog) {
        AgronomistProfileDialog(onDismiss = { showAgronomistDialog = false })
    }
}

