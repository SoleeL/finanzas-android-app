package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionSummary
import com.soleel.finanzas.core.model.TransactionsSummary
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toMonthDate
import com.soleel.finanzas.domain.transactions.utils.toNameTransactionWeek
import com.soleel.finanzas.domain.transactions.utils.toNameWeekOfMonth
import com.soleel.finanzas.domain.transactions.utils.toWeekDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyOfMonthTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    operator fun invoke(): Flow<List<TransactionsSummary>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByWeeksOfMonth()
}

private fun Flow<List<Transaction>>.mapToSummaryByWeeksOfMonth(): Flow<List<TransactionsSummary>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.date.toMonthDate() })
            .map(transform = { (monthDate, monthTransactions) ->
                TransactionsSummary(
                    localDate = monthDate,
                    dateName = monthDate.toNameWeekOfMonth(),
                    transactions = monthTransactions
                        .groupBy(keySelector = { it.date.toWeekDate() })
                        .flatMap(transform = { (weekDate, weekTransactions) ->
                            listOf(
                                TransactionSummary(
                                    name = weekDate.toNameTransactionWeek(),
                                    amount = weekTransactions.summaryIncome(),
                                    type = TransactionTypeEnum.INCOME
                                ),
                                TransactionSummary(
                                    name = weekDate.toNameTransactionWeek(),
                                    amount = weekTransactions.summaryExpenditure(),
                                    type = TransactionTypeEnum.EXPENDITURE
                                )
                            )
                        })
                )
            })
            .sortedByDescending(selector = { it.localDate })
    })
}