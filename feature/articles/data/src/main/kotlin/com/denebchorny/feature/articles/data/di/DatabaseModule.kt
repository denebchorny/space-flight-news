package com.denebchorny.feature.articles.data.di

import android.content.Context
import androidx.room.Room
import com.denebchorny.feature.articles.data.local.converter.ArticleTypeConverters
import com.denebchorny.feature.articles.data.local.dao.ArticleDao
import com.denebchorny.feature.articles.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleTypeConverters(): ArticleTypeConverters {
        return ArticleTypeConverters() // For @ProvidedTypeConverter
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        articleTypeConverters: ArticleTypeConverters
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "space_flight_news_database"
        )
        .addTypeConverter(articleTypeConverters) // Add instance for @ProvidedTypeConverter
        .fallbackToDestructiveMigration() // Not ideal for production, but fine for this stage
        .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }
}
