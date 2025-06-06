package com.denebchorny.feature.articles.presentation.module.articleDetail

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.denebchorny.core.designsystem.component.appBar.CenterAlignedTopAppBar
import com.denebchorny.core.designsystem.component.appBar.menuItems.BackMenuItem
import com.denebchorny.core.designsystem.component.layout.ScreenLayout
import com.denebchorny.core.designsystem.theme.Theme
import com.denebchorny.core.designsystem.theme.dimension.LocalSpacing
import com.denebchorny.core.ui.component.observer.Lifecycle
import com.denebchorny.feature.articles.presentation.R
import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailParams
import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailUIEvent
import com.denebchorny.feature.articles.presentation.module.articleDetail.state.ArticleDetailScreenState
import com.denebchorny.feature.articles.presentation.vo.NewsArticle

@Composable
fun ArticleDetailScreen(
    params: ArticleDetailParams,
    viewmodel: ArticleDetailViewmodel = hiltViewModel(),
) {
    val state: ArticleDetailScreenState by viewmodel.uiState.collectAsStateWithLifecycle()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Lifecycle(
        subscriber = viewmodel,
        onCreate = { viewmodel.onEvent(ArticleDetailUIEvent.OnInitialize(params)) }
    )

    val callbacks = remember {
        ArticleDetailCallbacks(
            onSocialClicked = { },
            onBack = { backDispatcher?.onBackPressed() }
        )
    }

    ArticleDetailLayout(state = state, callbacks = callbacks)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArticleDetailLayout(
    state: ArticleDetailScreenState,
    callbacks: ArticleDetailCallbacks
) {
    val spacing = LocalSpacing.current
    val contentScroll = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    ScreenLayout(
        topBar = {
            CenterAlignedTopAppBar(
                title = stringResource(R.string.articles_appbar_detail_title),
                navigationAction = { BackMenuItem { callbacks.onBack() } },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .fillMaxSize()
                .verticalScroll(contentScroll)
                .padding(bottom = spacing.medium)
        ) {
            ArticleHeader(state.article)
            ArticleImage(state.article)
            ArticleContent(state.article)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ArticleHeader(article: NewsArticle) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = article.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = article.source,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ArticleImage(article: NewsArticle) {
    if (article.imageUrl.isNotEmpty()) {
        val isLocalInspection = LocalInspectionMode.current

        val painter = if (isLocalInspection) {
            ColorPainter(color = Theme.colorScheme.surfaceContainerHigh)
        } else {
            rememberAsyncImagePainter(model = article.imageUrl)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painter,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun ArticleContent(article: NewsArticle) {
    Text(
        text = article.details,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun Screen_Preview() {
    val sampleArticle = NewsArticle(
        title = "NASA Astronaut Jeanette Epps Retires",
        date = "JUN 05, 2025",
        source = "Johnson Space Center",
        imageUrl = "https://www.nasa.gov/sites/default/files/styles/side_image/public/thumbnails/image/iss071e011894.jpg",
        details = "NASA astronaut Jeanette Epps retired May 30, after nearly 16 years of service with the agency. Epps most recently served as a mission specialist during NASA’s SpaceX Crew-8 mission, spending 235 days in space, including 232 days aboard the International Space Station, working on hundreds of scientific experiments during Expedition 71/72. \"I have had the distinct pleasure of following Jeanette’s journey here at NASA from the very beginning,\" said Steve Koerner, acting director of NASA’s Johnson Space Center in Houston. \"Jeanette’s tenacity and dedication to mission excellence is admirable. Her contributions to the advancement of human space exploration will continue to benefit humanity and inspire the next generation of explorers for several years to come.\" Epps was selected in 2009 as a member of NASA’s 20th astronaut class. In addition to her spaceflight, she served as a lead capsule communicator, or capcom, in NASA’s Mission Control Center and as a crew support astronaut for two space station expeditions. \"Ever since Jeanette joined the astronaut corps, she has met every challenge with resilience and determination,\" said Joe Acaba, NASA’s chief astronaut. \"We will miss her greatly, but I know she’s going to continue to do great things.\" Epps also participated in NEEMO (NASA Extreme Environment Mission Operation) off the coast of Florida, conducted geologic studies in Hawaii, and served as a representative to the Generic Joint Operations Panel, which addressed crew efficiency aboard the space station. The Syracuse, New York, native holds a bachelor’s degree in physics from Le Moyne College in Syracuse. She also earned master’s and doctorate degrees in aerospace engineering from the University of Maryland in College Park. During her graduate studies, she became a NASA Fellow, authoring several journal and conference articles about her research. Epps also received a provisional patent and a U.S. patent prior to her role at NASA. Learn more about International Space Station research and operations "
    )

    MaterialTheme {
        ArticleDetailLayout(
            state = ArticleDetailScreenState(article = sampleArticle),
            callbacks = ArticleDetailCallbacks(
                onSocialClicked = { },
                onBack = { }
            )
        )
    }
}