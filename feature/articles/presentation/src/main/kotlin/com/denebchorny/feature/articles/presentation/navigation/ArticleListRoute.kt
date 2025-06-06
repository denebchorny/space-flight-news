package com.denebchorny.feature.articles.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.denebchorny.feature.articles.presentation.module.articleList.ArticleListScreen
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIResult
import kotlinx.serialization.Serializable

@Serializable
object ArticleListRoute

fun NavGraphBuilder.articleListDestination(onResult: (ArticleListUIResult) -> Unit) {
    composable<ArticleListRoute> {
        ArticleListScreen { result ->
            onResult(result)
        }
    }
}