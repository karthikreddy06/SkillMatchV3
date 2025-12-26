package com.simats.SkillMatchV3.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Profile",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        // Profile Card
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("Alex Johnson", fontWeight = FontWeight.Bold)
                Text("UX Designer", fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))

                TextButton(onClick = onEditProfile) {
                    Text("Edit Profile")
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        ProfileSection("Skills", listOf("UI Design", "UX Research", "Wireframing", "Prototyping"))
        ProfileSection("Saved Jobs")
        ProfileSection("Applied Jobs")
        ProfileSection("Notifications")
        ProfileSection("Settings")

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Logout",
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProfileSection(
    title: String,
    chips: List<String> = emptyList()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Medium)

            if (chips.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                FlowRow {
                    chips.forEach {
                        AssistChip(
                            onClick = {},
                            label = { Text(it) }
                        )
                    }
                }
            }
        }
    }
}
