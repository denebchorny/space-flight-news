package com.denebchorny.feature.articles.presentation.module.articleDetail.interaction

sealed class ArticleDetailUIEvent {
    data class OnInitialize(val params: ArticleDetailParams) : ArticleDetailUIEvent()
}