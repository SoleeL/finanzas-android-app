package com.soleel.finanzas.data.paymentaccount.interfaces


import com.soleel.finanzas.core.model.PaymentAccount
import kotlinx.coroutines.flow.Flow


interface IPaymentAccountLocalDataSource {

    fun getPaymentAccount(paymentAccountId: String): Flow<PaymentAccount?>

    fun getPaymentAccountWithForceUpdate(paymentAccountId: String, forceUpdate: Boolean = false): PaymentAccount?

    fun getPaymentAccounts(): Flow<List<PaymentAccount>>

    fun getPaymentAccountsWithForceUpdate(forceUpdate: Boolean = false): List<PaymentAccount>

    fun getPaymentAccountWithTotalAmount(paymentAccountId: String): Flow<PaymentAccount?>

    fun getPaymentAccountsWithTotalAmount(): Flow<List<PaymentAccount>>

    suspend fun refreshPaymentAccounts()

    suspend fun refreshPaymentAccount(paymentAccountId: String)

    suspend fun createPaymentAccount(
        name: String,
        amount: Int,
        accountType: Int
    ): String

    suspend fun updatePaymentAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    )

    suspend fun deletePaymentAccount(paymentAccountId: String)

}