package com.ader.eventbudget20.di.module.repository

import com.ader.eventbudget20.data.dao.EventDao
import com.ader.eventbudget20.domain.repository.EventRepository
import com.ader.eventbudget20.domain.repository.impl.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class EventRepositoryModule {
    @Provides
    fun provideEventRepository(eventDao: EventDao): EventRepository{
        return EventRepositoryImpl(eventDao)
    }
}