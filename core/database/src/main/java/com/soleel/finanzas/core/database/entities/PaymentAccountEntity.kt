package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "payment_account_table",
)
data class PaymentAccountEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "create_at") var createAt: Long,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "account_type") var accountType: Int
)
