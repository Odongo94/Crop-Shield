package com.example.ui.screens.compliance

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.compliance.AgriculturalAuditCatalog
import com.example.model.compliance.AuditAreaCategory
import com.example.model.compliance.AuditStandard
import com.example.model.compliance.ComplianceProtocolItem
import com.example.model.compliance.ComplianceSeverity

/**
 * Interactive Agricultural Audits & Compliance Protocols Hub
 * for Daily Field Compliance in Kenya (KS 1758, PCPB, OSHA) & Globally (GlobalG.A.P., Rainforest Alliance, Fairtrade, HACCP).
 */
@Composable
fun AgriculturalAuditComplianceView(
    modifier: Modifier = Modifier
) {
    var selectedStandard by remember { mutableStateOf<AuditStandard?>(null) }
    var selectedCategory by remember { mutableStateOf<AuditAreaCategory?>(null) }
    var checkedItemIds by remember { mutableStateOf(setOf<String>()) }
    var expandedItemIds by remember { mutableStateOf(setOf<String>()) }

    val allProtocols = AgriculturalAuditCatalog.auditProtocols

    val filteredProtocols by remember(selectedStandard, selectedCategory) {
        derivedStateOf {
            allProtocols.filter { item ->
                (selectedStandard == null || item.standard == selectedStandard) &&
                (selectedCategory == null || item.category == selectedCategory)
            }
        }
    }

    val auditScore by remember(checkedItemIds) {
        derivedStateOf {
            AgriculturalAuditCatalog.evaluateAuditCompliance(checkedItemIds, allProtocols)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("agricultural_audit_compliance_view"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card & Overview
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HealthAndSafety,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Agricultural Audits & Compliance Hub",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Standard Operating Procedures (SOPs), Daily Field Verification Protocols, and Mock Audit Checklists for GlobalG.A.P. IFA v6, KS 1758 Kenya, PCPB Statutory, and HACCP compliance.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        // Live Audit Scorecard Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (auditScore.passStatus) MaterialTheme.colorScheme.secondaryContainer
                else if (auditScore.criticalFailureCount > 0) MaterialTheme.colorScheme.errorContainer
                else MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().testTag("audit_scorecard_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Daily Compliance Self-Audit Score",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${auditScore.compliantCount} of ${auditScore.totalChecked} Protocols Verified",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (auditScore.passStatus) MaterialTheme.colorScheme.primary
                        else if (auditScore.criticalFailureCount > 0) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.tertiary
                    ) {
                        Text(
                            text = "${"%.0f".format(auditScore.compliancePercentage)}%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = auditScore.auditorSummary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { checkedItemIds = allProtocols.map { it.id }.toSet() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.weight(1f).testTag("button_check_all_protocols")
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Check All", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = { checkedItemIds = emptySet() },
                        modifier = Modifier.weight(1f).testTag("button_reset_protocols")
                    ) {
                        Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset All", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Section 1: Standard Filter Chips
        Column {
            Text(
                text = "Filter by Audit Standard",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStandard == null,
                    onClick = { selectedStandard = null },
                    label = { Text("All Standards (${allProtocols.size})", style = MaterialTheme.typography.labelSmall) }
                )
                AuditStandard.values().forEach { std ->
                    FilterChip(
                        selected = selectedStandard == std,
                        onClick = { selectedStandard = if (selectedStandard == std) null else std },
                        label = { Text(std.title, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        // Section 2: Category Filter Chips
        Column {
            Text(
                text = "Filter by Audit Area Category",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All Categories", style = MaterialTheme.typography.labelSmall) }
                )
                AuditAreaCategory.values().forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = if (selectedCategory == cat) null else cat },
                        label = { Text(cat.displayName, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }
        }

        // Section 3: List of Protocol Items
        Text(
            text = "Compliance Verification Protocols (${filteredProtocols.size})",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        filteredProtocols.forEach { protocol ->
            val isChecked = checkedItemIds.contains(protocol.id)
            val isExpanded = expandedItemIds.contains(protocol.id)

            OutlinedCard(
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    width = if (isChecked) 2.dp else 1.dp,
                    color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                ),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = if (isChecked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    else MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth().testTag("card_protocol_${protocol.id}")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Top header: checkbox + title + badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                checkedItemIds = if (checked) checkedItemIds + protocol.id else checkedItemIds - protocol.id
                            },
                            modifier = Modifier.testTag("checkbox_protocol_${protocol.id}")
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = if (protocol.severity == ComplianceSeverity.CRITICAL_MUST) MaterialTheme.colorScheme.errorContainer
                                    else MaterialTheme.colorScheme.secondaryContainer
                                ) {
                                    Text(
                                        text = protocol.severity.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (protocol.severity == ComplianceSeverity.CRITICAL_MUST) MaterialTheme.colorScheme.onErrorContainer
                                        else MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = protocol.clauseNumber,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = protocol.requirementTitle,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = protocol.standard.title,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = protocol.requirementDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Daily Field Protocol Box
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AssignmentTurnedIn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Daily Field Protocol (Scouts & Managers):",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = protocol.dailyFieldProtocol,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Expandable Details (Records, Non-compliances, Legal Basis)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedItemIds = if (isExpanded) expandedItemIds - protocol.id else expandedItemIds + protocol.id
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isExpanded) "Hide Audit Documents & Legal Basis" else "View Required Records & Audit Traps",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    AnimatedVisibility(visible = isExpanded) {
                        Column(
                            modifier = Modifier.padding(top = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HorizontalDivider()

                            Text(
                                text = "Required Records & Verification Documents:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            protocol.requiredRecordsAndDocuments.forEach { doc ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(doc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Text(
                                text = "Common Audit Non-Compliance Traps:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            protocol.commonAuditNonCompliances.forEach { trap ->
                                Row(verticalAlignment = Alignment.Top) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(14.dp).padding(top = 2.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(trap, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Gavel, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Statutory & Legal Citation:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }
                                    Text(protocol.kenyanAndGlobalLegalBasis, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
