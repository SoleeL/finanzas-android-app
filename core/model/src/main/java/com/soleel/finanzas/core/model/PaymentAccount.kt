package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import java.util.Date


data class PaymentAccount(
    val id: String,
    val name: String,
    var amount: Int = 0,
    val createAt: Date,
    val updatedAt: Date,
    val type: PaymentAccountTypeEnum
)