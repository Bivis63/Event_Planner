package com.innoprog.eventplanner.domain.usecase

import com.innoprog.eventplanner.domain.model.Location
import com.innoprog.eventplanner.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GetLocationUseCase {
   suspend fun getLocation(query: String): Flow<Resource<Location>>
}