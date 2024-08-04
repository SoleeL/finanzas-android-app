package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import java.util.Date


data class Transaction(
    val id: String,
    var name: String,
    val amount: Int,
    val createdAt: Date,
    val updatedAt: Date,
    val type: TransactionTypeEnum,
    val category: TransactionCategoryEnum,
    val accountId: String
)