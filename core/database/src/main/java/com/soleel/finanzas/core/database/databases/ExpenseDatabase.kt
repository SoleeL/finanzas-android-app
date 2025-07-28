package com.soleel.finanzas.core.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soleel.finanzas.core.database.daos.AccountDAO

import com.soleel.finanzas.core.database.daos.ExpenseDAO
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.entities.ExpenseEntity


@Database(
    entities = [
        AccountEntity::class,
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun accountDAO(): AccountDAO
    abstract fun expenseDAO(): ExpenseDAO

}