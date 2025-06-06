package com.denebchorny.feature.articles.presentation.components.emptyView

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.denebchorny.core.designsystem.preview.PreviewScreen
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.ui.component.emptyView.EmptyView
import com.denebchorny.feature.articles.presentation.R

@Composable
internal fun EmptyContent() {
    EmptyView(
        title = stringResource(R.string.articles_empty_title),
        description = stringResource(R.string.articles_empty_description)
    )
}

@PreviewScreen
@Composable
private fun EmptyContent_Preview() {
    ApplicationTheme {
        EmptyContent()
    }
}