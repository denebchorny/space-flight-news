package com.denebchorny.core.common.android.resources.uiText

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.denebchorny.core.common.android.resources.uiText.args.EmptyUiArgs
import com.denebchorny.core.common.android.resources.uiText.args.UiTextArg
import com.denebchorny.core.common.android.resources.uiText.args.UiTextArgList
import com.denebchorny.core.common.android.resources.uiText.args.getValue
import com.denebchorny.core.common.android.resources.uiText.args.rawList

/**
 * Source of inspiration:
 *  - https://github.com/philipplackner/UniversalStringResources/blob/final/app/src/main/java/com/plcoding/universalstringresources/UiText.kt
 *  - https://gist.github.com/ElianFabian/406bdbccda2d8fdcbac45200247452d3#file-uitext-kt
 *  - https://medium.com/@ElianFabian/how-to-use-string-resources-in-viewmodels-fe5b8a3ac248
 * */

/**
 * A sealed interface representing different types of UI text.
 * Provides a method to convert the text into a [String] based on the given [Context].
 */
sealed interface UiText {

    /**
     * Converts the [uiText] instance into a [String] using the provided [Context].
     *
     * @param context The [Context] used to access resources.
     * @return The string representation of the [uiText].
     */
    fun asString(context: Context): String
}

/**
 * Represents an empty [concatUiText] instance.
 * This singleton object is used when a [concatUiText] is required, but no actual text content is needed.
 * It serves as a default or placeholder value within the [concatUiText] system to avoid nullability issues
 * and to provide a consistent way to handle empty text scenarios.
 *
 * **Usage Example:**
 * ```kotlin
 * val noText: UiText = EmptyUiText
 * ```
 *
 * **Benefits:**
 * - **Singleton Pattern:** Ensures that only one instance of [EmptyUiText] exists, conserving memory and maintaining consistency.
 * - **Non-Null Representation:** Provides a non-null [concatUiText] instance that signifies the absence of text, simplifying null checks
 *   and reducing potential [NullPointerException](https://kotlinlang.org/docs/null-safety.html) risks.
 * - **Consistency:** Offers a standardized way to represent empty text across the application, enhancing code uniformity.
 */
data object EmptyUiText : UiText {
    override fun asString(context: Context) = ""
}

/**
 * Represents a string resource from the application's resources.
 *
 * @property resId The resource ID of the string.
 * @property args A list of arguments to format the string with, if applicable.
 */
data class StringResource(
    @StringRes
    val resId: Int,
    val args: List<UiTextArg?>,
) : UiText {

    /**
     * Retrieves and formats the string resource using the provided [Context].
     *
     * @param context The [Context] used to access resources.
     * @return The formatted string.
     */
    override fun asString(context: Context): String {
        val arguments = args.map { arg ->
            arg?.getValue(context)
        }.toTypedArray()
        return context.getString(resId, *arguments)
    }
}

/**
 * Represents a dynamic string that is not sourced from resources.
 *
 * @property value The dynamic string value.
 */
data class DynamicString(val value: String) : UiText {

    /**
     * Returns the dynamic string value.
     *
     * @param context The [Context] (not used in this implementation).
     * @return The dynamic string.
     */
    override fun asString(context: Context) = value
}

/**
 * Represents a plurals resource from the application's resources.
 *
 * @property resId The resource ID of the plurals.
 * @property quantity The quantity to determine the correct plural form.
 * @property args A list of arguments to format the plurals string with, if applicable.
 */
data class PluralsResource(
    @PluralsRes
    val resId: Int,
    val quantity: Int,
    val args: List<UiTextArg?>,
) : UiText {

    /**
     * Retrieves and formats the plurals resource using the provided [Context].
     *
     * @param context The [Context] used to access resources.
     * @return The formatted plurals string.
     */
    override fun asString(context: Context): String {
        val arguments = args.map { arg ->
            arg?.getValue(context)
        }.toTypedArray()
        return context.resources.getQuantityString(resId, quantity, *arguments)
    }
}

/**
 * Represents a concatenated [concatUiText], combining multiple [concatUiText] instances into one.
 *
 * @property texts The list of [concatUiText] instances to concatenate.
 */
data class ConcatUiText(
    val texts: List<UiText>
) : UiText {

    /**
     * Concatenates all contained [concatUiText] instances into a single string.*
     *
     * @param context The [Context] used to access resources.
     * @return The concatenated string.
     */
    override fun asString(context: Context): String {
        return texts.joinToString(separator = "") { it.asString(context) }
    }
}

/**
 * Factory function to create a [uiText] instance from a raw string.
 *
 * @param value The raw string value.
 * @return A [DynamicString] if [value] is not empty, otherwise [EmptyUiText].
 */
fun uiText(value: String): UiText = when {
    value.isEmpty() -> EmptyUiText
    else -> DynamicString(value)
}

/**
 * Factory function to create a [StringResource] instance.
 *
 * @param resId The resource ID of the string.
 * @param args The arguments to format the string with.
 * @return A [StringResource] instance.
 */
fun uiText(
    @StringRes
    resId: Int,
    args: UiTextArgList = EmptyUiArgs,
): UiText = StringResource(
    resId = resId,
    args = args.rawList,
)

/**
 * Factory function to create a [PluralsResource] instance.
 *
 * @param resId The resource ID of the plurals.
 * @param quantity The quantity to determine the correct plural form.
 * @param args The arguments to format the plurals string with.
 * @return A [PluralsResource] instance.
 */
fun uiText(
    @PluralsRes
    resId: Int,
    quantity: Int,
    args: UiTextArgList = EmptyUiArgs,
): UiText = PluralsResource(
    resId = resId,
    quantity = quantity,
    args = args.rawList,
)

/**
 * Factory function to create a [ConcatUiText] instance from a variable number of [concatUiText] arguments.
 *
 * @param texts The [concatUiText] instances to concatenate.
 * @return A [ConcatUiText] instance containing the provided texts.
 */
fun concatUiText(vararg texts: UiText): UiText = ConcatUiText(texts.asList())

/**
 * Factory function to create a [ConcatUiText] instance from a list of [concatUiText] instances.
 *
 * @param texts The list of [concatUiText] instances to concatenate.
 * @return A [ConcatUiText] instance containing the provided texts.
 */
fun concatUiText(texts: List<UiText>): UiText = ConcatUiText(texts)

/**
 * Creates a [concatUiText] by using a [ConcatUiTextBuilder].
 * Example:
 *
 * ```
 * val concatText = concatUiText {
 *     add("Hello, ")
 *     addStringResource(R.string.username, uiTextArgsOf(userName))
 *     add("!")
 * }
 * ```
 *
 * @param block A lambda with receiver of [ConcatUiTextBuilder] to configure the concatenation.
 * @return The built [concatUiText] instance.
 */
fun concatUiText(block: ConcatUiTextBuilder.() -> Unit): UiText {
    val builder = ConcatUiTextBuilder()
    builder.block()
    return builder.build()
}


