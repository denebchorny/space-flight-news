package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
@Serializable
data class ArticleDTO(
    val id: Long,
    val title: String,
    val authors: List<AuthorDTO>,
    val url: String,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("news_site")
    val newsSite: String?,
    val summary: String?,
    @SerialName("published_at")
    val publishedAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<LaunchDTO>,
    val events: List<EventDTO>
)