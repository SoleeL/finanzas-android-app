package com.soleel.finanzas.data.account.interfaces


import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import kotlinx.coroutines.flow.Flow


interface IAccountLocalDataSource {

    fun getAccount(AccountId: String): Flow<Account?>

    fun getAccountWithForceUpdate(AccountId: String, forceUpdate: Boolean = false): Account?

    fun getAccounts(): Flow<List<Account>>

    fun getAccountsWithForceUpdate(forceUpdate: Boolean = false): List<Account>

    fun getAccountWithTotalAmount(AccountId: String): Flow<Account?>

    fun getAccountsWithTotalAmount(): Flow<List<Account>>

    suspend fun refreshAccounts()

    suspend fun refreshAccount(AccountId: String)

    suspend fun createAccount(
        name: String,
        amount: Int,
        type: AccountTypeEnum
    ): String

    suspend fun updateAccount(
        name: String,
        createAt: Long,
        initialAmount: Int,
        accountType: Int
    )

    suspend fun deleteAccount(AccountId: String)

}