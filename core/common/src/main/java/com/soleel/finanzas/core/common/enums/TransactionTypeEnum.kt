package com.soleel.finanzas.core.common.enums


enum class TransactionTypeEnum(
    val id: Int,
    val value: String
) {
    INCOME(id = 1, value = "Ingreso"),
    EXPENDITURE(id = 2, value = "Gasto");

    companion object {
        fun fromId(id: Int): TransactionTypeEnum {
            val transactionCategoryEnum: TransactionTypeEnum? = entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: INCOME
        }
    }
}