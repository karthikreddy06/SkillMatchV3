package com.simats.SkillMatchV3.network

import com.google.gson.annotations.SerializedName

data class RecommendedJobsResponse(
    val status: Boolean,
    @SerializedName("jobs")
    val jobs: List<RecommendedJobDto>
)

data class RecommendedJobDto(
    val id: Int,
    val title: String?,
    val company_name: String?,
    val location: String?,
    val salary_min: Int?,
    val salary_max: Int?,
    val required_skills: String?,
    val latitude: String?,
    val longitude: String?
)
