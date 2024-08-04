package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.SynchronizationEnum
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithTotalAmountEntity
import com.soleel.finanzas.core.model.Account
import java.util.Date


fun AccountEntity.toModel(): Account {
    return Account(
        id = this.id,
        type = AccountTypeEnum.fromId(id = this.type),
        name = this.name,
        createdAt = Date(this.createdAt),
        updatedAt = Date(this.updatedAt),
        isDeleted = this.isDeleted
    )
}

fun List<AccountEntity>.toModelList(): List<Account> {
    return this.map(transform = { it.toModel() })
}

fun AccountWithTotalAmountEntity.toModel(): Account {
    return Account(
        id = this.accountEntity.id,
        type = AccountTypeEnum.fromId(id = this.accountEntity.type),
        name = this.accountEntity.name,
        amount = this.totalIncome - this.totalExpense,
        createdAt = Date(this.accountEntity.createdAt),
        updatedAt = Date(this.accountEntity.updatedAt),
        isDeleted = this.accountEntity.isDeleted,
    )
}

fun List<AccountWithTotalAmountEntity>.toWithTotalAmountModelList(): List<Account> {
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
