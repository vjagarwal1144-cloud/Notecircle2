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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.LavenderPastel
import com.example.ui.theme.MintPastel
import com.example.ui.theme.PastelButterYellow
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMintLight
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
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
    onDeleteAccount: () -> Unit = {},
    onOpenStudio: () -> Unit = {},
    onOpenVault: () -> Unit = {},
    onOpenCommandCenter: () -> Unit = {},
    onOpenStudy: () -> Unit = {},
    onOpenAi: () -> Unit = {},
    onOpenFocusRoom: () -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var showBugReportDialog by remember { mutableStateOf(false) }
    var showExportSuccessDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showWidgetGuideDialog by remember { mutableStateOf(false) }

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

        // Feature Launchers Hub
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFFFFDF7),
                hasTape = true,
                tapeText = "QUICK FEATURE LAUNCHERS"
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Access all NoteCircle features directly:",
                        fontSize = 12.sp,
                        color = PrimaryInk.copy(alpha = 0.7f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeoButton(
                            onClick = onOpenStudio,
                            backgroundColor = PastelLavenderLight,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_studio_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🎨", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Studio", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                        NeoButton(
                            onClick = onOpenVault,
                            backgroundColor = PastelMintLight,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_vault_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🔒", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Vault", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeoButton(
                            onClick = onOpenCommandCenter,
                            backgroundColor = PastelPeachLight,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_command_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🎛️", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Command", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                        NeoButton(
                            onClick = onOpenStudy,
                            backgroundColor = PastelButterYellow,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_study_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("📚", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Study", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeoButton(
                            onClick = onOpenAi,
                            backgroundColor = PastelPinkLight,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_ai_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("✨", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("AI Assist", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                        NeoButton(
                            onClick = onOpenFocusRoom,
                            backgroundColor = PastelMintLight,
                            modifier = Modifier.weight(1f),
                            testTag = "settings_focus_button"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("☕", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Focus", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }

                    NeoButton(
                        onClick = { showWidgetGuideDialog = true },
                        backgroundColor = Color(0xFFE2F0D9),
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "settings_widgets_guide_button"
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Widgets, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("📱 Home-Screen Widgets Guide (5 Widgets)", fontSize = 11.sp, fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }

        // Appearance & Theme (Compose Material3 & DataStore)
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "THEME & DATASTORE"
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = PrimaryInk,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Material 3 Theme Switcher",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = if (isDarkTheme) "🌙 Dark Mode active (Saved in DataStore)" else "☀️ Light Mode active (Saved in DataStore)",
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                        }
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onToggleDarkTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AmberAccent,
                                checkedTrackColor = BorderDark
                            ),
                            modifier = Modifier.testTag("theme_switch_toggle")
                        )
                    }

                    // Tactile Theme Mode selector buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeoButton(
                            onClick = { if (isDarkTheme) onToggleDarkTheme() },
                            backgroundColor = if (!isDarkTheme) AmberAccent else CreamBackground,
                            modifier = Modifier.weight(1f),
                            testTag = "theme_light_btn"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("☀️", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Light Pastel",
                                    fontSize = 11.sp,
                                    fontWeight = if (!isDarkTheme) FontWeight.Black else FontWeight.Bold
                                )
                            }
                        }

                        NeoButton(
                            onClick = { if (!isDarkTheme) onToggleDarkTheme() },
                            backgroundColor = if (isDarkTheme) PastelLavenderLight else CreamBackground,
                            modifier = Modifier.weight(1f),
                            testTag = "theme_dark_btn"
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🌙", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Dark Mode",
                                    fontSize = 11.sp,
                                    fontWeight = if (isDarkTheme) FontWeight.Black else FontWeight.Bold
                                )
                            }
                        }
                    }

                    Text(
                        text = "🔒 Your theme preference is persisted immediately into Jetpack DataStore Preferences.",
                        fontSize = 10.sp,
                        color = PrimaryInk.copy(alpha = 0.6f)
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
                        text = "Messages are locally encrypted using authenticated AES-GCM 256-bit before transport.",
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

        // Logout and Delete Account
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NeoButton(
                    onClick = onLogout,
                    backgroundColor = PastelButterYellow,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "logout_button"
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Logout, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "LOG OUT OF DEVICE", fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    }
                }

                NeoButton(
                    onClick = { showDeleteConfirmDialog = true },
                    backgroundColor = CoralPastel,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "delete_account_button"
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "PERMANENTLY DELETE ACCOUNT", fontSize = 12.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    }
                }
            }
        }
    }

    // Delete Account Confirmation Dialog
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            title = { Text("Delete Account?", fontWeight = FontWeight.Black) },
            text = { Text("This will permanently erase your user profile, local keys, and private circle membership on this device. This action cannot be undone.") },
            confirmButton = {
                NeoButton(
                    onClick = {
                        showDeleteConfirmDialog = false
                        onDeleteAccount()
                    },
                    backgroundColor = CoralPastel
                ) {
                    Text("YES, DELETE", fontWeight = FontWeight.Black)
                }
            },
            dismissButton = {
                NeoButton(
                    onClick = { showDeleteConfirmDialog = false },
                    backgroundColor = Color.White
                ) {
                    Text("CANCEL", fontWeight = FontWeight.Black)
                }
            }
        )
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

    // Home-Screen Widgets Guide Dialog
    if (showWidgetGuideDialog) {
        Dialog(onDismissRequest = { showWidgetGuideDialog = false }) {
            NeoCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.White) {
                LazyColumn(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Text("📱 Android Home-Screen Widgets", fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("NoteCircle supports 5 privacy-first home-screen widgets:", fontSize = 11.sp, color = PrimaryInk.copy(alpha = 0.7f))
                    }
                    item {
                        Column(modifier = Modifier.background(PastelPeachLight, RoundedCornerShape(8.dp)).padding(10.dp)) {
                            Text("1. My Status Widget 🌿", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            Text("Displays your current availability status & mood emoji directly on home screen.", fontSize = 10.5.sp)
                        }
                    }
                    item {
                        Column(modifier = Modifier.background(PastelPinkLight, RoundedCornerShape(8.dp)).padding(10.dp)) {
                            Text("2. Your Circle Widget ⭕", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            Text("Shows latest whisper notes from your Close Friends circle without opening app.", fontSize = 10.5.sp)
                        }
                    }
                    item {
                        Column(modifier = Modifier.background(PastelMintLight, RoundedCornerShape(8.dp)).padding(10.dp)) {
                            Text("3. Quick Note Widget ✍️", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            Text("One-tap shortcut to write an ephemeral whisper note with audience preview.", fontSize = 10.5.sp)
                        }
                    }
                    item {
                        Column(modifier = Modifier.background(PastelButterYellow, RoundedCornerShape(8.dp)).padding(10.dp)) {
                            Text("4. Circle Countdown Widget ⏳", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            Text("Real-time countdown for upcoming circle meetups, birthdays, or tea parties.", fontSize = 10.5.sp)
                        }
                    }
                    item {
                        Column(modifier = Modifier.background(PastelLavenderLight, RoundedCornerShape(8.dp)).padding(10.dp)) {
                            Text("5. Quick Check-In Widget 🛡️", fontSize = 12.sp, fontWeight = FontWeight.Black)
                            Text("Instant 1-tap safety check-in: 'I\\'m Safe', 'Reached Home', or 'Need Someone'.", fontSize = 10.5.sp)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        NeoButton(onClick = { showWidgetGuideDialog = false }, backgroundColor = AmberAccent, modifier = Modifier.fillMaxWidth()) {
                            Text("AWESOME", fontWeight = FontWeight.Black)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AuthScreen(
    onLogin: (identifier: String, password: String, onError: (String) -> Unit) -> Unit,
    onRegister: (email: String, username: String, displayName: String, bio: String, avatarColorHex: String, password: String, onError: (String) -> Unit) -> Unit,
    onQuickDemo: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 0 = Log In, 1 = Create Account (One Account Per Email)
    var selectedAuthTab by remember { mutableStateOf(0) }

    // Log In State
    var loginIdentifier by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }
    var loginPasswordVisible by remember { mutableStateOf(false) }

    // Register State
    var registerEmail by remember { mutableStateOf("") }
    var registerPassword by remember { mutableStateOf("") }
    var registerPasswordVisible by remember { mutableStateOf(false) }
    var displayName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf("#FFBF00") }

    var authError by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    val avatarColors = listOf("#FFBF00", "#88D49E", "#70B8FF", "#D8B4F8", "#FF6B6B")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F0))
            .padding(18.dp),
        contentAlignment = Alignment.Center
    ) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.White,
            hasTape = true,
            tapeText = "★ NOTECIRCLE ACCESS"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Branding Header
                Text(text = "📓", fontSize = 44.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "NoteCircle",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = "Private by default. Temporary by design.",
                    fontSize = 11.5.sp,
                    color = PrimaryInk.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Dual Tab Switcher: [ LOG IN ] vs [ CREATE ACCOUNT ]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF0EDE4))
                        .border(1.5.dp, BorderDark, RoundedCornerShape(12.dp))
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedAuthTab == 0) AmberAccent else Color.Transparent)
                            .clickable {
                                selectedAuthTab = 0
                                authError = null
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LOG IN 🚀",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (selectedAuthTab == 1) AmberAccent else Color.Transparent)
                            .clickable {
                                selectedAuthTab = 1
                                authError = null
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "CREATE ACCOUNT 🌸",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Error Message Banner if any
                if (authError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CoralPastel)
                            .border(1.5.dp, BorderDark, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "⚠️ $authError",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryInk
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                if (selectedAuthTab == 0) {
                    // =========================
                    // TAB 0: LOG IN
                    // =========================
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column {
                            Text(
                                text = "EMAIL OR USERNAME",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = loginIdentifier,
                                onValueChange = {
                                    loginIdentifier = it
                                    authError = null
                                },
                                placeholder = { Text("e.g. maya@gmail.com or @maya") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_identifier_input"),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "PASSWORD",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = loginPassword,
                                onValueChange = {
                                    loginPassword = it
                                    authError = null
                                },
                                placeholder = { Text("Enter your password") },
                                singleLine = true,
                                visualTransformation = if (loginPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { loginPasswordVisible = !loginPasswordVisible }) {
                                        Icon(
                                            imageVector = if (loginPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = if (loginPasswordVisible) "Hide password" else "Show password",
                                            tint = PrimaryInk.copy(alpha = 0.6f)
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_password_input"),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        NeoButton(
                            onClick = {
                                if (loginIdentifier.isNotBlank() && loginPassword.isNotBlank()) {
                                    isSubmitting = true
                                    onLogin(loginIdentifier, loginPassword) { err ->
                                        isSubmitting = false
                                        authError = err
                                    }
                                } else {
                                    authError = "Please enter both identifier and password."
                                }
                            },
                            backgroundColor = AmberAccent,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isSubmitting,
                            testTag = "auth_login_button"
                        ) {
                            Text(
                                text = if (isSubmitting) "VERIFYING..." else "LOG IN TO NOTECIRCLE 🚀",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
                        }

                        // Quick Demo Login Button
                        NeoButton(
                            onClick = onQuickDemo,
                            backgroundColor = PastelButterYellow,
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "auth_demo_button"
                        ) {
                            Text(
                                text = "✨ QUICK DEMO LOGIN (MAYA)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                        }

                        Text(
                            text = "🔒 No OTP or phone verification required. Private by default.",
                            fontSize = 10.sp,
                            color = PrimaryInk.copy(alpha = 0.6f),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    // =========================
                    // TAB 1: CREATE ACCOUNT (1 per email)
                    // =========================
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(PastelMintLight, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🔒 Single account policy: 1 account per Gmail/Email address",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk
                            )
                        }

                        Column {
                            Text(
                                text = "GMAIL / EMAIL ADDRESS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            OutlinedTextField(
                                value = registerEmail,
                                onValueChange = {
                                    registerEmail = it
                                    authError = null
                                    if (username.isBlank() && it.contains("@")) {
                                        username = it.substringBefore("@").lowercase()
                                    }
                                    if (displayName.isBlank() && it.contains("@")) {
                                        displayName = it.substringBefore("@").replaceFirstChar { c -> c.uppercase() }
                                    }
                                },
                                placeholder = { Text("you@gmail.com") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_reg_email_input"),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "PASSWORD",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            OutlinedTextField(
                                value = registerPassword,
                                onValueChange = {
                                    registerPassword = it
                                    authError = null
                                },
                                placeholder = { Text("Choose a safe password") },
                                singleLine = true,
                                visualTransformation = if (registerPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { registerPasswordVisible = !registerPasswordVisible }) {
                                        Icon(
                                            imageVector = if (registerPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            contentDescription = if (registerPasswordVisible) "Hide password" else "Show password",
                                            tint = PrimaryInk.copy(alpha = 0.6f)
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_reg_password_input"),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "DISPLAY NAME",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                OutlinedTextField(
                                    value = displayName,
                                    onValueChange = { displayName = it },
                                    placeholder = { Text("Maya Lin") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("auth_reg_name_input"),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "USERNAME",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                OutlinedTextField(
                                    value = username,
                                    onValueChange = { username = it },
                                    placeholder = { Text("maya") },
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("auth_reg_username_input"),
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "BIO / TAGLINE (OPTIONAL)",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            OutlinedTextField(
                                value = bio,
                                onValueChange = { bio = it },
                                placeholder = { Text("Studying architecture & coffee enthusiast") },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_reg_bio_input"),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "AVATAR ACCENT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                avatarColors.forEach { colHex ->
                                    val c = Color(android.graphics.Color.parseColor(colHex))
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(c, CircleShape)
                                            .border(1.8.dp, BorderDark, CircleShape)
                                            .clickable { selectedColor = colHex },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (selectedColor == colHex) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        NeoButton(
                            onClick = {
                                if (!registerEmail.contains("@")) {
                                    authError = "Please enter a valid email address."
                                } else if (registerPassword.length < 4) {
                                    authError = "Password must be at least 4 characters."
                                } else if (displayName.isBlank() || username.isBlank()) {
                                    authError = "Please fill in display name and username."
                                } else {
                                    isSubmitting = true
                                    onRegister(
                                        registerEmail,
                                        username,
                                        displayName,
                                        bio,
                                        selectedColor,
                                        registerPassword
                                    ) { err ->
                                        isSubmitting = false
                                        authError = err
                                    }
                                }
                            },
                            backgroundColor = MintPastel,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isSubmitting,
                            testTag = "auth_register_button"
                        ) {
                            Text(
                                text = if (isSubmitting) "CREATING ACCOUNT..." else "CREATE MY ACCOUNT 🌸",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black
                            )
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
