package com.innoprog.eventplanner.network

sealed interface Request {

    data class GetLocation(val query: String) : Request
    data class GetForecast(val locationId: Int) : Request
}
