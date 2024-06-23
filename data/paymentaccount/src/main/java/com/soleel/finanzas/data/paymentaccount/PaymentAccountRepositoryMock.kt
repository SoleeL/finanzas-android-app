package com.soleel.finanzas.data.paymentaccount

import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PaymentAccountRepositoryMock : IPaymentAccountLocalDataSource {
    override fun getPaymentAccount(paymentAccountId: String): Flow<com.soleel.finanzas.core.model.PaymentAccount?> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithForceUpdate(
        paymentAccountId: String,
        forceUpdate: Boolean
    ): com.soleel.finanzas.core.model.PaymentAccount? {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccounts(): Flow<List<com.soleel.finanzas.core.model.PaymentAccount>> {
        return flowOf(
            listOf(
                com.soleel.finanzas.core.model.PaymentAccount(
                    id = "1",
                    name = "Account 1",
                    amount = 1000,
                    createAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    accountType = 1
                ),
                com.soleel.finanzas.core.model.PaymentAccount(
                    id = "2",
                    name = "Account 2",
                    amount = 2000,
                    createAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    accountType = 2
                )
            )
        )
    }

    override fun getPaymentAccountsWithForceUpdate(forceUpdate: Boolean): List<com.soleel.finanzas.core.model.PaymentAccount> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithTotalAmount(paymentAccountId: String): Flow<com.soleel.finanzas.core.model.PaymentAccount?> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountsWithTotalAmount(): Flow<List<com.soleel.finanzas.core.model.PaymentAccount>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPaymentAccounts() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPaymentAccount(paymentAccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createPaymentAccount(name: String, amount: Int, accountType: Int): String {
        TODO("Not yet implemented")
    }

    override suspend fun updatePaymentAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePaymentAccount(paymentAccountId: String) {
        TODO("Not yet implemented")
    }


}