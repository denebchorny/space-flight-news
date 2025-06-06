package com.denebchorny.feature.articles.presentation.module.articleList.interaction

import com.denebchorny.core.model.article.Article

sealed class ArticleListUIResult {
    data class OnArticleClicked(val article: Article) : ArticleListUIResult()
}