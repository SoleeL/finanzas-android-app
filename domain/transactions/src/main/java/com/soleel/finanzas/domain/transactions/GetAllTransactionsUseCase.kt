package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.SynchronizationEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.model.TransactionsGroup
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.toDayDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val accountRepository: IAccountLocalDataSource,
) {
    // Listado de transacciones agrupadas por dia/fecha
    operator fun invoke(): Flow<List<TransactionsGroup>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToWithAccount(accounts = accountRepository.getAccounts())
        .mapToGroupByDay()
}

private fun Flow<List<Transaction>>.mapToWithAccount(
    accounts: Flow<List<Account>>
): Flow<List<TransactionWithAccount>> {
    return combine(
        flow = this,
        flow2 = accounts,
        transform = { transactions, accounts ->
            transactions.map(
                transform = { transaction ->
                    val account = accounts.find(predicate = { it.id == transaction.accountId })

                    val accountNotFind = Account(
                        id = "",
                        type = AccountTypeEnum.CREDIT,
                        name = "null",
                        amount = 0,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        isDeleted = false,
                        synchronization = SynchronizationEnum.PENDING
                    )

                    TransactionWithAccount(
                        transaction = transaction,
                        account = account ?: accountNotFind
                    )
                }
            )
        }
    )
}

private fun Flow<List<TransactionWithAccount>>.mapToGroupByDay(): Flow<List<TransactionsGroup>> {
    return this.map(transform = { transactionsWithAccount ->
        transactionsWithAccount
            .groupBy(keySelector = { it.transaction.date.toDayDate() })
            .map(transform = { (localDate, dailyTransactionsWithAccount) ->
                TransactionsGroup(
                    localDate = localDate,
                    transactionsWithAccount = dailyTransactionsWithAccount
                )
            })
    })
}