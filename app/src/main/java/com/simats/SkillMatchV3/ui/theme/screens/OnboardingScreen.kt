package com.simats.SkillMatchV3.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.simats.SkillMatchV3.navigation.NavRoutes
import com.simats.SkillMatchV3.utils.PrefManager
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val gradientColors: List<Color>
)

@Composable
fun OnboardingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefManager = remember { PrefManager(context) }
    val scope = rememberCoroutineScope()
    
    val pages = listOf(
        OnboardingPageData(
            title = "Welcome to SkillMatch",
            description = "Your intelligent job-finding companion.\nWe use AI to match you with the perfect\nopportunities based on your skills and\npreferences.",
            icon = Icons.Default.Widgets,
            gradientColors = listOf(Color(0xFF42A5F5), Color(0xFF1976D2))
        ),
        OnboardingPageData(
            title = "Find Jobs Nearby",
            description = "Discover opportunities close to home\nwith our interactive map view. See job\nlocations, commute times, and apply\nwith one tap.",
            icon = Icons.Default.LocationOn,
            gradientColors = listOf(Color(0xFF66BB6A), Color(0xFF388E3C))
        ),
        OnboardingPageData(
            title = "AI-Powered Matching",
            description = "Our smart algorithm analyzes your skills,\nexperience, and preferences to\nrecommend jobs with the highest match\npercentage.",
            icon = Icons.Default.AutoGraph,
            gradientColors = listOf(Color(0xFFAB47BC), Color(0xFF7B1FA2))
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })

    fun completeOnboarding() {
        // We do NOT set onboarding seen here anymore because the EnablePermissions screen
        // will handle the final "setup complete" logic.
        // However, if the user "Skips", they technically saw onboarding.
        // To be safe and consistent with the previous step, we can set it,
        // or rely on EnablePermissions to set it.
        // Given EnablePermissions is part of the flow, let's just navigate.
        navController.navigate(NavRoutes.ENABLE_PERMISSIONS) {
            popUpTo(NavRoutes.ONBOARDING) { inclusive = true }
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Button (Skip or Back)
                if (pagerState.currentPage == 0) {
                    TextButton(onClick = { completeOnboarding() }) {
                        Text("Skip", color = Color.Gray, fontSize = 16.sp)
                    }
                } else {
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Gray
                        )
                    }
                }

                // Center Indicators
                OnboardingPageIndicator(
                    pageCount = pages.size,
                    currentPage = pagerState.currentPage
                )

                // Right Button (Next)
                Button(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.size - 1) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            } else {
                                completeOnboarding()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0066FF)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text("Next", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { pageIndex ->
            OnboardingPage(pageData = pages[pageIndex])
        }
    }
}

@Composable
fun OnboardingPage(pageData: OnboardingPageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Large Rounded Gradient Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Square card
                .clip(RoundedCornerShape(32.dp))
                .background(Brush.linearGradient(pageData.gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = pageData.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

@Composable
fun OnboardingPageIndicator(pageCount: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFF0066FF) else Color.LightGray)
            )
        }
    }
}
