package com.denebchorny.core.model.pagination

import com.denebchorny.core.model.article.Article

/**
 * A paginated list of [Article] items.
 *
 *  - limit: Number of results to return per page
 *  - offset: The initial index from which to return the results
 *  - results: list of [Article]
 */
data class PaginatedArticleList(
    val limit: Int,
    val offset: Int,
    val results: List<Article>
)