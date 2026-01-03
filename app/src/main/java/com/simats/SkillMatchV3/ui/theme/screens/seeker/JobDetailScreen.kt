package com.simats.SkillMatchV3.ui.theme.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.SkillMatchV3.ui.model.JobUiModel

@Composable
fun JobDetailScreen(
    job: JobUiModel,
    onBack: () -> Unit,
    onApply: () -> Unit,
    onSave: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF6F7FB))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 80.dp) // Space for bottom button
        ) {
            // Header with Back Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF1E88FF))
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            // Job Details Card (Overlapping Header)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = job.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = job.company,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(job.location, color = Color.Gray, fontSize = 14.sp)
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Icon(Icons.Default.MonetizationOn, null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(job.salary, color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }

            // Job Description
            Column(modifier = Modifier.padding(horizontal = 16.dp).offset(y = (-20).dp)) {
                Text(
                    text = "Job Description",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = job.description,
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Requirements",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "• 3+ years of experience\n• Proficiency in Kotlin\n• Knowledge of Jetpack Compose\n• Experience with REST APIs",
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                    lineHeight = 24.sp
                )
            }
        }

        // Bottom Action Bar
        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onSave,
                    modifier = Modifier.weight(0.3f).height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.BookmarkBorder, contentDescription = null)
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Button(
                    onClick = onApply,
                    modifier = Modifier.weight(0.7f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88FF))
                ) {
                    Text("Apply Now", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
