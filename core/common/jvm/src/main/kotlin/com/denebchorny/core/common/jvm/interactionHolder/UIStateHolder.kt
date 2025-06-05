package com.denebchorny.core.common.jvm.interactionHolder

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A generic holder class for UI state that represents an ongoing, persistent piece of data.
 *
 * This class uses a [MutableStateFlow] internally and exposes a read-only [StateFlow].
 * It's intended for managing UI-related state that changes over time and should be observed
 * by the UI layer. For example, loading states, user input, fetched data, etc.
 *
 * @param T The type of state this holder will manage.
 * @param initialValue The initial value of the state.
 */
class UIStateHolder<T>(initialValue: T) {
    private val _state = MutableStateFlow(initialValue)
    val flow: StateFlow<T> get() = _state.asStateFlow()

    /**
     * Sets a new value for the state, immediately updating any collectors of [flow].
     *
     * @param value The new state value.
     */
    fun setValue(value: T) {
        _state.value = value
    }

    /**
     * Atomically updates the state value using the specified [function]. This is useful
     * for state transformations that depend on the current value.
     *
     * @param function A function that receives the current state and returns the new state.
     */
    fun update(function: (T) -> T) = _state.update(function)
}