package com.innoprog.eventplanner.domain.impl

import androidx.lifecycle.LiveData
import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.usecase.GetEventListUseCase
import javax.inject.Inject

class GetEventListUseCaseImpl @Inject constructor(private val eventListRepository: EventListRepository) :
    GetEventListUseCase {

    override fun getEventList(): LiveData<List<Event>> {
        return eventListRepository.getEventList()
    }
}