package com.soleel.finanzas.domain.account.di

import com.soleel.finanzas.domain.account.GetAccountsWithExpensesInfoCurrentMonthUseCase
import com.soleel.finanzas.domain.account.interfaces.IGetAccountsWithExpensesInfoCurrentMonthUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GetAccountsWithExpensesInfoCurrentMonthUseCaseModule {

    @Binds
    abstract fun bindGetAccountsWithExpensesInfoCurrentMonthUseCase(
        impl: GetAccountsWithExpensesInfoCurrentMonthUseCase
    ): IGetAccountsWithExpensesInfoCurrentMonthUseCase
}