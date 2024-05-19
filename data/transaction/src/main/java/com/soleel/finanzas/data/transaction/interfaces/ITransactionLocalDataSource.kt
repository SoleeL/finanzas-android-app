package com.soleel.finanzas.data.transaction.interfaces

import com.soleel.finanzas.data.transaction.model.Transaction
import kotlinx.coroutines.flow.Flow

interface ITransactionLocalDataSource {

    fun getTransaction(transactionId: String): Flow<Transaction?>

    fun getTransactionWithForceUpdate(transactionId: String, forceUpdate: Boolean = false): Transaction?

    fun getTransactions(): Flow<List<Transaction>>

    fun getTransactionsWithForceUpdate(forceUpdate: Boolean = false): List<Transaction>

    suspend fun refreshTransactions()

    suspend fun refreshTransaction(transactionId: String)

    suspend fun createTransaction(
        name: String,
        amount: Int,
        transactionType: Int,
        transactionCategory: Int,
        paymentAccountId: String
    ): String

    suspend fun updateTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        paymentAccountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    )

    suspend fun deleteAllTransactions(paymentAccountId: String)

    suspend fun deleteTransaction(transactionId: String)
}