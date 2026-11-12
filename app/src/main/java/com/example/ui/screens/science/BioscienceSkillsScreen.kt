package com.example.ui.screens.science

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ai.BioscienceGeminiService
import com.example.data.science.BioscienceSkillsCatalog
import com.example.model.ai.AiChatMessage
import com.example.model.science.BioscienceDomain
import com.example.model.science.BioscienceSkillModule
import com.example.model.science.ScientificSearchTarget
import com.example.model.science.ScientificWebAppScaffold
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Main Bioscience Skills & Scientific Web Applications Screen.
 * Featuring Flask Icon, bioinformatics, molecular dynamics, chemistry tools,
 * biological databases, Google search engine, and Gemini chatbot.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioscienceSkillsScreen(
    onNavigateBack: () -> Unit = {}
) {
    var selectedMainTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("11 Global Portals", "NUFLIP Factsheets", "Web App Scaffolds", "Curriculum", "Search & AI")

    ScaffoldWithHeader(
        onNavigateBack = onNavigateBack,
        selectedTab = selectedMainTab,
        tabTitles = tabTitles,
        onTabSelected = { selectedMainTab = it }
    ) {
        when (selectedMainTab) {
            0 -> GlobalPestPortalsTab()
            1 -> NuflipFactsheetsTab()
            2 -> BioscienceScaffoldsTab()
            3 -> BioscienceCurriculumTab()
            4 -> BioscienceGeminiChatTab()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScaffoldWithHeader(
    onNavigateBack: () -> Unit,
    selectedTab: Int,
    tabTitles: List<String>,
    onTabSelected: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("bioscience_skills_screen")
    ) {
        // Hero Header with Flask Icon
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("btn_bioscience_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Science, // Conical Flask Icon
                                contentDescription = "Bioscience Skills Flask Icon",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Bioscience Skills",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "11 Global Portals • NUFLIP Factsheets • Web Scaffolds • AI",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable Tab Row for 5 tabs
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    edgePadding = 8.dp,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { onTabSelected(index) },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            },
                            modifier = Modifier.testTag("tab_bioscience_$index")
                        )
                    }
                }
            }
        }

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            content()
        }
    }
}

// =====================================================================
// TAB 0: CURRICULUM & MODULES
// =====================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BioscienceCurriculumTab() {
    var searchQuery by remember { mutableStateOf("") }
    var selectedDomain by remember { mutableStateOf<BioscienceDomain?>(null) }
    var selectedModuleForDialog by remember { mutableStateOf<BioscienceSkillModule?>(null) }

    val modules = remember(searchQuery, selectedDomain) {
        val base = if (selectedDomain != null) {
            BioscienceSkillsCatalog.getModulesByDomain(selectedDomain)
        } else {
            BioscienceSkillsCatalog.allModules
        }
        if (searchQuery.isNotBlank()) {
            val q = searchQuery.trim().lowercase()
            base.filter {
                it.title.lowercase().contains(q) ||
                it.summary.lowercase().contains(q) ||
                it.keyConcepts.any { c -> c.lowercase().contains(q) } ||
                it.toolsAndLibraries.any { t -> t.lowercase().contains(q) }
            }
        } else {
            base
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Search TextField
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_bioscience_module_search"),
                placeholder = { Text("Search bioinformatics, RDKit, OpenMM, BLAST...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Domain Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedDomain == null,
                    onClick = { selectedDomain = null },
                    label = { Text("All Domains (${BioscienceSkillsCatalog.allModules.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.testTag("filter_domain_all")
                )

                BioscienceDomain.entries.forEach { domain ->
                    FilterChip(
                        selected = selectedDomain == domain,
                        onClick = { selectedDomain = if (selectedDomain == domain) null else domain },
                        label = { Text(domain.title) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = domain.badgeColor.copy(alpha = 0.2f),
                            selectedLabelColor = domain.badgeColor
                        ),
                        modifier = Modifier.testTag("filter_domain_${domain.name.lowercase()}")
                    )
                }
            }
        }

        // Module Cards
        items(modules, key = { it.id }) { module ->
            BioscienceModuleCard(
                module = module,
                onViewDetails = { selectedModuleForDialog = module }
            )
        }

        if (modules.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No matching bioscience modules found",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Try clearing the search query or selecting 'All Domains'.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }

    // Detail Dialog
    selectedModuleForDialog?.let { module ->
        BioscienceModuleDetailDialog(
            module = module,
            onDismiss = { selectedModuleForDialog = null }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BioscienceModuleCard(
    module: BioscienceSkillModule,
    onViewDetails: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_module_${module.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Domain Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = module.domain.badgeColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, module.domain.badgeColor.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = module.domain.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = module.domain.badgeColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "${module.toolsAndLibraries.size} Tools",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Summary
            Text(
                text = module.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tools Chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                module.toolsAndLibraries.forEach { tool ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.height(26.dp)
                    ) {
                        Text(
                            text = tool,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(10.dp))

            // Footer Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recipe: ${module.codeRecipe.framework}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = onViewDetails,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("btn_view_module_${module.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Study & Code")
                }
            }
        }
    }
}

// =====================================================================
// MODULE DETAIL & CODE RECIPE DIALOG
// =====================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BioscienceModuleDetailDialog(
    module: BioscienceSkillModule,
    onDismiss: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = module.domain.badgeColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Summary
                Text(
                    text = module.summary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Key Concepts
                Text(
                    text = "Key Scientific Concepts:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                module.keyConcepts.forEach { concept ->
                    Text(
                        text = "• $concept",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Web App Architecture
                Text(
                    text = "Web App Architecture:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = module.webAppArchitecture,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Agricultural Relevance
                Text(
                    text = "Agricultural & Crop Science Application:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = module.agriculturalRelevance,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Code Recipe Box
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Code Recipe: ${module.codeRecipe.title}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(module.codeRecipe.code))
                            Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.testTag("btn_copy_code_recipe")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Code Recipe",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Surface(
                    color = Color(0xFF1E1E1E),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SelectionContainer {
                        Text(
                            text = module.codeRecipe.code,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Color(0xFFD4D4D4),
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = module.codeRecipe.explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Database Links
                Text(
                    text = "External Biological Databases:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(6.dp))
                module.databaseLinks.forEach { link ->
                    OutlinedButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Could not open browser: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("${link.title} (${link.provider})", fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.testTag("btn_close_module_dialog")
            ) {
                Text("Done")
            }
        }
    )
}

// =====================================================================
// TAB 1: WEB APP SCAFFOLDS
// =====================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BioscienceScaffoldsTab() {
    val scaffolds = BioscienceSkillsCatalog.webAppScaffolds
    var selectedScaffold by remember { mutableStateOf<ScientificWebAppScaffold?>(null) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Scientific Web App Blueprints",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Production starter templates for Streamlit, FastAPI, RDKit, and Biological ETL pipelines.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        items(scaffolds, key = { it.id }) { scaffold ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_scaffold_${scaffold.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = scaffold.domain.badgeColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = scaffold.tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = scaffold.domain.badgeColor,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(scaffold.sampleCode))
                                Toast.makeText(context, "Scaffold code copied!", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.testTag("btn_copy_scaffold_${scaffold.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Scaffold Code",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = scaffold.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = scaffold.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tech Stack Chips
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        scaffold.techStack.forEach { tech ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = tech,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Architecture Components
                    Text(
                        text = "Core Components:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    scaffold.architectureComponents.forEach { comp ->
                        Text(
                            text = " • $comp",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Preview Button
                    OutlinedButton(
                        onClick = { selectedScaffold = scaffold },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_inspect_scaffold_${scaffold.id}")
                    ) {
                        Icon(imageVector = Icons.Default.Code, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Inspect Complete Blueprint & Code")
                    }
                }
            }
        }
    }

    // Scaffold Code Dialog
    selectedScaffold?.let { sc ->
        AlertDialog(
            onDismissRequest = { selectedScaffold = null },
            title = {
                Text(sc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Demo Input: ${sc.demoInputDescription}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Expected Output: ${sc.expectedOutputDescription}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        color = Color(0xFF1E1E1E),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SelectionContainer {
                            Text(
                                text = sc.sampleCode,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                color = Color(0xFFD4D4D4),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    clipboardManager.setText(AnnotatedString(sc.sampleCode))
                    Toast.makeText(context, "Code copied to clipboard!", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Copy Code")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedScaffold = null }) {
                    Text("Close")
                }
            }
        )
    }
}

// =====================================================================
// TAB 2: GOOGLE SEARCH ENGINE & SCIENTIFIC DATABASES
// =====================================================================
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BioscienceSearchEngineTab() {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedTarget by remember { mutableStateOf(ScientificSearchTarget.GOOGLE) }

    val presetQueries = listOf(
        "AlphaFold 3 plant pathogen protein structure",
        "Streamlit 3D molecular viewer tutorial python",
        "RDKit pesticide QSAR bioactivity scikit-learn",
        "NCBI Entrez E-utilities python esearch efetch",
        "CRISPR Cas9 off target prediction plant genomics",
        "OpenMM molecular dynamics membrane protein simulation",
        "PubChem PUG REST API download 3D SDF structures",
        "Biopython PairwiseAligner dynamic programming matrix"
    )

    fun launchScientificSearch(query: String, target: ScientificSearchTarget) {
        val q = query.trim()
        if (q.isBlank()) {
            Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
            return
        }

        val encodedQuery = URLEncoder.encode(q, StandardCharsets.UTF_8.toString())
        val fullUrl = if (target.queryParam.isBlank()) {
            "${target.baseUrl}$encodedQuery"
        } else {
            "${target.baseUrl}?${target.queryParam}=$encodedQuery"
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fullUrl))
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open browser: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Engine Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Scientific Search Engine",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Query peer-reviewed literature, gene sequences, 3D crystals, and agrochemical records across worldwide repositories.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Target Engine Filter Chips
                    Text(
                        text = "Select Scientific Engine:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ScientificSearchTarget.entries.forEach { target ->
                            FilterChip(
                                selected = selectedTarget == target,
                                onClick = { selectedTarget = target },
                                label = { Text(target.displayName, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier.testTag("target_chip_${target.name.lowercase()}")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Input Field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_scientific_search_query"),
                        placeholder = { Text("Enter scientific search query...") },
                        trailingIcon = {
                            IconButton(
                                onClick = { launchScientificSearch(searchQuery, selectedTarget) },
                                modifier = Modifier.testTag("btn_run_scientific_search")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            launchScientificSearch(searchQuery, selectedTarget)
                        }),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = selectedTarget.helperText,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { launchScientificSearch(searchQuery, selectedTarget) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btn_launch_engine"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Search on ${selectedTarget.displayName}")
                    }
                }
            }
        }

        // Preset Scientific Research Queries
        item {
            Text(
                text = "Trending Scientific & Web Development Queries:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        items(presetQueries) { query ->
            OutlinedCard(
                onClick = {
                    searchQuery = query
                    launchScientificSearch(query, selectedTarget)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("card_preset_search_${query.hashCode()}"),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = query,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Tap to search on ${selectedTarget.displayName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.OpenInBrowser,
                        contentDescription = "Open Search",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// =====================================================================
// TAB 3: GEMINI BIOSCIENCE CHATBOT
// =====================================================================
@Composable
private fun BioscienceGeminiChatTab() {
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var userInput by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    val chatMessages = remember {
        mutableStateListOf(
            AiChatMessage(
                text = buildString {
                    append("👋 **Greetings! I am your CropShield Bioscience & Bioinformatics AI.**\n\n")
                    append("I specialize in:\n")
                    append("• **Building Scientific Web Apps**: Streamlit dashboards, Dash reactive apps, FastAPI microservices, and 3Dmol.js molecular viewers.\n")
                    append("• **Bioinformatics**: Sequence alignment (BLAST+, Biopython), phylogenetic tree reconstruction, and NGS variant calling pipelines.\n")
                    append("• **Molecular Dynamics**: AlphaFold 2/3 structure analysis, OpenMM/GROMACS simulations, and AutoDock Vina in silico pesticide docking.\n")
                    append("• **Chemoinformatics**: RDKit molecule processing, SMILES/InChI parsing, Lipinski Rule of 5 bioavailability, and QSAR modeling.\n")
                    append("• **Biological Databases**: Programmatic APIs for NCBI Entrez, UniProt KB, RCSB PDB, and PubChem PUG REST.\n\n")
                    append("Ask a question below or tap any **Quick Prompt** to get started!")
                },
                isUser = false,
                isOnlineGemini = true,
                topicCategory = "Bioscience AI Guide"
            )
        )
    }

    fun submitQuestion(prompt: String) {
        if (prompt.isBlank() || isSending) return
        val question = prompt.trim()
        userInput = ""

        chatMessages.add(AiChatMessage(text = question, isUser = true))
        isSending = true

        coroutineScope.launch {
            try {
                val response = BioscienceGeminiService.askBioscienceAi(
                    question = question,
                    history = chatMessages.toList()
                )
                chatMessages.add(response)
            } catch (e: Exception) {
                chatMessages.add(
                    AiChatMessage(
                        text = "An error occurred: ${e.message}. Please retry.",
                        isUser = false,
                        isOnlineGemini = false
                    )
                )
            } finally {
                isSending = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("bioscience_gemini_chat_tab")
    ) {
        // Quick Prompts Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(BioscienceGeminiService.quickPrompts, key = { it.id }) { qp ->
                OutlinedCard(
                    onClick = { submitQuestion(qp.fullPrompt) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.testTag("quick_prompt_${qp.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = qp.iconEmoji, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = qp.shortLabel,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = qp.category,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        // Message Thread
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chatMessages) { message ->
                BioscienceChatMessageBubble(
                    message = message,
                    onCopy = {
                        clipboardManager.setText(AnnotatedString(message.text))
                        Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                    },
                    onSelectFollowUp = { followUp ->
                        submitQuestion(followUp)
                    }
                )
            }

            if (isSending) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Analyzing bioscience inquiry...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Input Field
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    placeholder = { Text("Ask about Streamlit, BLAST, OpenMM, RDKit...") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_bioscience_chat"),
                    maxLines = 4,
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        submitQuestion(userInput)
                    })
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { submitQuestion(userInput) },
                    enabled = userInput.isNotBlank() && !isSending,
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (userInput.isNotBlank() && !isSending) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                        .testTag("btn_send_bioscience_chat")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Bioscience Query",
                        tint = if (userInput.isNotBlank() && !isSending) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BioscienceChatMessageBubble(
    message: AiChatMessage,
    onCopy: () -> Unit,
    onSelectFollowUp: (String) -> Unit
) {
    val isUser = message.isUser
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bubbleColor = if (isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }
    val textColor = if (isUser) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        // Tag Indicator for AI
        if (!isUser) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (message.isOnlineGemini) "Gemini 2.5 Flash • Bioscience AI" else "Offline Scientific Base",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Bubble Content
        Surface(
            color = bubbleColor,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            shadowElevation = if (isUser) 0.dp else 2.dp,
            modifier = Modifier.widthIn(max = 340.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                SelectionContainer {
                    Text(
                        text = message.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor
                    )
                }

                // Copy Action for Model response
                if (!isUser) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = onCopy,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy message",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // Suggested Follow-up Chips
        if (!isUser && message.suggestedFollowUps.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(start = 4.dp)
            ) {
                message.suggestedFollowUps.forEach { followUp ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        onClick = { onSelectFollowUp(followUp) },
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(
                            text = followUp,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}
