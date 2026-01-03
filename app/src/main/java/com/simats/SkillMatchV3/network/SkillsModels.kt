package com.simats.SkillMatchV3.network

data class Skill(
    val id: Int,
    val name: String
)

data class SkillsListResponse(
    val status: Boolean,
    val skills: List<Skill>
)

data class AddSkillRequest(
    val name: String
)

data class AddSkillResponse(
    val status: Boolean,
    val skill: Skill
)
