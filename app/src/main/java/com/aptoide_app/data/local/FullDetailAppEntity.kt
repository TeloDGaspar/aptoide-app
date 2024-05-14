package com.aptoide_app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * Represents a detailed app entity in the local database.
 */
@Entity
data class FullDetailAppEntity(
    val added: String,
    @TypeConverters(StringListConverter::class) val apkTags: List<String>,
    val downloads: Int,
    val graphic: String,
    val icon: String,
    val id: Int,
    val md5sum: String,
    val modified: String,
    val name: String,
    @PrimaryKey val packageX: String,
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
