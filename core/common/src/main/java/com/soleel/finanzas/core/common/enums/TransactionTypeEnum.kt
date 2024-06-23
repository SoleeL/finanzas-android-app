package com.soleel.finanzas.core.common.enums


enum class TransactionTypeEnum(
    val id: Int,
    val value: String
) {
    INCOME(id = 1, value = "Ingreso"),
    EXPENDITURE(id = 2, value = "Gasto")
}