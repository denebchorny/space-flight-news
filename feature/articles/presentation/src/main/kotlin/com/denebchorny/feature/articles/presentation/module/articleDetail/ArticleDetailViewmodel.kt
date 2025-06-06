package com.denebchorny.feature.articles.presentation.module.articleDetail

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
import com.denebchorny.feature.articles.presentation.mappers.toNewsArticle
import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailParams
import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailUIEvent
import com.denebchorny.feature.articles.presentation.module.articleDetail.state.ArticleDetailScreenState
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
class ArticleDetailViewmodel @Inject constructor(
    val fetchArticlesUseCase: FetchArticlesUseCase
) : ViewModel(), LifecycleListener, UIEventListener<ArticleDetailUIEvent> {

    // ATTRIBUTES ---------------------------------------------------------------------------------
    private lateinit var article: ArticleDetailParams

    // INTERACTION --------------------------------------------------------------------------------
    private val state = MutableStateFlow(ArticleDetailScreenState())
    val uiState: StateFlow<ArticleDetailScreenState> = state.asStateFlow()

    override fun onEvent(event: ArticleDetailUIEvent) {
        when (event) {
            is ArticleDetailUIEvent.OnInitialize -> onInitialize(event.params)
        }
    }

    // HELPERS ------------------------------------------------------------------------------------
    private fun onInitialize(params: ArticleDetailParams) {
        article = params
    }

    override fun onStart() {
        super.onStart()
        state.update { it.copy(article = article.toNewsArticle()) }
    }
}