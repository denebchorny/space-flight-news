package com.denebchorny.feature.articles.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIResult
import kotlinx.serialization.Serializable

@Serializable
object ArticleGraph

fun NavGraphBuilder.articleGraph(navController: NavController) {
    navigation<ArticleGraph>(startDestination = ArticleListRoute) {
        articleListDestination { result ->
            when (result) {
                is ArticleListUIResult.OnArticleClicked -> {}
            }
        }
    }
}