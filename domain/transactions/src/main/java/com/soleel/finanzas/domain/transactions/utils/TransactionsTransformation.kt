package com.soleel.finanzas.domain.transactions.utils

import com.soleel.finanzas.core.model.base.Expense

fun List<Expense>.summaryIncome(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.INCOME == it.type) it.amount else 0 }
)

fun List<Expense>.summaryExpenditure(): Int = this.sumOf(
    selector = { if (TransactionTypeEnum.EXPENDITURE == it.type) it.amount else 0 }
)