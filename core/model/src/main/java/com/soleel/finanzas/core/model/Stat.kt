package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum

data class Stat(
    val expenseType: ExpenseTypeEnum? = null,
    val amount: Int,
    val transactionNumber: Int? = null
)