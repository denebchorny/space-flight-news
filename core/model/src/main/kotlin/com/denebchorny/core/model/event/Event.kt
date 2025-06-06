package com.denebchorny.core.model.event

/**
 * Minimal Event reference as embedded under Article, Blog, or Report.
 *
 * @property id   Integer ID of the event.
 * @property provider  Which service provided this event (e.g. “Launch Library 2”).
 */
data class Event(
    val id: Long,
    val provider: String
)