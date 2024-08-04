package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import java.util.Date


data class Account(
    val id: String,
    val type: AccountTypeEnum,
    val name: String,
    var amount: Int = 0,
    val createdAt: Date,
    val updatedAt:Date,
    val isDeleted: Boolean = false,
)