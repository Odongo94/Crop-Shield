package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.model.CropThreat
import com.example.model.LifecycleStage
import com.example.model.StageIconType
import com.example.model.VulnerabilityLevel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

enum class PestMetamorphosisPhase(
    val stepNumber: Int,
    val shortLabel: String,
    val biologicalTerm: String,
    val icon: ImageVector,
    val testTag: String
) {
    EGG(1, "Egg", "Oviposition", Icons.Default.Egg, "pest_stage_node_egg"),
    LARVA(2, "Larva", "Feeding Instars", Icons.Default.BugReport, "pest_stage_node_larva"),
    PUPA(3, "Pupa", "Chrysalis / Cocoon", Icons.Default.Park, "pest_stage_node_pupa"),
    ADULT(4, "Adult", "Imago & Dispersal", Icons.Default.FlightTakeoff, "pest_stage_node_adult")
}

data class PestWheelStageGroup(
    val phase: PestMetamorphosisPhase,
    val stages: List<LifecycleStage>
)

fun mapThreatToPestWheelGroups(threat: CropThreat): List<PestWheelStageGroup> {
    val all = threat.lifecycleStages
    val eggStages = all.filter { it.iconType == StageIconType.EGG }
        .ifEmpty {
            listOf(
                LifecycleStage(
                    stageNumber = 1,
                    title = "${threat.name} Egg Stage",
                    scientificSub = "Oviposition / Early Colony",
                    iconType = StageIconType.EGG,
                    duration = "2 – 5 days",
                    vulnerability = VulnerabilityLevel.CRITICAL,
                    description = "Eggs or early reproductive propagules deposited on sheltered leaf undersides, stems, or near plant whorls.",
                    symptomsToSpot = "Clustered eggs, scale-covered masses, or initial tiny colonies under young leaves.",
                    targetAction = "Inspect leaf undersides weekly, crush visible egg masses, and release egg parasitoids.",
                    recommendedActiveIngredients = threat.ipmPlan.chemicalControls.take(2).map { it.activeIngredient }
                )
            )
        }

    val larvaStages = all.filter { it.iconType == StageIconType.LARVA }
        .ifEmpty {
            listOf(
                LifecycleStage(
                    stageNumber = 2,
                    title = "${threat.name} Larva / Nymph Stage",
                    scientificSub = "Active Crop-Feeding Phase",
                    iconType = StageIconType.LARVA,
                    duration = "10 – 18 days",
                    vulnerability = VulnerabilityLevel.CRITICAL,
                    description = "Immature feeding stages chew foliage, mine leaves, or sap plant vascular juices, causing the primary economic crop loss.",
                    symptomsToSpot = threat.diagnosticKeys.firstOrNull() ?: "Leaf chewing, window-pane feeding, stippling, or frass.",
                    targetAction = "CRITICAL WINDOW: Target young 1st–2nd instars with selective biologicals (Bt/Neem) or rotated IRAC chemistries.",
                    recommendedActiveIngredients = threat.ipmPlan.chemicalControls.map { it.activeIngredient }
                )
            )
        }

    val pupaStages = all.filter { it.iconType == StageIconType.PUPA }
        .ifEmpty {
            listOf(
                LifecycleStage(
                    stageNumber = 3,
                    title = "Pupation / Resting Transformation",
                    scientificSub = "Soil Cocoon or Nymphal Molt",
                    iconType = StageIconType.PUPA,
                    duration = "5 – 10 days",
                    vulnerability = VulnerabilityLevel.LOW,
                    description = "Protected resting transformation in soil chambers, crop debris, or late nymphal molts prior to adult emergence (hemimetabolous pests bypass true pupation).",
                    symptomsToSpot = "Reduced active leaf feeding while next-generation adults prepare to emerge from soil or plant litter.",
                    targetAction = "Shallow inter-row tillage, solarization, and crop residue sanitation to disrupt resting stages.",
                    recommendedActiveIngredients = listOf("Soil Tillage & Solarization", "Metarhizium anisopliae")
                )
            )
        }

    val adultStages = all.filter { it.iconType == StageIconType.ADULT }
        .ifEmpty {
            listOf(
                LifecycleStage(
                    stageNumber = 4,
                    title = "${threat.name} Adult Stage",
                    scientificSub = "Dispersal, Mating & Egg Laying",
                    iconType = StageIconType.ADULT,
                    duration = "10 – 21 days",
                    vulnerability = VulnerabilityLevel.MODERATE,
                    description = "Winged or mobile adults disperse across fields, mate, and locate fresh host crops to lay the next egg cycle.",
                    symptomsToSpot = "Adult moths, flies, beetles, or winged vectors captured in pheromone/sticky traps at dusk or dawn.",
                    targetAction = "Deploy pheromone funnel traps or yellow/blue sticky traps for mass trapping and early outbreak warning.",
                    recommendedActiveIngredients = listOf("Pheromone Traps", "Yellow/Blue Sticky Traps")
                )
            )
        }

    return listOf(
        PestWheelStageGroup(PestMetamorphosisPhase.EGG, eggStages),
        PestWheelStageGroup(PestMetamorphosisPhase.LARVA, larvaStages),
        PestWheelStageGroup(PestMetamorphosisPhase.PUPA, pupaStages),
        PestWheelStageGroup(PestMetamorphosisPhase.ADULT, adultStages)
    )
}

@Composable
fun PestLifecycleWheelCard(
    threat: CropThreat,
    modifier: Modifier = Modifier,
    initialPhaseIndex: Int = 0,
    onActiveIngredientClick: ((String) -> Unit)? = null
) {
    val wheelGroups = remember(threat.id) { mapThreatToPestWheelGroups(threat) }
    var selectedPhaseIndex by remember(threat.id) {
        mutableIntStateOf(initialPhaseIndex.coerceIn(0, wheelGroups.lastIndex))
    }
    var selectedSubStageIndex by remember(threat.id, selectedPhaseIndex) {
        mutableIntStateOf(0)
    }

    val activeGroup = wheelGroups[selectedPhaseIndex]
    val activeSubStage = activeGroup.stages[selectedSubStageIndex.coerceIn(0, activeGroup.stages.lastIndex)]

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("pest_lifecycle_wheel_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${threat.name} Lifecycle",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = threat.scientificName,
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tap any stage around the wheel to inspect biology & IPM control",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Stage ${selectedPhaseIndex + 1} / 4",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // Interactive 4-Stage Metamorphosis Orbital Wheel
            PestMetamorphosisWheelDiagram(
                wheelGroups = wheelGroups,
                selectedPhaseIndex = selectedPhaseIndex,
                activeDuration = activeSubStage.duration,
                activeVulnerability = activeSubStage.vulnerability,
                onSelectPhase = { index -> selectedPhaseIndex = index }
            )

            // Quick Stage Tab Strip (ensures one-tap scanning in tight viewports too)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                wheelGroups.forEachIndexed { index, group ->
                    val isSelected = index == selectedPhaseIndex
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedPhaseIndex = index },
                        label = {
                            Text(
                                text = "${group.phase.stepNumber}. ${group.phase.shortLabel}",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = group.phase.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        modifier = Modifier.testTag("pest_stage_chip_${group.phase.name.lowercase()}")
                    )
                }
            }

            // Animated Tap-Through Detail Panel
            AnimatedContent(
                targetState = activeGroup to activeSubStage,
                transitionSpec = {
                    fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) togetherWith
                        fadeOut(animationSpec = spring(stiffness = Spring.StiffnessMediumLow))
                },
                label = "pest_wheel_stage_details"
            ) { (group, stage) ->
                PestStageLearningPanel(
                    phase = group.phase,
                    allSubStages = group.stages,
                    selectedSubStageIndex = selectedSubStageIndex.coerceIn(0, group.stages.lastIndex),
                    stage = stage,
                    onSelectSubStage = { selectedSubStageIndex = it },
                    onActiveIngredientClick = onActiveIngredientClick
                )
            }

            // Previous / Next Stage Navigation Footer
            val prevIndex = if (selectedPhaseIndex == 0) wheelGroups.lastIndex else selectedPhaseIndex - 1
            val nextIndex = (selectedPhaseIndex + 1) % wheelGroups.size
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { selectedPhaseIndex = prevIndex },
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("pest_stage_prev_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous lifecycle stage",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = wheelGroups[prevIndex].phase.shortLabel,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Button(
                    onClick = { selectedPhaseIndex = nextIndex },
                    modifier = Modifier
                        .weight(1f)
                        .minimumInteractiveComponentSize()
                        .testTag("pest_stage_next_btn"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (selectedPhaseIndex == wheelGroups.lastIndex) {
                            "Cycle: Egg"
                        } else {
                            "Next: ${wheelGroups[nextIndex].phase.shortLabel}"
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next lifecycle stage",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PestMetamorphosisWheelDiagram(
    wheelGroups: List<PestWheelStageGroup>,
    selectedPhaseIndex: Int,
    activeDuration: String,
    activeVulnerability: VulnerabilityLevel,
    onSelectPhase: (Int) -> Unit
) {
    val activePhase = wheelGroups[selectedPhaseIndex].phase
    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val outlineColor = MaterialTheme.colorScheme.outlineVariant
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val diagramSize = maxWidth.coerceIn(230.dp, 280.dp)
        val density = LocalDensity.current
        val nodeSize = 64.dp
        val orbitRadiusDp = (diagramSize - nodeSize) * 0.45f
        val centerHubSize = diagramSize * 0.44f

        Box(
            modifier = Modifier.size(diagramSize),
            contentAlignment = Alignment.Center
        ) {
            // Custom Canvas Orbital Ring & Clockwise Metamorphosis Arrows
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radiusPx = with(density) { orbitRadiusDp.toPx() }
                val arcRectTopLeft = Offset(center.x - radiusPx, center.y - radiusPx)
                val arcSize = Size(radiusPx * 2f, radiusPx * 2f)

                // Soft atmospheric outer glow behind the cycle
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryContainerColor.copy(alpha = 0.45f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radiusPx * 1.15f
                    ),
                    radius = radiusPx * 1.15f,
                    center = center
                )

                // Subtle dashed orbit guide
                drawCircle(
                    color = outlineColor.copy(alpha = 0.45f),
                    radius = radiusPx,
                    center = center,
                    style = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                )

                // 4 quadrant clockwise transition arcs:
                // Top (-90°) Egg -> Right (0°) Larva -> Bottom (90°) Pupa -> Left (180°) Adult -> Top (-90°)
                val arcStarts = listOf(-62f, 28f, 118f, 208f)
                val sweep = 34f

                arcStarts.forEachIndexed { idx, startAngle ->
                    val isActiveTransition = idx == selectedPhaseIndex || idx < selectedPhaseIndex
                    val arcColor = if (isActiveTransition) primaryColor else outlineColor
                    val strokePx = if (idx == selectedPhaseIndex) 4.dp.toPx() else 2.5.dp.toPx()

                    drawArc(
                        color = arcColor,
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = arcRectTopLeft,
                        size = arcSize,
                        style = Stroke(width = strokePx, cap = StrokeCap.Round)
                    )

                    // Draw directional arrowhead at arc end
                    val endAngleRad = Math.toRadians((startAngle + sweep).toDouble())
                    val tipX = center.x + radiusPx * cos(endAngleRad).toFloat()
                    val tipY = center.y + radiusPx * sin(endAngleRad).toFloat()
                    // Tangent direction clockwise is angle + 90°
                    val tangentRad = endAngleRad + (PI / 2.0)
                    val arrowLen = 8.dp.toPx()
                    val wingAngle = 0.55

                    val leftWing = Offset(
                        tipX - arrowLen * cos(tangentRad - wingAngle).toFloat(),
                        tipY - arrowLen * sin(tangentRad - wingAngle).toFloat()
                    )
                    val rightWing = Offset(
                        tipX - arrowLen * cos(tangentRad + wingAngle).toFloat(),
                        tipY - arrowLen * sin(tangentRad + wingAngle).toFloat()
                    )
                    val arrowPath = Path().apply {
                        moveTo(tipX, tipY)
                        lineTo(leftWing.x, leftWing.y)
                        lineTo(rightWing.x, rightWing.y)
                        close()
                    }
                    drawPath(path = arrowPath, color = arcColor)
                }
            }

            // Center Entomological Visual Hub
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                border = BorderStroke(2.dp, primaryColor.copy(alpha = 0.45f)),
                tonalElevation = 4.dp,
                modifier = Modifier.size(centerHubSize)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(centerHubSize * 0.48f)
                            .testTag("pest_stage_illustration_${activePhase.name.lowercase()}")
                    ) {
                        drawPestStageIllustration(
                            phase = activePhase,
                            primaryColor = primaryColor,
                            secondaryColor = secondaryColor,
                            tertiaryColor = tertiaryColor,
                            surfaceVariantColor = surfaceVariantColor,
                            onSurfaceColor = onSurfaceColor
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = activePhase.shortLabel.uppercase(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = activeDuration,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (activeVulnerability == VulnerabilityLevel.CRITICAL || activeVulnerability == VulnerabilityLevel.HIGH) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                text = "Spray / Bio Window",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
            }

            // 4 Tappable Stage Nodes positioned around the cycle (Top, Right, Bottom, Left)
            val phaseAnglesDeg = listOf(-90.0, 0.0, 90.0, 180.0)
            wheelGroups.forEachIndexed { index, group ->
                val angleRad = Math.toRadians(phaseAnglesDeg[index])
                val radiusPx = with(density) { orbitRadiusDp.toPx() }
                val offsetX = (radiusPx * cos(angleRad)).roundToInt()
                val offsetY = (radiusPx * sin(angleRad)).roundToInt()

                val isSelected = index == selectedPhaseIndex
                val isCritical = group.stages.any {
                    it.vulnerability == VulnerabilityLevel.CRITICAL || it.vulnerability == VulnerabilityLevel.HIGH
                }

                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.12f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "node_scale_${group.phase.name}"
                )
                val containerColor by animateColorAsState(
                    targetValue = when {
                        isSelected -> MaterialTheme.colorScheme.primary
                        index < selectedPhaseIndex -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    label = "node_bg_${group.phase.name}"
                )
                val contentColor by animateColorAsState(
                    targetValue = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        index < selectedPhaseIndex -> MaterialTheme.colorScheme.onPrimaryContainer
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    label = "node_fg_${group.phase.name}"
                )

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX, offsetY) }
                        .size(nodeSize)
                        .scale(scale)
                        .minimumInteractiveComponentSize()
                        .clip(CircleShape)
                        .clickable(
                            role = Role.Tab,
                            onClickLabel = "Inspect ${group.phase.shortLabel} lifecycle stage",
                            onClick = { onSelectPhase(index) }
                        )
                        .semantics {
                            selected = isSelected
                            contentDescription = "${group.phase.shortLabel} stage, step ${group.phase.stepNumber} of 4"
                        }
                        .testTag(group.phase.testTag),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = containerColor,
                        border = BorderStroke(
                            width = if (isSelected) 2.5.dp else 1.5.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outlineVariant
                        ),
                        shadowElevation = if (isSelected) 6.dp else 1.dp,
                        modifier = Modifier.size(58.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = group.phase.icon,
                                contentDescription = null,
                                tint = contentColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = group.phase.shortLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                color = contentColor,
                                maxLines = 1
                            )
                        }
                    }

                    if (isCritical) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(4.dp)
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.error),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "!",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawPestStageIllustration(
    phase: PestMetamorphosisPhase,
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
    surfaceVariantColor: Color,
    onSurfaceColor: Color
) {
    val w = size.width
    val h = size.height

    when (phase) {
        PestMetamorphosisPhase.EGG -> {
            // Leaf silhouette backdrop
            val leafPath = Path().apply {
                moveTo(w * 0.12f, h * 0.72f)
                quadraticTo(w * 0.45f, h * 0.35f, w * 0.88f, h * 0.58f)
                quadraticTo(w * 0.55f, h * 0.90f, w * 0.12f, h * 0.72f)
                close()
            }
            drawPath(leafPath, color = primaryColor.copy(alpha = 0.28f))
            // Clustered pearlescent egg domes
            val eggOffsets = listOf(
                Offset(w * 0.38f, h * 0.52f),
                Offset(w * 0.52f, h * 0.46f),
                Offset(w * 0.64f, h * 0.54f),
                Offset(w * 0.45f, h * 0.62f),
                Offset(w * 0.57f, h * 0.63f),
                Offset(w * 0.50f, h * 0.54f)
            )
            val r = w * 0.085f
            eggOffsets.forEach { pos ->
                drawCircle(color = secondaryColor.copy(alpha = 0.9f), radius = r, center = pos)
                drawCircle(
                    color = Color.White.copy(alpha = 0.65f),
                    radius = r * 0.32f,
                    center = Offset(pos.x - r * 0.25f, pos.y - r * 0.25f)
                )
            }
        }

        PestMetamorphosisPhase.LARVA -> {
            // Chewed leaf stem
            drawLine(
                color = primaryColor.copy(alpha = 0.5f),
                start = Offset(w * 0.1f, h * 0.76f),
                end = Offset(w * 0.9f, h * 0.72f),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
            // Segmented curved caterpillar body
            val segments = listOf(
                Offset(w * 0.22f, h * 0.64f) to w * 0.08f,
                Offset(w * 0.33f, h * 0.55f) to w * 0.095f,
                Offset(w * 0.46f, h * 0.48f) to w * 0.105f,
                Offset(w * 0.59f, h * 0.49f) to w * 0.105f,
                Offset(w * 0.71f, h * 0.55f) to w * 0.095f
            )
            segments.forEachIndexed { idx, (pos, rad) ->
                val segColor = if (idx % 2 == 0) primaryColor else primaryColor.copy(alpha = 0.78f)
                drawCircle(color = segColor, radius = rad, center = pos)
                // Prolegs
                drawLine(
                    color = onSurfaceColor.copy(alpha = 0.55f),
                    start = Offset(pos.x, pos.y + rad * 0.7f),
                    end = Offset(pos.x - w * 0.02f, h * 0.73f),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            // Head capsule
            val headPos = Offset(w * 0.80f, h * 0.60f)
            drawCircle(color = secondaryColor, radius = w * 0.095f, center = headPos)
            drawCircle(color = Color.White, radius = w * 0.022f, center = Offset(headPos.x + w * 0.03f, headPos.y - w * 0.02f))
        }

        PestMetamorphosisPhase.PUPA -> {
            // Earthen cocoon rim
            drawOval(
                color = surfaceVariantColor.copy(alpha = 0.8f),
                topLeft = Offset(w * 0.18f, h * 0.20f),
                size = Size(w * 0.64f, h * 0.64f),
                style = Stroke(width = 2.5.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
            )
            // Tapered chrysalis body
            drawOval(
                color = secondaryColor,
                topLeft = Offset(w * 0.34f, h * 0.22f),
                size = Size(w * 0.32f, h * 0.58f)
            )
            // Abdominal chrysalis rings
            listOf(0.42f, 0.52f, 0.62f, 0.70f).forEach { yFrac ->
                drawLine(
                    color = onSurfaceColor.copy(alpha = 0.35f),
                    start = Offset(w * 0.37f, h * yFrac),
                    end = Offset(w * 0.63f, h * yFrac),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        PestMetamorphosisPhase.ADULT -> {
            // Left & right forewings
            val leftWing = Path().apply {
                moveTo(w * 0.48f, h * 0.46f)
                cubicTo(w * 0.12f, h * 0.20f, w * 0.08f, h * 0.60f, w * 0.46f, h * 0.64f)
                close()
            }
            val rightWing = Path().apply {
                moveTo(w * 0.52f, h * 0.46f)
                cubicTo(w * 0.88f, h * 0.20f, w * 0.92f, h * 0.60f, w * 0.54f, h * 0.64f)
                close()
            }
            drawPath(leftWing, color = tertiaryColor.copy(alpha = 0.85f))
            drawPath(rightWing, color = tertiaryColor.copy(alpha = 0.85f))
            // Wing eye spots
            drawCircle(color = secondaryColor, radius = w * 0.045f, center = Offset(w * 0.29f, h * 0.47f))
            drawCircle(color = secondaryColor, radius = w * 0.045f, center = Offset(w * 0.71f, h * 0.47f))
            // Thorax & abdomen
            drawOval(
                color = onSurfaceColor.copy(alpha = 0.85f),
                topLeft = Offset(w * 0.45f, h * 0.36f),
                size = Size(w * 0.10f, h * 0.44f)
            )
            // Antennae
            drawLine(
                color = onSurfaceColor,
                start = Offset(w * 0.48f, h * 0.37f),
                end = Offset(w * 0.36f, h * 0.20f),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                color = onSurfaceColor,
                start = Offset(w * 0.52f, h * 0.37f),
                end = Offset(w * 0.64f, h * 0.20f),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
private fun PestStageLearningPanel(
    phase: PestMetamorphosisPhase,
    allSubStages: List<LifecycleStage>,
    selectedSubStageIndex: Int,
    stage: LifecycleStage,
    onSelectSubStage: (Int) -> Unit,
    onActiveIngredientClick: ((String) -> Unit)?
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pest_stage_detail_panel")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Badges Row: Duration & Vulnerability
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.HourglassTop,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${phase.shortLabel} • ${stage.duration}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                M3VulnerabilityBadge(stage.vulnerability)
            }

            // Optional Sub-stage selector (e.g. Early Instars 1-3 vs Late Instars 4-6)
            if (allSubStages.size > 1) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    allSubStages.forEachIndexed { idx, sub ->
                        FilterChip(
                            selected = idx == selectedSubStageIndex,
                            onClick = { onSelectSubStage(idx) },
                            label = { Text(sub.scientificSub, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = stage.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stage.scientificSub,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = stage.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Field Symptoms Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Field Scouting Clue",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "What to Spot in the Field",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Text(
                            text = stage.symptomsToSpot,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // IPM Action Card
            val actionContainerColor = when (stage.vulnerability) {
                VulnerabilityLevel.CRITICAL -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.65f)
                VulnerabilityLevel.HIGH -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                else -> MaterialTheme.colorScheme.surface
            }
            val actionAccentColor = when (stage.vulnerability) {
                VulnerabilityLevel.CRITICAL -> MaterialTheme.colorScheme.error
                VulnerabilityLevel.HIGH -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.secondary
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = actionContainerColor,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "IPM Action",
                        tint = actionAccentColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "IPM Action for ${phase.shortLabel} Stage",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = actionAccentColor
                        )
                        Text(
                            text = stage.targetAction,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Active Ingredients / Control Chips
            if (stage.recommendedActiveIngredients.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Recommended Controls for This Stage",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        stage.recommendedActiveIngredients.forEach { ai ->
                            AssistChip(
                                onClick = { onActiveIngredientClick?.invoke(ai) },
                                label = { Text(ai, style = MaterialTheme.typography.labelSmall) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Medication,
                                        contentDescription = null,
                                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun M3VulnerabilityBadge(level: VulnerabilityLevel) {
    val (bg, fg, label, icon) = when (level) {
        VulnerabilityLevel.CRITICAL -> TripleIcon(
            MaterialTheme.colorScheme.errorContainer,
            MaterialTheme.colorScheme.onErrorContainer,
            "Critical Window",
            Icons.Default.Warning
        )
        VulnerabilityLevel.HIGH -> TripleIcon(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.onPrimaryContainer,
            "High Vulnerability",
            Icons.Default.CheckCircle
        )
        VulnerabilityLevel.MODERATE -> TripleIcon(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.onSecondaryContainer,
            "Moderate Control",
            Icons.Default.Shield
        )
        VulnerabilityLevel.LOW -> TripleIcon(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            "Protected Stage",
            Icons.Default.Shield
        )
    }

    Surface(shape = RoundedCornerShape(8.dp), color = bg) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = fg,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = fg
            )
        }
    }
}

private data class TripleIcon<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
