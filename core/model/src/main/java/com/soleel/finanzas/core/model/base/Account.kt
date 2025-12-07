package com.soleel.finanzas.core.model.base

import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import java.time.LocalDateTime


data class Account(
    val id: String,
    val type: AccountTypeEnum,
    val name: String,
    var totalIncome: Int = 0,
    var totalExpense: Int = 0,
    var totalAmount: Int = 0,
    var transactionsNumber: Int = 0,
    val createdAt: LocalDateTime,
    val updatedAt:LocalDateTime,
    val isDeleted: Boolean = false,
    val synchronization: SynchronizationEnum
)