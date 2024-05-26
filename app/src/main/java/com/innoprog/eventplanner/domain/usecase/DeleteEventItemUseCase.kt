package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.Event

interface DeleteEventItemUseCase {
    suspend fun deleteEventItem(eventItem: Event)
}