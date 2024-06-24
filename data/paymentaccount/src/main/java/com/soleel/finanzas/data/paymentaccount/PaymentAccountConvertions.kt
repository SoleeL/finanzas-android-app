package com.soleel.finanzas.data.paymentaccount

import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.database.entities.PaymentAccountEntity
import com.soleel.finanzas.core.database.extras.PaymentAccountWithTotalAmountEntity
import com.soleel.finanzas.core.model.PaymentAccount
import java.util.Date


fun PaymentAccountEntity.toModel(): PaymentAccount {
    return PaymentAccount(
        id = this.id,
        name = this.name,
        createAt = Date(this.createAt),
        updatedAt = Date(this.updatedAt),
        type = PaymentAccountTypeEnum.fromId(id = this.accountType)
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
        createAt = Date(this.paymentAccountEntity.createAt),
        updatedAt = Date(this.paymentAccountEntity.updatedAt),
        type = PaymentAccountTypeEnum.fromId(id = this.paymentAccountEntity.accountType)
    )
}

fun List<PaymentAccountWithTotalAmountEntity>.toWithTotalAmountModelList(): List<PaymentAccount> {
    return this.map(transform = {it.toModel()})
}

fun PaymentAccount.toEntity(): PaymentAccountEntity {
    return PaymentAccountEntity(
        id = this.id,
        name = this.name,
        createAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        accountType = this.type.id
    )
}
