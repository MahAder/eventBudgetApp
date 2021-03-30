package com.ader.eventbudget20.domain.model

data class Payment(
    val id: Int,
    val eventId: Int,
    val value: Float,
    val payersList: List<User>,
    val description: String,
    val paymentParticipants: List<User>
)