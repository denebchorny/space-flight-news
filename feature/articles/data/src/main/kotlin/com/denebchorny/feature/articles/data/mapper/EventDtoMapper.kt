package com.denebchorny.feature.articles.data.mapper

import com.denebchorny.core.model.event.Event
import com.denebchorny.feature.articles.data.remote.dto.EventDTO

fun EventDTO.toEvent(): Event {
    return Event(
        id = id,
        provider = provider
    )
}

fun List<EventDTO>.toEventList(): List<Event> = this.map { it.toEvent() }
