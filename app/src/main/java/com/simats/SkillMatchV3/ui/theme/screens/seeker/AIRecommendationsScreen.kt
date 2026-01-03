package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import com.simats.SkillMatchV3.network.RecommendedJobsResponse
import com.simats.SkillMatchV3.ui.model.JobUiModel
import com.simats.SkillMatchV3.utils.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AIRecommendationsScreen(navController: NavController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }

    var jobs by remember { mutableStateOf<List<JobUiModel>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val token = prefManager.getToken()
        if (token.isNullOrBlank()) {
            error = "Please login again"
            loading = false
            return@LaunchedEffect
        }

        Log.d("AI_RECOMMEND_API_REQUEST", "Requesting recommendations")

        ApiClient.retrofit.create(ApiService::class.java)
            .getRecommendedJobs("Bearer $token")
            .enqueue(object : Callback<RecommendedJobsResponse> {

                override fun onResponse(
                    call: Call<RecommendedJobsResponse>,
                    response: Response<RecommendedJobsResponse>
                ) {
                    loading = false

                    if (!response.isSuccessful || response.body()?.status != true) {
                        error = "No recommendations found"
                        Log.e("AI_RECOMMEND_API_FAILURE", "Failed to fetch recommendations: ${response.message()}")
                        return
                    }

                    val responseJobs = response.body()!!.jobs
                    if (responseJobs.isEmpty()) {
                         Log.d("AI_RECOMMEND_API_EMPTY", "No recommended jobs found")
                         jobs = emptyList()
                    } else {
                        Log.d("AI_RECOMMEND_API_SUCCESS", "Fetched ${responseJobs.size} recommended jobs")
                        jobs = responseJobs.map {
                            JobUiModel(
                                id = it.id.toString(),
                                title = it.title ?: "Unknown Title",
                                company = it.company_name ?: "Unknown Company",
                                location = it.location ?: "Remote",
                                salary = if (it.salary_min != null && it.salary_max != null) "₹${it.salary_min} - ₹${it.salary_max}" else "Negotiable",
                                description = it.required_skills ?: "",
                                latitude = it.latitude?.toDoubleOrNull() ?: 0.0,
                                longitude = it.longitude?.toDoubleOrNull() ?: 0.0
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<RecommendedJobsResponse>, t: Throwable) {
                    loading = false
                    error = t.localizedMessage ?: "Network error"
                    Log.e("AI_RECOMMEND_API_FAILURE", "Network error: ${t.message}")
                }
            })
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.clickable { navController.popBackStack() }
                )
                Spacer(Modifier.width(12.dp))
                Text("AI Recommendations", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            when {
                loading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                error != null -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Text(error!!, color = Color.Red) }

                jobs.isEmpty() -> Box(
                     Modifier.fillMaxSize(),
                     contentAlignment = Alignment.Center
                ) { Text("No recommendations yet") }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F7FF))
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text("How it works", fontWeight = FontWeight.Bold, color = Color(0xFF1E88FF))
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    "Jobs are ranked based on your saved skills and preferences.",
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                    items(jobs) { job ->
                        RecommendationItem(job) {
                            // navController.navigate(...) or other actions
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendationItem(job: JobUiModel, onClick: (JobUiModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick(job) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(Modifier.padding(16.dp)) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFEFEFEF), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Work, null)
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(job.title, fontWeight = FontWeight.Bold)
                Text(job.company, fontSize = 13.sp, color = Color.Gray)
                Text(job.location, fontSize = 12.sp)
            }

            Surface(shape = CircleShape, color = Color(0xFF1E88FF)) {
                Text(
                    "MATCH",
                    color = Color.White,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}
