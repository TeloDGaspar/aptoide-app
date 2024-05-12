package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Datasets(
    @Json(name = "all")
    val all: All
)