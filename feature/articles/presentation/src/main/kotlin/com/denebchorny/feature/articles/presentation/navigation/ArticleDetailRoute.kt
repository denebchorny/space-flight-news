package com.denebchorny.feature.articles.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.denebchorny.feature.articles.presentation.mappers.toParams
import com.denebchorny.feature.articles.presentation.module.articleDetail.ArticleDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetailRoute(
    val title: String,
    val publishedAt: String,
    val authors: String,
    val imageUrl: String,
    val summary: String,
)

fun NavGraphBuilder.articleDetailDestination() {
    composable<ArticleDetailRoute> { backstack ->
        ArticleDetailScreen(
            params = backstack
                .toRoute<ArticleDetailRoute>()
                .toParams(),
        )
    }
}