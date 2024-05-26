package com.innoprog.eventplanner.domain.model

data class CurrentWeather(
    val date: String?,
    val maxTemp: Float,
    val minTemp: Float,
    val symbol: String
)
