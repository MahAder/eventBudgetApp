package com.ader.eventbudget20.domain.repository.impl

import com.ader.eventbudget20.data.dao.EventDao
import com.ader.eventbudget20.data.model.EventDBModel
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.domain.repository.EventRepository
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel
import com.ader.eventbudget20.domain.utils.MapUtils.toDBModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class EventRepositoryImpl @Inject constructor(private val eventDao: EventDao) : EventRepository {
    override suspend fun createNewEvent(eventName: String): Long {
        return eventDao.insert(EventDBModel(eventName))
    }

    override suspend fun deleteEvent(event: Event) {
        eventDao.delete(event.toDBModel())
    }

    override fun getAllEventsLive(): Flow<List<Event>> {
        return eventDao.getAllEventsLive().map {
            it.map { eventDb ->
                eventDb.toModel()
            }
        }
    }

    override suspend fun getAllEvents(): List<Event> {
        return eventDao.getAllEvents().map {
            Event(it.id, it.name)
        }
    }

    override suspend fun getEventById(idEvent: Int): Event {
        return eventDao.getEventById(idEvent).toModel()
    }
}