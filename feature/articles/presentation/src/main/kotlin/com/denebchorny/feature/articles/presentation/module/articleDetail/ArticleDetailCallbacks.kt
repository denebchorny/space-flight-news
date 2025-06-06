package com.denebchorny.feature.articles.presentation.module.articleDetail

data class ArticleDetailCallbacks(
    val onSocialClicked: (url: String) -> Unit,
    val onBack: () -> Unit
)