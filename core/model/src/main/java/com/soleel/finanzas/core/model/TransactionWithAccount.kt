package com.soleel.finanzas.core.model

import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Expense


data class TransactionWithAccount(
    val expense: Expense,
    val account: Account
)