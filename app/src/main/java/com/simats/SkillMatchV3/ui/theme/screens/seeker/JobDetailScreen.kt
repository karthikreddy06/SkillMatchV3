package com.simats.SkillMatchV3.ui.theme.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import com.simats.SkillMatchV3.ui.screens.seeker.JobUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailScreen(
    job: JobUiModel,
    onBack: () -> Unit,
    onApply: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onSave) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "Save Job")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                text = job.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = job.company,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "📍 ${job.location}",
                fontSize = 14.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "💰 ${job.salary}",
                fontSize = 14.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Job Description",
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = job.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onApply,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Apply Now")
            }
        }
    }
}
