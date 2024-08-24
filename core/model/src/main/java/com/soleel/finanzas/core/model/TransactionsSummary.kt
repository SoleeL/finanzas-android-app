package com.soleel.finanzas.core.model

import java.time.LocalDate


data class TransactionsSummary(
    val localDate: LocalDate,
    val dateName: String, // Fecha del dia
    val transactions: List<TransactionSummary>
//    val income: SummaryTransaction, // Suma de los ingresos del dia
//    val expenditure: SummaryTransaction// Suma de los gastos del dia
)