package com.denebchorny.core.common.android.resources.uiText.args

import android.content.Context
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * A sealed interface representing an argument used within [UiText] resources.
 */
sealed interface UiTextArg {
    // The companion object is kept private to restrict instantiation and extension outside this file.
    private companion object
}

/**
 * Represents a boolean resource argument within a [UiText] resource.
 *
 * @property resId The resource ID of the boolean value.
 */
data class BooleanResourceArg(
    @BoolRes val resId: Int
) : UiTextArg

/**
 * Represents an integer resource argument within a [UiText] resource.
 *
 * @property resId The resource ID of the integer value.
 */
data class IntegerResourceArg(
    @IntegerRes val resId: Int
) : UiTextArg

/**
 * Represents a string resource argument within a [UiText] resource.
 *
 * This class handles string resources that may require formatting with additional arguments.
 *
 * @property resId The resource ID of the string.
 * @property args A list of [UiTextArg] instances used to format the string.
 */
data class StringResourceArg(
    @StringRes val resId: Int,
    val args: List<UiTextArg?>
) : UiTextArg

/**
 * Represents a string resource argument within a [UiText] resource.
 *
 * This class handles string resources that may require formatting with additional arguments.
 *
 * @property value the string value.
 */
data class DynamicStringArg(
    val value: String,
) : UiTextArg

/**
 * Represents a plurals resource argument within a [UiText] resource.
 *
 * This class handles plurals resources that may require different formatting based on quantity.
 *
 * @property resId The resource ID of the plurals.
 * @property quantity The quantity used to select the appropriate plural form.
 * @property args A list of [UiTextArg] instances used to format the plurals string.
 */
data class PluralsResourceArg(
    @PluralsRes val resId: Int,
    val quantity: Int,
    val args: List<UiTextArg?>
) : UiTextArg

/**
 * Converts a [UiTextArg] instance to its corresponding value based on the provided [Context].
 *
 * @receiver The [UiTextArg] instance to convert.
 * @param context The [Context] used to access resources.
 * @return The value of the argument as an [Any] type.
 */
internal fun UiTextArg.getValue(context: Context): Any {
    val resources = context.resources

    return when (this) {
        is BooleanResourceArg -> resources.getBoolean(resId)
        is IntegerResourceArg -> resources.getInteger(resId)
        is StringResourceArg -> {
            val arguments = args.map { arg -> arg?.getValue(context) }.toTypedArray()
            resources.getString(resId, *arguments)
        }

        is PluralsResourceArg -> {
            val arguments = args.map { arg -> arg?.getValue(context) }.toTypedArray()
            resources.getQuantityString(resId, quantity, *arguments)
        }

        is DynamicStringArg -> value
    }
}

/**
 * Extension function to convert any [Any] type to a [UiTextArg].
 *
 * @receiver The [Any] instance to convert.
 * @return The corresponding [UiTextArg] instance.
 * @throws IllegalArgumentException If the provided object is not a supported type.
 */
internal fun Any.asUiArg(): UiTextArg = when (this) {
    is UiTextArg -> this
    else -> throw IllegalArgumentException(
        "Unsupported type: ${this::class}. " +
                "Only UiTextArg implementations are supported."
    )
}

/**
 * Creates a [StringResourceArg] instance representing a string resource with optional arguments.
 *
 * @param resId The resource ID of the string to include.
 * @param args The [UiTextArgList] containing arguments for string formatting. Defaults to [EmptyUiArgs].
 * @return A [StringResourceArg] instance encapsulating the string resource and its arguments.
 */
fun stringResArg(
    @StringRes resId: Int,
    args: UiTextArgList = EmptyUiArgs,
): UiTextArg = StringResourceArg(resId, args.rawList)

/**
 * Creates a [StringResourceArg] instance representing a string resource with optional arguments.
 *
 * @param resId The resource ID of the string to include.
 * @param args The [UiTextArgList] containing arguments for string formatting. Defaults to [EmptyUiArgs].
 * @return A [StringResourceArg] instance encapsulating the string resource and its arguments.
 */
fun dynamicStringArg(
    value: String
): UiTextArg = DynamicStringArg(value)

/**
 * Creates a [PluralsResourceArg] instance representing a plurals resource with quantity and optional arguments.
 *
 * @param resId The resource ID of the plurals to include.
 * @param quantity The quantity used to select the appropriate plural form.
 * @param args The [UiTextArgList] containing arguments for plurals formatting. Defaults to [EmptyUiArgs].
 * @return A [PluralsResourceArg] instance encapsulating the plurals resource, quantity, and arguments.
 */
fun pluralsResArg(
    @PluralsRes resId: Int,
    quantity: Int,
    args: UiTextArgList = EmptyUiArgs,
): UiTextArg = PluralsResourceArg(resId, quantity, args.rawList)

/**
 * Creates an [IntegerResourceArg] instance representing an integer resource.
 *
 * @param resId The resource ID of the integer to include.
 * @return An [IntegerResourceArg] instance encapsulating the integer resource.
 */
fun integerResArg(@IntegerRes resId: Int): UiTextArg = IntegerResourceArg(resId)

/**
 * Creates a [BooleanResourceArg] instance representing a boolean resource.
 *
 * @param resId The resource ID of the boolean to include.
 * @return A [BooleanResourceArg] instance encapsulating the boolean resource.
 */
fun booleanResArg(@BoolRes resId: Int): UiTextArg = BooleanResourceArg(resId)
