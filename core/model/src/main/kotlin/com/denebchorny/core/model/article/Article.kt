package com.denebchorny.core.model.article

import com.denebchorny.core.model.author.Author
import com.denebchorny.core.model.event.Event
import com.denebchorny.core.model.launch.Launch

/**
 * Domain model for an Article.
 *
 * @property id           Unique integer ID (read‐only by API).
 * @property title        Title of the article. Max length 250.
 * @property authors      Non‐empty list of [Author].
 * @property url          Original article URI. Max length 200.
 * @property imageUrl     Article’s image URI. Max length 500.
 * @property newsSite     Name of the news site (read‐only by API).
 * @property summary      Summary text.
 * @property publishedAt  Publication timestamp (ISO‐8601 string).
 * @property updatedAt    Last update timestamp (ISO‐8601 string, read‐only by API).
 * @property featured     Whether the API has flagged this article as featured.
 * @property launches     List of related launches. At least one [Launch].
 * @property events       List of related events. At least one [Event].
 */
data class Article(
    val id:  Long,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    val launches: List<Launch>,
    val events: List<Event>
)