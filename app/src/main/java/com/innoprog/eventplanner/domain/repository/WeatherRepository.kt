package com.innoprog.eventplanner.domain.repository

import com.innoprog.eventplanner.domain.model.CurrentWeather
import com.innoprog.eventplanner.domain.model.Forecast
import com.innoprog.eventplanner.domain.model.Location
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getTemperatureForCity(locationId: Int): Flow<Resource<Forecast>>

    suspend fun getLocationByQuery(query: String): Flow<Resource<Location>>


}