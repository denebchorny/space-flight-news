package com.denebchorny.feature.articles.presentation.module.articleList

data class ArticleListCallbacks(
    val onArticleClicked: (articleId: Long) -> Unit,
    val onDismissBottomSheet: () -> Unit,
    val onMenuItemClicked: () -> Unit,
    val onPullToRefresh: () -> Unit,
    val onSearchQueryChanged: (query: String) -> Unit,
)
