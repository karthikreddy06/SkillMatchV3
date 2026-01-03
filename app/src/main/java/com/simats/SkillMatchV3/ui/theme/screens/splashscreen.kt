package com.simats.SkillMatchV3.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun SplashScreen(navController: NavHostController) {

    val context = LocalContext.current
    val prefManager = PrefManager(context)

    LaunchedEffect(Unit) {
        delay(1200)

        if (prefManager.isLoggedIn()) {

            val role = prefManager.getRole()?.lowercase(Locale.ROOT) ?: ""

            // ✅ FIXED: Simple, direct check. No more file paths.
            when (role) {
                "employer" -> {
                    navController.navigate(NavRoutes.EMPLOYER_ROOT) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
                "seeker" -> {
                    navController.navigate(NavRoutes.SEEKER_ROOT) {
                        popUpTo(NavRoutes.SPLASH) { inclusive = true }
                    }
                }
                else -> {
                    // Fallback if role is unknown/corrupted
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
