package com.innoprog.eventplanner.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.innoprog.eventplanner.domain.model.Event

@Entity(tableName = "event_items")
data class EventItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val eventName: String,
    val description: String,
    val data: String,
    val location: String,
    val city : String,
    val weather: String,
    val status: String ,
    val imageWeather: String
)
