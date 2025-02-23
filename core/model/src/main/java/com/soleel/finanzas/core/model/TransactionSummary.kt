package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.enums.TransactionTypeEnum


data class TransactionSummary (
    val name: String = "",
    val amount: Int = 0,
    val type: TransactionTypeEnum
)