package com.denebchorny.core.designsystem.component.snackbar.interactionHolder

import com.denebchorny.core.designsystem.component.snackbar.controller.SnackbarController
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarMessage
import kotlinx.coroutines.channels.Channel

/**
 * A holder class for UI Snackbar events that ensures only one subscriber receives them.
 * Typically, used inside a viewmodel to trigger snackbar messages to the screen.
 *
 * This class uses a [Channel] to allow:
 * - Sending events using [showSnackbar] or [tryShowSnackbar].
 * - Collecting events as a Flow using [flow].
 *
 * @param T The type of event this holder will manage.
 */
class UISnackbarHolder {

    /**
     * Sends a new event. This call suspends until the event is received.
     *
     * @param value The event value to send.
     */
    suspend fun showSnackbar(value: SnackbarMessage) {
        SnackbarController.send(value)
    }

    /**
     * Attempts to send a new event without suspending.
     * Returns true if the event was successfully sent, or false otherwise.
     *
     * @param value The event value to attempt to send.
     * @return Whether the event was successfully sent.
     */
    fun tryShowSnackbar(value: SnackbarMessage): Boolean {
        return SnackbarController.trySend(value)
    }
}





