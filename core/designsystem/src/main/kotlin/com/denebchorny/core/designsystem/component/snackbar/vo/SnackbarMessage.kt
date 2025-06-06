package com.denebchorny.core.designsystem.component.snackbar.vo

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.vector.ImageVector

data class SnackbarMessage(
    val text: SnackbarText,
    val withDismissAction: Boolean = false,
    val neutralAction: SnackbarAction? = null,
    val positiveAction: SnackbarAction? = null,
    val negativeAction: SnackbarAction? = null,
    val duration: SnackbarDuration = if (neutralAction == null && positiveAction == null && negativeAction == null) {
        SnackbarDuration.Short
    } else SnackbarDuration.Indefinite,
    val contentColor: SnackbarColor,
    val containerColor: SnackbarColor,
    val icon: ImageVector? = null,
    val iconTint: SnackbarColor = contentColor,
    val mode: SnackbarEnterMode = SnackbarEnterMode.Force
)

enum class SnackbarEnterMode {
    Enqueue,
    Force
}