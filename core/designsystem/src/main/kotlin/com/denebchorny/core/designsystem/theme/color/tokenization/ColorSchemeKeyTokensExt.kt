package com.denebchorny.core.designsystem.theme.color.tokenization

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.denebchorny.core.designsystem.theme.Theme

@Composable
fun ColorSchemeKeyTokens.toColor(): Color = Theme.colorScheme.fromToken(value = this)

@Stable
fun ColorScheme.fromToken(value: ColorSchemeKeyTokens): Color {
    return when (value) {
        ColorSchemeKeyTokens.Unspecified -> Color.Unspecified

        ColorSchemeKeyTokens.Primary -> primary
        ColorSchemeKeyTokens.OnPrimary -> onPrimary
        ColorSchemeKeyTokens.PrimaryContainer -> primaryContainer
        ColorSchemeKeyTokens.OnPrimaryContainer -> onPrimaryContainer

        ColorSchemeKeyTokens.Error -> error
        ColorSchemeKeyTokens.OnError -> onError
        ColorSchemeKeyTokens.ErrorContainer -> errorContainer
        ColorSchemeKeyTokens.OnErrorContainer -> onErrorContainer
    }
}