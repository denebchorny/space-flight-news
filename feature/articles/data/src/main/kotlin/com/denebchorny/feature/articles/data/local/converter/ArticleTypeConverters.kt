package com.denebchorny.feature.articles.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.denebchorny.core.model.author.Author
import com.denebchorny.core.model.event.Event
import com.denebchorny.core.model.launch.Launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

// It's good practice to provide the Json instance via DI if this were a larger app,
// but for a TypeConverter, direct instantiation is common.
private val json = Json { ignoreUnknownKeys = true; serializersModule = SerializersModule { /* Add contextual serializers if needed for complex types not automatically handled */ } }

@ProvidedTypeConverter // If you plan to inject Json or other dependencies
class ArticleTypeConverters {

    @TypeConverter
    fun fromAuthorsList(authors: List<Author>?): String? {
        return authors?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toAuthorsList(authorsString: String?): List<Author>? {
        return authorsString?.let { json.decodeFromString<List<Author>>(it) }
    }

    @TypeConverter
    fun fromEventsList(events: List<Event>?): String? {
        return events?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toEventsList(eventsString: String?): List<Event>? {
        return eventsString?.let { json.decodeFromString<List<Event>>(it) }
    }

    @TypeConverter
    fun fromLaunchesList(launches: List<Launch>?): String? {
        return launches?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toLaunchesList(launchesString: String?): List<Launch>? {
        return launchesString?.let { json.decodeFromString<List<Launch>>(it) }
    }
}
