package com.soleel.finanzas.domain.transactions

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAnuallyTransactionsWithPaymentAccountUseCase(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(): List<ArticleWithAuthor> = withContext(defaultDispatcher)
}