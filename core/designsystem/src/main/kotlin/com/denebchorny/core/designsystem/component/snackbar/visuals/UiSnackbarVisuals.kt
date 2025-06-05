package com.denebchorny.core.designsystem.component.snackbar.visuals

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarAction

data class UiSnackbarVisuals(
    override val message: String,
    override val withDismissAction: Boolean = false,
    val neutralAction: SnackbarAction? = null,
    val positiveAction: SnackbarAction? = null,
    val negativeAction: SnackbarAction? = null,
    override val duration: SnackbarDuration = if (
        neutralAction != null ||
        positiveAction != null ||
        negativeAction != null ||
        withDismissAction
        ) SnackbarDuration.Indefinite else SnackbarDuration.Short,
    val contentColor: Color,
    val containerColor: Color,
    val icon: ImageVector? = null,
    val iconTint: Color = contentColor
) : SnackbarVisuals {
    // This is not used but is added here to avoid misuse as a mandatory property
    override val actionLabel: String? = null
}