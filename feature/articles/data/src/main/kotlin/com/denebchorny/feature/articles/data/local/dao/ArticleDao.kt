package com.denebchorny.feature.articles.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.denebchorny.feature.articles.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC LIMIT :limit OFFSET :offset")
    suspend fun getArticles(limit: Int, offset: Int): List<ArticleEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Long): ArticleEntity?

    @Query("DELETE FROM articles")
    suspend fun clearAll()
}
