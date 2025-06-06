package com.denebchorny.core.designsystem.component.snackbar.vo

import android.content.Context
import androidx.annotation.StringRes
import com.denebchorny.core.common.android.resources.uiText.UiText

/**
 * Sealed interface representing different ways to define Snackbar text.
 */
sealed interface SnackbarText

/**
 * Represents Snackbar text defined by a dynamic [String].
 *
 * @param value The dynamic [String] value.
 */
internal data class DynamicString(val value: String) : SnackbarText

/**
 * Represents Snackbar text defined by a string resource.
 *
 * @param value The `@StringRes` resource ID.
 */
internal data class StringResource(@StringRes val value: Int) : SnackbarText

/**
 * Represents Snackbar text defined by a [UiText] object.
 *
 * @param value The [UiText] object.
 */
internal data class StringUiText(val value: UiText) : SnackbarText

/**
 * Creates a [SnackbarText] from a dynamic [String].
 *
 * @param value The dynamic [String] value.
 * @return A [SnackbarText.DynamicString] instance.
 */
fun snackbarText(value: String): SnackbarText = DynamicString(value)

/**
 * Creates a [SnackbarText] from a string resource.
 *
 * @param value The `@StringRes` resource ID.
 * @return A [SnackbarText.StringResource] instance.
 */
fun snackbarText(@StringRes value: Int): SnackbarText = StringResource(value)

/**
 * Creates a [SnackbarText] from a [UiText] object.
 *
 * @param value The [UiText] object.
 * @return A [SnackbarText.StringUiText] instance.
 */
fun snackbarText(value: UiText): SnackbarText = StringUiText(value)

/**
 * Resolves the [SnackbarText] to a [String].
 *
 * This function retrieves the actual [String] value based on the underlying representation of the
 * [SnackbarText]. It handles different text sources (dynamic string, string resource, [UiText]).
 *
 * @param context The Android [Context] required for resource access.
 * @return The resolved [String] value.
 */
fun SnackbarText.getString(context: Context): String {
    return when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(value)
        is StringUiText -> value.asString(context)
    }
}