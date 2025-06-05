package com.denebchorny.core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.button.Defaults.disabledOutlineOpacity
import com.denebchorny.core.designsystem.extension.conditionalIf
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.preview.parameterProvider.EnabledProvider
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.material3.OutlinedButton as OutlinedButtonM3

/**
 * A composable function that renders an outlined action button.
 *
 * @param modifier Modifier to customize the button's appearance and layout.
 * @param enabled Boolean indicating whether the button is enabled. Defaults to `true`.
 * @param textColor The color of the text displayed on the button.
 * @param borderColor The color of the button's border.
 * @param onClick Lambda function executed when the button is clicked.
 * @param content Composable content displayed inside the button.
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = Theme.colorScheme.onSurface,
    borderColor: Color = Theme.colorScheme.outline,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedButtonM3(
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        enabled = enabled,
        shape = Defaults.buttonShape,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(
            width = Defaults.buttonBorderWidth,
            color = getBorderColor(
                enabled = enabled,
                borderColor = borderColor,
                disabledOutlineOpacity = disabledOutlineOpacity
            )
        ),
        content = { content() }
    )
}

/**
 * A composable function that displays an action button with an icon and text.
 * Supports optional marquee animation for the text.
 *
 * @param modifier Modifier for styling and layout.
 * @param enabled Boolean to enable or disable the button. Defaults to `true`.
 * @param icon The ImageVector icon displayed on the button.
 * @param text The text displayed on the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param textColor Color of the text.
 * @param iconColor Color of the icon.
 * @param borderColor Color of the button's border.
 * @param marqueeDuration Duration in milliseconds for the marquee animation.
 * @param onClick Lambda function triggered on button click.
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    textColor: Color = Theme.colorScheme.onSurface,
    iconColor: Color = Theme.colorScheme.primary,
    borderColor: Color = Theme.colorScheme.outline,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    val disabledContent = Theme.colorScheme.outline
    val tint = remember(enabled, iconColor) {
        if (enabled) iconColor else disabledContent
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isMarqueeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    // Handle marquee animation state when the button is pressed
    LaunchedEffect(isPressed) {
        if (isPressed) {
            job?.cancel()
            job = scope.launch {
                isMarqueeEnabled = true
                delay(marqueeDuration)
                isMarqueeEnabled = false
            }
        }
    }

    ActionButton(
        modifier = modifier,
        enabled = enabled,
        textColor = textColor,
        borderColor = borderColor,
        onClick = onClick
    ) {
        Column(
            Modifier
                .defaultMinSize(
                    minWidth = ActionButtonDefaults.actionButtonWidthMin,
                    minHeight = ActionButtonDefaults.actionButtonHeightMin
                )
                .padding(
                    horizontal = ActionButtonDefaults.actionButtonHorizontalPadding,
                    vertical = ActionButtonDefaults.actionButtonVerticalPadding
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Content(
                text = text,
                maxLines = maxLines,
                isMarqueeEnabled = isMarqueeEnabled,
                icon = icon,
                tint = tint
            )
        }
    }
}

/**
 * A wrapper for the `ActionButton` that sets all colors to a single color.
 *
 * @param modifier Modifier for styling and layout.
 * @param enabled Boolean to enable or disable the button. Defaults to `true`.
 * @param icon The ImageVector icon displayed on the button.
 * @param text The text displayed on the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param color The color to be applied to the text, icon, and border.
 * @param marqueeDuration Duration in milliseconds for the marquee animation.
 * @param onClick Lambda function triggered on button click.
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    color: Color,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    ActionButton(
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        text = text,
        maxLines = maxLines,
        textColor = color,
        iconColor = color,
        borderColor = color,
        marqueeDuration = marqueeDuration,
        onClick = onClick
    )
}

/**
 * A composable function for creating a long action button.
 * This button is visually larger than regular buttons, with additional padding and spacing.
 * It supports an optional marquee animation for the text when the button is pressed.
 *
 * @param modifier Modifier for styling and layout.
 * @param enabled Boolean to enable or disable the button. Defaults to `true`.
 * @param icon The ImageVector icon displayed on the button.
 * @param text The text displayed on the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param textColor Color of the text.
 * @param iconColor Color of the icon.
 * @param borderColor Color of the button's border.
 * @param marqueeDuration Duration in milliseconds for the marquee animation.
 * @param onClick Lambda function triggered on button click.
 */
@Composable
fun LongActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    textColor: Color = Theme.colorScheme.onSurface,
    iconColor: Color = Theme.colorScheme.primary,
    borderColor: Color = Theme.colorScheme.outline,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    val disabledContent = Theme.colorScheme.outline
    val tint = remember(enabled, iconColor) {
        if (enabled) iconColor else disabledContent
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    var isMarqueeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var job: Job? by remember { mutableStateOf(null) }

    // Handle marquee animation state when the button is pressed
    LaunchedEffect(isPressed) {
        if (isPressed) {
            job?.cancel()
            job = scope.launch {
                isMarqueeEnabled = true
                delay(marqueeDuration)
                isMarqueeEnabled = false
            }
        }
    }

    ActionButton(
        modifier = modifier,
        enabled = enabled,
        textColor = textColor,
        borderColor = borderColor,
        onClick = onClick
    ) {
        Row(
            Modifier
                .defaultMinSize(
                    minWidth = ActionButtonDefaults.longActionButtonWidthMin,
                    minHeight = ActionButtonDefaults.longActionButtonHeightMin
                )
                .padding(
                    horizontal = ActionButtonDefaults.longActionButtonHorizontalPadding,
                    vertical = ActionButtonDefaults.longActionButtonVerticalPadding
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Content(
                text = text,
                maxLines = maxLines,
                isMarqueeEnabled = isMarqueeEnabled,
                icon = icon,
                tint = tint
            )
        }
    }
}

/**
 * A wrapper for the `ActionButton` that sets all colors to a single color.
 *
 * @param modifier Modifier for styling and layout.
 * @param enabled Boolean to enable or disable the button. Defaults to `true`.
 * @param icon The ImageVector icon displayed on the button.
 * @param text The text displayed on the button.
 * @param maxLines Maximum number of lines for the text. Defaults to `1`.
 * @param color The color to be applied to the text, icon, and border.
 * @param marqueeDuration Duration in milliseconds for the marquee animation.
 * @param onClick Lambda function triggered on button click.
 */
@Composable
fun LongActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    text: String,
    maxLines: Int = 1,
    color: Color,
    marqueeDuration: Long = ActionButtonDefaults.marqueeAnimationMillis,
    onClick: () -> Unit,
) {
    LongActionButton(
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        text = text,
        maxLines = maxLines,
        textColor = color,
        iconColor = color,
        borderColor = color,
        marqueeDuration = marqueeDuration,
        onClick = onClick
    )
}

/**
 * Internal layout for the content of the button, including the icon and text.
 *
 * @param text The text label content.
 * @param maxLines Maximum number of lines for the text.
 * @param isMarqueeEnabled Boolean flag to enable or disable marquee animation for the text.
 * @param icon The icon to be displayed at the top of the button.
 * @param tint The color tint for the icon.
 */
@Composable
private fun Content(
    text: String,
    maxLines: Int = 1,
    isMarqueeEnabled: Boolean,
    icon: ImageVector,
    tint: Color,
) {
    val spacing = LocalSpacing.current

    Box(
        Modifier.defaultMinSize(ActionButtonDefaults.iconSize, ActionButtonDefaults.iconSize),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint
        )
    }
    Spacer(modifier = Modifier.size(spacing.extraSmall))
    TextContent(
        text = text,
        maxLines = maxLines,
        showMarquee = isMarqueeEnabled
    )
}

/**
 * Internal composable for rendering the text content of the button.
 * Supports marquee animation when `showMarquee` is enabled.
 *
 * @param text The text label content.
 * @param maxLines Maximum number of lines for the text.
 * @param showMarquee Boolean flag to enable or disable marquee animation.
 */
@Composable
private fun TextContent(
    text: String,
    maxLines: Int,
    showMarquee: Boolean,
) {
    Text(
        modifier = Modifier.conditionalIf(showMarquee) {
            basicMarquee(
                iterations = 5,
                animationMode = MarqueeAnimationMode.Immediately,
                repeatDelayMillis = 0,
                initialDelayMillis = 0,
            )
        },
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = Theme.typography.bodySmall
    )
}

/**
 * Determines the border color of the button based on its state.
 *
 * @param enabled Boolean flag indicating whether the button is enabled.
 * @param borderColor The custom border color to use when the button is enabled.
 * @param disabledOutlineOpacity The opacity to apply to the outline when the button is disabled.
 * @return The calculated border color based on the button state.
 */
@Composable
fun getBorderColor(
    enabled: Boolean,
    borderColor: Color,
    disabledOutlineOpacity: Float
): Color {
    return if (enabled) {
        borderColor.takeOrElse { Theme.colorScheme.outline }
    } else {
        Theme.colorScheme.outline.takeOrElse {
            Theme.colorScheme.outline.copy(alpha = disabledOutlineOpacity)
        }
    }
}

object ActionButtonDefaults {
    val actionButtonWidthMin = 80.dp
    val actionButtonHeightMin = 80.dp
    val actionButtonHorizontalPadding = 8.dp
    val actionButtonVerticalPadding = 15.dp

    val longActionButtonWidthMin = 80.dp
    val longActionButtonHeightMin = 48.dp
    val longActionButtonHorizontalPadding = 8.dp
    val longActionButtonVerticalPadding = 8.dp

    const val marqueeAnimationMillis = 5_000L
    val iconSize = 32.dp
}

@PreviewComponent
@Composable
private fun ActionButtonsPreview(
    @PreviewParameter(EnabledProvider::class) enabled: Boolean
) {
    ApplicationTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            ActionButton(
                modifier = Modifier.padding(bottom = 8.dp),
                enabled = enabled,
                icon = Icons.Filled.Add,
                text = "Confirm",
                onClick = {}
            )
            LongActionButton(
                modifier = Modifier.padding(bottom = 8.dp),
                enabled = enabled,
                icon = Icons.Filled.Add,
                text = "Confirm Action",
                onClick = {}
            )
        }
    }
}