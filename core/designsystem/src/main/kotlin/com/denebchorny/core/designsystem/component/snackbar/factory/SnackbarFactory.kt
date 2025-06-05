package com.denebchorny.core.designsystem.component.snackbar.factory

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.vector.ImageVector
import com.denebchorny.core.common.android.resources.uiText.UiText
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarAction
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarEnterMode
import com.denebchorny.core.designsystem.component.snackbar.vo.SnackbarMessage
import com.denebchorny.core.designsystem.component.snackbar.vo.snackbarColor
import com.denebchorny.core.designsystem.theme.color.tokenization.ColorSchemeKeyTokens.ErrorContainer
import com.denebchorny.core.designsystem.theme.color.tokenization.ColorSchemeKeyTokens.OnErrorContainer
import com.denebchorny.core.designsystem.theme.color.tokenization.ColorSchemeKeyTokens.OnPrimaryContainer
import com.denebchorny.core.designsystem.theme.color.tokenization.ColorSchemeKeyTokens.PrimaryContainer
import com.hipcam.android.core.designsystem.presentation.component.snackbar.vo.snackbarText

/**
 * Factory class for creating different types of Snackbars within the application.
 *
 * This object provides a set of functions for creating pre-configured [SnackbarMessage] objects
 * for various snackbar types (success, error, warning, info).  It simplifies the creation of
 * Snackbars by handling color assignments, default durations, and message text formatting.
 */
object SnackbarFactory {

    /**
     * Represents different types of snackbars.
     */
    sealed interface SnackbarType {
        data object Success : SnackbarType
        data object Error : SnackbarType
    }

    /**
     * Retrieves the appropriate colors for the snackbar based on its type.
     *
     * @param type The type of snackbar.
     * @return A pair of colors (contentColor, containerColor).
     */
    private fun snackbarColors(type: SnackbarType) = when (type) {
        SnackbarType.Success -> snackbarColor(OnPrimaryContainer) to snackbarColor(PrimaryContainer)
        SnackbarType.Error -> snackbarColor(OnErrorContainer) to snackbarColor(ErrorContainer)
    }

    /**
     * Determines the default duration for the snackbar based on the presence of actions.
     *
     * @param actions Optional actions associated with the snackbar.
     * @return `SnackbarDuration.Short` if no actions are provided, otherwise `SnackbarDuration.Indefinite`.
     */
    private fun defaultDuration(
        withDismissAction: Boolean,
        vararg actions: SnackbarAction?
    ): SnackbarDuration {
        return if (actions.any { it != null } || withDismissAction) {
            SnackbarDuration.Indefinite
        } else {
            SnackbarDuration.Short
        }
    }

    /**
     * Creates a snackbar message with the specified properties.
     *
     * @param type The type of snackbar (Success, Error, Warning, Info).
     * @param text The message text, which can be a `String`, `Int` (@StringRes), or `UiText`.
     * @param withDismissAction Whether the snackbar should include a dismiss action.
     * @param neutralAction A neutral action button.
     * @param positiveAction A positive action button.
     * @param negativeAction A negative action button.
     * @param duration The duration for which the snackbar should be displayed.
     * @param icon An optional icon for the snackbar.
     * @param mode The enter mode for the snackbar.
     * @return A `SnackbarMessage` object with the configured properties.
     * @throws IllegalArgumentException If the provided text type is invalid.
     */
    private fun createSnackbar(
        type: SnackbarType,
        text: Any,
        withDismissAction: Boolean = false,
        neutralAction: SnackbarAction? = null,
        positiveAction: SnackbarAction? = null,
        negativeAction: SnackbarAction? = null,
        duration: SnackbarDuration = defaultDuration(
            withDismissAction,
            neutralAction,
            positiveAction,
            negativeAction
        ),
        icon: ImageVector? = null,
        mode: SnackbarEnterMode = SnackbarEnterMode.Force
    ): SnackbarMessage {
        return SnackbarMessage(
            text = when (text) {
                is String -> snackbarText(text)
                is Int -> snackbarText(text)  // @StringRes
                is UiText -> snackbarText(text)
                else -> throw IllegalArgumentException("Invalid text type")
            },
            withDismissAction = withDismissAction,
            neutralAction = neutralAction,
            positiveAction = positiveAction,
            negativeAction = negativeAction,
            duration = duration,
            contentColor = snackbarColors(type).first,
            containerColor = snackbarColors(type).second,
            icon = icon,
            mode = mode
        )
    }

    /**
     * Creates a snackbar of the specified type.
     *
     * @param type The type of snackbar.
     * @param text The message text (String, @StringRes Int, or UiText).
     * @param withDismissAction Whether the snackbar should include a dismiss action.
     * @param neutralAction A neutral action button.
     * @param positiveAction A positive action button.
     * @param negativeAction A negative action button.
     * @param duration The duration for which the snackbar should be displayed.
     * @param icon An optional icon for the snackbar.
     * @param mode The enter mode for the snackbar.
     * @return A `SnackbarMessage` object.
     */
    fun snackbar(
        type: SnackbarType,
        text: Any,
        withDismissAction: Boolean = false,
        neutralAction: SnackbarAction? = null,
        positiveAction: SnackbarAction? = null,
        negativeAction: SnackbarAction? = null,
        duration: SnackbarDuration = defaultDuration(
            withDismissAction,
            neutralAction,
            positiveAction,
            negativeAction
        ),
        icon: ImageVector? = null,
        mode: SnackbarEnterMode = SnackbarEnterMode.Force
    ): SnackbarMessage {
        return createSnackbar(
            type = type,
            text = text,
            withDismissAction = withDismissAction,
            neutralAction = neutralAction,
            positiveAction = positiveAction,
            negativeAction = negativeAction,
            duration = duration,
            icon = icon,
            mode = mode
        )
    }

    /**
     * Creates a success snackbar.
     *
     * @see snackbar
     */
    fun successSnackbar(
        text: Any,
        withDismissAction: Boolean = false,
        neutralAction: SnackbarAction? = null,
        positiveAction: SnackbarAction? = null,
        negativeAction: SnackbarAction? = null,
        duration: SnackbarDuration = defaultDuration(
            withDismissAction,
            neutralAction,
            positiveAction,
            negativeAction
        ),
        icon: ImageVector? = null,
        mode: SnackbarEnterMode = SnackbarEnterMode.Force
    ): SnackbarMessage = snackbar(
        type = SnackbarType.Success,
        text = text,
        withDismissAction = withDismissAction,
        neutralAction = neutralAction,
        positiveAction = positiveAction,
        negativeAction = negativeAction,
        duration = duration,
        icon = icon,
        mode = mode
    )

    /**
     * Creates an error snackbar.
     *
     * @see snackbar
     */
    fun errorSnackbar(
        text: Any,
        withDismissAction: Boolean = false,
        neutralAction: SnackbarAction? = null,
        positiveAction: SnackbarAction? = null,
        negativeAction: SnackbarAction? = null,
        duration: SnackbarDuration = defaultDuration(
            withDismissAction,
            neutralAction,
            positiveAction,
            negativeAction
        ),
        icon: ImageVector? = null,
        mode: SnackbarEnterMode = SnackbarEnterMode.Force
    ): SnackbarMessage = snackbar(
        type = SnackbarType.Error,
        text = text,
        withDismissAction = withDismissAction,
        neutralAction = neutralAction,
        positiveAction = positiveAction,
        negativeAction = negativeAction,
        duration = duration,
        icon = icon,
        mode = mode
    )
}