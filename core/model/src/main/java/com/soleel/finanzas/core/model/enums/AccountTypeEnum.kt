package com.soleel.finanzas.core.model.enums

import com.soleel.finanzas.core.ui.R

enum class AccountTypeEnum(
    val id: Int,
    val value: String,
    val icon: Int
) {

    CASH(id = 1, value = "Efectivo", icon = R.drawable.ic_account_cash),
    CREDIT(id = 2, value = "Credito", icon = R.drawable.ic_account_credit),
    DEBIT(id = 3, value = "Debito", icon =  R.drawable.ic_account_debit);

    companion object {
        fun fromId(id: Int): AccountTypeEnum {
            val accountTypeEnum: AccountTypeEnum? = entries.find(predicate = { it.id == id })
            return accountTypeEnum ?: CREDIT
        }
    }
}