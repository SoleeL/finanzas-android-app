package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.database.daos.AccountDAO
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.account.di.DefaultDispatcher
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.Date
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

    override fun getAccountWithTotalAmount(accountId: String): Flow<Account?> {
        return AccountDAO.getAccountByIdWithTotalsAmount(id = accountId).map(transform =  { it.toModel() })
    }

    override fun getAccountsWithTotalAmount(): Flow<List<Account>> {
        return AccountDAO.getAccountsWithTotalsAmount().map(transform =  { it.toWithTotalAmountModelList() })
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
            amount = amount,
            createdAt = Date(),
            updatedAt = Date(),
            isDeleted = false
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