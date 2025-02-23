package com.soleel.finanzas.data.account.di

import com.soleel.finanzas.data.account.AccountRepository
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

    @Singleton
    @Binds
    fun bindAccountLocalRepository(localRepository: AccountRepository) : IAccountLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindTransactionRemoteDataSource(remoteRepository: AccountRepository): IAccountRemoteDataSource

}