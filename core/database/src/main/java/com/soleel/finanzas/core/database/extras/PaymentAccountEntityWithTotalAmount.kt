package com.soleel.finanzas.core.database.extras

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soleel.finanzas.core.database.entities.PaymentAccountEntity

data class PaymentAccountEntityWithTotalAmount(
    @Embedded val paymentAccountEntity: PaymentAccountEntity,
    @ColumnInfo(name = "totalIncome") val totalIncome: Int,
    @ColumnInfo(name = "totalExpense") val totalExpense: Int
)