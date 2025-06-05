package com.denebchorny.core.common.android.resources.uiText

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.denebchorny.core.common.android.resources.uiText.args.EmptyUiArgs
import com.denebchorny.core.common.android.resources.uiText.args.UiTextArgList
import com.denebchorny.core.common.android.resources.uiText.args.UiTextArgListImpl
import com.denebchorny.core.common.android.resources.uiText.args.dynamicStringArg

/**
 * Builder class for constructing concatenated [concatUiText] instances.
 *
 * The [ConcatUiTextBuilder] facilitates the creation of complex [concatUiText] objects by allowing
 * the addition of various [concatUiText] components, including dynamic strings, string resources,
 * plurals resources, and common punctuation marks. It follows the builder pattern to provide
 * a fluent and intuitive API for assembling [concatUiText] instances.
 *
 * **Example Usage:**
 * ```kotlin
 * val greeting: UiText = concatUiText {
 *     add("Hello, ")
 *     addStringResource(R.string.username, uiTextArgsOf(userName))
 *     addExclamation()
 * }
 * // The resulting UiText represents "Hello, [username]!"
 * ```
 */
class ConcatUiTextBuilder {

    /**
     * Internal list holding the [concatUiText] components to be concatenated.
     */
    private val texts = mutableListOf<UiText>()

    /**
     * Adds a [concatUiText] instance to the builder.
     *
     * This method allows adding any [concatUiText] implementation, providing flexibility
     * in constructing complex text structures.
     *
     * @param text The [concatUiText] instance to add.
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun add(text: UiText) = apply {
        texts.add(text)
    }

    /**
     * Adds a dynamic string to the builder.
     *
     * This method wraps a raw string into a [DynamicString] [concatUiText] instance before adding it.
     *
     * @param value The dynamic string to add.
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun add(value: String) = apply {
        add(uiText(value))
    }

    /**
     * Adds a space character to the builder.
     *
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addSpace() = apply {
        add(" ")
    }

    /**
     * Adds a comma and space to the builder.
     *
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addComma() = apply {
        add(", ")
    }

    /**
     * Adds a period character to the builder.
     *
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addPeriod() = apply {
        add(".")
    }

    /**
     * Adds an exclamation mark to the builder.
     *
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addExclamation() = apply {
        add("!")
    }

    /**
     * Adds a question mark to the builder.
     *
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addQuestionMark() = apply {
        add("?")
    }

    /**
     * Adds a string resource to the builder.
     * This method wraps a string resource into a [StringResource] [concatUiText] instance
     * with optional formatting arguments before adding it.
     *
     * @param resId The resource ID of the string to add.
     * @param args The [UiTextArgList] containing arguments for string formatting.
     * Defaults to [EmptyUiArgs] if not provided.
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addStringResource(
        @StringRes resId: Int,
        args: UiTextArgList = EmptyUiArgs,
    ) = apply {
        add(uiText(resId, args))
    }

    fun addStringResource(
        @StringRes resId: Int,
        arg: String?,
    ) = apply {
        add(
            uiText(
                resId,
                arg?.let { UiTextArgListImpl(listOf(dynamicStringArg(it))) }
                    ?: EmptyUiArgs
            )
        )
    }


    /**
     * Adds a plurals resource to the builder.
     * This method wraps a plurals resource into a [PluralsResource] [concatUiText] instance
     * with optional formatting arguments before adding it.
     *
     * @param resId The resource ID of the plurals to add.
     * @param quantity The quantity to determine the correct plural form.
     * @param args The [UiTextArgList] containing arguments for plurals formatting.
     * Defaults to [EmptyUiArgs] if not provided.
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addPluralsResource(
        @PluralsRes resId: Int,
        quantity: Int,
        args: UiTextArgList = EmptyUiArgs
    ) = apply {
        add(uiText(resId, quantity, args))
    }

    /**
     * Adds multiple [concatUiText] instances to the builder.
     *
     * This method allows adding a variable number of [concatUiText] instances at once,
     * facilitating bulk additions.
     *
     * @param newTexts The [concatUiText] instances to add.
     * @return The current [ConcatUiTextBuilder] instance for chaining.
     */
    fun addAll(vararg newTexts: UiText) = apply {
        newTexts.forEach { add(it) }
    }

    /**
     * Builds and returns a concatenated [concatUiText] instance.
     * The resulting [concatUiText] depends on the number of texts added:
     * - If no texts were added, returns [EmptyUiText].
     * - If only one text was added, returns that single [concatUiText].
     * - If multiple texts were added, returns a [ConcatUiText] instance containing all added texts.
     * @return The constructed [concatUiText] instance.
     */
    fun build(): UiText {
        return when {
            texts.isEmpty() -> EmptyUiText
            texts.size == 1 -> texts.first()
            else -> ConcatUiText(texts.toList())
        }
    }
}
