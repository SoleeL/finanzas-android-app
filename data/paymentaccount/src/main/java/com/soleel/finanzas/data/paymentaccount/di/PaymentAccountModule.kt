package com.soleel.finanzas.data.paymentaccount.di

import com.soleel.finanzas.data.paymentaccount.PaymentAccountRepository
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface PaymentAccountModule {

    @Singleton
    @Binds
    fun bindPaymentAccountLocalRepository(localRepository: PaymentAccountRepository) : IPaymentAccountLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindTransactionRemoteDataSource(remoteRepository: PaymentAccountRepository): IPaymentAccountRemoteDataSource

}