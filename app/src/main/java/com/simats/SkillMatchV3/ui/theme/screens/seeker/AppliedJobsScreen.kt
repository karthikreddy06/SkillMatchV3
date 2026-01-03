package com.simats.SkillMatchV3.ui.theme.screens.seeker

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
import com.simats.SkillMatchV3.network.AppliedJobsResponse
import com.simats.SkillMatchV3.ui.model.JobUiModel
import com.simats.SkillMatchV3.ui.theme.components.JobCard
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AppliedJobsScreen(navController: NavController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    var appliedJobs by remember { mutableStateOf<List<JobUiModel>>(emptyList()) }
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

        apiService.getMyApplications("Bearer $token").enqueue(object : Callback<AppliedJobsResponse> {
            override fun onResponse(call: Call<AppliedJobsResponse>, response: Response<AppliedJobsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        appliedJobs = body.applications.map { job ->
                            JobUiModel(
                                id = job.job_id.toString(),
                                title = job.title ?: "Unknown Title",
                                company = job.company_name ?: "Unknown Company",
                                location = "Remote", // Defaulting as location isn't in AppliedJob model yet
                                salary = "Unknown",
                                description = "Status: ${job.status}\nApplied: ${job.applied_at}",
                                latitude = 0.0,
                                longitude = 0.0
                            )
                        }
                    } else {
                        appliedJobs = emptyList()
                    }
                } else {
                    error = "Failed to load applied jobs: ${response.message()}"
                }
                isLoading = false
            }

            override fun onFailure(call: Call<AppliedJobsResponse>, t: Throwable) {
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
            Text("Applied Jobs", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        if (isLoading) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error ?: "Unknown error", color = Color.Red)
            }
        } else if (appliedJobs.isEmpty()) {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("You haven't applied to any jobs yet", color = Color.Gray)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(appliedJobs) { job ->
                    JobCard(job) { 
                        // navController.navigate(...) or other actions
                    }
                }
            }
        }
    }
}
