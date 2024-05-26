package com.innoprog.eventplanner.data.network.dto

import com.innoprog.eventplanner.domain.model.ForecastLocation

data class ForecastLocationDto(
    val id: Int,
    val name: String,
    val country: String
){
    fun toDomain(): ForecastLocation{
        return ForecastLocation(
            id = this.id,
            name = this.name,
            country = this.country
        )
    }
}

