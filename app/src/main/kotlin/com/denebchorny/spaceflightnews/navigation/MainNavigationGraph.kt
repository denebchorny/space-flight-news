package com.denebchorny.spaceflightnews.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.denebchorny.feature.articles.presentation.navigation.ArticleGraph
import com.denebchorny.feature.articles.presentation.navigation.articleGraph

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ArticleGraph
    ) {
        articleGraph(navController)
    }
}