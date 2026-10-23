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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.data.local.entity.NoteEntity
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
fun AiCompanionScreen(
    notes: List<NoteEntity>,
    promptInput: String,
    generatedResult: String?,
    isProcessing: Boolean,
    onPromptChanged: (String) -> Unit,
    onGenerate: (actionType: String, content: String) -> Unit,
    onClearResult: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedSampleNote by remember { mutableStateOf<NoteEntity?>(notes.firstOrNull()) }

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
                        text = "AI Companion ✨",
                        fontSize = 20.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Optional on-device assistance with zero automatic uploads",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )
                }
            }
        }

        // Privacy Guarantee Card
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = PastelMintLight,
                hasTape = true,
                tapeText = "🛡️ PRIVACY CONSENT VERIFIED",
                tapeColor = PastelButterYellow
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Security",
                        tint = PrimaryInk,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Your private notes are never sent silently to external models or used for public AI training.",
                        fontSize = 11.5.sp,
                        lineHeight = 16.sp,
                        color = PrimaryInk
                    )
                }
            }
        }

        // FEATURE 31: Input & Action Trigger
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "✨ SMART ASSIST",
                tapeColor = PastelLavenderLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Select a Note or Enter Text to Polish",
                        fontSize = 15.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = promptInput,
                        onValueChange = onPromptChanged,
                        placeholder = { Text("Paste thoughts, a draft or reflection to structure...", fontSize = 13.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Choose an Assistant Action:",
                        fontSize = 12.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NeoButton(
                            text = "✨ Polish & Warmth",
                            onClick = { onGenerate("POLISH", promptInput.ifBlank { selectedSampleNote?.content ?: "A quiet morning moment." }) },
                            backgroundColor = PastelPinkLight
                        )
                        NeoButton(
                            text = "📋 Key Summary",
                            onClick = { onGenerate("SUMMARIZE", promptInput.ifBlank { selectedSampleNote?.content ?: "Planning weekend activities with friends." }) },
                            backgroundColor = PastelSkyBlueLight
                        )
                        NeoButton(
                            text = "🏷️ Auto-Tags",
                            onClick = { onGenerate("SUGGEST_TAGS", promptInput.ifBlank { selectedSampleNote?.content ?: "Study session about biology and notes." }) },
                            backgroundColor = PastelMint
                        )
                        NeoButton(
                            text = "✅ Task to Action",
                            onClick = { onGenerate("CONVERT_ACTION", promptInput.ifBlank { selectedSampleNote?.content ?: "Remember to buy matcha and call mom." }) },
                            backgroundColor = PastelButterYellow
                        )
                    }
                }
            }
        }

        // Loading or Result View
        if (isProcessing) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = PrimaryInk,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Thinking gently with local privacy...",
                            fontSize = 12.sp,
                            fontFamily = OutfitFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryInk.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        } else if (generatedResult != null) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = PastelButterYellow,
                    hasTape = true,
                    tapeText = "★ AI RESULT",
                    tapeColor = PastelPinkLight
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = generatedResult,
                            fontSize = 13.5.sp,
                            lineHeight = 19.sp,
                            color = PrimaryInk
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            NeoButton(
                                text = "Clear Result",
                                onClick = onClearResult,
                                backgroundColor = Color.White
                            )
                        }
                    }
                }
            }
        }

        // FEATURE 32: Connected Thoughts Knowledge Graph Preview
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface,
                hasTape = true,
                tapeText = "🧠 CONNECTED THOUGHTS",
                tapeColor = PastelMintLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Connected Thoughts Map",
                        fontSize = 16.sp,
                        fontFamily = OutfitFontFamily,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    Text(
                        text = "Automatic relationships between your shared thoughts & journal entries",
                        fontSize = 11.5.sp,
                        color = PrimaryInk.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("#mindfulness", "#gratitude", "#coffee", "#family", "#study", "#weekend", "#reading").forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(PastelLavenderLight)
                                    .border(1.2.dp, BorderDark, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 11.5.sp,
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
    }
}
