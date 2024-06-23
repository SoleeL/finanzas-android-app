package com.soleel.finanzas.data.transaction

import com.soleel.finanzas.core.database.entities.TransactionEntity
import com.soleel.finanzas.core.model.Transaction


fun TransactionEntity.toModel(): Transaction {
    return Transaction(
        id = this.id,
        name = this.name,
        amount = this.amount,
        createAt = this.createAt,
        updatedAt = this.updatedAt,
        transactionType = this.transactionType,
        categoryType = this.categoryType,
        paymentAccountId = this.paymentAccountId
    )
}

fun List<TransactionEntity>.toModelList() :List<Transaction> {
    return this.map(transform = { it.toModel() })
}
