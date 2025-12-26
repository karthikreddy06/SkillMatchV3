package com.simats.SkillMatchV3.ui.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SavedJobsScreen(
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
            Text("Saved Jobs", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.padding(16.dp)) {
            SavedJobItem("UX Designer", "Tech Solutions", "New York", "$80K - $100K") {
                onJobClick(
                    JobUiModel(
                        id = "1",
                        title = "UX Designer",
                        company = "Tech Solutions",
                        location = "New York",
                        salary = "$80K - $100K",
                        description = "Design UX flows"
                    )
                )
            }
        }
    }
}

@Composable
private fun SavedJobItem(
    title: String,
    company: String,
    location: String,
    salary: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(company, color = Color.Gray)
            Text("$location • $salary", fontSize = 12.sp)
        }
    }
}
