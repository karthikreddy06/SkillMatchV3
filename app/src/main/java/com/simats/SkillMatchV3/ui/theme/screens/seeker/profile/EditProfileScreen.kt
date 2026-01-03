package com.simats.SkillMatchV3.ui.theme.screens.seeker.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            modifier = Modifier.size(90.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text("AJ", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        Spacer(Modifier.height(24.dp))

        ProfileField("Full Name", "Alex Johnson")
        ProfileField("Email", "alex@example.com")
        ProfileField("Phone", "(555) 123-4567")
        ProfileField("Job Title", "UX Designer")
        ProfileField("Location", "New York, NY")

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
private fun ProfileField(
    label: String,
    value: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}
