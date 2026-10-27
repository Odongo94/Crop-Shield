package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TableChart
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.FieldNoteEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Structured model representing an editable row/column entry in the agricultural field notebook.
 */
data class NotebookEntryData(
    val id: Long = 0,
    val dateCreated: String,
    val locationBlock: String,
    val cropVariety: String,
    val growthStage: String,
    val pestOrDisease: String,
    val severityLevel: String,
    val soilCondition: String,
    val actionTaken: String,
    val remarks: String
) {
    fun toEntity(): FieldNoteEntity {
        val formattedText = buildString {
            append("[Stage: $growthStage] ")
            append("[Severity: $severityLevel] ")
            append("[Soil: $soilCondition] ")
            if (actionTaken.isNotBlank()) append("[Action: $actionTaken] ")
            append("\n$remarks")
        }
        return FieldNoteEntity(
            id = id,
            cropName = cropVariety.ifBlank { "Crop" },
            pestOrDisease = pestOrDisease.ifBlank { "General Observation" },
            noteText = formattedText.trim(),
            locationTag = locationBlock.ifBlank { "Field" },
            dateCreated = dateCreated
        )
    }

    companion object {
        fun fromEntity(entity: FieldNoteEntity): NotebookEntryData {
            var stage = "Vegetative"
            var severity = "Moderate"
            var soil = "Moist / Good"
            var action = "Monitored"
            var remarks = entity.noteText

            val raw = entity.noteText
            if (raw.contains("[Stage:") || raw.contains("[Severity:")) {
                val stageMatch = Regex("\\[Stage:\\s*([^\\]]+)\\]").find(raw)
                val severityMatch = Regex("\\[Severity:\\s*([^\\]]+)\\]").find(raw)
                val soilMatch = Regex("\\[Soil:\\s*([^\\]]+)\\]").find(raw)
                val actionMatch = Regex("\\[Action:\\s*([^\\]]+)\\]").find(raw)

                if (stageMatch != null) stage = stageMatch.groupValues[1]
                if (severityMatch != null) severity = severityMatch.groupValues[1]
                if (soilMatch != null) soil = soilMatch.groupValues[1]
                if (actionMatch != null) action = actionMatch.groupValues[1]

                remarks = raw.replace(Regex("\\[(Stage|Severity|Soil|Action):[^\\]]+\\]"), "").trim()
            }

            return NotebookEntryData(
                id = entity.id,
                dateCreated = entity.dateCreated,
                locationBlock = entity.locationTag,
                cropVariety = entity.cropName,
                growthStage = stage,
                pestOrDisease = entity.pestOrDisease,
                severityLevel = severity,
                soilCondition = soil,
                actionTaken = action,
                remarks = remarks
            )
        }
    }
}

enum class NotebookViewMode {
    NOTEBOOK_PAGES,
    COLUMNS_LEDGER
}

@Composable
fun FieldNotesScreen(
    notes: List<FieldNoteEntity>,
    onSaveNote: (FieldNoteEntity) -> Unit,
    onDeleteNote: (FieldNoteEntity) -> Unit,
    prefilledCrop: String? = null,
    prefilledPest: String? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCropFilter by remember { mutableStateOf("All") }
    var viewMode by remember { mutableStateOf(NotebookViewMode.NOTEBOOK_PAGES) }

    // State for creating or editing an entry
    var activeEditingEntry by remember { mutableStateOf<NotebookEntryData?>(null) }
    var noteToDelete by remember { mutableStateOf<FieldNoteEntity?>(null) }

    // If prefilled data was passed, open editor immediately
    remember(prefilledCrop, prefilledPest) {
        if (prefilledCrop != null || prefilledPest != null) {
            val dateStr = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date())
            activeEditingEntry = NotebookEntryData(
                id = 0,
                dateCreated = dateStr,
                locationBlock = "Block A - Main Field",
                cropVariety = prefilledCrop ?: "Maize",
                growthStage = "Vegetative V6",
                pestOrDisease = prefilledPest ?: "Scouting Observation",
                severityLevel = "Mild (< 10%)",
                soilCondition = "Moist / Good",
                actionTaken = "Flagged for Treatment",
                remarks = "Recorded during field inspection."
            )
        }
        true
    }

    val cropsList = listOf("Maize", "Tomato", "Potato", "Beans", "French Beans", "Roses", "Cabbage", "Wheat", "Avocado", "Coffee")

    // Filter notes
    val filteredNotes = remember(notes, searchQuery, selectedCropFilter) {
        notes.filter { note ->
            val matchesCrop = selectedCropFilter == "All" || note.cropName.contains(selectedCropFilter, ignoreCase = true)
            val matchesSearch = searchQuery.isBlank() ||
                    note.noteText.contains(searchQuery, ignoreCase = true) ||
                    note.cropName.contains(searchQuery, ignoreCase = true) ||
                    note.pestOrDisease.contains(searchQuery, ignoreCase = true) ||
                    note.locationTag.contains(searchQuery, ignoreCase = true)
            matchesCrop && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("field_notes_screen")
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Farm Field Notebook Header Card
        OutlinedCard(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Field Agronomy Notebook",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${notes.size} column entries recorded in local notebook",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Add Column Entry Button
                    Button(
                        onClick = {
                            val dateStr = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date())
                            activeEditingEntry = NotebookEntryData(
                                id = 0,
                                dateCreated = dateStr,
                                locationBlock = "Block A",
                                cropVariety = "Maize",
                                growthStage = "Vegetative",
                                pestOrDisease = "Healthy Crop",
                                severityLevel = "Clean / Zero",
                                soilCondition = "Moist / Optimum",
                                actionTaken = "Routine Monitoring",
                                remarks = ""
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("btn_add_notebook_entry")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("New Entry", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                // Mode Selector & CSV Export Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        FilterChip(
                            selected = viewMode == NotebookViewMode.NOTEBOOK_PAGES,
                            onClick = { viewMode = NotebookViewMode.NOTEBOOK_PAGES },
                            label = { Text("Notebook Pages", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.size(14.dp))
                            }
                        )
                        FilterChip(
                            selected = viewMode == NotebookViewMode.COLUMNS_LEDGER,
                            onClick = { viewMode = NotebookViewMode.COLUMNS_LEDGER },
                            label = { Text("Columns Table", fontSize = 11.sp) },
                            leadingIcon = {
                                Icon(Icons.Default.TableChart, contentDescription = null, modifier = Modifier.size(14.dp))
                            }
                        )
                    }

                    // Export / Share Notebook
                    IconButton(
                        onClick = {
                            if (notes.isNotEmpty()) {
                                val csvBuilder = StringBuilder()
                                csvBuilder.append("Date,Block_Location,Crop_Variety,Growth_Stage,Pest_Disease,Severity,Soil_Condition,Action_Taken,Remarks\n")
                                notes.forEach { note ->
                                    val data = NotebookEntryData.fromEntity(note)
                                    csvBuilder.append("\"${data.dateCreated}\",\"${data.locationBlock}\",\"${data.cropVariety}\",\"${data.growthStage}\",\"${data.pestOrDisease}\",\"${data.severityLevel}\",\"${data.soilCondition}\",\"${data.actionTaken}\",\"${data.remarks.replace("\"", "\"\"")}\"\n")
                                }
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/csv"
                                    putExtra(Intent.EXTRA_SUBJECT, "CropShield Field Agronomy Notebook Export")
                                    putExtra(Intent.EXTRA_TEXT, csvBuilder.toString())
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share Field Notebook CSV"))
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Export Notebook to CSV",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search Bar for Notebook
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search any column (plot, crop, threat, remarks...)") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_field_notes")
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Crop Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val filterOptions = listOf("All") + cropsList
            filterOptions.forEach { crop ->
                val isSelected = selectedCropFilter == crop
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCropFilter = crop },
                    label = { Text(crop, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Main Notebook Content (Cards View vs. Multi-Column Ledger View)
        if (filteredNotes.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedCard(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Notebook is Empty",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap 'New Entry' above to add your first column-based field observation.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            when (viewMode) {
                NotebookViewMode.NOTEBOOK_PAGES -> {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .testTag("notebook_pages_list"),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        itemsIndexed(filteredNotes, key = { _, note -> note.id }) { index, note ->
                            val entryData = remember(note) { NotebookEntryData.fromEntity(note) }
                            NotebookPageCard(
                                pageNumber = index + 1,
                                entry = entryData,
                                onEditClick = { activeEditingEntry = entryData },
                                onDeleteClick = { noteToDelete = note }
                            )
                        }
                    }
                }

                NotebookViewMode.COLUMNS_LEDGER -> {
                    NotebookLedgerTableView(
                        notes = filteredNotes,
                        onRowClick = { note -> activeEditingEntry = NotebookEntryData.fromEntity(note) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

    // Editable Notebook Entry Dialog (Handles both Add and Edit)
    activeEditingEntry?.let { entry ->
        NotebookEntryEditorDialog(
            entry = entry,
            isNew = entry.id == 0L,
            onDismiss = { activeEditingEntry = null },
            onSave = { updatedEntry ->
                onSaveNote(updatedEntry.toEntity())
                activeEditingEntry = null
            }
        )
    }

    // Delete Confirmation Dialog
    noteToDelete?.let { note ->
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text("Delete Notebook Entry?") },
            text = { Text("Are you sure you want to remove this observation for ${note.cropName} (${note.locationTag}) from your notebook?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteNote(note)
                        noteToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * Authentic lined-paper styled notebook page card with column metadata and direct edit button.
 */
@Composable
private fun NotebookPageCard(
    pageNumber: Int,
    entry: NotebookEntryData,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("notebook_page_${entry.id}")
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Left notebook binder margin bar
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp)
            ) {
                // Header: Page number, Date & Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Page #$pageNumber",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = entry.dateCreated,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row {
                        // Direct Edit Button
                        IconButton(
                            onClick = onEditClick,
                            modifier = Modifier.size(28.dp).testTag("btn_edit_note_${entry.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit column entry",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        // Delete Button
                        IconButton(
                            onClick = onDeleteClick,
                            modifier = Modifier.size(28.dp).testTag("btn_delete_note_${entry.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete entry",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Columns Grid Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NotebookColumnBadge(label = "Plot", value = entry.locationBlock, color = MaterialTheme.colorScheme.secondaryContainer)
                    NotebookColumnBadge(label = "Crop", value = entry.cropVariety, color = MaterialTheme.colorScheme.primaryContainer)
                    NotebookColumnBadge(label = "Stage", value = entry.growthStage, color = MaterialTheme.colorScheme.tertiaryContainer)
                    NotebookColumnBadge(label = "Severity", value = entry.severityLevel, color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Threat / Symptom observation
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.BugReport,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = entry.pestOrDisease,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Action Taken
                if (entry.actionTaken.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Treatment/Action: ${entry.actionTaken}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Soil Condition
                if (entry.soilCondition.isNotBlank()) {
                    Text(
                        text = "Soil/Environment: ${entry.soilCondition}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(8.dp))

                // Notebook ruled text area
                Text(
                    text = entry.remarks.ifBlank { "No additional remarks." },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                // Quick tap-to-edit cue
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap ✏️ to edit any column at your convenience",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            }
        }
    }
}

/**
 * Multi-Column Ledger view matching a physical agricultural field record book.
 */
@Composable
private fun NotebookLedgerTableView(
    notes: List<FieldNoteEntity>,
    onRowClick: (FieldNoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(rememberScrollState())
        ) {
            // Table Header Row
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TableHeaderCell("#", width = 36.dp)
                    TableHeaderCell("Date", width = 120.dp)
                    TableHeaderCell("Plot / Block", width = 110.dp)
                    TableHeaderCell("Crop Variety", width = 110.dp)
                    TableHeaderCell("Growth Stage", width = 100.dp)
                    TableHeaderCell("Pest / Disease", width = 130.dp)
                    TableHeaderCell("Severity", width = 90.dp)
                    TableHeaderCell("Action Taken", width = 130.dp)
                    TableHeaderCell("Remarks", width = 180.dp)
                    TableHeaderCell("Action", width = 60.dp)
                }
            }

            HorizontalDivider()

            // Table Data Rows
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(notes) { index, note ->
                    val data = remember(note) { NotebookEntryData.fromEntity(note) }
                    val isEven = index % 2 == 0

                    Surface(
                        color = if (isEven) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onRowClick(note) }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TableCell("${index + 1}", width = 36.dp, isBold = true)
                            TableCell(data.dateCreated, width = 120.dp)
                            TableCell(data.locationBlock, width = 110.dp)
                            TableCell(data.cropVariety, width = 110.dp, isBold = true)
                            TableCell(data.growthStage, width = 100.dp)
                            TableCell(data.pestOrDisease, width = 130.dp, color = MaterialTheme.colorScheme.primary)
                            TableCell(data.severityLevel, width = 90.dp)
                            TableCell(data.actionTaken, width = 130.dp)
                            TableCell(data.remarks, width = 180.dp)
                            Box(modifier = Modifier.width(60.dp), contentAlignment = Alignment.Center) {
                                IconButton(onClick = { onRowClick(note) }, modifier = Modifier.size(24.dp)) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit row",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                }
            }
        }
    }
}

/**
 * Interactive Editor Dialog for creating or modifying all entry columns of a notebook observation.
 */
@Composable
fun NotebookEntryEditorDialog(
    entry: NotebookEntryData,
    isNew: Boolean,
    onDismiss: () -> Unit,
    onSave: (NotebookEntryData) -> Unit
) {
    var dateCreated by remember { mutableStateOf(entry.dateCreated) }
    var locationBlock by remember { mutableStateOf(entry.locationBlock) }
    var cropVariety by remember { mutableStateOf(entry.cropVariety) }
    var growthStage by remember { mutableStateOf(entry.growthStage) }
    var pestOrDisease by remember { mutableStateOf(entry.pestOrDisease) }
    var severityLevel by remember { mutableStateOf(entry.severityLevel) }
    var soilCondition by remember { mutableStateOf(entry.soilCondition) }
    var actionTaken by remember { mutableStateOf(entry.actionTaken) }
    var remarks by remember { mutableStateOf(entry.remarks) }

    val stagePresets = listOf("Germination", "Vegetative V4-V6", "Flowering / Tasseling", "Fruiting / Podding", "Maturity / Harvest")
    val severityPresets = listOf("Clean / Zero", "Trace (<5%)", "Mild (5-15%)", "Moderate (15-30%)", "Severe (>30%)")
    val commonCrops = listOf("Maize", "Tomato", "Potato", "Beans", "French Beans", "Roses", "Cabbage", "Wheat", "Avocado")

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = if (isNew) Icons.Default.Add else Icons.Default.EditNote,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        },
        title = {
            Text(
                text = if (isNew) "New Notebook Entry Column" else "Edit Notebook Entry Columns",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Edit any column values below. Changes are saved directly to your local database.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Date & Time Column
                OutlinedTextField(
                    value = dateCreated,
                    onValueChange = { dateCreated = it },
                    label = { Text("Date & Time Column") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            dateCreated = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date())
                        }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "Current Date", modifier = Modifier.size(16.dp))
                        }
                    }
                )

                // Location / Block Column
                OutlinedTextField(
                    value = locationBlock,
                    onValueChange = { locationBlock = it },
                    label = { Text("Field / Block / Plot Location Column") },
                    placeholder = { Text("e.g. Block 4 - Greenhouse 2") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Crop & Variety Column
                Column {
                    OutlinedTextField(
                        value = cropVariety,
                        onValueChange = { cropVariety = it },
                        label = { Text("Crop & Variety Column") },
                        placeholder = { Text("e.g. Maize (H614D) or Tomato (Anna F1)") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        commonCrops.forEach { c ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (cropVariety == c) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.clickable { cropVariety = c }
                            ) {
                                Text(
                                    text = c,
                                    fontSize = 10.sp,
                                    color = if (cropVariety == c) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                // Phenological Stage Column
                Column {
                    OutlinedTextField(
                        value = growthStage,
                        onValueChange = { growthStage = it },
                        label = { Text("Phenological Growth Stage Column") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        stagePresets.forEach { s ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (growthStage == s) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.clickable { growthStage = s }
                            ) {
                                Text(
                                    text = s,
                                    fontSize = 10.sp,
                                    color = if (growthStage == s) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                // Pest / Disease / Symptom Column
                OutlinedTextField(
                    value = pestOrDisease,
                    onValueChange = { pestOrDisease = it },
                    label = { Text("Observed Pest / Disease / Symptom Column") },
                    placeholder = { Text("e.g. Fall Armyworm or Healthy Crop") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Severity / Incidence Level Column
                Column {
                    OutlinedTextField(
                        value = severityLevel,
                        onValueChange = { severityLevel = it },
                        label = { Text("Severity / Infestation Level Column") },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        severityPresets.forEach { sev ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (severityLevel == sev) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.clickable { severityLevel = sev }
                            ) {
                                Text(
                                    text = sev,
                                    fontSize = 10.sp,
                                    color = if (severityLevel == sev) MaterialTheme.colorScheme.onError else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                // Soil & Environment Column
                OutlinedTextField(
                    value = soilCondition,
                    onValueChange = { soilCondition = it },
                    label = { Text("Soil & Moisture Condition Column") },
                    placeholder = { Text("e.g. Moist / Optimum, Waterlogged, or Dry") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Agronomic Action Taken Column
                OutlinedTextField(
                    value = actionTaken,
                    onValueChange = { actionTaken = it },
                    label = { Text("Action Taken / Treatment Column") },
                    placeholder = { Text("e.g. Sprayed bio-pesticide @ 15ml/20L") },
                    singleLine = true,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                // Detailed Remarks Column
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    label = { Text("Field Observations & Remarks Column") },
                    placeholder = { Text("Enter detailed observations, leaf symptom descriptions, or next scouting date...") },
                    minLines = 3,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        entry.copy(
                            dateCreated = dateCreated.ifBlank { "Date" },
                            locationBlock = locationBlock.ifBlank { "Plot" },
                            cropVariety = cropVariety.ifBlank { "Crop" },
                            growthStage = growthStage.ifBlank { "Vegetative" },
                            pestOrDisease = pestOrDisease.ifBlank { "Observation" },
                            severityLevel = severityLevel.ifBlank { "Clean" },
                            soilCondition = soilCondition.ifBlank { "Normal" },
                            actionTaken = actionTaken.trim(),
                            remarks = remarks.trim()
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isNew) "Save Entry" else "Update Entry")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun NotebookColumnBadge(label: String, value: String, color: Color) {
    Surface(
        color = color,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "$label:", fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Normal)
        }
    }
}

@Composable
private fun TableHeaderCell(text: String, width: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun TableCell(
    text: String,
    width: androidx.compose.ui.unit.Dp,
    isBold: Boolean = false,
    color: Color = Color.Unspecified
) {
    Box(
        modifier = Modifier
            .width(width)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (color != Color.Unspecified) color else MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}
