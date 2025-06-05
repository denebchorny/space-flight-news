package com.denebchorny.core.designsystem.component.snackbar.vo

import androidx.compose.ui.graphics.Color

data class SnackbarAction(
    val label: String,
    val color: Color = Color.Unspecified,
    val onClick: () -> Unit
)