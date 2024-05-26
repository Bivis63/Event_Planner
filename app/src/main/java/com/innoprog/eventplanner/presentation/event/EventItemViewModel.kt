package com.innoprog.eventplanner.presentation.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innoprog.eventplanner.domain.model.Event
import com.innoprog.eventplanner.domain.impl.AddEventItemUseCaseImpl
import com.innoprog.eventplanner.domain.impl.EditEventItemUseCaseImpl
import com.innoprog.eventplanner.domain.impl.GetEventItemUseCaseImpl
import com.innoprog.eventplanner.domain.usecase.GetLocationUseCase
import com.innoprog.eventplanner.domain.usecase.GetTemperatureUseCase
import com.innoprog.eventplanner.presentation.weather.state.ForecastState
import com.innoprog.eventplanner.presentation.weather.state.LocationState
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventItemViewModel @Inject constructor(
    private val getEventItemUseCase: GetEventItemUseCaseImpl,
    private val addEventItemUseCase: AddEventItemUseCaseImpl,
    private val editEventItemUseCase: EditEventItemUseCaseImpl,
    private val getLocationUseCase: GetLocationUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase
) : ViewModel() {

    private val _locationState = MutableLiveData<LocationState>()
    val locationState: LiveData<LocationState> = _locationState

    private val _temperatureState = MutableLiveData<ForecastState>()
    val temperatureState: LiveData<ForecastState> = _temperatureState


    private val _eventItem = MutableLiveData<Event>()
    val eventItem: LiveData<Event>
        get() = _eventItem

    fun getEventItem(eventItemId: Int) {
        viewModelScope.launch {
            val item = getEventItemUseCase.getEventItem(eventItemId)
            _eventItem.value = item
        }
    }

    fun addEventItem(
        inputName: String?,
        inputDescriptions: String?,
        inputLocation: String?,
        inputCity: String?,
        inputData: String?,
        weather: String,
        imageWeather:String
    ) {
        val name = parseParam(inputName)
        val descriptions = parseParam(inputDescriptions)
        val location = parseParam(inputLocation)
        val city = parseParam(inputCity)
        val data = parseParam(inputData)
        viewModelScope.launch {
            val eventItem = Event(
                eventName = name,
                description = descriptions,
                data = data,
                location = location,
                city = city,
                weather = weather,
                imageWeather =imageWeather
            )
            addEventItemUseCase.addEventItem(eventItem)
        }
    }

    fun editEventItem(
        inputName: String?,
        inputDescriptions: String?,
        inputLocation: String?,
        inputCity: String?,
        inputData: String?,
        weather: String,
        imageWeather:String
    ) {
        val name = parseParam(inputName)
        val descriptions = parseParam(inputDescriptions)
        val location = parseParam(inputLocation)
        val city = parseParam(inputCity)
        val data = parseParam(inputData)
        viewModelScope.launch {
            _eventItem.value?.let {
                val item = it.copy(
                    eventName = name,
                    description = descriptions,
                    data = data,
                    location = location,
                    city = city,
                    weather = weather,
                    imageWeather = imageWeather
                )
                editEventItemUseCase.editEventItem(item)
            }
        }
    }

    private fun parseParam(params: String?): String {
        return params?.trim() ?: ""
    }

    fun getLocation(query: String) {
        viewModelScope.launch() {
            getLocationUseCase.getLocation(query).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _locationState.postValue(LocationState.Content(response.data))
                    }

                    is Resource.Error -> {
                        _locationState.postValue(LocationState.Error(response.errorType))
                    }
                }
            }
        }
    }

    fun getTemperature(locationId: Int) {
        viewModelScope.launch() {
            getTemperatureUseCase.getTemperature(locationId).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _temperatureState.postValue(ForecastState.Content(response.data))
                    }

                    is Resource.Error -> {
                        _temperatureState.postValue(ForecastState.Error(response.errorType))
                    }
                }
            }
        }
    }


}