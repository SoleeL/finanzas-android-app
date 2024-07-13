package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.SummaryTransactions
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionWeek
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionWeek
import com.soleel.finanzas.domain.transactions.utils.toWeekDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos semanales
    operator fun invoke(): Flow<List<SummaryTransactions>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByWeek()
}

private fun Flow<List<Transaction>>.mapToSummaryByWeek(): Flow<List<SummaryTransactions>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toWeekDate() })
            .map(transform = { (weekDate, weekTransactions) ->
                SummaryTransactions(
                    date = weekDate,
                    income = SummaryTransaction(
                        name = weekDate.toIncomeNameTransactionWeek(),
                        amount = weekTransactions.summaryIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = weekDate.toExpenditureNameTransactionWeek(),
                        amount = weekTransactions.summaryExpenditure(),
                    )
                )
            })
    })
}