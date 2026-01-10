package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.database.daos.AccountDAO
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.model.base.AccountDto
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


class AccountRepositoryImpl @Inject constructor(
    private val accountDAO: AccountDAO,
) : IAccountRepository {

    /* -------------------- CREATE ----------------------------------- */

    override suspend fun createAccount(account: AccountDto): UUID {
        account.id = UUID.randomUUID()
        val now: LocalDateTime = LocalDateTime.now()
        account.createdAt = now
        account.updatedAt = now

        val accountEntity: AccountEntity = account.toEntity()

        accountDAO.insert(entity = accountEntity)
        return accountEntity.id
    }

    /* -------------------- READ ------------------------------------- */

    override suspend fun getAccountsCount(): Int {
        return accountDAO.getAccountsCount()
    }

    override suspend fun getAccountsNotDeletedCount(): Int {
        return accountDAO.getAccountsNotDeletedCount()
    }

    override suspend fun getAccounts(): List<AccountDto> {
        return accountDAO.getAccounts().toDtoList()
    }

    override suspend fun getAccount(accountId: UUID): AccountDto? {
        return accountDAO.getAccount(id = accountId)?.toDto()
    }

    override fun getAccountsFlow(): Flow<List<AccountDto>> {
        return accountDAO.getAccountsFlow().map(transform = { it.toDtoList() })
    }

    override fun getAccountFlow(accountId: UUID): Flow<AccountDto?> {
        return accountDAO.getAccountFlow(id = accountId).map(transform = { it?.toDto() })
    }

    /* -------------------- UPDATE ----------------------------------- */
    override suspend fun updateAccount(account: AccountDto) {
        accountDAO.update(entity = account.toEntity())
    }

    /* -------------------- DELETE ----------------------------------- */
    override suspend fun deleteAccount(account: AccountDto) {
        accountDAO.delete(entity = account.toEntity())
    }
}