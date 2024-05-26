package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.usecase.DeleteEventItemUseCase
import javax.inject.Inject

class DeleteEventItemUseCaseImpl @Inject constructor(private val eventListRepository: EventListRepository):
    DeleteEventItemUseCase {

   override suspend fun deleteEventItem(eventItem: Event) {
        eventListRepository.deleteEventItem(eventItem)
    }
}