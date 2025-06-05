package com.denebchorny.core.designsystem.component.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.preview.parameterProvider.EnabledProvider
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme

/**
 * A composable function for creating a positive-styled action button.
 * This button uses the "primary" color scheme to visually indicate actions
 * that are constructive or positive in nature, such as confirming or proceeding.
 *
 * @param modifier Modifier to be applied to the button for styling or layout adjustments.
 * @param enabled Indicates whether the button is enabled. Defaults to `true`.
 * @param icon The icon to display inside the button.
 * @param text The text to display inside the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param marqueeDuration Duration for the marquee animation of the text when applicable.
 * @param onClick Lambda function invoked when the button is clicked.
 *
 * Usage:
 * ```
 * PositiveActionButton(
 *     icon = Icons.Default.Check,
 *     text = "Confirm",
 *     onClick = { confirmAction() }
 * )
 * ```
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PositiveActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    ActionButton(
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        text = text,
        maxLines = maxLines,
        textColor = Theme.colorScheme.primary,
        iconColor = Theme.colorScheme.primary,
        borderColor = Theme.colorScheme.primary,
        marqueeDuration = marqueeDuration,
        onClick = onClick
    )
}

/**
 * A composable function for creating a positive-styled long action button.
 * This is a variant of the PositiveActionButton with larger dimensions,
 * typically used for actions that need more visual emphasis.
 *
 * @param modifier Modifier to be applied to the button for styling or layout adjustments.
 * @param enabled Indicates whether the button is enabled. Defaults to `true`.
 * @param icon The icon to display inside the button.
 * @param text The text to display inside the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param marqueeDuration Duration for the marquee animation of the text when applicable.
 * @param onClick Lambda function invoked when the button is clicked.
 *
 * Usage:
 * ```
 * PositiveLongActionButton(
 *     icon = Icons.Default.ArrowForward,
 *     text = "Continue",
 *     onClick = { proceedToNextStep() }
 * )
 * ```
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PositiveLongActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    LongActionButton(
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        text = text,
        maxLines = maxLines,
        textColor = Theme.colorScheme.primary,
        iconColor = Theme.colorScheme.primary,
        borderColor = Theme.colorScheme.primary,
        marqueeDuration = marqueeDuration,
        onClick = onClick
    )
}

@PreviewComponent
@Composable
private fun PositiveActionButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            PositiveActionButton(
                modifier = Modifier.padding(bottom = 8.dp),
                enabled = enabled,
                icon = Icons.Filled.Add,
                text = "Confirm",
                onClick = {}
            )
            PositiveLongActionButton(
                modifier = Modifier.padding(bottom = 8.dp),
                enabled = enabled,
                icon = Icons.Filled.Add,
                text = "Confirm Action",
                onClick = {}
            )
        }
    }
}
