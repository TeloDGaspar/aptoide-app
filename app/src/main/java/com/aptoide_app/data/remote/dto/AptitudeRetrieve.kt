package com.aptoide_app.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AptitudeRetrieve(
    @Json(name = "responses")
    val responses: Responses,
    @Json(name = "status")
    val status: String
)