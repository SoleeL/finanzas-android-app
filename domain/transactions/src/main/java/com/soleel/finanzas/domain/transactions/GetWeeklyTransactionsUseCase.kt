package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.model.SummaryTransaction
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithAccount
import com.soleel.finanzas.core.model.TransactionsSum
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import com.soleel.finanzas.domain.transactions.utils.mapToWithAccount
import com.soleel.finanzas.domain.transactions.utils.reverseOrder
import com.soleel.finanzas.domain.transactions.utils.sumExpenditure
import com.soleel.finanzas.domain.transactions.utils.sumIncome
import com.soleel.finanzas.domain.transactions.utils.toExpenditureNameTransactionWeek
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionDay
import com.soleel.finanzas.domain.transactions.utils.toIncomeNameTransactionWeek
import com.soleel.finanzas.domain.transactions.utils.toWeekDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeeklyTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource
) {
    // Listado de los ingresos y gastos semanales
    operator fun invoke(): Flow<List<TransactionsSum>> = transactionRepository.getTransactions()
        .reverseOrder()
        .mapToSumByWeek()
}

private fun Flow<List<Transaction>>.mapToSumByWeek(): Flow<List<TransactionsSum>> {
    return this.map(transform = { transactions ->
        transactions
            .groupBy(keySelector = { it.createAt.toWeekDate() })
            .map(transform = { (weekDate, weekTransactions) ->
                TransactionsSum(
                    date = weekDate,
                    income = SummaryTransaction(
                        name = weekDate.toIncomeNameTransactionWeek(),
                        amount = weekTransactions.sumIncome(),
                    ),
                    expenditure = SummaryTransaction(
                        name = weekDate.toExpenditureNameTransactionWeek(),
                        amount = weekTransactions.sumExpenditure(),
                    )
                )
            })
    })
}