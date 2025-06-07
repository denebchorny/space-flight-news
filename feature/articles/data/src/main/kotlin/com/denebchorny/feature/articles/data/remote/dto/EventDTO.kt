package com.denebchorny.feature.articles.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Minimal Event reference as embedded under Article, Blog, or Report.
 *
 * @property id   Integer ID of the event.
 * @property provider  Which service provided this event (e.g. “Launch Library 2”).
 */
@Serializable
data class EventDTO(
    @SerialName("event_id")
    val id: Long,
    val provider: String?
)