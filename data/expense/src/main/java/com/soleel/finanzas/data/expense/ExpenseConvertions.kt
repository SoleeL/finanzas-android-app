package com.soleel.finanzas.data.expense

import com.soleel.finanzas.core.database.entities.ExpenseEntity
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.core.model.base.ExpenseDto
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


fun ExpenseEntity.toModel(): ExpenseDto {
    return ExpenseDto(
        id = this.id,
        expenseType = this.type,
        name = this.name,
        date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.date), ZoneId.systemDefault()),
        amount = this.amount,
        accountId = this.accountId,
        createdAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.createdAt), ZoneId.systemDefault()),
        updatedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.updatedAt), ZoneId.systemDefault()),
        isDeleted = this.isDeleted
    )
}

fun List<ExpenseEntity>.toModelList() :List<ExpenseDto> {
    return this.map(transform = { it.toModel() })
}