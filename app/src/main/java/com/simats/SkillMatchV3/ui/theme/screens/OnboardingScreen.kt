package com.simats.SkillMatchV3.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager

@Composable
fun OnboardingScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Welcome to SkillMatch",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // ✅ CORRECT: no arguments
                prefManager.setOnboardingSeen()

                navController.navigate(NavRoutes.CHOOSE_ROLE) {
                    popUpTo(NavRoutes.ONBOARDING) { inclusive = true }
                }
            }
        ) {
            Text("Get Started")
        }
    }
}
