package com.ader.eventbudget20.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ader.eventbudget20.constants.DatabaseConstants

@Entity(tableName = DatabaseConstants.USER_TABLE_NAME)
data class UserDBModel(
    @ColumnInfo(name = DatabaseConstants.NAME_COLUMN)
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseConstants.ID_USER)
    var id: Int = 0
}