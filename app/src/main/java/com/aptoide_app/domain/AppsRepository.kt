package com.aptoide_app.domain

import com.aptoide_app.data.local.FullDetailAppEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppsRepository {
    fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>>

    fun getAppsFromDataBase() : Flow<List<FullDetailApp>>
}
