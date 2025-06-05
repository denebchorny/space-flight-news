package com.denebchorny.core.designsystem.component.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.button.TextButton
import com.denebchorny.core.designsystem.component.snackbar.visuals.UiSnackbarVisuals
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarAction
import com.denebchorny.core.designsystem.component.snackbar.vo.UiSnackbarData
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing

/**
 * Displays a Snackbar.
 *
 * @param data The [UiSnackbarData] containing the Snackbar's visuals and actions.
 */
@Composable
fun Snackbar(data: UiSnackbarData) {
    val spacing = LocalSpacing.current
    val visuals = data.visuals
    val contentBottomPadding = remember(visuals.positiveAction, visuals.negativeAction) {
        if (visuals.positiveAction == null && visuals.negativeAction == null) {
            spacing.medium
        } else {
            spacing.none
        }
    }
    SnackbarLayout(data) {
        Row(
            modifier = Modifier
                .padding(top = spacing.medium, bottom = contentBottomPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            visuals.icon?.let { icon ->
                LeadingIcon(icon, visuals.iconTint)
            }
            MessageText(visuals)
            visuals.neutralAction?.let { action ->
                NeutralButton(
                    action = action,
                    contentColor = visuals.contentColor,
                    dismiss = data.dismiss
                )
            }
            visuals.withDismissAction.takeIf { it }?.let {
                DismissIcon(
                    tint = visuals.iconTint,
                    dismiss = data.dismiss
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium, Alignment.End)
        ) {
            visuals.negativeAction?.let { action ->
                RoleButton(action, visuals.contentColor, data.dismiss)
            }
            visuals.positiveAction?.let { action ->
                RoleButton(action, visuals.contentColor, data.dismiss)
            }
        }
    }
}

/**
 * Layout for the Snackbar.
 *
 * @param data The [UiSnackbarData] containing the Snackbar's visuals.
 * @param content The content of the Snackbar.
 */
@Composable
private fun SnackbarLayout(
    data: UiSnackbarData,
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = spacing.small)
            .padding(bottom = spacing.default),
    ) {
        Surface(
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(15.dp),
            contentColor = Theme.colorScheme.onSurface,
            color = data.visuals.containerColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.medium),
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}

/**
 * Displays the leading icon in the Snackbar.
 *
 * @param visuals The [UiSnackbarVisuals] containing the icon information.
 */
@Composable
private fun LeadingIcon(icon: ImageVector, tint: Color) {
    IconButton(
        onClick = {},
        modifier = Modifier
            .padding(end = LocalSpacing.current.default)
            .size(SnackbarDefaults.iconSizeDefault),
        colors = IconButtonDefaults.iconButtonColors(contentColor = tint)
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
}

/**
 * Displays the message text in the Snackbar.
 *
 * @param visuals The [UiSnackbarVisuals] containing the message text.
 */
@Composable
private fun RowScope.MessageText(visuals: UiSnackbarVisuals) {
    Text(
        modifier = Modifier.weight(1f),
        text = visuals.message,
        style = Theme.typography.bodySmall,
        color = visuals.contentColor
    )
}

/**
 * Displays the neutral button in the Snackbar.
 *
 * @param visuals The [UiSnackbarVisuals] containing the neutral action information.
 */
@Composable
private fun NeutralButton(action: SnackbarAction, contentColor: Color, dismiss: () -> Unit) {
    ActionButton(
        action = action,
        dismiss = dismiss,
        contentColor = contentColor,
        paddingValues = PaddingValues(start = LocalSpacing.current.default)
    )
}

/**
 * Displays the roled button in the Snackbar.
 *
 * @param visuals The [UiSnackbarVisuals] containing the positive or negative action information.
 */
@Composable
private fun RoleButton(action: SnackbarAction, contentColor: Color, dismiss: () -> Unit) {
    ActionButton(
        modifier = Modifier.padding(
            top = LocalSpacing.current.default,
            bottom = LocalSpacing.current.medium
        ),
        action = action,
        dismiss = dismiss,
        contentColor = contentColor
    )
}

/**
 * Displays the dismiss icon in the Snackbar.
 *
 * @param data The [UiSnackbarData] containing the dismiss action.
 * @param visuals The [UiSnackbarVisuals] containing the dismiss icon information.
 */
@Composable
private fun DismissIcon(tint: Color, dismiss: () -> Unit) {
    IconButton(
        onClick = dismiss,
        modifier = Modifier
            .padding(start = LocalSpacing.current.default)
            .size(SnackbarDefaults.iconDismissDefault),
        colors = IconButtonDefaults.iconButtonColors(contentColor = tint)
    ) {
        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
    }
}

/**
 * Displays an action button in the Snackbar.
 *
 * @param action The [SnackbarAction] to display.
 * @param contentColor The color of the button content.
 * @param paddingValues The padding values for the button.
 */
@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    action: SnackbarAction,
    dismiss: () -> Unit,
    contentColor: Color,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
        TextButton(
            modifier = Modifier.defaultMinSize(minHeight = 1.dp),
            contentPadding = paddingValues,
            onClick = {
                dismiss()
                action.onClick()
            },
            colors = ButtonDefaults.textButtonColors(
                contentColor = action.color.takeOrElse { contentColor }
            )
        ) {
            Text(
                modifier = modifier,
                text = action.label,
                fontWeight = FontWeight.Medium,
                style = Theme.typography.bodyMedium
            )
        }
    }
}

/**
 * Default values for Snackbar components.
 */
object SnackbarDefaults {
    internal val iconSizeDefault = 32.dp
    internal val iconDismissDefault = 24.dp
}

@PreviewComponent
@Composable
private fun SnackbarPreview() {
    ApplicationTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a short message",
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message to be displayed in a Snackbar",
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message short text",
                        neutralAction = SnackbarAction(label = "Neutral", onClick = {}),
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message to be displayed in a Snackbar",
                        neutralAction = SnackbarAction(label = "Neutral", onClick = {}),
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message to be displayed in a Snackbar",
                        neutralAction = SnackbarAction(label = "Neutral", onClick = {}),
                        withDismissAction = true,
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message to be displayed in a Snackbar",
                        positiveAction = SnackbarAction(label = "Positive", onClick = {}),
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
            Snackbar(
                UiSnackbarData(
                    UiSnackbarVisuals(
                        message = "This is a message to be displayed in a Snackbar",
                        positiveAction = SnackbarAction(label = "Positive", onClick = {}),
                        negativeAction = SnackbarAction(label = "Negative", onClick = {}),
                        contentColor = Theme.colorScheme.onPrimary,
                        containerColor = Theme.colorScheme.primary,
                        icon = Icons.Filled.Close
                    ),
                    dismiss = {}
                )
            )
        }
    }
}