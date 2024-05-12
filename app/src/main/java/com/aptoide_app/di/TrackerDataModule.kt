package com.aptoide_app.di

import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.domain.AppsRepository
import com.aptoide_app.domain.AppsRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }).build()
    }

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient): AptoideApi {
        return Retrofit.Builder()
            .baseUrl(AptoideApi.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create(AptoideApi::class.java) // Provide the API interface class here
    }

    @Provides
    @Singleton
    fun provideApps(api: AptoideApi): AppsRepository = AppsRepositoryImpl(api)

}
