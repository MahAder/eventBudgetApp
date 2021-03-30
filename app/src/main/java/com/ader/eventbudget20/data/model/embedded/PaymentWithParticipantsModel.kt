package com.ader.eventbudget20.data.model.embedded

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ader.eventbudget20.constants.DatabaseConstants
import com.ader.eventbudget20.data.model.PayerDBModel
import com.ader.eventbudget20.data.model.PaymentDBModel
import com.ader.eventbudget20.data.model.PaymentParticipantDBModel
import com.ader.eventbudget20.data.model.UserDBModel
import com.ader.eventbudget20.domain.model.User

data class PaymentWithParticipantsModel(
    @Embedded
    val paymentDBModel: PaymentDBModel,

    @Relation(
        parentColumn = DatabaseConstants.ID_PAYMENT,
        entityColumn = DatabaseConstants.ID_USER,
        associateBy = Junction(PayerDBModel::class)
    )
    val payerList: List<UserDBModel>,

    @Relation(
        parentColumn = DatabaseConstants.ID_PAYMENT,
        entityColumn = DatabaseConstants.ID_USER,
        associateBy = Junction(PaymentParticipantDBModel::class)
    )

    val paymentParticipants: List<UserDBModel>
)