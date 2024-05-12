package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Responses(
    @Json(name = "listApps")
    val listApps: ListApps
)