package com.denebchorny.feature.articles.data.repository

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.core.common.jvm.result.flatMap
import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.data.mapper.toArticle
import com.denebchorny.feature.articles.data.mapper.toArticleList
import com.denebchorny.feature.articles.data.repository.datasource.ArticlesRemoteDataSource
import com.denebchorny.feature.articles.domain.error.ArticleError
import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepositoryImpl @Inject constructor(
    private val remote: ArticlesRemoteDataSource,
) : ArticlesRepository {

    override suspend fun fetchArticles(
        limit: Int,
        offset: Int
    ): Outcome<ArticleError, List<Article>> {
        return remote.fetchArticles(limit = limit, offset = offset)
            .flatMap { Outcome.Success(it.results.toArticleList()) }
    }

    override suspend fun getArticle(id: Int): Outcome<ArticleError, Article> {
        return remote.getArticle(id)
            .flatMap { Outcome.Success(it.toArticle()) }
    }
}