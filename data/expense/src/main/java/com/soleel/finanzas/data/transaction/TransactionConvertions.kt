package com.soleel.finanzas.data.transaction

import com.soleel.finanzas.core.database.entities.TransactionEntity
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun TransactionEntity.toModel(): Expense {
    return Expense(
        id = this.id,
        expenseType = ExpenseTypeEnum.fromId(id = this.type),
        name = this.name,
        date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.date), ZoneId.systemDefault()),
        amount = this.amount,
        accountId = this.accountId,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.createdAt), ZoneId.systemDefault()),
        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault()),
        isDeleted = this.isDeleted,
        synchronization = SynchronizationEnum.fromId(id = this.synchronization)
    )
}

fun List<TransactionEntity>.toModelList() :List<Expense> {
    return this.map(transform = { it.toModel() })
}
