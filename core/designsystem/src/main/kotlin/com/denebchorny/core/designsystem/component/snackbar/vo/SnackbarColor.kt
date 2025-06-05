package com.denebchorny.core.designsystem.component.snackbar.vo

import android.content.Context
import androidx.annotation.ColorRes
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.denebchorny.core.designsystem.theme.color.tokenization.ColorSchemeKeyTokens
import com.denebchorny.core.designsystem.theme.color.tokenization.fromToken

/**
 * Sealed interface representing different ways to define a Snackbar color.
 */
sealed interface SnackbarColor

/**
 * Represents a Snackbar color defined by a theme color token.
 *
 * @param value The [ColorSchemeKeyTokens] representing the color.
 */
internal data class KeyTokenColor(val value: ColorSchemeKeyTokens) : SnackbarColor

/**
 * Represents a Snackbar color defined by a Compose [Color].
 *
 * @param value The Compose [Color] value.
 */
internal data class ComposeColor(val value: Color) : SnackbarColor

/**
 * Represents a Snackbar color defined by an Android resource color.
 *
 * @param value The `@ColorRes` resource ID.
 */
internal data class ResourceColor(@ColorRes val value: Int) : SnackbarColor

/**
 * Creates a [SnackbarColor] from a theme color token.
 *
 * @param value The [ColorSchemeKeyTokens] representing the color.
 * @return A [SnackbarColor.KeyTokenColor] instance.
 */
fun snackbarColor(value: ColorSchemeKeyTokens): SnackbarColor = KeyTokenColor(value)

/**
 * Creates a [SnackbarColor] from a Compose [Color].
 *
 * @param value The Compose [Color] value.
 * @return A [SnackbarColor.ComposeColor] instance.
 */
fun snackbarColor(value: Color): SnackbarColor = ComposeColor(value)

/**
 * Creates a [SnackbarColor] from an Android resource color.
 *
 * @param value The `@ColorRes` resource ID.
 * @return A [SnackbarColor.ResourceColor] instance.
 */
fun snackbarColor(@ColorRes value: Int): SnackbarColor = ResourceColor(value)

/**
 * Resolves the [SnackbarColor] to a Compose [Color].
 *
 * This function retrieves the actual Compose [Color] value based on the underlying
 * representation of the [SnackbarColor].  It handles different color sources (Compose Color,
 * resource color, theme color token).
 *
 * @param context The Android [Context] required for resource access.
 * @param colorScheme The [UIColorScheme] used for resolving theme color tokens.
 * @return The resolved Compose [Color] value.
 */
fun SnackbarColor.getColor(context: Context, colorScheme: ColorScheme): Color {
    return when (this) {
        is ComposeColor -> value
        is ResourceColor -> Color(context.getColor(value))
        is KeyTokenColor -> colorScheme.fromToken(value)
    }
}