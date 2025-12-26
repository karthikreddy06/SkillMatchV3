package com.simats.SkillMatchV3.ui.screens.seeker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FindJobsNearbyScreen(
    onJobClick: (JobUiModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Nearby Jobs", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                onJobClick(
                    JobUiModel(
                        id = "1",
                        title = "Delivery Executive",
                        company = "Swiggy",
                        location = "Hyderabad",
                        salary = "₹15k - ₹20k",
                        description = "Deliver food orders to nearby customers."
                    )
                )
            }
        ) {
            Text("Open Sample Job")
        }
    }
}
