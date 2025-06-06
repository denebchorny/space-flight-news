package com.denebchorny.spaceflightnews.di

import com.denebchorny.core.model.config.ApplicationConfig
import com.denebchorny.spaceflightnews.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ConfigurationModule {

    @Provides
    @Singleton
    fun providesApplicationConfig(): ApplicationConfig {
        return ApplicationConfig(
            isDebug = BuildConfig.DEBUG,
        )
    }
}