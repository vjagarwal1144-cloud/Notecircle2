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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.StudyFlashcardEntity
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
import com.example.ui.theme.PastelMintSoft
import com.example.ui.theme.PastelPeachLight
import com.example.ui.theme.PastelPinkLight
import com.example.ui.theme.PastelSkyBlueLight
import com.example.ui.theme.PrimaryInk

@Composable
fun StudyWorkspaceScreen(
    flashcards: List<StudyFlashcardEntity>,
    onCreateFlashcard: (subject: String, prompt: String, answer: String) -> Unit,
    onToggleMastered: (cardId: String, isMastered: Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var isAddingCard by remember { mutableStateOf(false) }
    var newSubject by remember { mutableStateOf("") }
    var newPrompt by remember { mutableStateOf("") }
    var newAnswer by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        // Header
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
                    text = "Study Workspace 📚",
                    fontSize = 20.sp,
                    fontFamily = OutfitFontFamily,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = "Subjects, flashcards & revision notes",
                    fontSize = 11.5.sp,
                    color = PrimaryInk.copy(alpha = 0.65f)
                )
            }
            NeoButton(
                text = "+ Card",
                onClick = { isAddingCard = !isAddingCard },
                backgroundColor = PastelButterYellow
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = PastelLavenderLight,
                    hasTape = true,
                    tapeText = "🎓 ACTIVE DECKS",
                    tapeColor = PastelButterYellow
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Interactive Flashcards (${flashcards.size})",
                            fontSize = 16.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Tap a flashcard to reveal the answer. Toggle mastered when reviewed.",
                            fontSize = 11.5.sp,
                            color = PrimaryInk.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            if (isAddingCard) {
                item {
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = MaterialTheme.colorScheme.surface,
                        hasTape = true,
                        tapeText = "NEW FLASHCARD",
                        tapeColor = PastelMintLight
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            OutlinedTextField(
                                value = newSubject,
                                onValueChange = { newSubject = it },
                                placeholder = { Text("Subject (e.g. Psychology, Biology)", fontSize = 13.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newPrompt,
                                onValueChange = { newPrompt = it },
                                placeholder = { Text("Question or Prompt", fontSize = 13.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = newAnswer,
                                onValueChange = { newAnswer = it },
                                placeholder = { Text("Answer", fontSize = 13.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                NeoButton(
                                    text = "Cancel",
                                    onClick = { isAddingCard = false },
                                    backgroundColor = Color(0xFFF1ECE4)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                NeoButton(
                                    text = "Create Flashcard ✨",
                                    onClick = {
                                        if (newSubject.isNotBlank() && newPrompt.isNotBlank() && newAnswer.isNotBlank()) {
                                            onCreateFlashcard(newSubject, newPrompt, newAnswer)
                                            newSubject = ""
                                            newPrompt = ""
                                            newAnswer = ""
                                            isAddingCard = false
                                        }
                                    },
                                    backgroundColor = PastelButterYellow
                                )
                            }
                        }
                    }
                }
            }

            items(flashcards) { card ->
                var isRevealed by remember { mutableStateOf(false) }

                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = if (card.isMastered) Color(0xFFF5F3EF) else MaterialTheme.colorScheme.surface,
                    hasTape = true,
                    tapeText = card.subject.uppercase(),
                    tapeColor = if (card.isMastered) PastelMint else PastelPinkLight
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isRevealed = !isRevealed }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Q: ${card.prompt}",
                                fontSize = 15.sp,
                                fontFamily = OutfitFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (isRevealed) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(PastelMintSoft)
                                    .border(1.2.dp, BorderDark.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(
                                        text = "ANSWER:",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = PrimaryInk.copy(alpha = 0.6f)
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = card.answer,
                                        fontSize = 13.5.sp,
                                        lineHeight = 19.sp,
                                        color = PrimaryInk
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        } else {
                            Text(
                                text = "👉 Tap to reveal answer",
                                fontSize = 11.5.sp,
                                color = PrimaryInk.copy(alpha = 0.5f),
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            NeoButton(
                                text = if (card.isMastered) "✓ Mastered" else "Mark Mastered",
                                onClick = { onToggleMastered(card.cardId, card.isMastered) },
                                backgroundColor = if (card.isMastered) PastelMint else Color(0xFFF1ECE4)
                            )
                        }
                    }
                }
            }
        }
    }
}
