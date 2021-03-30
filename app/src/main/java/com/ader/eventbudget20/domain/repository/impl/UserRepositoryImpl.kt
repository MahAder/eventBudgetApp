package com.ader.eventbudget20.domain.repository.impl

import com.ader.eventbudget20.data.dao.UserDao
import com.ader.eventbudget20.data.model.UserDBModel
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.domain.repository.UserRepository
import com.ader.eventbudget20.domain.utils.DBMapUtils.toModel
import com.ader.eventbudget20.domain.utils.MapUtils.toDBModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun createUser(name: String): User {
        val id = userDao.insert(UserDBModel(name))
        return userDao.getUser(id.toInt()).toModel()
    }

    override suspend fun deleteUser(user: User) {
        userDao.delete(user.toDBModel())
    }

    override fun getAllUsersLive(): Flow<List<User>> {
        return userDao.getAllUsersLive().map {
            it.map { user ->
                user.toModel()
            }
        }
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers().map {
            it.toModel()
        }
    }
}