package com.innoprog.eventplanner.domain.repository

import androidx.lifecycle.LiveData
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EventListRepository {

    suspend fun addEventItem(eventItem: Event)

    suspend fun deleteEventItem(eventItem: Event)

    suspend fun editEventItem(eventItem: Event)

    suspend fun getEventItem(eventId: Int): Event

    fun getEventList(): LiveData<List<Event>>


}