package com.soleel.finanzas.core.model.base

import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import java.time.LocalDateTime


data class Expense(
    val id: String,
    val expenseType: ExpenseTypeEnum,
    var name: String,
    var date: LocalDateTime,
    val amount: Int,
    val accountId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isDeleted: Boolean,
    val synchronization: SynchronizationEnum
)