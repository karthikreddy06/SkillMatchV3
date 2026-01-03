package com.simats.SkillMatchV3.ui.theme.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.simats.SkillMatchV3.navigation.NavRoutes

@Composable
fun SeekerHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .verticalScroll(rememberScrollState())
    ) {
        // HEADER
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
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navController.navigate(NavRoutes.SEEKER_PROFILE) },
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // SEARCH BAR VISUAL
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Search, null, tint = Color.Gray)
                        Spacer(Modifier.width(8.dp))
                        Text("Search jobs, companies...", color = Color.Gray)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // QUICK ACTIONS
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                "Quick Actions",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeActionCard(
                    title = "Find Jobs\nNearby",
                    icon = Icons.Default.LocationOn,
                    color = Color(0xFFE3F2FD),
                    onClick = { navController.navigate(NavRoutes.MAP) }
                )
                HomeActionCard(
                    title = "AI\nRecommendations",
                    icon = Icons.Default.AutoAwesome,
                    color = Color(0xFFE8F5E9),
                    onClick = { navController.navigate(NavRoutes.AI_RECOMMENDATIONS) }
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HomeActionCard(
                    title = "Saved\nJobs",
                    icon = Icons.Default.Bookmark,
                    color = Color(0xFFFFF8E1),
                    onClick = { navController.navigate(NavRoutes.SEEKER_SAVED_JOBS) }
                )
                HomeActionCard(
                    title = "Applied\nJobs",
                    icon = Icons.Default.WorkHistory,
                    color = Color(0xFFF3E5F5),
                    onClick = { navController.navigate(NavRoutes.SEEKER_APPLIED_JOBS) }
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun HomeActionCard(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.48f)
            .height(110.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = Color.Black.copy(alpha = 0.7f))
            }
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}
