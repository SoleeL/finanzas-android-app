package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "transaction_table",
    indices = [
        Index(name = "index_payment_account_id", value = ["payment_account_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = PaymentAccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["payment_account_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "create_at") var createAt: Long,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "transaction_type") var transactionType: Int,
    @ColumnInfo(name = "category_type") var categoryType: Int,
    @ColumnInfo(name = "payment_account_id") var paymentAccountId: String
)