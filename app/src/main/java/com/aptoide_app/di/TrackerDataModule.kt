package com.aptoide_app.di

import android.content.Context
import androidx.room.Room
import com.aptoide_app.data.local.AppDatabase
import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.domain.app.AppsRepository
import com.aptoide_app.domain.app.AppsRepositoryImpl
import com.aptoide_app.domain.connectivity.ConnectivityObserver
import com.aptoide_app.domain.connectivity.NetworkConnectivityObserver
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
import javax.inject.Singleton

/**
 * TrackerDataModule is a Dagger module that provides dependencies for the application.
 * It provides instances of OkHttpClient, AptoideApi, Context, AppDatabase, FullDetailAppDao, ConnectivityObserver, and AppsRepository.
 */
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
    fun provideNetwork(@ApplicationContext context: Context): ConnectivityObserver = NetworkConnectivityObserver(context)

    @Provides
    @Singleton
    fun provideApps(api: AptoideApi, fullDetailAppDao: FullDetailAppDao, network: ConnectivityObserver): AppsRepository = AppsRepositoryImpl(api,fullDetailAppDao,network)
}
