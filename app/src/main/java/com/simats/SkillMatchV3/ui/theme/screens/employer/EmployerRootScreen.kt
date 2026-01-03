package com.simats.SkillMatchV3.ui.theme.screens.employer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.ui.screens.employer.ActiveJobsScreen
import com.simats.SkillMatchV3.ui.screens.employer.EmployerApplicantsScreen
import com.simats.SkillMatchV3.ui.screens.employer.EmployerPostJobScreen
import com.simats.SkillMatchV3.ui.theme.components.EmployerBottomBar
import com.simats.SkillMatchV3.ui.theme.screens.home.EmployerHomeScreen
import com.simats.SkillMatchV3.ui.theme.screens.SettingsScreen
import com.simats.SkillMatchV3.ui.theme.screens.common.NotificationsScreen

@Composable
fun EmployerRootScreen(rootNavController: NavHostController) {
    val employerNavController = rememberNavController()
    val navBackStackEntry by employerNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Conditionally hide bottom bar on detail screens
            val shouldShowBottomBar = currentRoute in listOf(
                NavRoutes.EMPLOYER_HOME,
                NavRoutes.EMPLOYER_POST_JOB,
                NavRoutes.EMPLOYER_APPLICANTS,
                NavRoutes.EMPLOYER_CHAT,
                NavRoutes.EMPLOYER_PROFILE
            )
            if (shouldShowBottomBar) {
                EmployerBottomBar(
                    navController = employerNavController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = employerNavController,
            startDestination = NavRoutes.EMPLOYER_HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavRoutes.EMPLOYER_HOME) {
                EmployerHomeScreen(
                    onPostJobClick = { employerNavController.navigate(NavRoutes.EMPLOYER_POST_JOB) },
                    onApplicantsClick = { 
                        employerNavController.navigate(NavRoutes.EMPLOYER_APPLICANTS) 
                    },
                    onActiveJobsClick = { employerNavController.navigate(NavRoutes.EMPLOYER_ACTIVE_JOBS) },
                    onNewApplicantsClick = { 
                        employerNavController.navigate(NavRoutes.EMPLOYER_APPLICANTS)
                    }
                )
            }
            composable(NavRoutes.EMPLOYER_ACTIVE_JOBS) {
                ActiveJobsScreen(onBackPress = { employerNavController.popBackStack() })
            }
            composable(NavRoutes.EMPLOYER_POST_JOB) {
                EmployerPostJobScreen(employerNavController)
            }
            composable(NavRoutes.EMPLOYER_APPLICANTS) {
                EmployerApplicantsScreen(
                    onApplicantClick = { applicantName: String ->
                        employerNavController.navigate("${NavRoutes.EMPLOYER_CANDIDATE_PROFILE}/$applicantName")
                    },
                    onMessageClick = { applicantName: String ->
                         employerNavController.navigate("chat_detail/$applicantName")
                    },
                    onBackPress = { employerNavController.popBackStack() }
                )
            }
            composable(NavRoutes.EMPLOYER_CHAT) {
                EmployerChatScreen(
                    onChatClick = { name: String ->
                        employerNavController.navigate("chat_detail/$name")
                    }
                )
            }
            composable(
                route = "${NavRoutes.EMPLOYER_CANDIDATE_PROFILE}/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Applicant"
                EmployerCandidateProfileScreen(employerNavController, name)
            }
            composable(
                route = "chat_detail/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: "Applicant"
                EmployerChatDetailScreen(employerNavController, name)
            }
            composable(NavRoutes.EMPLOYER_PROFILE) {
                EmployerProfileScreen(
                    navController = employerNavController,
                    onLogout = {
                         rootNavController.navigate(NavRoutes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }
            
            composable(NavRoutes.EMPLOYER_NOTIFICATIONS) {
                NotificationsScreen(employerNavController)
            }
            
            // Re-using SettingsScreen but need to ensure it uses the correct context
            // Since we defined SettingsScreen to take a navController and onLogout callback
            composable("settings/employer") {
                SettingsScreen(
                    navController = employerNavController,
                    onLogout = {
                        rootNavController.navigate(NavRoutes.LOGIN) {
                            popUpTo(0)
                        }
                    }
                )
            }
            
            composable(NavRoutes.EMPLOYER_EDIT_PROFILE) {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Edit Profile Screen Placeholder")
                }
            }
        }
    }
}
