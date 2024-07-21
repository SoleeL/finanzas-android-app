package com.soleel.finanzas.core.database.extras

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soleel.finanzas.core.database.entities.AccountEntity

data class AccountWithTotalAmountEntity(
    @Embedded val accountEntity: AccountEntity,
    @ColumnInfo(name = "totalIncome") val totalIncome: Int,
    @ColumnInfo(name = "totalExpense") val totalExpense: Int
)