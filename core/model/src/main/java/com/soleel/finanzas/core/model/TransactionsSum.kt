package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import java.util.Date


data class TransactionsSum(
    val date: Date, // Fecha del dia
    val income: SummaryTransaction, // Suma de los ingresos del dia
    val expenditure: SummaryTransaction// Suma de los gastos del dia
)