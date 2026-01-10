package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.soleel.finanzas.core.model.enums.ExpenseTypeEnum
import java.time.LocalDateTime
import java.util.UUID


@Entity(
    tableName = "expense_table",
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
data class ExpenseEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: UUID,

    @ColumnInfo(name = "type")
    val type: ExpenseTypeEnum,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "date")
    val date: LocalDateTime,
    @ColumnInfo(name = "amount")
    val amount: Int,

    @ColumnInfo(name = "account_id")
    val accountId: UUID,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean
)