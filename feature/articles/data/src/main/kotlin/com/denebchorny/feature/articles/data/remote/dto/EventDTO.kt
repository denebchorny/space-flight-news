package com.denebchorny.feature.articles.data.remote.dto

/**
 * Minimal Event reference as embedded under Article, Blog, or Report.
 *
 * @property id   Integer ID of the event.
 * @property provider  Which service provided this event (e.g. “Launch Library 2”).
 */
data class EventDTO(
    val id: Int,
    val provider: String
)