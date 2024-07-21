package com.soleel.finanzas.data.account

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithTotalAmountEntity
import com.soleel.finanzas.core.model.Account
import java.util.Date


fun AccountEntity.toModel(): Account {
    return Account(
        id = this.id,
        name = this.name,
        createAt = Date(this.createAt),
        updatedAt = Date(this.updatedAt),
        type = AccountTypeEnum.fromId(id = this.type)
    )
}

fun List<AccountEntity>.toModelList(): List<Account> {
    return this.map(transform = { it.toModel() })
}

fun AccountWithTotalAmountEntity.toModel(): Account {
    return Account(
        id = this.accountEntity.id,
        name = this.accountEntity.name,
        amount = this.totalIncome - this.totalExpense,
        createAt = Date(this.accountEntity.createAt),
        updatedAt = Date(this.accountEntity.updatedAt),
        type = AccountTypeEnum.fromId(id = this.accountEntity.type)
    )
}

fun List<AccountWithTotalAmountEntity>.toWithTotalAmountModelList(): List<Account> {
    return this.map(transform = {it.toModel()})
}

fun Account.toEntity(): AccountEntity {
    return AccountEntity(
        id = this.id,
        name = this.name,
        createAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        type = this.type.id
    )
}
