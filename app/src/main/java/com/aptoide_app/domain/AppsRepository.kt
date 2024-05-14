package com.aptoide_app.domain

import com.aptoide_app.data.local.FullDetailAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * AppsRepository is an interface that defines the operations related to fetching app details.
 * It provides a method to get full details of apps.
 * This interface is implemented by AppsRepositoryImpl.
 */
interface AppsRepository {
    /**
     * Fetches full details of apps.
     *
     * @return A Result object containing a Flow of a list of FullDetailApp objects.
     */
    fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>>
}
