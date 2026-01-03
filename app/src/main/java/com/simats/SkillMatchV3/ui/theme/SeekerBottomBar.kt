package com.simats.SkillMatchV3.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes

@Composable
fun SeekerBottomBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {

        BottomItem(Icons.Outlined.Home, "Home", NavRoutes.SEEKER_HOME, navController, currentRoute)
        BottomItem(Icons.Outlined.LocationOn, "Map", NavRoutes.MAP, navController, currentRoute)
        BottomItem(Icons.Outlined.Work, "Jobs", NavRoutes.JOBS, navController, currentRoute)
        BottomItem(Icons.Outlined.Chat, "Chat", NavRoutes.CHAT, navController, currentRoute)
        BottomItem(Icons.Outlined.Person, "Profile", NavRoutes.SEEKER_PROFILE, navController, currentRoute)
    }
}

@Composable
private fun RowScope.BottomItem(
    icon: ImageVector,
    label: String,
    route: String,
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBarItem(
        selected = currentRoute == route,
        onClick = {
            // 5️⃣ SeekerBottomBar.kt (STOP SCREEN KILLING)
            // Use SAME seekerNavController, No custom back handling, No popUpTo(route)
            if (currentRoute != route) {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        },
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}
