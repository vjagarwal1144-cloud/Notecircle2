package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RssFeed
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.RssFeed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.UserEntity
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CoralPastel
import com.example.ui.theme.GlassCardLight
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PrimaryInk
import com.example.ui.viewmodel.ScreenTab

@Composable
fun NoteCircleHeader(
    currentUser: UserEntity?,
    unreadNotifications: Int,
    onStatusClick: () -> Unit,
    onMoodClick: () -> Unit,
    onFocusRoomClick: () -> Unit,
    onStudioClick: () -> Unit,
    onCommandCenterClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Logo Badge with cute playful sticker look
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(PastelButterYellow)
                    .border(1.8.dp, BorderDark, RoundedCornerShape(14.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "📓",
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "NoteCircle",
                    fontSize = 16.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = (-0.3).sp
                )
            }

            // Right Actions: Companion Pill, Mood Garden Pill, Availability Chip, Focus Room, Command Center, Notifications, Settings
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Digital Companion Pill
                if (currentUser != null) {
                    Row(
                        modifier = Modifier
                            .testTag("companion_pill")
                            .clip(RoundedCornerShape(16.dp))
                            .background(PastelLavenderLight)
                            .border(1.6.dp, BorderDark, RoundedCornerShape(16.dp))
                            .clickable { onStudioClick() }
                            .padding(horizontal = 8.dp, vertical = 4.5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentUser.companionEmoji,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = currentUser.companionName,
                            fontSize = 10.5.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }

                    // Mood Garden Pill
                    Row(
                        modifier = Modifier
                            .testTag("mood_garden_pill")
                            .clip(RoundedCornerShape(16.dp))
                            .background(PastelPinkLight)
                            .border(1.6.dp, BorderDark, RoundedCornerShape(16.dp))
                            .clickable { onMoodClick() }
                            .padding(horizontal = 8.dp, vertical = 4.5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentUser.moodEmoji,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = currentUser.moodStatus.lowercase().replaceFirstChar { it.uppercase() },
                            fontSize = 10.5.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }

                    // Availability Pill
                    Row(
                        modifier = Modifier
                            .testTag("availability_pill")
                            .clip(RoundedCornerShape(16.dp))
                            .background(PastelMint)
                            .border(1.6.dp, BorderDark, RoundedCornerShape(16.dp))
                            .clickable { onStatusClick() }
                            .padding(horizontal = 8.dp, vertical = 4.5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currentUser.statusEmoji,
                            fontSize = 12.sp
                        )
                    }
                }

                // Cozy Focus Room Quick Launcher
                Box(
                    modifier = Modifier
                        .testTag("focus_room_button")
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(PastelPeachLight)
                        .border(1.6.dp, BorderDark, RoundedCornerShape(10.dp))
                        .clickable { onFocusRoomClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.HourglassTop,
                        contentDescription = "Focus Room",
                        tint = PrimaryInk,
                        modifier = Modifier.size(17.dp)
                    )
                }

                // Notification Bell with Badge
                Box {
                    Box(
                        modifier = Modifier
                            .testTag("notification_button")
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.6.dp, BorderDark, RoundedCornerShape(10.dp))
                            .clickable { onNotificationsClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = PrimaryInk,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    if (unreadNotifications > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 3.dp, y = (-3).dp)
                                .size(15.dp)
                                .background(CoralPastel, CircleShape)
                                .border(1.dp, BorderDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = unreadNotifications.toString(),
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                        }
                    }
                }

                // Settings Gear (Header only - never in bottom navigation)
                Box(
                    modifier = Modifier
                        .testTag("settings_button")
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .border(1.6.dp, BorderDark, RoundedCornerShape(10.dp))
                        .clickable { onSettingsClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = PrimaryInk,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

/**
 * Bottom navigation bar with EXACTLY 5 ITEMS:
 * STREAM, NOTES, +, CHAT, CIRCLE.
 * Settings must NOT appear here.
 */
@Composable
fun NoteCircleBottomBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        // Soft drop shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 2.5.dp, y = 2.5.dp)
                .background(BorderDark, RoundedCornerShape(22.dp))
        )

        // Frosted Glass Bottom bar container
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassCardLight, RoundedCornerShape(22.dp))
                .border(2.dp, BorderDark, RoundedCornerShape(22.dp))
                .padding(vertical = 5.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                label = "STREAM",
                selectedIcon = Icons.Filled.RssFeed,
                unselectedIcon = Icons.Outlined.RssFeed,
                isSelected = currentTab == ScreenTab.STREAM,
                accentColor = PastelButterYellow,
                onClick = { onTabSelected(ScreenTab.STREAM) },
                testTag = "nav_stream"
            )

            BottomNavItem(
                label = "NOTES",
                selectedIcon = Icons.Filled.MenuBook,
                unselectedIcon = Icons.Outlined.MenuBook,
                isSelected = currentTab == ScreenTab.NOTES,
                accentColor = PastelLavenderLight,
                onClick = { onTabSelected(ScreenTab.NOTES) },
                testTag = "nav_notes"
            )

            // Center Action '+' Button
            CenterActionButton(
                onClick = { onTabSelected(ScreenTab.CREATE) }
            )

            BottomNavItem(
                label = "CHAT",
                selectedIcon = Icons.Filled.Chat,
                unselectedIcon = Icons.Outlined.Chat,
                isSelected = currentTab == ScreenTab.CHAT,
                accentColor = PastelMint,
                onClick = { onTabSelected(ScreenTab.CHAT) },
                testTag = "nav_chat"
            )

            BottomNavItem(
                label = "CIRCLE",
                selectedIcon = Icons.Filled.Groups,
                unselectedIcon = Icons.Outlined.Groups,
                isSelected = currentTab == ScreenTab.CIRCLE,
                accentColor = PastelPinkLight,
                onClick = { onTabSelected(ScreenTab.CIRCLE) },
                testTag = "nav_circle"
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    label: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        modifier = Modifier
            .testTag(testTag)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 7.dp, vertical = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) accentColor else Color.Transparent)
                .then(if (isSelected) Modifier.border(1.4.dp, BorderDark, RoundedCornerShape(10.dp)) else Modifier)
                .padding(horizontal = 10.dp, vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = label,
                tint = PrimaryInk,
                modifier = Modifier.size(21.dp)
            )
        }
        Text(
            text = label,
            fontSize = 9.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
            color = if (isSelected) PrimaryInk else PrimaryInk.copy(alpha = 0.55f),
            letterSpacing = 0.4.sp
        )
    }
}

@Composable
private fun CenterActionButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .offset(y = (-8).dp)
            .padding(horizontal = 2.dp)
    ) {
        // Shadow for '+' button
        Box(
            modifier = Modifier
                .size(48.dp)
                .offset(x = 2.5.dp, y = 2.5.dp)
                .background(BorderDark, RoundedCornerShape(16.dp))
        )

        // '+' Button surface with pastel peach / amber accent
        Box(
            modifier = Modifier
                .testTag("nav_create_action")
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(AmberAccent)
                .border(2.dp, BorderDark, RoundedCornerShape(16.dp))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Create Note",
                tint = PrimaryInk,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
