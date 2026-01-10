package com.soleel.finanzas.data.account.interfaces


import com.soleel.finanzas.core.model.base.AccountDto
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface IAccountRepository {

    // Create
    suspend fun createAccount(account: AccountDto): UUID

    // Read
    suspend fun getAccountsCount(): Int
    suspend fun getAccountsNotDeletedCount(): Int
    suspend fun getAccounts(): List<AccountDto>
    suspend fun getAccount(accountId: UUID): AccountDto?

    fun getAccountsFlow(): Flow<List<AccountDto>>
    fun getAccountFlow(accountId: UUID): Flow<AccountDto?>

    // Update
    suspend fun updateAccount(account: AccountDto)

    // Delete
    suspend fun deleteAccount(account: AccountDto)

}