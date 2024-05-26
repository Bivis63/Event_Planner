package com.innoprog.eventplanner.domain.usecase

import androidx.lifecycle.LiveData
import com.innoprog.eventplanner.domain.model.Event

interface GetEventListUseCase {
    fun getEventList(): LiveData<List<Event>>
}