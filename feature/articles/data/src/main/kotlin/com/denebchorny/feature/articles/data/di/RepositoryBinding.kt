package com.denebchorny.feature.articles.data.di

import com.denebchorny.feature.articles.data.repository.ArticlesRemoteDataSourceImpl
import com.denebchorny.feature.articles.data.repository.ArticlesRepositoryImpl
import com.denebchorny.feature.articles.data.repository.datasource.ArticlesRemoteDataSource
import com.denebchorny.feature.articles.domain.repository.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinding {

    @Binds
    abstract fun bindsEmergencyAlertRemoteDataSource(
        dataSource: ArticlesRemoteDataSourceImpl
    ): ArticlesRemoteDataSource

    @Binds
    abstract fun bindsEmergencyAlertRepository(
        repository: ArticlesRepositoryImpl
    ): ArticlesRepository

}