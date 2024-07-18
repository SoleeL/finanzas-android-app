package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum


data class TransactionSummary (
    val name: String = "",
    val amount: Int = 0,
    val type: TransactionTypeEnum
)