package com.simats.SkillMatchV3.ui.theme.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.* // This covers NavigationBar, NavigationBarItem, Icon, Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.simats.SkillMatchV3.navigation.NavRoutes

@Composable
fun EmployerBottomBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar {
        BottomItem("Home", Icons.Default.Home, NavRoutes.EMPLOYER_HOME, navController, currentRoute)
        BottomItem("Post", Icons.Default.Add, NavRoutes.EMPLOYER_POST_JOB, navController, currentRoute)
        BottomItem("Applicants", Icons.Default.People, NavRoutes.EMPLOYER_APPLICANTS, navController, currentRoute)
        BottomItem("Chat", Icons.Default.Chat, NavRoutes.EMPLOYER_CHAT, navController, currentRoute)
        BottomItem("Profile", Icons.Default.Person, NavRoutes.EMPLOYER_PROFILE, navController, currentRoute)
    }
}

@Composable
private fun RowScope.BottomItem( // Added RowScope receiver here
    label: String,
    icon: ImageVector,
    route: String,
    navController: NavController,
    currentRoute: String?
) {
    NavigationBarItem(
        selected = currentRoute == route,
        onClick = {
            if (currentRoute != route) {
                navController.navigate(route) {
                    popUpTo(NavRoutes.EMPLOYER_HOME) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}
