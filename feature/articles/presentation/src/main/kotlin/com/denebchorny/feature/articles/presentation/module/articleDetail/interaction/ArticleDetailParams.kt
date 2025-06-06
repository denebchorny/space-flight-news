package com.denebchorny.feature.articles.presentation.module.articleDetail.interaction

data class ArticleDetailParams(
    val title: String,
    val publishedAt: String,
    val authors: String,
    val imageUrl: String,
    val summary: String,
)