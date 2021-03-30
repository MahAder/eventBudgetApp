package com.ader.eventbudget20.presentation.payment.createpayment

interface CreatePaymentNavigation {
    fun showIncorrectValueError()

    fun showEmptyPayerError()

    fun showEmptyParticipantsError()

    fun close()
}