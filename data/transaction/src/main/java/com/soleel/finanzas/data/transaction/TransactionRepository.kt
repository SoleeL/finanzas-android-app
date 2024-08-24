package com.soleel.finanzas.data.transaction

import com.soleel.finanzas.core.common.enums.SynchronizationEnum
import com.soleel.finanzas.core.database.daos.TransactionDAO
import com.soleel.finanzas.core.database.entities.TransactionEntity
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.data.transaction.di.DefaultDispatcher
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class TransactionRepository @Inject constructor(
    private val transactionDAO: TransactionDAO,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ITransactionLocalDataSource {

    override fun getTransaction(transactionId: String): Flow<Transaction?> {
        return transactionDAO
            .getTransactionForTransactionId(transactionId)
            .map(transform = {it.toModel()})
    }

    override fun getTransactionWithForceUpdate(transactionId: String, forceUpdate: Boolean): Transaction? {
        TODO("Not yet implemented")
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDAO
            .getTransactions()
            .map(transform = {it.toModelList()})
    }

    override fun getTransactionsByCreatedOrder(): Flow<List<Transaction>> {
        return transactionDAO
            .getTransactionsByCreatedOrder()
            .map(transform =  { it.toModelList() })
    }

    override fun getTransactionsWithForceUpdate(forceUpdate: Boolean): List<Transaction> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransactions() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createTransaction(
        type: Int,
        category: Int,
        name: String,
        date: Long,
        amount: Int,
        accountId: String
    ): String {

        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            }
        )

        val transaction = TransactionEntity(
            id = id,
            type = type,
            category = category,
            name = name,
            date = date,
            amount = amount,
            accountId = accountId,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            isDeleted = false,
            synchronization = SynchronizationEnum.PENDING.id
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                transactionDAO.insert((transaction))
            }
        )

        return id
    }

    override suspend fun updateTransaction(
        transactionName: String,
        transactionAmount: Int,
        transactionDescription: String,
        transactionCreateAt: Long,
        accountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions(accountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }
}