package com.soleel.finanzas.data.paymentaccount

import com.soleel.finanzas.core.database.daos.PaymentAccountDAO
import com.soleel.finanzas.data.paymentaccount.di.DefaultDispatcher
import com.soleel.finanzas.data.paymentaccount.interfaces.IPaymentAccountLocalDataSource
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.finanzas.data.paymentaccount.model.PaymentAccountDbModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class PaymentAccountRepository @Inject constructor(
    private val paymentAccountDAO: PaymentAccountDAO,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : IPaymentAccountLocalDataSource {

    override fun getPaymentAccount(paymentAccountId: String): Flow<PaymentAccount?> {
        return paymentAccountDAO
            .getPaymentAccountById(paymentAccountId)
            .map(transform = PaymentAccountDbModel::asExternalModel)
    }

    override fun getPaymentAccountWithForceUpdate(paymentAccountId: String, forceUpdate: Boolean): PaymentAccount? {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccounts(): Flow<List<PaymentAccount>> {
        return paymentAccountDAO
            .getAllPaymentAccount()
            .map(transform = PaymentAccountDbModel::asExternalModelList)
    }

    override fun getPaymentAccountsWithForceUpdate(forceUpdate: Boolean): List<PaymentAccount> {
        TODO("Not yet implemented")
    }

    override fun getPaymentAccountWithTotalAmount(paymentAccountId: String): Flow<PaymentAccount?> {
        return paymentAccountDAO
            .getPaymentAccountByIdWithTotalsAmount(paymentAccountId)
            .map(transform = PaymentAccountDbModel::asExternalModelWithTotalAmount)
    }

    override fun getPaymentAccountsWithTotalAmount(): Flow<List<PaymentAccount>> {
        return paymentAccountDAO
            .getPaymentAccountsWithTotalsAmount()
            .map(transform = PaymentAccountDbModel::asExternalModelWithTotalAmountList)
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
        accountType: Int
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
            createAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            accountType = accountType
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                paymentAccountDAO.insert(PaymentAccountDbModel.asInternalModel(paymentAccount))
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