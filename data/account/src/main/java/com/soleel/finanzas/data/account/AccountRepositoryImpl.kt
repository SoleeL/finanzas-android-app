package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.database.daos.AccountDAO
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val accountDAO: AccountDAO,
) : IAccountRepository {

    /* -------------------- CREATE ----------------------------------- */

    override suspend fun createAccount(account: AccountEntity): UUID {
        accountDAO.insert(entity = account)
        return account.id
    }

    /* -------------------- READ ------------------------------------- */

    override suspend fun getAccountsCount(): Int {
        return accountDAO.getAccountsCount()
    }

    override suspend fun getAccountsNotDeletedCount(): Int {
        return accountDAO.getAccountsNotDeletedCount()
    }

    override suspend fun getAccounts(): List<AccountEntity> {
        return accountDAO.getAccounts()
    }

    override suspend fun getAccount(accountId: UUID): AccountEntity? {
        return accountDAO.getAccount(id = accountId)
    }

    override fun getAccountsFlow(): Flow<List<AccountEntity>> {
        return accountDAO.getAccountsFlow()
    }

    override fun getAccountFlow(accountId: UUID): Flow<AccountEntity?> {
        return accountDAO.getAccountFlow(id = accountId)
    }

    /* -------------------- UPDATE ----------------------------------- */
    override suspend fun updateAccount(account: AccountEntity) {
        accountDAO.update(entity = account)
    }

    /* -------------------- DELETE ----------------------------------- */
    override suspend fun deleteAccount(account: AccountEntity) {
        accountDAO.delete(entity = account)
    }
}