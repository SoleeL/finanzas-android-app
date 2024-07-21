package com.soleel.finanzas.core.model

import java.util.Date


data class TransactionsGroup(
    val date: Date, // Fecha del dia
    val transactionsWithAccount: List<TransactionWithAccount> // Todas las transacciones del dia
)