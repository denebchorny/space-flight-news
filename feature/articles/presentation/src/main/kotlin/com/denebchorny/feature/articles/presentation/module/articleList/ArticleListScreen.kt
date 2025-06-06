package com.denebchorny.feature.articles.presentation.module.articleList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.denebchorny.core.designsystem.component.appBar.CenterAlignedTopAppBar
import com.denebchorny.core.designsystem.component.appBar.MenuItem
import com.denebchorny.core.designsystem.component.layout.ScreenLayout
import com.denebchorny.core.designsystem.component.observer.ObserveAsEvents
import com.denebchorny.core.designsystem.component.pullToRefresh.PullToRefresh
import com.denebchorny.core.designsystem.preview.PreviewScreen
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import com.denebchorny.core.ui.component.card.ArticleCard
import com.denebchorny.core.ui.component.card.ArticleItemData
import com.denebchorny.core.ui.component.observer.Lifecycle
import com.denebchorny.core.ui.component.search.SearchBar
import com.denebchorny.feature.articles.presentation.R
import com.denebchorny.feature.articles.presentation.components.EmptyContent
import com.denebchorny.feature.articles.presentation.components.RetryContent
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIAction
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnArticleClicked
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnMenuItemClicked
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnPullToRefresh
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnSearchQueryChanged
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIResult
import com.denebchorny.feature.articles.presentation.module.articleList.state.ArticleListScreenState
import com.denebchorny.feature.articles.presentation.module.articleList.state.ArticleListUiMode

@Composable
fun ArticleListScreen(
    viewmodel: ArticleListViewmodel = hiltViewModel(),
    onResult: (ArticleListUIResult) -> Unit
) {
    val state: ArticleListScreenState by viewmodel.uiState.collectAsStateWithLifecycle()

    Lifecycle(subscriber = viewmodel)

    ObserveAsEvents(viewmodel.uiAction) { action ->
        when (action) {
            is ArticleListUIAction.DelegateResult -> onResult(action.value)
        }
    }

    val callbacks = remember {
        ArticleListCallbacks(
            onArticleClicked = { viewmodel.onEvent(OnArticleClicked(it)) },
            onMenuItemClicked = { viewmodel.onEvent(OnMenuItemClicked) },
            onPullToRefresh = { viewmodel.onEvent(OnPullToRefresh) },
            onSearchQueryChanged = { viewmodel.onEvent(OnSearchQueryChanged(it)) },
        )
    }

    ArticleListLayout(state = state, callbacks = callbacks)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleListLayout(
    state: ArticleListScreenState,
    callbacks: ArticleListCallbacks
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ScreenLayout(
        topBar = {
            CenterAlignedTopAppBar(
                title = stringResource(R.string.articlelist_appbar_title),
                actions = {
                    MenuItem(
                        icon = Icons.Filled.Person,
                        onClick = callbacks.onMenuItemClicked
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBar(
                modifier = Modifier.padding(horizontal = Theme.spacing.default),
                query = state.searchQuery,
                onQueryChange = callbacks.onSearchQueryChanged,
                onClickTrailingIcon = { }
            )
            PullToRefresh(
                isRefreshing = state.isRefreshing,
                onRefresh = callbacks.onPullToRefresh
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    when (state.uiMode) {
                        ArticleListUiMode.Content -> ArticleListContent(
                            items = state.items,
                            scrollBehavior = scrollBehavior,
                            callbacks = callbacks
                        )

                        ArticleListUiMode.Empty -> EmptyContent()
                        ArticleListUiMode.Retry -> RetryContent()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleListContent(
    items: List<ArticleItemData>,
    scrollBehavior: TopAppBarScrollBehavior,
    callbacks: ArticleListCallbacks
) {
    val spacing = LocalSpacing.current
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        state = listState,
        contentPadding = PaddingValues(
            start = spacing.default,
            end = spacing.default,
            top = spacing.default,
            bottom = spacing.large
        ),
        verticalArrangement = Arrangement.spacedBy(spacing.medium),
    ) {
        items(items = items, key = { item -> item.id }) { item ->
            ArticleCard(article = item) {
                callbacks.onArticleClicked(item.id)
            }
        }
    }
}

@PreviewScreen
@Composable
private fun ScreenPreview() {
    ApplicationTheme {
        ArticleListLayout(
            state = ArticleListScreenState(),
            callbacks = ArticleListCallbacks(
                onArticleClicked = { },
                onMenuItemClicked = { },
                onPullToRefresh = { },
                onSearchQueryChanged = { },
            )
        )
    }
}