package com.ader.eventbudget20.data.dao

import androidx.room.*
import com.ader.eventbudget20.data.model.PayerDBModel
import com.ader.eventbudget20.data.model.PaymentDBModel
import com.ader.eventbudget20.data.model.PaymentParticipantDBModel
import com.ader.eventbudget20.data.model.embedded.PaymentWithParticipantsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(paymentDBModel: PaymentDBModel): Long

    @Insert
    fun insert(paymentDBModelList: List<PaymentDBModel>)

    @Insert
    fun insertPaymentParticipant(paymentParticipantDBModel: PaymentParticipantDBModel)

    @Insert
    fun insertPaymentParticipantList(paymentParticipantDBModel: List<PaymentParticipantDBModel>)

    @Insert
    fun insertPayerList(payersList: List<PayerDBModel>)

    @Delete
    fun deletePayment(paymentDBModel: PaymentDBModel)

    @Delete
    fun deletePaymentParticipant(paymentParticipantDBModel: PaymentParticipantDBModel)

    @Query("SELECT * FROM payment WHERE id_payment == :idPayment")
    fun getPayment(idPayment: Int): PaymentWithParticipantsModel

    @Query("SELECT * FROM payment WHERE id_event == :eventId")
    fun getAllEventPaymentsLive(eventId: Int): Flow<List<PaymentWithParticipantsModel>>

    @Query("SELECT * FROM payment WHERE id_event == :eventId")
    fun getAllEventPayments(eventId: Int): List<PaymentWithParticipantsModel>

    @Query("SELECT * FROM payment INNER JOIN payer ON payment.id_payment = payer.id_payment WHERE payment.id_event == :eventId AND payer.id_user == :userId")
    fun getAllEventUserPayment(eventId: Int, userId: Int): List<PaymentWithParticipantsModel>

    @Query("SELECT * FROM payment INNER JOIN payment_participant ON payment.id_payment = payment_participant.id_payment WHERE payment.id_event == :eventId AND payment_participant.id_user == :userId")
    fun getAllEventPaymentWithUserParticipate(eventId: Int, userId: Int): List<PaymentWithParticipantsModel>
}