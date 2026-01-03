package com.simats.SkillMatchV3.ui.theme.screens.seeker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.utils.PrefManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobPreferencesScreen(navController: NavController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    
    // State (In a real app, this would be in ViewModel)
    var minSalary by remember { mutableStateOf("") }
    var maxSalary by remember { mutableStateOf("") }
    var commuteDistance by remember { mutableFloatStateOf(20f) }
    
    val jobTypes = listOf("Full-time", "Part-time", "Contract", "Internship")
    val selectedJobTypes = remember { mutableStateListOf<String>() }

    val shifts = listOf("Day Shift", "Night Shift", "Flexible")
    val selectedShifts = remember { mutableStateListOf<String>() }

    val levels = listOf("Entry Level", "Mid Level", "Senior")
    val selectedLevels = remember { mutableStateListOf<String>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Job Preferences", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            
            // Salary Range
            item {
                Text("Salary Range (Annual)", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = minSalary,
                        onValueChange = { if (it.all { char -> char.isDigit() }) minSalary = it },
                        label = { Text("Min") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = maxSalary,
                        onValueChange = { if (it.all { char -> char.isDigit() }) maxSalary = it },
                        label = { Text("Max") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            // Commute Distance
            item {
                Text("Commute Distance: ${commuteDistance.toInt()} miles", fontWeight = FontWeight.Bold)
                Slider(
                    value = commuteDistance,
                    onValueChange = { commuteDistance = it },
                    valueRange = 5f..100f,
                    steps = 18, // (100-10)/5 approx steps
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF0066FF),
                        activeTrackColor = Color(0xFF0066FF)
                    )
                )
            }

            // Job Type
            item {
                Text("Job Type", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    jobTypes.forEach { type ->
                        FilterChip(
                            selected = selectedJobTypes.contains(type),
                            onClick = {
                                if (selectedJobTypes.contains(type)) selectedJobTypes.remove(type)
                                else selectedJobTypes.add(type)
                            },
                            label = { Text(type) }
                        )
                    }
                }
            }

            // Shift Preference
            item {
                Text("Shift Preference", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    shifts.forEach { shift ->
                        FilterChip(
                            selected = selectedShifts.contains(shift),
                            onClick = {
                                if (selectedShifts.contains(shift)) selectedShifts.remove(shift)
                                else selectedShifts.add(shift)
                            },
                            label = { Text(shift) }
                        )
                    }
                }
            }
            
            // Experience Level
            item {
                Text("Experience Level", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    levels.forEach { level ->
                        FilterChip(
                            selected = selectedLevels.contains(level),
                            onClick = {
                                if (selectedLevels.contains(level)) selectedLevels.remove(level)
                                else selectedLevels.add(level)
                            },
                            label = { Text(level) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Logic to save preferences
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF))
                ) {
                    Text("Save Preferences", fontSize = 16.sp)
                }
            }
        }
    }
}
