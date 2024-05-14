package com.aptoide_app.data.mapper

import com.aptoide_app.data.local.FullDetailAppEntity
import com.aptoide_app.domain.FullDetailApp

fun List<FullDetailApp>.toEntity(): List<FullDetailAppEntity> {
    return map { item ->
        FullDetailAppEntity(
            added = item.added,
            apkTags = item.apkTags,
            downloads = item.downloads,
            graphic = item.graphic,
            icon = item.icon,
            id = item.id,
            md5sum = item.md5sum,
            modified = item.modified,
            name = item.name,
            packageX = item.packageX,
            pdownloads = item.pdownloads,
            rating = item.rating,
            size = item.size,
            storeId = item.storeId,
            storeName = item.storeName,
            updated = item.updated,
            uptype = item.uptype,
            vercode = item.vercode,
            vername = item.vername
        )
    }
}

fun List<FullDetailAppEntity>.toObject(): List<FullDetailApp> {
    return map { item ->
        FullDetailApp(
            added = item.added,
            apkTags = item.apkTags,
            downloads = item.downloads,
            graphic = item.graphic,
            icon = item.icon,
            id = item.id,
            md5sum = item.md5sum,
            modified = item.modified,
            name = item.name,
            packageX = item.packageX,
            pdownloads = item.pdownloads,
            rating = item.rating,
            size = item.size,
            storeId = item.storeId,
            storeName = item.storeName,
            updated = item.updated,
            uptype = item.uptype,
            vercode = item.vercode,
            vername = item.vername
        )
    }
}