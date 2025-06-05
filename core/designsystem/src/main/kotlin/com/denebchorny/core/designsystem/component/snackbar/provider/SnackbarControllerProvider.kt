package com.denebchorny.core.designsystem.component.snackbar.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.denebchorny.core.designsystem.component.observer.ObserveAsEvents
import com.denebchorny.core.designsystem.component.snackbar.controller.LocalSnackbarController
import com.denebchorny.core.designsystem.component.snackbar.controller.SnackbarController
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarMessage
import kotlinx.coroutines.flow.Flow

/**
 * Provides a [SnackbarController] and [SnackbarHostState] for displaying Snackbars.
 *
 * This composable sets up the necessary components for displaying Snackbars within a given
 * composable hierarchy. It creates a [SnackbarHostState], a [SnackbarController], and makes
 * the controller available via [LocalSnackbarController].  It also observes an optional
 * [Flow] of [SnackbarMessage] objects and automatically displays them.
 *
 * @param flow An optional [Flow] of [SnackbarMessage] objects.  If provided, any emitted
 *             [SnackbarMessage] will be displayed automatically.
 * @param content The composable content that will have access to the provided
 *                [SnackbarHostState] and [SnackbarController].  The `snackbarHost`
 *                parameter is the [SnackbarHostState] that should be used to host the
 *                Snackbars within this content.
 */
@Composable
fun SnackbarControllerProvider(
    flow: Flow<SnackbarMessage>? = SnackbarController.events,
    content: @Composable (snackbarHost: SnackbarHostState) -> Unit
) {
    // Use application context to avoid leaks
    val context = LocalContext.current.applicationContext

    val colorScheme = Theme.colorScheme

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val snackbarController = remember(colorScheme) {
        SnackbarController(
            host = snackbarHostState,
            scope = coroutineScope,
            context = context,
            colorScheme = colorScheme
        )
    }

    flow?.let {
        ObserveAsEvents(flow = it, key1 = snackbarHostState) { event ->
            snackbarController.showSnackbar(event)
        }
    }

    CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
        content(snackbarHostState)
    }
}