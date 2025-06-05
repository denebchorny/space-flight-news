@file:OptIn(ExperimentalMaterial3Api::class)

package com.denebchorny.core.designsystem.component.dialogs

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.theme.Theme
import androidx.compose.material3.ModalBottomSheet as ModalBottomSheetM3

@Composable
fun ModalBottomSheetDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    state: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    background: Color = Color.Unspecified,
    dragHandleColor: Color = Theme.colorScheme.outline,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    val backgroundColor = background.takeOrElse { Theme.colorScheme.surface }

    ModalBottomSheetM3(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = state,
        sheetMaxWidth = sheetMaxWidth,
        shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        containerColor = backgroundColor,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                width = 102.dp,
                height = 2.dp,
                shape = RoundedCornerShape(4.dp),
                color = dragHandleColor,
            )
        },
        scrimColor = scrimColor,
        contentWindowInsets = contentWindowInsets,
        properties = properties,
    ) {
        content()
    }
}