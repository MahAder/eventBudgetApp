package com.ader.eventbudget20.data.model.embedded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ader.eventbudget20.constants.DatabaseConstants
import com.ader.eventbudget20.data.model.EventDBModel
import com.ader.eventbudget20.data.model.ParticipantDBModel
import com.ader.eventbudget20.data.model.UserDBModel

data class EventWithUsersModel(
    @Embedded val event: EventDBModel,
    @Relation(
        parentColumn = DatabaseConstants.ID_EVENT,
        entityColumn = DatabaseConstants.ID_USER,
        associateBy = Junction(ParticipantDBModel::class)
    )
    val users: List<UserDBModel>
)