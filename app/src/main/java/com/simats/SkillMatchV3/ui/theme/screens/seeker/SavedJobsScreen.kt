package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.SavedJobsResponse
import com.simats.SkillMatchV3.ui.model.JobUiModel
import com.simats.SkillMatchV3.ui.theme.components.JobCard
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SavedJobsScreen(navController: NavController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    var savedJobs by remember { mutableStateOf<List<JobUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val token = prefManager.getToken()
        if (token.isNullOrEmpty()) {
            error = "User not logged in"
            isLoading = false
            return@LaunchedEffect
        }

        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        
        apiService.getSavedJobs("Bearer $token").enqueue(object : Callback<SavedJobsResponse> {
            override fun onResponse(call: Call<SavedJobsResponse>, response: Response<SavedJobsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        savedJobs = body.jobs.map { job ->
                            JobUiModel(
                                id = job.id.toString(),
                                title = job.title,
                                company = job.company_name,
                                location = job.location,
                                salary = "₹${job.salary_min} - ₹${job.salary_max}",
                                description = "Job Type: ${job.job_type}",
                                latitude = 0.0,
                                longitude = 0.0
                            )
                        }
                    } else {
                        savedJobs = emptyList()
                    }
                } else {
                    error = "Failed to load saved jobs: ${response.message()}"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<SavedJobsResponse>, t: Throwable) {
                error = "Network error: ${t.localizedMessage}"
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF6F7FB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text("Saved Jobs", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        if (isLoading) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error ?: "Unknown error", color = Color.Red)
            }
        } else if (savedJobs.isEmpty()) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No saved jobs found", color = Color.Gray)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(savedJobs) { job ->
                    JobCard(job) { 
                        // navController.navigate(...) or other actions
                    }
                }
            }
        }
    }
}
