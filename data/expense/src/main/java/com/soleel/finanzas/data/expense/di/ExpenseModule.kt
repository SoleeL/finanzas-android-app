package com.soleel.finanzas.data.expense.di

import com.soleel.finanzas.data.expense.ExpenseRepositoryImpl
import com.soleel.finanzas.data.expense.interfaces.IExpenseRepository
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
    fun bindExpenseLocalRepository(localRepository: ExpenseRepositoryImpl): IExpenseRepository

//    @Singleton
//    @Binds
//    abstract fun bindExpenseRemoteDataSource(remoteRepository: ExpenseRepository): IExpenseRemoteDataSource

}