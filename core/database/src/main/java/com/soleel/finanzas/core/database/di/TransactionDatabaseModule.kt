package com.soleel.finanzas.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.soleel.finanzas.core.database.daos.AccountDAO
import com.soleel.finanzas.core.database.daos.ExpenseDAO
import com.soleel.finanzas.core.database.databases.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExpenseDatabaseModule {

    @Singleton
    @Provides
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return Room.databaseBuilder(
            context,
            ExpenseDatabase::class.java,
            "expense_database.db"
        )
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Provides
    fun provideAccountDAO(database: ExpenseDatabase): AccountDAO = database.accountDAO()

    @Provides
    fun provideExpenseDAO(database: ExpenseDatabase): ExpenseDAO = database.expenseDAO()

}
