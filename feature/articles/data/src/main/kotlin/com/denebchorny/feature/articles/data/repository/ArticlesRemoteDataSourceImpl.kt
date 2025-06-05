package com.denebchorny.feature.articles.data.repository

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.core.network.android.adapter.fold
import com.denebchorny.feature.articles.data.remote.api.ArticlesApi
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO
import com.denebchorny.feature.articles.data.remote.dto.PaginatedArticlesDTO
import com.denebchorny.feature.articles.data.repository.datasource.ArticlesRemoteDataSource
import com.denebchorny.feature.articles.domain.error.ArticleError
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRemoteDataSourceImpl @Inject constructor(
    private val api: ArticlesApi
) : ArticlesRemoteDataSource {

    override suspend fun fetchArticles(
        limit: Int,
        offset: Int
    ): Outcome<ArticleError, PaginatedArticlesDTO> {
        return api.fetchArticles(limit, offset).fold(
            onSuccess = { Outcome.Success(it) },
            onError = { _, _ -> Outcome.Error(ArticleError.JustAnError) }
        )
    }

    override suspend fun getArticle(id: Int): Outcome<ArticleError, ArticleDTO> {
        return api.getArticle(id).fold(
            onSuccess = { Outcome.Success(it) },
            onError = { _, _ -> Outcome.Error(ArticleError.JustAnError) }
        )
    }
}