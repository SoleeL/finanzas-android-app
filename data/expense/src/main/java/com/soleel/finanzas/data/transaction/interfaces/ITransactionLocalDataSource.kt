package com.soleel.finanzas.data.transaction.interfaces

import com.soleel.finanzas.core.model.base.Expense
import kotlinx.coroutines.flow.Flow

interface ITransactionLocalDataSource {

    fun getTransaction(transactionId: String): Flow<Expense?>

    fun getTransactionWithForceUpdate(transactionId: String, forceUpdate: Boolean = false): Expense?

    fun getTransactions(): Flow<List<Expense>>

    fun getTransactionsWithForceUpdate(forceUpdate: Boolean = false): List<Expense>

    suspend fun refreshTransactions()

    suspend fun refreshTransaction(transactionId: String)

    suspend fun createTransaction(
        type: Int,
        category: Int,
        name: String,
        date: Long,
        amount: Int,
        accountId: String
    ): String

    suspend fun updateTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        accountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    )

    suspend fun deleteAllTransactions(accountId: String)

    suspend fun deleteTransaction(transactionId: String)
}