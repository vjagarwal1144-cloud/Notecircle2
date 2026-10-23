package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AudienceType
import com.example.ui.components.AvailabilityDialog
import com.example.ui.components.CreateNoteSheet
import com.example.ui.components.MoodGardenDialog
import com.example.ui.components.NoteCircleBottomBar
import com.example.ui.components.NoteCircleHeader
import com.example.ui.components.ThoughtBubbleDialog
import com.example.ui.screens.AiCompanionScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ChatDetailScreen
import com.example.ui.screens.ChatListScreen
import com.example.ui.screens.CircleScreen
import com.example.ui.screens.CircleWorkspaceScreen
import com.example.ui.screens.CommandCenterScreen
import com.example.ui.screens.FocusRoomScreen
import com.example.ui.screens.NotesScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.PersonalizationStudioScreen
import com.example.ui.screens.PrivateVaultScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StreamScreen
import com.example.ui.screens.StudyWorkspaceScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.NoteCircleViewModel
import com.example.ui.viewmodel.ScreenTab
import com.example.ui.viewmodel.SubScreen

class MainActivity : ComponentActivity() {

    private val viewModel: NoteCircleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
            val activeNotes by viewModel.activeNotes.collectAsStateWithLifecycle()
            val vaultNotes by viewModel.vaultNotes.collectAsStateWithLifecycle()
            val allCircles by viewModel.allCircles.collectAsStateWithLifecycle()
            val connections by viewModel.connections.collectAsStateWithLifecycle()
            val pendingRequests by viewModel.pendingRequests.collectAsStateWithLifecycle()
            val conversations by viewModel.conversations.collectAsStateWithLifecycle()
            val notifications by viewModel.notifications.collectAsStateWithLifecycle()
            val unreadNotifications by viewModel.unreadNotifications.collectAsStateWithLifecycle()
            val activeChatMessages by viewModel.activeChatMessages.collectAsStateWithLifecycle()
            val activeNoteReplies by viewModel.activeNoteReplies.collectAsStateWithLifecycle()

            val allRituals by viewModel.allRituals.collectAsStateWithLifecycle()
            val allPolls by viewModel.allPolls.collectAsStateWithLifecycle()
            val allCalendarEvents by viewModel.allCalendarEvents.collectAsStateWithLifecycle()
            val allQuests by viewModel.allQuests.collectAsStateWithLifecycle()
            val allFlashcards by viewModel.allFlashcards.collectAsStateWithLifecycle()

            MyApplicationTheme(darkTheme = uiState.isDarkTheme) {
                if (currentUser == null) {
                    // Onboarding / Privacy-First Registration Flow
                    AuthScreen(
                        onRegister = { email, username, displayName, bio, avatarColorHex ->
                            viewModel.register(email, username, displayName, bio, avatarColorHex)
                        }
                    )
                } else {
                    // Main App Shell with Header and 5-item Bottom Bar
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background,
                        topBar = {
                            if (uiState.activeSubScreen == null) {
                                NoteCircleHeader(
                                    currentUser = currentUser,
                                    unreadNotifications = unreadNotifications,
                                    onStatusClick = { viewModel.showAvailabilityDialog(true) },
                                    onMoodClick = { viewModel.showMoodGarden(true) },
                                    onFocusRoomClick = { viewModel.openFocusRoom() },
                                    onStudioClick = { viewModel.openSubScreen(SubScreen.STUDIO) },
                                    onCommandCenterClick = { viewModel.openSubScreen(SubScreen.COMMAND_CENTER) },
                                    onNotificationsClick = { viewModel.openSubScreen(SubScreen.NOTIFICATIONS) },
                                    onSettingsClick = { viewModel.openSubScreen(SubScreen.SETTINGS) }
                                )
                            }
                        },
                        bottomBar = {
                            if (uiState.activeSubScreen == null) {
                                NoteCircleBottomBar(
                                    currentTab = uiState.activeTab,
                                    onTabSelected = { tab -> viewModel.selectTab(tab) }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            when (uiState.activeSubScreen) {
                                SubScreen.SETTINGS -> {
                                    SettingsScreen(
                                        currentUser = currentUser,
                                        isDarkTheme = uiState.isDarkTheme,
                                        onToggleDarkTheme = { viewModel.toggleDarkTheme() },
                                        onUpdateProfile = { name, bio, color, emoji ->
                                            viewModel.updateProfile(name, bio, color, emoji)
                                        },
                                        onLogout = { viewModel.logout() },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.NOTIFICATIONS -> {
                                    NotificationsScreen(
                                        notifications = notifications,
                                        onMarkAllRead = { viewModel.markAllNotificationsRead() },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.CHAT_DETAIL -> {
                                    val activeConv = uiState.activeConversation
                                    if (activeConv != null) {
                                        ChatDetailScreen(
                                            conversation = activeConv,
                                            messages = activeChatMessages,
                                            currentUser = currentUser,
                                            messageInput = uiState.messageInput,
                                            onMessageInputChanged = { viewModel.updateMessageInput(it) },
                                            onSendMessage = { viewModel.sendMessage(it) },
                                            onBack = { viewModel.navigateBack() }
                                        )
                                    }
                                }
                                SubScreen.FOCUS_ROOM -> {
                                    FocusRoomScreen(
                                        currentUser = currentUser,
                                        minutesRemaining = uiState.focusMinutesRemaining,
                                        isTimerActive = uiState.isFocusTimerActive,
                                        activeSound = uiState.activeFocusSound,
                                        onToggleTimer = { viewModel.toggleFocusTimer() },
                                        onSetDuration = { viewModel.setFocusDuration(it) },
                                        onSetSound = { viewModel.setFocusSound(it) },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.STUDIO -> {
                                    PersonalizationStudioScreen(
                                        currentUser = currentUser,
                                        onSelectCompanion = { viewModel.setDigitalCompanion(it) },
                                        onSelectTheme = { viewModel.setLivingTheme(it) },
                                        onSelectMood = { viewModel.setMood(it) },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.VAULT -> {
                                    PrivateVaultScreen(
                                        isUnlocked = uiState.isVaultUnlocked,
                                        vaultNotes = vaultNotes,
                                        errorMessage = uiState.vaultErrorMessage,
                                        onUnlock = { viewModel.unlockVault(it) },
                                        onLock = { viewModel.lockVault() },
                                        onCreateVaultNote = { content ->
                                            viewModel.createNote(
                                                content = content,
                                                categoryEmoji = "🔒",
                                                categoryName = "Vault Note",
                                                audienceType = AudienceType.ONLY_ME,
                                                isVaultProtected = true
                                            )
                                        },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.WORKSPACE -> {
                                    val currentCircle = uiState.activeCircleForWorkspace ?: allCircles.firstOrNull()
                                    if (currentCircle != null) {
                                        CircleWorkspaceScreen(
                                            circle = currentCircle,
                                            rituals = allRituals,
                                            polls = allPolls,
                                            events = allCalendarEvents,
                                            quests = allQuests,
                                            onVotePoll = { pollId, opt -> viewModel.voteInPoll(pollId, opt) },
                                            onCreatePoll = { cId, q, op1, op2, op3 -> viewModel.createPoll(cId, q, op1, op2, op3) },
                                            onCreateEvent = { cId, t, d, time, loc -> viewModel.createCalendarEvent(cId, t, d, time, loc) },
                                            onCompleteQuest = { qId -> viewModel.completeQuest(qId) },
                                            onBack = { viewModel.navigateBack() }
                                        )
                                    }
                                }
                                SubScreen.STUDY -> {
                                    StudyWorkspaceScreen(
                                        flashcards = allFlashcards,
                                        onCreateFlashcard = { subj, prompt, ans -> viewModel.createFlashcard(subj, prompt, ans) },
                                        onToggleMastered = { id, mastered -> viewModel.toggleFlashcard(id, mastered) },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.COMMAND_CENTER -> {
                                    CommandCenterScreen(
                                        currentUser = currentUser,
                                        notes = activeNotes,
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                SubScreen.AI_ASSIST -> {
                                    AiCompanionScreen(
                                        notes = activeNotes,
                                        promptInput = uiState.aiPromptInput,
                                        generatedResult = uiState.aiGeneratedResult,
                                        isProcessing = uiState.isAiProcessing,
                                        onPromptChanged = { viewModel.updateAiPrompt(it) },
                                        onGenerate = { action, content -> viewModel.generateAiAssistance(action, content) },
                                        onClearResult = { viewModel.clearAiResult() },
                                        onBack = { viewModel.navigateBack() }
                                    )
                                }
                                null -> {
                                    when (uiState.activeTab) {
                                        ScreenTab.STREAM -> {
                                            StreamScreen(
                                                currentUser = currentUser,
                                                notes = activeNotes,
                                                circles = allCircles,
                                                selectedFilter = uiState.selectedCircleFilter,
                                                onFilterSelected = { viewModel.setCircleFilter(it) },
                                                onOpenMoodGarden = { viewModel.showMoodGarden(true) },
                                                onSelectMood = { viewModel.setMood(it) },
                                                onOpenThoughtBubble = { viewModel.showThoughtBubbleDialog(true) },
                                                onOpenCommandCenter = { viewModel.openSubScreen(SubScreen.COMMAND_CENTER) },
                                                onOpenAiCompanion = { viewModel.openSubScreen(SubScreen.AI_ASSIST) },
                                                onOpenStudyWorkspace = { viewModel.openSubScreen(SubScreen.STUDY) },
                                                onOpenVault = { viewModel.openSubScreen(SubScreen.VAULT) },
                                                gratitudeInput = uiState.gratitudeInput,
                                                onGratitudeInputChanged = { viewModel.updateGratitudeInput(it) },
                                                onPostGratitude = { viewModel.postGratitudeRitual() },
                                                isRitualAnswered = uiState.isRitualAnsweredToday,
                                                onReactToNote = { noteId, emoji -> viewModel.reactToNote(noteId, emoji) },
                                                onOpenReplies = { note -> viewModel.openNoteReplies(note) },
                                                onCreateNoteClick = { viewModel.showCreateNoteDialog(true) },
                                                selectedNoteForReplies = uiState.selectedNoteForReplies,
                                                replies = activeNoteReplies,
                                                replyInput = uiState.replyInput,
                                                onReplyInputChanged = { viewModel.updateReplyInput(it) },
                                                onAddReply = { noteId, text -> viewModel.addReply(noteId, text) },
                                                onCloseReplies = { viewModel.closeNoteReplies() }
                                            )
                                        }
                                        ScreenTab.NOTES -> {
                                            NotesScreen(
                                                currentUser = currentUser,
                                                allNotes = activeNotes,
                                                selectedTab = uiState.selectedNoteTab,
                                                onTabSelected = { viewModel.setNoteTab(it) },
                                                onTogglePin = { noteId, pinned -> viewModel.togglePinNote(noteId, pinned) },
                                                onDeleteNote = { noteId -> viewModel.deleteNote(noteId) },
                                                onCreateNoteClick = { viewModel.showCreateNoteDialog(true) }
                                            )
                                        }
                                        ScreenTab.CREATE -> {
                                            // Triggered via center bottom bar button; modal opened
                                        }
                                        ScreenTab.CHAT -> {
                                            ChatListScreen(
                                                conversations = conversations,
                                                onConversationClick = { conv -> viewModel.openConversation(conv) },
                                                onGoToCircleClick = { viewModel.selectTab(ScreenTab.CIRCLE) }
                                            )
                                        }
                                        ScreenTab.CIRCLE -> {
                                            CircleScreen(
                                                circles = allCircles,
                                                connections = connections,
                                                pendingRequests = pendingRequests,
                                                searchQuery = uiState.searchQuery,
                                                searchResults = uiState.searchResults,
                                                onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
                                                onSendRequest = { viewModel.sendConnectionRequest(it) },
                                                onAcceptRequest = { viewModel.acceptConnection(it) },
                                                onRejectRequest = { viewModel.rejectConnection(it) },
                                                onToggleCloseFriend = { userId, isClose -> viewModel.toggleCloseFriend(userId, isClose) },
                                                onStartChat = { user -> viewModel.startChatWithConnection(user) },
                                                isAddCircleOpen = uiState.isAddCircleOpen,
                                                onShowAddCircle = { viewModel.showAddCircleDialog(it) },
                                                onCreateCircle = { name, desc, emoji, color ->
                                                    viewModel.createCircle(name, desc, emoji, color)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Availability Dialog Modal
                        if (uiState.isAvailabilityDialogOpen) {
                            AvailabilityDialog(
                                currentStatus = currentUser?.availabilityStatus ?: "AVAILABLE",
                                onStatusSelected = { status -> viewModel.setAvailability(status) },
                                onDismiss = { viewModel.showAvailabilityDialog(false) }
                            )
                        }

                        // Mood Garden Dialog Modal
                        if (uiState.isMoodGardenOpen) {
                            MoodGardenDialog(
                                currentMood = currentUser?.moodStatus ?: "BLOOMING",
                                onMoodSelected = { mood -> viewModel.setMood(mood) },
                                onDismiss = { viewModel.showMoodGarden(false) }
                            )
                        }

                        // FEATURE 8: Thought Bubble Dialog
                        if (uiState.isThoughtBubbleDialogOpen) {
                            ThoughtBubbleDialog(
                                initialText = uiState.thoughtBubbleInput,
                                onSave = { bubble ->
                                    viewModel.updateThoughtBubbleInput(bubble)
                                    viewModel.saveThoughtBubble()
                                },
                                onDismiss = { viewModel.showThoughtBubbleDialog(false) }
                            )
                        }

                        // Create Note Modal Sheet with Privacy Preview & Memory Capsule
                        if (uiState.isCreateNoteOpen) {
                            CreateNoteSheet(
                                circles = allCircles,
                                onPostNote = { content, catEmoji, catName, audience, circleId, circleName, expiry, pinned, isCapsule, unlockDate ->
                                    viewModel.createNote(
                                        content = content,
                                        categoryEmoji = catEmoji,
                                        categoryName = catName,
                                        audienceType = audience,
                                        targetCircleId = circleId,
                                        targetCircleName = circleName,
                                        expiry = expiry,
                                        isPinned = pinned,
                                        isMemoryCapsule = isCapsule,
                                        unlockDate = unlockDate
                                    )
                                },
                                onDismiss = { viewModel.showCreateNoteDialog(false) }
                            )
                        }
                    }
                }
            }
        }
    }
}
