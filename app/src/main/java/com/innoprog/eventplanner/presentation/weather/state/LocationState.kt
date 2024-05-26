package com.innoprog.eventplanner.presentation.weather.state

import com.innoprog.eventplanner.domain.model.Location
import com.innoprog.eventplanner.utils.ErrorType

sealed interface LocationState {
    data class Content(val locationInfo: Location) : LocationState

    data class Error(val type: ErrorType) : LocationState
}