package com.denebchorny.core.designsystem.component.snackbar.controller

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.denebchorny.core.designsystem.component.snackbar.visuals.UiSnackbarVisuals
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarEnterMode
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarMessage
import com.denebchorny.core.designsystem.component.snackbar.vo.getColor
import com.denebchorny.core.designsystem.component.snackbar.vo.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * A controller for displaying Snackbars within a Compose application.
 *
 * This class provides a centralized way to manage and display Snackbars using a [SnackbarHostState].
 * It handles the creation of [UiSnackbarVisuals] from [SnackbarMessage] objects and launches
 * the Snackbar display within a coroutine scope.
 *
 * @param host The [SnackbarHostState] used to display the Snackbars.
 * @param scope The [CoroutineScope] in which to launch the Snackbar display.  This should typically
 *              be a scope tied to the lifecycle of the composable that hosts the Snackbar.
 * @param context The Android [Context] required for resource access (e.g., strings, colors).
 *                Important, only use applicationContext to avoid leaks
 * @param colorScheme The [ColorScheme] used for theming the Snackbar.
 */
@Immutable
class SnackbarController(
    private val host: SnackbarHostState,
    private val scope: CoroutineScope,
    private val context: Context,
    private val colorScheme: ColorScheme
) {

    /**
     * Shows a Snackbar with the given [SnackbarMessage].
     *
     * This function creates a [UiSnackbarVisuals] object from the provided [SnackbarMessage] and
     * uses the [SnackbarHostState] to display the Snackbar.  It handles potential conflicts
     * by dismissing any currently displayed Snackbar if the [SnackbarMessage]'s `mode` is [SnackbarEnterMode.Force].
     *
     * @param message The [SnackbarMessage] containing the information to display in the Snackbar.
     */
    fun showSnackbar(message: SnackbarMessage) {
        scope.launch {
            if (message.mode == SnackbarEnterMode.Force) {
                host.currentSnackbarData?.dismiss()
            }

            host.showSnackbar(
                UiSnackbarVisuals(
                    message = message.text.getString(context),
                    withDismissAction = message.withDismissAction,
                    neutralAction = message.neutralAction,
                    positiveAction = message.positiveAction,
                    negativeAction = message.negativeAction,
                    duration = message.duration,
                    contentColor = message.contentColor.getColor(context, colorScheme),
                    containerColor = message.containerColor.getColor(context, colorScheme),
                    icon = message.icon,
                    iconTint = message.iconTint.getColor(context, colorScheme)
                )
            )
        }
    }

    companion object {
        private val eventsHandler = Channel<SnackbarMessage>()
        internal val events = eventsHandler.receiveAsFlow()

        /**
         * Sends a new event. This call suspends until the event is received.
         *
         * @param value The event value to send.
         */
        internal suspend fun send(value: SnackbarMessage) {
            eventsHandler.send(value)
        }

        /**
         * Attempts to send a new event without suspending.
         * Returns true if the event was successfully sent, or false otherwise.
         *
         * @param value The event value to attempt to send.
         * @return Whether the event was successfully sent.
         */
        internal fun trySend(value: SnackbarMessage): Boolean {
            return eventsHandler.trySend(value).isSuccess
        }

        /**
         * Provides access to the current [SnackbarController] instance.
         *
         * This property allows composables to retrieve the current [SnackbarController] instance
         * using `SnackbarController.current`.  It is intended to be used within a composition context
         * where a [SnackbarController] has been provided via [LocalSnackbarController].
         */
        val current: SnackbarController
            @Composable
            @ReadOnlyComposable
            get() = LocalSnackbarController.current
    }
}

/**
 * A [CompositionLocal] key for the [SnackbarController].
 *
 * This provides a way to access the current [SnackbarController] instance within a composable hierarchy.
 *  It's crucial to provide a value for this `CompositionLocal` at the root of the relevant composable
 *  hierarchy to make the `SnackbarController` available.
 */
val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error(
        "SnackbarHostState was not provided. Remember that you can invoke " +
                "LocalSnackbarController.current only when it has been defined as a provider " +
                "inside a composable or inside a Screenlayout that already provides it."
    )
}