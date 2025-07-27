package com.soleel.finanzas.domain.transactions

//class GetDaysOfWeekTransactionsUseCase @Inject constructor(
//    private val transactionRepository: ITransactionLocalDataSource
//) {
//    operator fun invoke(): Flow<List<TransactionsSummary>> =
//        transactionRepository.getTransactions()
//            .mapToSummaryByDaysOfWeek()
//}
//
//private fun Flow<List<Transaction>>.mapToSummaryByDaysOfWeek(): Flow<List<TransactionsSummary>> {
//    return this.map(transform = { transactions ->
//        transactions
//            .groupBy(keySelector = { it.date.toWeekDate() })
//            .map(transform = { (weekDate, weekTransactions) ->
//                TransactionsSummary(
//                    localDate = weekDate,
//                    dateName = weekDate.toNameDaysOfWeek(),
//                    transactions = weekTransactions
//                        .groupBy(keySelector = { it.date.toDayDate() })
//                        .flatMap(transform = { (dayDate, dayTransactions) ->
//                            listOf(
//                                TransactionSummary(
//                                    name = dayDate.toNameTransactionDay(),
//                                    amount = dayTransactions.summaryIncome(),
//                                    type = TransactionTypeEnum.INCOME
//                                ),
//                                TransactionSummary(
//                                    name = dayDate.toNameTransactionDay(),
//                                    amount = dayTransactions.summaryExpenditure(),
//                                    type = TransactionTypeEnum.EXPENDITURE
//                                )
//                            )
//                        })
//                )
//            })
//            .sortedByDescending(selector = { it.localDate} )
//    })
//}