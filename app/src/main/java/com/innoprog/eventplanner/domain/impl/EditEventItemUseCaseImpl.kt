package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.usecase.EditEventItemUseCase
import javax.inject.Inject

class EditEventItemUseCaseImpl @Inject constructor(private val eventListRepository: EventListRepository) :
    EditEventItemUseCase {

    override suspend fun editEventItem(eventId: Event) {
        eventListRepository.editEventItem(eventId)
    }
}