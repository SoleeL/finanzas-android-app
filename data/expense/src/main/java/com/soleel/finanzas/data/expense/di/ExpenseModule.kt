package com.soleel.finanzas.data.expense.di

import com.soleel.finanzas.data.expense.ExpenseRepository
import com.soleel.finanzas.data.expense.interfaces.IExpenseLocalDataSource
import com.soleel.finanzas.data.expense.interfaces.ITransactionLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface ExpenseModule {

    @Singleton
    @Binds
    fun bindExpenseLocalRepository(localRepository: ExpenseRepository): IExpenseLocalDataSource

//    @Singleton
//    @Binds
//    abstract fun bindExpenseRemoteDataSource(remoteRepository: ExpenseRepository): IExpenseRemoteDataSource

}