package com.soleel.finanzas.core.model

data class Transaction(
    val id: String,
    var name: String,
    val amount: Int,
    val createAt: Long,
    val updatedAt: Long,
    val categoryType: Int,
    val transactionType: Int,
    val paymentAccountId: String
)