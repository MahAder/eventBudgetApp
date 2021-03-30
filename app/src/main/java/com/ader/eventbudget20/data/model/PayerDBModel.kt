package com.ader.eventbudget20.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.ader.eventbudget20.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.PAYER_TABLE_NAME, foreignKeys = [
    ForeignKey(onDelete = ForeignKey.CASCADE, entity = UserDBModel::class,
        parentColumns = [DatabaseConstants.ID_USER], childColumns = [DatabaseConstants.ID_USER])
], indices = [Index(DatabaseConstants.ID_USER)],
    primaryKeys = [DatabaseConstants.ID_USER, DatabaseConstants.ID_PAYMENT])
data class PayerDBModel(
    @ColumnInfo(name = DatabaseConstants.ID_PAYMENT)
    val idPayment: Int,

    @ColumnInfo(name = DatabaseConstants.ID_USER)
    val idUser: Int
)