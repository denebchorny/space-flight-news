package com.denebchorny.core.designsystem.component.observer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Simplifies the process of observing a Flow of data and reacting to emitted values.
 * [flow]: The Flow of data to be observed. This Flow emits values that will trigger
 *         the onEvent callback.
 * [key1], [key2]: Optional keys that control the recomposition behavior of the LaunchedEffect.
 *         If these keys change, the LaunchedEffect will be re-launched, effectively restarting
 *         the data collection process. This can be useful for fine-tuning the update logic
 *         based on specific conditions.
 * [onEvent]: A callback function that will be invoked for each emitted value from the Flow.
 *         This callback receives the emitted value as a parameter and can be used to update the UI
 *         or perform other actions.
 * */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}