package com.ader.eventbudget20.di.module.repository

import com.ader.eventbudget20.data.AppDataBase
import com.ader.eventbudget20.domain.repository.ParticipantRepository
import com.ader.eventbudget20.domain.repository.impl.ParticipantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ParticipantRepositoryModule {
    @Provides
    fun provideParticipantRepository(appDataBase: AppDataBase): ParticipantRepository{
        return ParticipantRepositoryImpl(appDataBase.participantDao())
    }
}