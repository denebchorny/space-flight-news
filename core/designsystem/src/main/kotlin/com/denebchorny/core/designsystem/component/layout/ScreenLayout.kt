package com.denebchorny.core.designsystem.component.layout

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.component.snackbar.Snackbar
import com.denebchorny.core.designsystem.component.snackbar.provider.SnackbarControllerProvider
import com.denebchorny.core.designsystem.component.snackbar.visuals.UiSnackbarVisuals
import com.denebchorny.core.designsystem.component.snackbar.vo.UiSnackbarData

/**
 * A composable function that serves as the base layout for the app's content.
 *
 * @param topBar A composable for the top bar content. Defaults to an empty lambda.
 * @param bottomBar A composable for the bottom bar content. Defaults to an empty lambda.
 * @param floatingActionButton A composable for the Floating Action Button (FAB). Defaults to an empty lambda.
 * @param floatingActionButtonPosition The position of the FAB. Defaults to [FabPosition.End].
 * @param contentWindowInsets The window insets for the content area. Defaults to [ScaffoldDefaults.contentWindowInsets].
 * @param content The main content to be displayed in the layout.
 */
@Composable
fun ScreenLayout(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable () -> Unit,
) {
    SnackbarControllerProvider { snackBarHost ->
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = topBar,
            bottomBar = bottomBar,
            floatingActionButton = floatingActionButton,
            floatingActionButtonPosition = floatingActionButtonPosition,
            snackbarHost = { SnackbarHostImpl(snackBarHost) },
            contentWindowInsets = contentWindowInsets
        ) { values ->
            Surface(
                color = Theme.colorScheme.surface,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values),
            ) {
                CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun SnackbarHostImpl(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        when (val visuals = data.visuals) {
            is UiSnackbarVisuals -> Snackbar(UiSnackbarData(visuals, data::dismiss))
            else -> {}
        }
    }
}

@PreviewComponent
@Composable
private fun FragmentPreview() {
    ApplicationTheme {
        ScreenLayout {

        }
    }
}