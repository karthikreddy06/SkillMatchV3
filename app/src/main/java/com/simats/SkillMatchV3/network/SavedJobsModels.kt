package com.simats.SkillMatchV3.network

data class SaveJobRequest(
    val job_id: Int
)

data class SavedJob(
    val id: Int,
    val title: String,
    val company_name: String,
    val location: String,
    val job_type: String,
    val salary_min: Int,
    val salary_max: Int
)

data class SavedJobsResponse(
    val status: Boolean,
    val jobs: List<SavedJob>
)
