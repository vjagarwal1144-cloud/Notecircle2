package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.CalendarEventDao
import com.example.data.local.dao.CircleDao
import com.example.data.local.dao.CircleRitualDao
import com.example.data.local.dao.ConversationDao
import com.example.data.local.dao.DecisionPollDao
import com.example.data.local.dao.FriendshipQuestDao
import com.example.data.local.dao.MessageDao
import com.example.data.local.dao.NoteDao
import com.example.data.local.dao.NoteReplyDao
import com.example.data.local.dao.NotificationDao
import com.example.data.local.dao.StudyFlashcardDao
import com.example.data.local.dao.UserDao
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

@Database(
    entities = [
        UserEntity::class,
        CircleEntity::class,
        NoteEntity::class,
        NoteReplyEntity::class,
        ConversationEntity::class,
        MessageEntity::class,
        NotificationEntity::class,
        CircleRitualEntity::class,
        DecisionPollEntity::class,
        CalendarEventEntity::class,
        FriendshipQuestEntity::class,
        StudyFlashcardEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class NoteCircleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun circleDao(): CircleDao
    abstract fun noteDao(): NoteDao
    abstract fun noteReplyDao(): NoteReplyDao
    abstract fun conversationDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun notificationDao(): NotificationDao
    abstract fun circleRitualDao(): CircleRitualDao
    abstract fun decisionPollDao(): DecisionPollDao
    abstract fun calendarEventDao(): CalendarEventDao
    abstract fun friendshipQuestDao(): FriendshipQuestDao
    abstract fun studyFlashcardDao(): StudyFlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: NoteCircleDatabase? = null

        fun getDatabase(context: Context): NoteCircleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteCircleDatabase::class.java,
                    "notecircle_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
