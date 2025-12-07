package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.base.Account
import java.time.LocalDateTime

data class AccountWithExpensesInfo (
    val account: Account,
    val amountExpenses: Int,
    val lastExpenseDate: LocalDateTime?,
)