package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithExpenseInfoEntity
import com.soleel.finanzas.core.model.base.Account
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun AccountEntity.toModel(): Account {
    return Account(
        id = this.id,
        type = AccountTypeEnum.fromId(id = this.type),
        name = this.name,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.createdAt), ZoneId.systemDefault()),
        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault()),
        isDeleted = this.isDeleted,
        synchronization = SynchronizationEnum.fromId(this.synchronization)
    )
}

fun List<AccountEntity>.toModelList(): List<Account> {
    return this.map(transform = { it.toModel() })
}

fun AccountWithExpenseInfoEntity.toModel(): Account {
    return Account(
        id = this.accountEntity.id,
        type = AccountTypeEnum.fromId(id = this.accountEntity.type),
        name = this.accountEntity.name,
        transactionsNumber = this.expensesNumber,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.createdAt), ZoneId.systemDefault()),
        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.updatedAt), ZoneId.systemDefault()),
        isDeleted = this.accountEntity.isDeleted,
        synchronization = SynchronizationEnum.fromId(this.accountEntity.synchronization)
    )
}

fun List<AccountWithExpenseInfoEntity>.toWithTotalAmountModelList(): List<Account> {
    return this.map(transform = { it.toModel() })
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = this.id,
        type = this.type.id,
        name = this.name,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        isDeleted = this.isDeleted,
        synchronization = SynchronizationEnum.PENDING.id
    )
}
