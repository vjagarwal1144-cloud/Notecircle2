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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.data.local.entity.ConversationEntity
import com.example.data.local.entity.MessageEntity
import com.example.data.local.entity.UserEntity
import com.example.ui.components.NeoAvatar
import com.example.ui.components.NeoBadge
import com.example.ui.components.NeoButton
import com.example.ui.components.NeoCard
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.BorderDark
import com.example.ui.theme.CoralPastel
import com.example.ui.theme.MintPastel
import com.example.ui.theme.PrimaryInk
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatListScreen(
    conversations: List<ConversationEntity>,
    onConversationClick: (ConversationEntity) -> Unit,
    onGoToCircleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // E2EE Security Banner
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color(0xFFE8F5E9),
                hasTape = true,
                tapeText = "🔒 SECURE VAULT"
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "E2EE",
                        tint = PrimaryInk,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "End-to-End Encrypted",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Text(
                            text = "Only you and your connection have keys to decrypt messages. No plaintext stored on servers.",
                            fontSize = 11.sp,
                            color = PrimaryInk.copy(alpha = 0.7f),
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Conversation items or Empty State
        if (conversations.isEmpty()) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    hasTape = true,
                    tapeText = "NO CHATS"
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "💬", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No private chats yet",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Start a 1-on-1 encrypted conversation with an approved circle connection.",
                            fontSize = 12.sp,
                            color = PrimaryInk.copy(alpha = 0.7f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        NeoButton(onClick = onGoToCircleClick, backgroundColor = AmberAccent) {
                            Text(text = "FIND CONNECTIONS", fontSize = 12.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                        }
                    }
                }
            }
        } else {
            items(conversations, key = { it.conversationId }) { conv ->
                ConversationItemCard(conversation = conv, onClick = { onConversationClick(conv) })
            }
        }
    }
}

@Composable
private fun ConversationItemCard(
    conversation: ConversationEntity,
    onClick: () -> Unit
) {
    NeoCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("conversation_${conversation.conversationId}"),
        backgroundColor = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = try {
                Color(android.graphics.Color.parseColor(conversation.participantAvatarColor))
            } catch (e: Exception) {
                AmberAccent
            }

            NeoAvatar(name = conversation.title, size = 44.dp, backgroundColor = color)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = conversation.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    val timeStr = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(conversation.lastMessageTimestamp))
                    Text(
                        text = timeStr,
                        fontSize = 10.sp,
                        color = PrimaryInk.copy(alpha = 0.5f),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Encrypted",
                        tint = PrimaryInk.copy(alpha = 0.5f),
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = conversation.lastMessageText.ifBlank { "Encrypted conversation" },
                        fontSize = 12.sp,
                        maxLines = 1,
                        color = PrimaryInk.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (conversation.unreadCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(CoralPastel, CircleShape)
                        .border(1.dp, BorderDark, CircleShape)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = conversation.unreadCount.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                }
            }
        }
    }
}

/**
 * Detailed Chat screen with BackHandler and E2EE verification.
 */
@Composable
fun ChatDetailScreen(
    conversation: ConversationEntity,
    messages: List<MessageEntity>,
    currentUser: UserEntity?,
    messageInput: String,
    onMessageInputChanged: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var showSafetyDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, BorderDark)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryInk
                )
            }

            val avatarColor = try {
                Color(android.graphics.Color.parseColor(conversation.participantAvatarColor))
            } catch (e: Exception) {
                AmberAccent
            }

            NeoAvatar(name = conversation.title, size = 36.dp, backgroundColor = avatarColor)
            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = conversation.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
                Text(
                    text = conversation.participantUsername,
                    fontSize = 11.sp,
                    color = PrimaryInk.copy(alpha = 0.6f)
                )
            }

            // Safety Number Badge
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE8F5E9))
                    .border(1.5.dp, BorderDark, RoundedCornerShape(8.dp))
                    .clickable { showSafetyDialog = true }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Verified Safety Number",
                    tint = PrimaryInk,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "VERIFIED",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
            }
        }

        // Messages LazyColumn
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages, key = { it.messageId }) { msg ->
                val isMe = msg.senderId == currentUser?.userId
                MessageBubble(message = msg, isMe = isMe)
            }
        }

        // Message Input Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, BorderDark)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageInput,
                onValueChange = onMessageInputChanged,
                placeholder = { Text("Encrypted message...", fontSize = 13.sp) },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BorderDark,
                    unfocusedBorderColor = BorderDark.copy(alpha = 0.5f),
                    focusedContainerColor = Color(0xFFFAF7F0),
                    unfocusedContainerColor = Color(0xFFFAF7F0)
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            NeoButton(
                onClick = { onSendMessage(messageInput) },
                backgroundColor = AmberAccent,
                enabled = messageInput.isNotBlank(),
                testTag = "send_message_button"
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                    tint = PrimaryInk,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }

    // Safety Number Verification Dialog
    if (showSafetyDialog) {
        SafetyNumberDialog(
            participantName = conversation.title,
            safetyNumber = conversation.safetyNumber,
            onDismiss = { showSafetyDialog = false }
        )
    }
}

@Composable
private fun MessageBubble(
    message: MessageEntity,
    isMe: Boolean
) {
    val alignment = if (isMe) Alignment.End else Alignment.Start
    val bgColor = if (isMe) AmberAccent else Color.White

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier.padding(bottom = 2.dp, end = 2.dp)
        ) {
            // Shadow
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .offset(x = 2.dp, y = 2.dp)
                    .background(BorderDark, RoundedCornerShape(12.dp))
            )

            // Surface
            Column(
                modifier = Modifier
                    .background(bgColor, RoundedCornerShape(12.dp))
                    .border(1.5.dp, BorderDark, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = message.plainText,
                    fontSize = 14.sp,
                    color = PrimaryInk,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val timeStr = SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(message.timestamp))
                    Text(
                        text = timeStr,
                        fontSize = 9.sp,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                    if (isMe) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Delivered",
                            tint = PrimaryInk,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "E2EE",
                        tint = PrimaryInk.copy(alpha = 0.4f),
                        modifier = Modifier.size(9.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SafetyNumberDialog(
    participantName: String,
    safetyNumber: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "E2EE VERIFICATION"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Safety Number",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = PrimaryInk
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Verify this 16-digit safety fingerprint with $participantName to ensure end-to-end encryption keys match.",
                    fontSize = 12.sp,
                    color = PrimaryInk.copy(alpha = 0.7f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Safety Number Block
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFAF7F0), RoundedCornerShape(10.dp))
                        .border(2.dp, BorderDark, RoundedCornerShape(10.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = safetyNumber,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        fontFamily = FontFamily.Monospace,
                        color = PrimaryInk,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                NeoBadge(
                    text = "STATUS: VERIFIED & ACTIVE",
                    backgroundColor = MintPastel
                )

                Spacer(modifier = Modifier.height(16.dp))

                NeoButton(
                    onClick = onDismiss,
                    backgroundColor = AmberAccent,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "DONE", fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }
            }
        }
    }
}
