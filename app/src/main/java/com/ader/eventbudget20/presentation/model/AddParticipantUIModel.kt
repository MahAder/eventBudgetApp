package com.ader.eventbudget20.presentation.model

import com.ader.eventbudget20.domain.model.User

data class AddParticipantAdapterModel(
    val user: User,
    var isParticipant: Boolean
)