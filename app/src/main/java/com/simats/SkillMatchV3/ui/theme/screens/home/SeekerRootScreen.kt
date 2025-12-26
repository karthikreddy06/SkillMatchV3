package com.simats.SkillMatchV3.ui.theme.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.ui.components.SeekerBottomBar
import com.simats.SkillMatchV3.ui.screens.profile.ProfileScreen
import com.simats.SkillMatchV3.ui.screens.seeker.*
import com.simats.SkillMatchV3.ui.theme.screens.seeker.JobDetailScreen
import com.simats.SkillMatchV3.ui.theme.screens.seeker.profile.EditProfileScreen

@Composable
fun SeekerRootScreen(
    mainNavController: NavHostController
) {
    val bottomNavController = rememberNavController()
    val backStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    var jobsMode by remember { mutableStateOf(JobsMode.SAVED) }
    var selectedJob by remember { mutableStateOf<JobUiModel?>(null) }

    val onJobClick: (JobUiModel) -> Unit = { job ->
        selectedJob = job
        bottomNavController.navigate(NavRoutes.JOB_DETAIL)
    }

    Scaffold(
        bottomBar = {
            SeekerBottomBar(
                navController = bottomNavController,
                currentRoute = currentRoute
            )
        }
    ) { padding ->

        NavHost(
            navController = bottomNavController,
            startDestination = NavRoutes.SEEKER_HOME,
            modifier = Modifier.padding(padding)
        ) {

            composable(NavRoutes.SEEKER_HOME) {
                SeekerHomeScreen(
                    onFindJobsNearby = { bottomNavController.navigate(NavRoutes.MAP) },
                    onSavedJobs = {
                        jobsMode = JobsMode.SAVED
                        bottomNavController.navigate(NavRoutes.JOBS)
                    },
                    onAppliedJobs = {
                        jobsMode = JobsMode.APPLIED
                        bottomNavController.navigate(NavRoutes.JOBS)
                    },
                    onAIRecommendations = {
                        bottomNavController.navigate(NavRoutes.AI_RECOMMENDATIONS)
                    }
                )
            }

            composable(NavRoutes.MAP) {
                FindJobsNearbyScreen(onJobClick = onJobClick)
            }

            composable(NavRoutes.JOBS) {
                when (jobsMode) {
                    JobsMode.SAVED -> SavedJobsScreen(
                        onBack = { bottomNavController.popBackStack() },
                        onJobClick = onJobClick
                    )

                    JobsMode.APPLIED -> AppliedJobsScreen(
                        onBack = { bottomNavController.popBackStack() },
                        onJobClick = onJobClick
                    )
                }
            }

            composable(NavRoutes.AI_RECOMMENDATIONS) {
                AIRecommendationsScreen(onJobClick = onJobClick)
            }

            composable(NavRoutes.CHAT) {
                ChatScreen()
            }

            composable(NavRoutes.SEEKER_PROFILE) {
                ProfileScreen(
                    onEditProfile = {
                        bottomNavController.navigate(NavRoutes.EDIT_PROFILE)
                    }
                )
            }

            composable(NavRoutes.EDIT_PROFILE) {
                EditProfileScreen(
                    onBack = { bottomNavController.popBackStack() }
                )
            }

            composable(NavRoutes.JOB_DETAIL) {
                selectedJob?.let { job ->
                    JobDetailScreen(
                        job = job,
                        onBack = { bottomNavController.popBackStack() },
                        onApply = {},
                        onSave = {}
                    )
                }
            }
        }
    }
}
