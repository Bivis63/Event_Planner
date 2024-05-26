package com.innoprog.eventplanner.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.impl.DeleteEventItemUseCaseImpl
import com.innoprog.eventplanner.domain.impl.EditEventItemUseCaseImpl
import com.innoprog.eventplanner.domain.impl.GetEventListUseCaseImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getEventListUseCase: GetEventListUseCaseImpl,
    private val deleteEventItemUseCase: DeleteEventItemUseCaseImpl,
    private val editEventItemUseCase: EditEventItemUseCaseImpl
    ) : ViewModel() {


    val eventList: LiveData<List<Event>> = getEventListUseCase.getEventList()

    fun deleteEventItem(eventItem: Event) {
        viewModelScope.launch {
            deleteEventItemUseCase.deleteEventItem(eventItem)
        }
    }
    fun editEventItem(eventItem: Event, status: String) {
        viewModelScope.launch {
            val updatedEventItem = eventItem.copy(status = status)
            editEventItemUseCase.editEventItem(updatedEventItem)
        }
    }
}