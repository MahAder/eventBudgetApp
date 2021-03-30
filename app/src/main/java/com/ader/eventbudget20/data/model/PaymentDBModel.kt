package com.ader.eventbudget20.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ader.eventbudget20.constants.DatabaseConstants

@Entity(
    tableName = DatabaseConstants.PAYMENT_TABLE_NAME, foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE, parentColumns = [DatabaseConstants.ID_EVENT],
            childColumns = [DatabaseConstants.ID_EVENT], entity = EventDBModel::class
        )
    ]
)
data class PaymentDBModel(
    @ColumnInfo(name = DatabaseConstants.PAYMENT_VALUE)
    val value: Float,

    @ColumnInfo(name = DatabaseConstants.PAYMENT_DESCRIPTION)
    val description: String,

    @ColumnInfo(name = DatabaseConstants.ID_EVENT)
    val eventId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.ID_PAYMENT)
    var id: Int = 0
}