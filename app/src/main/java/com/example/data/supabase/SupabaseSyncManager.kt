package com.example.data.supabase

import android.util.Log
import com.example.data.local.entity.CircleEntity
import com.example.data.local.entity.NoteEntity
import com.example.data.local.entity.UserEntity
import com.example.data.supabase.model.SupabaseCircleDto
import com.example.data.supabase.model.SupabaseNoteDto
import com.example.data.supabase.model.SupabaseUserDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class CloudSyncState {
    IDLE,
    SYNCING,
    SYNCED,
    OFFLINE
}

class SupabaseSyncManager {
    private val TAG = "SupabaseSyncManager"

    private val _syncState = MutableStateFlow(CloudSyncState.IDLE)
    val syncState: StateFlow<CloudSyncState> = _syncState.asStateFlow()

    suspend fun syncUserToSupabase(user: UserEntity): Boolean {
        if (!SupabaseClient.isConfigured) {
            Log.d(TAG, "Supabase credentials not configured yet in .env; maintaining offline-first local persistence.")
            _syncState.value = CloudSyncState.OFFLINE
            return false
        }

        return try {
            _syncState.value = CloudSyncState.SYNCING
            val dto = SupabaseUserDto(
                id = user.userId,
                email = user.email,
                username = user.username,
                displayName = user.displayName,
                bio = user.bio,
                avatarColorHex = user.avatarColorHex,
                statusEmoji = user.statusEmoji,
                availabilityStatus = user.availabilityStatus,
                moodStatus = user.moodStatus,
                companionName = user.companionName,
                activeTheme = user.activeTheme,
                createdAt = user.createdAt
            )
            val response = SupabaseClient.apiService.upsertUser(user = dto)
            if (response.isSuccessful) {
                Log.i(TAG, "User ${user.email} synced successfully to Supabase.")
                _syncState.value = CloudSyncState.SYNCED
                true
            } else {
                Log.w(TAG, "Supabase sync error code: ${response.code()}")
                _syncState.value = CloudSyncState.OFFLINE
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync user to Supabase: ${e.message}")
            _syncState.value = CloudSyncState.OFFLINE
            false
        }
    }

    suspend fun syncNoteToSupabase(note: NoteEntity): Boolean {
        if (!SupabaseClient.isConfigured) {
            _syncState.value = CloudSyncState.OFFLINE
            return false
        }

        return try {
            _syncState.value = CloudSyncState.SYNCING
            val dto = SupabaseNoteDto(
                id = note.noteId,
                authorId = note.authorId,
                authorName = note.authorName,
                authorUsername = note.authorUsername,
                content = note.content,
                categoryEmoji = note.categoryEmoji,
                categoryName = note.categoryName,
                tags = note.tags,
                audienceType = note.audienceType,
                targetCircleId = note.targetCircleId,
                isPinned = note.isPinned,
                isMemoryCapsule = note.isMemoryCapsule,
                unlockDate = note.unlockDate,
                isVaultProtected = note.isVaultProtected,
                expiresAt = note.expiresAt,
                createdAt = note.createdAt
            )
            val response = SupabaseClient.apiService.insertNote(note = dto)
            if (response.isSuccessful) {
                _syncState.value = CloudSyncState.SYNCED
                true
            } else {
                _syncState.value = CloudSyncState.OFFLINE
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync note to Supabase: ${e.message}")
            _syncState.value = CloudSyncState.OFFLINE
            false
        }
    }

    suspend fun syncCircleToSupabase(circle: CircleEntity): Boolean {
        if (!SupabaseClient.isConfigured) {
            _syncState.value = CloudSyncState.OFFLINE
            return false
        }

        return try {
            _syncState.value = CloudSyncState.SYNCING
            val dto = SupabaseCircleDto(
                id = circle.circleId,
                name = circle.name,
                description = circle.description,
                emoji = circle.emoji,
                colorHex = circle.colorHex,
                memberCount = circle.memberCount,
                createdAt = circle.createdAt
            )
            val response = SupabaseClient.apiService.insertCircle(circle = dto)
            if (response.isSuccessful) {
                _syncState.value = CloudSyncState.SYNCED
                true
            } else {
                _syncState.value = CloudSyncState.OFFLINE
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync circle to Supabase: ${e.message}")
            _syncState.value = CloudSyncState.OFFLINE
            false
        }
    }
}
