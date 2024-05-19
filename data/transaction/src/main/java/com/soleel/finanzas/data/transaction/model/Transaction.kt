package com.soleel.finanzas.data.transaction.model

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