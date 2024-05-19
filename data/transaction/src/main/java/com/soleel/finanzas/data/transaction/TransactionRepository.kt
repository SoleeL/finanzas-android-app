package com.soleel.finanzas.data.transaction

import com.soleel.finanzas.core.database.daos.TransactionDAO
import com.soleel.finanzas.core.database.entities.TransactionEntity
import com.soleel.finanzas.data.transaction.di.DefaultDispatcher
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.data.transaction.model.Transaction
import com.soleel.finanzas.data.transaction.model.TransactionDbModel
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
            .map(transform = TransactionDbModel::asExternalModel)
    }

    override fun getTransactionWithForceUpdate(transactionId: String, forceUpdate: Boolean): Transaction? {
        TODO("Not yet implemented")
    }

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDAO
            .getAllTransaction()
            .map(transform = TransactionDbModel::asExternalModelList)
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
        name: String,
        amount: Int,
        transactionType: Int,
        transactionCategory: Int,
        paymentAccountId: String
    ): String {

        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            })

        val transaction = TransactionEntity(
            id = id,
            name = name,
            amount = amount,
            createAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            transactionType = transactionType,
            categoryType = transactionCategory,
            paymentAccountId = paymentAccountId
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
        paymentAccountId: Int,
        typeTransactionId: Int,
        categoryId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTransactions(paymentAccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(transactionId: String) {
        TODO("Not yet implemented")
    }
}