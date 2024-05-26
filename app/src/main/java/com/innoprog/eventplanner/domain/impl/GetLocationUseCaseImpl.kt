package com.innoprog.eventplanner.domain.impl

import com.innoprog.eventplanner.domain.model.Location
import com.innoprog.eventplanner.domain.repository.WeatherRepository
import com.innoprog.eventplanner.domain.usecase.GetLocationUseCase
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUseCaseImpl @Inject constructor(private val repository: WeatherRepository) :
    GetLocationUseCase {
    override suspend fun getLocation(query: String): Flow<Resource<Location>> {
        return repository.getLocationByQuery(query)
    }
}