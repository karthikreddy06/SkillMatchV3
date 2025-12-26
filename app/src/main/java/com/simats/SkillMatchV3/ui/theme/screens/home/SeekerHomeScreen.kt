package com.simats.SkillMatchV3.ui.theme.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SeekerHomeScreen(
    onFindJobsNearby: () -> Unit,
    onSavedJobs: () -> Unit,
    onAppliedJobs: () -> Unit,
    onAIRecommendations: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .verticalScroll(rememberScrollState())
    ) {

        /* ---------------- HEADER ---------------- */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E88FF))
                .padding(20.dp)
        ) {
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Good Morning,", color = Color.White, fontSize = 14.sp)
                        Text(
                            "Alex",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search jobs, companies...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(14.dp)),
                    leadingIcon = {
                        Icon(Icons.Default.Search, null)
                    },
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        /* ---------------- QUICK ACTIONS ---------------- */

        Column(Modifier.padding(horizontal = 16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionCard(
                    "Find Jobs\nNearby",
                    Icons.Default.LocationOn,
                    Color(0xFFE3F2FD),
                    onFindJobsNearby
                )
                ActionCard(
                    "AI\nRecommendations",
                    Icons.Default.Lightbulb,
                    Color(0xFFE8F5E9),
                    onAIRecommendations
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionCard(
                    "Saved\nJobs",
                    Icons.Default.Bookmark,
                    Color(0xFFFFF8E1),
                    onSavedJobs
                )
                ActionCard(
                    "Applied\nJobs",
                    Icons.Default.CheckCircle,
                    Color(0xFFF3E5F5),
                    onAppliedJobs
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

/* ---------------- ACTION CARD ---------------- */

@Composable
private fun ActionCard(
    title: String,
    icon: ImageVector,
    bg: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.48f)
            .height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(bg, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null)
            }
            Text(title, fontWeight = FontWeight.Medium)
        }
    }
}
