package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionSummary
import com.soleel.finanzas.core.model.TransactionsSummary
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toDayDate
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionDay
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionDay
import com.soleel.finanzas.domain.transactions.utils.toWeekDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDaysOfWeekTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos diarios
    operator fun invoke(): Flow<List<TransactionsSummary>> =
        transactionRepository.getTransactionsByCreatedOrder()
            .mapToSummaryByDaysOfWeek()
}

private fun Flow<List<Transaction>>.mapToSummaryByDaysOfWeek(): Flow<List<TransactionsSummary>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toWeekDate() })
            .map(transform = { (weekDate, weekTransactions) ->
                TransactionsSummary(
                    date = weekDate,
                    transactions = weekTransactions
                        .groupBy(keySelector = { it.createAt.toDayDate() })
                        .flatMap(transform = { (dayDate, dayTransactions) ->
                            listOf(
                                TransactionSummary(
                                    name = dayDate.toIncomeNameTransactionDay(),
                                    amount = dayTransactions.summaryIncome(),
                                    type = TransactionTypeEnum.INCOME
                                ),
                                TransactionSummary(
                                    name = dayDate.toExpenditureNameTransactionDay(),
                                    amount = dayTransactions.summaryExpenditure(),
                                    type = TransactionTypeEnum.EXPENDITURE
                                )
                            )
                        })
                )
            })
    })
}