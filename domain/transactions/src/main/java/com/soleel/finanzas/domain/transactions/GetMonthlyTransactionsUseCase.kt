package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionsSum
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.sumExpenditure
import com.soleel.finanzas.domain.transactions.utils.sumIncome
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
    operator fun invoke(): Flow<List<TransactionsSum>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSumByMonth()
}

private fun Flow<List<Transaction>>.mapToSumByMonth(): Flow<List<TransactionsSum>> {
    return this.map(transform = {transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toMonthDate() })
            .map(transform = { (monthDate, monthTransactions) ->
                TransactionsSum(
                    date = monthDate,
                    income = SummaryTransaction(
                        name = monthDate.toIncomeNameTransactionMonth(),
                        amount = monthTransactions.sumIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = monthDate.toExpenditureNameTransactionMonth(),
                        amount = monthTransactions.sumExpenditure(),
                    )
                )
            })
    })
}