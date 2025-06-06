package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * A paginated list of [ArticleDTO] items.
 *
 * Mirrors the “PaginatedArticleList” schema:
 *  - count: total number of articles
 *  - next: URL of next page (nullable)
 *  - previous: URL of previous page (nullable)
 *  - results: list of [ArticleDTO]
 */
@Serializable
data class PaginatedArticlesDTO(
    val results: List<ArticleDTO>
)