package com.ader.eventbudget20.domain.repository

import com.ader.eventbudget20.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(name: String): User

    suspend fun deleteUser(user: User)

    fun getAllUsersLive(): Flow<List<User>>

    suspend fun getAllUsers(): List<User>
}