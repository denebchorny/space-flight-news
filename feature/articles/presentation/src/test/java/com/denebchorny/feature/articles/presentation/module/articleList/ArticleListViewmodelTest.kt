package com.denebchorny.feature.articles.presentation.module.articleList

import com.denebchorny.core.common.jvm.result.Outcome
import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.domain.error.ArticleError
import com.denebchorny.feature.articles.domain.usecase.FetchArticlesUseCase
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent
import com.denebchorny.feature.articles.presentation.module.articleList.state.ArticleListUiMode
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleListViewmodelTest {

    private val dispatcher = StandardTestDispatcher()
    private val fetchArticlesUseCase = mockk<FetchArticlesUseCase>()
    private lateinit var viewModel: ArticleListViewmodel

    private var limit: Int = 10
    private var offset: Int = 0
    private val article = Article(
        id = 1,
        title = "Sample",
        authors = emptyList(),
        url = "",
        imageUrl = "",
        newsSite = "",
        summary = "",
        publishedAt = "2025-01-01T00:00:00Z",
        updatedAt = "2025-01-02T00:00:00Z",
        featured = false,
        launches = emptyList(),
        events = emptyList()
    )

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = ArticleListViewmodel(fetchArticlesUseCase, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `fetchArticles updates state on success`() = runTest {
        // GIVEN
        coEvery { fetchArticlesUseCase(any(), any()) } returns Outcome.Success(listOf(article))

        // WHEN
        viewModel.onStart()
        advanceUntilIdle()

        // THEN
        coVerify { fetchArticlesUseCase(limit, offset) }
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(ArticleListUiMode.Content, state.uiMode)
        assertEquals(1, state.items.size)
    }

    @Test
    fun `fetchArticles sets Retry mode on error when no articles`() = runTest {
        // GIVEN
        coEvery {
            fetchArticlesUseCase(any(), any())
        } returns Outcome.Error(ArticleError.JustAnError)

        // WHEN
        viewModel.onStart() // triggers fetchArticles()
        advanceUntilIdle()

        // THEN
        coVerify { fetchArticlesUseCase(limit, offset) }
        val state = viewModel.uiState.value
        assertEquals(ArticleListUiMode.Retry, state.uiMode)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onSearchQueryChanged updates state with query`() = runTest {
        // WHEN
        viewModel.onEvent(ArticleListUIEvent.OnSearchQueryChanged("Space"))
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        assertEquals("Space", state.searchQuery)
    }

    @Test
    fun `onDismissBottomSheet true sets showDialog`() = runTest {
        // GIVEN
        viewModel.onEvent(ArticleListUIEvent.OnMenuItemClicked) // true

        // WHEN
        viewModel.onEvent(ArticleListUIEvent.OnDismissBottomSheet) // false

        // THEN
        assertFalse(viewModel.uiState.value.showDialog)
    }

    @Test
    fun `onPullToRefresh triggers refresh and sets isRefreshing`() = runTest {
        // GIVEN
        coEvery { fetchArticlesUseCase(any(), any()) } returns Outcome.Success(listOf(article))

        // WHEN
        viewModel.onEvent(ArticleListUIEvent.OnPullToRefresh)
        advanceUntilIdle()

        // THEN
        coVerify { fetchArticlesUseCase(limit, offset) }
        val state = viewModel.uiState.value
        assertFalse(state.isRefreshing)
        assertEquals(1, state.items.size)
    }
}