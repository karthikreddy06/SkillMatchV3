package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.JobsResponse
import com.simats.SkillMatchV3.ui.model.JobUiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchJobsScreen(
    navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var allJobs by remember { mutableStateOf<List<JobUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    // Fetch jobs from API
    LaunchedEffect(Unit) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        apiService.getJobs().enqueue(object : Callback<JobsResponse> {
            override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        allJobs = body.jobs.map { job ->
                            JobUiModel(
                                id = job.id,
                                title = job.title,
                                company = job.companyName,
                                location = job.location,
                                salary = "₹${job.salaryMin.toInt()} - ₹${job.salaryMax.toInt()}",
                                description = "Job Type: ${job.jobType}",
                                latitude = job.latitude,
                                longitude = job.longitude
                            )
                        }
                        isLoading = false
                    } else {
                        isLoading = false
                    }
                } else {
                    error = "Failed to load jobs"
                    isLoading = false
                }
            }

            override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                error = "Network error: ${t.localizedMessage}"
                isLoading = false
            }
        })
    }

    val filteredJobs = if (searchQuery.isBlank()) {
        allJobs
    } else {
        allJobs.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.company.contains(searchQuery, ignoreCase = true) ||
            it.location.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by title, company, or city") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color(0xFF1E88FF),
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(error ?: "Unknown error", color = Color.Red, fontSize = 16.sp)
            }
        } else if (filteredJobs.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No jobs found", color = Color.Gray, fontSize = 16.sp)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredJobs) { job ->
                    SearchJobCard(job) {
                        // Navigate to detail
                    }
                }
            }
        }
    }
}

@Composable
fun SearchJobCard(job: JobUiModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(job.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(job.company, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(job.location, color = Color.Blue, fontSize = 12.sp)
        }
    }
}
