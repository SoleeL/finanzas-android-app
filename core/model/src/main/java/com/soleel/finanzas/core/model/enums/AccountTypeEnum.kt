package com.soleel.finanzas.core.model.enums

import com.soleel.finanzas.core.ui.R

enum class AccountTypeEnum(
    val id: Int,
    val value: String,
    val icon: Int
) {
    CREDIT(id = 1, value = "Credito", icon = R.drawable.ic_account_credit),
    DEBIT(id = 2, value = "Debito", icon =  R.drawable.ic_account_debit),
    SAVING(id = 3, value = "Ahorro", icon = R.drawable.ic_account_saving),
    INVESTMENT(id = 4, value = "Inversion", icon = R.drawable.ic_account_investment),
    CASH(id = 5, value = "Efectivo", icon = R.drawable.ic_account_cash);

    companion object {
        fun fromId(id: Int): AccountTypeEnum {
            val accountTypeEnum: AccountTypeEnum? = entries.find(predicate = { it.id == id })
            return accountTypeEnum ?: CREDIT
        }
    }
}