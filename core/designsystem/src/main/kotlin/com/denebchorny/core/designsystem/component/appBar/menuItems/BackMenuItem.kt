package com.denebchorny.core.designsystem.component.appBar.menuItems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import com.denebchorny.core.designsystem.component.appBar.MenuItem

@Composable
fun BackMenuItem(onClick: () -> Unit) {
    MenuItem(
        onClick = onClick,
        icon = Icons.Filled.ChevronLeft,
        contentDescription = null,
    )
}