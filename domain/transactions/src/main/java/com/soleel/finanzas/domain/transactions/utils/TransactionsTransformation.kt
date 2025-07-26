package com.soleel.finanzas.domain.transactions.utils

import com.soleel.finanzas.core.model.Transaction

fun List<Transaction>.summaryIncome(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.INCOME == it.type) it.amount else 0 }
)

fun List<Transaction>.summaryExpenditure(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.EXPENDITURE == it.type) it.amount else 0 }
)