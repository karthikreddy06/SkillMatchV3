package com.simats.SkillMatchV3.ui.theme.screens.seeker

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.simats.SkillMatchV3.network.ApiClient
import com.simats.SkillMatchV3.network.ApiService
import com.simats.SkillMatchV3.network.JobsResponse
import com.simats.SkillMatchV3.ui.model.JobUiModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MapScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val defaultLocation = LatLng(20.5937, 78.9629) // Center of India
    
    var jobs by remember { mutableStateOf<List<JobUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 5f)
    }

    // Fetch jobs from API
    LaunchedEffect(Unit) {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        apiService.getJobs().enqueue(object : Callback<JobsResponse> {
            override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status) {
                        jobs = body.jobs.map { job ->
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
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                Log.e("MapScreen", "Failed to load jobs", t)
                isLoading = false
            }
        })
    }

    var hasLocationPermission by remember {
        mutableStateOf(
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            jobs.forEach { job ->
                Marker(
                    state = MarkerState(position = LatLng(job.latitude, job.longitude)),
                    title = job.title,
                    snippet = job.company
                )
            }
        }

        // Job Cards Overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .padding(bottom = 80.dp) // Space for bottom bar
        ) {
            if (jobs.isNotEmpty()) {
                // Show up to 2 jobs if available
                jobs.take(2).forEach { job ->
                    MapJobCard(job)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else if (isLoading) {
                // Show loading indicator
                Card(
                     modifier = Modifier.fillMaxWidth(),
                     shape = RoundedCornerShape(12.dp),
                     colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                         CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
            }
        }
        
        // My Location Button
        FloatingActionButton(
            onClick = {
                if (!hasLocationPermission) {
                     locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
            containerColor = Color.White,
            contentColor = Color(0xFF1E88FF)
        ) {
            Icon(Icons.Default.MyLocation, contentDescription = "My Location")
        }
    }
}

@Composable
fun MapJobCard(job: JobUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF1E88FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    if (job.company.isNotEmpty()) job.company.take(1) else "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(job.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(job.company, color = Color.Gray, fontSize = 14.sp)
                Text(job.location, color = Color.Blue, fontSize = 12.sp)
            }
        }
    }
}
