package com.denebchorny.core.designsystem.component.snackbar.vo

import androidx.compose.runtime.Stable
import com.denebchorny.core.designsystem.component.snackbar.visuals.UiSnackbarVisuals

@Stable
data class UiSnackbarData(
    val visuals: UiSnackbarVisuals,
    val dismiss: () -> Unit
)