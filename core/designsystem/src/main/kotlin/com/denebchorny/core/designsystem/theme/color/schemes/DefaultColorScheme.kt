package com.denebchorny.core.designsystem.theme.color.schemes

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.denebchorny.core.designsystem.theme.color.Pink40
import com.denebchorny.core.designsystem.theme.color.Pink80
import com.denebchorny.core.designsystem.theme.color.Purple40
import com.denebchorny.core.designsystem.theme.color.Purple80
import com.denebchorny.core.designsystem.theme.color.PurpleGrey40
import com.denebchorny.core.designsystem.theme.color.PurpleGrey80

internal val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

internal val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)