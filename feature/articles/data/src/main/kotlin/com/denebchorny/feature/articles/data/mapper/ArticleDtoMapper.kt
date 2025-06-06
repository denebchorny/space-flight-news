package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO

fun ArticleDTO.toArticle(): Article {
    return Article(
        id = id,
        title = title,
        authors = authors.toAuthorList(),
        url = url,
        imageUrl = imageUrl.orEmpty(),
        newsSite = newsSite.orEmpty(),
        summary = summary.orEmpty(),
        publishedAt = publishedAt,
        updatedAt = updatedAt,
        featured = featured,
        launches = launches.toLaunchList(),
        events = events.toEventList()
    )
}

fun List<ArticleDTO>.toArticleList(): List<Article> = this.map { it.toArticle() }