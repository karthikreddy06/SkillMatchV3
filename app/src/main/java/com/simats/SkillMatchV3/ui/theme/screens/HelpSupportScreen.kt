package com.simats.SkillMatchV3.ui.theme.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.SkillMatchV3.navigation.NavRoutes

data class FAQItem(
    val question: String,
    val answer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpSupportScreen(
    navController: NavController,
    userRole: String // "seeker" or "employer"
) {
    val context = LocalContext.current

    val faqList = if (userRole.equals("seeker", ignoreCase = true)) {
        listOf(
            FAQItem(
                "How do I apply for a job?",
                "Browse jobs on the Home screen, click on a job card to view details, and tap the 'Apply Now' button."
            ),
            FAQItem(
                "How do I upload or update my resume?",
                "Go to Profile > Resume to upload a new PDF or edit your existing details."
            ),
            FAQItem(
                "Can I use the app offline?",
                "You can view previously loaded jobs, but you need an internet connection to apply or chat."
            ),
            FAQItem(
                "How do I update my profile?",
                "Navigate to the Profile tab and click the 'Edit Profile' button to update your skills and bio."
            )
        )
    } else {
        listOf(
            FAQItem(
                "How do I post a job?",
                "Go to the 'Post Job' tab, fill in the required details, and click 'Publish Job'."
            ),
            FAQItem(
                "How do I view applicants?",
                "Navigate to the 'Applicants' tab to see a list of candidates who have applied to your jobs."
            ),
            FAQItem(
                "How does job matching work?",
                "Our AI analyzes job descriptions and candidate profiles to recommend the best matches automatically."
            ),
            FAQItem(
                "How do I manage company profile?",
                "Go to Profile > Edit Company Profile to update your logo, description, and location."
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ------------------------------------------
            // CONTACT US SECTION
            // ------------------------------------------
            item {
                SectionHeader("Contact Us")
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ContactCard(
                        title = "Email Support",
                        icon = Icons.Default.Email,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:karthikkarthik05421@gmail.com")
                            }
                            context.startActivity(Intent.createChooser(intent, "Send Email"))
                        }
                    )
                    
                    ContactCard(
                        title = "Call Center",
                        icon = Icons.Default.Call,
                        modifier = Modifier.weight(1f),
                        onClick = {
                             val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:9390527148")
                            }
                            context.startActivity(intent)
                        }
                    )
                }
            }
            
            // ------------------------------------------
            // REPORT ISSUE SECTION
            // ------------------------------------------
            item {
                 Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(NavRoutes.REPORT_ISSUE) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ReportProblem, 
                            contentDescription = null, 
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                             Text(
                                text = "Report an Issue",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "Found a bug or having trouble?",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // ------------------------------------------
            // FAQ SECTION
            // ------------------------------------------
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionHeader("Frequently Asked Questions")
            }

            items(faqList) { faq ->
                ExpandableFAQCard(faq)
            }
            
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun ContactCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = modifier
            .height(100.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF0066FF),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ExpandableFAQCard(faq: FAQItem) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Rotation"
    )

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = faq.question,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    modifier = Modifier.rotate(rotationState),
                    tint = Color.Gray
                )
            }
            
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = faq.answer,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}
