package com.aptoide_app.data.remote

import com.aptoide_app.data.remote.dto.AptitudeRetrieve
import retrofit2.Call
import retrofit2.http.GET

/**
 * AptoideApi is an interface for the Aptoide API.
 * It provides a method to fetch app listings from the Aptoide API.
 * This interface is used with Retrofit to create the API service.
 */
interface AptoideApi {
    /**
     * Fetches app listings from the Aptoide API.
     * This is a GET request to the "6/bulkRequest/api_list/listApps" endpoint.
     *
     * @return A Call object that can be used to send the request.
     */
    @GET("6/bulkRequest/api_list/listApps")
    fun getListings(
    ): Call<AptitudeRetrieve>

    companion object{
        const val baseUrl = "https://ws2.aptoide.com/api/"
    }
}
