package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.CalendarEventEntity
import com.example.data.local.entity.CircleEntity
import com.example.data.local.entity.CircleRitualEntity
import com.example.data.local.entity.DecisionPollEntity
import com.example.data.local.entity.FriendshipQuestEntity
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelMintLight
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PastelSkyBlueLight
import com.example.ui.theme.PrimaryInk

enum class WorkspaceTab {
    RITUALS,
    DECISIONS,
    CALENDAR,
    QUESTS
}

@Composable
fun CircleWorkspaceScreen(
    circle: CircleEntity,
    rituals: List<CircleRitualEntity>,
    polls: List<DecisionPollEntity>,
    events: List<CalendarEventEntity>,
    quests: List<FriendshipQuestEntity>,
    onVotePoll: (pollId: String, optionIndex: Int) -> Unit,
    onCreatePoll: (circleId: String, question: String, op1: String, op2: String, op3: String) -> Unit,
    onCreateEvent: (circleId: String, title: String, date: String, time: String, location: String) -> Unit,
    onCompleteQuest: (questId: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedTab by remember { mutableStateOf(WorkspaceTab.DECISIONS) }
    var isAddingPoll by remember { mutableStateOf(false) }
    var newPollQuestion by remember { mutableStateOf("") }
    var newPollOp1 by remember { mutableStateOf("") }
    var newPollOp2 by remember { mutableStateOf("") }

    var isAddingEvent by remember { mutableStateOf(false) }
    var newEventTitle by remember { mutableStateOf("") }
    var newEventDate by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.6.dp, BorderDark, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryInk
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(circle.emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "${circle.name} Workspace",
                        fontSize = 18.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Private collaboration & decision space",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WorkspaceTab.values().forEach { tab ->
                val isSelected = selectedTab == tab
                val (label, icon) = when (tab) {
                    WorkspaceTab.DECISIONS -> "Polls" to Icons.Default.HowToVote
                    WorkspaceTab.CALENDAR -> "Calendar" to Icons.Default.CalendarMonth
                    WorkspaceTab.QUESTS -> "Quests" to Icons.Default.MilitaryTech
                    WorkspaceTab.RITUALS -> "Rituals" to Icons.Default.Spa
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) PastelButterYellow else MaterialTheme.colorScheme.surface)
                        .border(
                            if (isSelected) 1.8.dp else 1.2.dp,
                            BorderDark,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedTab = tab }
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = PrimaryInk,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = PrimaryInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedTab) {
                WorkspaceTab.DECISIONS -> {
                    // Feature 37: Decision Cards / Polls
                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = PastelPeachLight,
                            hasTape = true,
                            tapeText = "📊 GROUP DECISIONS",
                            tapeColor = PastelButterYellow
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Decision Cards",
                                        fontSize = 16.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = "Vote together on dinner, dates & plans without noise.",
                                        fontSize = 11.5.sp,
                                        color = PrimaryInk.copy(alpha = 0.7f)
                                    )
                                }
                                NeoButton(
                                    text = "+ Poll",
                                    onClick = { isAddingPoll = !isAddingPoll },
                                    backgroundColor = Color.White
                                )
                            }
                        }
                    }

                    if (isAddingPoll) {
                        item {
                            NeoCard(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                hasTape = true,
                                tapeText = "NEW POLL",
                                tapeColor = PastelLavenderLight
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    OutlinedTextField(
                                        value = newPollQuestion,
                                        onValueChange = { newPollQuestion = it },
                                        placeholder = { Text("What should we decide?", fontSize = 13.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = newPollOp1,
                                        onValueChange = { newPollOp1 = it },
                                        placeholder = { Text("Option 1", fontSize = 13.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = newPollOp2,
                                        onValueChange = { newPollOp2 = it },
                                        placeholder = { Text("Option 2", fontSize = 13.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        NeoButton(
                                            text = "Cancel",
                                            onClick = { isAddingPoll = false },
                                            backgroundColor = Color(0xFFF1ECE4)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        NeoButton(
                                            text = "Publish Poll ✨",
                                            onClick = {
                                                if (newPollQuestion.isNotBlank() && newPollOp1.isNotBlank() && newPollOp2.isNotBlank()) {
                                                    onCreatePoll(circle.circleId, newPollQuestion, newPollOp1, newPollOp2, "")
                                                    newPollQuestion = ""
                                                    newPollOp1 = ""
                                                    newPollOp2 = ""
                                                    isAddingPoll = false
                                                }
                                            },
                                            backgroundColor = PastelButterYellow
                                        )
                                    }
                                }
                            }
                        }
                    }

                    items(polls) { poll ->
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = "★ ACTIVE POLL",
                            tapeColor = PastelMintLight
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = poll.question,
                                    fontSize = 15.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Proposed by ${poll.authorName}",
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.5f)
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                                // Options
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Option 1
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (poll.selectedOption == 1) PastelButterYellow else Color(0xFFF9F7F3))
                                            .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                            .clickable { onVotePoll(poll.pollId, 1) }
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(poll.option1, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryInk)
                                        Text("${poll.votes1} votes", fontSize = 11.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                                    }

                                    // Option 2
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(if (poll.selectedOption == 2) PastelButterYellow else Color(0xFFF9F7F3))
                                            .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                            .clickable { onVotePoll(poll.pollId, 2) }
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(poll.option2, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryInk)
                                        Text("${poll.votes2} votes", fontSize = 11.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                                    }

                                    // Option 3 if exists
                                    if (poll.option3.isNotBlank()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(if (poll.selectedOption == 3) PastelButterYellow else Color(0xFFF9F7F3))
                                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                                .clickable { onVotePoll(poll.pollId, 3) }
                                                .padding(horizontal = 12.dp, vertical = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(poll.option3, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryInk)
                                            Text("${poll.votes3} votes", fontSize = 11.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                WorkspaceTab.CALENDAR -> {
                    // Feature 18: Shared Calendar
                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = PastelSkyBlueLight,
                            hasTape = true,
                            tapeText = "📅 SHARED CALENDAR",
                            tapeColor = PastelLavenderLight
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Circle Calendar",
                                        fontSize = 16.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = "Birthdays, meetups, trips and reminders",
                                        fontSize = 11.5.sp,
                                        color = PrimaryInk.copy(alpha = 0.7f)
                                    )
                                }
                                NeoButton(
                                    text = "+ Event",
                                    onClick = { isAddingEvent = !isAddingEvent },
                                    backgroundColor = Color.White
                                )
                            }
                        }
                    }

                    if (isAddingEvent) {
                        item {
                            NeoCard(
                                modifier = Modifier.fillMaxWidth(),
                                backgroundColor = MaterialTheme.colorScheme.surface,
                                hasTape = true,
                                tapeText = "NEW EVENT",
                                tapeColor = PastelMintLight
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    OutlinedTextField(
                                        value = newEventTitle,
                                        onValueChange = { newEventTitle = it },
                                        placeholder = { Text("Event Name (e.g. Picnic 🧺)", fontSize = 13.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = newEventDate,
                                        onValueChange = { newEventDate = it },
                                        placeholder = { Text("Date (e.g. Next Sunday 3pm)", fontSize = 13.sp) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        NeoButton(
                                            text = "Cancel",
                                            onClick = { isAddingEvent = false },
                                            backgroundColor = Color(0xFFF1ECE4)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        NeoButton(
                                            text = "Save Event 📅",
                                            onClick = {
                                                if (newEventTitle.isNotBlank()) {
                                                    onCreateEvent(circle.circleId, newEventTitle, newEventDate, "TBD", "")
                                                    newEventTitle = ""
                                                    newEventDate = ""
                                                    isAddingEvent = false
                                                }
                                            },
                                            backgroundColor = PastelButterYellow
                                        )
                                    }
                                }
                            }
                        }
                    }

                    items(events) { event ->
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = "★ EVENT",
                            tapeColor = PastelPeachLight
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(event.emoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = event.title,
                                        fontSize = 15.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = "${event.dateText} • ${event.timeText}",
                                        fontSize = 12.sp,
                                        color = PrimaryInk.copy(alpha = 0.65f)
                                    )
                                }
                            }
                        }
                    }
                }

                WorkspaceTab.QUESTS -> {
                    // Feature 16: Friendship Quests
                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = PastelLavenderLight,
                            hasTape = true,
                            tapeText = "⚔️ FRIENDSHIP QUESTS",
                            tapeColor = PastelMintLight
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Wholesome Quests",
                                    fontSize = 16.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Meaningful shared moments. No scores or public rankings.",
                                    fontSize = 11.5.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    items(quests) { quest ->
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = if (quest.isCompleted) Color(0xFFF7F5F0) else MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = if (quest.isCompleted) "✓ COMPLETED" else "ACTIVE QUEST",
                            tapeColor = if (quest.isCompleted) PastelMint else PastelPinkLight
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = quest.title,
                                        fontSize = 14.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = quest.description,
                                        fontSize = 11.5.sp,
                                        color = PrimaryInk.copy(alpha = 0.7f)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Reward: ${quest.rewardBadge}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AmberAccent
                                    )
                                }
                                if (!quest.isCompleted) {
                                    NeoButton(
                                        text = "Complete",
                                        onClick = { onCompleteQuest(quest.questId) },
                                        backgroundColor = PastelButterYellow
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completed",
                                        tint = PrimaryInk,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                WorkspaceTab.RITUALS -> {
                    // Feature 13: Circle Rituals
                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = PastelMintLight,
                            hasTape = true,
                            tapeText = "🍵 CIRCLE RITUALS",
                            tapeColor = PastelButterYellow
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Daily & Weekly Rituals",
                                    fontSize = 16.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Gentle recurring check-ins for close friends and family.",
                                    fontSize = 11.5.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }

                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = "TODAY'S PROMPT",
                            tapeColor = PastelPinkLight
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "🌸 \"What brought you peace or made you smile today?\"",
                                    fontSize = 14.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Answer in the stream's gratitude card to bloom your garden.",
                                    fontSize = 12.sp,
                                    color = PrimaryInk.copy(alpha = 0.65f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
