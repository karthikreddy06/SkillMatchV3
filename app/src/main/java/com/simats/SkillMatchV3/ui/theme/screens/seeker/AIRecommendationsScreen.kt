package com.simats.SkillMatchV3.ui.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AIRecommendationsScreen(
    onJobClick: (JobUiModel) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        /* ---------------- TOP BAR ---------------- */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Text(
                text = "AI Recommendations",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.NotificationsNone, contentDescription = null)
        }

        /* ---------------- INFO CARD ---------------- */

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F7FF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "How it works",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88FF)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Our AI analyzes your skills, experience, and preferences to find jobs that match your profile. The higher the match percentage, the better the job fits your qualifications.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        /* ---------------- SECTION TITLE ---------------- */

        Text(
            text = "Top Matches for You",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(12.dp))

        /* ---------------- JOB LIST ---------------- */

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {

            AIJobItem(
                title = "UX Designer",
                company = "Tech Solutions Inc.",
                location = "New York, NY",
                salary = "$80K - $100K",
                time = "2 days ago",
                match = "95% Match",
                onClick = {
                    onJobClick(
                        JobUiModel(
                            id = "21",
                            title = "UX Designer",
                            company = "Tech Solutions Inc.",
                            location = "New York, NY",
                            salary = "$80K - $100K",
                            description = "AI matched role based on your UX skills."
                        )
                    )
                }
            )

            AIJobItem(
                title = "Frontend Developer",
                company = "Digital Innovations",
                location = "Remote",
                salary = "$90K - $110K",
                time = "1 day ago",
                match = "90% Match",
                onClick = {
                    onJobClick(
                        JobUiModel(
                            id = "22",
                            title = "Frontend Developer",
                            company = "Digital Innovations",
                            location = "Remote",
                            salary = "$90K - $110K",
                            description = "High frontend skill match."
                        )
                    )
                }
            )

            AIJobItem(
                title = "UI/UX Designer",
                company = "Design Studio",
                location = "Remote",
                salary = "$75K - $95K",
                time = "1 week ago",
                match = "85% Match",
                onClick = {
                    onJobClick(
                        JobUiModel(
                            id = "23",
                            title = "UI/UX Designer",
                            company = "Design Studio",
                            location = "Remote",
                            salary = "$75K - $95K",
                            description = "Strong UI/UX alignment with your profile."
                        )
                    )
                }
            )

            AIJobItem(
                title = "UI Developer",
                company = "Tech Innovators",
                location = "Boston, MA",
                salary = "$90K - $110K",
                time = "3 days ago",
                match = "82% Match",
                onClick = {
                    onJobClick(
                        JobUiModel(
                            id = "24",
                            title = "UI Developer",
                            company = "Tech Innovators",
                            location = "Boston, MA",
                            salary = "$90K - $110K",
                            description = "Good UI development role match."
                        )
                    )
                }
            )
        }

        Spacer(Modifier.height(24.dp))
    }
}

/* ================================================= */
/* ================= JOB ITEM ======================= */
/* ================================================= */

@Composable
private fun AIJobItem(
    title: String,
    company: String,
    location: String,
    salary: String,
    time: String,
    match: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFEFEFEF), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Work, contentDescription = null)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(title, fontWeight = FontWeight.Bold)
                Text(company, fontSize = 13.sp, color = Color.Gray)

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(location, fontSize = 12.sp)
                }

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AttachMoney, null, Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(salary, fontSize = 12.sp)

                    Spacer(Modifier.width(12.dp))

                    Icon(Icons.Default.AccessTime, null, Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(time, fontSize = 12.sp)
                }
            }

            Surface(
                shape = CircleShape,
                color = Color(0xFF1E88FF)
            ) {
                Text(
                    text = match,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
