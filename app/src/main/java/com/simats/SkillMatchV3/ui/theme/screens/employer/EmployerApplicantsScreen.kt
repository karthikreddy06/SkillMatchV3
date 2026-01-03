package com.simats.SkillMatchV3.ui.screens.employer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow

// Define an enum for status to make filtering easier
enum class ApplicantStatus {
    ALL, NEW, SHORTLISTED, REJECTED
}

data class ApplicantUiModel(
    val id: Int,
    val name: String,
    val role: String,
    val appliedFor: String,
    val date: String,
    val match: String,
    val status: String,
    val statusEnum: ApplicantStatus,
    val location: String = "New York, NY",
    val skills: List<String> = listOf("UI Design", "Figma")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerApplicantsScreen(
    initialTab: ApplicantStatus = ApplicantStatus.ALL,
    onApplicantClick: (String) -> Unit = {},
    onMessageClick: (String) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember(initialTab) { mutableStateOf(initialTab) }
    var showFilterSheet by remember { mutableStateOf(false) }

    // Filter States
    var selectedJobRole by remember { mutableStateOf<String?>(null) }
    var selectedLocation by remember { mutableStateOf<String?>(null) }
    var selectedSkill by remember { mutableStateOf<String?>(null) }

    // Dummy Data
    val allApplicants = remember {
        listOf(
            ApplicantUiModel(1, "Alex Johnson", "UX Designer", "UX Designer", "May 15, 2023", "95 % Match", "New", ApplicantStatus.NEW, "New York, NY", listOf("Figma", "Prototyping")),
            ApplicantUiModel(2, "Sarah Williams", "Product Designer", "UX Designer", "May 12, 2023", "88 % Match", "Shortlisted", ApplicantStatus.SHORTLISTED, "San Francisco, CA", listOf("Sketch", "Wireframing")),
            ApplicantUiModel(3, "Michael Brown", "UI/UX Designer", "UX Designer", "May 10, 2023", "75 % Match", "Rejected", ApplicantStatus.REJECTED, "Remote", listOf("Adobe XD")),
            ApplicantUiModel(4, "Emily Davis", "Frontend Developer", "Frontend Developer", "May 16, 2023", "92 % Match", "New", ApplicantStatus.NEW, "New York, NY", listOf("React", "Kotlin")),
            ApplicantUiModel(5, "David Wilson", "Backend Dev", "Backend Engineer", "May 14, 2023", "80 % Match", "New", ApplicantStatus.NEW, "Remote", listOf("Java", "Spring Boot"))
        )
    }

    // Filter Logic
    val filteredApplicants = allApplicants.filter { applicant ->
        val matchesSearch = applicant.name.contains(searchQuery, ignoreCase = true) ||
                            applicant.role.contains(searchQuery, ignoreCase = true) ||
                            applicant.appliedFor.contains(searchQuery, ignoreCase = true)
        
        val matchesTab = when (selectedTab) {
            ApplicantStatus.ALL -> true
            else -> applicant.statusEnum == selectedTab
        }

        val matchesJobRole = selectedJobRole?.let { applicant.appliedFor == it } ?: true
        val matchesLocation = selectedLocation?.let { applicant.location == it } ?: true
        val matchesSkill = selectedSkill?.let { skill -> applicant.skills.any { it.contains(skill, ignoreCase = true) } } ?: true

        matchesSearch && matchesTab && matchesJobRole && matchesLocation && matchesSkill
    }

    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Filter Applicants", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { 
                        selectedJobRole = null
                        selectedLocation = null
                        selectedSkill = null
                    }) {
                        Text("Reset")
                    }
                }
                
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Text("Job Post", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    listOf("UX Designer", "Frontend Developer", "Backend Engineer").forEach { role ->
                        FilterChip(
                            selected = selectedJobRole == role,
                            onClick = { selectedJobRole = if (selectedJobRole == role) null else role },
                            label = { Text(role) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Location", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    listOf("New York, NY", "San Francisco, CA", "Remote").forEach { loc ->
                        FilterChip(
                            selected = selectedLocation == loc,
                            onClick = { selectedLocation = if (selectedLocation == loc) null else loc },
                            label = { Text(loc) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Skills", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    listOf("Figma", "React", "Kotlin", "Java").forEach { skill ->
                        FilterChip(
                            selected = selectedSkill == skill,
                            onClick = { selectedSkill = if (selectedSkill == skill) null else skill },
                            label = { Text(skill) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { showFilterSheet = false },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88FF))
                ) {
                    Text("Apply Filters")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {

        /* ---------------- HEADER ---------------- */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Applicants",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Search Bar
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search applicants...") },
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    
                    Surface(
                        onClick = { showFilterSheet = true },
                        shape = RoundedCornerShape(12.dp),
                        color = if (selectedJobRole != null || selectedLocation != null || selectedSkill != null) Color(0xFFE3F2FD) else Color.White,
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.FilterList, 
                                null,
                                tint = if (selectedJobRole != null || selectedLocation != null || selectedSkill != null) Color(0xFF1E88FF) else Color.Black
                            )
                        }
                    }
                }
            }

            // Tabs
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TabButton("All", selectedTab == ApplicantStatus.ALL) { selectedTab = ApplicantStatus.ALL }
                    TabButton("New", selectedTab == ApplicantStatus.NEW) { selectedTab = ApplicantStatus.NEW }
                    TabButton("Shortlisted", selectedTab == ApplicantStatus.SHORTLISTED) { selectedTab = ApplicantStatus.SHORTLISTED }
                    TabButton("Rejected", selectedTab == ApplicantStatus.REJECTED) { selectedTab = ApplicantStatus.REJECTED }
                }
            }

            // Applicant Cards List
            if (filteredApplicants.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text("No applicants found", color = Color.Gray)
                    }
                }
            } else {
                items(filteredApplicants) { applicant ->
                    // Determine colors based on status
                    val (statusColor, statusTextColor) = when (applicant.statusEnum) {
                        ApplicantStatus.NEW -> Color(0xFFE3F2FD) to Color(0xFF1976D2)
                        ApplicantStatus.SHORTLISTED -> Color(0xFFE8F5E9) to Color(0xFF388E3C)
                        ApplicantStatus.REJECTED -> Color(0xFFFFEBEE) to Color(0xFFD32F2F)
                        else -> Color.LightGray to Color.Black
                    }

                    ApplicantCard(
                        applicant = applicant,
                        statusColor = statusColor,
                        statusTextColor = statusTextColor,
                        matchColor = Color(0xFFE3F2FD),
                        matchTextColor = Color(0xFF1976D2),
                        onViewProfile = { onApplicantClick(applicant.name) },
                        onMessage = { onMessageClick(applicant.name) }
                    )
                }
            }
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF1E88FF) else Color.White,
            contentColor = if (isSelected) Color.White else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        elevation = if (isSelected) ButtonDefaults.buttonElevation(defaultElevation = 2.dp) else ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(text, fontSize = 14.sp)
    }
}

@Composable
fun ApplicantCard(
    applicant: ApplicantUiModel,
    statusColor: Color,
    statusTextColor: Color,
    matchColor: Color,
    matchTextColor: Color,
    onViewProfile: () -> Unit,
    onMessage: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = CircleShape,
                        color = Color(0xFF1E88FF)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.Person, null, tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(applicant.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(applicant.role, color = Color.Gray, fontSize = 14.sp)
                    }
                }
                
                Surface(
                    color = statusColor,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = applicant.status,
                        color = statusTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row {
                         Text("Applied for:  ", fontSize = 12.sp, color = Color.Gray)
                         Text(applicant.appliedFor, fontSize = 12.sp, color = Color.DarkGray)
                    }
                }
                
                 Surface(
                    color = matchColor,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = applicant.match,
                        color = matchTextColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
             Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                 Text(applicant.date, fontSize = 12.sp, color = Color.Gray)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onViewProfile,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE3F2FD)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f).height(40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("View Profile", color = Color(0xFF1E88FF), fontSize = 14.sp)
                }
                
                Button(
                    onClick = onMessage,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F5E9)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.weight(1f).height(40.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Message", color = Color(0xFF388E3C), fontSize = 14.sp)
                }
            }
        }
    }
}
