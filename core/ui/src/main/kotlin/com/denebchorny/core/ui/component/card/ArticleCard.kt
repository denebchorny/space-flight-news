package com.denebchorny.core.ui.component.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.denebchorny.core.designsystem.component.spacer.Spacer
import com.denebchorny.core.designsystem.component.spacer.VerticalSpacer
import com.denebchorny.core.designsystem.preview.PreviewComponent
import com.denebchorny.core.designsystem.theme.ApplicationTheme
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import com.denebchorny.core.ui.parameterProvider.SampleArticleItemDataProvider

@Stable
data class ArticleItemData(
    val id: Long,
    val title: String,
    val summary: String,
    val imageUrl: String
)

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: ArticleItemData,
    onArticleClick: (articleId: Long) -> Unit,
) {
    val spacing = LocalSpacing.current
    val isLocalInspection = LocalInspectionMode.current

    val painter = if (isLocalInspection) {
        ColorPainter(color = Theme.colorScheme.surfaceContainerHigh)
    } else {
        rememberAsyncImagePainter(model = article.imageUrl)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onArticleClick(article.id) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(spacing.small)),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(spacing.medium)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                VerticalSpacer(spacing.extraSmall)
                Text(
                    text = article.summary,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@PreviewComponent
@Composable
private fun ArticleCard_Preview(
    @PreviewParameter(SampleArticleItemDataProvider::class) article: ArticleItemData
) {
    ApplicationTheme {
        ArticleCard(article = article, onArticleClick = { id ->
            println("Clicked article with ID: $id")
        })
    }
}
