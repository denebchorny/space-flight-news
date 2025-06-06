package com.denebchorny.feature.articles.domain.usecase

import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

class FetchArticlesUseCaseTest {

    private val repositoryMock = mockk<ArticlesRepository>(relaxed = true)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `UseCase calls ArticlesRepository#fetchArticles`() = runTest {
        // GIVEN
        val limit = 10
        val offset = 0
        val usecase = FetchArticlesUseCase(repositoryMock)

        // WHEN
        usecase(limit = limit, offset = offset)

        // THEN
        coVerify(exactly = 1) { repositoryMock.fetchArticles(limit, offset) }
    }
}