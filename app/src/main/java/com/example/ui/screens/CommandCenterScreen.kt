package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.UserEntity
import com.example.ui.components.NeoBadge
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommandCenterScreen(
    currentUser: UserEntity?,
    notes: List<NoteEntity>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val activeNotesCount = notes.filter { !it.isDraft && !it.isVaultProtected }.size
    val capsuleCount = notes.filter { it.isMemoryCapsule }.size
    val gratitudeCount = notes.filter { it.categoryName == "Gratitude" || it.isRitual }.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar
        item {
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
                Column {
                    Text(
                        text = "Personal Command Center 🎛️",
                        fontSize = 20.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Private insights, growth garden & essential info cards",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )
                }
            }
        }

        // FEATURE 30: Personal Growth Garden
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = PastelMintLight,
                hasTape = true,
                tapeText = "🌱 PERSONAL GROWTH GARDEN",
                tapeColor = PastelButterYellow
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Gentle Growth Space",
                        fontSize = 17.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Every gratitude note, focus session, and wholesome quest blooms a flower. No punishment for quiet days.",
                        fontSize = 12.sp,
                        color = PrimaryInk.copy(alpha = 0.7f),
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { idx ->
                            val isBloomed = idx < (currentUser?.growthGardenFlowers ?: 3)
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(if (isBloomed) PastelPinkLight else Color(0xFFF1ECE4))
                                        .border(1.6.dp, BorderDark, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (isBloomed) "🌸" else "🌱",
                                        fontSize = 22.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = if (isBloomed) "Bloomed" else "Growing",
                                    fontSize = 10.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryInk
                                )
                            }
                        }
                    }
                }
            }
        }

        // FEATURE 50: Personal Knowledge Insights
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = PastelLavenderLight,
                hasTape = true,
                tapeText = "📊 PRIVATE INSIGHTS",
                tapeColor = PastelPeachLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Personal Knowledge Insights",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Private to your eyes only. Never sent to advertising trackers.",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        InsightBox(
                            label = "Active Notes",
                            value = activeNotesCount.toString(),
                            emoji = "📝",
                            backgroundColor = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        InsightBox(
                            label = "Gratitude Moments",
                            value = gratitudeCount.toString(),
                            emoji = "🌸",
                            backgroundColor = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                        InsightBox(
                            label = "Memory Capsules",
                            value = capsuleCount.toString(),
                            emoji = "⏳",
                            backgroundColor = Color.White,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // FEATURE 51: Personal Information Cards
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "🪪 INFO CARDS",
                tapeColor = PastelButterYellow
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Personal Information Cards",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Reusable mini cards to share your preferences with close friends",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        InfoCardItem(
                            title = "Coffee / Tea Order ☕",
                            content = "Oat milk vanilla matcha latte, hot, less sweet.",
                            badge = "CLOSE FRIENDS"
                        )
                        InfoCardItem(
                            title = "Birthday Wishlist 🎂",
                            content = "Pastel stationery, scented candles, cozy warm socks.",
                            badge = "FAMILY CIRCLE"
                        )
                        InfoCardItem(
                            title = "Study Preferences 📚",
                            content = "Lofi beats, 25/5 Pomodoro, silent library corners.",
                            badge = "STUDY LOUNGE"
                        )
                    }
                }
            }
        }

        // FEATURE 54: Personal Reference Library
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "🔖 REFERENCE LIBRARY",
                tapeColor = PastelMintLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Personal Reference Library",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Bookmarked links, comforting quotes & saved ideas",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        ReferenceQuoteItem(
                            quote = "\"Almost everything will work again if you unplug it for a few minutes, including you.\"",
                            author = "Anne Lamott"
                        )
                        ReferenceQuoteItem(
                            quote = "\"Private by default. Temporary by design. Meaningful by choice.\"",
                            author = "NoteCircle Core Philosophy"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InsightBox(
    label: String,
    value: String,
    emoji: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundColor)
            .border(1.4.dp, BorderDark, RoundedCornerShape(14.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 18.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Black,
            color = PrimaryInk
        )
        Text(
            text = label,
            fontSize = 9.5.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Bold,
            color = PrimaryInk.copy(alpha = 0.6f),
            maxLines = 1
        )
    }
}

@Composable
private fun InfoCardItem(
    title: String,
    content: String,
    badge: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9F7F3))
            .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 13.5.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryInk
                )
                NeoBadge(text = badge, backgroundColor = PastelMint, textColor = PrimaryInk)
            }
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = content,
                fontSize = 12.sp,
                color = PrimaryInk.copy(alpha = 0.75f)
            )
        }
    }
}

@Composable
private fun ReferenceQuoteItem(
    quote: String,
    author: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF9F7F3))
            .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = quote,
            fontSize = 12.5.sp,
            lineHeight = 17.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryInk
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "— $author",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = AmberAccent
        )
    }
}
