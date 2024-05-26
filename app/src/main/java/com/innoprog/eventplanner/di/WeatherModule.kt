package com.innoprog.eventplanner.di

import com.innoprog.eventplanner.data.impl.WeatherRepositoryImpl
import com.innoprog.eventplanner.data.network.ForecaApi
import com.innoprog.eventplanner.data.network.client.NetworkClient
import com.innoprog.eventplanner.data.network.client.NetworkClientImpl
import com.innoprog.eventplanner.domain.impl.GetLocationUseCaseImpl
import com.innoprog.eventplanner.domain.impl.GetTemperatureUseCaseImpl
import com.innoprog.eventplanner.domain.repository.WeatherRepository
import com.innoprog.eventplanner.domain.usecase.GetLocationUseCase
import com.innoprog.eventplanner.domain.usecase.GetTemperatureUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [WeatherModule.ForecaApiModule::class])
interface WeatherModule {

    @ApplicationScope
    @Binds
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindGetLocationUseCase(impl: GetLocationUseCaseImpl): GetLocationUseCase

    @Binds
    fun bindGetTemperatureUseCase(impl: GetTemperatureUseCaseImpl): GetTemperatureUseCase

    @Binds
    fun bindNetworkClient(impl: NetworkClientImpl): NetworkClient

    @Module
    class ForecaApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): ForecaApi {
            return retrofit.create(ForecaApi::class.java)
        }
    }
}