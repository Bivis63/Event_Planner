package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.Event

interface EditEventItemUseCase {
    suspend fun editEventItem(eventId: Event)
}