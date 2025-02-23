package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithTransactionInfoEntity
import com.soleel.finanzas.core.model.Account
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

fun AccountWithTransactionInfoEntity.toModel(): Account {
    return Account(
        id = this.accountEntity.id,
        type = AccountTypeEnum.fromId(id = this.accountEntity.type),
        name = this.accountEntity.name,
        totalIncome = this.totalIncome,
        totalExpense = this.totalExpense,
        totalAmount = this.totalIncome - this.totalExpense,
        transactionsNumber = this.transactionsNumber,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.createdAt), ZoneId.systemDefault()),
        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.accountEntity.updatedAt), ZoneId.systemDefault()),
        isDeleted = this.accountEntity.isDeleted,
        synchronization = SynchronizationEnum.fromId(this.accountEntity.synchronization)
    )
}

fun List<AccountWithTransactionInfoEntity>.toWithTotalAmountModelList(): List<Account> {
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
