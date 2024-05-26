package com.innoprog.eventplanner.presentation.weather.state

import com.innoprog.eventplanner.domain.model.Forecast
import com.innoprog.eventplanner.utils.ErrorType

sealed interface ForecastState {
    data class Content(val currentInfo: Forecast) : ForecastState

    data class Error(val type: ErrorType) : ForecastState
}