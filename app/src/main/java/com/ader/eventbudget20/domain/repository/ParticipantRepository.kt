package com.ader.eventbudget20.domain.repository

import com.ader.eventbudget20.data.model.ParticipantDBModel
import com.ader.eventbudget20.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ParticipantRepository {
    suspend fun createParticipant(userId: Int, eventId: Int): Long

    suspend fun createParticipants(participants: List<ParticipantDBModel>)

    suspend fun deleteParticipant(user: User, eventId: Int)

    fun getAllParticipantsLive(eventId: Int): Flow<List<User>>

    suspend fun getAllParticipants(eventId: Int): List<User>
}