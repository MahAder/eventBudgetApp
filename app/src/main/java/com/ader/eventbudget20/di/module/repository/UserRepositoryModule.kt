package com.ader.eventbudget20.di.module.repository

import com.ader.eventbudget20.data.AppDataBase
import com.ader.eventbudget20.domain.repository.UserRepository
import com.ader.eventbudget20.domain.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserRepositoryModule {
    @Provides
    fun provideUserRepository(appDataBase: AppDataBase): UserRepository {
        return UserRepositoryImpl(appDataBase.userDao())
    }
}