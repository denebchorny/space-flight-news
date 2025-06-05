package com.denebchorny.core.common.android.resources.uiText.args

/**
 * Represents an empty list of [UiTextArg] instances.
 *
 * This singleton object is used as a default value when no arguments are provided
 * for a [UiText] resource. It ensures that functions expecting a [UiTextArgList]
 * can operate without requiring explicit argument lists.
 */
internal val EmptyUiArgs: UiTextArgList = UiTextArgListImpl(emptyList())

/**
 * A sealed interface representing a list of [UiTextArg] instances.
 *
 * Being a sealed interface, all implementations of [UiTextArgList] are known at compile-time,
 * providing better type safety and exhaustiveness checks. This interface extends [List]
 * of nullable [UiTextArg] elements, allowing it to be used wherever a list of arguments is required.
 */
sealed interface UiTextArgList {
    // The companion object is kept private to restrict instantiation outside this file.
    private companion object
}

/**
 * Internal implementation of the [UiTextArgList] interface.
 *
 * This class wraps a list of nullable [UiTextArg] instances and delegates all [List]
 * functionalities to the underlying [sourceList]. It overrides [toString], [hashCode],
 * and [equals] to ensure proper value-based comparisons and representations.
 *
 * @property sourceList The underlying list of nullable [UiTextArg] instances.
 */
internal class UiTextArgListImpl(
    val sourceList: List<UiTextArg?>,
) : UiTextArgList, List<UiTextArg?> by sourceList {

    override fun toString(): String = sourceList.toString()
    override fun hashCode(): Int = sourceList.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is UiTextArgListImpl) {
            return sourceList == other.sourceList
        }
        return false
    }
}

/**
 * Extension property to retrieve the raw list of [UiTextArg] instances from a [UiTextArgList].
 *
 * Casts the [UiTextArgList] to its internal implementation [UiTextArgListImpl] and returns
 * the underlying [sourceList]. This property should be used cautiously, ensuring that the
 * [UiTextArgList] is indeed an instance of [UiTextArgListImpl].
 *
 * @receiver The [UiTextArgList] instance.
 * @return The raw [List] of nullable [UiTextArg] instances.
 * @throws ClassCastException If the [UiTextArgList] is not an instance of [UiTextArgListImpl].
 */
internal val UiTextArgList.rawList: List<UiTextArg?>
    get() {
        this as UiTextArgListImpl
        return sourceList
    }

/**
 * Creates a [UiTextArgList] from a primary argument and a variable number of additional arguments.
 *
 * This function converts each argument to a [UiTextArg] using the [asUiArg] extension function,
 * collects them into a list, and wraps them into a [UiTextArgListImpl].
 *
 * @param args Additional arguments to include in the list.
 * @return A [UiTextArgList] containing all provided arguments.
 */
fun uiTextArgsOf(vararg args: Any?): UiTextArgList {
    val uiArgs = buildList {
        for (arg in args) {
            add(arg?.asUiArg())
        }
    }
    return UiTextArgListImpl(uiArgs)
}
