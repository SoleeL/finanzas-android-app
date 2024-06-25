package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.Date
import javax.inject.Inject

class GetAllTransactionsWithAccountUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val accountRepository: IAccountLocalDataSource,
) {
    operator fun invoke(): Flow<List<TransactionWithAccount>> = transactionRepository.getTransactions()
        .mapToWithAccount(accounts = accountRepository.getAccounts())
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
                    val account: Account? =
                        accounts.find(predicate = { it.id == transaction.accountId })
                    TransactionWithAccount(
                        transaction = transaction,
                        account = account ?: Account(
                            id = "",
                            name = "null",
                            amount = 0,
                            createAt = Date(),
                            updatedAt = Date(),
                            type = AccountTypeEnum.CREDIT
                        )
                    )
                }
            )
        }
    )
}