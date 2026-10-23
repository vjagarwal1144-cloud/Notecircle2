package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    fun getCurrentUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE isCurrentUser = 1 LIMIT 1")
    suspend fun getCurrentUserSync(): UserEntity?

    @Query("SELECT * FROM users WHERE connectionStatus = 'CONNECTED' ORDER BY displayName ASC")
    fun getConnections(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE connectionStatus = 'REQUEST_RECEIVED' ORDER BY createdAt DESC")
    fun getPendingRequests(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE isCurrentUser = 0 AND (username LIKE '%' || :query || '%' OR displayName LIKE '%' || :query || '%')")
    fun searchUsers(query: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET availabilityStatus = :status, statusEmoji = :emoji WHERE userId = :userId")
    suspend fun setAvailability(userId: String, status: String, emoji: String)

    @Query("UPDATE users SET moodStatus = :mood, moodEmoji = :emoji WHERE userId = :userId")
    suspend fun setMood(userId: String, mood: String, emoji: String)

    @Query("UPDATE users SET thoughtBubble = :bubble, thoughtBubbleTimestamp = :ts WHERE userId = :userId")
    suspend fun setThoughtBubble(userId: String, bubble: String, ts: Long)

    @Query("UPDATE users SET companionName = :name, companionEmoji = :emoji WHERE userId = :userId")
    suspend fun setCompanion(userId: String, name: String, emoji: String)

    @Query("UPDATE users SET activeTheme = :theme WHERE userId = :userId")
    suspend fun setTheme(userId: String, theme: String)

    @Query("UPDATE users SET vaultPin = :pin, isVaultLocked = :locked WHERE userId = :userId")
    suspend fun setVaultSecurity(userId: String, pin: String, locked: Boolean)

    @Query("UPDATE users SET growthGardenFlowers = growthGardenFlowers + 1 WHERE userId = :userId")
    suspend fun incrementGrowthFlower(userId: String)

    @Query("UPDATE users SET connectionStatus = :status WHERE userId = :userId")
    suspend fun setConnectionStatus(userId: String, status: String)

    @Query("UPDATE users SET isCloseFriend = :isClose WHERE userId = :userId")
    suspend fun setCloseFriend(userId: String, isClose: Boolean)

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUser(userId: String)
}

@Dao
interface CircleDao {
    @Query("SELECT * FROM circles ORDER BY createdAt ASC")
    fun getAllCircles(): Flow<List<CircleEntity>>

    @Query("SELECT * FROM circles WHERE circleId = :id LIMIT 1")
    fun getCircleById(id: String): Flow<CircleEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircle(circle: CircleEntity)

    @Query("DELETE FROM circles WHERE circleId = :circleId")
    suspend fun deleteCircle(circleId: String)
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE isDraft = 0 AND isVaultProtected = 0 ORDER BY isPinned DESC, createdAt DESC")
    fun getActiveNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isVaultProtected = 1 ORDER BY createdAt DESC")
    fun getVaultNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE isDraft = 1 ORDER BY createdAt DESC")
    fun getDraftNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE authorId = :authorId ORDER BY createdAt DESC")
    fun getUserNotes(authorId: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE content LIKE '%' || :query || '%' OR categoryName LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE noteId = :noteId LIMIT 1")
    fun getNoteById(noteId: String): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: String)

    @Query("UPDATE notes SET isPinned = :isPinned WHERE noteId = :noteId")
    suspend fun togglePin(noteId: String, isPinned: Boolean)

    @Query("UPDATE notes SET reactions = :reactions WHERE noteId = :noteId")
    suspend fun updateReactions(noteId: String, reactions: String)

    @Query("UPDATE notes SET replyCount = replyCount + 1 WHERE noteId = :noteId")
    suspend fun incrementReplyCount(noteId: String)
}

@Dao
interface NoteReplyDao {
    @Query("SELECT * FROM note_replies WHERE noteId = :noteId ORDER BY createdAt ASC")
    fun getRepliesForNote(noteId: String): Flow<List<NoteReplyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReply(reply: NoteReplyEntity)
}

@Dao
interface ConversationDao {
    @Query("SELECT * FROM conversations ORDER BY lastMessageTimestamp DESC")
    fun getAllConversations(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM conversations WHERE conversationId = :id LIMIT 1")
    fun getConversationById(id: String): Flow<ConversationEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity)

    @Query("UPDATE conversations SET lastMessageText = :text, lastMessageTimestamp = :timestamp WHERE conversationId = :conversationId")
    suspend fun updateLastMessage(conversationId: String, text: String, timestamp: Long)

    @Query("UPDATE conversations SET unreadCount = 0 WHERE conversationId = :conversationId")
    suspend fun markAsRead(conversationId: String)
}

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY timestamp ASC")
    fun getMessages(conversationId: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE plainText LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchMessages(query: String): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE isBookmarked = 1 ORDER BY timestamp DESC")
    fun getBookmarkedMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("UPDATE messages SET status = :status WHERE messageId = :messageId")
    suspend fun updateMessageStatus(messageId: String, status: String)

    @Query("UPDATE messages SET isBookmarked = :isBookmarked, collectionName = :collection WHERE messageId = :messageId")
    suspend fun setBookmark(messageId: String, isBookmarked: Boolean, collection: String?)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllAsRead()
}

@Dao
interface CircleRitualDao {
    @Query("SELECT * FROM circle_rituals ORDER BY activeDate DESC")
    fun getAllRituals(): Flow<List<CircleRitualEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRitual(ritual: CircleRitualEntity)
}

@Dao
interface DecisionPollDao {
    @Query("SELECT * FROM decision_polls WHERE circleId = :circleId ORDER BY createdAt DESC")
    fun getPollsForCircle(circleId: String): Flow<List<DecisionPollEntity>>

    @Query("SELECT * FROM decision_polls ORDER BY createdAt DESC")
    fun getAllPolls(): Flow<List<DecisionPollEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoll(poll: DecisionPollEntity)

    @Query("UPDATE decision_polls SET votes1 = votes1 + :add1, votes2 = votes2 + :add2, votes3 = votes3 + :add3, hasVoted = 1, selectedOption = :opt WHERE pollId = :pollId")
    suspend fun recordVote(pollId: String, add1: Int, add2: Int, add3: Int, opt: Int)
}

@Dao
interface CalendarEventDao {
    @Query("SELECT * FROM calendar_events ORDER BY timestamp ASC")
    fun getAllEvents(): Flow<List<CalendarEventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: CalendarEventEntity)

    @Query("DELETE FROM calendar_events WHERE eventId = :eventId")
    suspend fun deleteEvent(eventId: String)
}

@Dao
interface FriendshipQuestDao {
    @Query("SELECT * FROM friendship_quests ORDER BY isCompleted ASC")
    fun getAllQuests(): Flow<List<FriendshipQuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: FriendshipQuestEntity)

    @Query("UPDATE friendship_quests SET isCompleted = 1, completedAt = :ts WHERE questId = :questId")
    suspend fun completeQuest(questId: String, ts: Long)
}

@Dao
interface StudyFlashcardDao {
    @Query("SELECT * FROM study_flashcards ORDER BY isMastered ASC, createdAt DESC")
    fun getAllFlashcards(): Flow<List<StudyFlashcardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(card: StudyFlashcardEntity)

    @Query("UPDATE study_flashcards SET isMastered = :mastered WHERE cardId = :cardId")
    suspend fun toggleMastered(cardId: String, mastered: Boolean)

    @Query("DELETE FROM study_flashcards WHERE cardId = :cardId")
    suspend fun deleteFlashcard(cardId: String)
}
