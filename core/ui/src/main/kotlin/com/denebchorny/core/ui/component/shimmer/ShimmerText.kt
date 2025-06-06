package com.denebchorny.core.ui.component.shimmer

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.denebchorny.core.ui.extension.shimmerEffect

@Composable
fun ShimmerText(
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle
) {
    Text(
        modifier = modifier.shimmerEffect(),
        text = "",
        fontWeight = fontWeight,
        style = style,
    )
}