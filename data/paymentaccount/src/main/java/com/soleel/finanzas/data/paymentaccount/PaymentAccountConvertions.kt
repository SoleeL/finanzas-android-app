package com.soleel.finanzas.data.paymentaccount

import com.soleel.finanzas.core.database.entities.PaymentAccountEntity
import com.soleel.finanzas.core.database.extras.PaymentAccountWithTotalAmountEntity
import com.soleel.finanzas.core.model.PaymentAccount


fun PaymentAccountEntity.toModel(): PaymentAccount {
    return PaymentAccount(
        id = this.id,
        name = this.name,
        createAt = this.createAt,
        updatedAt = this.updatedAt,
        accountType = this.accountType,
    )
}

fun List<PaymentAccountEntity>.toModelList(): List<PaymentAccount> {
    return this.map(transform = { it.toModel() })
}

fun PaymentAccountWithTotalAmountEntity.toModel(): PaymentAccount {
    return PaymentAccount(
        id = this.paymentAccountEntity.id,
        name = this.paymentAccountEntity.name,
        amount = this.totalIncome - this.totalExpense,
        createAt = this.paymentAccountEntity.createAt,
        updatedAt = this.paymentAccountEntity.updatedAt,
        accountType = this.paymentAccountEntity.accountType,
    )
}

fun List<PaymentAccountWithTotalAmountEntity>.toModelList(): List<PaymentAccount> {
    return this.map(transform = {it.toModel()})
}

fun PaymentAccount.toEntity(): PaymentAccountEntity {
    return PaymentAccountEntity(
        id = this.id,
        name = this.name,
        createAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        accountType = this.accountType
    )
}
