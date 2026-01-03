package com.simats.SkillMatchV3.ui.theme.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    
    // Determine user role (safe default is seeker if not found)
    // Fixed: getUserRole() -> getRole()
    val userRole = prefManager.getRole() ?: "seeker"
    
    // State for toggles
    var autoSyncEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            
            // ------------------------------------------
            // SEEKER SETTINGS
            // ------------------------------------------
            if (userRole.equals("seeker", ignoreCase = true)) {
                
                item { SectionHeader("General") }

                item {
                    SettingsToggleItem(
                        title = "Auto-Sync Records",
                        icon = Icons.Default.Sync,
                        isChecked = autoSyncEnabled,
                        onCheckedChange = { autoSyncEnabled = it }
                    )
                }
                
                item {
                    SettingsNavigationItem(
                        title = "Storage Management",
                        icon = Icons.Default.Storage,
                        onClick = { /* Navigate to storage */ }
                    )
                }

                item {
                    SettingsToggleItem(
                        title = "Push Notifications",
                        icon = Icons.Default.Notifications,
                        isChecked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }

                item { SectionHeader("Support") }

                item {
                    SettingsNavigationItem(
                        title = "About App",
                        icon = Icons.Default.Info,
                        onClick = { /* Navigate to About */ }
                    )
                }

                item {
                    SettingsNavigationItem(
                        title = "Help & FAQ",
                        icon = Icons.AutoMirrored.Filled.Help,
                        onClick = { 
                            navController.navigate("help_support/$userRole") 
                        }
                    )
                }

                item {
                    SettingsNavigationItem(
                        title = "Report an Issue",
                        icon = Icons.Default.ReportProblem,
                        onClick = { navController.navigate(NavRoutes.REPORT_ISSUE) }
                    )
                }

            } 
            // ------------------------------------------
            // EMPLOYER SETTINGS
            // ------------------------------------------
            else {
                
                item { SectionHeader("General") }

                item {
                    SettingsToggleItem(
                        title = "Auto-Sync Job Data",
                        icon = Icons.Default.Sync,
                        isChecked = autoSyncEnabled,
                        onCheckedChange = { autoSyncEnabled = it }
                    )
                }

                item {
                    SettingsNavigationItem(
                        title = "Manage Posted Jobs",
                        icon = Icons.Default.Work,
                        onClick = { navController.navigate(NavRoutes.EMPLOYER_ACTIVE_JOBS) }
                    )
                }

                item {
                    SettingsToggleItem(
                        title = "Notification Preferences",
                        icon = Icons.Default.Notifications,
                        isChecked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }

                item { SectionHeader("Support") }

                item {
                    SettingsNavigationItem(
                        title = "About App",
                        icon = Icons.Default.Info,
                        onClick = { /* Navigate to About */ }
                    )
                }

                item {
                    SettingsNavigationItem(
                        title = "Help & FAQ",
                        icon = Icons.AutoMirrored.Filled.Help,
                        onClick = { 
                            navController.navigate("help_support/$userRole")
                        }
                    )
                }

                item {
                    SettingsNavigationItem(
                        title = "Report an Issue",
                        icon = Icons.Default.ReportProblem,
                        onClick = { navController.navigate(NavRoutes.REPORT_ISSUE) }
                    )
                }
            }

            // ------------------------------------------
            // LOGOUT (Common)
            // ------------------------------------------
            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Button(
                    onClick = {
                        prefManager.clearSession()
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.Red
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Out",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

// ------------------------------------------
// REUSABLE COMPONENTS
// ------------------------------------------

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 4.dp)
    )
}

@Composable
fun SettingsToggleItem(
    title: String,
    icon: ImageVector,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF0066FF))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF0066FF),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun SettingsNavigationItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF0066FF))
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
