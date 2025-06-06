package com.denebchorny.feature.articles.presentation.module.articleDetail.state

import androidx.compose.runtime.Stable
import com.denebchorny.feature.articles.presentation.vo.NewsArticle

@Stable
data class ArticleDetailScreenState(
    val article: NewsArticle = NewsArticle.empty
)