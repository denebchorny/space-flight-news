package com.denebchorny.feature.articles.presentation.mappers

import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailParams
import com.denebchorny.feature.articles.presentation.navigation.ArticleDetailRoute

fun ArticleDetailRoute.toParams(): ArticleDetailParams = ArticleDetailParams(
    title = title,
    publishedAt = publishedAt,
    authors = authors,
    imageUrl = imageUrl,
    summary = summary
)