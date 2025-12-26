package com.simats.SkillMatchV3.ui.theme.screens.seeker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SkillsSelectionScreen(navController: NavHostController) {

    val skills = listOf(
        "Delivery",
        "Cleaning",
        "Driving",
        "Sales",
        "Cooking",
        "Security",
        "Helper"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(skills) { skill ->
            AssistChip(
                onClick = { /* TODO */ },
                label = { Text(skill) }
            )
        }
    }
}
