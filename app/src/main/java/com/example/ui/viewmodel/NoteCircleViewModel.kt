package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.NoteCircleDatabase
import com.example.data.local.entity.CalendarEventEntity
import com.example.data.local.entity.CircleEntity
import com.example.data.local.entity.CircleRitualEntity
import com.example.data.local.entity.ConversationEntity
import com.example.data.local.entity.DecisionPollEntity
import com.example.data.local.entity.FriendshipQuestEntity
import com.example.data.local.entity.MessageEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.NoteReplyEntity
import com.example.data.local.entity.NotificationEntity
import com.example.data.local.entity.StudyFlashcardEntity
import com.example.data.local.entity.UserEntity
import com.example.data.model.AudienceType
import com.example.data.model.AvailabilityStatus
import com.example.data.model.DigitalCompanion
import com.example.data.model.LivingTheme
import com.example.data.model.MoodGarden
import com.example.data.model.NoteExpiry
import com.example.data.repository.NoteCircleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ScreenTab {
    STREAM,
    NOTES,
    CREATE,
    CHAT,
    CIRCLE
}

enum class SubScreen {
    SETTINGS,
    CHAT_DETAIL,
    NOTIFICATIONS,
    FOCUS_ROOM,
    STUDIO,
    VAULT,
    WORKSPACE,
    STUDY,
    COMMAND_CENTER,
    AI_ASSIST
}

enum class NoteSubTab {
    ACTIVE,
    PINNED,
    CAPSULES,
    JOURNAL,
    GRATITUDE,
    DRAFTS,
    HISTORY
}

data class NoteCircleUiState(
    val currentUser: UserEntity? = null,
    val activeTab: ScreenTab = ScreenTab.STREAM,
    val activeSubScreen: SubScreen? = null,
    val selectedCircleFilter: String? = null, // null = All, "CLOSE_FRIENDS", or circleId
    val selectedNoteTab: NoteSubTab = NoteSubTab.ACTIVE,
    val isAvailabilityDialogOpen: Boolean = false,
    val isMoodGardenOpen: Boolean = false,
    val isCreateNoteOpen: Boolean = false,
    val isAddCircleOpen: Boolean = false,
    val selectedNoteForReplies: NoteEntity? = null,
    val activeConversation: ConversationEntity? = null,
    val activeCircleForWorkspace: CircleEntity? = null,
    val searchQuery: String = "",
    val searchResults: List<UserEntity> = emptyList(),
    val searchedNotes: List<NoteEntity> = emptyList(),
    val isDarkTheme: Boolean = false,
    val messageInput: String = "",
    val replyInput: String = "",
    // Cozy Focus Room state
    val focusMinutesRemaining: Int = 25,
    val isFocusTimerActive: Boolean = false,
    val activeFocusSound: String = "Rainy Cafe ☕",
    // Daily Circle Ritual & Gratitude
    val gratitudeInput: String = "",
    val isRitualAnsweredToday: Boolean = false,
    // Private Vault state
    val isVaultUnlocked: Boolean = false,
    val vaultPinInput: String = "",
    val vaultErrorMessage: String? = null,
    // Thought Bubble
    val isThoughtBubbleDialogOpen: Boolean = false,
    val thoughtBubbleInput: String = "",
    // AI Assistant state
    val aiPromptInput: String = "",
    val aiGeneratedResult: String? = null,
    val isAiProcessing: Boolean = false,
    // Tag / Category filter for notes
    val selectedTagFilter: String? = null,
    // Supabase Cloud Sync state
    val cloudSyncState: com.example.data.supabase.CloudSyncState = com.example.data.supabase.CloudSyncState.IDLE
)

class NoteCircleViewModel(application: Application) : AndroidViewModel(application) {

    private val db = NoteCircleDatabase.getDatabase(application)
    private val repository = NoteCircleRepository(db)
    private val themePreferences = com.example.data.local.ThemePreferences(application)
    private val supabaseSyncManager = com.example.data.supabase.SupabaseSyncManager()

    private val _uiState = MutableStateFlow(NoteCircleUiState())
    val uiState: StateFlow<NoteCircleUiState> = _uiState.asStateFlow()

    init {
        // Collect saved theme from DataStore preferences
        viewModelScope.launch {
            themePreferences.isDarkTheme.collect { isDark ->
                _uiState.update { it.copy(isDarkTheme = isDark) }
            }
        }
        // Collect Supabase cloud sync status
        viewModelScope.launch {
            supabaseSyncManager.syncState.collect { syncState ->
                _uiState.update { it.copy(cloudSyncState = syncState) }
            }
        }
    }

    val currentUser: StateFlow<UserEntity?> = repository.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val activeNotes: StateFlow<List<NoteEntity>> = repository.activeNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vaultNotes: StateFlow<List<NoteEntity>> = repository.vaultNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCircles: StateFlow<List<CircleEntity>> = repository.allCircles
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val connections: StateFlow<List<UserEntity>> = repository.connections
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val pendingRequests: StateFlow<List<UserEntity>> = repository.pendingRequests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val conversations: StateFlow<List<ConversationEntity>> = repository.conversations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = repository.notifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotifications: StateFlow<Int> = repository.unreadNotificationCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val allRituals: StateFlow<List<CircleRitualEntity>> = repository.allRituals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allPolls: StateFlow<List<DecisionPollEntity>> = repository.allPolls
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCalendarEvents: StateFlow<List<CalendarEventEntity>> = repository.allCalendarEvents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allQuests: StateFlow<List<FriendshipQuestEntity>> = repository.allQuests
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFlashcards: StateFlow<List<StudyFlashcardEntity>> = repository.allFlashcards
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeChatMessages = MutableStateFlow<List<MessageEntity>>(emptyList())
    val activeChatMessages: StateFlow<List<MessageEntity>> = _activeChatMessages.asStateFlow()

    private val _activeNoteReplies = MutableStateFlow<List<NoteReplyEntity>>(emptyList())
    val activeNoteReplies: StateFlow<List<NoteReplyEntity>> = _activeNoteReplies.asStateFlow()

    // Navigation & Screen selection
    fun selectTab(tab: ScreenTab) {
        if (tab == ScreenTab.CREATE) {
            _uiState.update { it.copy(isCreateNoteOpen = true) }
        } else {
            _uiState.update { it.copy(activeTab = tab, activeSubScreen = null) }
        }
    }

    fun openSubScreen(subScreen: SubScreen) {
        _uiState.update { it.copy(activeSubScreen = subScreen) }
    }

    fun openCircleWorkspace(circle: CircleEntity) {
        _uiState.update { it.copy(activeSubScreen = SubScreen.WORKSPACE, activeCircleForWorkspace = circle) }
    }

    fun navigateBack(): Boolean {
        if (_uiState.value.isCreateNoteOpen) {
            _uiState.update { it.copy(isCreateNoteOpen = false) }
            return true
        }
        if (_uiState.value.selectedNoteForReplies != null) {
            _uiState.update { it.copy(selectedNoteForReplies = null) }
            return true
        }
        if (_uiState.value.isAvailabilityDialogOpen) {
            _uiState.update { it.copy(isAvailabilityDialogOpen = false) }
            return true
        }
        if (_uiState.value.isMoodGardenOpen) {
            _uiState.update { it.copy(isMoodGardenOpen = false) }
            return true
        }
        if (_uiState.value.isThoughtBubbleDialogOpen) {
            _uiState.update { it.copy(isThoughtBubbleDialogOpen = false) }
            return true
        }
        if (_uiState.value.isAddCircleOpen) {
            _uiState.update { it.copy(isAddCircleOpen = false) }
            return true
        }
        if (_uiState.value.activeSubScreen != null) {
            _uiState.update { it.copy(activeSubScreen = null, activeConversation = null, activeCircleForWorkspace = null) }
            return true
        }
        return false
    }

    // Auth & Registration
    fun login(
        identifier: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val user = repository.login(identifier, password)
                if (user != null) {
                    onResult(true, null)
                } else {
                    onResult(false, "Invalid username/email or password. Please verify your credentials or create a new account.")
                }
            } catch (e: Exception) {
                onResult(false, e.localizedMessage ?: "Login failed. Please try again.")
            }
        }
    }

    fun register(
        email: String,
        username: String,
        displayName: String,
        bio: String,
        avatarColorHex: String,
        password: String = "password123",
        onResult: (Boolean, String?) -> Unit = { _, _ -> }
    ) {
        viewModelScope.launch {
            try {
                val user = repository.registerUser(email, username, displayName, bio, avatarColorHex, password)
                supabaseSyncManager.syncUserToSupabase(user)
                onResult(true, null)
            } catch (e: IllegalArgumentException) {
                onResult(false, e.message ?: "An account with this email already exists.")
            } catch (e: Exception) {
                onResult(false, e.localizedMessage ?: "Registration failed. Please try again.")
            }
        }
    }

    fun updateProfile(displayName: String, bio: String, avatarColorHex: String, statusEmoji: String) {
        viewModelScope.launch {
            repository.updateProfile(displayName, bio, avatarColorHex, statusEmoji)
        }
    }

    fun setAvailability(status: AvailabilityStatus) {
        viewModelScope.launch {
            repository.updateAvailability(status)
            _uiState.update { it.copy(isAvailabilityDialogOpen = false) }
        }
    }

    fun showAvailabilityDialog(show: Boolean) {
        _uiState.update { it.copy(isAvailabilityDialogOpen = show) }
    }

    // Mood Garden & Thought Bubble
    fun showMoodGarden(show: Boolean) {
        _uiState.update { it.copy(isMoodGardenOpen = show) }
    }

    fun setMood(mood: MoodGarden) {
        viewModelScope.launch {
            repository.updateMood(mood)
            _uiState.update { it.copy(isMoodGardenOpen = false) }
        }
    }

    fun showThoughtBubbleDialog(show: Boolean) {
        _uiState.update { it.copy(isThoughtBubbleDialogOpen = show, thoughtBubbleInput = currentUser.value?.thoughtBubble ?: "") }
    }

    fun updateThoughtBubbleInput(text: String) {
        _uiState.update { it.copy(thoughtBubbleInput = text) }
    }

    fun saveThoughtBubble() {
        val bubble = _uiState.value.thoughtBubbleInput
        viewModelScope.launch {
            repository.updateThoughtBubble(bubble)
            _uiState.update { it.copy(isThoughtBubbleDialogOpen = false) }
        }
    }

    // Digital Companion & Living Themes (Personalization Studio)
    fun setDigitalCompanion(companion: DigitalCompanion) {
        viewModelScope.launch {
            repository.updateCompanion(companion.petName, companion.emoji)
        }
    }

    fun setLivingTheme(theme: LivingTheme) {
        viewModelScope.launch {
            repository.updateTheme(theme.name)
            if (theme.isDark != _uiState.value.isDarkTheme) {
                _uiState.update { it.copy(isDarkTheme = theme.isDark) }
            }
        }
    }

    // Private Vault
    fun unlockVault(enteredPin: String) {
        val actualPin = currentUser.value?.vaultPin ?: "1234"
        if (enteredPin == actualPin) {
            _uiState.update { it.copy(isVaultUnlocked = true, vaultErrorMessage = null, vaultPinInput = "") }
        } else {
            _uiState.update { it.copy(vaultErrorMessage = "Incorrect PIN. Try again.") }
        }
    }

    fun lockVault() {
        _uiState.update { it.copy(isVaultUnlocked = false, vaultPinInput = "") }
    }

    fun setVaultPin(newPin: String) {
        viewModelScope.launch {
            repository.setVaultSecurity(newPin, false)
            _uiState.update { it.copy(isVaultUnlocked = true, vaultErrorMessage = null, vaultPinInput = "") }
        }
    }

    // Cozy Focus Room
    fun openFocusRoom() {
        _uiState.update { it.copy(activeSubScreen = SubScreen.FOCUS_ROOM) }
    }

    fun toggleFocusTimer() {
        _uiState.update { it.copy(isFocusTimerActive = !it.isFocusTimerActive) }
    }

    fun setFocusDuration(minutes: Int) {
        _uiState.update { it.copy(focusMinutesRemaining = minutes, isFocusTimerActive = false) }
    }

    fun setFocusSound(sound: String) {
        _uiState.update { it.copy(activeFocusSound = sound) }
    }

    // Daily Circle Ritual & Gratitude
    fun updateGratitudeInput(text: String) {
        _uiState.update { it.copy(gratitudeInput = text) }
    }

    fun postGratitudeRitual() {
        val text = _uiState.value.gratitudeInput
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.createNote(
                content = "🌸 Gratitude Ritual: $text",
                categoryEmoji = "🌸",
                categoryName = "Gratitude",
                audienceType = AudienceType.EVERYONE_IN_CIRCLE,
                expiry = NoteExpiry.ONE_DAY,
                isRitual = true,
                noteType = "GRATITUDE"
            )
            repository.incrementGrowthFlower()
            _uiState.update { it.copy(gratitudeInput = "", isRitualAnsweredToday = true) }
        }
    }

    // Circle Workspace Actions (Quests, Polls, Events)
    fun voteInPoll(pollId: String, optionIndex: Int) {
        viewModelScope.launch {
            repository.votePoll(pollId, optionIndex)
        }
    }

    fun createPoll(circleId: String, question: String, op1: String, op2: String, op3: String) {
        viewModelScope.launch {
            repository.createPoll(circleId, question, op1, op2, op3)
        }
    }

    fun createCalendarEvent(circleId: String, title: String, date: String, time: String, location: String) {
        viewModelScope.launch {
            repository.createCalendarEvent(circleId, title, date, time, location)
        }
    }

    fun completeQuest(questId: String) {
        viewModelScope.launch {
            repository.completeQuest(questId)
        }
    }

    // Study Workspace
    fun createFlashcard(subject: String, prompt: String, answer: String) {
        viewModelScope.launch {
            repository.createFlashcard(subject, prompt, answer)
        }
    }

    fun toggleFlashcard(cardId: String, isMastered: Boolean) {
        viewModelScope.launch {
            repository.toggleFlashcard(cardId, isMastered)
        }
    }

    // AI Companion Logic (Feature 31, 33, 34)
    fun updateAiPrompt(text: String) {
        _uiState.update { it.copy(aiPromptInput = text) }
    }

    fun generateAiAssistance(actionType: String, content: String) {
        _uiState.update { it.copy(isAiProcessing = true) }
        viewModelScope.launch {
            kotlinx.coroutines.delay(600) // gentle simulation delay
            val result = when (actionType) {
                "POLISH" -> "✨ Polished for warmth & clarity:\n\n${content.trim()}\n\n— Keep gently expressing your authentic thoughts!"
                "SUMMARIZE" -> "📋 Key Takeaways:\n• ${content.take(80)}...\n• Core theme: Thoughtful personal reflection\n• Action: Stay present and rested."
                "SUGGEST_TAGS" -> "🏷️ Suggested Tags: #gentle #mindfulness #cozy #reflection"
                "CONVERT_ACTION" -> "✅ Draft Checklist Task: [ ] ${content.take(60)}"
                else -> "🌸 NoteCircle AI Companion: Here to assist your private notes safely on-device."
            }
            _uiState.update { it.copy(aiGeneratedResult = result, isAiProcessing = false) }
        }
    }

    fun clearAiResult() {
        _uiState.update { it.copy(aiGeneratedResult = null, aiPromptInput = "") }
    }

    fun showCreateNoteDialog(show: Boolean) {
        _uiState.update { it.copy(isCreateNoteOpen = show) }
    }

    fun showAddCircleDialog(show: Boolean) {
        _uiState.update { it.copy(isAddCircleOpen = show) }
    }

    fun setCircleFilter(filter: String?) {
        _uiState.update { it.copy(selectedCircleFilter = filter) }
    }

    fun setNoteTab(tab: NoteSubTab) {
        _uiState.update { it.copy(selectedNoteTab = tab) }
    }

    fun setTagFilter(tag: String?) {
        _uiState.update { it.copy(selectedTagFilter = tag) }
    }

    fun toggleDarkTheme() {
        val next = !_uiState.value.isDarkTheme
        viewModelScope.launch {
            themePreferences.setDarkTheme(next)
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            themePreferences.setDarkTheme(isDark)
        }
    }

    // Notes Actions
    fun createNote(
        content: String,
        categoryEmoji: String,
        categoryName: String,
        audienceType: AudienceType,
        targetCircleId: String? = null,
        targetCircleName: String? = null,
        expiry: NoteExpiry = NoteExpiry.ONE_DAY,
        isPinned: Boolean = false,
        isDraft: Boolean = false,
        isMemoryCapsule: Boolean = false,
        unlockDate: Long? = null,
        moodTag: String? = null,
        noteType: String = "STANDARD",
        tags: String = "",
        isVaultProtected: Boolean = false,
        audioDurationSec: Int = 0,
        audioTranscript: String? = null
    ) {
        viewModelScope.launch {
            val note = repository.createNote(
                content = content,
                categoryEmoji = categoryEmoji,
                categoryName = categoryName,
                audienceType = audienceType,
                targetCircleId = targetCircleId,
                targetCircleName = targetCircleName,
                expiry = expiry,
                isPinned = isPinned,
                isDraft = isDraft,
                isMemoryCapsule = isMemoryCapsule,
                unlockDate = unlockDate,
                moodTag = moodTag,
                noteType = noteType,
                tags = tags,
                isVaultProtected = isVaultProtected,
                audioDurationSec = audioDurationSec,
                audioTranscript = audioTranscript
            )
            if (note != null) {
                supabaseSyncManager.syncNoteToSupabase(note)
            }
            repository.incrementGrowthFlower()
            _uiState.update { it.copy(isCreateNoteOpen = false) }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun togglePinNote(noteId: String, currentPinned: Boolean) {
        viewModelScope.launch {
            repository.togglePinNote(noteId, currentPinned)
        }
    }

    fun reactToNote(noteId: String, emoji: String) {
        viewModelScope.launch {
            repository.reactToNote(noteId, emoji)
        }
    }

    fun openNoteReplies(note: NoteEntity) {
        _uiState.update { it.copy(selectedNoteForReplies = note) }
        viewModelScope.launch {
            repository.getRepliesForNote(note.noteId).collect { replies ->
                _activeNoteReplies.value = replies
            }
        }
    }

    fun closeNoteReplies() {
        _uiState.update { it.copy(selectedNoteForReplies = null) }
    }

    fun addReply(noteId: String, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.addReply(noteId, content)
            _uiState.update { it.copy(replyInput = "") }
        }
    }

    fun updateReplyInput(text: String) {
        _uiState.update { it.copy(replyInput = text) }
    }

    // Circles Actions
    fun createCircle(name: String, description: String, emoji: String, colorHex: String) {
        viewModelScope.launch {
            val circle = repository.createCircle(name, description, emoji, colorHex)
            supabaseSyncManager.syncCircleToSupabase(circle)
            _uiState.update { it.copy(isAddCircleOpen = false) }
        }
    }

    fun deleteCircle(circleId: String) {
        viewModelScope.launch {
            repository.deleteCircle(circleId)
        }
    }

    // Chat Actions
    fun openConversation(conversation: ConversationEntity) {
        _uiState.update { it.copy(activeConversation = conversation, activeSubScreen = SubScreen.CHAT_DETAIL) }
        viewModelScope.launch {
            repository.markConversationAsRead(conversation.conversationId)
            repository.getMessages(conversation.conversationId).collect { messages ->
                _activeChatMessages.value = messages
            }
        }
    }

    fun startChatWithConnection(user: UserEntity) {
        viewModelScope.launch {
            val conv = repository.startConversationWithUser(user)
            openConversation(conv)
        }
    }

    fun sendMessage(text: String) {
        val conv = _uiState.value.activeConversation ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendMessage(conv.conversationId, conv.participantId, text)
            _uiState.update { it.copy(messageInput = "") }
        }
    }

    fun updateMessageInput(text: String) {
        _uiState.update { it.copy(messageInput = text) }
    }

    fun bookmarkMessage(messageId: String, isBookmarked: Boolean, collection: String?) {
        viewModelScope.launch {
            repository.toggleBookmarkMessage(messageId, isBookmarked, collection)
        }
    }

    // Connections & Search
    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.isBlank()) {
            _uiState.update { it.copy(searchResults = emptyList(), searchedNotes = emptyList()) }
        } else {
            viewModelScope.launch {
                repository.searchUsers(query).collect { results ->
                    _uiState.update { it.copy(searchResults = results) }
                }
            }
            viewModelScope.launch {
                repository.searchNotes(query).collect { notes ->
                    _uiState.update { it.copy(searchedNotes = notes) }
                }
            }
        }
    }

    fun sendConnectionRequest(usernameOrEmail: String) {
        viewModelScope.launch {
            repository.sendConnectionRequest(usernameOrEmail)
            onSearchQueryChanged("")
        }
    }

    fun acceptConnection(userId: String) {
        viewModelScope.launch {
            repository.acceptConnection(userId)
        }
    }

    fun rejectConnection(userId: String) {
        viewModelScope.launch {
            repository.rejectConnection(userId)
        }
    }

    fun toggleCloseFriend(userId: String, isClose: Boolean) {
        viewModelScope.launch {
            repository.toggleCloseFriend(userId, isClose)
        }
    }

    // Notifications
    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _uiState.update { NoteCircleUiState() }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            repository.deleteAccount()
            _uiState.update { NoteCircleUiState() }
        }
    }
}
