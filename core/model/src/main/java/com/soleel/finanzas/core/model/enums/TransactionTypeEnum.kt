package com.soleel.finanzas.core.model.enums


import com.soleel.finanzas.core.ui.R


enum class TransactionTypeEnum(
    val id: Int,
    val value: String,
    val icon: Int
) {
    INCOME(id = 1, value = "Ingreso", icon = R.drawable.ic_income),
    EXPENDITURE(id = 2, value = "Gasto", icon = R.drawable.ic_expenditure);

    companion object {
        fun fromId(id: Int): TransactionTypeEnum {
            val transactionCategoryEnum: TransactionTypeEnum? = entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: INCOME
        }
    }
}