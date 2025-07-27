package com.soleel.finanzas.domain.stats

//
//class GetCategoryStatsUseCase @Inject constructor(
//    private val transactionRepository: ITransactionLocalDataSource
//) {
//    operator fun invoke(): Flow<Pair<List<Stat>, List<Stat>>> = transactionRepository.getTransactions()
//        .mapToCategoryStat()
//}
//
//private fun Flow<List<Transaction>>.mapToCategoryStat(): Flow<Pair<List<Stat>, List<Stat>>> {
//    return this.map(transform = { transactions ->
//        val statsByType: Map<ExpenseTypeEnum, List<Stat>> = transactions
//            .groupBy(keySelector = { it.type })
//            .mapValues(transform = { (transactionType, transactionsByType) ->
//                transactionsByType
//                    .groupBy(keySelector = { it.category })
//                    .map(transform = { (transactionCategory, transactionsByCategory) ->
//                        Stat(
//                            category = transactionCategory,
//                            amount = transactionsByCategory.sumOf(selector = { transaction: Transaction ->
//                                transaction.amount
//                            }),
//                            transactionNumber = transactionsByCategory.size
//                        )
//                    })
//            })
//
//        Pair<List<Stat>, List<Stat>>(
//            statsByType[TransactionTypeEnum.EXPENDITURE].orEmpty(),
//            statsByType[TransactionTypeEnum.INCOME].orEmpty()
//        )
//    })
//}