package com.simats.SkillMatchV3.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simats.SkillMatchV3.screens.OnboardingScreen
import com.simats.SkillMatchV3.ui.screens.auth.*
import com.simats.SkillMatchV3.ui.theme.screens.employer.EmployerRootScreen
import com.simats.SkillMatchV3.ui.screens.splash.SplashScreen
import com.simats.SkillMatchV3.ui.theme.screens.EnablePermissionsScreen
import com.simats.SkillMatchV3.ui.theme.screens.HelpSupportScreen
import com.simats.SkillMatchV3.ui.theme.screens.SettingsScreen
import com.simats.SkillMatchV3.ui.theme.screens.common.NotificationsScreen
import com.simats.SkillMatchV3.ui.theme.screens.common.ReportIssueScreen
import com.simats.SkillMatchV3.ui.theme.screens.home.SeekerRootScreen
import com.simats.SkillMatchV3.ui.theme.screens.seeker.*
import com.simats.SkillMatchV3.ui.theme.screens.seeker.profile.ProfileScreen

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
        
        composable(NavRoutes.ENABLE_PERMISSIONS) {
            EnablePermissionsScreen(navController)
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

        // Seeker Main Entry Point
        composable(NavRoutes.SEEKER_ROOT) {
            SeekerRootScreen(navController)
        }

        // Employer Main Entry Point
        composable(NavRoutes.EMPLOYER_ROOT) {
            EmployerRootScreen(navController)
        }

        // Standalone screens
        composable(NavRoutes.SKILLS_SELECTION) {
            SkillsSelectionScreen(navController)
        }
        
        // Seeker Profile Management
        composable(NavRoutes.ADD_SKILLS) {
            AddSkillsScreen(navController)
        }
        
        composable(NavRoutes.UPLOAD_RESUME) {
            UploadResumeScreen(navController)
        }
        
        composable(NavRoutes.JOB_PREFERENCES) {
            JobPreferencesScreen(navController)
        }
        
        composable(NavRoutes.SEARCH_JOBS) {
            SearchJobsScreen(navController)
        }
        
        // Notifications
        composable(NavRoutes.SEEKER_NOTIFICATIONS) {
             NotificationsScreen(navController)
        }
        
        composable(NavRoutes.EMPLOYER_NOTIFICATIONS) {
             NotificationsScreen(navController)
        }
        
        composable(NavRoutes.SEEKER_SETTINGS) {
            SettingsScreen(navController) {
                navController.navigate(NavRoutes.LOGIN) {
                    popUpTo(0)
                }
            }
        }

        // Settings with arguments (General use)
        composable(NavRoutes.SETTINGS) { backStackEntry ->
            SettingsScreen(navController) {
                navController.navigate(NavRoutes.LOGIN) {
                    popUpTo(0)
                }
            }
        }
        
        // Help & Support with arguments
        composable(NavRoutes.HELP_SUPPORT) { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role") ?: "seeker"
            HelpSupportScreen(navController, role)
        }
        
        // Report Issue
        composable(NavRoutes.REPORT_ISSUE) {
            ReportIssueScreen(navController)
        }
        
        // Chat crash fix
        composable(NavRoutes.CHAT) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Chat coming soon")
            }
        }
    }
}
