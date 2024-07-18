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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetDailyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos diarios
    operator fun invoke(): Flow<List<TransactionsSummary>> = transactionRepository.getTransactionsByCreatedOrder()
        .mapToSummaryByDay()
}

private fun Flow<List<Transaction>>.mapToSummaryByDay(): Flow<List<TransactionsSummary>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toDayDate() })
            .map(transform = { (dayDate, dayTransactions) ->
                TransactionsSummary(
                    date = dayDate,
                    transactions = listOf(
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
                )
            })
    })
}

