package com.aptoide_app.domain

import android.util.Log
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.data.remote.dto.AptitudeRetrieve
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val aptoideApi: AptoideApi
) : AppsRepository {

    override fun allApps(): Flow<List<AppItem>> {
        TODO()
    }

    override fun getAppItem(): Result<Flow<List<AppItem>>> {
        return try {
            val apps = TODO()
            Result.success(apps)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun getApps() : AptitudeRetrieve{
        try {
            Log.i("Devlog", "Fetching apps from server")
            return aptoideApi.getListings()
        } catch (e: Exception) {
            Log.e("Devlog", "Error fetching apps from server: ${e.message}", e)
            throw e // Rethrow the exception to propagate it to the caller
        }
    }


    private fun getSimplefiedApps(): Flow<List<SimplifiedApp>> = flow {
        try {
            val response = getApps()
            val listings = response.responses.listApps.datasets.all.data.list.map { app ->
                SimplifiedApp(app.name, app.icon)
            }
            val sortedListings = listings.sortedBy { it.name }
            emit(sortedListings)
        } catch (e: Exception) {
            emit(emptyList())
            Log.e("Devlog1", "Error fetching simplified apps: ${e.message}", e)
        }
    }

    private fun getFullDetailApps(): Flow<List<FullDetailApp>> = flow {
        val response = getApps()
        val listings = response.responses.listApps.datasets.all.data.list.map { app ->
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
        }
        val sortedListings = listings.sortedBy { it.name }
        emit(sortedListings)
    }

    override fun getSimplifiedApps(): Result<Flow<List<SimplifiedApp>>> {
        return try {
            Log.i("Devlog","Throwa")
            Result.success(getSimplefiedApps())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>> {
        return try {
            Result.success(getFullDetailApps())
        } catch (e: Exception) {

            Result.failure(e)
        }
    }


}
