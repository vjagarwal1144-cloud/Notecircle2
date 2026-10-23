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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.NoteEntity
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
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PrimaryInk

@Composable
fun PrivateVaultScreen(
    isUnlocked: Boolean,
    vaultNotes: List<NoteEntity>,
    errorMessage: String?,
    onUnlock: (String) -> Unit,
    onLock: () -> Unit,
    onCreateVaultNote: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var enteredPin by remember { mutableStateOf("") }
    var newNoteContent by remember { mutableStateOf("") }
    var isAddingNote by remember { mutableStateOf(false) }

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
                    text = "Zero-knowledge protected space on device",
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
                        contentDescription = "Lock",
                        tint = PrimaryInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (!isUnlocked) {
            // Locked State: PIN entry
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                contentAlignment = Alignment.Center
            ) {
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
                            text = "Default PIN is 1234. Change anytime in settings.",
                            fontSize = 11.5.sp,
                            color = PrimaryInk.copy(alpha = 0.65f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = enteredPin,
                            onValueChange = { if (it.length <= 6) enteredPin = it },
                            placeholder = { Text("Enter 4-digit PIN", fontSize = 13.sp) },
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
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
                            text = "Unlock Private Vault ✨",
                            onClick = { onUnlock(enteredPin) },
                            backgroundColor = AmberAccent,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            // Unlocked State: List of Vault notes
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
                                    text = "Protected Notes (${vaultNotes.size})",
                                    fontSize = 15.sp,
                                    fontFamily = OutfitFontFamily,
                                    fontWeight = FontWeight.Black,
                                    color = PrimaryInk
                                )
                                Text(
                                    text = "These notes are never shown in the public circle stream.",
                                    fontSize = 11.sp,
                                    color = PrimaryInk.copy(alpha = 0.7f)
                                )
                            }
                            NeoButton(
                                text = "+ Note",
                                onClick = { isAddingNote = !isAddingNote },
                                backgroundColor = PastelPinkLight,
                                modifier = Modifier
                            )
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
                                        .height(100.dp),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
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
                                                onCreateVaultNote(newNoteContent)
                                                newNoteContent = ""
                                                isAddingNote = false
                                            }
                                        },
                                        backgroundColor = PastelButterYellow
                                    )
                                }
                            }
                        }
                    }
                }

                if (vaultNotes.isEmpty()) {
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
                                    text = "No private vault notes yet",
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
                    items(vaultNotes) { note ->
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
                                    NeoBadge(
                                        text = "ENCRYPTED LOCAL STORAGE",
                                        backgroundColor = PastelMint,
                                        textColor = PrimaryInk
                                    )
                                    Text(
                                        text = "Only Me",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryInk.copy(alpha = 0.5f)
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
