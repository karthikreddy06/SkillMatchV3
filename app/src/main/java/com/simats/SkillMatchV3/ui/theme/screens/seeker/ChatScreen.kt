package com.simats.SkillMatchV3.ui.screens.seeker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ChatScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        /* ---------------- TOP BAR ---------------- */

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            Icon(Icons.Default.Search, contentDescription = null)
        }

        /* ---------------- CHAT LIST ---------------- */

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {

            item {
                ChatItem(
                    name = "Tech Solutions Inc.",
                    lastMessage = "We reviewed your profile.",
                    time = "2m"
                )
            }

            item {
                ChatItem(
                    name = "Digital Innovations",
                    lastMessage = "Interview scheduled.",
                    time = "1h"
                )
            }

            item {
                ChatItem(
                    name = "Creative Labs",
                    lastMessage = "Thanks for applying!",
                    time = "Yesterday"
                )
            }
        }
    }
}

/* ================================================= */
/* ================= CHAT ITEM ====================== */
/* ================================================= */

@Composable
private fun ChatItem(
    name: String,
    lastMessage: String,
    time: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /* Avatar */
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFEFEFEF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Business, contentDescription = null)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(
                    text = lastMessage,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }

            Text(
                text = time,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
