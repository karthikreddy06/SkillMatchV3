package com.simats.SkillMatchV3.ui.theme.screens.employer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerCandidateProfileScreen(
    navController: NavController,
    applicantName: String
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Candidate Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAFAFA))
            )
        },
        bottomBar = {
            Column(modifier = Modifier.background(Color.White).padding(16.dp)) {
                 Button(
                    onClick = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88FF)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Schedule Interview", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                 Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    TextButton(onClick = { /* TODO */ }) { Text("Message") }
                    TextButton(onClick = { /* TODO */ }) { Text("Add to Shortlist") }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFAFAFA))
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            
            // --- Header Card ---
            Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = Color(0xFF1E88FF), shape = CircleShape, modifier = Modifier.size(52.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Person, null, tint = Color.White)
                                }
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(applicantName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("UX Designer", color = Color.Gray, fontSize = 14.sp)
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("New York, NY", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                        Surface(color = Color(0xFFE3F2FD), shape = RoundedCornerShape(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
                                Icon(Icons.Default.Star, null, tint = Color(0xFF1E88FF), modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("95% Match", color = Color(0xFF1E88FF), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                    HorizontalDivider()
                    InfoRow(icon = Icons.Default.Email, text = "alex@example.com")
                    InfoRow(icon = Icons.Default.Phone, text = "(555) 123-4567")
                }
            }
            
            // --- Other Cards ---
            SectionCard(title = "Professional Summary") {
                Text("Passionate UX designer with 5+ years of experience creating user-centric digital products. Proven track record of improving user engagement and satisfaction through thoughtful design.", color = Color.DarkGray, lineHeight = 20.sp)
            }
             SectionCard(title = "Skills") {
                FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
                    SkillChip("UI Design")
                    SkillChip("UX Research")
                    SkillChip("Wireframing")
                    SkillChip("Prototyping")
                    SkillChip("User Testing")
                    SkillChip("Figma")
                    SkillChip("Sketch")
                    SkillChip("Adobe XD")
                }
            }
             SectionCard(title = "Experience") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ExperienceItem("Senior UX Designer", "Design Studio", "2020 - Present", "Led UX design for multiple high-profile projects, conducted user research, and mentored junior designers.")
                    ExperienceItem("UX Designer", "Creative Agency", "2018 - 2020", "Designed user interfaces and experiences for web and mobile applications.")
                }
            }
             SectionCard(title = "Education") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    EducationItem("Master of Design", "Design University", "2018")
                    EducationItem("Bachelor of Arts in Design", "State University", "2016")
                }
            }
             SectionCard(title = "Resume") {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Description, null, tint = Color.Gray)
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Resume.pdf", fontWeight = FontWeight.Medium)
                            Text("2.3 MB", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                    TextButton(onClick = {}) { Text("Download") }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun SectionCard(title: String, content: @Composable () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, color = Color.DarkGray, fontSize = 14.sp)
    }
}

@Composable
fun ExperienceItem(title: String, company: String, duration: String, description: String) {
    Column {
        Text(title, fontWeight = FontWeight.Bold)
        Text(company, fontSize = 14.sp, color = Color.DarkGray)
        Text(duration, fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(4.dp))
        Text(description, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Composable
fun EducationItem(degree: String, school: String, year: String) {
    Column {
        Text(degree, fontWeight = FontWeight.Bold)
        Text(school, fontSize = 14.sp, color = Color.DarkGray)
        Text(year, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun SkillChip(text: String) {
    Surface(color = Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp)) {
        Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 13.sp)
    }
}
