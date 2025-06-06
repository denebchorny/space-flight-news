package com.denebchorny.feature.articles.presentation.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.denebchorny.core.designsystem.component.dialogs.ModalBottomSheetDialog
import com.denebchorny.core.designsystem.component.spacer.VerticalSpacer
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import com.denebchorny.feature.articles.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItsMeDialog(onDismissRequest: () -> Unit) {
    val spacing = LocalSpacing.current

    ModalBottomSheetDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.medium, vertical = spacing.default)
        ) {
            Text(
                text = stringResource(R.string.articles_its_me_dialog_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            VerticalSpacer(spacing.small)
            Text(
                text = stringResource(R.string.articles_its_me_dialog_description),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            VerticalSpacer(spacing.medium)
            Text(
                text = stringResource(R.string.articles_its_me_dialog_extra),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


