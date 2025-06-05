package com.denebchorny.core.common.android.resources.uiText.extension

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.denebchorny.core.common.android.resources.uiText.UiText
import com.denebchorny.core.common.android.resources.uiText.concatUiText

/**
 * Extension function for [concatUiText] that converts it to a [String] within a [Composable] context.
 *
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun Greeting(uiText: UiText) {
 *     Text(text = uiText.asString())
 * }
 * ```
 *
 * **Note:**
 * - Ensure that this function is called within a Composable scope where [LocalContext.current] is available.
 * @receiver The [concatUiText] instance to convert to a [String].
 * @return The string representation of the [concatUiText].
 */
@Composable
fun UiText.asString(): String {
    val context = LocalContext.current
    return asString(context)
}

/**
 * Concatenates all [concatUiText] instances in the collection into a single string.
 * @param context The [Context] used to access resources.
 * @param separator The string to separate each concatenated [concatUiText].
 * @param prefix A string to prefix the final concatenated string.
 * @param postfix A string to postfix the final concatenated string.
 * @param limit The maximum number of elements to include.
 * @param truncated The string to append if the list is truncated.
 * @return The concatenated string.
 */
fun Collection<UiText>.joinAsString(
    context: Context,
    separator: CharSequence = "\n",
    prefix: CharSequence = "",
    postfix: CharSequence = "",
    limit: Int = -1,
    truncated: CharSequence = "...",
): String {
    if (isEmpty()) {
        return ""
    }
    return joinToString(
        separator = separator,
        prefix = prefix,
        postfix = postfix,
        limit = limit,
        truncated = truncated,
    ) { uiText ->
        uiText.asString(context)
    }
}
