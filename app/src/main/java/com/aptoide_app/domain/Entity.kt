package com.aptoide_app.domain

data class FullDetailApp(
    val added: String,
    val apkTags: List<String>,
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
