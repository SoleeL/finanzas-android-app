package com.soleel.finanzas.data.account.interfaces


import com.soleel.finanzas.core.database.entities.AccountEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID


interface IAccountRepository {

    // Create
    suspend fun createAccount(account: AccountEntity): UUID

    // Read
    suspend fun getAccountsCount(): Int
    suspend fun getAccountsNotDeletedCount(): Int
    suspend fun getAccounts(): List<AccountEntity>
    suspend fun getAccount(accountId: UUID): AccountEntity?

    fun getAccountsFlow(): Flow<List<AccountEntity>>
    fun getAccountFlow(accountId: UUID): Flow<AccountEntity?>

    // Update
    suspend fun updateAccount(account: AccountEntity)

    // Delete
    suspend fun deleteAccount(account: AccountEntity)

}