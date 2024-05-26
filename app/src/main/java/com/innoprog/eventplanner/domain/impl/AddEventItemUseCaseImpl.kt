package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.usecase.AddEventItemUseCase
import javax.inject.Inject

class AddEventItemUseCaseImpl @Inject constructor(private val eventListRepository: EventListRepository) :
    AddEventItemUseCase {

    override suspend fun addEventItem(eventItem: Event) {
        eventListRepository.addEventItem(eventItem)
    }
}