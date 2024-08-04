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
        createdAt = Date(this.createdAt),
        updatedAt = Date(this.updatedAt),
        type = TransactionTypeEnum.fromId(id = this.type),
        category = TransactionCategoryEnum.fromId(id = this.category),
        accountId = this.accountId
    )
}

fun List<TransactionEntity>.toModelList() :List<Transaction> {
    return this.map(transform = { it.toModel() })
}
