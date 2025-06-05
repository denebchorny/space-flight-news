package com.denebchorny.feature.articles.data.remote.dto

/**
 * A paginated list of [ArticleDTO] items.
 *
 * Mirrors the “PaginatedArticleList” schema:
 *  - count: total number of articles
 *  - next: URL of next page (nullable)
 *  - previous: URL of previous page (nullable)
 *  - results: list of [ArticleDTO]
 */
data class PaginatedArticlesDTO(
    val results: List<ArticleDTO>
)