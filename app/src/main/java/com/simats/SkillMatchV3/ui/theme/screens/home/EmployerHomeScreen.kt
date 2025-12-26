package com.simats.SkillMatchV3.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmployerHomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
    ) {

        // 🔵 Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF0A66FF))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Welcome,", color = Color.White, fontSize = 14.sp)
                    Text(
                        "Tech Solutions Inc.",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(Icons.Default.Person, null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ➕ Post Job Button
        Button(
            onClick = { },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Post New Job", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📊 Stats
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StatCard("Active Jobs", "2")
            StatCard("Applicants", "36")
            StatCard("New Today", "10")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 📄 Active Job
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Active Job Listings", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

            JobCardEmployer(
                title = "Frontend Developer",
                applicants = "24 applicants",
                daysLeft = "10 days left"
            )
        }
    }
}

@Composable
private fun StatCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, fontWeight = FontWeight.Bold)
            Text(title, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun JobCardEmployer(title: String, applicants: String, daysLeft: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(applicants, fontSize = 13.sp, color = Color.Gray)
            Text(daysLeft, fontSize = 12.sp, color = Color(0xFF22C55E))
        }
    }
}
