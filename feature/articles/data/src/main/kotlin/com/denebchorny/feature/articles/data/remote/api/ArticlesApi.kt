package com.denebchorny.feature.articles.data.remote.api

import com.denebchorny.core.network.android.adapter.NetworkResponse
import com.denebchorny.core.network.android.interceptor.Timeout
import com.denebchorny.feature.articles.data.remote.dto.ArticleDTO
import com.denebchorny.feature.articles.data.remote.dto.PaginatedArticlesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticlesApi {

    @Timeout(read = 20_000, write = 20_000, conn = 20_000)
    @GET("v4/articles")
    suspend fun fetchArticles(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): NetworkResponse<PaginatedArticlesDTO, Unit>

    @GET("v4/articles/{id}")
    suspend fun getArticle(
        @Path("id") id: Int
    ): NetworkResponse<ArticleDTO, Unit>
}