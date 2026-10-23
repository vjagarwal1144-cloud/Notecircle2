package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userId: String,
    val email: String,
    val username: String,
    val password: String = "password123",
    val displayName: String,
    val bio: String,
    val avatarColorHex: String = "#FFE2E8",
    val statusEmoji: String = "🌿",
    val availabilityStatus: String = "AVAILABLE",
    val moodStatus: String = "BLOOMING",
    val moodEmoji: String = "🌸",
    val thoughtBubble: String = "",
    val thoughtBubbleTimestamp: Long = 0L,
    val companionName: String = "Mochi",
    val companionEmoji: String = "🐱",
    val activeTheme: String = "STRAWBERRY_MILK",
    val isCurrentUser: Boolean = false,
    val connectionStatus: String = "NONE",
    val isCloseFriend: Boolean = false,
    val safetyNumber: String = "8492-1049-5582-7104",
    val vaultPin: String = "1234",
    val isVaultLocked: Boolean = false,
    val growthGardenFlowers: Int = 3,
    val quietHoursEnabled: Boolean = false,
    val aiConsentGranted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "circles")
data class CircleEntity(
    @PrimaryKey val circleId: String,
    val name: String,
    val description: String,
    val emoji: String,
    val colorHex: String = "#DDF7E8",
    val memberCount: Int = 1,
    val isTemporary: Boolean = false,
    val expiresAt: Long? = null,
    val wallpaperTheme: String = "COZY_WARMTH",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val noteId: String,
    val authorId: String,
    val authorName: String,
    val authorUsername: String,
    val authorAvatarColor: String = "#FFE2E8",
    val content: String,
    val categoryEmoji: String = "💭",
    val categoryName: String = "Thought",
    val audienceType: String = "EVERYONE_IN_CIRCLE",
    val targetCircleId: String? = null,
    val targetCircleName: String? = null,
    val expiryType: String = "ONE_DAY",
    val expiresAt: Long? = null,
    val isPinned: Boolean = false,
    val isDraft: Boolean = false,
    val isMemoryCapsule: Boolean = false,
    val unlockDate: Long? = null,
    val moodTag: String? = null,
    val isRitual: Boolean = false,
    val noteType: String = "STANDARD", // STANDARD, WHISPER, MEMORY_CAPSULE, FUTURE_SELF, GRATITUDE, JOURNAL, COLLABORATIVE, VOICE_POSTCARD
    val tags: String = "",
    val isVaultProtected: Boolean = false,
    val audioDurationSec: Int = 0,
    val audioTranscript: String? = null,
    val collaborators: String = "",
    val reminderTimestamp: Long? = null,
    val reactions: String = "",
    val replyCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "note_replies")
data class NoteReplyEntity(
    @PrimaryKey val replyId: String,
    val noteId: String,
    val authorId: String,
    val authorName: String,
    val authorUsername: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey val conversationId: String,
    val participantId: String,
    val title: String,
    val participantUsername: String,
    val participantAvatarColor: String = "#DDF1FF",
    val isGroup: Boolean = false,
    val lastMessageText: String = "",
    val lastMessageTimestamp: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val isE2EE: Boolean = true,
    val safetyNumber: String = "8492-1049-5582-7104",
    val wallpaper: String = "DEFAULT"
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val messageId: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val cipherText: String,
    val plainText: String,
    val status: String = "DELIVERED",
    val timestamp: Long = System.currentTimeMillis(),
    val isE2EE: Boolean = true,
    val reactions: String = "",
    val isBookmarked: Boolean = false,
    val collectionName: String? = null,
    val scheduledDeliveryTime: Long? = null
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val type: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isRead: Boolean = false
)

@Entity(tableName = "circle_rituals")
data class CircleRitualEntity(
    @PrimaryKey val ritualId: String,
    val circleId: String,
    val promptText: String,
    val authorName: String,
    val emoji: String = "🌸",
    val activeDate: Long = System.currentTimeMillis(),
    val responseCount: Int = 0
)

@Entity(tableName = "decision_polls")
data class DecisionPollEntity(
    @PrimaryKey val pollId: String,
    val circleId: String,
    val question: String,
    val authorName: String,
    val option1: String,
    val option2: String,
    val option3: String = "",
    val votes1: Int = 0,
    val votes2: Int = 0,
    val votes3: Int = 0,
    val hasVoted: Boolean = false,
    val selectedOption: Int = 0,
    val isClosed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "calendar_events")
data class CalendarEventEntity(
    @PrimaryKey val eventId: String,
    val circleId: String,
    val title: String,
    val dateText: String,
    val timeText: String,
    val emoji: String = "📅",
    val location: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "friendship_quests")
data class FriendshipQuestEntity(
    @PrimaryKey val questId: String,
    val title: String,
    val description: String,
    val rewardBadge: String,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)

@Entity(tableName = "study_flashcards")
data class StudyFlashcardEntity(
    @PrimaryKey val cardId: String,
    val subject: String,
    val prompt: String,
    val answer: String,
    val isMastered: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
