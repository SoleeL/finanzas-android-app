package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.mapToWithAccount
import com.soleel.finanzas.domain.transactions.utils.reverseOrder
import com.soleel.finanzas.domain.transactions.utils.toDayDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val accountRepository: IAccountLocalDataSource,
) {
    // Listado de transacciones agrupadas por dia/fecha
    operator fun invoke(): Flow<List<TransactionsGroup>> = transactionRepository.getTransactions()
        .reverseOrder()
        .mapToWithAccount(accounts = accountRepository.getAccounts())
        .mapToGroupByDay()
}

fun Flow<List<TransactionWithAccount>>.mapToGroupByDay(): Flow<List<TransactionsGroup>> {
    return this.map(transform = { transactionsWithAccount ->
        transactionsWithAccount
            .groupBy(keySelector = { it.transaction.createAt.toDayDate() })
            .map(transform = { (localDate, dailyTransactionsWithAccount) ->
                TransactionsGroup(
                    date = localDate,
                    transactionsWithAccount = dailyTransactionsWithAccount
                )
            })
    })
}