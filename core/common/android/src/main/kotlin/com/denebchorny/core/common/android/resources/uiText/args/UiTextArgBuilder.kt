package com.denebchorny.core.common.android.resources.uiText.args

import android.os.Parcelable
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import java.io.Serializable

/**
 * Builder class for constructing a [UiTextArgList] instance.
 *
 * **Example Usage:**
 * ```kotlin
 * val args: UiTextArgList = UiTextArgBuilder()
 *     .addStringRes(R.string.username, uiTextArgsOf("Alice"))
 *     .addIntegerRes(R.integer.message_count)
 *     .addBooleanRes(R.bool.is_feature_enabled)
 *     .build()
 * ```
 */
class UiTextArgBuilder {
    /**
     * Internal mutable list holding the arguments to be included in the [UiTextArgList].
     */
    private val _args = mutableListOf<Any?>()

    /**
     * Adds a string resource argument with formatting arguments to the builder.
     *
     * @param resId The resource ID of the string to include.
     * @param args The [UiTextArgList] containing arguments for string formatting.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addStringRes(
        @StringRes resId: Int,
        args: UiTextArgList,
    ) = apply {
        _args.add(stringResArg(resId, args))
    }

    /**
     * Adds a string resource argument without formatting arguments to the builder.
     *
     * @param resId The resource ID of the string to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addStringRes(
        @StringRes resId: Int,
    ) = apply {
        _args.add(stringResArg(resId))
    }

    /**
     * Adds a plurals resource argument with formatting arguments to the builder.
     *
     * @param resId The resource ID of the plurals to include.
     * @param quantity The quantity used to select the appropriate plural form.
     * @param args The [UiTextArgList] containing arguments for plurals formatting.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addPluralsRes(
        @PluralsRes resId: Int,
        quantity: Int,
        args: UiTextArgList,
    ) = apply {
        _args.add(pluralsResArg(resId, quantity, args))
    }

    /**
     * Adds a plurals resource argument without formatting arguments to the builder.
     *
     * @param resId The resource ID of the plurals to include.
     * @param quantity The quantity used to select the appropriate plural form.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addPluralsRes(
        @PluralsRes resId: Int,
        quantity: Int,
    ) = apply {
        _args.add(pluralsResArg(resId, quantity))
    }

    /**
     * Adds an integer resource argument to the builder.
     *
     * @param resId The resource ID of the integer to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addIntegerRes(@IntegerRes resId: Int) = apply {
        _args.add(integerResArg(resId))
    }

    /**
     * Adds a boolean resource argument to the builder.
     *
     * @param resId The resource ID of the boolean to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun addBooleanRes(@BoolRes resId: Int) = apply {
        _args.add(booleanResArg(resId))
    }

    /**
     * Adds a [Parcelable] object as an argument to the builder.
     *
     * @param value The [Parcelable] object to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun add(value: Parcelable) = apply {
        _args.add(value)
    }

    /**
     * Adds a [Serializable] object as an argument to the builder.
     *
     * @param value The [Serializable] object to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun add(value: Serializable) = apply {
        _args.add(value)
    }

    /**
     * Adds a generic [Any?] object as an argument to the builder.
     *
     * @param value The object to include.
     * @return The current [UiTextArgBuilder] instance for chaining.
     */
    fun add(value: Any?) = apply {
        _args.add(value)
    }

    /**
     * Builds and returns a [UiTextArgList] instance containing all added arguments.
     *
     * @return A [UiTextArgList] containing all added arguments.
     * @throws IllegalStateException If no arguments have been added to the builder.
     */
    fun build(): UiTextArgList {
        if (_args.isEmpty()) {
            throw IllegalStateException("Can't create empty UiTextArgs")
        }

        val arg0 = _args.first()
        val rest = _args.drop(1)

        return uiTextArgsOf(arg0, *rest.toTypedArray())
    }
}
