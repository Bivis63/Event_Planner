package com.innoprog.eventplanner.domain.model

import java.io.Serializable

data class Event(
    val eventName: String,
    val description: String,
    val data: String,
    val location: String,
    val city: String,
    val weather: String = "+10",
    var status: String ="",
    var id: Int = UNDEFINED_ID,
    val imageWeather: String
) : Serializable {

    companion object {
        const val UNDEFINED_ID = 0
    }
}

