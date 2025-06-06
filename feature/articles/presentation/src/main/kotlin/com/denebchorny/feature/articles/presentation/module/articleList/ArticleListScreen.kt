package com.denebchorny.feature.articles.presentation.module.articleList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.denebchorny.core.ui.component.shimmer.ShimmerBox
import com.denebchorny.feature.articles.presentation.R
import com.denebchorny.feature.articles.presentation.components.dialog.ItsMeDialog
import com.denebchorny.feature.articles.presentation.components.emptyView.EmptyContent
import com.denebchorny.feature.articles.presentation.components.emptyView.EmptySearchContent
import com.denebchorny.feature.articles.presentation.components.emptyView.RetryContent
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIAction
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnArticleClicked
import com.denebchorny.feature.articles.presentation.module.articleList.interaction.ArticleListUIEvent.OnDismissBottomSheet
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
            onDismissBottomSheet = { viewmodel.onEvent(OnDismissBottomSheet) },
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
                title = stringResource(R.string.articles_appbar_title),
                actions = {
                    MenuItem(
                        icon = Icons.Filled.QuestionMark,
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
                onQueryChange = callbacks.onSearchQueryChanged
            )
            PullToRefresh(
                isRefreshing = state.isRefreshing,
                onRefresh = callbacks.onPullToRefresh
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    when (state.uiMode) {
                        ArticleListUiMode.Content -> ArticleListContent(
                            state = state,
                            scrollBehavior = scrollBehavior,
                            callbacks = callbacks
                        )

                        ArticleListUiMode.Empty -> EmptyContent()
                        ArticleListUiMode.Retry -> RetryContent()
                    }
                }
            }

            if (state.showDialog) {
                ItsMeDialog { callbacks.onDismissBottomSheet() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleListContent(
    state: ArticleListScreenState,
    scrollBehavior: TopAppBarScrollBehavior,
    callbacks: ArticleListCallbacks
) {
    val spacing = LocalSpacing.current
    val listState = rememberLazyListState()

    val filteredItems by remember(state.items, state.searchQuery) {
        derivedStateOf { filterItems(state.items, state.searchQuery) }
    }

    if (state.searchQuery.isNotEmpty() && filteredItems.isEmpty()) {
        EmptySearchContent()
    } else {
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
            if (state.isLoading) {
                repeat(10) {
                    item(key = it) {
                        ShimmerBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                }
            } else {
                items(items = filteredItems, key = { item -> item.id }) { item ->
                    ArticleCard(
                        modifier = Modifier.animateItem(),
                        article = item
                    ) {
                        callbacks.onArticleClicked(item.id)
                    }
                }
            }
        }
    }
}

private fun filterItems(
    items: List<ArticleItemData>,
    query: String
) = items.filter { item ->
    query.isBlank() || item.title.contains(query, ignoreCase = true)
}

@PreviewScreen
@Composable
private fun Screen_Preview() {
    ApplicationTheme {
        ArticleListLayout(
            state = ArticleListScreenState(),
            callbacks = ArticleListCallbacks(
                onArticleClicked = { },
                onMenuItemClicked = { },
                onPullToRefresh = { },
                onSearchQueryChanged = { },
                onDismissBottomSheet = { },
            )
        )
    }
}