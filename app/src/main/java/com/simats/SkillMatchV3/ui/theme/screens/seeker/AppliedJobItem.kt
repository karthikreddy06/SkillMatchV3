package com.simats.SkillMatchV3.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppliedJobItem(
    title: String,
    company: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = company,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Status: ${status.uppercase()}",
                color = when (status.lowercase()) {
                    "pending" -> Color(0xFFFFA000)
                    "accepted" -> Color(0xFF2E7D32)
                    "rejected" -> Color.Red
                    else -> Color.Gray
                }
            )
        }
    }
}
