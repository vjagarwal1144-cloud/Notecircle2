package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.UserEntity
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.OutfitFontFamily
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PastelSkyBlueLight
import com.example.ui.theme.PrimaryInk

@Composable
fun FocusRoomScreen(
    currentUser: UserEntity?,
    minutesRemaining: Int,
    isTimerActive: Boolean,
    activeSound: String,
    onToggleTimer: () -> Unit,
    onSetDuration: (Int) -> Unit,
    onSetSound: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val focusModes = listOf(
        "Rainy Cafe ☕",
        "Quiet Library 📚",
        "Starlit Night 🌙",
        "Zen Garden 🌿"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F3))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryInk)
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "Cozy Focus Space",
                    fontSize = 20.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = "Gentle pomodoro & quiet presence with your circle.",
                    fontSize = 11.5.sp,
                    color = PrimaryInk.copy(alpha = 0.65f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Large Circular Timer Display
        NeoCard(
            modifier = Modifier.size(230.dp),
            backgroundColor = PastelLavenderLight,
            shape = CircleShape,
            hasTape = true,
            tapeText = if (isTimerActive) "🔥 FOCUSING" else "PAUSED",
            tapeColor = if (isTimerActive) AmberAccent else PastelMint
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "$minutesRemaining:00",
                    fontSize = 44.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                NeoBadge(
                    text = activeSound,
                    backgroundColor = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Timer Controls
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            NeoButton(
                onClick = onToggleTimer,
                backgroundColor = if (isTimerActive) PastelPinkLight else AmberAccent
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (isTimerActive) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = PrimaryInk,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isTimerActive) "PAUSE" else "START FOCUS",
                        fontSize = 13.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                }
            }

            NeoButton(
                onClick = { onSetDuration(25) },
                backgroundColor = Color.White
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = PrimaryInk, modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Duration Options
        Text(
            text = "CHOOSE SESSION LENGTH",
            fontSize = 11.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Black,
            color = PrimaryInk,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(15, 25, 45, 60).forEach { mins ->
                val isSelected = minutesRemaining == mins
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) PastelButterYellow else Color.White)
                        .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                        .clickable { onSetDuration(mins) }
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "${mins}m",
                        fontSize = 12.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = PrimaryInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Ambient Sound Modes
        Text(
            text = "COZY AMBIENCE",
            fontSize = 11.sp,
            fontFamily = OutfitFontFamily,
            fontWeight = FontWeight.Black,
            color = PrimaryInk,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            focusModes.forEach { mode ->
                val isSelected = activeSound == mode
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) PastelMint else Color.White)
                        .border(1.4.dp, BorderDark, RoundedCornerShape(12.dp))
                        .clickable { onSetSound(mode) }
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = mode,
                        fontSize = 11.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = PrimaryInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quiet Presence Info Card
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = PastelSkyBlueLight,
            hasTape = true,
            tapeText = "QUIET PRESENCE",
            tapeColor = PastelButterYellow
        ) {
            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Shared Presence Without Distraction",
                        fontSize = 13.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Your circle sees you are in focus mode. Notifications are quietly muted so you stay in flow.",
                        fontSize = 11.sp,
                        color = PrimaryInk.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}
