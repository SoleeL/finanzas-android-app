package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.SummaryTransactions
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.summaryExpenditure
import com.soleel.finanzas.domain.transactions.utils.summaryIncome
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionYear
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionYear
import com.soleel.finanzas.domain.transactions.utils.toYearDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAnnuallyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    operator fun invoke(): Flow<List<SummaryTransactions>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByYear()
}

private fun Flow<List<Transaction>>.mapToSummaryByYear(): Flow<List<SummaryTransactions>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toYearDate() })
            .map(transform = { (yearDate, yearTransactions) ->
                SummaryTransactions(
                    date = yearDate,
                    income = SummaryTransaction(
                        name = yearDate.toIncomeNameTransactionYear(),
                        amount = yearTransactions.summaryIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = yearDate.toExpenditureNameTransactionYear(),
                        amount = yearTransactions.summaryExpenditure(),
                    )
                )
            })
    })
}