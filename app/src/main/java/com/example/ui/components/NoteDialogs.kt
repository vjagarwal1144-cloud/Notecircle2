package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.CircleEntity
import com.example.data.model.AudienceType
import com.example.data.model.AvailabilityStatus
import com.example.data.model.MoodGarden
import com.example.data.model.NoteExpiry
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelMintSoft
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PastelSkyBlueLight
import com.example.ui.theme.PrimaryInk

/**
 * Presence Without Pressure Availability Dialog.
 */
@Composable
fun AvailabilityDialog(
    currentStatus: String,
    onStatusSelected: (AvailabilityStatus) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "★ PRESENCE",
            tapeColor = PastelMint
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Presence Without Pressure",
                            fontSize = 18.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Private to approved connections. No streaks.",
                            fontSize = 11.5.sp,
                            color = PrimaryInk.copy(alpha = 0.65f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    AvailabilityStatus.values().forEach { status ->
                        val isSelected = status.name == currentStatus
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) PastelButterYellow else Color(0xFFF9F7F3))
                                .border(1.6.dp, BorderDark, RoundedCornerShape(14.dp))
                                .clickable { onStatusSelected(status) }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = status.emoji,
                                fontSize = 22.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = status.title,
                                    fontSize = 14.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = status.description,
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                            if (isSelected) {
                                Text(
                                    text = "ACTIVE",
                                    fontSize = 9.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk,
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(6.dp))
                                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Mood Garden Dialog: Plant your daily emotional vibe in your private space.
 */
@Composable
fun MoodGardenDialog(
    currentMood: String,
    onMoodSelected: (MoodGarden) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "🌸 MOOD GARDEN",
            tapeColor = PastelPinkLight
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Plant Your Mood",
                            fontSize = 19.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Gently share how your heart feels today.",
                            fontSize = 11.5.sp,
                            color = PrimaryInk.copy(alpha = 0.65f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    MoodGarden.values().forEach { mood ->
                        val isSelected = mood.name == currentMood
                        val pastelColor = try {
                            Color(android.graphics.Color.parseColor(mood.colorHex))
                        } catch (e: Exception) {
                            PastelPinkLight
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isSelected) pastelColor else Color(0xFFF9F7F3))
                                .border(1.6.dp, BorderDark, RoundedCornerShape(14.dp))
                                .clickable { onMoodSelected(mood) }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = mood.emoji,
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = mood.title,
                                    fontSize = 14.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = mood.vibe,
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                            if (isSelected) {
                                Text(
                                    text = "PLANTED ✨",
                                    fontSize = 9.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk,
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(6.dp))
                                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Cute Create Note Sheet with Category Selector, Privacy Preview, and Memory Capsule toggle.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateNoteSheet(
    circles: List<CircleEntity>,
    onPostNote: (
        content: String,
        categoryEmoji: String,
        categoryName: String,
        audienceType: AudienceType,
        targetCircleId: String?,
        targetCircleName: String?,
        expiry: NoteExpiry,
        isPinned: Boolean,
        isMemoryCapsule: Boolean,
        unlockDate: Long?,
        tags: String
    ) -> Unit,
    onDismiss: () -> Unit
) {
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(CategoryOption("Thought", "💭")) }
    var selectedTag by remember { mutableStateOf("Personal") }
    var selectedAudience by remember { mutableStateOf(AudienceType.EVERYONE_IN_CIRCLE) }
    var selectedCircle by remember { mutableStateOf<CircleEntity?>(circles.firstOrNull()) }
    var selectedExpiry by remember { mutableStateOf(NoteExpiry.ONE_DAY) }
    var isPinned by remember { mutableStateOf(false) }
    var isMemoryCapsule by remember { mutableStateOf(false) }

    val categories = listOf(
        CategoryOption("Thought", "💭"),
        CategoryOption("Coffee", "☕"),
        CategoryOption("Study", "📚"),
        CategoryOption("Gratitude", "🌸"),
        CategoryOption("Vibe", "🎧"),
        CategoryOption("Night Owl", "🌙"),
        CategoryOption("Plan", "🚀"),
        CategoryOption("Comfort", "🧸")
    )

    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 680.dp),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = if (isMemoryCapsule) "🎁 TIME CAPSULE" else "★ WHISPER NOTE",
            tapeColor = if (isMemoryCapsule) PastelLavender else PastelButterYellow
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isMemoryCapsule) "Create Memory Capsule" else "New Whisper Note",
                            fontSize = 19.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Private by default. Temporary by design.",
                            fontSize = 11.sp,
                            color = PrimaryInk.copy(alpha = 0.6f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Input Field
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    placeholder = {
                        Text(
                            text = if (isMemoryCapsule) "Write a message for the future... (unlocks on a chosen date)"
                            else "What's on your heart? (e.g. studying calculus, free for coffee, listening to rainy lofi...)",
                            fontSize = 13.5.sp,
                            color = PrimaryInk.copy(alpha = 0.5f)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .testTag("create_note_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BorderDark,
                        unfocusedBorderColor = BorderDark.copy(alpha = 0.4f),
                        focusedContainerColor = Color(0xFFFAF7F3),
                        unfocusedContainerColor = Color(0xFFFAF7F3)
                    ),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Category Selector
                Text(
                    text = "VIBE & CATEGORY",
                    fontSize = 10.5.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = cat.name == selectedCategory.name
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) PastelPinkLight else Color(0xFFF2EEE4))
                                .border(1.4.dp, BorderDark, RoundedCornerShape(10.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 9.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(cat.emoji, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                cat.name,
                                fontSize = 11.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = PrimaryInk
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tag / Label Selector ('Personal', 'Work', 'Ideas', 'Study', 'Travel', 'Gratitude')
                Text(
                    text = "NOTE LABEL / TAG",
                    fontSize = 10.5.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val noteTags = listOf(
                        "Personal" to "🌸",
                        "Work" to "💼",
                        "Ideas" to "💡",
                        "Study" to "📚",
                        "Travel" to "✈️",
                        "Gratitude" to "✨"
                    )
                    noteTags.forEach { (tag, emoji) ->
                        val isChosen = selectedTag == tag
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isChosen) PastelMint else Color(0xFFF2EEE4))
                                .border(1.4.dp, BorderDark, RoundedCornerShape(10.dp))
                                .clickable { selectedTag = tag }
                                .padding(horizontal = 9.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(emoji, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                tag,
                                fontSize = 11.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (isChosen) FontWeight.Black else FontWeight.Bold,
                                color = PrimaryInk
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Memory Capsule Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(PastelLavenderLight)
                        .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🎁", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Seal as Memory Capsule",
                                fontSize = 13.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Locks this note until a future moment",
                                fontSize = 10.sp,
                                color = PrimaryInk.copy(alpha = 0.7f)
                            )
                        }
                    }
                    Switch(
                        checked = isMemoryCapsule,
                        onCheckedChange = { isMemoryCapsule = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AmberAccent,
                            checkedTrackColor = BorderDark
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Audience Selector
                Text(
                    text = "TARGET AUDIENCE",
                    fontSize = 10.5.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    AudienceType.values().forEach { aud ->
                        val isSelected = aud == selectedAudience
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) PastelMint else Color(0xFFF9F7F3))
                                .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                .clickable { selectedAudience = aud }
                                .padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(aud.icon, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = aud.label,
                                fontSize = 12.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                Text(
                                    text = "SELECTED",
                                    fontSize = 9.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                            }
                        }
                    }
                }

                // If Specific Circle selected, show circle options
                if (selectedAudience == AudienceType.SPECIFIC_CIRCLE && circles.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        circles.forEach { c ->
                            val isChosen = c.circleId == selectedCircle?.circleId
                            Text(
                                text = "${c.emoji} ${c.name}",
                                fontSize = 11.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (isChosen) FontWeight.Black else FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isChosen) PastelButterYellow else Color(0xFFF2EEE4))
                                    .border(1.2.dp, BorderDark, RoundedCornerShape(8.dp))
                                    .clickable { selectedCircle = c }
                                    .padding(horizontal = 9.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Expiration Picker (if not a memory capsule)
                if (!isMemoryCapsule) {
                    Text(
                        text = "EXPIRATION (TEMPORARY NOTE)",
                        fontSize = 10.5.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        NoteExpiry.values().forEach { exp ->
                            val isSelected = exp == selectedExpiry
                            Text(
                                text = exp.label,
                                fontSize = 11.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PastelSkyBlueLight else Color(0xFFF2EEE4))
                                    .border(1.2.dp, BorderDark, RoundedCornerShape(8.dp))
                                    .clickable { selectedExpiry = exp }
                                    .padding(horizontal = 9.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pin Note Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isPinned = !isPinned },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isPinned,
                        onCheckedChange = { isPinned = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = BorderDark,
                            checkmarkColor = AmberAccent
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = "Pin Note",
                        tint = PrimaryInk,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Pin note to top of notebook",
                        fontSize = 12.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk
                    )
                }

                // Privacy Preview Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PastelMintSoft, RoundedCornerShape(10.dp))
                        .border(1.2.dp, BorderDark.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Private",
                        tint = PrimaryInk,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "PRIVACY PREVIEW: VERIFIED",
                            fontSize = 9.5.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Zero public discoverability. Encrypted on-device storage.",
                            fontSize = 10.sp,
                            color = PrimaryInk.copy(alpha = 0.75f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Post Button
                NeoButton(
                    onClick = {
                        if (content.isNotBlank()) {
                            val unlockDate = if (isMemoryCapsule) System.currentTimeMillis() + 86400000L * 7 else null
                            onPostNote(
                                content,
                                selectedCategory.emoji,
                                selectedCategory.name,
                                selectedAudience,
                                if (selectedAudience == AudienceType.SPECIFIC_CIRCLE) selectedCircle?.circleId else null,
                                if (selectedAudience == AudienceType.SPECIFIC_CIRCLE) selectedCircle?.name else null,
                                selectedExpiry,
                                isPinned,
                                isMemoryCapsule,
                                unlockDate,
                                selectedTag
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (isMemoryCapsule) PastelLavender else AmberAccent,
                    testTag = "post_note_button",
                    enabled = content.isNotBlank()
                ) {
                    Text(
                        text = if (isMemoryCapsule) "SEAL MEMORY CAPSULE 🎁" else "POST TO CIRCLE 🚀",
                        fontSize = 13.5.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                }
            }
        }
    }
}

data class CategoryOption(val name: String, val emoji: String)

/**
 * FEATURE 8: Thought Bubble Dialog.
 * Quick temporary status message floating above user's avatar.
 */
@Composable
fun ThoughtBubbleDialog(
    initialText: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "💭 THOUGHT BUBBLE",
            tapeColor = PastelButterYellow
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Float a Thought Bubble",
                            fontSize = 18.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "What is gently on your mind right now?",
                            fontSize = 11.5.sp,
                            color = PrimaryInk.copy(alpha = 0.65f)
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = text,
                    onValueChange = { if (it.length <= 60) text = it },
                    placeholder = { Text("e.g. Listening to the rain 🌧️, Craving warm chai...", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "${text.length}/60 characters • Visible to trusted connections",
                    fontSize = 10.sp,
                    color = PrimaryInk.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    NeoButton(
                        text = "Clear",
                        onClick = {
                            onSave("")
                        },
                        backgroundColor = Color(0xFFF1ECE4)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    NeoButton(
                        text = "Float Bubble 🎈",
                        onClick = { onSave(text) },
                        backgroundColor = PastelButterYellow
                    )
                }
            }
        }
    }
}
