package com.ader.eventbudget20.presentation.model

import com.ader.eventbudget20.domain.model.User

data class ParticipantUIModel(
    val user: User,
    val total: Float
)