package com.simats.SkillMatchV3.ui.screens.employer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ActiveJobUiModel(
    val title: String,
    val location: String,
    val applicants: Int,
    val daysLeft: Int,
    val type: String = "Full-time",
    val salary: String = "$50k - $70k"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveJobsScreen(onBackPress: () -> Unit) {
    val jobs = listOf(
        ActiveJobUiModel("Frontend Developer", "New York, NY", 24, 10),
        ActiveJobUiModel("UX Designer", "Remote", 15, 5, salary = "$60k - $80k"),
        ActiveJobUiModel("Backend Engineer", "San Francisco, CA", 8, 12, salary = "$90k - $120k")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Active Jobs", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(jobs) { job ->
                ActiveJobCard(job)
            }
        }
    }
}

@Composable
fun ActiveJobCard(job: ActiveJobUiModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(job.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(job.location, fontSize = 14.sp, color = Color.Gray)
                }
                Surface(
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        job.type,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFF1E88FF),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Salary: ${job.salary}", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = {},
                    label = { Text("${job.applicants} applicants") },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFF5F5F5))
                )
                AssistChip(
                    onClick = {},
                    label = { Text("${job.daysLeft} days left") },
                    colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFF5F5F5))
                )
            }
        }
    }
}
