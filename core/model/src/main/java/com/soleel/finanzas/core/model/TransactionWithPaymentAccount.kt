package com.soleel.finanzas.core.model


data class TransactionWithPaymentAccount(
    val transaction: Transaction,
    val paymentAccount: PaymentAccount?
)