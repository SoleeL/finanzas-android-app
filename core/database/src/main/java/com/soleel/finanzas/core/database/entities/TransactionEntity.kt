package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "transaction_table",
    indices = [
        Index(name = "index_account_id", value = ["account_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["account_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TransactionEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "category") var category: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "date") var date: Long,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "account_id") var accountId: String,
    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "is_deleted") var isDeleted: Boolean,
    @ColumnInfo(name = "synchronization") var synchronization: Int,
)