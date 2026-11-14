package com.example.ui.screens.community

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.DeveloperInfo
import com.example.model.KenyaAgriculturalCounty
import com.example.model.cloud.AppFeedback
import com.example.model.cloud.CommunityMessage
import com.example.model.cloud.DirectMessage
import com.example.model.cloud.FeedbackFeatureSource
import com.example.ui.components.AuthSignUpCard
import com.example.ui.components.FarmerRegistrationInput
import com.example.ui.components.FeedbackImprovementAreas
import com.google.firebase.auth.FirebaseUser

private val communityTopics = listOf(
    "General",
    "Pest Outbreak",
    "Disease Help",
    "Soil & Fertilizer",
    "Livestock & Poultry",
    "Spray & PHI"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityAndSupportScreen(
    currentUser: FirebaseUser?,
    isSuperAdmin: Boolean,
    selectedCounty: KenyaAgriculturalCounty,
    communityMessages: List<CommunityMessage>,
    directMessages: List<DirectMessage>,
    feedbackList: List<AppFeedback>,
    onAuthCompleted: (FirebaseUser, FarmerRegistrationInput) -> Unit,
    onSendCommunityMessage: (topic: String, text: String) -> Unit,
    onTogglePinSolution: (messageId: String, pinned: Boolean) -> Unit,
    onDeleteCommunityMessage: (messageId: String) -> Unit,
    onSendDirectMessage: (text: String) -> Unit,
    onSubmitFeedback: (source: String, topic: String, wasHelpful: Boolean, area: String, suggestion: String) -> Unit,
    onOpenAdminConsole: () -> Unit = {},
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var activeTab by remember(initialTab) { mutableIntStateOf(initialTab) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("community_support_screen")
    ) {
        // Admin banner visible strictly to the Super Admin
        if (isSuperAdmin) {
            Surface(
                color = MaterialTheme.colorScheme.tertiaryContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = "Admin Verified",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Admin Mode Active (${DeveloperInfo.NAME})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                    OutlinedButton(
                        onClick = onOpenAdminConsole,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("btn_community_open_admin_console")
                    ) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Admin Map & DB")
                    }
                }
            }
        }

        PrimaryTabRow(
            selectedTabIndex = activeTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("community_tabs")
        ) {
            Tab(
                selected = activeTab == 0,
                onClick = { activeTab = 0 },
                icon = { Icon(Icons.Default.Forum, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Community", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold) },
                modifier = Modifier.testTag("tab_community_chat")
            )
            Tab(
                selected = activeTab == 1,
                onClick = { activeTab = 1 },
                icon = { Icon(Icons.Default.SupportAgent, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = {
                    Text(
                        text = if (isSuperAdmin) "Direct Inbox" else "Ask Support",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.testTag("tab_direct_admin")
            )
            Tab(
                selected = activeTab == 2,
                onClick = { activeTab = 2 },
                icon = { Icon(Icons.Default.Feedback, contentDescription = null, modifier = Modifier.size(18.dp)) },
                text = { Text("Beta Feedback", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold) },
                modifier = Modifier.testTag("tab_beta_feedback")
            )
        }

        when (activeTab) {
            0 -> CommunityChatTab(
                currentUser = currentUser,
                isSuperAdmin = isSuperAdmin,
                selectedCounty = selectedCounty,
                messages = communityMessages,
                onAuthCompleted = onAuthCompleted,
                onSendCommunityMessage = onSendCommunityMessage,
                onTogglePinSolution = onTogglePinSolution,
                onDeleteCommunityMessage = onDeleteCommunityMessage
            )

            1 -> DirectAdminChatTab(
                currentUser = currentUser,
                isSuperAdmin = isSuperAdmin,
                selectedCounty = selectedCounty,
                messages = directMessages,
                onAuthCompleted = onAuthCompleted,
                onSendDirectMessage = onSendDirectMessage,
                onOpenAdminConsole = onOpenAdminConsole
            )

            else -> BetaFeedbackTab(
                currentUser = currentUser,
                feedbackList = feedbackList,
                onSubmitFeedback = onSubmitFeedback
            )
        }
    }
}

@Composable
private fun CommunityChatTab(
    currentUser: FirebaseUser?,
    isSuperAdmin: Boolean,
    selectedCounty: KenyaAgriculturalCounty,
    messages: List<CommunityMessage>,
    onAuthCompleted: (FirebaseUser, FarmerRegistrationInput) -> Unit,
    onSendCommunityMessage: (topic: String, text: String) -> Unit,
    onTogglePinSolution: (messageId: String, pinned: Boolean) -> Unit,
    onDeleteCommunityMessage: (messageId: String) -> Unit
) {
    if (currentUser == null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AuthSignUpCard(
                    selectedCounty = selectedCounty,
                    title = "Join Farmers & Agronomy Community",
                    subtitle = "Sign up or sign in with your Google email to solve crop & livestock problems with fellow farmers and Shamba AI. Your email stays strictly private.",
                    onAuthCompleted = onAuthCompleted
                )
            }
        }
        return
    }

    var selectedTopic by remember { mutableStateOf(communityTopics.first()) }
    var filterTopic by remember { mutableStateOf<String?>(null) }
    var draftText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val pinnedSolutions = remember(messages) { messages.filter { it.isSolutionPinned } }
    val displayedMessages = remember(messages, filterTopic) {
        if (filterTopic == null) messages else messages.filter { it.topic == filterTopic }
    }

    LaunchedEffect(displayedMessages.size) {
        if (displayedMessages.isNotEmpty()) {
            listState.animateScrollToItem(displayedMessages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // AI auto-response & fact-check notice
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Shamba AI auto-replies within 5 min if no farmer responds & fact-checks unsafe advice.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        // Topic filter strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = filterTopic == null,
                onClick = { filterTopic = null },
                label = { Text("All Topics (${messages.size})", style = MaterialTheme.typography.labelSmall) }
            )
            communityTopics.forEach { topic ->
                FilterChip(
                    selected = filterTopic == topic,
                    onClick = { filterTopic = if (filterTopic == topic) null else topic },
                    label = { Text(topic, style = MaterialTheme.typography.labelSmall) }
                )
            }
        }

        // Pinned Solutions Summary Banner
        if (pinnedSolutions.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp)
                    .testTag("pinned_solutions_banner")
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.PushPin, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Pinned Community Solution (${pinnedSolutions.size})",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    val latestPinned = pinnedSolutions.last()
                    Text(
                        text = "[${latestPinned.topic}] ${latestPinned.messageText}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            if (displayedMessages.isEmpty()) {
                item {
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Shamba Community Problem-Solving Board",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Ask about any pest outbreak, leaf spot, soil pH, or dairy/poultry problem. Fellow farmers, agronomy advisors, and Shamba AI (after 5 minutes if unanswered) help solve issues together. Farmer emails remain strictly hidden.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(displayedMessages, key = { it.id }) { msg ->
                    val isAiAuthor = msg.authorRole == "ai_advisor"
                    val isAdvisorAuthor = msg.authorRole == "super_admin"
                    val isMine = !isAiAuthor && msg.authorId == currentUser.uid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
                    ) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when {
                                    isAiAuthor -> MaterialTheme.colorScheme.secondaryContainer
                                    isAdvisorAuthor -> MaterialTheme.colorScheme.tertiaryContainer
                                    isMine -> MaterialTheme.colorScheme.primaryContainer
                                    else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
                                }
                            ),
                            border = if (msg.isSolutionPinned) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null,
                            modifier = Modifier
                                .widthIn(max = 320.dp)
                                .testTag("community_msg_${msg.id}")
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = if (isAdvisorAuthor && !isSuperAdmin) "Agronomy Advisor" else msg.authorName,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (isAiAuthor) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.secondary) {
                                                Text(
                                                    text = "SHAMBA AI",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onSecondary,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                                )
                                            }
                                        } else if (isAdvisorAuthor) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.tertiary) {
                                                Text(
                                                    text = "ADVISOR",
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = MaterialTheme.colorScheme.onTertiary,
                                                    fontWeight = FontWeight.Bold,
                                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                    }
                                    Row {
                                        if (isSuperAdmin) {
                                            IconButton(
                                                onClick = { onTogglePinSolution(msg.id, !msg.isSolutionPinned) },
                                                modifier = Modifier.minimumInteractiveComponentSize()
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.PushPin,
                                                    contentDescription = if (msg.isSolutionPinned) "Unpin Solution" else "Pin Solution",
                                                    tint = if (msg.isSolutionPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                        if (isMine || isSuperAdmin) {
                                            IconButton(
                                                onClick = { onDeleteCommunityMessage(msg.id) },
                                                modifier = Modifier.minimumInteractiveComponentSize()
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete message",
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)) {
                                        Text(
                                            text = msg.topic,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    if (msg.county.isNotBlank()) {
                                        Text(
                                            text = msg.county.substringBefore(" ("),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                Text(
                                    text = msg.messageText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(6.dp)) }
        }

        // Composer
        Surface(tonalElevation = 3.dp, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    communityTopics.forEach { topic ->
                        FilterChip(
                            selected = selectedTopic == topic,
                            onClick = { selectedTopic = topic },
                            label = { Text(topic, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = draftText,
                        onValueChange = { draftText = it },
                        placeholder = { Text("Share a farm question or solution...") },
                        maxLines = 3,
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_community_message")
                    )
                    IconButton(
                        onClick = {
                            if (draftText.isNotBlank()) {
                                onSendCommunityMessage(selectedTopic, draftText)
                                draftText = ""
                            }
                        },
                        enabled = draftText.isNotBlank(),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .clip(CircleShape)
                            .background(
                                if (draftText.isNotBlank()) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            )
                            .testTag("btn_send_community_message")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send Community Message",
                            tint = if (draftText.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectAdminChatTab(
    currentUser: FirebaseUser?,
    isSuperAdmin: Boolean,
    selectedCounty: KenyaAgriculturalCounty,
    messages: List<DirectMessage>,
    onAuthCompleted: (FirebaseUser, FarmerRegistrationInput) -> Unit,
    onSendDirectMessage: (String) -> Unit,
    onOpenAdminConsole: () -> Unit
) {
    if (currentUser == null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AuthSignUpCard(
                    selectedCounty = selectedCounty,
                    title = "Message CropShield Support Directly",
                    subtitle = "Sign in with your Google email to open a private 1-on-1 agronomy help thread.",
                    onAuthCompleted = onAuthCompleted
                )
            }
        }
        return
    }

    var draftText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Private Support Contact Header (Super Admin identity & email hidden from farmers)
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.55f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isSuperAdmin) "Direct Support Console" else "CropShield Agronomy Help Desk",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Private 1-on-1 Farm Support Channel",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (isSuperAdmin) {
                    OutlinedButton(onClick = onOpenAdminConsole) {
                        Text("All Farmer Inboxes")
                    }
                }
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(6.dp)) }
            if (messages.isEmpty()) {
                item {
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "This is your private 1-on-1 support channel with the CropShield advisory desk. Send any urgent farm issue, spray question, or feature request here.",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(messages, key = { it.id }) { msg ->
                    val isMine = msg.senderId == currentUser.uid
                    val isAdvisorMsg = msg.senderRole == "super_admin"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
                    ) {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isMine) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier
                                .widthIn(max = 310.dp)
                                .testTag("direct_msg_${msg.id}")
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text(
                                    text = if (isAdvisorMsg && !isSuperAdmin) "Agronomy Advisor" else msg.senderName,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isMine) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                    else MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = msg.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(6.dp)) }
        }

        Surface(tonalElevation = 3.dp, modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = draftText,
                    onValueChange = { draftText = it },
                    placeholder = { Text("Write directly to CropShield support...") },
                    maxLines = 3,
                    shape = RoundedCornerShape(22.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("input_direct_admin_message")
                )
                IconButton(
                    onClick = {
                        if (draftText.isNotBlank()) {
                            onSendDirectMessage(draftText)
                            draftText = ""
                        }
                    },
                    enabled = draftText.isNotBlank(),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .clip(CircleShape)
                        .background(
                            if (draftText.isNotBlank()) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                        .testTag("btn_send_direct_admin_message")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Direct Message",
                        tint = if (draftText.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun BetaFeedbackTab(
    currentUser: FirebaseUser?,
    feedbackList: List<AppFeedback>,
    onSubmitFeedback: (source: String, topic: String, wasHelpful: Boolean, area: String, suggestion: String) -> Unit
) {
    val context = LocalContext.current
    var wasHelpful by remember { mutableStateOf(true) }
    var selectedSource by remember { mutableStateOf(FeedbackFeatureSource.GENERAL_APP) }
    var selectedArea by remember { mutableStateOf(FeedbackImprovementAreas.areas.first()) }
    var topicAsked by remember { mutableStateOf("") }
    var improvementNote by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "CropShield is Under Active Development",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Whenever you ask a question or use a farm tool, let us know if it helped and where we should improve next.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                    )

                    Text("1. Was the help / answer useful?", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        FilterChip(
                            selected = wasHelpful,
                            onClick = { wasHelpful = true },
                            leadingIcon = { Icon(Icons.Default.ThumbUp, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text("Yes, Helpful") },
                            modifier = Modifier.weight(1f).testTag("beta_vote_helpful")
                        )
                        FilterChip(
                            selected = !wasHelpful,
                            onClick = { wasHelpful = false },
                            leadingIcon = { Icon(Icons.Default.ThumbDown, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text("Not Yet / Needs Work") },
                            modifier = Modifier.weight(1f).testTag("beta_vote_not_helpful")
                        )
                    }

                    Text("2. Which part of the app did you use?", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FeedbackFeatureSource.values().forEach { src ->
                            FilterChip(
                                selected = selectedSource == src,
                                onClick = { selectedSource = src },
                                label = { Text(src.label, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    Text("3. Where do you feel needs improvement?", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        (FeedbackImprovementAreas.areas + "Community & Support").forEach { area ->
                            FilterChip(
                                selected = selectedArea == area,
                                onClick = { selectedArea = area },
                                label = { Text(area, style = MaterialTheme.typography.labelSmall) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = topicAsked,
                        onValueChange = { topicAsked = it },
                        label = { Text("What did you ask or look up? (optional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("beta_feedback_topic_input")
                    )

                    OutlinedTextField(
                        value = improvementNote,
                        onValueChange = { improvementNote = it },
                        label = { Text("Tell us what to add, fix, or improve") },
                        placeholder = { Text("e.g. Add local county agrovet prices or more cassava diseases") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth().testTag("beta_feedback_suggestion_input")
                    )

                    Button(
                        onClick = {
                            val finalNote = improvementNote.ifBlank {
                                if (wasHelpful) "Found ${selectedSource.label} helpful"
                                else "Needs improvement in $selectedArea"
                            }
                            onSubmitFeedback(
                                selectedSource.code,
                                topicAsked.ifBlank { selectedSource.label },
                                wasHelpful,
                                selectedArea,
                                finalNote
                            )
                            topicAsked = ""
                            improvementNote = ""
                            Toast.makeText(
                                context,
                                if (currentUser != null) "Thank you! Feedback submitted." else "Feedback saved locally (sign in to sync).",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("beta_feedback_submit_btn")
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Submit Development Feedback", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Text(
                text = "Your Submitted Feedback (${feedbackList.size})",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        if (feedbackList.isEmpty()) {
            item {
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No feedback submitted yet. Every suggestion helps make CropShield more useful for farmers.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }
        } else {
            items(feedbackList, key = { "fb_${it.id}_${it.questionOrTopic.hashCode()}" }) { fb ->
                OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (fb.wasHelpful) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.errorContainer
                            ) {
                                Text(
                                    text = if (fb.wasHelpful) "👍 Helpful • ${fb.featureSource}" else "👎 Needs Work • ${fb.featureSource}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            Text(fb.improvementArea, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        }
                        if (fb.questionOrTopic.isNotBlank()) {
                            Text("Topic: ${fb.questionOrTopic}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
                        }
                        Text(fb.improvementSuggestion, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
