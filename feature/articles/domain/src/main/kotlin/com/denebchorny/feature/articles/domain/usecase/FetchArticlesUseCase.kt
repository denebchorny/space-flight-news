package com.denebchorny.feature.articles.domain.usecase

import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import javax.inject.Inject

class FetchArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {
    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ) = repository.fetchArticles(
        limit = limit,
        offset = offset
    )
}