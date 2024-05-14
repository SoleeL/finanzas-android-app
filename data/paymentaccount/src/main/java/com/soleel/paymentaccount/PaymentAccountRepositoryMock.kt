package com.soleel.paymentaccount

import com.soleel.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.paymentaccount.model.PaymentAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PaymentAccountRepositoryMock : IPaymentAccountLocalDataSource {
    override fun getPaymentAccount(paymentAccountId: String): Flow<PaymentAccount?> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithForceUpdate(
        paymentAccountId: String,
        forceUpdate: Boolean
    ): PaymentAccount? {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccounts(): Flow<List<PaymentAccount>> {
        return flowOf(
            listOf(
                PaymentAccount(
                    id = "1",
                    name = "Account 1",
                    amount = 1000,
                    createAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                    accountType = 1
                ),
                PaymentAccount(
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

    override fun getPaymentAccountsWithForceUpdate(forceUpdate: Boolean): List<PaymentAccount> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithTotalAmount(paymentAccountId: String): Flow<PaymentAccount?> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountsWithTotalAmount(): Flow<List<PaymentAccount>> {
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