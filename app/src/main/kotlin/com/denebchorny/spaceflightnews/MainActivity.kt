package com.denebchorny.spaceflightnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.spaceflightnews.navigation.MainNavigationGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            ApplicationTheme {
                MainNavigationGraph(navController)
            }
        }
    }
}