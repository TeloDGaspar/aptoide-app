package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "hidden")
    val hidden: Int,
    @Json(name = "limit")
    val limit: Int,
    @Json(name = "list")
    val list: List<Apps>,
    @Json(name = "next")
    val next: Int,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "total")
    val total: Int
)