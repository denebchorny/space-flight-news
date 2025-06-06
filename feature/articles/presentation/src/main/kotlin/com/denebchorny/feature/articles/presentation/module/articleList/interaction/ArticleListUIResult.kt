package com.denebchorny.feature.articles.presentation.module.articleList.interaction

sealed class ArticleListUIResult {
    data class OnArticleClicked(val id: Int) : ArticleListUIResult()
}