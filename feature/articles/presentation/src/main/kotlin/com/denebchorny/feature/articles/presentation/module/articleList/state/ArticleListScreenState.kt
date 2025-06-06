package com.denebchorny.feature.articles.presentation.module.articleList.state

import androidx.compose.runtime.Stable
import com.denebchorny.core.ui.component.card.ArticleItemData

sealed class ArticleListUiMode {
    data object Content : ArticleListUiMode()
    data object Empty : ArticleListUiMode()
    data object Retry : ArticleListUiMode()
}

@Stable
data class ArticleListScreenState(
    val uiMode: ArticleListUiMode = ArticleListUiMode.Content,
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val items: List<ArticleItemData> = listOf()
)