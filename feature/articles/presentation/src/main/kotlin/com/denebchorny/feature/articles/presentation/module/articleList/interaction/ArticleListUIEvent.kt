package com.denebchorny.feature.articles.presentation.module.articleList.interaction

sealed class ArticleListUIEvent {
    data class OnArticleClicked(val id: Long) : ArticleListUIEvent()
    data class OnSearchQueryChanged(val query: String) : ArticleListUIEvent()
    data object OnDismissBottomSheet : ArticleListUIEvent()
    data object OnMenuItemClicked : ArticleListUIEvent()
    data object OnPullToRefresh : ArticleListUIEvent()
}