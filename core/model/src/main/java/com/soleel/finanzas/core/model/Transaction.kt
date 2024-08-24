package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.core.model.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.model.enums.TransactionTypeEnum
import java.time.LocalDateTime


data class Transaction(
    val id: String,
    val type: TransactionTypeEnum,
    val category: TransactionCategoryEnum,
    var name: String,
    var date: LocalDateTime,
    val amount: Int,
    val accountId: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isDeleted: Boolean,
    val synchronization: SynchronizationEnum
)