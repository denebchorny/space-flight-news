package com.denebchorny.core.common.jvm.interactionHolder

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * A generic holder class for UI events that occur once and should be collected only when needed.
 *
 * This class uses a [MutableSharedFlow] internally and exposes a read-only [SharedFlow].
 * It allows you to:
 * - Emit new events using [emit] or [tryEmit].
 * - Collect these events in a composable or any other part of the code that needs to handle one-time UI actions
 *   (e.g., navigation, showing a toast, dialog prompts).
 *
 * @param T The type of event this holder will manage.
 */
class UIActionHolder<T> {
    private val _events = MutableSharedFlow<T>()
    val flow: SharedFlow<T> get() = _events.asSharedFlow()

    /**
     * Emits a new event. This call suspends until there is space in the buffer or
     * until the flow collector is ready to receive it.
     *
     * @param value The event value to emit.
     */
    suspend fun emit(value: T) {
        _events.emit(value)
    }

    /**
     * Attempts to emit a new event without suspending.
     * Returns `true` if the event was successfully emitted, or `false` otherwise.
     *
     * @param value The event value to attempt to emit.
     * @return Whether the event was successfully emitted.
     */
    fun tryEmit(value: T): Boolean {
        return _events.tryEmit(value)
    }
}





