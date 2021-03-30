package com.ader.eventbudget20.data.model

import androidx.room.*
import com.ader.eventbudget20.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.PARTICIPANT_TABLE_NAME, foreignKeys = [
    ForeignKey(onDelete = ForeignKey.CASCADE, entity = UserDBModel::class,
    parentColumns = [DatabaseConstants.ID_USER], childColumns = [DatabaseConstants.ID_USER])
], indices = [Index(DatabaseConstants.ID_USER)],
primaryKeys = [DatabaseConstants.ID_USER, DatabaseConstants.ID_EVENT])
data class ParticipantDBModel(
    @ColumnInfo(name = DatabaseConstants.ID_USER)
    val idUser: Int,

    @ColumnInfo(name = DatabaseConstants.ID_EVENT)
    val  idEvent: Int
)