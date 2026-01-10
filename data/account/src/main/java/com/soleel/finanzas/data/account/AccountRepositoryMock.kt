package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AccountRepositoryMock : IAccountRepository {
    override suspend fun createAccount(account: AccountEntity): UUID {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountsCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountsNotDeletedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAccounts(): List<AccountEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccount(accountId: UUID): AccountEntity? {
        TODO("Not yet implemented")
    }

    override fun getAccountsFlow(): Flow<List<AccountEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAccountFlow(accountId: UUID): Flow<AccountEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(account: AccountEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(account: AccountEntity) {
        TODO("Not yet implemented")
    }


}