package com.ader.eventbudget20.domain.repository

import com.ader.eventbudget20.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun createNewEvent(eventName: String): Long

    suspend fun deleteEvent(event: Event)

    fun getAllEventsLive(): Flow<List<Event>>

    suspend fun getAllEvents(): List<Event>

    suspend fun getEventById(idEvent: Int): Event
}