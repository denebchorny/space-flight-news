package com.denebchorny.feature.articles.domain.usecase

import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchArticlesUseCaseTest {

    @MockK
    private lateinit var repository: ArticlesRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `UseCase calls ArticlesRepository#fetchArticles`() = runTest {
        // GIVEN
        val limit = 10
        val offset = 0
        val usecase = FetchArticlesUseCase(repository)

        // WHEN
        usecase(limit = limit, offset = offset)

        // THEN
        coVerify(exactly = 1) {
            repository.fetchArticles(limit, offset)
        }
    }
}