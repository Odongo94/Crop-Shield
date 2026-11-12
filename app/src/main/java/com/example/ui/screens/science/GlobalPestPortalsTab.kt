package com.example.ui.screens.science

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.science.GlobalPestPortalsCatalog
import com.example.model.science.GlobalPestIdentificationPortal
import com.example.model.science.PortalSearchResult
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Tab displaying and searching across the 11 Authoritative Global & Regional
 * Pest and Disease Identification Portals requested by the user.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GlobalPestPortalsTab(
    initialQuery: String = "",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var queryText by remember { mutableStateOf(initialQuery) }
    var selectedPortalId by remember { mutableStateOf<String?>("all") }

    val presetPestQueries = listOf(
        "Fall Armyworm (Spodoptera frugiperda)",
        "Tomato Late Blight (Phytophthora infestans)",
        "Tuta absoluta (Tomato Leafminer)",
        "Bacterial Wilt (Ralstonia solanacearum)",
        "Spider Mites (Tetranychus urticae)",
        "Melon Thrips (Thrips palmi)",
        "Watermelon Anthracnose",
        "Maize Stem Borer (Busseola fusca)",
        "False Codling Moth (Thaumatotibia leucotreta)",
        "Blossom End Rot (Calcium Deficiency)"
    )

    fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open browser: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun shareSearch(query: String) {
        val shareText = "Diagnostic Search across 11 Global Pest Portals for '$query':\n" +
            "• CABI: https://plantwiseplusknowledgebank.org/action/doSearch?AllField=${URLEncoder.encode(query, "UTF-8")}\n" +
            "• EPPO: https://gd.eppo.int/search?k=${URLEncoder.encode(query, "UTF-8")}\n" +
            "• iNaturalist: https://www.inaturalist.org/search?q=${URLEncoder.encode(query, "UTF-8")}"
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share Diagnostic Links"))
    }

    val portals = GlobalPestPortalsCatalog.allPortals
    val searchResults: List<PortalSearchResult> = if (queryText.isNotBlank()) {
        GlobalPestPortalsCatalog.searchAllPortals(queryText)
    } else {
        emptyList()
    }

    val displayedResults = if (selectedPortalId == "all" || selectedPortalId == null) {
        searchResults
    } else {
        searchResults.filter { it.portal.id == selectedPortalId }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "11 Global Diagnostic Portals",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Authoritative international databases & factsheets",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Cross-reference any crop pest, pathogen, weed, or disorder across 11 premier international research and quarantine institutions with one search.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Search Input Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Search All 11 Portals & Google:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = queryText,
                        onValueChange = { queryText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_global_pest_search"),
                        placeholder = { Text("Enter pest, disease, or scientific name...") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        },
                        trailingIcon = {
                            if (queryText.isNotBlank()) {
                                IconButton(
                                    onClick = { shareSearch(queryText) },
                                    modifier = Modifier.testTag("btn_share_global_search")
                                ) {
                                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            if (queryText.isNotBlank()) {
                                val encoded = URLEncoder.encode(queryText.trim(), StandardCharsets.UTF_8.toString())
                                openUrl("https://www.google.com/search?q=$encoded+pest+disease+identification")
                            }
                        }),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Preset Chips
                    Text(
                        text = "Quick Field Inquiries:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        presetPestQueries.take(6).forEach { preset ->
                            FilterChip(
                                selected = queryText.contains(preset.substringBefore(" (")),
                                onClick = { queryText = preset.substringBefore(" (") },
                                label = { Text(preset.substringBefore(" ("), fontSize = 11.sp) },
                                modifier = Modifier.testTag("chip_preset_${preset.take(8).lowercase().trim()}")
                            )
                        }
                    }

                    if (queryText.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val encoded = URLEncoder.encode(queryText.trim(), StandardCharsets.UTF_8.toString())
                                    openUrl("https://www.google.com/search?q=$encoded+pest+disease+identification")
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_google_search_pest"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Search on Google")
                            }

                            OutlinedButton(
                                onClick = {
                                    val encoded = URLEncoder.encode(queryText.trim(), StandardCharsets.UTF_8.toString())
                                    openUrl("https://scholar.google.com/scholar?q=$encoded+plant+pathology+control")
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_scholar_search_pest"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Google Scholar")
                            }
                        }
                    }
                }
            }
        }

        // Filter chips for portals
        item {
            Column {
                Text(
                    text = "Filter Portals:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FilterChip(
                        selected = selectedPortalId == "all",
                        onClick = { selectedPortalId = "all" },
                        label = { Text("All 11 Portals", fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    portals.forEach { p ->
                        FilterChip(
                            selected = selectedPortalId == p.id,
                            onClick = { selectedPortalId = p.id },
                            label = { Text("${p.iconEmoji} ${p.name.substringBefore(" ")}", fontSize = 12.sp) }
                        )
                    }
                }
            }
        }

        // If searching: display results
        if (queryText.isNotBlank()) {
            item {
                Text(
                    text = "Deep Diagnostic Links for '$queryText' (${displayedResults.size} portals):",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(displayedResults) { res ->
                OutlinedCard(
                    onClick = { openUrl(res.deepSearchUrl) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("portal_result_${res.portal.id}"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Text(res.portal.iconEmoji, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = res.portal.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = res.portal.organization,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            IconButton(onClick = { openUrl(res.deepSearchUrl) }) {
                                Icon(
                                    imageVector = Icons.Default.OpenInBrowser,
                                    contentDescription = "Open",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = res.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { openUrl(res.deepSearchUrl) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Open ${res.portal.name} Search", fontSize = 12.sp)
                        }
                    }
                }
            }
        } else {
            // When not searching: show the 11 portal directory
            item {
                Text(
                    text = "Directory of 11 Integrated Portals:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            val filteredPortals = if (selectedPortalId == "all" || selectedPortalId == null) {
                portals
            } else {
                portals.filter { it.id == selectedPortalId }
            }

            items(filteredPortals) { portal ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("portal_card_${portal.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(portal.iconEmoji, fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = portal.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = portal.organization,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = portal.coverageDescription,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Specialties
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            portal.keySpecialties.forEach { spec ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = spec,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { openUrl(portal.url) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Visit Official Site", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    queryText = "Tomato"
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Search Portal", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
