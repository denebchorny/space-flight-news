package com.denebchorny.feature.articles.data.repository

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.core.common.jvm.result.flatMap // For fetchArticles placeholder
import com.denebchorny.core.common.jvm.result.flatMapSuspend // For getArticle
import com.denebchorny.core.model.article.Article // Domain model
// Specific mapper imports
import com.denebchorny.feature.articles.data.mapper.toArticleList // For List<ArticleDTO> -> List<Article>
import com.denebchorny.feature.articles.data.mapper.toArticleEntityList // For List<ArticleDTO> -> List<ArticleEntity>
import com.denebchorny.feature.articles.data.mapper.toDomainArticles // For List<ArticleEntity> -> List<Article>
import com.denebchorny.feature.articles.data.mapper.toArticle // For ArticleDTO -> Article and ArticleEntity -> Article
import com.denebchorny.feature.articles.data.mapper.toArticleEntity // For ArticleDTO -> ArticleEntity
import com.denebchorny.feature.articles.data.local.dao.ArticleDao
import com.denebchorny.feature.articles.data.repository.datasource.ArticlesRemoteDataSource
import com.denebchorny.feature.articles.domain.error.ArticleError
import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepositoryImpl @Inject constructor(
    private val remote: ArticlesRemoteDataSource, // Keep this
    private val articleDao: ArticleDao,      // Add this
    private val json: Json                   // Add this
) : ArticlesRepository {

    override suspend fun fetchArticles(
        limit: Int,
        offset: Int
    ): Outcome<ArticleError, List<Article>> {
        // 1. Try fetching from local DAO
        val localArticles = articleDao.getArticles(limit = limit, offset = offset)
        if (localArticles.isNotEmpty()) {
            // Map List<ArticleEntity> to Domain List<Article>
            // localArticles.toDomainArticles(json) is from ArticleEntityMapper.kt
            return Outcome.Success(localArticles.toDomainArticles(json))
        }

        // 2. If local is empty, fetch from remote
        // remote.fetchArticles returns Outcome<ArticleError, PaginatedArticlesDTO>
        // PaginatedArticlesDTO contains 'results: List<ArticleDTO>'
        return remote.fetchArticles(limit = limit, offset = offset).flatMapSuspend { paginatedResult -> // paginatedResult is PaginatedArticlesDTO
            val remoteArticleDtos = paginatedResult.results
            if (remoteArticleDtos.isNotEmpty()) {
                // 3. Save remote articles (as Entities) to local cache
                // remoteArticleDtos.toArticleEntityList(json) is from ArticleDtoToEntityMapper.kt
                val entitiesToSave = remoteArticleDtos.toArticleEntityList(json)
                articleDao.insertAll(entitiesToSave)
            }

            // 4. Map List<ArticleDTO> to Domain List<Article> to return
            // remoteArticleDtos.toArticleList() is from ArticleDtoMapper.kt
            Outcome.Success(remoteArticleDtos.toArticleList())
        }
        // Errors from remote.fetchArticles() are propagated by flatMapSuspend.
    }

    override suspend fun getArticle(id: Long): Outcome<ArticleError, Article> {
        // 1. Try fetching from local DAO
        val localArticleEntity = articleDao.getArticleById(id)
        if (localArticleEntity != null) {
            // Map ArticleEntity to Domain Article.
            // localArticleEntity.toArticle(json) is from ArticleEntityMapper.kt
            return Outcome.Success(localArticleEntity.toArticle(json))
        }

        // 2. If not in local, fetch from remote.
        return remote.getArticle(id.toInt()).flatMapSuspend { remoteArticleDto -> // remoteArticleDto is ArticleDTO
            // 3. Save remote article (as Entity) to local cache
            // remoteArticleDto.toArticleEntity(json) is from ArticleDtoToEntityMapper.kt
            val entityToSave = remoteArticleDto.toArticleEntity(json)
            articleDao.insert(entityToSave)

            // 4. Map DTO to Domain Article to return to the caller
            // remoteArticleDto.toArticle() is from ArticleDtoMapper.kt
            Outcome.Success(remoteArticleDto.toArticle())
        }
        // Errors from remote.getArticle() are propagated by flatMapSuspend.
    }
}