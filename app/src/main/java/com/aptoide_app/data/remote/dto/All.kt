package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class All(
    @Json(name = "data")
    val `data`: Data,
    @Json(name = "info")
    val info: InfoX
)