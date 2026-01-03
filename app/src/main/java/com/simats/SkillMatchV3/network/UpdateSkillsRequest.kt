package com.simats.SkillMatchV3.network

import com.google.gson.annotations.SerializedName

data class UpdateSkillsRequest(
    @SerializedName("skills")
    val skills: List<Int>
)
