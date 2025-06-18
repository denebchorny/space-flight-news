package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.article.Article
import com.denebchorny.core.model.author.Author
import com.denebchorny.core.model.event.Event
import com.denebchorny.core.model.launch.Launch
import com.denebchorny.feature.articles.data.local.entity.ArticleEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun ArticleEntity.toArticle(json: Json): Article {
    return Article(
        id = this.id,
        title = this.title,
        // Assuming authorsJson, launchesJson, eventsJson are non-null strings from DB
        // If they can be null, add appropriate null handling
        authors = json.decodeFromString<List<Author>>(this.authorsJson),
        url = this.url,
        imageUrl = this.imageUrl,
        newsSite = this.newsSite,
        summary = this.summary,
        publishedAt = this.publishedAt,
        updatedAt = this.updatedAt,
        featured = this.featured,
        launches = json.decodeFromString<List<Launch>>(this.launchesJson),
        events = json.decodeFromString<List<Event>>(this.eventsJson)
    )
}

fun List<ArticleEntity>.toDomainArticles(json: Json): List<Article> = this.map { it.toArticle(json) }

fun Article.toArticleEntity(json: Json): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        title = this.title,
        authorsJson = json.encodeToString(this.authors),
        url = this.url,
        imageUrl = this.imageUrl,
        newsSite = this.newsSite,
        summary = this.summary,
        publishedAt = this.publishedAt,
        updatedAt = this.updatedAt,
        featured = this.featured,
        launchesJson = json.encodeToString(this.launches),
        eventsJson = json.encodeToString(this.events)
    )
}
