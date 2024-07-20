package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionSummary
import com.soleel.finanzas.core.model.TransactionsSummary
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
    operator fun invoke(): Flow<List<TransactionsSummary>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByMonthsOfYear()
}

private fun Flow<List<Transaction>>.mapToSummaryByMonthsOfYear(): Flow<List<TransactionsSummary>> {
    return this.map(transform = {transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toMonthDate() })
            .map(transform = { (monthDate, monthTransactions) ->
                TransactionsSummary(
                    date = monthDate,
                    transactions = listOf(
                        TransactionSummary(
                            name = monthDate.toIncomeNameTransactionMonth(),
                            amount = monthTransactions.summaryIncome(),
                            type = TransactionTypeEnum.INCOME
                        ),
                        TransactionSummary(
                            name = monthDate.toExpenditureNameTransactionMonth(),
                            amount = monthTransactions.summaryExpenditure(),
                            type = TransactionTypeEnum.EXPENDITURE
                        )
                    )
                )
            })
    })
}