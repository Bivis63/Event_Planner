package com.innoprog.eventplanner.data.network

import com.innoprog.eventplanner.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ForecaApi {

    @Headers("Authorization: Bearer ${BuildConfig.FORECA_TOKEN}")
    @GET("/api/v1/location/search/{query}")
    suspend fun getLocations(
        @Path("query") query: String
    ): LocationsResponse

    @Headers("Authorization: Bearer ${BuildConfig.FORECA_TOKEN}")
    @GET("/api/v1/forecast/daily/{location}")
    suspend fun getForecast(
        @Path("location") locationId: Int,
    ): ForecastResponse
}