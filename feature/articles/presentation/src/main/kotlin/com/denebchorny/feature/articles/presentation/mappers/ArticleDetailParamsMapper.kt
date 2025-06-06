package com.denebchorny.feature.articles.presentation.mappers

import com.denebchorny.feature.articles.presentation.module.articleDetail.interaction.ArticleDetailParams
import com.denebchorny.feature.articles.presentation.vo.NewsArticle

fun ArticleDetailParams.toNewsArticle(): NewsArticle = NewsArticle(
    title = title,
    date = publishedAt,
    source = authors,
    imageUrl = imageUrl,
    details = summary,
)