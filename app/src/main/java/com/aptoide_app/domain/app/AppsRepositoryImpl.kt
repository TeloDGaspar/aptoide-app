package com.aptoide_app.domain.app

import com.aptoide_app.data.local.FullDetailAppDao
import com.aptoide_app.data.mapper.toEntity
import com.aptoide_app.data.mapper.toObject
import com.aptoide_app.data.remote.AptoideApi
import com.aptoide_app.data.remote.dto.AptitudeRetrieve
import com.aptoide_app.domain.connectivity.ConnectivityObserver
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of the AppsRepository interface.
 *
 * @property aptoideApi The API for fetching app data.
 * @property fullDetailApp The DAO for accessing app data in the local database.
 * @property connectivityObserver Observes the network connectivity status.
 * @property dispatcher The CoroutineDispatcher on which this repository operates.
 */
class AppsRepositoryImpl @Inject constructor(
    private val aptoideApi: AptoideApi,
    private val fullDetailApp: FullDetailAppDao,
    private val connectivityObserver: ConnectivityObserver,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AppsRepository {

    private val _network = MutableStateFlow(
        ConnectivityObserver.Status.Initial
    )
    private val _fullDetailApp: MutableStateFlow<List<FullDetailApp>> =
        MutableStateFlow(emptyList())

    val fullDetailAppVal: StateFlow<List<FullDetailApp>> = _fullDetailApp.asStateFlow()

    init {
        observeChangesInNetworking()
    }

    /**
     * Fetches app data from the API, maps the data to FullDetailApp objects,
     * stores the data in the local database, and emits the data as a Flow.
     *
     * @return A Flow of a list of FullDetailApp objects.
     */
    fun getApps(): Flow<List<FullDetailApp>> = fullDetailAppVal

    private fun observeChangesInNetworking() {
        CoroutineScope(dispatcher).launch {
            connectivityObserver.observe.collect{
                if (
                    it == ConnectivityObserver.Status.Initial ||
                    it == ConnectivityObserver.Status.Available ||
                    it == ConnectivityObserver.Status.LinkPropertiesChanged ||
                    it == ConnectivityObserver.Status.BlockedStatusChanged
                ) {
                    val response = runCatching { aptoideApi.getListings().execute() }
                    val listings = convertToObject(response)

                    if (listings != null) {
                        if(listings.isNotEmpty()){
                            _fullDetailApp.value = listings
                            updateDb(listings)
                        }
                    }else{
                        val localApps = fullDetailApp.getFullDetailApp()
                        _fullDetailApp.value = localApps.toObject()
                    }
                }else if (it == ConnectivityObserver.Status.Lost) {

                    val localApps = fullDetailApp.getFullDetailApp()
                    _fullDetailApp.value = localApps.toObject()
                }
                _network.value = it
            }
        }
    }

    private fun convertToObject(response: Result<Response<AptitudeRetrieve>>): List<FullDetailApp>? {
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
        return listings
    }

    private fun updateDb(fetchedApps: List<FullDetailApp>?) {
        CoroutineScope(dispatcher).launch {
            fullDetailApp.clearCompanyListings()
            fetchedApps?.toEntity()?.forEach { fullDetailApp.insertAppDetails(it) }
        }
    }


    /**
     * Fetches app data by calling the getApps function and wraps the result in a Result object.
     *
     * @return A Result object containing a Flow of a list of FullDetailApp objects.
     */
    override fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>> = runCatching {
        getApps()
    }
}
