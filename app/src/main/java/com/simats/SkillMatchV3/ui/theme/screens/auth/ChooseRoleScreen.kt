package com.simats.SkillMatchV3.ui.screens.auth

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
fun ChooseRoleScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Choose Your Role", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                prefManager.setRole("com/simats/SkillMatchV3/ui/theme/screens/seeker")
                navController.navigate(NavRoutes.REGISTER)
            }
        ) {
            Text("Job Seeker")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                prefManager.setRole("employer")
                navController.navigate(NavRoutes.REGISTER)
            }
        ) {
            Text("Employer")
        }
    }
}
