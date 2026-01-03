package com.simats.SkillMatchV3.ui.screens.employer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerPostJobScreen(navController: NavController) {

    // Job fields
    var jobTitle by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var jobDescription by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }

    // Job type
    var isRemote by remember { mutableStateOf(false) }

    // Location coordinates (CRITICAL)
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    // Error state
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post a New Job", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {

                    // 🔴 VALIDATION (THIS IS THE FIX)
                    if (jobTitle.isBlank()) {
                        errorMessage = "Job title is required"
                        return@Button
                    }

                    if (!isRemote) {
                        if (latitude == null || longitude == null) {
                            errorMessage = "Please select a valid job location"
                            return@Button
                        }
                    }

                    if (jobDescription.isBlank() || requirements.isBlank()) {
                        errorMessage = "Description and requirements are required"
                        return@Button
                    }

                    errorMessage = null

                    // ✅ SAFE TO SEND TO BACKEND
                    // sendPostJob(
                    //   title = jobTitle,
                    //   location = if (isRemote) "Remote" else location,
                    //   latitude = latitude,
                    //   longitude = longitude,
                    //   isRemote = isRemote
                    // )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88FF)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Post Job", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                LabelText("Job Title", true)
                OutlinedTextField(
                    value = jobTitle,
                    onValueChange = { jobTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = isRemote,
                        onCheckedChange = {
                            isRemote = it
                            if (it) {
                                location = "Remote"
                                latitude = null
                                longitude = null
                            }
                        }
                    )
                    Text("This is a remote job")
                }
            }

            if (!isRemote) {
                item {
                    LabelText("Job Location", true)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        placeholder = { Text("Select location using map") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            // 🔹 TEMP DEMO (replace with Map Picker later)
                            location = "Hyderabad"
                            latitude = 17.3850
                            longitude = 78.4867
                        }
                    ) {
                        Text("Pick Location on Map")
                    }
                }
            }

            item {
                LabelText("Job Description", true)
                OutlinedTextField(
                    value = jobDescription,
                    onValueChange = { jobDescription = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )
            }

            item {
                LabelText("Requirements", true)
                OutlinedTextField(
                    value = requirements,
                    onValueChange = { requirements = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )
            }

            if (errorMessage != null) {
                item {
                    Text(
                        text = errorMessage!!,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun LabelText(text: String, required: Boolean = false) {
    Row {
        Text(text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        if (required) Text(" *", color = Color.Red)
    }
    Spacer(modifier = Modifier.height(6.dp))
}
