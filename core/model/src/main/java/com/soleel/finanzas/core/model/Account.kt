package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import java.util.Date


data class Account(
    val id: String,
    val name: String,
    var amount: Int = 0,
    val createAt: Date,
    val updatedAt: Date,
    val type: AccountTypeEnum
)