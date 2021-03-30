package com.ader.eventbudget20.domain.utils

import com.ader.eventbudget20.data.model.EventDBModel
import com.ader.eventbudget20.data.model.UserDBModel
import com.ader.eventbudget20.data.model.embedded.PaymentWithParticipantsModel
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel

object DBMapUtils {
    fun UserDBModel.toModel(): User {
        return User(this.id, this.name)
    }

    fun EventDBModel.toModel(): Event {
        return Event(this.id, this.name)
    }

    fun PaymentWithParticipantsModel.toPaymentModel(): Payment {
        return Payment(
            this.paymentDBModel.id,
            this.paymentDBModel.eventId,
            this.paymentDBModel.value,
            this.payerList.map { user ->
                user.toModel()
            },
            this.paymentDBModel.description,
            this.paymentParticipants.map { userDb ->
                userDb.toModel()
            }
        )
    }
}