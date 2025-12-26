package com.simats.SkillMatchV3.network

data class ProfileResponse(
    val status: Boolean,
    val profile: UserProfile,
    val skills: List<Skill>   // <-- uses Skill from SkillsModels.kt
)

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String?,
    val latitude: Double?,
    val longitude: Double?,
    val resume_path: String?
)

data class UpdateProfileRequest(
    val name: String,
    val phone: String,
    val latitude: Double?,
    val longitude: Double?
)
