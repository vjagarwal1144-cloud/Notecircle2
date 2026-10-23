package com.example.data.supabase

import com.example.data.supabase.model.SupabaseCircleDto
import com.example.data.supabase.model.SupabaseNoteDto
import com.example.data.supabase.model.SupabaseUserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseApiService {

    @GET("rest/v1/users")
    suspend fun getUsers(
        @Query("select") select: String = "*",
        @Query("email") emailFilter: String? = null
    ): Response<List<SupabaseUserDto>>

    @POST("rest/v1/users")
    suspend fun upsertUser(
        @Header("Prefer") prefer: String = "resolution=merge-duplicates",
        @Body user: SupabaseUserDto
    ): Response<Unit>

    @GET("rest/v1/notes")
    suspend fun getNotes(
        @Query("select") select: String = "*",
        @Query("author_id") authorIdFilter: String? = null
    ): Response<List<SupabaseNoteDto>>

    @POST("rest/v1/notes")
    suspend fun insertNote(
        @Header("Prefer") prefer: String = "resolution=merge-duplicates",
        @Body note: SupabaseNoteDto
    ): Response<Unit>

    @GET("rest/v1/circles")
    suspend fun getCircles(
        @Query("select") select: String = "*"
    ): Response<List<SupabaseCircleDto>>

    @POST("rest/v1/circles")
    suspend fun insertCircle(
        @Header("Prefer") prefer: String = "resolution=merge-duplicates",
        @Body circle: SupabaseCircleDto
    ): Response<Unit>
}
