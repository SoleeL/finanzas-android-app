package com.soleel.finanzas.core.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase

import com.soleel.finanzas.core.database.daos.PaymentAccountDAO
import com.soleel.finanzas.core.database.daos.TransactionDAO
import com.soleel.finanzas.core.database.entities.PaymentAccountEntity
import com.soleel.finanzas.core.database.entities.TransactionEntity


@Database(
    entities = [
        PaymentAccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun paymentAccountDAO(): PaymentAccountDAO
    abstract fun transactionDAO(): TransactionDAO

}