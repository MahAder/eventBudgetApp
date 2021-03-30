package com.ader.eventbudget20.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.ader.eventbudget20.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.PAYMENT_PARTICIPANT_TABLE_NAME, foreignKeys = [
    ForeignKey(onDelete = ForeignKey.CASCADE, entity = PaymentDBModel::class,
    parentColumns = [DatabaseConstants.ID_PAYMENT], childColumns = [DatabaseConstants.ID_PAYMENT])
], indices = [Index(DatabaseConstants.ID_PAYMENT)],
primaryKeys = [DatabaseConstants.ID_PAYMENT, DatabaseConstants.ID_USER])
data class PaymentParticipantDBModel(
    @ColumnInfo(name = DatabaseConstants.ID_PAYMENT)
    val idPayment: Int,

    @ColumnInfo(name = DatabaseConstants.ID_USER)
    val idUser: Int
)