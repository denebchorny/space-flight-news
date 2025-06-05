package com.denebchorny.core.common.android.viewmodel

/**
 * An interface defining lifecycle event callbacks. Classes implementing this interface can handle
 * lifecycle events such as `onCreate`, `onStart`, `onResume`, etc.
 *
 * This interface is designed to be implemented by ViewModels used as subscribers in the `Lifecycle` function,
 * facilitating a clear structure for lifecycle event handling in business logic or resource management.
 */
interface LifecycleListener {
    fun onAny() {}
    fun onCreate() {}
    fun onStart() {}
    fun onResume() {}
    fun onPause() {}
    fun onStop() {}
    fun onDestroy() {}
}