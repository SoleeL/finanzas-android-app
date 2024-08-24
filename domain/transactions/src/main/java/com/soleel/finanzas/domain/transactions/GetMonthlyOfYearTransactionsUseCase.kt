package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionSummary
import com.soleel.finanzas.core.model.TransactionsSummary
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toMonthDate
import com.soleel.finanzas.domain.transactions.utils.toNameMonthOfYear
import com.soleel.finanzas.domain.transactions.utils.toNameTransactionMonth
import com.soleel.finanzas.domain.transactions.utils.toYearDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlyOfYearTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    operator fun invoke(): Flow<List<TransactionsSummary>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByMonthsOfYear()
}

private fun Flow<List<Transaction>>.mapToSummaryByMonthsOfYear(): Flow<List<TransactionsSummary>> {
    return this.map(transform = {transactions ->
        transactions
            .groupBy(keySelector = { it.date.toYearDate()})
            .map(transform = { (yearDate, yearTractions) ->
                TransactionsSummary(
                    localDate = yearDate,
                    dateName = yearDate.toNameMonthOfYear(),
                    transactions = yearTractions
                        .groupBy(keySelector = { it.date.toMonthDate() })
                        .flatMap(transform =  { (monthDate, monthTransactions) ->
                            listOf(
                                TransactionSummary(
                                    name = monthDate.toNameTransactionMonth(),
                                    amount = monthTransactions.summaryIncome(),
                                    type = TransactionTypeEnum.INCOME
                                ),
                                TransactionSummary(
                                    name = monthDate.toNameTransactionMonth(),
                                    amount = monthTransactions.summaryExpenditure(),
                                    type = TransactionTypeEnum.EXPENDITURE
                                )
                            )
                        })
                )
            })
            .sortedByDescending(selector = { it.localDate })
    })
}