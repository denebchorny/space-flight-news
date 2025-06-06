package com.denebchorny.feature.articles.presentation.components.emptyView

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.ui.component.emptyView.EmptyView
import com.denebchorny.feature.articles.presentation.R

@Composable
fun EmptySearchContent() {
    EmptyView(
        title = stringResource(R.string.articles_empty_search_title),
        description = stringResource(R.string.articles_empty_search_description)
    )
}

@Preview
@Composable
private fun EmptySearchContent_Preview() {
    ApplicationTheme {
        EmptySearchContent()
    }
}