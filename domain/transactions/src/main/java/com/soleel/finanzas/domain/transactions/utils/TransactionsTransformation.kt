package com.soleel.finanzas.domain.transactions.utils

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.Date

fun Flow<List<Transaction>>.reverseOrder(): Flow<List<Transaction>> {
    return this.map(transform = { transactions ->
        transactions.sortedByDescending(selector = { it.createAt })
    })
}

fun Flow<List<Transaction>>.mapToWithAccount(
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
                        name = "null",
                        amount = 0,
                        createAt = Date(),
                        updatedAt = Date(),
                        type = AccountTypeEnum.CREDIT
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

fun List<Transaction>.sumIncome(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.INCOME == it.type) it.amount else 0 }
)

fun List<Transaction>.sumExpenditure(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.EXPENDITURE == it.type) it.amount else 0 }
)