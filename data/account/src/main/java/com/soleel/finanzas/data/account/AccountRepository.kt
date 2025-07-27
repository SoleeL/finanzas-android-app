package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.database.daos.AccountDAO
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.data.account.di.DefaultDispatcher
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


class AccountRepository @Inject constructor(
    private val AccountDAO: AccountDAO,
    // private val AccountNetwork: AccountNetwork,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : IAccountLocalDataSource {

    override fun getAccount(accountId: String): Flow<Account?> {
        return AccountDAO.getAccountById(accountId).map(transform =  { it.toModel() })
    }

    override fun getAccountWithForceUpdate(accountId: String, forceUpdate: Boolean): Account? {
        TODO("Not yet implemented")
    }

    override fun getAccounts(): Flow<List<Account>> {
        return AccountDAO.getAllAccount().map(transform =  { it.toModelList() })
    }

    override fun getAccountsWithForceUpdate(forceUpdate: Boolean): List<Account> {
        TODO("Not yet implemented")
    }

    override fun getAccountWithTransactionInfo(accountId: String): Flow<Account?> {
        TODO("Not yet implemented")
//        return AccountDAO.getAccountByIdWithTotalsAmount(id = accountId).map(transform = { it.toModel() })
    }

    override fun getAccountsWithTransactionInfo(): Flow<List<Account>> {
        TODO("Not yet implemented")
        return AccountDAO.getAccountsWithTransactionalInfo().map(transform =  { it.toWithTotalAmountModelList() })
    }

    override suspend fun refreshAccounts() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccount(accountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAccount(
        name: String,
        amount: Int,
        type: AccountTypeEnum
    ): String {
        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            })

        val account = Account(
            id = id,
            type = type,
            name = name,
            totalAmount = amount,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isDeleted = false,
            synchronization = SynchronizationEnum.PENDING
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                AccountDAO.insert(account.toEntity())
            }
        )

        return id
    }

    override suspend fun updateAccount(
        name: String,
        createdAt: Long,
        initialAmount: Int,
        accountType: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(accountId: String) {
        TODO("Not yet implemented")
    }
}