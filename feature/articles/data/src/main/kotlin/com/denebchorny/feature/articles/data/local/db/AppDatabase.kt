package com.denebchorny.feature.articles.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.denebchorny.feature.articles.data.local.converter.ArticleTypeConverters
import com.denebchorny.feature.articles.data.local.dao.ArticleDao
import com.denebchorny.feature.articles.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
@TypeConverters(ArticleTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
