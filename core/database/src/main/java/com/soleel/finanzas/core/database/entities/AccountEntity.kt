package com.soleel.finanzas.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import java.time.LocalDateTime
import java.util.UUID


@Entity(
    tableName = "account_table",
)
data class AccountEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: UUID,

    @ColumnInfo(name = "type")
    var type: AccountTypeEnum,

    @ColumnInfo(name = "issue")
    val issue: String, // README: Emisor de la tarjeta/medio de pago
    @ColumnInfo(name = "fee")
    val fee: Int, // README: Es la comision por uso de la tarjeta

    @ColumnInfo(name = "credit_limit")
    val creditLimit: Int, // README: Es el cupo de la tarjeta
    @ColumnInfo(name = "interest_rate")
    val interestRate: Float, // README: Es la tasa de interes vigente
    @ColumnInfo(name = "interest_free_installments")
    val interestFreeInstallments: List<Int>, // README: Es el listado de cuotas sin intereses
    @ColumnInfo(name = "billing_day")
    val billingDay: Int, // README: Es el dia de facturacion de la tarjeta
    @ColumnInfo(name = "due_day")
    val dueDay: Int, // README: Es el dia limite de pago de la tarjeta

    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean
)