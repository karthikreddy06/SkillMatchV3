package com.simats.SkillMatchV3.network

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    /* ================= AUTH ================= */

    @POST("login.php")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("register.php")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>


    /* ================= JOBS ================= */

    @GET("jobs_list.php")
    fun getJobs(): Call<JobsResponse>

    @POST("apply_job.php")
    fun applyJob(
        @Header("Authorization") token: String,
        @Query("job_id") jobId: Int
    ): Call<GenericResponse>

    @GET("my_applications.php")
    fun getMyApplications(
        @Header("Authorization") token: String
    ): Call<AppliedJobsResponse>

    @POST("saved_job.php")
    fun saveJob(
        @Header("Authorization") token: String,
        @Body request: SaveJobRequest
    ): Call<GenericResponse>

    @GET("saved_jobs_list.php")
    fun getSavedJobs(
        @Header("Authorization") token: String
    ): Call<SavedJobsResponse>


    /* ================= PROFILE ================= */

    @GET("get_profile.php")
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<ProfileResponse>

    @POST("seeker/update_profile.php")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Call<GenericResponse>


    /* ================= SKILLS ================= */

    /**
     * Get all available skills
     */
    @GET("skills_list.php")
    fun getSkills(): Call<SkillsListResponse>

    /**
     * Get logged-in user's skills
     * MUST EXIST: /skillmatch/get_user_skills.php
     */
    @GET("get_user_skills.php")
    fun getUserSkills(
        @Header("Authorization") token: String
    ): Call<SkillsListResponse>

    /**
     * Replace all user skills
     * Expects JSON: { "skills": [1,2,3] }
     */
    @POST("update_user_skills.php")
    fun updateSkills(
        @Header("Authorization") token: String,
        @Body request: UpdateSkillsRequest
    ): Call<GenericResponse>

    /**
     * Add a custom skill (future-proof)
     */
    @POST("add_skill.php")
    fun addSkill(
        @Header("Authorization") token: String,
        @Body request: AddSkillRequest
    ): Call<AddSkillResponse>


    /* ================= AI ================= */

    @GET("recommended_jobs.php")
    fun getRecommendedJobs(
        @Header("Authorization") token: String
    ): Call<RecommendedJobsResponse>
}

/* ================= COMMON ================= */

data class ApiResponse(
    val status: Boolean,
    val message: String
)
