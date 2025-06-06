package com.denebchorny.feature.articles.data.repository

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO
import com.denebchorny.feature.articles.data.remote.dto.AuthorDTO
import com.denebchorny.feature.articles.data.remote.dto.EventDTO
import com.denebchorny.feature.articles.data.remote.dto.LaunchDTO
import com.denebchorny.feature.articles.data.remote.dto.PaginatedArticlesDTO
import com.denebchorny.feature.articles.data.repository.datasource.ArticlesRemoteDataSource
import com.denebchorny.feature.articles.domain.error.ArticleError
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticlesRepositoryImplTest {

    private val remoteMock = mockk<ArticlesRemoteDataSource>(relaxed = true)
    private val repository = ArticlesRepositoryImpl(remoteMock)

    private var limit: Int = 10
    private var offset: Int = 0
    private val articleDto = ArticleDTO(
        id = 1,
        title = "Sample Title",
        authors = listOf(AuthorDTO("Author", null)),
        url = "https://example.com",
        imageUrl = null,
        newsSite = null,
        summary = null,
        publishedAt = "2025-01-13T00:00:00Z",
        updatedAt = "2025-01-15T00:00:00Z",
        featured = false,
        launches = listOf(LaunchDTO("uuid", "provider")),
        events = listOf(EventDTO(1, "provider"))
    )

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `fetchArticles returns mapped success`() = runTest {
        // GIVEN
        val paginated = PaginatedArticlesDTO(listOf(articleDto))
        coEvery { remoteMock.fetchArticles(any(), any()) } returns Outcome.Success(paginated)

        // WHEN
        val result = repository.fetchArticles()

        // THEN
        coVerify { remoteMock.fetchArticles(limit, offset) }
        assertTrue(result is Outcome.Success)
        val articles = (result as Outcome.Success).data
        assertEquals(1, articles.size)
        assertEquals("Sample Title", articles[0].title)
    }

    @Test
    fun `fetchArticles returns error`() = runTest {
        // GIVEN
        coEvery {
            remoteMock.fetchArticles(any(), any())
        } returns Outcome.Error(ArticleError.JustAnError)

        // WHEN
        val result = repository.fetchArticles()

        // THEN
        coVerify { remoteMock.fetchArticles(limit, offset) }
        assertTrue(result is Outcome.Error)
        assertEquals(ArticleError.JustAnError, (result as Outcome.Error).error)
    }

    @Test
    fun `getArticle returns mapped success`() = runTest {
        // GIVEN
        coEvery { remoteMock.getArticle(any()) } returns Outcome.Success(articleDto)

        // WHEN
        val result = repository.getArticle(1)

        // THEN
        coVerify { remoteMock.getArticle(1) }
        assertTrue(result is Outcome.Success)
        assertEquals("Sample Title", (result as Outcome.Success).data.title)
    }

    @Test
    fun `getArticle returns error`() = runTest {
        // GIVEN
        coEvery { remoteMock.getArticle(any()) } returns Outcome.Error(ArticleError.JustAnError)

        // WHEN
        val result = repository.getArticle(1)

        // THEN
        coVerify { remoteMock.getArticle(1) }
        assertTrue(result is Outcome.Error)
        assertEquals(ArticleError.JustAnError, (result as Outcome.Error).error)
    }
}
