package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.Event

interface AddEventItemUseCase {
    suspend fun addEventItem(eventItem: Event)
}