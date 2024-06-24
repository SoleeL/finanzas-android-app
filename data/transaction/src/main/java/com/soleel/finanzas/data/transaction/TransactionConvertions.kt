package com.soleel.finanzas.data.transaction

import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.database.entities.TransactionEntity
import com.soleel.finanzas.core.model.Transaction
import java.util.Date


fun TransactionEntity.toModel(): Transaction {
    return Transaction(
        id = this.id,
        name = this.name,
        amount = this.amount,
        createAt = Date(this.createAt),
        updatedAt = Date(this.updatedAt),
        type = TransactionTypeEnum.fromId(id = this.transactionType),
        category = TransactionCategoryEnum.fromId(id = this.categoryType),
        paymentAccountId = this.paymentAccountId
    )
}

fun List<TransactionEntity>.toModelList() :List<Transaction> {
    return this.map(transform = { it.toModel() })
}
