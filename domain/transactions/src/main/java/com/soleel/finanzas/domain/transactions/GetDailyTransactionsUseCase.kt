package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.model.TransactionsSum
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.mapToWithAccount
import com.soleel.finanzas.domain.transactions.utils.reverseOrder
import com.soleel.finanzas.domain.transactions.utils.sumExpenditure
import com.soleel.finanzas.domain.transactions.utils.sumIncome
import com.soleel.finanzas.domain.transactions.utils.toDayDate
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionDay
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetDailyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos diarios
    operator fun invoke(): Flow<List<TransactionsSum>> = transactionRepository.getTransactions()
        .reverseOrder()
        .mapToSumByDay()
}

fun Flow<List<Transaction>>.mapToSumByDay(): Flow<List<TransactionsSum>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toDayDate() })
            .map(transform = { (dayDate, dayTransactions) ->
                TransactionsSum(
                    date = dayDate,
                    income = SummaryTransaction(
                        name = dayDate.toIncomeNameTransactionDay(),
                        amount = dayTransactions.sumIncome()
                    ),
                    expenditure = SummaryTransaction(
                        name = dayDate.toExpenditureNameTransactionDay(),
                        amount = dayTransactions.sumExpenditure(),
                    )
                )
            })
    })
}

