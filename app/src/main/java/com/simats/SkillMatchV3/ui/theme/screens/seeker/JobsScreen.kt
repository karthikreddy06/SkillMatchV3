package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.simats.SkillMatchV3.network.JobsResponse
import com.simats.SkillMatchV3.ui.model.Job
import com.simats.SkillMatchV3.ui.model.JobUiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun JobsScreen(
    navController: NavController
) {
    val context = LocalContext.current
    var jobs by remember { mutableStateOf<List<Job>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        Log.d("JOBS_API_REQUEST", "Starting API request to fetch jobs...")
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        
        apiService.getJobs().enqueue(object : Callback<JobsResponse> {
            override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        jobs = body.jobs
                        isLoading = false
                        Log.d("JOBS_API_SUCCESS", "Successfully fetched ${jobs.size} jobs")
                    } else {
                        jobs = emptyList()
                        isLoading = false
                        Log.d("JOBS_API_SUCCESS", "Fetched 0 jobs (empty or status false)")
                    }
                } else {
                    Log.e("JOBS_API_FAILURE", "API Error: ${response.code()} ${response.message()}")
                    error = "Failed to load jobs: ${response.message()}"
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                Log.e("JOBS_API_FAILURE", "Network Error: ${t.localizedMessage}")
                error = "Network error. Please try again."
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .padding(16.dp)
    ) {
        Text(
            "Explore Jobs",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red)
            }
        } else if (jobs.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No jobs available at the moment.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(jobs) { job ->
                    // Convert API model to UI model
                    val uiModel = JobUiModel(
                        id = job.id,
                        title = job.title,
                        company = job.companyName,
                        location = job.location,
                        salary = "₹${job.salaryMin.toInt()} - ₹${job.salaryMax.toInt()}",
                        description = "Job Type: ${job.jobType}\nSkills: ${job.requiredSkills.joinToString(", ")}",
                        latitude = job.latitude,
                        longitude = job.longitude
                    )
                    
                    JobCard(uiModel) {
                        // navController.navigate(...) can be added here when job details screen is ready
                    }
                }
            }
        }
    }
}

@Composable
fun JobCard(job: JobUiModel, onClick: (JobUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(job) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(job.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(job.company, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(job.location, color = Color.Blue, fontSize = 12.sp)
                Text(job.salary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
        }
    }
}
