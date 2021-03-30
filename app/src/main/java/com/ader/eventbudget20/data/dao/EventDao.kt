package com.ader.eventbudget20.data.dao

import androidx.room.*
import com.ader.eventbudget20.data.model.EventDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(eventDBModel: EventDBModel): Long

    @Delete
    fun delete(eventDBModel: EventDBModel)

    @Query("SELECT * FROM event")
    fun getAllEventsLive(): Flow<List<EventDBModel>>

    @Query("SELECT * FROM event")
    fun getAllEvents(): List<EventDBModel>

    @Query("SELECT * FROM event WHERE id_event == :idEvent")
    fun getEventById(idEvent: Int): EventDBModel
}