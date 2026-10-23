package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.UserEntity
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CoralPastel
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PlusJakartaFontFamily
import com.example.ui.theme.PrimaryInk
import com.example.ui.viewmodel.NoteSubTab
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesScreen(
    currentUser: UserEntity?,
    allNotes: List<NoteEntity>,
    selectedTab: NoteSubTab,
    onTabSelected: (NoteSubTab) -> Unit,
    onTogglePin: (noteId: String, currentPinned: Boolean) -> Unit,
    onDeleteNote: (noteId: String) -> Unit,
    onCreateNoteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val myNotes = allNotes.filter { it.authorId == currentUser?.userId }
    val displayNotes = when (selectedTab) {
        NoteSubTab.ACTIVE -> myNotes.filter { !it.isDraft && !it.isMemoryCapsule && (it.expiresAt == null || it.expiresAt > System.currentTimeMillis()) }
        NoteSubTab.PINNED -> myNotes.filter { it.isPinned }
        NoteSubTab.CAPSULES -> myNotes.filter { it.isMemoryCapsule }
        NoteSubTab.JOURNAL -> myNotes.filter { it.audienceType == "ONLY_ME" || it.noteType == "JOURNAL" }
        NoteSubTab.GRATITUDE -> myNotes.filter { it.categoryName == "Gratitude" || it.isRitual }
        NoteSubTab.DRAFTS -> myNotes.filter { it.isDraft }
        NoteSubTab.HISTORY -> myNotes.filter { it.expiresAt != null && it.expiresAt <= System.currentTimeMillis() }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Smart Note Templates Header
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFFFFBEA),
                hasTape = true,
                tapeText = "✨ SMART TEMPLATES",
                tapeColor = PastelButterYellow
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Creative Note Starters",
                        fontSize = 15.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        item {
                            TemplatePill("📚 Study Session", "Exam prep in library", PastelLavenderLight) { onCreateNoteClick() }
                        }
                        item {
                            TemplatePill("☕ Coffee Break", "Free for a quick cup?", PastelButterYellow) { onCreateNoteClick() }
                        }
                        item {
                            TemplatePill("🌸 Gratitude Note", "A lovely little moment", PastelPinkLight) { onCreateNoteClick() }
                        }
                        item {
                            TemplatePill("🌙 Late Night Vibe", "Night owl thoughts", PastelMint) { onCreateNoteClick() }
                        }
                    }
                }
            }
        }

        // Sub-Tabs Bar: ACTIVE, PINNED, CAPSULES, JOURNAL, GRATITUDE, DRAFTS, HISTORY
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(NoteSubTab.values()) { tab ->
                    val isSelected = tab == selectedTab
                    val count = when (tab) {
                        NoteSubTab.ACTIVE -> myNotes.count { !it.isDraft && !it.isMemoryCapsule && (it.expiresAt == null || it.expiresAt > System.currentTimeMillis()) }
                        NoteSubTab.PINNED -> myNotes.count { it.isPinned }
                        NoteSubTab.CAPSULES -> myNotes.count { it.isMemoryCapsule }
                        NoteSubTab.JOURNAL -> myNotes.count { it.audienceType == "ONLY_ME" || it.noteType == "JOURNAL" }
                        NoteSubTab.GRATITUDE -> myNotes.count { it.categoryName == "Gratitude" || it.isRitual }
                        NoteSubTab.DRAFTS -> myNotes.count { it.isDraft }
                        NoteSubTab.HISTORY -> myNotes.count { it.expiresAt != null && it.expiresAt <= System.currentTimeMillis() }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) AmberAccent else MaterialTheme.colorScheme.surface)
                            .border(1.6.dp, BorderDark, RoundedCornerShape(12.dp))
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${tab.name} ($count)",
                            fontSize = 11.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = PrimaryInk
                        )
                    }
                }
            }
        }

        // Notes List or Empty State
        if (displayNotes.isEmpty()) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    hasTape = true,
                    tapeText = "EMPTY SECTION",
                    tapeColor = PastelLavenderLight
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = if (selectedTab == NoteSubTab.CAPSULES) "🎁" else "📝", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No ${selectedTab.name.lowercase()} items",
                            fontSize = 16.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (selectedTab == NoteSubTab.CAPSULES) "Seal a meaningful moment or letter to be opened in the future."
                            else "Jot down a quick thought or plan for your trusted circle.",
                            fontSize = 12.sp,
                            color = PrimaryInk.copy(alpha = 0.7f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        NeoButton(onClick = onCreateNoteClick, backgroundColor = AmberAccent) {
                            Text(text = "WRITE A NOTE", fontSize = 12.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                }
            }
        } else {
            items(displayNotes, key = { it.noteId }) { note ->
                MyNoteItemCard(
                    note = note,
                    onTogglePin = { onTogglePin(note.noteId, note.isPinned) },
                    onDelete = { onDeleteNote(note.noteId) }
                )
            }
        }
    }
}

@Composable
private fun TemplatePill(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 7.dp)
    ) {
        Text(text = title, fontSize = 12.5.sp, fontFamily = OutfitFontFamily, fontWeight = FontWeight.Black, color = PrimaryInk)
        Text(text = subtitle, fontSize = 9.5.sp, color = PrimaryInk.copy(alpha = 0.65f))
    }
}

@Composable
private fun MyNoteItemCard(
    note: NoteEntity,
    onTogglePin: () -> Unit,
    onDelete: () -> Unit
) {
    val tapeText = when {
        note.isMemoryCapsule -> "🎁 MEMORY CAPSULE"
        note.isPinned -> "📌 PINNED NOTE"
        else -> "NOTE"
    }

    NeoCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("my_note_${note.noteId}"),
        backgroundColor = if (note.isPinned) Color(0xFFFFFBEA) else MaterialTheme.colorScheme.surface,
        hasTape = true,
        tapeText = tapeText,
        tapeColor = if (note.isMemoryCapsule) PastelLavender else PastelButterYellow
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NeoBadge(
                    text = "${note.categoryEmoji} ${note.categoryName}",
                    backgroundColor = PastelPeachLight
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onTogglePin,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PushPin,
                            contentDescription = "Toggle Pin",
                            tint = if (note.isPinned) AmberAccent else PrimaryInk.copy(alpha = 0.45f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = CoralPastel,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = note.content,
                fontSize = 14.5.sp,
                fontFamily = PlusJakartaFontFamily,
                lineHeight = 21.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryInk
            )

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = BorderDark.copy(alpha = 0.12f))
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val timeStr = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(note.createdAt))
                Text(
                    text = timeStr,
                    fontSize = 10.sp,
                    color = PrimaryInk.copy(alpha = 0.6f)
                )

                if (note.isMemoryCapsule) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(10.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "Unlocks in 7 days",
                            fontSize = 10.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }
                } else {
                    Text(
                        text = "Audience: ${note.audienceType.replace("_", " ")}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}
