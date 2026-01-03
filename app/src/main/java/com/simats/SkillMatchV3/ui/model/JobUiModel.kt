package com.simats.SkillMatchV3.ui.model

data class JobUiModel(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
