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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.example.ui.theme.PastelLavenderLight
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelMintLight
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PrimaryInk
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PrivateVaultScreen(
    currentUser: UserEntity?,
    isUnlocked: Boolean,
    vaultNotes: List<NoteEntity>,
    errorMessage: String?,
    onUnlock: (String) -> Unit,
    onSetPin: (String) -> Unit,
    onLock: () -> Unit,
    onCreateVaultNote: (content: String, tag: String) -> Unit,
    onDeleteVaultNote: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    // If user has default or blank pin, default to setup mode
    val hasCustomPin = currentUser?.vaultPin != null && currentUser.vaultPin.isNotBlank() && currentUser.vaultPin != "1234"
    var isSetupMode by remember { mutableStateOf(!hasCustomPin) }

    var enteredPin by remember { mutableStateOf("") }
    var newPin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var setupError by remember { mutableStateOf<String?>(null) }

    var newNoteContent by remember { mutableStateOf("") }
    var selectedNoteTag by remember { mutableStateOf("Personal") }
    var isAddingNote by remember { mutableStateOf(false) }

    var selectedFilterTag by remember { mutableStateOf<String?>(null) }
    val vaultTags = listOf("All", "Personal", "Work", "Ideas", "Study", "Gratitude")

    val filteredVaultNotes = if (selectedFilterTag == null || selectedFilterTag == "All") {
        vaultNotes
    } else {
        vaultNotes.filter { it.tags.contains(selectedFilterTag!!, ignoreCase = true) || it.categoryName.equals(selectedFilterTag, ignoreCase = true) }
    }

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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Private Vault 🔒",
                    fontSize = 20.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = "Encrypted local zero-knowledge storage",
                    fontSize = 11.5.sp,
                    color = PrimaryInk.copy(alpha = 0.65f)
                )
            }
            if (isUnlocked) {
                IconButton(
                    onClick = onLock,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CoralPastel)
                        .border(1.6.dp, BorderDark, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Lock Vault",
                        tint = PrimaryInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (!isUnlocked) {
            // Locked State: Choose between Set Up PIN or Enter PIN
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isSetupMode) {
                    // SETUP / CREATE PIN FLOW FOR NEW USERS
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(0.94f),
                        backgroundColor = PastelLavenderLight,
                        hasTape = true,
                        tapeText = "🔐 SET UP VAULT PIN",
                        tapeColor = PastelButterYellow
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(PastelMint)
                                    .border(2.dp, BorderDark, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Key,
                                    contentDescription = "Create PIN",
                                    tint = PrimaryInk,
                                    modifier = Modifier.size(30.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Create Your 4-Digit PIN",
                                fontSize = 18.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Set your personal PIN to securely seal and protect your private notes.",
                                fontSize = 11.5.sp,
                                color = PrimaryInk.copy(alpha = 0.7f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = newPin,
                                onValueChange = { if (it.length <= 4) newPin = it },
                                placeholder = { Text("Choose 4-digit PIN", fontSize = 13.sp) },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth().testTag("vault_create_pin_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = BorderDark,
                                    unfocusedBorderColor = BorderDark.copy(alpha = 0.5f)
                                )
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = confirmPin,
                                onValueChange = { if (it.length <= 4) confirmPin = it },
                                placeholder = { Text("Confirm 4-digit PIN", fontSize = 13.sp) },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth().testTag("vault_confirm_pin_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = BorderDark,
                                    unfocusedBorderColor = BorderDark.copy(alpha = 0.5f)
                                )
                            )

                            if (setupError != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "⚠️ $setupError",
                                    fontSize = 12.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            NeoButton(
                                text = "SAVE PIN & UNLOCK VAULT 🔒",
                                onClick = {
                                    if (newPin.length != 4) {
                                        setupError = "PIN must be exactly 4 digits."
                                    } else if (newPin != confirmPin) {
                                        setupError = "PINs do not match. Please re-enter."
                                    } else {
                                        setupError = null
                                        onSetPin(newPin)
                                    }
                                },
                                backgroundColor = PastelMint,
                                modifier = Modifier.fillMaxWidth().testTag("vault_save_pin_button")
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Already have a PIN? Unlock Vault ➔",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier
                                    .clickable { isSetupMode = false }
                                    .padding(4.dp)
                            )
                        }
                    }
                } else {
                    // ENTER PIN TO UNLOCK FLOW
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(0.92f),
                        backgroundColor = PastelLavenderLight,
                        hasTape = true,
                        tapeText = "🔐 ENTER VAULT PIN",
                        tapeColor = PastelButterYellow
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(2.dp, BorderDark, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = PrimaryInk,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Vault is Sealed",
                                fontSize = 18.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Black,
                                color = PrimaryInk
                            )
                            Text(
                                text = "Enter your 4-digit PIN to decrypt your private space.",
                                fontSize = 11.5.sp,
                                color = PrimaryInk.copy(alpha = 0.65f)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = enteredPin,
                                onValueChange = { if (it.length <= 4) enteredPin = it },
                                placeholder = { Text("Enter 4-digit PIN", fontSize = 13.sp) },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth().testTag("vault_pin_input"),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = BorderDark,
                                    unfocusedBorderColor = BorderDark.copy(alpha = 0.5f)
                                )
                            )

                            if (errorMessage != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = errorMessage,
                                    fontSize = 12.sp,
                                    color = Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            NeoButton(
                                text = "UNLOCK PRIVATE VAULT ✨",
                                onClick = { onUnlock(enteredPin) },
                                backgroundColor = AmberAccent,
                                modifier = Modifier.fillMaxWidth().testTag("vault_unlock_button")
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "New user or forgot PIN? Set New PIN ➔",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier
                                    .clickable { isSetupMode = true }
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // UNLOCKED STATE: LIST OF VAULT NOTES & LABELS
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = PastelMint,
                        hasTape = true,
                        tapeText = "★ VAULT UNLOCKED",
                        tapeColor = PastelButterYellow
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LockOpen,
                                contentDescription = "Unlocked",
                                tint = PrimaryInk,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Protected Notes (${filteredVaultNotes.size})",
                                    fontSize = 15.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Encrypted on-device. Never exposed to public streams.",
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                            NeoButton(
                                text = "+ Note",
                                onClick = { isAddingNote = !isAddingNote },
                                backgroundColor = PastelPinkLight,
                                testTag = "vault_add_note_button"
                            )
                        }
                    }
                }

                // Tag Filter Row for Vault Notes
                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(vaultTags) { tag ->
                            val isSelected = (selectedFilterTag == null && tag == "All") || selectedFilterTag == tag
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) AmberAccent else MaterialTheme.colorScheme.surface)
                                    .border(1.4.dp, BorderDark, RoundedCornerShape(10.dp))
                                    .clickable { selectedFilterTag = if (tag == "All") null else tag }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    color = PrimaryInk
                                )
                            }
                        }
                    }
                }

                if (isAddingNote) {
                    item {
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = "🔒 NEW VAULT NOTE",
                            tapeColor = PastelLavenderLight
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                OutlinedTextField(
                                    value = newNoteContent,
                                    onValueChange = { newNoteContent = it },
                                    placeholder = { Text("Write your private protected thought...", fontSize = 13.sp) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .testTag("vault_new_note_input"),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "TAG / LABEL",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    items(listOf("Personal", "Work", "Ideas", "Study", "Gratitude")) { tag ->
                                        val isChosen = selectedNoteTag == tag
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(if (isChosen) PastelMint else Color(0xFFF0EDE4))
                                                .border(1.2.dp, BorderDark, RoundedCornerShape(8.dp))
                                                .clickable { selectedNoteTag = tag }
                                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text(tag, fontSize = 11.sp, fontWeight = if (isChosen) FontWeight.Black else FontWeight.Bold)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    NeoButton(
                                        text = "Cancel",
                                        onClick = { isAddingNote = false },
                                        backgroundColor = Color(0xFFF1ECE4)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    NeoButton(
                                        text = "Store in Vault 🔐",
                                        onClick = {
                                            if (newNoteContent.isNotBlank()) {
                                                onCreateVaultNote(newNoteContent, selectedNoteTag)
                                                newNoteContent = ""
                                                isAddingNote = false
                                            }
                                        },
                                        backgroundColor = PastelButterYellow,
                                        testTag = "vault_save_note_button"
                                    )
                                }
                            }
                        }
                    }
                }

                if (filteredVaultNotes.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🧸", fontSize = 42.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "No private vault notes in this section",
                                    fontSize = 15.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "Tap '+ Note' above to store your most secret reflections.",
                                    fontSize = 12.sp,
                                    color = PrimaryInk.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                } else {
                    items(filteredVaultNotes, key = { it.noteId }) { note ->
                        NeoCard(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            hasTape = true,
                            tapeText = "🔒 VAULT PROTECTED",
                            tapeColor = PastelPinkLight
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = note.content,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    color = PrimaryInk
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        if (note.tags.isNotBlank()) {
                                            NeoBadge(
                                                text = "#${note.tags}",
                                                backgroundColor = PastelPeachLight,
                                                textColor = PrimaryInk
                                            )
                                        }
                                        NeoBadge(
                                            text = "ENCRYPTED LOCAL STORAGE",
                                            backgroundColor = PastelMint,
                                            textColor = PrimaryInk
                                        )
                                    }
                                    IconButton(
                                        onClick = { onDeleteVaultNote(note.noteId) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteOutline,
                                            contentDescription = "Delete Vault Note",
                                            tint = PrimaryInk.copy(alpha = 0.6f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
