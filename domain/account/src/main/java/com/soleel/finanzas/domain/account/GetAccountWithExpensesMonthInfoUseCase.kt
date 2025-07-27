package com.soleel.finanzas.domain.account

import com.soleel.finanzas.core.model.AccountWithExpensesMonthInfo
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountWithExpensesMonthInfoUseCase @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
    private val transactionRepository: ITransactionLocalDataSource,
) {
    operator fun invoke(): Flow<List<AccountWithExpensesMonthInfo>> = accountRepository.getAccounts()
        .mapToWithExpenses(transactionRepository.getTransactions())
        .mapToMonthInfo()
}

//class GetWeeklyOfMonthTransactionsUseCase @Inject constructor(
//    private val transactionRepository: ITransactionLocalDataSource,
//) {
//    operator fun invoke(): Flow<List<TransactionsSummary>> = transactionRepository.getTransactions()
//        .mapToSummaryByWeeksOfMonth()
//}
//
//private fun Flow<List<SurfaceControl.Transaction>>.mapToSummaryByWeeksOfMonth(): Flow<List<TransactionsSummary>> {
//    return this.map(transform = { transactions ->
//        transactions
//            .groupBy(keySelector = { it.date.toMonthDate() })
//            .map(transform = { (monthDate, monthTransactions) ->
//                TransactionsSummary(
//                    localDate = monthDate,
//                    dateName = monthDate.toNameWeekOfMonth(),
//                    transactions = monthTransactions
//                        .groupBy(keySelector = { it.date.toWeekDate() })
//                        .flatMap(transform = { (weekDate, weekTransactions) ->
//                            listOf(
//                                TransactionSummary(
//                                    name = weekDate.toNameTransactionWeek(),
//                                    amount = weekTransactions.summaryIncome(),
//                                    type = TransactionTypeEnum.INCOME
//                                ),
//                                TransactionSummary(
//                                    name = weekDate.toNameTransactionWeek(),
//                                    amount = weekTransactions.summaryExpenditure(),
//                                    type = TransactionTypeEnum.EXPENDITURE
//                                )
//                            )
//                        })
//                )
//            })
//            .sortedByDescending(selector = { it.localDate })
//    })
//}