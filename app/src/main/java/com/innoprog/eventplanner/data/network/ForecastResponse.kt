package com.innoprog.eventplanner.data.network

import com.innoprog.eventplanner.data.network.dto.CurrentWeatherDto
import com.innoprog.eventplanner.network.Response

data class ForecastResponse(val forecast: List<CurrentWeatherDto>): Response()
