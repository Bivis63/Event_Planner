package com.innoprog.eventplanner.di

import androidx.lifecycle.ViewModel
import com.innoprog.eventplanner.presentation.event.EventItemViewModel
import com.innoprog.eventplanner.presentation.main.MainViewModel
import com.innoprog.eventplanner.presentation.weather.WeatherViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EventItemViewModel::class)
    fun bindEventItemViewModel(viewModel: EventItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    fun bindWeatherViewModel(viewModel: WeatherViewModel): ViewModel
}