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

fun TestApp(
    added: String = "",
    apkTags: List<String> = emptyList(),
    downloads: Int = 0,
    graphic: String = "",
    icon: String = "",
    id: Int = 0,
    md5sum: String = "",
    modified: String = "",
    name: String = "",
    packageX: String = "",
    pdownloads: Int = 0,
    rating: Double = 0.0,
    size: Long = 0,
    storeId: Int = 0,
    storeName: String = "",
    updated: String = "",
    uptype: String = "",
    vercode: Int = 0,
    vername: String = ""

) = FullDetailApp(
    added,
    apkTags,
    downloads,
    graphic,
    icon,
    id,
    md5sum,
    modified,
    name,
    packageX,
    pdownloads,
    rating,
    size,
    storeId,
    storeName,
    updated,
    uptype,
    vercode,
    vername
)


