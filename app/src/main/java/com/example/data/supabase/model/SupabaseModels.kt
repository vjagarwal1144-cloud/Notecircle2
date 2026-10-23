package com.example.data.supabase.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SupabaseUserDto(
    @Json(name = "id") val id: String,
    @Json(name = "email") val email: String,
    @Json(name = "username") val username: String,
    @Json(name = "display_name") val displayName: String,
    @Json(name = "bio") val bio: String = "",
    @Json(name = "avatar_color_hex") val avatarColorHex: String = "#FFE2E8",
    @Json(name = "status_emoji") val statusEmoji: String = "🌿",
    @Json(name = "availability_status") val availabilityStatus: String = "AVAILABLE",
    @Json(name = "mood_status") val moodStatus: String = "BLOOMING",
    @Json(name = "companion_name") val companionName: String = "Mochi",
    @Json(name = "active_theme") val activeTheme: String = "STRAWBERRY_MILK",
    @Json(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@JsonClass(generateAdapter = true)
data class SupabaseNoteDto(
    @Json(name = "id") val id: String,
    @Json(name = "author_id") val authorId: String,
    @Json(name = "author_name") val authorName: String,
    @Json(name = "author_username") val authorUsername: String,
    @Json(name = "content") val content: String,
    @Json(name = "category_emoji") val categoryEmoji: String = "💭",
    @Json(name = "category_name") val categoryName: String = "Thought",
    @Json(name = "tags") val tags: String = "",
    @Json(name = "audience_type") val audienceType: String = "EVERYONE_IN_CIRCLE",
    @Json(name = "target_circle_id") val targetCircleId: String? = null,
    @Json(name = "is_pinned") val isPinned: Boolean = false,
    @Json(name = "is_memory_capsule") val isMemoryCapsule: Boolean = false,
    @Json(name = "unlock_date") val unlockDate: Long? = null,
    @Json(name = "is_vault_protected") val isVaultProtected: Boolean = false,
    @Json(name = "expires_at") val expiresAt: Long? = null,
    @Json(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)

@JsonClass(generateAdapter = true)
data class SupabaseCircleDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String = "",
    @Json(name = "emoji") val emoji: String = "⭐️",
    @Json(name = "color_hex") val colorHex: String = "#FFE2E8",
    @Json(name = "member_count") val memberCount: Int = 1,
    @Json(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
