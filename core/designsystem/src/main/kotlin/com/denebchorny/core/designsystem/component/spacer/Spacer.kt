package com.denebchorny.core.designsystem.component.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * A horizontal spacer that adds empty space with a specified width.
 *
 * @param width The width of the spacer in Dp.
 *
 * Usage:
 * ```
 * Row {
 *     Text("Start")
 *     HorizontalSpacer(width = 16.dp)
 *     Text("End")
 * }
 * ```
 */
@Composable
@NonRestartableComposable
fun HorizontalSpacer(width: Dp) = Spacer(modifier = Modifier.width(width))

/**
 * A vertical spacer that adds empty space with a specified height.
 *
 * @param height The height of the spacer in Dp.
 *
 * Usage:
 * ```
 * Column {
 *     Text("Above")
 *     VerticalSpacer(height = 8.dp)
 *     Text("Below")
 * }
 * ```
 */
@Composable
@NonRestartableComposable
fun VerticalSpacer(height: Dp) = Spacer(modifier = Modifier.height(height))

/**
 * A generic spacer that adds empty space with a specified width and height.
 *
 * @param width The width of the spacer in Dp.
 * @param height The height of the spacer in Dp.
 *
 * Usage:
 * ```
 * Spacer(width = 16.dp, height = 8.dp)
 * ```
 */
@Composable
@NonRestartableComposable
fun Spacer(width: Dp, height: Dp) = Spacer(modifier = Modifier.size(width, height))

/**
 * A square spacer that adds empty space with equal width and height.
 *
 * @param size The size of the spacer (both width and height) in Dp.
 *
 * Usage:
 * ```
 * Spacer(size = 16.dp)
 * ```
 */
@Composable
@NonRestartableComposable
fun Spacer(size: Dp) = Spacer(modifier = Modifier.size(size))