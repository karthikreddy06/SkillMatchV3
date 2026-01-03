package com.simats.SkillMatchV3.ui.theme.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.ui.components.SeekerBottomBar
import com.simats.SkillMatchV3.ui.theme.screens.seeker.*
import com.simats.SkillMatchV3.ui.theme.screens.seeker.profile.ProfileScreen
import com.simats.SkillMatchV3.ui.theme.screens.seeker.profile.EditProfileScreen
import com.simats.SkillMatchV3.ui.theme.screens.common.*
import com.simats.SkillMatchV3.ui.theme.screens.SettingsScreen

@Composable
fun SeekerRootScreen(
    mainNavController: NavHostController
) {
    val seekerNavController = rememberNavController()
    val backStackEntry by seekerNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            SeekerBottomBar(
                navController = seekerNavController,
                currentRoute = currentRoute
            )
        }
    ) { padding ->

        NavHost(
            navController = seekerNavController,
            startDestination = NavRoutes.SEEKER_HOME,
            modifier = Modifier.padding(padding)
        ) {

            // HOME
            composable(NavRoutes.SEEKER_HOME) {
                SeekerHomeScreen(
                    navController = seekerNavController
                )
            }

            // JOBS LIST
            composable(NavRoutes.JOBS) {
                JobsScreen(
                    navController = seekerNavController
                )
            }

            // MAP
            composable(NavRoutes.MAP) {
                MapScreen(
                    navController = seekerNavController
                )
            }

            // CHAT (placeholder)
            composable(NavRoutes.CHAT) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chat Screen")
                }
            }

            // PROFILE
            composable(NavRoutes.SEEKER_PROFILE) {
                ProfileScreen(
                    navController = seekerNavController,
                    onLogout = {
                        mainNavController.navigate(NavRoutes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }

            // SAVED JOBS
            composable(NavRoutes.SEEKER_SAVED_JOBS) {
                SavedJobsScreen(
                    navController = seekerNavController
                )
            }

            // APPLIED JOBS
            composable(NavRoutes.SEEKER_APPLIED_JOBS) {
                AppliedJobsScreen(
                    navController = seekerNavController
                )
            }

            // NOTIFICATIONS
            composable(NavRoutes.SEEKER_NOTIFICATIONS) {
                NotificationsScreen(
                    navController = seekerNavController
                )
            }

            // SETTINGS
            composable(NavRoutes.SEEKER_SETTINGS) {
                SettingsScreen(
                    navController = seekerNavController,
                    onLogout = {
                        mainNavController.navigate(NavRoutes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }

            // REPORT ISSUE
            composable(NavRoutes.REPORT_ISSUE) {
                ReportIssueScreen(
                    navController = seekerNavController
                )
            }

            // AI RECOMMENDATIONS
            composable(NavRoutes.AI_RECOMMENDATIONS) {
                AIRecommendationsScreen(
                    navController = seekerNavController
                )
            }

            // ADD SKILLS
            composable(NavRoutes.ADD_SKILLS) {
                AddSkillsScreen(
                    navController = seekerNavController
                )
            }

            // EDIT PROFILE
            composable(NavRoutes.EDIT_PROFILE) {
                EditProfileScreen(
                    navController = seekerNavController
                )
            }
        }
    }
}
