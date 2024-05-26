package com.innoprog.eventplanner.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.innoprog.eventplanner.data.db.EventDao
import com.innoprog.eventplanner.data.db.EventListMapper
import com.innoprog.eventplanner.data.network.ForecaApi
import com.innoprog.eventplanner.domain.repository.EventListRepository
import com.innoprog.eventplanner.domain.model.Event
import javax.inject.Inject

class EventListRepositoryImpl @Inject constructor(
    private val eventListDao: EventDao,
    private val mapper: EventListMapper,
) : EventListRepository {


    override suspend fun addEventItem(eventItem: Event) {
        eventListDao.addEventItem(mapper.mapEntityToDbModel(eventItem))
    }

    override suspend fun deleteEventItem(eventItem: Event) {
        eventListDao.deleteEventItem(eventItem.id)
    }

    override suspend fun editEventItem(eventItem: Event) {
        eventListDao.addEventItem(mapper.mapEntityToDbModel(eventItem))
    }

    override suspend fun getEventItem(eventId: Int): Event {
        val dbModel = eventListDao.getEventItem(eventId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getEventList(): LiveData<List<Event>> =
        MediatorLiveData<List<Event>>().apply {
            addSource(eventListDao.getEventList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
}