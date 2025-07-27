package com.soleel.finanzas.core.database.extras

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.soleel.finanzas.core.database.entities.AccountEntity

data class AccountWithTransactionInfoEntity(
    @Embedded val accountEntity: AccountEntity,
    @ColumnInfo(name = "transactions_number") val transactionsNumber: Int = 0
)