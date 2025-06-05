package com.denebchorny.core.designsystem.component.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties
import com.denebchorny.core.designsystem.component.button.TextButton
import com.denebchorny.core.designsystem.theme.Theme

/**
 * Displays a customizable AlertDialog using Material 3 components.
 *
 * @param onDismissRequest Called when the user attempts to dismiss the dialog (e.g., tapping outside).
 * @param confirmText The text label for the "confirm" button.
 * @param confirmOnClick The action invoked when the confirm button is clicked.
 * @param modifier Modifier for layout adjustments of the dialog.
 * @param title Title text of the dialog (if null, no title is displayed).
 * @param text Main body text of the dialog (if null, no body text is displayed).
 * @param icon Optional icon to show at the top of the dialog.
 * @param dismissText Text for the optional dismiss button (if null, no dismiss button is shown).
 * @param dismissOnClick The action invoked when the dismiss button is clicked.
 * @param properties Additional dialog properties (e.g., controlling dim behind the dialog).
 */
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmText: String,
    confirmOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String? = null,
    icon: @Composable (() -> Unit)? = null,
    dismissText: String? = null,
    dismissOnClick: () -> Unit = {},
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        icon = icon,
        title = createTitleContent(title),
        text = createTextContent(text),
        confirmButton = { TextButton(onClick = confirmOnClick, text = confirmText) },
        dismissButton = createDismissButtonContent(dismissText, dismissOnClick),
        properties = properties
    )
}

/**
 * Creates a composable for the dialog title if a non-null string is provided.
 *
 * @param title The text for the dialog's title, or null if none is needed.
 * @return A lambda composable containing the styled title text, or null.
 */
@Composable
private fun createTitleContent(title: String?): @Composable (() -> Unit)? {
    return title?.let { safeTitle ->
        {
            Text(
                text = safeTitle,
                fontWeight = FontWeight.Medium,
                style = Theme.typography.bodyMedium,
                color = Theme.colorScheme.onSurface,
            )
        }
    }
}

/**
 * Creates a composable for the dialog text (body) if a non-null string is provided.
 *
 * @param text The main body text for the dialog, or null if none is needed.
 * @return A lambda composable containing the styled body text, or null.
 */
@Composable
private fun createTextContent(text: String?): @Composable (() -> Unit)? {
    return text?.let { safeText ->
        {
            Text(
                text = safeText,
                style = Theme.typography.bodySmall,
                color = Theme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/**
 * Creates a composable for the dialog's optional dismiss button if [dismissText] is provided.
 *
 * @param dismissText The label for the dismiss button, or null if no dismiss button is required.
 * @param dismissOnClick The action invoked when the dismiss button is clicked.
 * @return A lambda composable wrapping a [TextButton], or null if [dismissText] is null.
 */
@Composable
private fun createDismissButtonContent(
    dismissText: String?,
    dismissOnClick: () -> Unit
): @Composable (() -> Unit)? {
    return dismissText?.let { safeDismissText ->
        {
            TextButton(
                onClick = dismissOnClick,
                text = safeDismissText
            )
        }
    }
}
