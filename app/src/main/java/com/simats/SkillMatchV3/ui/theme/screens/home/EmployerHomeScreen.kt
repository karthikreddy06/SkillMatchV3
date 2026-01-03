package com.simats.SkillMatchV3.ui.theme.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 🎨 COLORS
private val Blue = Color(0xFF1E73FF)
private val Green = Color(0xFF1EC98D)
private val LightGray = Color(0xFFF4F6FA)

@Composable
fun EmployerHomeScreen(
    onPostJobClick: () -> Unit = {},
    onApplicantsClick: () -> Unit = {},
    onActiveJobsClick: () -> Unit = {},
    onNewApplicantsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
    ) {

        // 🔵 HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Blue)
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Welcome,",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = "Tech Solutions Inc.",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onPostJobClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Post New Job", color = Color.White)
                }
            }

            // Profile Icon
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                 Surface(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 📊 STATS ROW
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // "Active Jobs" Card -> Leads to Active Jobs Screen
            StatCard("2", "Active\nJobs", onClick = onActiveJobsClick)
            
            // "Total Applicants" -> Leads to Applicants Screen (All)
            StatCard("36", "Total\nApplicants", onClick = onApplicantsClick)
            
            // "New Today" -> Leads to Applicants Screen (New Tab)
            StatCard("10", "New\nToday", onClick = onNewApplicantsClick)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🧩 ACTIONS ROW
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionCard("Manage Applicants", Icons.Default.Group, onClick = onApplicantsClick, modifier = Modifier.fillMaxWidth())
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📄 JOB LIST HEADER
        Text(
            text = "Active Job Listings",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Example Job Card (Clicking this could also go to Active Jobs or Job Detail)
        JobCard(onClick = onActiveJobsClick)
    }
}

// -----------------------------------------------------------
// 🧩 SUPPORTING COMPONENTS
// -----------------------------------------------------------

@Composable
private fun StatCard(value: String, label: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clickable(onClick = onClick), // Make clickable
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = Blue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                lineHeight = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String, 
    icon: ImageVector, 
    onClick: () -> Unit,
    modifier: Modifier = Modifier.width(160.dp)
) {
    Card(
        modifier = modifier
            .height(90.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row( 
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center 
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Blue)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun JobCard(onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Frontend Developer",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("24 applicants", fontSize = 12.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = {},
                    label = { Text("+7 new") },
                    colors = AssistChipDefaults.assistChipColors(leadingIconContentColor = Blue)
                )
                AssistChip(
                    onClick = {},
                    label = { Text("10 days left") }
                )
            }
        }
    }
}
