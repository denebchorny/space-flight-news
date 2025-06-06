package com.denebchorny.feature.articles.data.di

import com.denebchorny.feature.articles.data.remote.api.ArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiProvider {

    @Singleton
    @Provides
    fun providesEmergencyAlertApi(retrofit: Retrofit): ArticlesApi {
        return retrofit.create(ArticlesApi::class.java)
    }

}