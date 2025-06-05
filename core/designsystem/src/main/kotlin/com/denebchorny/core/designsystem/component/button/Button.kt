package com.denebchorny.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.button.Defaults.disabledOutlineOpacity
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.preview.parameterProvider.EnabledProvider
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import androidx.compose.material3.Button as ButtonM3
import androidx.compose.material3.OutlinedButton as OutlinedButtonM3
import androidx.compose.material3.TextButton as TextButtonM3


/**
 * Filled button with generic content slot. Wraps Material 3 [ButtonM3].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.buttonColors].
 * @param content The button content.
 */
@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    ButtonM3(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = Defaults.buttonShape,
        colors = colors,
        contentPadding = contentPadding,
        content = content,
    )
}

/**
 * Filled button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.buttonColors].
 * @param text The button text label content.
 * @param icon The button leading icon content.
 */
@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    icon: @Composable (() -> Unit) = {},
) {
    FilledButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (icon != {}) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
        colors = colors
    ) {
        ButtonContent(
            text = text,
            icon = icon,
        )
    }
}

/**
 * Filled button with text and optional icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The text label for the button.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.buttonColors].
 * @param icon Optional leading icon for the button.
 */
@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    icon: ImageVector? = null,
) {
    val tint = remember(enabled, colors) {
        if (enabled) colors.contentColor else colors.disabledContentColor
    }

    FilledButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        text = { TextContent(text) },
        icon = {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint
                )
            }
        },
    )
}

/**
 * Outlined button with generic content slot. Wraps Material 3 [OutlinedButtonM3].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param contentPadding The spacing values to apply internally between the container and the
 * content.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.outlinedButtonColors].
 * @param borderColor The border color for the button.
 * @param disabledBorderColor The border color for the button when disabled.
 * @param content The button content.
 */
@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    borderColor: Color = Theme.colorScheme.outline,
    disabledBorderColor: Color = Theme.colorScheme.outline,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButtonM3(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        shape = Defaults.buttonShape,
        colors = colors,
        border = BorderStroke(
            width = Defaults.buttonBorderWidth,
            color = if (enabled) {
                borderColor.takeOrElse { Theme.colorScheme.outline }
            } else {
                disabledBorderColor.takeOrElse {
                    Theme.colorScheme.outline.copy(alpha = disabledOutlineOpacity)
                }
            },
        ),
        content = content,
    )
}

/**
 * Outlined button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.outlinedButtonColors].
 * @param borderColor The border color for the button.
 * @param disabledBorderColor The border color for the button when disabled.
 * @param icon The button leading icon content.
 */
@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    borderColor: Color = Theme.colorScheme.outline,
    disabledBorderColor: Color = Theme.colorScheme.outline,
    icon: @Composable (() -> Unit) = {},
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        borderColor = borderColor,
        disabledBorderColor = disabledBorderColor,
        contentPadding = if (icon != {}) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        ButtonContent(
            text = text,
            icon = icon,
        )
    }
}

/**
 * Outlined button with generic content slot. Wraps Material 3 [OutlinedButtonM3].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.outlinedButtonColors].
 * @param borderColor The border color for the button.
 * @param disabledBorderColor The border color for the button when disabled.
 * @param icon Optional leading icon for the button.
 */
@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    borderColor: Color = Theme.colorScheme.outline,
    disabledBorderColor: Color = Theme.colorScheme.outline,
    icon: ImageVector? = null,
) {
    val tint = remember(enabled, colors) {
        if (enabled) colors.contentColor else colors.disabledContentColor
    }

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        borderColor = borderColor,
        disabledBorderColor = disabledBorderColor,
        text = { TextContent(text) },
        icon = {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint
                )
            }
        },
    )
}

/**
 * Text button with generic content slot. Wraps Material 3 [TextButtonM3].
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.textButtonColors].
 * @param content The button content.
 */
@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    TextButtonM3(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = colors,
        content = content,
    )
}

/**
 * Text button with text and icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The button text label content.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.textButtonColors].
 * @param icon The button leading icon content.
 */
@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    icon: @Composable (() -> Unit) = {},
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding,
        colors = colors
    ) {
        ButtonContent(
            text = text,
            icon = icon,
        )
    }
}

/**
 * Text button with text and optional icon content slots.
 *
 * @param onClick Will be called when the user clicks the button.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The text label for the button.
 * @param colors ButtonColors that will be used to resolve the colors for this button in
 * different states. See [ButtonDefaults.textButtonColors].
 * @param icon Optional leading icon for the button.
 */
@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
    icon: ImageVector? = null
) {
    val tint = remember(enabled, colors) {
        if (enabled) colors.contentColor else colors.disabledContentColor
    }

    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        text = { TextContent(text) },
        icon = {
            icon?.let {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint
                )
            }
        },
    )
}

/**
 * Internal button content layout for arranging the text label and leading icon.
 *
 * @param text The button text label content.
 * @param icon The button leading icon content. Default is `null` for no leading icon.
 */
@Composable
private fun ButtonContent(
    text: @Composable () -> Unit,
    icon: @Composable (() -> Unit) = {},
) {
    if (icon != {}) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            icon()
        }
    }
    Box(
        Modifier.padding(
            start = if (icon != {}) {
                ButtonDefaults.IconSpacing
            } else {
                0.dp
            },
        ),
    ) {
        text()
    }
}

/**
 * Internal text content.
 *
 * @param text The button text label content.
 */
@Composable
private fun TextContent(text: String) {
    Text(text = text, style = Theme.typography.bodyMedium)
}

internal object Defaults {
    const val disabledOutlineOpacity = 0.12f
    val buttonBorderWidth = 1.dp
    val buttonShape = RoundedCornerShape(15.dp)
}

@PreviewComponent
@Composable
private fun FilledButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            FilledButton(onClick = {}, enabled = enabled) { Text("Test button") }
            FilledButton(
                onClick = {},
                enabled = enabled,
                text = { Text("Test button") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
            )
            FilledButton(
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
private fun OutlinedButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            OutlinedButton(onClick = {}, enabled = enabled) { Text("Test button") }
            OutlinedButton(
                onClick = {},
                enabled = enabled,
                text = { Text("Test button") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
            )
            OutlinedButton(
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
private fun TextButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            TextButton(onClick = {}, enabled = enabled, text = { Text("Test button") })
            TextButton(
                onClick = {},
                enabled = enabled,
                text = { Text("Test button") },
                icon = { Icon(imageVector = Icons.Filled.Add, contentDescription = null) },
            )
            TextButton(
                onClick = {},
                enabled = enabled,
                text = "Test button",
                icon = Icons.Filled.Add,
            )
        }
    }
}