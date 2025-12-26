package com.simats.SkillMatchV3.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefManager = PrefManager(context)

    LaunchedEffect(Unit) {
        delay(1200)

        if (prefManager.isLoggedIn()) {

            when (prefManager.getRole()) {
                "seeker" -> {
                    navController.navigate(NavRoutes.SEEKER_ROOT) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }

                "employer" -> {
                    navController.navigate(NavRoutes.EMPLOYER_HOME) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }

                else -> {
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
            }

        } else if (prefManager.hasSeenOnboarding()) {

            navController.navigate(NavRoutes.LOGIN) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }

        } else {

            navController.navigate(NavRoutes.ONBOARDING) {
                popUpTo(NavRoutes.SPLASH) { inclusive = true }
            }
        }
    }
}
