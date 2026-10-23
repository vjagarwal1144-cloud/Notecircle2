package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.CircleEntity
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

@Composable
fun CircleScreen(
    circles: List<CircleEntity>,
    connections: List<UserEntity>,
    pendingRequests: List<UserEntity>,
    searchQuery: String,
    searchResults: List<UserEntity>,
    onSearchQueryChanged: (String) -> Unit,
    onSendRequest: (String) -> Unit,
    onAcceptRequest: (String) -> Unit,
    onRejectRequest: (String) -> Unit,
    onToggleCloseFriend: (userId: String, isClose: Boolean) -> Unit,
    onStartChat: (UserEntity) -> Unit,
    isAddCircleOpen: Boolean,
    onShowAddCircle: (Boolean) -> Unit,
    onCreateCircle: (name: String, description: String, emoji: String, colorHex: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Bar for Username
        item {
            NeoCard(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = PrimaryInk,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChanged,
                        placeholder = {
                            Text(
                                "Search @username to invite...",
                                fontSize = 13.sp,
                                color = PrimaryInk.copy(alpha = 0.5f)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("user_search_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { onSearchQueryChanged("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = PrimaryInk, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // Search Results Section (if searching)
        if (searchQuery.isNotBlank()) {
            item {
                Text(
                    text = "SEARCH RESULTS FOR \"$searchQuery\"",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk
                )
            }
            if (searchResults.isEmpty()) {
                item {
                    NeoCard(
                        modifier = Modifier.fillMaxWidth(),
                        backgroundColor = Color(0xFFF9F7F1)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No users found with \"$searchQuery\"",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryInk
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "You can send an invite link or direct connection request.",
                                fontSize = 11.sp,
                                color = PrimaryInk.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            NeoButton(
                                onClick = { onSendRequest(searchQuery) },
                                backgroundColor = AmberAccent
                            ) {
                                Text("SEND CONNECTION REQUEST", fontSize = 11.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            } else {
                items(searchResults) { user ->
                    SearchResultCard(
                        user = user,
                        onSendRequest = { onSendRequest(user.username) }
                    )
                }
            }
        }

        // Pending Requests Section
        if (pendingRequests.isNotEmpty()) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color(0xFFFFF9E6),
                    hasTape = true,
                    tapeText = "PENDING REQUESTS"
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Connection Requests (${pendingRequests.size})",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        pendingRequests.forEach { req ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    NeoAvatar(name = req.displayName, size = 32.dp, backgroundColor = LavenderPastel)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(text = req.displayName, fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                                        Text(text = req.username, fontSize = 10.sp, color = PrimaryInk.copy(alpha = 0.6f))
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MintPastel)
                                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                            .clickable { onAcceptRequest(req.userId) }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Check, contentDescription = "Accept", tint = PrimaryInk, modifier = Modifier.size(16.dp))
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(CoralPastel)
                                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                            .clickable { onRejectRequest(req.userId) }
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = "Decline", tint = PrimaryInk, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }

        // My Circles Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "MY CIRCLES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryInk,
                    letterSpacing = 0.5.sp
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(AmberAccent)
                        .border(1.5.dp, BorderDark, RoundedCornerShape(6.dp))
                        .clickable { onShowAddCircle(true) }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "NEW CIRCLE", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }
            }
        }

        // Circles Grid
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(circles) { circle ->
                    CircleCardItem(circle = circle)
                }
            }
        }

        // My Connections Section
        item {
            Text(
                text = "APPROVED CONNECTIONS (${connections.size})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = PrimaryInk,
                letterSpacing = 0.5.sp
            )
        }

        if (connections.isEmpty()) {
            item {
                NeoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    hasTape = true,
                    tapeText = "CONNECTIONS"
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🤝", fontSize = 40.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No connections yet",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Search for your friends' @usernames above to connect and share private notes.",
                            fontSize = 12.sp,
                            color = PrimaryInk.copy(alpha = 0.7f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        } else {
            items(connections, key = { it.userId }) { conn ->
                ConnectionRowItem(
                    user = conn,
                    onToggleCloseFriend = { isClose -> onToggleCloseFriend(conn.userId, isClose) },
                    onStartChat = { onStartChat(conn) }
                )
            }
        }
    }

    // Add Circle Dialog
    if (isAddCircleOpen) {
        AddCircleDialog(
            onCreate = onCreateCircle,
            onDismiss = { onShowAddCircle(false) }
        )
    }
}

@Composable
private fun CircleCardItem(circle: CircleEntity) {
    val color = try {
        Color(android.graphics.Color.parseColor(circle.colorHex))
    } catch (e: Exception) {
        MintPastel
    }

    NeoCard(
        modifier = Modifier.width(160.dp),
        backgroundColor = color
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(text = circle.emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = circle.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = PrimaryInk
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = circle.description,
                fontSize = 11.sp,
                maxLines = 2,
                color = PrimaryInk.copy(alpha = 0.7f),
                lineHeight = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            NeoBadge(
                text = "PRIVATE SPACE",
                backgroundColor = Color.White
            )
        }
    }
}

@Composable
private fun ConnectionRowItem(
    user: UserEntity,
    onToggleCloseFriend: (Boolean) -> Unit,
    onStartChat: () -> Unit
) {
    NeoCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = try {
                Color(android.graphics.Color.parseColor(user.avatarColorHex))
            } catch (e: Exception) {
                AmberAccent
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                NeoAvatar(name = user.displayName, size = 40.dp, backgroundColor = color, statusEmoji = user.statusEmoji)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.displayName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = PrimaryInk
                        )
                        if (user.isCloseFriend) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.Star, contentDescription = "Close Friend", tint = AmberAccent, modifier = Modifier.size(14.dp))
                        }
                    }
                    Text(
                        text = user.username,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryInk.copy(alpha = 0.6f)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Close Friend Star Toggle
                IconButton(
                    onClick = { onToggleCloseFriend(!user.isCloseFriend) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (user.isCloseFriend) Icons.Default.Star else Icons.Outlined.StarOutline,
                        contentDescription = "Toggle Close Friend",
                        tint = if (user.isCloseFriend) AmberAccent else PrimaryInk.copy(alpha = 0.5f)
                    )
                }

                // Chat Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(AmberAccent)
                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                        .clickable(onClick = onStartChat)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Chat, contentDescription = "Message", tint = PrimaryInk, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "CHAT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultCard(
    user: UserEntity,
    onSendRequest: () -> Unit
) {
    NeoCard(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                NeoAvatar(name = user.displayName, size = 36.dp, backgroundColor = LavenderPastel)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = user.displayName, fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    Text(text = user.username, fontSize = 11.sp, color = PrimaryInk.copy(alpha = 0.6f))
                }
            }

            NeoButton(
                onClick = onSendRequest,
                backgroundColor = AmberAccent
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null, tint = PrimaryInk, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "CONNECT", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }
            }
        }
    }
}

@Composable
private fun AddCircleDialog(
    onCreate: (name: String, description: String, emoji: String, colorHex: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableStateOf("🎯") }
    var selectedColor by remember { mutableStateOf("#88D49E") }

    val emojis = listOf("🎯", "📚", "☕", "🏡", "✈️", "🚀", "🍕", "🎨", "🌙", "⭐️")
    val colors = listOf("#88D49E", "#FFBF00", "#70B8FF", "#D8B4F8", "#FF6B6B")

    Dialog(onDismissRequest = onDismiss) {
        NeoCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            hasTape = true,
            tapeText = "CREATE CIRCLE"
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "New Trusted Circle", fontSize = 18.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryInk)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Circle name (e.g. Study Pod, Besties...)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Short description...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "CHOOSE EMOJI", fontSize = 10.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(emojis) { emoji ->
                        Text(
                            text = emoji,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (emoji == selectedEmoji) AmberAccent else Color(0xFFF2EEE4))
                                .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                .clickable { selectedEmoji = emoji }
                                .padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                NeoButton(
                    onClick = {
                        if (name.isNotBlank()) {
                            onCreate(name, description, selectedEmoji, selectedColor)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = AmberAccent,
                    enabled = name.isNotBlank()
                ) {
                    Text(text = "CREATE CIRCLE ✨", fontSize = 13.sp, fontWeight = FontWeight.Black, color = PrimaryInk)
                }
            }
        }
    }
}
