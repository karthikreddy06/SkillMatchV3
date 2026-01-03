package com.simats.SkillMatchV3.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.SkillMatchV3.ui.model.JobUiModel

@Composable
fun JobCard(
    job: JobUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(job.title, fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(job.company, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(2.dp))
            Text(job.location, style = MaterialTheme.typography.bodySmall)
        }
    }
}
