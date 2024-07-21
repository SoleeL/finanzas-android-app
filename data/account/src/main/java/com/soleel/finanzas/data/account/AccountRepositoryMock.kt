package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class AccountRepositoryMock : IAccountLocalDataSource {
    override fun getAccount(AccountId: String): Flow<Account?> {
        TODO("Not yet implemented")
    }

    override fun getAccountWithForceUpdate(
        AccountId: String,
        forceUpdate: Boolean
    ): Account? {
        TODO("Not yet implemented")
    }

    override fun getAccounts(): Flow<List<Account>> {
        return flowOf(
            listOf(
                Account(
                    id = "1",
                    name = "Account 1",
                    amount = 1000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.CREDIT
                ),
                Account(
                    id = "2",
                    name = "Account 2",
                    amount = 2000,
                    createAt = Date(),
                    updatedAt = Date(),
                    type = AccountTypeEnum.DEBIT
                )
            )
        )
    }

    override fun getAccountsWithForceUpdate(forceUpdate: Boolean): List<Account> {
        TODO("Not yet implemented")
    }

    override fun getAccountWithTotalAmount(AccountId: String): Flow<Account?> {
        TODO("Not yet implemented")
    }

    override fun getAccountsWithTotalAmount(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccounts() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccount(AccountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAccount(name: String, amount: Int, type: AccountTypeEnum): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(AccountId: String) {
        TODO("Not yet implemented")
    }


}