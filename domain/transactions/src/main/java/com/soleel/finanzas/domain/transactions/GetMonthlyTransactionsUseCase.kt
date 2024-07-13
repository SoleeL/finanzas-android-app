package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.SummaryTransactions
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionMonth
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionMonth
import com.soleel.finanzas.domain.transactions.utils.toMonthDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMonthlyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos mensuales
    operator fun invoke(): Flow<List<SummaryTransactions>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByMonth()
}

private fun Flow<List<Transaction>>.mapToSummaryByMonth(): Flow<List<SummaryTransactions>> {
    return this.map(transform = {transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toMonthDate() })
            .map(transform = { (monthDate, monthTransactions) ->
                SummaryTransactions(
                    date = monthDate,
                    income = SummaryTransaction(
                        name = monthDate.toIncomeNameTransactionMonth(),
                        amount = monthTransactions.summaryIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = monthDate.toExpenditureNameTransactionMonth(),
                        amount = monthTransactions.summaryExpenditure(),
                    )
                )
            })
    })
}