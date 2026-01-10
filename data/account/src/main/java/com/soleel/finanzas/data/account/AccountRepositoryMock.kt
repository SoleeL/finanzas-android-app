package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.model.base.AccountDto
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class AccountRepositoryMock : IAccountRepository {
    override suspend fun createAccount(account: AccountDto): UUID {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountsCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountsNotDeletedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAccounts(): List<AccountDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccount(accountId: UUID): AccountDto? {
        TODO("Not yet implemented")
    }

    override fun getAccountsFlow(): Flow<List<AccountDto>> {
        TODO("Not yet implemented")
    }

    override fun getAccountFlow(accountId: UUID): Flow<AccountDto?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateAccount(account: AccountDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(account: AccountDto) {
        TODO("Not yet implemented")
    }


}