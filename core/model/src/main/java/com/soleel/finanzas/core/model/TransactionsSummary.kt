package com.soleel.finanzas.core.model

import java.util.Date


data class TransactionsSummary(
    val date: Date, // Fecha del dia
    val transactions: List<TransactionSummary>
//    val income: SummaryTransaction, // Suma de los ingresos del dia
//    val expenditure: SummaryTransaction// Suma de los gastos del dia
)