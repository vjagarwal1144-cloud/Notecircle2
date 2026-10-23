package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.CircleEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.NoteReplyEntity
import com.example.data.local.entity.UserEntity
import com.example.data.model.MoodGarden
import com.example.ui.components.NeoAvatar
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BluePastel
import com.example.ui.theme.BorderDark
import com.example.ui.theme.GlassCardLight
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelButterYellowLight
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelMintLight
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PastelSkyBlueLight
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PrimaryInk
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StreamScreen(
    currentUser: UserEntity?,
    notes: List<NoteEntity>,
    circles: List<CircleEntity>,
    selectedFilter: String?,
    onFilterSelected: (String?) -> Unit,
    onOpenMoodGarden: () -> Unit,
    onSelectMood: (MoodGarden) -> Unit,
    onOpenThoughtBubble: () -> Unit,
    onOpenCommandCenter: () -> Unit,
    onOpenAiCompanion: () -> Unit,
    onOpenStudyWorkspace: () -> Unit,
    onOpenVault: () -> Unit,
    gratitudeInput: String,
    onGratitudeInputChanged: (String) -> Unit,
    onPostGratitude: () -> Unit,
    isRitualAnswered: Boolean,
    onReactToNote: (noteId: String, emoji: String) -> Unit,
    onOpenReplies: (NoteEntity) -> Unit,
    onCreateNoteClick: () -> Unit,
    selectedNoteForReplies: NoteEntity?,
    replies: List<NoteReplyEntity>,
    replyInput: String,
    onReplyInputChanged: (String) -> Unit,
    onAddReply: (noteId: String, text: String) -> Unit,
    onCloseReplies: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredNotes = notes.filter { note ->
        when (selectedFilter) {
            null -> true
            "CLOSE_FRIENDS" -> note.audienceType == "CLOSE_FRIENDS"
            else -> note.targetCircleId == selectedFilter || note.audienceType == "EVERYONE_IN_CIRCLE"
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Quick Feature Hubs: Command Center, Vault, Study, AI Companion
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(PastelPeachLight)
                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                .clickable { onOpenCommandCenter() }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🎛️", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Command Center", fontSize = 11.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(PastelMintLight)
                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                .clickable { onOpenVault() }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🔒", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Private Vault", fontSize = 11.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(PastelLavenderLight)
                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                .clickable { onOpenStudyWorkspace() }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("📚", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Study Lounge", fontSize = 11.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(PastelButterYellow)
                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                .clickable { onOpenAiCompanion() }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("✨", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("AI Companion", fontSize = 11.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                }
            }

            // FEATURE 8: Thought Bubble Banner
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.White,
                    hasTape = true,
                    tapeText = "💭 THOUGHT BUBBLE",
                    tapeColor = PastelButterYellow
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenThoughtBubble() }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(PastelPinkLight)
                                .border(1.4.dp, BorderDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(currentUser?.companionEmoji ?: "🐱", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (currentUser?.thoughtBubble.isNullOrBlank()) "Tap to float a thought bubble..." else "\"${currentUser?.thoughtBubble}\"",
                                fontSize = 13.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Temporary status bubble visible to approved connections",
                                fontSize = 10.5.sp,
                                color = PrimaryInk.copy(alpha = 0.6f)
                            )
                        }
                        Text("✏️", fontSize = 16.sp)
                    }
                }
            }

            // Mood Garden Header Bar
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = PastelPinkLight,
                    hasTape = true,
                    tapeText = "🌸 MOOD GARDEN",
                    tapeColor = PastelButterYellow
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Today's Circle Atmosphere",
                                    fontSize = 15.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Your mood: ${currentUser?.moodEmoji ?: "🌸"} ${currentUser?.moodStatus?.lowercase()?.replaceFirstChar { it.uppercase() } ?: "Blooming"}",
                                    fontSize = 11.5.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                                    .border(1.2.dp, BorderDark, RoundedCornerShape(10.dp))
                                    .clickable { onOpenMoodGarden() }
                                    .padding(horizontal = 10.dp, vertical = 4.5.dp)
                            ) {
                                Text(
                                    text = "CHANGE 🌿",
                                    fontSize = 10.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Quick Mood Garden Pills
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            items(MoodGarden.values()) { mood ->
                                val isCurrent = currentUser?.moodStatus == mood.name
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isCurrent) AmberAccent else Color.White)
                                        .border(1.2.dp, BorderDark, RoundedCornerShape(12.dp))
                                        .clickable { onSelectMood(mood) }
                                        .padding(horizontal = 9.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(mood.emoji, fontSize = 13.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = mood.title,
                                        fontSize = 10.5.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = if (isCurrent) FontWeight.Black else FontWeight.Bold,
                                        color = PrimaryInk
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Circle Ritual / Gratitude Card (If not answered today)
            if (!isRitualAnswered) {
                item {
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = PastelMintLight,
                        hasTape = true,
                        tapeText = "✨ CIRCLE RITUAL",
                        tapeColor = PastelLavenderLight
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Daily Circle Ritual: What made you smile today?",
                                fontSize = 14.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Share a tiny moment of gratitude with your people.",
                                fontSize = 11.sp,
                                color = PrimaryInk.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = gratitudeInput,
                                    onValueChange = onGratitudeInputChanged,
                                    placeholder = { Text("e.g. A warm matcha latte, cozy rain...", fontSize = 12.sp) },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(52.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White,
                                        focusedBorderColor = BorderDark,
                                        unfocusedBorderColor = BorderDark.copy(alpha = 0.4f)
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                NeoButton(
                                    onClick = onPostGratitude,
                                    backgroundColor = AmberAccent,
                                    enabled = gratitudeInput.isNotBlank()
                                ) {
                                    Text("SHARE 🌸", fontSize = 11.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black)
                                }
                            }
                        }
                    }
                }
            }

            // Filter Chips Bar
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    item {
                        FilterChip(
                            label = "All Circles",
                            icon = "🌟",
                            isSelected = selectedFilter == null,
                            accentColor = PastelButterYellow,
                            onClick = { onFilterSelected(null) }
                        )
                    }
                    item {
                        FilterChip(
                            label = "Close Friends",
                            icon = "⭐️",
                            isSelected = selectedFilter == "CLOSE_FRIENDS",
                            accentColor = PastelLavenderLight,
                            onClick = { onFilterSelected("CLOSE_FRIENDS") }
                        )
                    }
                    items(circles) { circle ->
                        FilterChip(
                            label = circle.name,
                            icon = circle.emoji,
                            isSelected = selectedFilter == circle.circleId,
                            accentColor = PastelMint,
                            onClick = { onFilterSelected(circle.circleId) }
                        )
                    }
                }
            }

            // Stream Notes or Empty State
            if (filteredNotes.isEmpty()) {
                item {
                    EmptyStreamCard(onCreateNoteClick = onCreateNoteClick)
                }
            } else {
                items(filteredNotes, key = { it.noteId }) { note ->
                    NoteCardItem(
                        note = note,
                        onReact = { emoji -> onReactToNote(note.noteId, emoji) },
                        onOpenReplies = { onOpenReplies(note) }
                    )
                }
            }
        }

        // Replies Dialog
        if (selectedNoteForReplies != null) {
            RepliesDialog(
                note = selectedNoteForReplies,
                replies = replies,
                replyInput = replyInput,
                onReplyInputChanged = onReplyInputChanged,
                onSendReply = { onAddReply(selectedNoteForReplies.noteId, replyInput) },
                onDismiss = onCloseReplies
            )
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    icon: String,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) accentColor else MaterialTheme.colorScheme.surface)
            .border(1.6.dp, BorderDark, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 13.dp, vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = icon, fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 11.5.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
            color = PrimaryInk
        )
    }
}

@Composable
private fun NoteCardItem(
    note: NoteEntity,
    onReact: (String) -> Unit,
    onOpenReplies: () -> Unit
) {
    val tapeText = when {
        note.isMemoryCapsule -> "🎁 MEMORY CAPSULE"
        note.isRitual -> "🌸 CIRCLE RITUAL"
        note.audienceType == "CLOSE_FRIENDS" -> "⭐️ CLOSE FRIENDS"
        note.audienceType == "SPECIFIC_CIRCLE" -> "🎯 ${note.targetCircleName ?: "CIRCLE"}"
        note.audienceType == "ONLY_ME" -> "🔒 ONLY ME"
        else -> "★ WHISPER NOTE"
    }

    val tapeColor = when {
        note.isMemoryCapsule -> PastelLavender
        note.isRitual -> PastelPinkLight
        note.audienceType == "CLOSE_FRIENDS" -> PastelButterYellowLight
        else -> PastelMint
    }

    NeoCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("note_card_${note.noteId}"),
        backgroundColor = if (note.isPinned) Color(0xFFFFFBEA) else MaterialTheme.colorScheme.surface,
        hasTape = true,
        tapeText = tapeText,
        tapeColor = tapeColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Author Avatar, Display Name, Category Badge, Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val avatarColor = try {
                    Color(android.graphics.Color.parseColor(note.authorAvatarColor))
                } catch (e: Exception) {
                    AmberAccent
                }

                NeoAvatar(
                    name = note.authorName,
                    backgroundColor = avatarColor,
                    size = 40.dp
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = note.authorName,
                            fontSize = 14.5.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        if (note.isPinned) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.PushPin,
                                contentDescription = "Pinned",
                                tint = PrimaryInk,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                    Text(
                        text = note.authorUsername,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                }

                // Category Badge
                NeoBadge(
                    text = "${note.categoryEmoji} ${note.categoryName}",
                    backgroundColor = PastelPeachLight
                )
            }

            Spacer(modifier = Modifier.height(11.dp))

            // Note Content
            Text(
                text = note.content,
                fontSize = 15.sp,
                fontFamily = PlusJakartaFontFamily,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryInk
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Expiry countdown / info chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val timeStr = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(note.createdAt))
                Text(
                    text = "Shared at $timeStr",
                    fontSize = 11.sp,
                    color = PrimaryInk.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Medium
                )

                val expiryLabel = when {
                    note.isMemoryCapsule -> "🎁 Unlocks in 7 days"
                    note.expiresAt == null -> "Permanent"
                    note.expiresAt > System.currentTimeMillis() -> {
                        val hoursLeft = ((note.expiresAt - System.currentTimeMillis()) / 3600000L).coerceAtLeast(1)
                        "Expires in ${hoursLeft}h"
                    }
                    else -> "Expired"
                }

                Text(
                    text = "⏳ $expiryLabel",
                    fontSize = 10.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    modifier = Modifier
                        .background(Color(0xFFEFECE6), RoundedCornerShape(6.dp))
                        .border(1.dp, BorderDark.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 7.dp, vertical = 2.5.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = BorderDark.copy(alpha = 0.12f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Actions row: Quick Reactions + Reply Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reactions
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("❤️", "✨", "☕", "🙌", "🌸").forEach { emoji ->
                        val count = if (note.reactions == emoji) 1 else 0
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (count > 0) PastelButterYellow else Color(0xFFF9F7F3))
                                .border(1.2.dp, BorderDark, RoundedCornerShape(8.dp))
                                .clickable { onReact(emoji) }
                                .padding(horizontal = 7.dp, vertical = 3.5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = emoji, fontSize = 12.5.sp)
                            if (count > 0) {
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = count.toString(),
                                    fontSize = 10.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                            }
                        }
                    }
                }

                // Replies button
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(PastelMint)
                        .border(1.2.dp, BorderDark, RoundedCornerShape(10.dp))
                        .clickable(onClick = onOpenReplies)
                        .padding(horizontal = 9.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Replies",
                        tint = PrimaryInk,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (note.replyCount > 0) "${note.replyCount} Replies" else "Reply",
                        fontSize = 10.5.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyStreamCard(
    onCreateNoteClick: () -> Unit
) {
    NeoCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        hasTape = true,
        tapeText = "QUIET STREAM",
        tapeColor = PastelMint
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🌿", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your Stream is Peaceful",
                fontSize = 18.sp,
                fontFamily = OutfitFontFamily,
                fontWeight = FontWeight.Black,
                color = PrimaryInk
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "No notes in your trusted circle yet. Plant your mood above or share a warm update with your people!",
                fontSize = 13.sp,
                color = PrimaryInk.copy(alpha = 0.7f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(18.dp))
            NeoButton(
                onClick = onCreateNoteClick,
                backgroundColor = AmberAccent,
                testTag = "empty_stream_create_button"
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "SHARE A WHISPER NOTE",
                        fontSize = 13.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                }
            }
        }
    }
}

@Composable
private fun RepliesDialog(
    note: NoteEntity,
    replies: List<NoteReplyEntity>,
    replyInput: String,
    onReplyInputChanged: (String) -> Unit,
    onSendReply: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(490.dp),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "REPLIES",
            tapeColor = PastelLavenderLight
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Replies to ${note.authorName}",
                            fontSize = 16.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Private to this circle note.",
                            fontSize = 11.sp,
                            color = PrimaryInk.copy(alpha = 0.6f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Divider(color = BorderDark.copy(alpha = 0.15f))

                // Replies List
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (replies.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 30.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No replies yet. Send warm thoughts!",
                                    fontSize = 12.sp,
                                    color = PrimaryInk.copy(alpha = 0.6f)
                                )
                            }
                        }
                    } else {
                        items(replies) { reply ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFFAF7F3), RoundedCornerShape(12.dp))
                                    .border(1.2.dp, BorderDark.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                    .padding(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                NeoAvatar(name = reply.authorName, size = 30.dp, backgroundColor = BluePastel)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = reply.authorName,
                                        fontSize = 12.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = reply.content,
                                        fontSize = 13.sp,
                                        fontFamily = PlusJakartaFontFamily,
                                        color = PrimaryInk
                                    )
                                }
                            }
                        }
                    }
                }

                // Reply Input Field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = replyInput,
                        onValueChange = onReplyInputChanged,
                        placeholder = { Text("Write a reply...", fontSize = 12.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reply_input_field"),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BorderDark,
                            unfocusedBorderColor = BorderDark.copy(alpha = 0.4f)
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    NeoButton(
                        onClick = onSendReply,
                        backgroundColor = AmberAccent,
                        enabled = replyInput.isNotBlank(),
                        testTag = "send_reply_button"
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send", tint = PrimaryInk, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}
