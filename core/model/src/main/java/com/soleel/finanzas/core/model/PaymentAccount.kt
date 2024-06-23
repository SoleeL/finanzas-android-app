package com.soleel.finanzas.core.model

data class PaymentAccount(
    val id: String,
    val name: String,
    var amount: Int = 0,
    val createAt: Long, // Covertir a Date
    val updatedAt: Long, // Covertir a Date
    val accountType: Int // Convertir a PaymentAccountTypeEnum
)