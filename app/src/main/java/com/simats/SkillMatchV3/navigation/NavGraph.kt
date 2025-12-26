package com.simats.SkillMatchV3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simats.SkillMatchV3.screens.OnboardingScreen
import com.simats.SkillMatchV3.ui.screens.auth.*
import com.simats.SkillMatchV3.ui.screens.home.EmployerHomeScreen
import com.simats.SkillMatchV3.ui.screens.splash.SplashScreen
import com.simats.SkillMatchV3.ui.theme.screens.home.SeekerRootScreen
import com.simats.SkillMatchV3.ui.theme.screens.seeker.SkillsSelectionScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH
    ) {

        composable(NavRoutes.SPLASH) {
            SplashScreen(navController)
        }

        composable(NavRoutes.ONBOARDING) {
            OnboardingScreen(navController)
        }

        composable(NavRoutes.CHOOSE_ROLE) {
            ChooseRoleScreen(navController)
        }

        composable(NavRoutes.LOGIN) {
            LoginScreen(navController)
        }

        composable(NavRoutes.REGISTER) {
            RegisterScreen(navController)
        }

        composable(NavRoutes.SEEKER_ROOT) {
            SeekerRootScreen(navController)
        }

        composable(NavRoutes.EMPLOYER_HOME) {
            EmployerHomeScreen()
        }

        composable(NavRoutes.SKILLS_SELECTION) {
            SkillsSelectionScreen(navController)
        }
    }
}
