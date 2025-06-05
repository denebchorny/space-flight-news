package com.denebchorny.core.designsystem.component.statusBar

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import com.denebchorny.core.designsystem.theme.appearance.Appearance
import com.denebchorny.core.designsystem.theme.appearance.Appearance.Dark
import com.denebchorny.core.designsystem.theme.appearance.Appearance.FollowSystem
import com.denebchorny.core.designsystem.theme.appearance.Appearance.Light

@Composable
fun StatusBar(
    appearance: Appearance,
    isSystemDark: Boolean,
    statusBarLight: Color = Color.Transparent,
    statusBarDark: Color = Color.Transparent,
    navigationBarLight: Color = Color.Transparent,
    navigationBarDark: Color = Color.Transparent,
) {
    if (!LocalInspectionMode.current) {
        val context = LocalContext.current as ComponentActivity

        // Utility to determine system bar style based on appearance and theme
        fun resolveSystemBarStyle(
            appearance: Appearance,
            lightColor: Color,
            darkColor: Color
        ): SystemBarStyle {
            return when (appearance) {
                Light -> SystemBarStyle.light(lightColor.toArgb(), darkColor.toArgb())
                Dark -> SystemBarStyle.dark(darkColor.toArgb())
                FollowSystem -> if (!isSystemDark) {
                    SystemBarStyle.light(lightColor.toArgb(), darkColor.toArgb())
                } else {
                    SystemBarStyle.dark(darkColor.toArgb())
                }
            }
        }

        // Define styles
        val statusBarStyle = resolveSystemBarStyle(appearance, statusBarLight, statusBarDark)
        val navigationBarStyle =
            resolveSystemBarStyle(appearance, navigationBarLight, navigationBarDark)

        // Apply styles using DisposableEffect
        DisposableEffect(appearance, isSystemDark) {
            context.enableEdgeToEdge(
                statusBarStyle = statusBarStyle,
                navigationBarStyle = navigationBarStyle
            )

            // No cleanup needed in this case
            onDispose { }
        }
    }
}