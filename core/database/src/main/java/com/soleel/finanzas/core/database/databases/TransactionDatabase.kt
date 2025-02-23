package com.soleel.finanzas.core.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soleel.finanzas.core.database.daos.AccountDAO

import com.soleel.finanzas.core.database.daos.TransactionDAO
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.entities.TransactionEntity


@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun accountDAO(): AccountDAO
    abstract fun transactionDAO(): TransactionDAO

}