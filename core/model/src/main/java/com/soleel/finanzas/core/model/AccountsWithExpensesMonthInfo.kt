package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.base.Account
import java.time.LocalDateTime

data class AccountWithExpensesMonthInfo (
    val account: Account,
    val amountExpensesThisMonth: Int,
    val lastExpenseDate: LocalDateTime,
)