package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.feature.articles.data.local.entity.ArticleEntity
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// This mapper relies on existing mappers in ArticleDtoMapper.kt:
// - List<AuthorDTO> -> List<Author> (via authors.toAuthorList())
// - List<LaunchDTO> -> List<Launch> (via launches.toLaunchList())
// - List<EventDTO>  -> List<Event>  (via events.toEventList())

fun ArticleDTO.toArticleEntity(json: Json): ArticleEntity {
    return ArticleEntity(
        id = this.id,
        title = this.title.orEmpty(),
        // First map DTO sub-objects to Domain sub-objects, then serialize Domain to JSON string
        authorsJson = json.encodeToString(this.authors.toAuthorList()),
        url = this.url.orEmpty(),
        imageUrl = this.imageUrl.orEmpty(),
        newsSite = this.newsSite.orEmpty(),
        summary = this.summary.orEmpty(),
        publishedAt = this.publishedAt.orEmpty(),
        updatedAt = this.updatedAt.orEmpty(),
        featured = this.featured ?: false,
        launchesJson = json.encodeToString(this.launches.toLaunchList()),
        eventsJson = json.encodeToString(this.events.toEventList())
    )
}

fun List<ArticleDTO>.toArticleEntityList(json: Json): List<ArticleEntity> = this.map { it.toArticleEntity(json) }
