package com.denebchorny.core.common.android.viewmodel

/**
 * A generic interface for handling UI events of a specific type.
 *
 * This interface allows a class to listen to and handle events that occur in the UI.
 * It is designed to be generic, meaning it can handle events of any type `T`.
 *
 * Will be mostly used as an interface for ViewModels to listen to UI events of a specific type
 * to enable communication between the UI and the ViewModel, where the UI triggers events
 * (e.g., user interactions) that the ViewModel handles.
 *
 * @param T The type of the event that this listener will handle.
 */
interface UIEventListener<T> {
    fun onEvent(event: T)
}