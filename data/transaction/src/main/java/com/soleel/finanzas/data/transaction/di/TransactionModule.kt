package com.soleel.finanzas.data.transaction.di

import com.soleel.finanzas.data.transaction.TransactionRepository
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface TransactionModule {

    @Singleton
    @Binds
    fun bindTransactionLocalRepository(localRepository: TransactionRepository): ITransactionLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindTransactionRemoteDataSource(remoteRepository: TransactionRepository): ITransactionRemoteDataSource

}