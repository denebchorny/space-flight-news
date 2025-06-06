package com.denebchorny.core.ui.extension

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * The base color used for the shimmer effect.
 */
val baseColor = Color(0xFFDBDBDB)

/**
 * Controls how the shimmer is drawn relative to the composable content.
 */
enum class ShimmerMode {
    BEHIND,   // Draw shimmer beneath the composable content
    OVER,     // Draw shimmer above the composable content
    ONLY      // Draw only the shimmer, ignoring the composable content
}

/**
 * Default values for the shimmer effect parameters.
 */
private object ShimmerDefaults {
    const val DURATION_MILLIS = 1000
    const val SHADOW_WIDTH = 300f
    const val ANGLE_DEGREES = 45f
    const val START_OFFSET = 0f
    const val END_OFFSET = 500f

    /**
     * The gradient colors used for the shimmer effect.
     */
    val shimmerColors = listOf(
        baseColor.copy(alpha = 0.1f),
        baseColor.copy(alpha = 0.2f),
        baseColor.copy(alpha = 0.1f)
    )
}

/**
 * A highly flexible shimmer effect modifier for Jetpack Compose.
 *
 * @param isEnabled If false, shimmer is disabled and this modifier becomes a no-op.
 * @param durationMillis Duration of one shimmer cycle in milliseconds.
 * @param colors Gradient colors used for the shimmer effect.
 * @param shadowWidth Thickness of the shimmer band in pixels.
 * @param drawMode Determines whether to draw the shimmer behind, over, or only the composable content.
 * @param shape Optional shape to clip the shimmer effect (e.g., [RoundedCornerShape]). If null, uses full bounds.
 * @param angleDegrees Direction of the shimmer in degrees (0 = left-to-right, 90 = top-to-bottom).
 * @param easing Easing function for the shimmer animation.
 * @param background Optional background color to display behind the shimmer effect.
 */
@Composable
fun Modifier.shimmerEffect(
    isEnabled: Boolean = true,
    durationMillis: Int = ShimmerDefaults.DURATION_MILLIS,
    colors: List<Color> = ShimmerDefaults.shimmerColors,
    shadowWidth: Float = ShimmerDefaults.SHADOW_WIDTH,
    drawMode: ShimmerMode = ShimmerMode.ONLY,
    shape: Shape? = RoundedCornerShape(15.dp),
    angleDegrees: Float = ShimmerDefaults.ANGLE_DEGREES,
    easing: Easing = LinearEasing,
    background: Color = Color.Transparent
): Modifier = composed {
    // If shimmer is disabled, return the original modifier
    if (!isEnabled) return@composed this

    // Track the composable’s measured width & height in pixels
    var containerWidth by remember { mutableFloatStateOf(0f) }
    var containerHeight by remember { mutableFloatStateOf(0f) }

    // Determine the maximum dimension to cover diagonal shimmer sweeps
    val maxDimension = maxOf(containerWidth, containerHeight)

    // Infinite transition for shimmer offset animation
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmerTransition")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -shadowWidth,                   // Start just off the composable
        targetValue = maxDimension + shadowWidth,      // End beyond the far edge
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = easing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerOffset"
    )

    // Capture the size of the composable
    this
        .onSizeChanged { size: IntSize ->
            containerWidth = size.width.toFloat()
            containerHeight = size.height.toFloat()
        }
        .run {
            when (drawMode) {
                ShimmerMode.BEHIND -> drawBehindShimmer(
                    shimmerOffset,
                    angleDegrees,
                    shadowWidth,
                    colors,
                    shape,
                    background // Pass background color
                )

                ShimmerMode.OVER -> drawOverShimmer(
                    shimmerOffset,
                    angleDegrees,
                    shadowWidth,
                    colors,
                    shape,
                    background // Pass background color
                )

                ShimmerMode.ONLY -> drawOnlyShimmer(
                    shimmerOffset,
                    angleDegrees,
                    shadowWidth,
                    colors,
                    shape,
                    background // Pass background color
                )
            }
        }
}

/**
 * A highly flexible shimmer effect modifier for Jetpack Compose.
 *
 * @param isEnabled If false, shimmer is disabled and this modifier is effectively no-op.
 * @param durationMillis How long (in ms) one cycle of shimmer should take before repeating.
 * @param colors The gradient colors used for the shimmer.
 * @param shadowWidth How "thick" the shimmer band should be, in pixels.
 * @param drawMode Whether to draw the shimmer behind, over, or instead of child content.
 * @param shape Optional shape to clip the shimmer into (e.g., [CircleShape], [RoundedCornerShape]).
 *              If null, the shimmer uses the full bounds.
 *
 * @param angleDegrees The direction (in degrees) of the shimmer gradient.
 *                     0 = left-to-right, 90 = top-to-bottom, 45 = diagonal, etc.
 * @param startOffset The initial offset for the shimmer animation.
 * @param endOffset The final offset for the shimmer animation (the shimmer travels from startOffset to endOffset).
 * @param easing Easing function for how the shimmer moves (e.g., [LinearEasing], [FastOutSlowInEasing]).
 */
@Composable
fun Modifier.shimmerEffect(
    isEnabled: Boolean = true,
    durationMillis: Int = ShimmerDefaults.DURATION_MILLIS,
    colors: List<Color> = ShimmerDefaults.shimmerColors,
    shadowWidth: Float = ShimmerDefaults.SHADOW_WIDTH,
    drawMode: ShimmerMode = ShimmerMode.ONLY,
    shape: Shape? = RoundedCornerShape(15.dp),
    angleDegrees: Float = ShimmerDefaults.ANGLE_DEGREES,
    startOffset: Float = ShimmerDefaults.START_OFFSET,
    endOffset: Float = ShimmerDefaults.END_OFFSET,
    easing: Easing = LinearEasing,
    background: Color = Color.Transparent
): Modifier {
    // If shimmer is disabled, we do nothing
    if (!isEnabled) return this

    // Create an infinite transition for the shimmer's offset
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmerTransition")

    // Animate the offset from startOffset to endOffset
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = startOffset,
        targetValue = endOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = easing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerOffset"
    )

    return when (drawMode) {
        ShimmerMode.BEHIND -> drawBehindShimmer(
            shimmerOffset,
            angleDegrees,
            shadowWidth,
            colors,
            shape,
            background // Pass background color
        )

        ShimmerMode.OVER -> drawOverShimmer(
            shimmerOffset,
            angleDegrees,
            shadowWidth,
            colors,
            shape,
            background // Pass background color
        )

        ShimmerMode.ONLY -> drawOnlyShimmer(
            shimmerOffset,
            angleDegrees,
            shadowWidth,
            colors,
            shape,
            background // Pass background color
        )
    }
}

/**
 * Draws the shimmer effect behind the composable content, with an optional background color.
 *
 * @param offset Current shimmer offset.
 * @param angleDegrees Direction of the shimmer.
 * @param shadowWidth Thickness of the shimmer band.
 * @param colors Gradient colors for the shimmer.
 * @param shape Optional shape to clip the shimmer effect.
 * @param background Background color to draw behind the shimmer.
 */
private fun Modifier.drawBehindShimmer(
    offset: Float,
    angleDegrees: Float,
    shadowWidth: Float,
    colors: List<Color>,
    shape: Shape?,
    background: Color
): Modifier {
    return drawBehind {
        // Draw the background color first
        if (background != Color.Transparent) {
            drawRect(
                color = background,
                size = size,
                topLeft = Offset.Zero,
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
        }

        // Create the shimmer gradient brush
        val brush = buildShimmerBrush(offset, angleDegrees, shadowWidth, colors)

        // Draw the shimmer effect
        if (shape == null) {
            drawRect(brush = brush)
        } else {
            val outline = shape.createOutline(size, layoutDirection, this)
            drawOutline(outline = outline, brush = brush)
        }
    }
}

/**
 * Draws the shimmer effect over the composable content, with an optional background color.
 *
 * @param offset Current shimmer offset.
 * @param angleDegrees Direction of the shimmer.
 * @param shadowWidth Thickness of the shimmer band.
 * @param colors Gradient colors for the shimmer.
 * @param shape Optional shape to clip the shimmer effect.
 * @param background Background color to draw behind the shimmer.
 */
private fun Modifier.drawOverShimmer(
    offset: Float,
    angleDegrees: Float,
    shadowWidth: Float,
    colors: List<Color>,
    shape: Shape?,
    background: Color
): Modifier {
    return drawWithContent {
        // Draw the background color first
        if (background != Color.Transparent) {
            drawRect(
                color = background,
                size = size,
                topLeft = Offset.Zero,
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
        }

        // Draw child composables
        drawContent()

        // Create the shimmer gradient brush
        val brush = buildShimmerBrush(offset, angleDegrees, shadowWidth, colors)

        // Draw the shimmer effect over the content
        if (shape == null) {
            drawRect(brush = brush)
        } else {
            val outline = shape.createOutline(size, layoutDirection, this)
            drawOutline(outline = outline, brush = brush)
        }
    }
}

/**
 * Draws only the shimmer effect, ignoring the composable content, with an optional background color.
 *
 * @param offset Current shimmer offset.
 * @param angleDegrees Direction of the shimmer.
 * @param shadowWidth Thickness of the shimmer band.
 * @param colors Gradient colors for the shimmer.
 * @param shape Optional shape to clip the shimmer effect.
 * @param background Background color to draw behind the shimmer.
 */
private fun Modifier.drawOnlyShimmer(
    offset: Float,
    angleDegrees: Float,
    shadowWidth: Float,
    colors: List<Color>,
    shape: Shape?,
    background: Color
): Modifier {
    return drawWithContent {
        // Draw the background color first
        if (background != Color.Transparent) {
            drawRect(
                color = background,
                size = size,
                topLeft = Offset.Zero,
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
        }

        // Create the shimmer gradient brush
        val brush = buildShimmerBrush(offset, angleDegrees, shadowWidth, colors)

        // Draw the shimmer effect only
        if (shape == null) {
            drawRect(brush = brush)
        } else {
            val outline = shape.createOutline(size, layoutDirection, this)
            drawOutline(outline = outline, brush = brush)
        }
    }
}

/**
 * Builds a linear gradient brush for the shimmer effect based on the current offset and angle.
 *
 * @param offset Current shimmer offset.
 * @param angleDegrees Direction of the shimmer.
 * @param shadowWidth Thickness of the shimmer band.
 * @param colors Gradient colors for the shimmer.
 * @return A [Brush] representing the shimmer gradient.
 */
private fun buildShimmerBrush(
    offset: Float,
    angleDegrees: Float,
    shadowWidth: Float,
    colors: List<Color>
): Brush {
    // Convert angle to radians
    val angleRad = Math.toRadians(angleDegrees.toDouble())
    val dx = cos(angleRad).toFloat()
    val dy = sin(angleRad).toFloat()

    // Calculate start and end points for the gradient
    val startX = offset * dx
    val startY = offset * dy
    val endX = (offset + shadowWidth) * dx
    val endY = (offset + shadowWidth) * dy

    return Brush.linearGradient(
        colors = colors,
        start = Offset(startX, startY),
        end = Offset(endX, endY)
    )
}
