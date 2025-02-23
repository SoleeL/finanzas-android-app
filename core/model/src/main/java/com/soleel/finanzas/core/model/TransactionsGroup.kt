package com.soleel.finanzas.core.model

import java.time.LocalDate


data class TransactionsGroup(
    val localDate: LocalDate, // Fecha del dia
    val transactionsWithAccount: List<TransactionWithAccount> // Todas las transacciones del dia
)