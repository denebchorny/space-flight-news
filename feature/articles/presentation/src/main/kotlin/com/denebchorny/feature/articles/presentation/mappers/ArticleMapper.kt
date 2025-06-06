package com.denebchorny.feature.articles.presentation.mappers

import com.denebchorny.core.model.article.Article
import com.denebchorny.feature.articles.presentation.navigation.ArticleDetailRoute
import com.denebchorny.feature.articles.presentation.vo.NewsArticle
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun Article.toArticleDetailRoute(): ArticleDetailRoute = ArticleDetailRoute(
    title = title,
    publishedAt = formatIso8601ToReadable(publishedAt),
    authors = authors.joinToString { it.name },
    imageUrl = imageUrl,
    summary = summary,
)

private val readableFormatter: DateTimeFormatter = DateTimeFormatter
    .ofPattern("dd MMM yyyy, hh:mm a")
    .withZone(ZoneId.systemDefault())

/**
 * Converts an ISO-8601 timestamp (e.g. "2024-05-15T14:22:00Z") into a human-readable
 * format ("dd MMM yyyy, hh:mm a"), or returns an empty string if parsing fails.
 */
private fun formatIso8601ToReadable(isoString: String): String {
    return try {
        if (isoString.isBlank()) {
            ""
        } else {
            val instant = Instant.parse(isoString)
            val zonedDateTime: ZonedDateTime = instant.atZone(ZoneId.systemDefault())
            zonedDateTime.format(readableFormatter)
        }
    } catch (e: DateTimeParseException) {
        ""
    } catch (e: Exception) {
        ""
    }
}