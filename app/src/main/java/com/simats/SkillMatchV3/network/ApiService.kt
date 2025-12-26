package com.simats.SkillMatchV3.network

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login.php")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register.php")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("saved_job.php")
    fun saveJob(
        @Header("Authorization") token: String,
        @Body request: SaveJobRequest
    ): Call<GenericResponse>

    @GET("saved_jobs_list.php")
    fun getSavedJobs(
        @Header("Authorization") token: String
    ): Call<SavedJobsResponse>

    @GET("my_applications.php")
    fun getMyApplications(
        @Header("Authorization") token: String
    ): Call<AppliedJobsResponse>

    // ✅ FIX: Added the missing 'applyJob' function
    @POST("apply_job.php")
    fun applyJob(
        @Header("Authorization") token: String,
        @Query("job_id") jobId: Int
    ): Call<Map<String, Any>>

    // ---------------- PROFILE & SKILLS ----------------

    @GET("get_profile.php")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    // ✅ UPDATED: Used 'suspend' and new path/response type
    @POST("seeker/update_profile.php")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): ApiResponse

    @POST("update_user_skills.php")
    fun updateSkills(
        @Header("Authorization") token: String,
        @Body request: UpdateSkillsRequest
    ): Call<GenericResponse>

    @GET("skills_list.php")
    fun getSkills(): Call<SkillsListResponse>

    @GET("recommended_jobs.php")
    fun getRecommendedJobs(
        @Header("Authorization") token: String
    ): Call<RecommendedJobsResponse>
}

// ✅ ADDED: Simple response model
data class ApiResponse(
    val status: Boolean,
    val message: String
)
