package com.innoprog.eventplanner.domain.model

import com.innoprog.eventplanner.network.Response

data class Forecast(val forecast: List<CurrentWeather>): Response()
