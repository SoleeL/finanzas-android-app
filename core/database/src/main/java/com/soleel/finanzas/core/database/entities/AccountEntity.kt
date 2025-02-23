package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "account_table",
)
data class AccountEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "type") var type: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
    @ColumnInfo(name = "is_deleted") var isDeleted: Boolean,
    @ColumnInfo(name = "synchronization") var synchronization: Int,
)