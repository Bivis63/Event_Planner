package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.Event

interface GetEventItemUseCase {
    suspend fun getEventItem(eventId: Int): Event
}