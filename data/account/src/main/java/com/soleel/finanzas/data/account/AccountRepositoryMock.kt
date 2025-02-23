package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime

class AccountRepositoryMock : IAccountLocalDataSource {
    override fun getAccount(accountId: String): Flow<Account?> {
        TODO("Not yet implemented")
    }

    override fun getAccountWithForceUpdate(
        accountId: String,
        forceUpdate: Boolean
    ): Account? {
        TODO("Not yet implemented")
    }

    override fun getAccounts(): Flow<List<Account>> {
        return flowOf(
            listOf(
                Account(
                    id = "1",
                    type = AccountTypeEnum.CREDIT,
                    name = "Account 1",
                    totalAmount = 1000,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    isDeleted = false,
                    synchronization = SynchronizationEnum.PENDING
                ),
                Account(
                    id = "2",
                    type = AccountTypeEnum.DEBIT,
                    name = "Account 2",
                    totalAmount = 2000,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    isDeleted = false,
                    synchronization = SynchronizationEnum.PENDING
                )
            )
        )
    }

    override fun getAccountsWithForceUpdate(forceUpdate: Boolean): List<Account> {
        TODO("Not yet implemented")
    }

    override fun getAccountWithTransactionInfo(accountId: String): Flow<Account?> {
        TODO("Not yet implemented")
    }

    override fun getAccountsWithTransactionInfo(): Flow<List<Account>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccounts() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshAccount(accountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createAccount(name: String, amount: Int, type: AccountTypeEnum): String {
        TODO("Not yet implemented")
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