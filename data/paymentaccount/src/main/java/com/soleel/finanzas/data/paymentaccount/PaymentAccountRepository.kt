package com.soleel.finanzas.data.paymentaccount

import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.database.daos.PaymentAccountDAO
import com.soleel.finanzas.core.model.PaymentAccount
import com.soleel.finanzas.data.paymentaccount.di.DefaultDispatcher
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID
import javax.inject.Inject


class PaymentAccountRepository @Inject constructor(
    private val paymentAccountDAO: PaymentAccountDAO,
    // private val paymentAccountNetwork: PaymentAccountNetwork,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : IPaymentAccountLocalDataSource {

    override fun getPaymentAccount(paymentAccountId: String): Flow<PaymentAccount?> {
        return paymentAccountDAO.getPaymentAccountById(paymentAccountId).map(transform =  { it.toModel() })
    }

    override fun getPaymentAccountWithForceUpdate(paymentAccountId: String, forceUpdate: Boolean): PaymentAccount? {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccounts(): Flow<List<PaymentAccount>> {
        return paymentAccountDAO.getAllPaymentAccount().map(transform =  { it.toModelList() })
    }

    override fun getPaymentAccountsWithForceUpdate(forceUpdate: Boolean): List<PaymentAccount> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithTotalAmount(paymentAccountId: String): Flow<PaymentAccount?> {
        return paymentAccountDAO.getPaymentAccountByIdWithTotalsAmount(paymentAccountId).map(transform =  { it.toModel() })
    }

    override fun getPaymentAccountsWithTotalAmount(): Flow<List<PaymentAccount>> {
        return paymentAccountDAO.getPaymentAccountsWithTotalsAmount().map(transform =  { it.toWithTotalAmountModelList() })
    }

    override suspend fun refreshPaymentAccounts() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPaymentAccount(paymentAccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createPaymentAccount(
        name: String,
        amount: Int,
        type: PaymentAccountTypeEnum
    ): String {
        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            })

        val paymentAccount = PaymentAccount(
            id = id,
            name = name,
            amount = amount,
            createAt = Date(),
            updatedAt = Date(),
            type = type
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                paymentAccountDAO.insert(paymentAccount.toEntity())
            }
        )

        return id
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