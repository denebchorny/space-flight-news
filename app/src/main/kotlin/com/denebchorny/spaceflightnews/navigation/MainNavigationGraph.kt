package com.denebchorny.spaceflightnews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ""
    ) {

    }
}