package com.denebchorny.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.denebchorny.core.designsystem.component.statusBar.StatusBar
import com.denebchorny.core.designsystem.theme.appearance.Appearance
import com.denebchorny.core.designsystem.theme.appearance.Appearance.Dark
import com.denebchorny.core.designsystem.theme.appearance.Appearance.FollowSystem
import com.denebchorny.core.designsystem.theme.appearance.Appearance.Light
import com.denebchorny.core.designsystem.theme.color.schemes.DarkColorScheme
import com.denebchorny.core.designsystem.theme.color.schemes.LightColorScheme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import com.denebchorny.core.designsystem.theme.dimension.Spacing
import com.denebchorny.core.designsystem.theme.typography.Typography

@Composable
fun ApplicationTheme(
    appearance: Appearance = Appearance.default,
    isSystemDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val spacing = Spacing.default

    val colorScheme = rememberColorScheme(appearance, isSystemDark)

    CompositionLocalProvider(LocalSpacing provides spacing) {
        StatusBar(appearance, isSystemDark)
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
private fun rememberColorScheme(
    appearance: Appearance,
    isSystemDark: Boolean
): ColorScheme {
    return remember(appearance, isSystemDark) {
        when (appearance) {
            Light -> LightColorScheme
            Dark -> DarkColorScheme
            FollowSystem -> if (isSystemDark) DarkColorScheme else LightColorScheme
        }
    }
}