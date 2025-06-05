package theme.dimension

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val none: Dp,
    val tiny: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val default: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
) {
    companion object {
        val default = Spacing(
            none = 0.dp,
            tiny = 2.dp,
            extraSmall = 4.dp,
            small = 8.dp,
            default = 12.dp,
            medium = 16.dp,
            large = 24.dp,
            extraLarge = 32.dp
        )
    }
}

val LocalSpacing = staticCompositionLocalOf { Spacing.default }