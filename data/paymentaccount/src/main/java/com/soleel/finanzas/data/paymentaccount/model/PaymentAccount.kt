package com.soleel.finanzas.data.paymentaccount.model

data class PaymentAccount(
    val id: String,
    val name: String,
    var amount: Int = 0,
    val createAt: Long,
    val updatedAt: Long,
    val accountType: Int
)