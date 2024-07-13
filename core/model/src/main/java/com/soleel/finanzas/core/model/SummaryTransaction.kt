package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum


data class SummaryTransaction (
    val name: String = "",
    val amount: Int = 0,
)