package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.model.CurrentWeather
import com.innoprog.eventplanner.domain.model.Forecast
import com.innoprog.eventplanner.domain.repository.WeatherRepository
import com.innoprog.eventplanner.domain.usecase.GetTemperatureUseCase
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTemperatureUseCaseImpl @Inject constructor(private val repository: WeatherRepository) :
    GetTemperatureUseCase {

    override suspend fun getTemperature(locationId: Int): Flow<Resource<Forecast>> {
        return repository.getTemperatureForCity(locationId)
    }
}