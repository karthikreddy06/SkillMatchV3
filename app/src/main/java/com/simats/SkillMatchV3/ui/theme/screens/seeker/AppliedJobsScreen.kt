package com.simats.SkillMatchV3.ui.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppliedJobsScreen(
    onBack: () -> Unit,
    onJobClick: (JobUiModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBack() }
            )
            Spacer(Modifier.width(12.dp))
            Text("Applied Jobs", fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Applications will appear here")
        }
    }
}
