package com.denebchorny.feature.articles.presentation.module.articleList.interaction

sealed class ArticleListUIAction {
    data class DelegateResult(val value: ArticleListUIResult) : ArticleListUIAction()
}