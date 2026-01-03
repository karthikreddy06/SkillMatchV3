package com.simats.SkillMatchV3.ui.theme.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager

data class PermissionItemModel(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val permission: String
)

@Composable
fun EnablePermissionsScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val prefManager = remember { PrefManager(context) }

    // Determine Storage Permission based on SDK
    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Determine Notification Permission (Only needed for API 33+)
    val notificationPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.POST_NOTIFICATIONS
    } else {
        // For older versions, we can treat it as always granted or ignore
        "android.permission.POST_NOTIFICATIONS_COMPAT" 
    }

    val permissionItems = remember {
        listOf(
            PermissionItemModel(
                "Location",
                "Find jobs near you on the map",
                Icons.Default.LocationOn,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PermissionItemModel(
                "Notifications",
                "Get alerts for new job matches",
                Icons.Default.Notifications,
                notificationPermission
            ),
            PermissionItemModel(
                "Storage",
                "Save and access your resume",
                Icons.Default.Folder,
                storagePermission
            ),
            PermissionItemModel(
                "Camera",
                "Upload profile photo or scan documents",
                Icons.Default.CameraAlt,
                Manifest.permission.CAMERA
            )
        )
    }

    // State to track if permissions are granted
    val permissionStates = remember { mutableStateMapOf<String, Boolean>() }
    
    // Function to check permissions
    fun checkPermissions() {
        permissionItems.forEach { item ->
            if (item.permission == "android.permission.POST_NOTIFICATIONS_COMPAT") {
                permissionStates[item.permission] = true
            } else {
                val isGranted = ContextCompat.checkSelfPermission(
                    context,
                    item.permission
                ) == PackageManager.PERMISSION_GRANTED
                permissionStates[item.permission] = isGranted
            }
        }
    }

    // Check on launch and resume
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkPermissions()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Handle denied permanently dialog
    var showSettingsDialog by remember { mutableStateOf(false) }

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        checkPermissions()
    }
    
    // Logic to handle toggle click
    fun onTogglePermission(permission: String, isChecked: Boolean) {
        if (isChecked) {
            // User wants to turn ON
            if (permission == "android.permission.POST_NOTIFICATIONS_COMPAT") {
                permissionStates[permission] = true
                return
            }
            
            val isGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            if (!isGranted) {
                // Check rationale
                if (context is Activity && ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                     multiplePermissionLauncher.launch(permission)
                } else {
                     multiplePermissionLauncher.launch(permission)
                }
            }
        } else {
            // User wants to turn OFF - we can't revoke permissions programmatically.
            // We can only open settings.
            showSettingsDialog = true
        }
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Permission Required") },
            text = { Text("To change this permission, you need to go to App Settings.") },
            confirmButton = {
                TextButton(onClick = {
                    showSettingsDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = "Enable Permissions",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Allow SkillMatch to provide you with the best experience",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(permissionItems) { item ->
                PermissionItemRow(
                    item = item,
                    isChecked = permissionStates[item.permission] ?: false,
                    onToggle = { isChecked ->
                        onTogglePermission(item.permission, isChecked)
                    }
                )
            }
        }
        
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            Text(
                text = "You can change these permissions anytime in Settings",
                modifier = Modifier.padding(16.dp),
                color = Color(0xFF1565C0),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
        
        Button(
            onClick = { 
                prefManager.setOnboardingSeen()
                navController.navigate(NavRoutes.CHOOSE_ROLE) {
                    popUpTo(NavRoutes.ENABLE_PERMISSIONS) { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
            shape = RoundedCornerShape(8.dp),
            enabled = permissionStates.values.any { it }
        ) {
            Text("Continue", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PermissionItemRow(
    item: PermissionItemModel,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isChecked) Color(0xFFE3F2FD) else Color(0xFFF5F5F5),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if (isChecked) Color(0xFF0066FF) else Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = item.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
            
            Switch(
                checked = isChecked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF0066FF),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE0E0E0)
                )
            )
        }
    }
}
