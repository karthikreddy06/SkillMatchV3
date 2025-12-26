package com.simats.SkillMatchV3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.simats.SkillMatchV3.navigation.NavGraph
import com.simats.SkillMatchV3.ui.theme.SkillMatchV3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkillMatchV3Theme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
