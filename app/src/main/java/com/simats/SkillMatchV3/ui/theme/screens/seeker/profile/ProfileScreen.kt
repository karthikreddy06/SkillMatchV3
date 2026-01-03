package com.simats.SkillMatchV3.ui.theme.screens.seeker.profile

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
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
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.SkillsListResponse
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    val scrollState = rememberScrollState()

    val userName = prefManager.getUserName() ?: "Alex"
    val jobRole = "Android Developer"
    
    var skills by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    
    fun fetchSkills() {
        val token = prefManager.getToken()
        if (token != null) {
             isLoading = true
             val apiService = ApiClient.retrofit.create(ApiService::class.java)
             apiService.getUserSkills("Bearer $token").enqueue(object : Callback<SkillsListResponse> {
                override fun onResponse(call: Call<SkillsListResponse>, response: Response<SkillsListResponse>) {
                    isLoading = false
                    if (response.isSuccessful && response.body()?.status == true) {
                        val fetchedSkills = response.body()!!.skills.map { it.name }
                        skills = fetchedSkills
                        prefManager.saveSkills(fetchedSkills)
                        Log.d("GET_USER_SKILLS_SUCCESS", "Fetched ${fetchedSkills.size} user skills")
                    } else {
                        Log.e("GET_USER_SKILLS_FAILURE", "Failed to fetch user skills")
                    }
                }

                override fun onFailure(call: Call<SkillsListResponse>, t: Throwable) {
                    isLoading = false
                    Log.e("GET_USER_SKILLS_FAILURE", "Network error: ${t.localizedMessage}")
                }
             })
        } else {
            isLoading = false
        }
    }

    LaunchedEffect(Unit) {
        fetchSkills()
    }
    
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val skillsUpdatedState = savedStateHandle?.get<Boolean>("skills_updated")
    
    LaunchedEffect(skillsUpdatedState) {
        if (skillsUpdatedState == true) {
            fetchSkills()
            savedStateHandle?.set("skills_updated", false) 
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = userName.take(1).uppercase(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = jobRole,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { navController.navigate(NavRoutes.EDIT_PROFILE) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Edit Profile")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Skills",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { navController.navigate(NavRoutes.ADD_SKILLS) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Skills", tint = MaterialTheme.colorScheme.primary)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }
        } else if (skills.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { navController.navigate(NavRoutes.ADD_SKILLS) },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                Box(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No skills added yet. Tap to add.", color = Color.Gray)
                }
            }
        } else {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skills.forEach { skill ->
                    AssistChip(
                        onClick = { navController.navigate(NavRoutes.ADD_SKILLS) },
                        label = { Text(skill) },
                        leadingIcon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileMenuItem(
            icon = Icons.Default.Bookmark,
            title = "Saved Jobs",
            onClick = { navController.navigate(NavRoutes.SEEKER_SAVED_JOBS) } 
        )
        
        ProfileMenuItem(
            icon = Icons.Default.Work,
            title = "Applied Jobs",
            onClick = { navController.navigate(NavRoutes.SEEKER_APPLIED_JOBS) }
        )
        
        ProfileMenuItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            onClick = { navController.navigate(NavRoutes.SEEKER_NOTIFICATIONS) }
        )
        
        ProfileMenuItem(
            icon = Icons.Default.Settings,
            title = "Settings",
            onClick = { navController.navigate(NavRoutes.SEEKER_SETTINGS) }
        )

        ProfileMenuItem(
            icon = Icons.Default.ReportProblem,
            title = "Report Issue",
            onClick = { navController.navigate(NavRoutes.REPORT_ISSUE) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                prefManager.clearSession()
                onLogout()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer, contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Log Out", fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
