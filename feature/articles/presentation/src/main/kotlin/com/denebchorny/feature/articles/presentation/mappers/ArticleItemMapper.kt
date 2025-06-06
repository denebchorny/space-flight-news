package com.denebchorny.feature.articles.presentation.mappers

import com.denebchorny.core.model.article.Article
import com.denebchorny.core.ui.component.card.ArticleItemData

fun Article.toItemData(): ArticleItemData = ArticleItemData(
    id = id,
    title = title,
    summary = summary,
    imageUrl = imageUrl
)

fun List<Article>.toItemDataList(): List<ArticleItemData> = this.map { it.toItemData() }