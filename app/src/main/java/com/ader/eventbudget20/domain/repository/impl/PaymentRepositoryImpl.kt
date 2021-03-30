package com.ader.eventbudget20.domain.repository.impl

import com.ader.eventbudget20.data.dao.PaymentDao
import com.ader.eventbudget20.data.model.PayerDBModel
import com.ader.eventbudget20.data.model.PaymentDBModel
import com.ader.eventbudget20.data.model.PaymentParticipantDBModel
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.PaymentRepository
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel
import com.ader.eventbudget20.domain.utils.DBMapUtils.toPaymentModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.ArrayList

class PaymentRepositoryImpl(private val paymentDao: PaymentDao) : PaymentRepository {
    override fun createPayment(
        eventId: Int,
        value: Float,
        payerList: List<User>,
        description: String,
        paymentParticipants: List<User>
    ) {
        val paymentDBModel = PaymentDBModel(value, description, eventId)
        val paymentId = paymentDao.insert(paymentDBModel).toInt()
        val payerDBList = ArrayList<PayerDBModel>()
        val participantList = ArrayList<PaymentParticipantDBModel>()
        for (user in payerList) {
            payerDBList.add(
                PayerDBModel(
                    paymentId, user.id
                )
            )
        }
        for (user in paymentParticipants) {
            participantList.add(
                PaymentParticipantDBModel(paymentId, user.id)
            )
        }
        paymentDao.insertPayerList(payerDBList)
        paymentDao.insertPaymentParticipantList(participantList)
    }

    override fun deletePayment(payment: Payment) {
        val paymentDBModel = PaymentDBModel(
            payment.value,
            payment.description,
            payment.eventId
        )
        paymentDBModel.id = payment.id
        paymentDao.deletePayment(paymentDBModel)
    }

    override fun getPaymentById(idPayment: Int): Payment {
        return paymentDao.getPayment(idPayment).toPaymentModel()
    }

    override fun getAllEventPaymentsLive(idEvent: Int): Flow<List<Payment>> {
        return paymentDao.getAllEventPaymentsLive(idEvent).map {
            it.map { payment ->
                payment.toPaymentModel()
            }
        }
    }

    override fun getAllUserEventPayment(eventId: Int, userId: Int): List<Payment> {
        return paymentDao.getAllEventUserPayment(eventId, userId).map { payment ->
            payment.toPaymentModel()
        }
    }

    override fun getAllUserEventPaymentParticipation(eventId: Int, userId: Int): List<Payment> {
        return paymentDao.getAllEventPaymentWithUserParticipate(eventId, userId).map { payment ->
            payment.toPaymentModel()
        }
    }
}