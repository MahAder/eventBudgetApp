package com.ader.eventbudget20.domain.repository.impl

import com.ader.eventbudget20.data.dao.ParticipantDao
import com.ader.eventbudget20.data.model.ParticipantDBModel
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel
import com.ader.eventbudget20.domain.utils.MapUtils.toParticipantDBModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ParticipantRepositoryImpl(private val participantDao: ParticipantDao): ParticipantRepository {
    override suspend fun createParticipant(userId: Int, eventId: Int): Long {
        return participantDao.insert(ParticipantDBModel(userId, eventId))
    }

    override suspend fun createParticipants(participants: List<ParticipantDBModel>) {
        participantDao.insert(participants)
    }

    override suspend fun deleteParticipant(user: User, eventId: Int) {
        participantDao.delete(user.toParticipantDBModel(eventId))
    }

    override fun getAllParticipantsLive(eventId: Int): Flow<List<User>?> {
        return participantDao.getAllParticipantsLive(eventId).map {
            it?.users?.map { userDBModel ->
                userDBModel.toModel()
            }
        }
    }

    override suspend fun getAllParticipants(eventId: Int): List<User> {
        return participantDao.getAllParticipants(eventId).users.map {
            it.toModel()
        }
    }
}