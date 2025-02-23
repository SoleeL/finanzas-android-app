package com.soleel.finanzas.core.model


data class TransactionWithAccount(
    val transaction: Transaction,
    val account: Account
)