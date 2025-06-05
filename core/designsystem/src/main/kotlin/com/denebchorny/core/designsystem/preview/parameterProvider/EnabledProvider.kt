package com.denebchorny.core.designsystem.preview.parameterProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

/**
 * Provides a sequence of [Boolean] values (true and false) for use in Compose preview annotations.
 *
 * This class implements [PreviewParameterProvider], which allows developers to preview a
 * Composable with different boolean states. It is particularly useful for testing UI components
 * with enabled/disabled, visible/invisible, or any other binary state configurations in the
 * Android Studio Preview tool.
 *
 * Example usage:
 * ```
 * @Preview
 * @Composable
 * fun PreviewThemedComponent(
 *     @PreviewParameter(EnableProvider::class) isEnabled: Boolean
 * ) {
 *     Button(enabled = isEnabled)
 * }
 * ```
 */
class EnabledProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}