package com.innoprog.eventplanner.data.impl

import com.innoprog.eventplanner.data.network.ForecastResponse
import com.innoprog.eventplanner.data.network.LocationsResponse
import com.innoprog.eventplanner.data.network.client.NetworkClient
import com.innoprog.eventplanner.domain.model.CurrentWeather
import com.innoprog.eventplanner.domain.model.Forecast
import com.innoprog.eventplanner.domain.model.ForecastLocation
import com.innoprog.eventplanner.domain.model.Location
import com.innoprog.eventplanner.domain.repository.WeatherRepository
import com.innoprog.eventplanner.network.Request
import com.innoprog.eventplanner.utils.ErrorType
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    WeatherRepository {

    override suspend fun getTemperatureForCity(locationId: Int): Flow<Resource<Forecast>> = flow {
        val response = networkClient.doRequest(Request.GetForecast(locationId))

        if (response is ForecastResponse && response.resultCode == 200) {
            emit(Resource.Success(mapToForecast(response)))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    override suspend fun getLocationByQuery(query: String): Flow<Resource<Location>> = flow {

        val response = networkClient.doRequest(Request.GetLocation(query))

        if (response is LocationsResponse && response.resultCode == 200) {
            emit(Resource.Success(mapToLocation(response)))
        } else
            emit(Resource.Error(getErrorType(response.resultCode)))
    }

    private fun mapToForecast(response: ForecastResponse): Forecast {
        val forecast = response.forecast.map { it.toDomain() } as ArrayList<CurrentWeather>
        return Forecast(forecast)
    }

    private fun mapToLocation(response: LocationsResponse): Location {
        val forecastLocations =
            response.locations.map { it.toDomain() } as ArrayList<ForecastLocation>
        return Location(forecastLocations)
    }

    private fun getErrorType(code: Int): ErrorType = when (code) {
        -1 -> ErrorType.NO_CONNECTION
        400 -> ErrorType.BAD_REQUEST
        403 -> ErrorType.CAPTCHA_REQUIRED
        404 -> ErrorType.NOT_FOUND
        else -> ErrorType.UNEXPECTED
    }
}