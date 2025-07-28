package com.soleel.finanzas.core.database.extras

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soleel.finanzas.core.database.entities.AccountEntity

data class AccountWithExpenseInfoEntity(
    @Embedded val accountEntity: AccountEntity,
    @ColumnInfo(name = "expenses_number") val expensesNumber: Int = 0
)