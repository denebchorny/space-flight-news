package theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import theme.Appearance.Dark
import theme.Appearance.FollowSystem
import theme.Appearance.Light
import theme.color.schemes.DarkColorScheme
import theme.color.schemes.LightColorScheme
import theme.dimension.LocalSpacing
import theme.dimension.Spacing
import theme.typography.Typography

@Composable
fun ApplicationTheme(
    appearance: Appearance = Appearance.default,
    isSystemDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val spacing = Spacing.default

    val colorScheme = rememberColorScheme(appearance, isSystemDark)

    CompositionLocalProvider(LocalSpacing provides spacing) {
        // StatusBar(appearance, isSystemDark)
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