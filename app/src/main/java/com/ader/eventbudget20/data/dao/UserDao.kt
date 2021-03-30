package com.ader.eventbudget20.data.dao

import androidx.room.*
import com.ader.eventbudget20.data.model.UserDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserDBModel): Long

    @Delete
    fun delete(user: UserDBModel)

    @Query("SELECT * FROM user")
    fun getAllUsersLive(): Flow<List<UserDBModel>>

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<UserDBModel>

    @Query("SELECT * FROM user WHERE id_user == :id LIMIT 1")
    fun getUser(id: Int): UserDBModel
}