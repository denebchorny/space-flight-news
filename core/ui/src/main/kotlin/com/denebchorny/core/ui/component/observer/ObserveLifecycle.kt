package com.denebchorny.core.ui.component.observer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle.Event.ON_ANY
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.denebchorny.core.common.android.viewmodel.LifecycleListener

/**
 * A composable function that integrates lifecycle event handling into a Compose environment, allowing
 * seamless interaction between `LifecycleOwner` events and Compose UI components or business logic.
 *
 * This function provides callbacks for each lifecycle event and supports an optional `subscriber`
 * parameter for classes implementing `LifecycleListener`. This makes it particularly
 * useful for managing resources, starting/stopping tasks, or observing lifecycle changes in a structured way.
 *
 * @param subscriber An optional parameter that could be a `ViewModel` implementing `LifecycleListener`.
 *                   If provided, the subscriber's lifecycle methods are invoked automatically for corresponding events.
 * @param onAny Callback for any lifecycle event. Invoked when any lifecycle event occurs.
 * @param onCreate Callback for the `ON_CREATE` event. Invoked when the lifecycle enters the "Created" state.
 * @param onStart Callback for the `ON_START` event. Invoked when the lifecycle enters the "Started" state.
 * @param onResume Callback for the `ON_RESUME` event. Invoked when the lifecycle enters the "Resumed" state.
 * @param onPause Callback for the `ON_PAUSE` event. Invoked when the lifecycle enters the "Paused" state.
 * @param onStop Callback for the `ON_STOP` event. Invoked when the lifecycle enters the "Stopped" state.
 * @param onDestroy Callback for the `ON_DESTROY` event. Invoked when the lifecycle enters the "Destroyed" state.
 *
 * The `subscriber` parameter allows coupling this function with ViewModels that adhere to `LifecycleListener` interface,
 * enabling a clear separation of lifecycle-dependent logic while maintaining composability.
 *
 * Example usage with callbacks:
 * ```
 * @Composable
 * fun MyScreen() {
 *     Lifecycle(
 *         onCreate = { Log.d("Lifecycle", "ON_CREATE") },
 *         onResume = { Log.d("Lifecycle", "ON_RESUME") },
 *         onDestroy = { Log.d("Lifecycle", "ON_DESTROY") }
 *     )
 *     Text("Hello, world!")
 * }
 * ```
 *
 * Example usage with a ViewModel subscriber:
 * ```
 * class MyViewModel : ViewModel(), LifecycleListener {
 *     override fun onCreate() { Log.d("Lifecycle", "ViewModel: ON_CREATE") }
 *     override fun onDestroy() { Log.d("Lifecycle", "ViewModel: ON_DESTROY") }
 * }
 *
 * @Composable
 * fun MyScreen(myViewModel: MyViewModel) {
 *     Lifecycle(subscriber = myViewModel)
 *     Text("Hello, world!")
 * }
 * ```
 */
@Composable
fun Lifecycle(
    onAny: (LifecycleOwner.() -> Unit)? = null,
    onCreate: (LifecycleOwner.() -> Unit)? = null,
    onStart: (LifecycleOwner.() -> Unit)? = null,
    onResume: (LifecycleOwner.() -> Unit)? = null,
    onPause: (LifecycleOwner.() -> Unit)? = null,
    onStop: (LifecycleOwner.() -> Unit)? = null,
    onDestroy: (LifecycleOwner.() -> Unit)? = null,
    subscriber: LifecycleListener? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                ON_ANY -> {
                    subscriber?.onAny()
                    onAny?.invoke(source)
                }

                ON_CREATE -> {
                    subscriber?.onCreate()
                    onCreate?.invoke(source)
                }

                ON_START -> {
                    subscriber?.onStart()
                    onStart?.invoke(source)
                }

                ON_RESUME -> {
                    subscriber?.onResume()
                    onResume?.invoke(source)
                }

                ON_PAUSE -> {
                    subscriber?.onPause()
                    onPause?.invoke(source)
                }

                ON_STOP -> {
                    subscriber?.onStop()
                    onStop?.invoke(source)
                }

                ON_DESTROY -> {
                    subscriber?.onDestroy()
                    onDestroy?.invoke(source)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
