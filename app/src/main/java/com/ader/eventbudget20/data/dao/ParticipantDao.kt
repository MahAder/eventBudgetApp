package com.ader.eventbudget20.data.dao

import androidx.room.*
import com.ader.eventbudget20.data.model.ParticipantDBModel
import com.ader.eventbudget20.data.model.embedded.EventWithUsersModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(participantDBModel: ParticipantDBModel): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(participantList: List<ParticipantDBModel>)

    @Delete
    fun delete(participantDBModel: ParticipantDBModel)

    @Query("SELECT * FROM event WHERE event.id_event == :eventId")
    fun getAllParticipantsLive(eventId: Int): Flow<EventWithUsersModel?>

    @Query("SELECT * FROM event WHERE event.id_event == :eventId")
    fun getAllParticipants(eventId: Int): EventWithUsersModel
}