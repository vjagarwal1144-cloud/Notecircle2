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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.local.entity.UserEntity
import com.example.data.model.DigitalCompanion
import com.example.data.model.LivingTheme
import com.example.data.model.MoodGarden
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PersonalizationStudioScreen(
    currentUser: UserEntity?,
    onSelectCompanion: (DigitalCompanion) -> Unit,
    onSelectTheme: (LivingTheme) -> Unit,
    onSelectMood: (MoodGarden) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var reducedMotion by remember { mutableStateOf(false) }
    var tactileHaptics by remember { mutableStateOf(true) }
    var roundedCornersChoice by remember { mutableStateOf("Cozy (22dp)") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
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
                        text = "Personalization Studio ✨",
                        fontSize = 20.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Cute companions, living themes & custom aesthetics",
                        fontSize = 12.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )
                }
            }
        }

        // Active Companion Banner
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = PastelPinkLight,
                hasTape = true,
                tapeText = "★ YOUR COMPANION",
                tapeColor = PastelButterYellow
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, BorderDark, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentUser?.companionEmoji ?: "🐱",
                            fontSize = 32.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentUser?.companionName ?: "Mochi",
                            fontSize = 18.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        val companion = DigitalCompanion.values().find { it.petName == currentUser?.companionName } ?: DigitalCompanion.MOCHI
                        Text(
                            text = companion.greeting,
                            fontSize = 12.sp,
                            color = PrimaryInk.copy(alpha = 0.8f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // FEATURE 2: Cute Digital Companions Selector
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "🐾 CHOOSE COMPANION",
                tapeColor = PastelPeachLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Cute Digital Companions",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "A gentle companion to sit quietly on your notebook",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        DigitalCompanion.values().forEach { comp ->
                            val isSelected = currentUser?.companionName == comp.petName
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) PastelButterYellow else Color(0xFFF9F7F3))
                                    .border(1.6.dp, BorderDark, RoundedCornerShape(14.dp))
                                    .clickable { onSelectCompanion(comp) }
                                    .padding(horizontal = 12.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(comp.emoji, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${comp.petName} the ${comp.species}",
                                        fontSize = 14.sp,
                                        fontFamily = OutfitFontFamily,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk
                                    )
                                    Text(
                                        text = comp.trait,
                                        fontSize = 11.sp,
                                        color = PrimaryInk.copy(alpha = 0.65f)
                                    )
                                }
                                if (isSelected) {
                                    NeoBadge(text = "ACTIVE", backgroundColor = PastelMint, textColor = PrimaryInk)
                                }
                            }
                        }
                    }
                }
            }
        }

        // FEATURE 3: Living Themes
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "🎨 LIVING THEMES",
                tapeColor = PastelLavenderLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Living Theme Palettes",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Coordinated pastel harmony across all screens",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LivingTheme.values().forEach { theme ->
                            val isSelected = currentUser?.activeTheme == theme.name
                            val chipBg = try {
                                Color(android.graphics.Color.parseColor(theme.primaryColorHex))
                            } catch (e: Exception) {
                                PastelPinkLight
                            }

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(chipBg)
                                    .border(
                                        if (isSelected) 2.dp else 1.2.dp,
                                        BorderDark,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable { onSelectTheme(theme) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(theme.emoji, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = theme.title,
                                    fontSize = 12.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = if (theme.isDark) Color.White else PrimaryInk
                                )
                                if (isSelected) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = if (theme.isDark) Color.White else PrimaryInk,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // FEATURE 1: Mood Garden Visualization
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = PastelMintLight,
                hasTape = true,
                tapeText = "🌿 MOOD GARDEN OVERVIEW",
                tapeColor = PastelPinkLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Your Private Mood Garden",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Plants grow silently with each mood you log. No streak pressure.",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MoodGarden.values().forEach { mood ->
                            val isSelected = currentUser?.moodStatus == mood.name
                            Column(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                                    .clickable { onSelectMood(mood) }
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(mood.emoji, fontSize = 22.sp)
                                Text(
                                    text = mood.title,
                                    fontSize = 10.5.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryInk
                                )
                                if (isSelected) {
                                    Text(
                                        text = "★ PLANTED",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        color = AmberAccent
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // FEATURE 4 & 5: Animation & Studio Settings
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "⚙️ STUDIO CONTROLS",
                tapeColor = PastelButterYellow
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tactile Micro-Interactions",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Reduced Motion",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Disable spring bounces and floating micro-animations",
                                fontSize = 11.sp,
                                color = PrimaryInk.copy(alpha = 0.6f)
                            )
                        }
                        Switch(
                            checked = reducedMotion,
                            onCheckedChange = { reducedMotion = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PrimaryInk,
                                checkedTrackColor = PastelPinkLight
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tactile Haptic Feedback",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Vibrate softly on card taps and mood planting",
                                fontSize = 11.sp,
                                color = PrimaryInk.copy(alpha = 0.6f)
                            )
                        }
                        Switch(
                            checked = tactileHaptics,
                            onCheckedChange = { tactileHaptics = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PrimaryInk,
                                checkedTrackColor = PastelMint
                            )
                        )
                    }
                }
            }
        }
    }
}
