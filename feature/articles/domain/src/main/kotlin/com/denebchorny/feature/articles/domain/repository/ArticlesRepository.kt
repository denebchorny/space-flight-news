package com.denebchorny.feature.articles.domain.repository

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.domain.error.ArticleError

interface ArticlesRepository {
    suspend fun fetchArticles(
        limit: Int = 10,
        offset: Int = 0
    ): Outcome<ArticleError, List<Article>>

    suspend fun getArticle(id: Int): Outcome<ArticleError, Article>
}