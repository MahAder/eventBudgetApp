package com.ader.eventbudget20.di.module.repository

import com.ader.eventbudget20.data.AppDataBase
import com.ader.eventbudget20.domain.repository.PaymentRepository
import com.ader.eventbudget20.domain.repository.impl.PaymentRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PaymentRepositoryModule {
    @Provides
    fun providePaymentRepository(appDataBase: AppDataBase): PaymentRepository {
        return PaymentRepositoryImpl(appDataBase.paymentDao())
    }
}