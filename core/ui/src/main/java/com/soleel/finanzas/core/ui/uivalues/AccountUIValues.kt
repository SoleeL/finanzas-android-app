package com.soleel.finanzas.core.ui.uivalues

import com.soleel.finanzas.core.common.enums.AccountTypeEnum


data class AccountUIValues(
    val type: AccountTypeUIValues,
    var name: String,
    var amount: String,
)

fun getAccountUI(
    accountTypeEnum: AccountTypeEnum,
    accountName: String = "",
    accountAmount: String = ""
): AccountUIValues {
    val accountTypeUI: AccountTypeUIValues = getAccountTypeUI(
        accountType = accountTypeEnum
    )

    val accountUI: AccountUIValues = AccountUIValues(
        type = accountTypeUI,
        name = accountName.ifEmpty(defaultValue = {"Cuenta de pago"}),
        amount = accountAmount.ifEmpty(defaultValue = {"$1,000,000"})
    )

    return accountUI
}