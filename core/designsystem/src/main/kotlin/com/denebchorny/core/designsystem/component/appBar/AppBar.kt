@file:OptIn(ExperimentalMaterial3Api::class)

package com.denebchorny.core.designsystem.component.appBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.appBar.menuItems.BackMenuItem
import com.denebchorny.core.designsystem.component.button.TextButton
import com.denebchorny.core.designsystem.component.layout.ScreenLayout
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.preview.PreviewPhone
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import androidx.compose.material3.CenterAlignedTopAppBar as CenterAlignedTopAppBarM3

sealed interface DropdownActionMenuState {
    data object Expanded : DropdownActionMenuState
    data object Collapsed : DropdownActionMenuState
}

interface DropdownActionMenuScope {
    val state: DropdownActionMenuState
    fun toggleTo(value: Boolean)
    fun expand()
    fun collapse()
    fun toggle()
}

private class DropdownActionMenuScopeImpl : DropdownActionMenuScope {
    private var _state: DropdownActionMenuState by mutableStateOf(DropdownActionMenuState.Collapsed)
    override val state: DropdownActionMenuState
        get() = _state

    fun isExpanded() = _state == DropdownActionMenuState.Expanded

    override fun toggleTo(value: Boolean) {
        _state = if (value) DropdownActionMenuState.Expanded else DropdownActionMenuState.Collapsed
    }

    override fun expand() {
        _state = DropdownActionMenuState.Expanded
    }

    override fun collapse() {
        _state = DropdownActionMenuState.Collapsed
    }

    override fun toggle() {
        _state = when (_state) {
            DropdownActionMenuState.Expanded -> DropdownActionMenuState.Collapsed
            DropdownActionMenuState.Collapsed -> DropdownActionMenuState.Expanded
        }
    }
}

@Composable
fun CenterAlignedTopAppBar(
    title: String,
    navigationAction: (@Composable () -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        navigationIconContentColor = Theme.colorScheme.primary,
        titleContentColor = Theme.colorScheme.onSurfaceVariant,
        actionIconContentColor = Theme.colorScheme.primary,
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    // Precompute uppercase title outside composable to avoid re-computation during recompositions
    val uppercaseTitle = remember(title) { title.uppercase() }

    CenterAlignedTopAppBarM3(
        title = { AppBarTitle(text = uppercaseTitle) },
        navigationIcon = { navigationAction?.invoke() },
        actions = { actions?.invoke() },
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun AppBarTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        style = Theme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun MenuItem(
    enabled: Boolean = true,
    icon: ImageVector,
    contentDescription: String? = null,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        colors = colors
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun MenuItem(
    text: String,
    enabled: Boolean = true,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    style: TextStyle = LocalTextStyle.current,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        text = {
            Text(
                text = text,
                style = style,
                minLines = 1,
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
            )
        },
    )
}

@Composable
fun DropdownActionMenu(
    modifier: Modifier = Modifier,
    action: @Composable DropdownActionMenuScope.() -> Unit,
    enabled: Boolean = true,
    shape: Shape = MenuDefaults.shape,
    containerColor: Color = MenuDefaults.containerColor,
    tonalElevation: Dp = MenuDefaults.TonalElevation,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    content: @Composable DropdownActionMenuScope.() -> Unit
) {
    val scope = remember { DropdownActionMenuScopeImpl() }

    scope.action()

    DropdownMenu(
        modifier = modifier,
        expanded = scope.isExpanded() && enabled,
        onDismissRequest = { scope.toggleTo(false) },
        shape = shape,
        containerColor = containerColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border
    ) {
        scope.content()
    }
}

@Composable
fun DropdownActionMenu(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    enabled: Boolean = true,
    shape: Shape = MenuDefaults.shape,
    containerColor: Color = MenuDefaults.containerColor,
    tonalElevation: Dp = MenuDefaults.TonalElevation,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    content: @Composable DropdownActionMenuScope.() -> Unit
) {
    DropdownActionMenu(
        modifier = modifier,
        enabled = enabled,
        action = {
            MenuItem(
                icon = icon,
                onClick = {
                    toggleTo(true)
                }
            )
        },
        shape = shape,
        containerColor = containerColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border
    ) {
        this.content()
    }
}

@Composable
fun DropdownActionMenu(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = MenuDefaults.shape,
    containerColor: Color = MenuDefaults.containerColor,
    tonalElevation: Dp = MenuDefaults.TonalElevation,
    shadowElevation: Dp = MenuDefaults.ShadowElevation,
    border: BorderStroke? = null,
    content: @Composable DropdownActionMenuScope.() -> Unit
) {
    DropdownActionMenu(
        modifier = modifier,
        enabled = enabled,
        action = {
            MenuItem(
                text = text,
                onClick = {
                    toggleTo(true)
                }
            )
        },
        shape = shape,
        containerColor = containerColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border
    ) {
        this.content()
    }
}

@Composable
fun DropdownActionMenuScope.DropdownMenuItem(
    enabled: Boolean = true,
    title: String,
    subtitle: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    colors: MenuItemColors = MenuDefaults.itemColors(),
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = { ItemContent(title = title, subtitle = subtitle) },
        leadingIcon = if (leadingIcon != null) {
            {
                DropdownItemIcon(icon = leadingIcon)
            }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            {
                DropdownItemIcon(icon = trailingIcon)
            }
        } else null,
        onClick = {
            toggleTo(false)
            onClick()
        },
        enabled = enabled,
        colors = colors,
    )
}

@Composable
private fun ItemTitle(text: String) {
    Text(text = text, style = Theme.typography.labelLarge)
}

@Composable
private fun ItemSubtitle(text: String) {
    Text(text = text, style = Theme.typography.labelSmall)
}

@Composable
private fun ItemContent(
    title: String,
    subtitle: String? = null
) {
    if (subtitle == null) {
        ItemTitle(text = title)
    } else {
        Column {
            ItemTitle(text = title)
            ItemSubtitle(text = subtitle)
        }
    }
}

@Composable
private fun DropdownItemIcon(
    icon: ImageVector,
) {
    Icon(
        modifier = Modifier.size(18.dp),
        imageVector = icon,
        contentDescription = null,
    )
}

@PreviewComponent
@Composable
private fun CenteredAppBar_ActionMenuItem_Preview() {
    ApplicationTheme {
        CenterAlignedTopAppBar(
            title = "Deneb Chorny",
            navigationAction = { BackMenuItem { } },
            actions = { MenuItem(icon = Icons.Filled.Android) {} },
        )
    }
}

@PreviewPhone
@Composable
private fun CenteredAppBar_DropdownMenuItem_Preview() {
    ApplicationTheme {
        ScreenLayout(
            topBar = {
                CenterAlignedTopAppBar(
                    title = "Deneb Chorny",
                    navigationAction = { MenuItem(icon = Icons.Filled.ArrowBack) {} },
                    actions = {
                        DropdownActionMenu(icon = Icons.Filled.Image) {
                            DropdownMenuItem(title = "Home") {}
                            DropdownMenuItem(
                                leadingIcon = Icons.Filled.Android,
                                title = "Obelisco"
                            ) {}
                        }
                    },
                )
            }
        ) {

        }
    }
}
