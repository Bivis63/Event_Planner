package com.innoprog.eventplanner.presentation.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innoprog.eventplanner.domain.usecase.GetLocationUseCase
import com.innoprog.eventplanner.domain.usecase.GetTemperatureUseCase
import com.innoprog.eventplanner.presentation.weather.state.ForecastState
import com.innoprog.eventplanner.presentation.weather.state.LocationState
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase
) : ViewModel() {

    private val _locationState = MutableLiveData<LocationState>()
    val locationState: LiveData<LocationState> = _locationState

    private val _temperatureState = MutableLiveData<ForecastState>()
    val temperatureState: LiveData<ForecastState> = _temperatureState


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