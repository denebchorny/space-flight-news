package com.denebchorny.feature.articles.presentation.module.articleList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denebchorny.core.common.android.resources.uiText.UiText
import com.denebchorny.core.common.android.resources.uiText.uiText
import com.denebchorny.core.common.android.viewmodel.LifecycleListener
import com.denebchorny.core.common.android.viewmodel.UIEventListener
import com.denebchorny.core.common.jvm.interactionHolder.UIActionHolder
import com.denebchorny.core.common.jvm.result.onErrorSuspend
import com.denebchorny.core.common.jvm.result.onSuccess
import com.denebchorny.core.designsystem.component.snackbar.factory.SnackbarFactory.errorSnackbar
import com.denebchorny.core.designsystem.component.snackbar.interactionHolder.UISnackbarHolder
import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.domain.usecase.FetchArticlesUseCase
import com.denebchorny.feature.articles.presentation.R
import com.denebchorny.feature.articles.presentation.mappers.toItemDataList
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIAction
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIResult
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIResult.OnArticleClicked
import com.denebchorny.feature.articles.presentation.module.articleList.state.ArticleListScreenState
import com.denebchorny.feature.articles.presentation.module.articleList.state.ArticleListUiMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewmodel @Inject constructor(
    val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel(), LifecycleListener, UIEventListener<ArticleListUIEvent> {

    // ATTRIBUTES ---------------------------------------------------------------------------------
    private var articlesRequestsJob: Job? = null
    private var limit: Int = 10
    private var offset: Int = 0
    private var articles: List<Article> = listOf()

    // INTERACTION --------------------------------------------------------------------------------
    private val state = MutableStateFlow(ArticleListScreenState())
    val uiState: StateFlow<ArticleListScreenState> = state.asStateFlow()

    private val action = UIActionHolder<ArticleListUIAction>()
    val uiAction = action.flow

    private val snackbarHolder = UISnackbarHolder()

    override fun onEvent(event: ArticleListUIEvent) {
        when (event) {
            is ArticleListUIEvent.OnArticleClicked -> onArticleClicked(event.id)
            is ArticleListUIEvent.OnMenuItemClicked -> {}
            is ArticleListUIEvent.OnPullToRefresh -> onPullToRefresh()
            is ArticleListUIEvent.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
        }
    }

    // LIFECYCLE ----------------------------------------------------------------------------------
    override fun onStart() {
        fetchArticles()
    }

    override fun onStop() {
        articlesRequestsJob?.cancel()
        super.onStop()
    }

    // API CALLS ----------------------------------------------------------------------------------
    private fun fetchArticles() {
        articlesRequestsJob?.cancel()
        articlesRequestsJob = viewModelScope.launch {
            fetchArticlesUseCase(limit, offset)
                .onErrorSuspend {
                    if (articles.isEmpty()) {
                        state.update { it.copy(uiMode = ArticleListUiMode.Retry) }
                    } else {
                        showErrorSnackbar(uiText(R.string.articles_error_fetching_data))
                    }
                    setPullToRefresh(false)
                }
                .onSuccess { items ->
                    articles = items
                    state.update {
                        it.copy(
                            items = items.toItemDataList(),
                            uiMode = if (articles.isEmpty()) {
                                ArticleListUiMode.Empty
                            } else {
                                ArticleListUiMode.Content
                            }
                        )
                    }
                    setPullToRefresh(false)
                }
        }
    }

    // HELPERS ------------------------------------------------------------------------------------
    private fun onArticleClicked(id: Long) {
        val article = articles.firstOrNull { it.id == id }
        if (article == null) {
            showErrorSnackbar(uiText(R.string.articles_error_getting_article))
        } else {
            delegateAction(OnArticleClicked(article))
        }
    }

    private fun onPullToRefresh() {
        setPullToRefresh(true)
        fetchArticles()
    }

    private fun setPullToRefresh(value: Boolean) {
        state.update { it.copy(isRefreshing = value) }
    }

    private fun onSearchQueryChanged(query: String) {
        state.update { it.copy(searchQuery = query) }
    }

    private fun delegateAction(result: ArticleListUIResult) = viewModelScope.launch {
        action.emit(ArticleListUIAction.DelegateResult(result))
    }

    private fun showErrorSnackbar(message: UiText) = viewModelScope.launch {
        snackbarHolder.showSnackbar(errorSnackbar(text = message))
    }
}