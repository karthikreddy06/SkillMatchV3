package com.simats.SkillMatchV3.network

data class Skill(
    val id: Int,
    val name: String
)

data class SkillsListResponse(
    val status: Boolean,
    val skills: List<Skill>
)

data class UpdateSkillsRequest(
    val skill_ids: List<Int>
)
