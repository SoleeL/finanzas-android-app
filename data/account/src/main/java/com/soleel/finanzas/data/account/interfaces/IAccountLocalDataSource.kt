package com.soleel.finanzas.data.account.interfaces


import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import kotlinx.coroutines.flow.Flow


interface IAccountLocalDataSource {

    fun getAccount(accountId: String): Flow<Account?>

    fun getAccountWithForceUpdate(accountId: String, forceUpdate: Boolean = false): Account?

    fun getAccounts(): Flow<List<Account>>

    fun getAccountsWithForceUpdate(forceUpdate: Boolean = false): List<Account>

    fun getAccountWithTotalAmount(accountId: String): Flow<Account?>

    fun getAccountsWithTotalAmount(): Flow<List<Account>>

    suspend fun refreshAccounts()

    suspend fun refreshAccount(accountId: String)

    suspend fun createAccount(
        name: String,
        amount: Int,
        type: AccountTypeEnum
    ): String

    suspend fun updateAccount(
        name: String,
        createdAt: Long,
        initialAmount: Int,
        accountType: Int
    )

    suspend fun deleteAccount(accountId: String)

}