package com.simats.SkillMatchV3.ui.screens.employer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NotificationItem(
    val title: String,
    val description: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType {
    APPLICATION, MESSAGE, SYSTEM, INTERVIEW
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerNotificationsScreen(onBackPress: () -> Unit) {
    val notifications = listOf(
        NotificationItem(
            "New Application Received",
            "Alex Johnson applied for UX Designer position.",
            "10 mins ago",
            NotificationType.APPLICATION
        ),
        NotificationItem(
            "New Message",
            "Sarah Williams: 'Is the position still available?'",
            "1 hour ago",
            NotificationType.MESSAGE
        ),
        NotificationItem(
            "Interview Scheduled",
            "Interview confirmed with Michael Brown for tomorrow at 2 PM.",
            "3 hours ago",
            NotificationType.INTERVIEW
        ),
        NotificationItem(
            "Job Post Approved",
            "Your job post 'Frontend Developer' is now live.",
            "Yesterday",
            NotificationType.SYSTEM,
            isRead = true
        ),
        NotificationItem(
            "Application Withdrawn",
            "Emily Davis withdrew her application.",
            "2 days ago",
            NotificationType.APPLICATION,
            isRead = true
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(notifications) { notification ->
                NotificationCard(notification)
            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead) Color.White else Color(0xFFE3F2FD)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon based on type
            val (icon, color) = when (notification.type) {
                NotificationType.APPLICATION -> Icons.Default.Person to Color(0xFF1E88FF)
                NotificationType.MESSAGE -> Icons.Default.Message to Color(0xFF4CAF50)
                NotificationType.SYSTEM -> Icons.Default.Info to Color(0xFFFF9800)
                NotificationType.INTERVIEW -> Icons.Default.CheckCircle to Color(0xFF9C27B0)
            }

            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = color)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.time,
                    color = Color.LightGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
