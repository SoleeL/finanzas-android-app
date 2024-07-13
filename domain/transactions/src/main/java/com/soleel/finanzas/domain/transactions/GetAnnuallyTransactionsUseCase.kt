package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionsSum
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.sumExpenditure
import com.soleel.finanzas.domain.transactions.utils.sumIncome
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionYear
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionYear
import com.soleel.finanzas.domain.transactions.utils.toYearDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAnnuallyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    operator fun invoke(): Flow<List<TransactionsSum>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSumByYear()
}

private fun Flow<List<Transaction>>.mapToSumByYear(): Flow<List<TransactionsSum>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toYearDate() })
            .map(transform = { (yearDate, yearTransactions) ->
                TransactionsSum(
                    date = yearDate,
                    income = SummaryTransaction(
                        name = yearDate.toIncomeNameTransactionYear(),
                        amount = yearTransactions.sumIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = yearDate.toExpenditureNameTransactionYear(),
                        amount = yearTransactions.sumExpenditure(),
                    )
                )
            })
    })
}