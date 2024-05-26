package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.usecase.GetEventItemUseCase
import javax.inject.Inject

class GetEventItemUseCaseImpl @Inject constructor(private val eventListRepository: EventListRepository) :
    GetEventItemUseCase {

    override suspend fun getEventItem(eventId: Int): Event {
        return eventListRepository.getEventItem(eventId)
    }
}