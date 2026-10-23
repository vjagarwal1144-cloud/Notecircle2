package com.example.data.repository

import com.example.data.crypto.E2EEEngine
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
import com.example.data.model.ConnectionStatus
import com.example.data.model.MoodGarden
import com.example.data.model.NoteExpiry
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class NoteCircleRepository(private val db: NoteCircleDatabase) {

    private val userDao = db.userDao()
    private val circleDao = db.circleDao()
    private val noteDao = db.noteDao()
    private val noteReplyDao = db.noteReplyDao()
    private val conversationDao = db.conversationDao()
    private val messageDao = db.messageDao()
    private val notificationDao = db.notificationDao()
    private val circleRitualDao = db.circleRitualDao()
    private val decisionPollDao = db.decisionPollDao()
    private val calendarEventDao = db.calendarEventDao()
    private val friendshipQuestDao = db.friendshipQuestDao()
    private val studyFlashcardDao = db.studyFlashcardDao()

    // Current User & Profile
    val currentUser: Flow<UserEntity?> = userDao.getCurrentUser()
    val connections: Flow<List<UserEntity>> = userDao.getConnections()
    val pendingRequests: Flow<List<UserEntity>> = userDao.getPendingRequests()

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getUserByEmail(email.trim().lowercase())
    }

    suspend fun login(identifier: String, password: String): UserEntity? {
        val cleanIdentifier = identifier.trim()
        val usernameIdentifier = if (cleanIdentifier.startsWith("@")) cleanIdentifier.lowercase() else "@${cleanIdentifier.lowercase()}"
        val emailIdentifier = cleanIdentifier.lowercase()

        // Search by email first, then username
        var user = userDao.login(emailIdentifier, password)
        if (user == null) {
            user = userDao.login(cleanIdentifier, password)
        }
        if (user == null) {
            user = userDao.login(usernameIdentifier, password)
        }

        if (user != null) {
            userDao.clearCurrentUser()
            userDao.setCurrentUser(user.userId)
            return user.copy(isCurrentUser = true)
        }
        return null
    }

    suspend fun registerUser(
        email: String,
        username: String,
        displayName: String,
        bio: String,
        avatarColorHex: String,
        password: String = "password123"
    ): UserEntity {
        val cleanEmail = email.trim().lowercase()
        val existing = userDao.getUserByEmail(cleanEmail)
        if (existing != null) {
            throw IllegalArgumentException("An account already exists for this email ($cleanEmail). Please log in with your password!")
        }

        // Clear any previous active user session
        userDao.clearCurrentUser()

        val cleanUsername = if (username.startsWith("@")) username.lowercase() else "@${username.lowercase()}"
        val userId = UUID.randomUUID().toString()
        val user = UserEntity(
            userId = userId,
            email = cleanEmail,
            username = cleanUsername.trim(),
            password = password,
            displayName = displayName.trim(),
            bio = bio.trim(),
            avatarColorHex = avatarColorHex,
            statusEmoji = "🌿",
            availabilityStatus = AvailabilityStatus.AVAILABLE.name,
            moodStatus = "BLOOMING",
            moodEmoji = "🌸",
            thoughtBubble = "Just arrived in NoteCircle ✨",
            thoughtBubbleTimestamp = System.currentTimeMillis(),
            companionName = "Mochi",
            companionEmoji = "🐱",
            activeTheme = "STRAWBERRY_MILK",
            isCurrentUser = true,
            connectionStatus = ConnectionStatus.CONNECTED.name,
            isCloseFriend = false,
            safetyNumber = E2EEEngine.generateSafetyNumber(userId, "server-auth-root")
        )
        userDao.insertUser(user)

        // Create default personal circles
        val closeFriendsCircle = CircleEntity(
            circleId = UUID.randomUUID().toString(),
            name = "Close Friends",
            description = "My inner circle for spontaneous thoughts & updates",
            emoji = "⭐️",
            colorHex = "#FFE2E8",
            memberCount = 1
        )
        val familyCircle = CircleEntity(
            circleId = UUID.randomUUID().toString(),
            name = "Family Circle",
            description = "Private home updates and dinner plans",
            emoji = "🏡",
            colorHex = "#DDF7E8",
            memberCount = 1
        )
        val studyCircle = CircleEntity(
            circleId = UUID.randomUUID().toString(),
            name = "Study Lounge",
            description = "Quiet collaborative learning and focus sessions",
            emoji = "📚",
            colorHex = "#E9E2FF",
            memberCount = 1
        )
        circleDao.insertCircle(closeFriendsCircle)
        circleDao.insertCircle(familyCircle)
        circleDao.insertCircle(studyCircle)

        // Seed sample default Friendship Quests
        friendshipQuestDao.insertQuest(
            FriendshipQuestEntity(
                questId = UUID.randomUUID().toString(),
                title = "Send a Voice Postcard 🎙️",
                description = "Record a gentle voice message or whisper note for a trusted connection.",
                rewardBadge = "🌸 Whispering Wind"
            )
        )
        friendshipQuestDao.insertQuest(
            FriendshipQuestEntity(
                questId = UUID.randomUUID().toString(),
                title = "Plant a Daily Ritual 🍵",
                description = "Share one cozy gratitude moment in the Circle Rituals tab.",
                rewardBadge = "✨ Gratitude Keeper"
            )
        )
        friendshipQuestDao.insertQuest(
            FriendshipQuestEntity(
                questId = UUID.randomUUID().toString(),
                title = "25m Cozy Focus Session ☕",
                description = "Complete a focus session in the Cozy Focus Room with Rain sounds.",
                rewardBadge = "🌿 Mindful Blossom"
            )
        )

        // Seed sample Calendar Event
        calendarEventDao.insertEvent(
            CalendarEventEntity(
                eventId = UUID.randomUUID().toString(),
                circleId = closeFriendsCircle.circleId,
                title = "Weekend Tea & Chill 🍵",
                dateText = "Saturday, Oct 25",
                timeText = "4:00 PM",
                emoji = "🫖",
                location = "Cozy Corner Cafe"
            )
        )

        // Seed sample Decision Poll
        decisionPollDao.insertPoll(
            DecisionPollEntity(
                pollId = UUID.randomUUID().toString(),
                circleId = closeFriendsCircle.circleId,
                question = "Where should we have Saturday coffee? ☕",
                authorName = displayName,
                option1 = "Cozy Corner Cafe 🌿",
                option2 = "Matcha Garden Lounge 🍵",
                option3 = "Boba Bakery 🍰",
                votes1 = 2,
                votes2 = 1,
                votes3 = 3
            )
        )

        // Seed sample Study Flashcards
        studyFlashcardDao.insertFlashcard(
            StudyFlashcardEntity(
                cardId = UUID.randomUUID().toString(),
                subject = "Mindfulness",
                prompt = "What is the core principle of 'Presence Without Pressure'?",
                answer = "Sharing genuine moments without artificial streaks, follower counts, or public engagement anxiety."
            )
        )

        // Insert welcome security notification
        notificationDao.insertNotification(
            NotificationEntity(
                id = UUID.randomUUID().toString(),
                title = "Welcome to NoteCircle",
                body = "Your private notebook, cute companion, and encrypted chat are ready. Private by default.",
                type = "SECURITY_ALERT"
            )
        )

        return user
    }

    suspend fun updateProfile(
        displayName: String,
        bio: String,
        avatarColorHex: String,
        statusEmoji: String
    ) {
        val current = userDao.getCurrentUserSync() ?: return
        val updated = current.copy(
            displayName = displayName.trim(),
            bio = bio.trim(),
            avatarColorHex = avatarColorHex,
            statusEmoji = statusEmoji
        )
        userDao.updateUser(updated)
    }

    suspend fun updateAvailability(status: AvailabilityStatus) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setAvailability(current.userId, status.name, status.emoji)
    }

    suspend fun updateMood(mood: MoodGarden) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setMood(current.userId, mood.name, mood.emoji)
    }

    suspend fun updateThoughtBubble(bubble: String) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setThoughtBubble(current.userId, bubble.trim(), System.currentTimeMillis())
    }

    suspend fun updateCompanion(name: String, emoji: String) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setCompanion(current.userId, name, emoji)
    }

    suspend fun updateTheme(themeName: String) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setTheme(current.userId, themeName)
    }

    suspend fun setVaultSecurity(pin: String, locked: Boolean) {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.setVaultSecurity(current.userId, pin, locked)
    }

    suspend fun incrementGrowthFlower() {
        val current = userDao.getCurrentUserSync() ?: return
        userDao.incrementGrowthFlower(current.userId)
    }

    // Circles
    val allCircles: Flow<List<CircleEntity>> = circleDao.getAllCircles()

    suspend fun createCircle(name: String, description: String, emoji: String, colorHex: String): CircleEntity {
        val circle = CircleEntity(
            circleId = UUID.randomUUID().toString(),
            name = name.trim(),
            description = description.trim(),
            emoji = emoji,
            colorHex = colorHex,
            memberCount = 1
        )
        circleDao.insertCircle(circle)
        return circle
    }

    suspend fun deleteCircle(circleId: String) {
        circleDao.deleteCircle(circleId)
    }

    // Notes
    val activeNotes: Flow<List<NoteEntity>> = noteDao.getActiveNotes()
    val vaultNotes: Flow<List<NoteEntity>> = noteDao.getVaultNotes()
    val draftNotes: Flow<List<NoteEntity>> = noteDao.getDraftNotes()

    fun getUserNotes(authorId: String): Flow<List<NoteEntity>> = noteDao.getUserNotes(authorId)

    suspend fun createNote(
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
        isRitual: Boolean = false,
        noteType: String = "STANDARD",
        tags: String = "",
        isVaultProtected: Boolean = false,
        audioDurationSec: Int = 0,
        audioTranscript: String? = null,
        collaborators: String = "",
        reminderTimestamp: Long? = null
    ): NoteEntity? {
        val current = userDao.getCurrentUserSync() ?: return null
        val note = NoteEntity(
            noteId = UUID.randomUUID().toString(),
            authorId = current.userId,
            authorName = current.displayName,
            authorUsername = current.username,
            authorAvatarColor = current.avatarColorHex,
            content = content.trim(),
            categoryEmoji = categoryEmoji,
            categoryName = categoryName,
            audienceType = audienceType.name,
            targetCircleId = targetCircleId,
            targetCircleName = targetCircleName,
            expiryType = expiry.name,
            expiresAt = NoteExpiry.calculateExpiresAt(expiry),
            isPinned = isPinned,
            isDraft = isDraft,
            isMemoryCapsule = isMemoryCapsule,
            unlockDate = unlockDate,
            moodTag = moodTag,
            isRitual = isRitual,
            noteType = noteType,
            tags = tags,
            isVaultProtected = isVaultProtected,
            audioDurationSec = audioDurationSec,
            audioTranscript = audioTranscript,
            collaborators = collaborators,
            reminderTimestamp = reminderTimestamp,
            reactions = "",
            replyCount = 0
        )
        noteDao.insertNote(note)
        return note
    }

    suspend fun deleteNote(noteId: String) {
        noteDao.deleteNote(noteId)
    }

    suspend fun togglePinNote(noteId: String, currentPinned: Boolean) {
        noteDao.togglePin(noteId, !currentPinned)
    }

    suspend fun reactToNote(noteId: String, emoji: String) {
        noteDao.updateReactions(noteId, emoji)
    }

    fun getRepliesForNote(noteId: String): Flow<List<NoteReplyEntity>> {
        return noteReplyDao.getRepliesForNote(noteId)
    }

    suspend fun addReply(noteId: String, content: String) {
        val current = userDao.getCurrentUserSync() ?: return
        val reply = NoteReplyEntity(
            replyId = UUID.randomUUID().toString(),
            noteId = noteId,
            authorId = current.userId,
            authorName = current.displayName,
            authorUsername = current.username,
            content = content.trim()
        )
        noteReplyDao.insertReply(reply)
        noteDao.incrementReplyCount(noteId)
    }

    // Conversations & Messages (E2EE)
    val conversations: Flow<List<ConversationEntity>> = conversationDao.getAllConversations()

    fun getMessages(conversationId: String): Flow<List<MessageEntity>> = messageDao.getMessages(conversationId)

    suspend fun startConversationWithUser(user: UserEntity): ConversationEntity {
        val current = userDao.getCurrentUserSync() ?: throw IllegalStateException("Not logged in")
        val conversationId = UUID.randomUUID().toString()
        val safetyNumber = E2EEEngine.generateSafetyNumber(current.userId, user.userId)
        val conversation = ConversationEntity(
            conversationId = conversationId,
            participantId = user.userId,
            title = user.displayName,
            participantUsername = user.username,
            participantAvatarColor = user.avatarColorHex,
            isGroup = false,
            lastMessageText = "End-to-End Encrypted chat created",
            lastMessageTimestamp = System.currentTimeMillis(),
            unreadCount = 0,
            isE2EE = true,
            safetyNumber = safetyNumber
        )
        conversationDao.insertConversation(conversation)
        return conversation
    }

    suspend fun sendMessage(conversationId: String, participantId: String, text: String) {
        val current = userDao.getCurrentUserSync() ?: return
        val secretKey = E2EEEngine.deriveKey(conversationId, current.userId, participantId)
        val cipherText = E2EEEngine.encrypt(text.trim(), secretKey)

        val message = MessageEntity(
            messageId = UUID.randomUUID().toString(),
            conversationId = conversationId,
            senderId = current.userId,
            senderName = current.displayName,
            cipherText = cipherText,
            plainText = text.trim(),
            status = "DELIVERED",
            timestamp = System.currentTimeMillis(),
            isE2EE = true
        )
        messageDao.insertMessage(message)
        conversationDao.updateLastMessage(conversationId, text.trim(), message.timestamp)
    }

    suspend fun markConversationAsRead(conversationId: String) {
        conversationDao.markAsRead(conversationId)
    }

    suspend fun toggleBookmarkMessage(messageId: String, isBookmarked: Boolean, collection: String?) {
        messageDao.setBookmark(messageId, isBookmarked, collection)
    }

    // Connections & Search
    fun searchUsers(query: String): Flow<List<UserEntity>> = userDao.searchUsers(query)
    fun searchNotes(query: String): Flow<List<NoteEntity>> = noteDao.searchNotes(query)
    fun searchMessages(query: String): Flow<List<MessageEntity>> = messageDao.searchMessages(query)

    suspend fun sendConnectionRequest(usernameOrEmail: String): Boolean {
        val current = userDao.getCurrentUserSync() ?: return false
        val clean = if (usernameOrEmail.startsWith("@")) usernameOrEmail.lowercase() else "@${usernameOrEmail.lowercase()}"
        val existing = userDao.getUserByUsername(clean)
        if (existing != null) {
            userDao.setConnectionStatus(existing.userId, ConnectionStatus.REQUEST_SENT.name)
            return true
        } else {
            val newContact = UserEntity(
                userId = UUID.randomUUID().toString(),
                email = "${clean.removePrefix("@")}@notecircle.internal",
                username = clean,
                displayName = clean.removePrefix("@").replaceFirstChar { it.uppercase() },
                bio = "Invited connection",
                avatarColorHex = "#DDF1FF",
                connectionStatus = ConnectionStatus.REQUEST_SENT.name,
                isCurrentUser = false,
                safetyNumber = E2EEEngine.generateSafetyNumber(current.userId, clean)
            )
            userDao.insertUser(newContact)
            return true
        }
    }

    suspend fun acceptConnection(userId: String) {
        userDao.setConnectionStatus(userId, ConnectionStatus.CONNECTED.name)
        notificationDao.insertNotification(
            NotificationEntity(
                id = UUID.randomUUID().toString(),
                title = "Connection Approved",
                body = "You and your connection can now see private notes and chat.",
                type = "CONNECTION_ACCEPTED"
            )
        )
    }

    suspend fun rejectConnection(userId: String) {
        userDao.setConnectionStatus(userId, ConnectionStatus.NONE.name)
    }

    suspend fun toggleCloseFriend(userId: String, isClose: Boolean) {
        userDao.setCloseFriend(userId, isClose)
    }

    // Circle Rituals, Polls, Events, Quests, Flashcards
    val allRituals: Flow<List<CircleRitualEntity>> = circleRitualDao.getAllRituals()
    val allPolls: Flow<List<DecisionPollEntity>> = decisionPollDao.getAllPolls()
    val allCalendarEvents: Flow<List<CalendarEventEntity>> = calendarEventDao.getAllEvents()
    val allQuests: Flow<List<FriendshipQuestEntity>> = friendshipQuestDao.getAllQuests()
    val allFlashcards: Flow<List<StudyFlashcardEntity>> = studyFlashcardDao.getAllFlashcards()

    suspend fun createRitual(circleId: String, prompt: String, emoji: String) {
        val current = userDao.getCurrentUserSync() ?: return
        circleRitualDao.insertRitual(
            CircleRitualEntity(
                ritualId = UUID.randomUUID().toString(),
                circleId = circleId,
                promptText = prompt,
                authorName = current.displayName,
                emoji = emoji
            )
        )
    }

    suspend fun createPoll(circleId: String, question: String, op1: String, op2: String, op3: String) {
        val current = userDao.getCurrentUserSync() ?: return
        decisionPollDao.insertPoll(
            DecisionPollEntity(
                pollId = UUID.randomUUID().toString(),
                circleId = circleId,
                question = question,
                authorName = current.displayName,
                option1 = op1,
                option2 = op2,
                option3 = op3
            )
        )
    }

    suspend fun votePoll(pollId: String, optionIndex: Int) {
        when (optionIndex) {
            1 -> decisionPollDao.recordVote(pollId, 1, 0, 0, 1)
            2 -> decisionPollDao.recordVote(pollId, 0, 1, 0, 2)
            3 -> decisionPollDao.recordVote(pollId, 0, 0, 1, 3)
        }
    }

    suspend fun createCalendarEvent(circleId: String, title: String, date: String, time: String, location: String) {
        calendarEventDao.insertEvent(
            CalendarEventEntity(
                eventId = UUID.randomUUID().toString(),
                circleId = circleId,
                title = title,
                dateText = date,
                timeText = time,
                location = location
            )
        )
    }

    suspend fun completeQuest(questId: String) {
        friendshipQuestDao.completeQuest(questId, System.currentTimeMillis())
        incrementGrowthFlower()
    }

    suspend fun createFlashcard(subject: String, prompt: String, answer: String) {
        studyFlashcardDao.insertFlashcard(
            StudyFlashcardEntity(
                cardId = UUID.randomUUID().toString(),
                subject = subject,
                prompt = prompt,
                answer = answer
            )
        )
    }

    suspend fun toggleFlashcard(cardId: String, isMastered: Boolean) {
        studyFlashcardDao.toggleMastered(cardId, !isMastered)
    }

    // Notifications
    val notifications: Flow<List<NotificationEntity>> = notificationDao.getAllNotifications()
    val unreadNotificationCount: Flow<Int> = notificationDao.getUnreadCount()

    suspend fun markAllNotificationsAsRead() {
        notificationDao.markAllAsRead()
    }

    suspend fun logout() {
        userDao.clearCurrentUser()
    }

    suspend fun deleteAccount() {
        val current = userDao.getCurrentUserSync()
        if (current != null) {
            userDao.deleteUser(current.userId)
        }
    }
}
