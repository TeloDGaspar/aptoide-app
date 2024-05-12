package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListApps(
    @Json(name = "datasets")
    val datasets: Datasets,
    @Json(name = "info")
    val info: InfoX
)