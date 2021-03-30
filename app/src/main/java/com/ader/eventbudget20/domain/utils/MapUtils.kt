package com.ader.eventbudget20.domain.utils

import com.ader.eventbudget20.data.model.EventDBModel
import com.ader.eventbudget20.data.model.ParticipantDBModel
import com.ader.eventbudget20.data.model.UserDBModel
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.domain.model.User

object MapUtils {
    fun User.toDBModel(): UserDBModel {
        val userDBModel = UserDBModel(this.name)
        userDBModel.id = this.id
        return userDBModel
    }

    fun Event.toDBModel(): EventDBModel {
        val eventDBModel = EventDBModel(this.name)
        eventDBModel.id = this.id
        return eventDBModel
    }

    fun User.toParticipantDBModel(eventId: Int): ParticipantDBModel{
        return ParticipantDBModel(this.id, eventId)
    }
}