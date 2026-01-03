package com.simats.SkillMatchV3.ui.model

import com.google.gson.annotations.SerializedName

data class Job(
    val id: String,
    val title: String,
    val location: String,
    val latitude: Double,
    val longitude: Double,
    
    @SerializedName("required_skills")
    val requiredSkills: List<String>,
    
    @SerializedName("job_type")
    val jobType: String,
    
    @SerializedName("salary_min")
    val salaryMin: Double,
    
    @SerializedName("salary_max")
    val salaryMax: Double,
    
    @SerializedName("company_name")
    val companyName: String,
    
    @SerializedName("has_location")
    val hasLocation: Boolean
)
