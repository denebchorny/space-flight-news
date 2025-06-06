package com.denebchorny.core.network.di

import com.denebchorny.core.model.config.ApplicationConfig
import com.denebchorny.core.network.android.adapter.NetworkResponseAdapterFactory
import com.denebchorny.core.network.android.interceptor.HeaderInterceptor
import com.denebchorny.core.network.android.interceptor.TimeoutInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun providesOkHttpClientBuilder(
        config: ApplicationConfig,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(TimeoutInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    if (config.isDebug) setLevel(BODY)
                },
            )
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        httpBuilder: OkHttpClient.Builder,
        networkJson: Json,
    ): Retrofit {
        val client: OkHttpClient = httpBuilder.build()
        return Retrofit.Builder()
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .baseUrl("https://api.spaceflightnewsapi.net/")
            .build()
    }
}

