package com.aptoide_app.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aptoide_app.data.local.AppDatabase
import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.domain.AppsRepository
import com.aptoide_app.domain.AppsRepositoryImpl
import com.aptoide_app.domain.ConnectivityObserver
import com.aptoide_app.domain.NetworkConnectivityObserver
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
            .create(AptoideApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "your_database_name"
        ).build()
    }

    @Provides
    @Singleton
    fun provide(appDatabase: AppDatabase): FullDetailAppDao{
        return appDatabase.fullDetailAppDao()
    }

    @Provides
    @Singleton
    fun provideApps(api: AptoideApi, fullDetailAppDao: FullDetailAppDao): AppsRepository = AppsRepositoryImpl(api,fullDetailAppDao)

    @Provides
    @Singleton
    fun provideNetwork( @ApplicationContext context: Context):ConnectivityObserver = NetworkConnectivityObserver(context)
}
