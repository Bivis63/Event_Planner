package com.innoprog.eventplanner.data.network

import com.innoprog.eventplanner.data.network.dto.ForecastLocationDto
import com.innoprog.eventplanner.network.Response

data class LocationsResponse(val locations: ArrayList<ForecastLocationDto>) : Response()