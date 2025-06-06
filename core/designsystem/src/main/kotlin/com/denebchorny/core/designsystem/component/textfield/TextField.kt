package com.denebchorny.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.denebchorny.core.designsystem.component.spacer.Spacer
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme

/**
 * Custom TextField Composable that wraps around Jetpack Compose's OutlinedTextField
 * to provide additional customization and styling.
 *
 * @param modifier Modifier to be applied to the TextField.
 * @param enabled Determines if the TextField is enabled for user interaction.
 * @param readOnly If true, the TextField is read-only.
 * @param label Optional label to be displayed above the TextField.
 * @param placeholder Optional placeholder text displayed inside the TextField.
 * @param value The current text to be displayed in the TextField.
 * @param onValueChange Callback invoked when the text changes.
 * @param singleLine If true, the TextField will be single-lined.
 * @param maxLines Maximum number of lines for the TextField.
 * @param minLines Minimum number of lines for the TextField.
 * @param leadingIcon Optional leading icon displayed inside the TextField.
 * @param trailingIcon Optional trailing icon displayed inside the TextField.
 * @param onTrailingIconClick Optional callback for trailing icon click events.
 * @param supportingText Optional supporting text displayed below the TextField.
 * @param isError If true, the TextField is displayed in an error state.
 * @param visualTransformation Visual transformation applied to the text (e.g., password masking).
 * @param keyboardOptions Configuration options for the soft keyboard.
 * @param keyboardActions Callbacks for keyboard IME actions.
 * @param interactionSource Represents the stream of interactions for the TextField.
 * @param contentPadding Padding around the TextField.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    supportingText: String? = null,
    isError: Boolean = false,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(15.dp),
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Theme.colorScheme.onPrimary,
        unfocusedTextColor = Theme.colorScheme.onPrimary,
        errorTextColor = Theme.colorScheme.onPrimary,
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = TextFieldDefaults.contentPaddingWithoutLabel()
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        label?.let { text ->
            Label(text, isError)
            Spacer(6.dp)
        }
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                colors = colors,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = isError,
                prefix = prefix,
                suffix = suffix,
                placeholder = placeholder?.let { text -> { Placeholder(text) } },
                leadingIcon = leadingIcon?.let { icon -> { FieldIcon(icon = icon) } },
                trailingIcon = trailingIcon?.let { icon ->
                    {
                        FieldIcon(onClick = { onTrailingIconClick?.invoke() }, icon = icon)
                    }
                },
                supportingText = supportingText?.let { text ->
                    {
                        Spacer(2.dp)
                        Row {
                            Text(
                                text = supportingText,
                                style = Theme.typography.labelSmall,
                                modifier = Modifier.weight(1F, fill = false)
                            )
                        }
                    }
                },
                contentPadding = contentPadding,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = shape,
                    )
                }
            )
        }
    }
}

@Composable
private fun FieldIcon(
    onClick: () -> Unit = { },
    icon: ImageVector,
) {
    IconButton(
        modifier = Modifier.size(24.dp),
        onClick = onClick::invoke
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Theme.colorScheme.primary
        )
    }
}

@Composable
private fun Placeholder(text: String) {
    Text(
        text = text,
        color = Theme.colorScheme.surfaceContainerLow.copy(alpha = 0.5F),
        style = Theme.typography.bodyMedium
    )
}

@Composable
private fun Label(text: String, isError: Boolean) {
    Row {
        Text(
            text = text,
            style = Theme.typography.labelMedium,
            color = if (isError) {
                Theme.colorScheme.error
            } else Theme.colorScheme.surfaceContainerHigh,
            modifier = Modifier.weight(1F, fill = false)
        )
    }
}

@Composable
private fun SupportingText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color
) {
    Text(
        text = text,
        style = Theme.typography.labelSmall,
        modifier = modifier,
        color = textColor
    )
}

@PreviewComponent
@Composable
private fun MinimalTextFieldPreview() {
    ApplicationTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {}
            )
        }
    }
}

@PreviewComponent
@Composable
private fun LabeledTextFieldPreview() {
    ApplicationTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Just a label",
                value = "",
                onValueChange = {}
            )
        }
    }
}

@PreviewComponent
@Composable
private fun PlaceholderTextFieldPreview() {
    ApplicationTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = "denebchorny@gmail.com",
                value = "",
                onValueChange = {}
            )
        }
    }
}

@PreviewComponent
@Composable
private fun SupportTextTextFieldPreview() {
    ApplicationTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                supportingText = "Support text",
                onValueChange = {}
            )
        }
    }
}