package com.denebchorny.feature.articles.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String,
    val featured: Boolean,
    // These will be stored as JSON strings; TypeConverters will handle conversion
    val authorsJson: String,
    val launchesJson: String,
    val eventsJson: String
)
