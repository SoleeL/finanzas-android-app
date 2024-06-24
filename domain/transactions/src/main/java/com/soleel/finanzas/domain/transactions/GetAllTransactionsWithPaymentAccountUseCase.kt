package com.soleel.finanzas.domain.transactions

import com.soleel.finanzas.core.model.PaymentAccount
import com.soleel.finanzas.core.model.Transaction
import com.soleel.finanzas.core.model.TransactionWithPaymentAccount
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.transaction.interfaces.ITransactionLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAllTransactionsWithPaymentAccountUseCase @Inject constructor(
    private val transactionRepository: ITransactionLocalDataSource,
    private val paymentAccountRepository: IPaymentAccountLocalDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(): List<TransactionWithPaymentAccount> = withContext(
        context = defaultDispatcher,
        block = {
            transactionRepository.getTransactions()
                .mapToWithPaymentAccount(paymentAccounts = paymentAccountRepository.getPaymentAccounts())
                .first()
        }
    )
}

private fun Flow<List<Transaction>>.mapToWithPaymentAccount(
    paymentAccounts: Flow<List<PaymentAccount>>
): Flow<List<TransactionWithPaymentAccount>> {
    return combine(
        flow = this,
        flow2 = paymentAccounts,
        transform = { transactions, accounts ->
            transactions.map(
                transform = { transaction ->
                    val account = accounts.find(predicate = { it.id == transaction.paymentAccountId })
                    TransactionWithPaymentAccount(transaction, account)
                }
            )
        }
    )
}