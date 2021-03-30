package com.ader.eventbudget20.constants

object DatabaseConstants {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "event_budget_db"

    //Table names
    const val EVENT_TABLE_NAME = "event"
    const val USER_TABLE_NAME = "user"
    const val PARTICIPANT_TABLE_NAME = "participant"
    const val PAYMENT_TABLE_NAME = "payment"
    const val PAYMENT_PARTICIPANT_TABLE_NAME = "payment_participant"
    const val PAYER_TABLE_NAME = "payer"

    //Event table columns name
    const val ID_COLUMN_NAME = "id"
    const val NAME_COLUMN = "name"
    const val ID_USER = "id_user"
    const val ID_EVENT = "id_event"

    //Payment Table
    const val ID_PAYMENT = "id_payment"
    const val PAYMENT_VALUE = "value"
    const val PAYMENT_DESCRIPTION = "payment_description"
}