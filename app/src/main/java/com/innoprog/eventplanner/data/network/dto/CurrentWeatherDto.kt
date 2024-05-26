package com.innoprog.eventplanner.data.network.dto

import com.innoprog.eventplanner.domain.model.CurrentWeather

data class CurrentWeatherDto(
    val date: String?,
    val maxTemp: Float,
    val minTemp: Float,
    val symbol: String
) {
    fun toDomain(): CurrentWeather {
        return CurrentWeather(
            date = this.date,
            maxTemp = this.maxTemp,
            minTemp = this.minTemp,
            symbol = this.symbol
        )
    }
}