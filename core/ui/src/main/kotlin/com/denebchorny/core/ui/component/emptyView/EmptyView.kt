package com.denebchorny.core.ui.component.emptyView

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.spacer.Spacer
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing

@Composable
fun EmptyView(
    title: String,
    description: String,
    background: Color = Theme.colorScheme.surface,
    contentScroll: ScrollState = rememberScrollState()
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(contentScroll)
            .padding(horizontal = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = spacing.large),
            text = title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            style = Theme.typography.titleMedium
        )
        Spacer(spacing.default)
        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = Theme.typography.titleSmall
        )
    }
}

@PreviewComponent
@Composable
private fun EmptyViewPreview() {
    ApplicationTheme {
        EmptyView(
            title = "This is just a title text",
            description = "This is a long description used to notify the user that something is " +
                    "happening. For example, there is no any item listed for you",
        )
    }
}