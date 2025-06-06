package com.denebchorny.feature.articles.domain.error

sealed class ArticleError {
    data object JustAnError : ArticleError()
}