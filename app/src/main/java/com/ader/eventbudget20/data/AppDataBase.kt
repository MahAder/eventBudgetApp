package com.ader.eventbudget20.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ader.eventbudget20.constants.DatabaseConstants
import com.ader.eventbudget20.data.dao.EventDao
import com.ader.eventbudget20.data.dao.ParticipantDao
import com.ader.eventbudget20.data.dao.PaymentDao
import com.ader.eventbudget20.data.dao.UserDao
import com.ader.eventbudget20.data.model.*

@Database(entities = [EventDBModel::class, UserDBModel::class, ParticipantDBModel::class,
                        PaymentDBModel::class, PaymentParticipantDBModel::class, PayerDBModel::class],
    version = DatabaseConstants.DATABASE_VERSION)
abstract class AppDataBase: RoomDatabase() {
    abstract fun eventDao(): EventDao

    abstract fun userDao(): UserDao

    abstract fun participantDao(): ParticipantDao

    abstract fun paymentDao(): PaymentDao
}