package com.denebchorny.core.ui.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.spacer.Spacer
import com.denebchorny.core.designsystem.component.textfield.TextField
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.ui.R

/**
 * A composable search bar allowing users to input and change search queries.
 * The search bar supports customization for padding and interaction.
 * It exposes callbacks for query changes and trailing icon clicks.
 * @param modifier A [Modifier] for customizing the appearance and behavior of the search bar.
 * @param enabled A boolean flag that determines if the search bar is enabled or disabled.
 * @param query The current search query value displayed in the search bar.
 * @param trailingIcon An optional [ImageVector] for a trailing icon to be displayed.
 * @param onQueryChange A lambda that is called when the search query changes.
 * @param onClickTrailingIcon A lambda that is called when the trailing icon is clicked.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    query: String = "",
    trailingIcon: ImageVector? = null,
    onQueryChange: (String) -> Unit,
    onClickTrailingIcon: () -> Unit = {}
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        placeholder = stringResource(R.string.coreui_searchbar_placeholder),
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        leadingIcon = Icons.Filled.Search,
        trailingIcon = trailingIcon,
        onTrailingIconClick = onClickTrailingIcon
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    ApplicationTheme {
        Column {
            SearchBar(
                onQueryChange = { },
                onClickTrailingIcon = { }
            )
            Spacer(12.dp)
            SearchBar(
                trailingIcon = Icons.Filled.FilterList,
                onQueryChange = { },
                onClickTrailingIcon = { }
            )
        }
    }
}