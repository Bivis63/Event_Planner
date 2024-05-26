package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.CurrentWeather
import com.innoprog.eventplanner.domain.model.Forecast
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetTemperatureUseCase {

    suspend fun getTemperature(locationId: Int): Flow<Resource<Forecast>>
}