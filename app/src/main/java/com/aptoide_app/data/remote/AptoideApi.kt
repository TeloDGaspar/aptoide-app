package com.aptoide_app.data.remote

/*
import com.aptoide_app.remote.dto.AptoideResponse
*/
import com.aptoide_app.data.remote.dto.AptitudeRetrieve
import retrofit2.http.GET

interface AptoideApi {
    @GET("6/bulkRequest/api_list/listApps")
    suspend fun getListings(
    ): AptitudeRetrieve

    companion object{
        const val baseUrl = "https://ws2.aptoide.com/api/"
    }
}