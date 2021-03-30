package com.ader.eventbudget20.domain.repository

import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.domain.model.User
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun createPayment(eventId: Int, value: Float, payerList: List<User>, description: String, paymentParticipants: List<User>)

    fun deletePayment(payment: Payment)

    fun getPaymentById(idPayment: Int): Payment

    fun getAllEventPaymentsLive(idEvent: Int): Flow<List<Payment>>

    fun getAllUserEventPayment(eventId: Int, userId: Int): List<Payment>

    fun getAllUserEventPaymentParticipation(eventId: Int, userId: Int): List<Payment>
}