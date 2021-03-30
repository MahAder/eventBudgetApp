package com.ader.eventbudget20.di.module

import android.content.Context
import androidx.room.Room
import com.ader.eventbudget20.constants.DatabaseConstants
import com.ader.eventbudget20.data.AppDataBase
import com.ader.eventbudget20.data.dao.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideEventDao(appDataBase: AppDataBase): EventDao{
        return appDataBase.eventDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).build()
    }
}