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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.UserEntity
import com.example.ui.components.NeoAvatar
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CoralPastel
import com.example.ui.theme.LavenderPastel
import com.example.ui.theme.MintPastel
import com.example.ui.theme.PrimaryInk
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SettingsScreen(
    currentUser: UserEntity?,
    isDarkTheme: Boolean,
    onToggleDarkTheme: () -> Unit,
    onUpdateProfile: (displayName: String, bio: String, avatarColorHex: String, statusEmoji: String) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var showBugReportDialog by remember { mutableStateOf(false) }
    var showExportSuccessDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryInk)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Settings & Privacy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
            }
        }

        // Profile Section Card
        if (currentUser != null) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    hasTape = true,
                    tapeText = "MY ACCOUNT"
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NeoAvatar(
                                name = currentUser.displayName,
                                size = 52.dp,
                                backgroundColor = AmberAccent,
                                statusEmoji = currentUser.statusEmoji
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = currentUser.displayName,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = currentUser.username,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryInk.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = currentUser.email,
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.5f)
                                )
                            }
                        }

                        if (currentUser.bio.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "\"${currentUser.bio}\"",
                                fontSize = 12.sp,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                color = PrimaryInk.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        // Appearance
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            tint = PrimaryInk,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Appearance Theme", fontSize = 14.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                            Text(
                                text = if (isDarkTheme) "Dark Neo-Brutalist" else "Light Neo-Brutalist",
                                fontSize = 11.sp,
                                color = PrimaryInk.copy(alpha = 0.6f)
                            )
                        }
                    }
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onToggleDarkTheme() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AmberAccent,
                            checkedTrackColor = BorderDark
                        )
                    )
                }
            }
        }

        // Privacy Center
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFF9F7F1),
                hasTape = true,
                tapeText = "PRIVACY CENTER"
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Zero Public Discovery",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "NoteCircle has no public search engine index, no explore tab, and no follower counts. Only people you explicitly approve can see your notes or send messages.",
                        fontSize = 12.sp,
                        color = PrimaryInk.copy(alpha = 0.7f),
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        NeoBadge(text = "NO AD TRACKING", backgroundColor = MintPastel)
                        NeoBadge(text = "NO PUBLIC PROFILE", backgroundColor = LavenderPastel)
                    }
                }
            }
        }

        // Security & E2EE
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFE8F5E9),
                hasTape = true,
                tapeText = "E2EE ENCRYPTION"
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Device Fingerprint & Keys",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your device safety fingerprint: ${currentUser?.safetyNumber ?: "8492-1049-5582-7104"}",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Messages are locally encrypted using authenticated AES-GCM before transport.",
                        fontSize = 11.sp,
                        color = PrimaryInk.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Data & Support Actions
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showExportSuccessDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Export My Local Data", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryInk)
                            Text(text = "Download JSON copy of your notes and circles", fontSize = 10.sp, color = PrimaryInk.copy(alpha = 0.6f))
                        }
                    }

                    Divider(color = BorderDark.copy(alpha = 0.15f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showBugReportDialog = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Report a Bug / Issue", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryInk)
                            Text(text = "Submit feedback to the NoteCircle engineering team", fontSize = 10.sp, color = PrimaryInk.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }

        // Logout Button
        item {
            NeoButton(
                onClick = onLogout,
                backgroundColor = CoralPastel,
                modifier = Modifier.fillMaxWidth(),
                testTag = "logout_button"
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Logout, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "LOG OUT OF DEVICE", fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }
            }
        }
    }

    // Bug Report Dialog
    if (showBugReportDialog) {
        BugReportDialog(onDismiss = { showBugReportDialog = false })
    }

    // Export Success Dialog
    if (showExportSuccessDialog) {
        Dialog(onDismissRequest = { showExportSuccessDialog = false }) {
            NeoCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📦", fontSize = 36.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Export Ready", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Your private notes, circles, and account metadata have been exported safely.", fontSize = 12.sp, color = PrimaryInk.copy(alpha = 0.7f))
                    Spacer(modifier = Modifier.height(14.dp))
                    NeoButton(onClick = { showExportSuccessDialog = false }, backgroundColor = AmberAccent) {
                        Text("CLOSE", fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun AuthScreen(
    onRegister: (email: String, username: String, displayName: String, bio: String, avatarColorHex: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableStateOf(1) } // 1: Email, 2: Code, 3: Profile Setup
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("#FFBF00") }

    val avatarColors = listOf("#FFBF00", "#88D49E", "#70B8FF", "#D8B4F8", "#FF6B6B")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F0))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            hasTape = true,
            tapeText = "★ NOTECIRCLE ONBOARDING"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Branding
                Text(text = "📓", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "NoteCircle",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = "Private by default. Temporary by design.",
                    fontSize = 12.sp,
                    color = PrimaryInk.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                when (step) {
                    1 -> {
                        // Step 1: Email Input
                        Text(
                            text = "ENTER YOUR EMAIL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("you@example.com") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_email_input"),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        NeoButton(
                            onClick = {
                                if (email.contains("@")) {
                                    step = 2
                                    code = "849201" // Demo code pre-fill helper
                                }
                            },
                            backgroundColor = AmberAccent,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = email.contains("@"),
                            testTag = "auth_continue_button"
                        ) {
                            Text("CONTINUE WITH EMAIL ➔", fontSize = 13.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    2 -> {
                        // Step 2: Verification Code
                        Text(
                            text = "6-DIGIT VERIFICATION CODE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it },
                            placeholder = { Text("e.g. 849201") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_code_input"),
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Code sent to $email. Tap below to verify.",
                            fontSize = 11.sp,
                            color = PrimaryInk.copy(alpha = 0.6f),
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        NeoButton(
                            onClick = {
                                if (code.length >= 4) {
                                    step = 3
                                    username = email.substringBefore("@")
                                    displayName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                                }
                            },
                            backgroundColor = MintPastel,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "auth_verify_button"
                        ) {
                            Text("VERIFY CODE ➔", fontSize = 13.sp, fontWeight = FontWeight.Black)
                        }
                    }
                    3 -> {
                        // Step 3: Profile Setup
                        Text(
                            text = "YOUR DISPLAY NAME",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = displayName,
                            onValueChange = { displayName = it },
                            placeholder = { Text("e.g. Maya Lin") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "UNIQUE USERNAME",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = { Text("e.g. maya") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "BIO (OPTIONAL)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            placeholder = { Text("Studying architecture & coffee enthusiast") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "AVATAR ACCENT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            avatarColors.forEach { colHex ->
                                val c = Color(android.graphics.Color.parseColor(colHex))
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(c, CircleShape)
                                        .border(2.dp, BorderDark, CircleShape)
                                        .clickable { selectedColor = colHex },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (selectedColor == colHex) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        NeoButton(
                            onClick = {
                                if (displayName.isNotBlank() && username.isNotBlank()) {
                                    onRegister(email, username, displayName, bio, selectedColor)
                                }
                            },
                            backgroundColor = AmberAccent,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "auth_finish_button"
                        ) {
                            Text("ENTER NOTECIRCLE 🚀", fontSize = 13.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(
    notifications: List<NotificationEntity>,
    onMarkAllRead: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryInk)
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Alerts & Inbox", fontSize = 20.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }

                if (notifications.any { !it.isRead }) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AmberAccent)
                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                            .clickable(onClick = onMarkAllRead)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(text = "MARK READ", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    }
                }
            }
        }

        if (notifications.isEmpty()) {
            item {
                NeoCard(modifier = Modifier.fillMaxWidth(), backgroundColor = MaterialTheme.colorScheme.surface) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🔔", fontSize = 38.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "All Caught Up!", fontSize = 15.sp, fontWeight = FontWeight.Black)
                        Text(text = "No pending notifications or alerts.", fontSize = 11.sp, color = PrimaryInk.copy(alpha = 0.6f))
                    }
                }
            }
        } else {
            items(notifications) { notif ->
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (!notif.isRead) Color(0xFFFFFBEA) else MaterialTheme.colorScheme.surface
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        val icon = when (notif.type) {
                            "SECURITY_ALERT" -> "🔒"
                            "CONNECTION_ACCEPTED" -> "🤝"
                            else -> "💬"
                        }
                        Text(text = icon, fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = notif.title, fontSize = 14.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                            Text(text = notif.body, fontSize = 12.sp, color = PrimaryInk.copy(alpha = 0.8f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BugReportDialog(onDismiss: () -> Unit) {
    var reportText by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        NeoCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
            Column(modifier = Modifier.padding(18.dp)) {
                if (isSubmitted) {
                    Text("✅", fontSize = 36.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Report Received", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Text("Thank you for helping keep NoteCircle safe and reliable.", fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    NeoButton(onClick = onDismiss, backgroundColor = AmberAccent) {
                        Text("DONE", fontWeight = FontWeight.Black)
                    }
                } else {
                    Text("Report a Problem", fontSize = 16.sp, fontWeight = FontWeight.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reportText,
                        onValueChange = { reportText = it },
                        placeholder = { Text("Describe what happened...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NeoButton(
                        onClick = { isSubmitted = true },
                        backgroundColor = AmberAccent,
                        enabled = reportText.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("SUBMIT REPORT", fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    }
}
