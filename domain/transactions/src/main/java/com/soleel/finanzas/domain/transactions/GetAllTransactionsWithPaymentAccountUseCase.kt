package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllTransactionsWithPaymentAccountUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(): List<AllTransactionsWithPaymentAccount> = withContext(defaultDispatcher) {

    }
}