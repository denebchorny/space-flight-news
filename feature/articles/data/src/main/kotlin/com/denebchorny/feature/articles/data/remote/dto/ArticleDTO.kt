package com.denebchorny.feature.articles.data.remote.dto

/**
 * Domain model for an Article.
 *
 * @property id           Unique integer ID (read‐only by API).
 * @property title        Title of the article. Max length 250.
 * @property authors      Non‐empty list of [AuthorDTO].
 * @property url          Original article URI. Max length 200.
 * @property imageUrl     Article’s image URI. Max length 500.
 * @property newsSite     Name of the news site (read‐only by API).
 * @property summary      Summary text.
 * @property publishedAt  Publication timestamp (ISO‐8601 string).
 * @property updatedAt    Last update timestamp (ISO‐8601 string, read‐only by API).
 * @property featured     Whether the API has flagged this article as featured.
 * @property launches     List of related launches. At least one [LaunchDTO].
 * @property events       List of related events. At least one [EventDTO].
 */
data class ArticleDTO(
    val id: Int,
    val title: String,
    val authors: List<AuthorDTO>,
    val url: String,
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<LaunchDTO>,
    val events: List<EventDTO>
)