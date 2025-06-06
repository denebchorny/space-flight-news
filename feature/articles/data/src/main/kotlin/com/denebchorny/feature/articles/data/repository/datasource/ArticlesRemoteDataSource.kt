package com.denebchorny.feature.articles.data.repository.datasource

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO
import com.denebchorny.feature.articles.data.remote.dto.PaginatedArticlesDTO
import com.denebchorny.feature.articles.domain.error.ArticleError

interface ArticlesRemoteDataSource {
    suspend fun fetchArticles(
        limit: Int = 10,
        offset: Int = 0
    ): Outcome<ArticleError, PaginatedArticlesDTO>

    suspend fun getArticle(
        id: Int
    ): Outcome<ArticleError, ArticleDTO>
}