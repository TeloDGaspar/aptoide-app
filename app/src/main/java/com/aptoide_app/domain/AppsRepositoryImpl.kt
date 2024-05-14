package com.aptoide_app.domain

import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.mapper.toEntity
import com.aptoide_app.data.mapper.toObject
import com.aptoide_app.data.remote.AptoideApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Implementation of the AppsRepository interface.
 *
 * @property aptoideApi The API for fetching app data.
 * @property fullDetailApp The DAO for accessing app data in the local database.
 * @property dispatcher The CoroutineDispatcher on which this repository operates.
 */
class AppsRepositoryImpl @Inject constructor(
    private val aptoideApi: AptoideApi,
    private val fullDetailApp: FullDetailAppDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppsRepository {

    /**
     * Fetches app data from the API, maps the data to FullDetailApp objects,
     * stores the data in the local database, and emits the data as a Flow.
     *
     * @return A Flow of a list of FullDetailApp objects.
     */
    fun getApps(): Flow<List<FullDetailApp>> = flow {
        val response = runCatching { aptoideApi.getListings().execute() }
        val listings = response.getOrNull()
            ?.body()?.responses?.listApps?.datasets?.all?.data?.list?.map { app ->
                FullDetailApp(
                    app.added,
                    app.apkTags,
                    app.downloads,
                    app.graphic ?: "",
                    app.icon,
                    app.id,
                    app.md5sum,
                    app.modified,
                    app.name,
                    app.packageX,
                    app.pdownloads,
                    app.rating,
                    app.size,
                    app.storeId,
                    app.storeName,
                    app.updated,
                    app.uptype,
                    app.vercode,
                    app.vername
                )
            }?.sortedBy { it.name }
        listings?.toEntity()?.forEach { fullDetailApp.insertAppDetails(it) }
        emit(listings ?: emptyList())
    }.flowOn(dispatcher)

    /**
     * Fetches app data from the local database, maps the data to FullDetailApp objects,
     * and emits the data as a Flow.
     *
     * @return A Flow of a list of FullDetailApp objects.
     */
    override fun getAppsFromDataBase(): Flow<List<FullDetailApp>> = flow {
        val fullDetailAppEntityList = fullDetailApp.getFullDetailApp()
        emit(fullDetailAppEntityList.toObject())
    }.flowOn(dispatcher)

    /**
     * Fetches app data by calling the getApps function and wraps the result in a Result object.
     *
     * @return A Result object containing a Flow of a list of FullDetailApp objects.
     */
    override fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>> = runCatching { getApps() }
}