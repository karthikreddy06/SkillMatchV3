package com.simats.SkillMatchV3.network

data class AppliedJob(
    val application_id: Int,
    val job_id: Int,
    val title: String,
    val company_name: String,
    val status: String,
    val applied_at: String
)

data class AppliedJobsResponse(
    val status: Boolean,
    val applications: List<AppliedJob>
)
