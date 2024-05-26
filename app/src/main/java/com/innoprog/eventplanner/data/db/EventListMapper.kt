package com.innoprog.eventplanner.data.db

import com.innoprog.eventplanner.domain.model.Event
import javax.inject.Inject

class EventListMapper @Inject constructor() {

    fun mapEntityToDbModel(eventItem: Event): EventItemDbModel {
        return EventItemDbModel(
            id = eventItem.id,
            eventName = eventItem.eventName,
            description = eventItem.description,
            data = eventItem.data,
            location = eventItem.location,
            city = eventItem.city,
            weather = eventItem.weather ?: "",
            status = eventItem.status,
            imageWeather = eventItem.imageWeather
        )
    }

    fun mapDbModelToEntity(eventItemDbModel: EventItemDbModel): Event {
        return Event(
            id = eventItemDbModel.id,
            eventName = eventItemDbModel.eventName,
            description = eventItemDbModel.description,
            data = eventItemDbModel.data,
            location = eventItemDbModel.location,
            city = eventItemDbModel.city,
            weather = eventItemDbModel.weather ?: "",
            status = eventItemDbModel.status,
            imageWeather = eventItemDbModel.imageWeather
        )
    }

    fun mapListDbModelToListEntity(list: List<EventItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}