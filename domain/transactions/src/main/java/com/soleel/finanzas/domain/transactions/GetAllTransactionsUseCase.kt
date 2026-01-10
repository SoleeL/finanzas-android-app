package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import com.soleel.finanzas.data.expense.interfaces.IExpenseLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.toDayDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject



data class TransactionsGroup(
    val localDate: LocalDate, // Fecha del dia
    val transactionsWithAccount: List<TransactionWithAccount> // Todas las transacciones del dia
)

data class TransactionWithAccount(
    val expense: Expense,
    val account: AccountEntity
)


class GetAllTransactionsUseCase @Inject constructor(
    private val transactionRepository: IExpenseLocalDataSource,
    private val accountRepository: IAccountRepository,
) {
    // Listado de transacciones agrupadas por dia/fecha
    operator fun invoke(): Flow<List<TransactionsGroup>> = transactionRepository.getExpenses()
        .mapToWithAccount(accounts = accountRepository.getAccounts())
        .mapToGroupByDay()
}

private fun Flow<List<Expense>>.mapToWithAccount(
    accounts: Flow<List<AccountEntity>>
): Flow<List<TransactionWithAccount>> {
    return combine(
        flow = this,
        flow2 = accounts,
        transform = { transactions, accounts ->
            transactions.map(
                transform = { transaction ->
                    val account = accounts.find(predicate = { it.id == transaction.accountId })

                    val accountNotFind = AccountEntity(
                        id = "",
                        type = AccountTypeEnum.CREDIT,
                        name = "null",
                        totalAmount = 0,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        isDeleted = false,
                        synchronization = SynchronizationEnum.PENDING
                    )

                    TransactionWithAccount(
                        expense = transaction,
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
            .groupBy(keySelector = { it.expense.date.toDayDate() })
            .map(transform = { (localDate, dailyTransactionsWithAccount) ->
                TransactionsGroup(
                    localDate = localDate,
                    transactionsWithAccount = dailyTransactionsWithAccount
                )
            })
            .sortedByDescending(selector = { it.localDate })
    })
}