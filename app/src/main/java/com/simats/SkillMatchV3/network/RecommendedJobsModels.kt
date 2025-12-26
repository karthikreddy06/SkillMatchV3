package com.simats.SkillMatchV3.network

data class RecommendedJobsResponse(
    val status: Boolean,
    val jobs: List<Job>
)

data class Job(
    val id: Int,
    val title: String,
    val company: String,
    val location: String,
    val description: String
)
