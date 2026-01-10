package com.soleel.finanzas.data.account.di

import com.soleel.finanzas.data.account.AccountRepositoryImpl
import com.soleel.finanzas.data.account.AccountRepositoryMock
import com.soleel.finanzas.data.account.BuildConfig
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AccountModule {

    @Provides
    fun provideAccountRepository(
        accountRepositoryMock: AccountRepositoryMock,
        accountRepositoryImpl: AccountRepositoryImpl
    ) : IAccountRepository {
        return if (BuildConfig.DEMO) {
            accountRepositoryMock
        } else {
            accountRepositoryImpl
        }
    }

}