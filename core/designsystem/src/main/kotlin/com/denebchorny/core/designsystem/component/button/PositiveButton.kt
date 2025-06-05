package com.denebchorny.core.designsystem.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.preview.parameterProvider.EnabledProvider
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme

/**
 * Creates a positive-styled filled button with an optional icon.
 *
 * @param onClick Lambda invoked when the button is clicked.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param text The text to display on the button.
 * @param icon An optional icon to display alongside the text.
 */
@Composable
fun PositiveFilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: ImageVector? = null,
) {
    FilledButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Theme.colorScheme.primary,
            contentColor = Theme.colorScheme.onPrimary
        ),
        text = text,
        icon = icon
    )
}

/**
 * Creates a positive-styled outlined button with an optional icon.
 *
 * @param onClick Lambda invoked when the button is clicked.
 * @param modifier Modifier applied to the button.
 * @param text The text to display on the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param icon An optional icon to display alongside the text.
 */
@Composable
fun PositiveOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Theme.colorScheme.primary,
        ),
        borderColor = Theme.colorScheme.primary,
        text = text,
        icon = icon
    )
}

/**
 * Creates a positive-styled text button with an optional icon.
 *
 * @param onClick Lambda invoked when the button is clicked.
 * @param modifier Modifier applied to the button.
 * @param enabled Whether the button is enabled or disabled.
 * @param text The text to display on the button.
 * @param icon An optional icon to display alongside the text.
 */
@Composable
fun PositiveTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: ImageVector? = null,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Theme.colorScheme.primary,
        ),
        text = text,
        icon = icon
    )
}

@PreviewComponent
@Composable
private fun PositiveFilledButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PositiveFilledButton(
                onClick = {},
                enabled = enabled,
                text = "Test button"
            )
            PositiveFilledButton(
                onClick = {},
                enabled = enabled,
                text = "Test button",
                icon = Icons.Filled.Add,
            )
        }
    }
}

@PreviewComponent
@Composable
private fun PositiveOutlinedButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PositiveOutlinedButton(
                onClick = {},
                enabled = enabled,
                text = "Test button"
            )
            PositiveOutlinedButton(
                onClick = {},
                enabled = enabled,
                text = "Test button",
                icon = Icons.Filled.Add,
            )
        }
    }
}

@PreviewComponent
@Composable
private fun PositiveTextButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PositiveTextButton(
                onClick = {},
                enabled = enabled,
                text = "Test button"
            )
            PositiveTextButton(
                onClick = {},
                enabled = enabled,
                text = "Test button",
                icon = Icons.Filled.Add,
            )
        }
    }
}