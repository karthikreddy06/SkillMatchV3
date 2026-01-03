package com.simats.SkillMatchV3.network

import com.simats.SkillMatchV3.ui.model.Job

data class JobsResponse(
    val status: Boolean,
    val count: Int,
    val jobs: List<Job>
)
