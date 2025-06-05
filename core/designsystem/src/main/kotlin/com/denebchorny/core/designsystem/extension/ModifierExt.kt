package com.denebchorny.core.designsystem.extension

import android.view.MotionEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter

inline fun <T> Modifier.nullConditional(
    argument: T?,
    ifNotNull: Modifier.(T) -> Modifier,
    ifNull: Modifier.() -> Modifier = { this },
): Modifier {
    return if (argument != null) {
        then(ifNotNull(Modifier, argument))
    } else {
        then(ifNull(Modifier))
    }
}

fun Modifier.conditionalIf(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier {
    return if (condition) {
        this.then(modifier(this))
    } else {
        this
    }
}

inline fun Modifier.conditional(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: Modifier.() -> Modifier = { this },
): Modifier = if (condition) {
    then(ifTrue(Modifier))
} else {
    then(ifFalse(Modifier))
}

/**
 * Takes a condition and modifier function as arguments
 * returns new modifier with function applied based on condition
 * helps in chaining modifier where some functionality needs to be applied based on condition
 */
fun Modifier.conditional(condition: Boolean, function: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(function())
    } else {
        this
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onActionDownChange(callback: (Boolean) -> Unit): Modifier {
    return this.pointerInteropFilter { motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                callback(true)
                true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                callback(false)
                true
            }
            else -> false
        }
    }
}

fun Modifier.rotate(degrees: Float): Modifier = this.then(
    Modifier.graphicsLayer(rotationZ = degrees)
)

/**
 * Wraps the Modifier in a [Modifier.clickable] if `onClick` is not null.
 * When `onClick` is null, leaves the Modifier unchanged (no click behavior).
 */
fun Modifier.optionalClickable(onClick: (() -> Unit)?): Modifier {
    return if (onClick != null) {
        this.clickable(onClick = onClick)
    } else {
        this
    }
}

fun Modifier.optionalToggleable(
    toggleValue: Boolean,
    interactionSource: MutableInteractionSource? = null,
    onToggled: ((Boolean) -> Unit)? = null
): Modifier {
    return if (onToggled != null) {
        this.toggleable(
            value = toggleValue,
            interactionSource = interactionSource,
            indication = null,
            enabled = true,
            role = null,
            onValueChange = onToggled
        )
    } else {
        this
    }
}