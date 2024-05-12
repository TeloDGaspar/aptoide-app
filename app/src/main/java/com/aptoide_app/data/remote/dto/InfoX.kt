package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoX(
    @Json(name = "status")
    val status: String,
    @Json(name = "time")
    val time: Time
)