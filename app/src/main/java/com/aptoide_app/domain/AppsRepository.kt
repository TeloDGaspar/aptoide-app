package com.aptoide_app.domain

import kotlinx.coroutines.flow.Flow

interface AppsRepository {
    fun allApps(): Flow<List<AppItem>>

    fun getAppItem(): Result<Flow<List<AppItem>>>

    fun getSimplifiedApps(): Result<Flow<List<SimplifiedApp>>>
    fun getFullDetailsApps(): Result<Flow<List<FullDetailApp>>>
}

data class FullDetailApp(
    val added: String,
    val apkTags: List<Any>,
    val downloads: Int,
    val graphic: String,
    val icon: String,
    val id: Int,
    val md5sum: String,
    val modified: String,
    val name: String,
    val packageX: String,
    val pdownloads: Int,
    val rating: Double,
    val size: Long,
    val storeId: Int,
    val storeName: String,
    val updated: String,
    val uptype: String,
    val vercode: Int,
    val vername: String
)

data class AppItem(
    val name: List<String>,
    val graphic: List<String>
)

data class SimplifiedApp(
    val name: String,
    val icon: String
)