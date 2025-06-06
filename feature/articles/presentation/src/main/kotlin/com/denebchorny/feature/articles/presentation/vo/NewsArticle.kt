package com.denebchorny.feature.articles.presentation.vo

data class NewsArticle(
    val title: String,
    val date: String,
    val source: String,
    val imageUrl: String,
    val details: String
) {
    companion object {
        val empty: NewsArticle = NewsArticle(
            title = "",
            date = "",
            source = "",
            imageUrl = "",
            details = ""
        )
    }
}