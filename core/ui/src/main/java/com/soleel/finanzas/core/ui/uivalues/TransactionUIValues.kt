package com.soleel.finanzas.core.ui.uivalues

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum


data class TransactionUIValues(
    val account: AccountUIValues,
    val type: TransactionTypeUIValues,
    val category: TransactionCategoryUIValues?,
    val name: String,
    val amount: String,
    val date: String
)

fun getTransactionUI(
    accountTypeEnum: AccountTypeEnum,
    accountName: String,
    accountAmount: String,
    transactionType: TransactionTypeEnum,
    transactionCategory: TransactionCategoryEnum? = null,
    transactionName: String = "",
    transactionAmount: String = "",
    transactionDate: String = ""
): TransactionUIValues {
    val accountUI: AccountUIValues = getAccountUI(
        accountTypeEnum = accountTypeEnum,
        accountName = accountName,
        accountAmount = accountAmount
    )

    val transactionTypeUI: TransactionTypeUIValues = getTransactionTypeUI(
        transactionType = transactionType
    )

    val transactionCategoryUI: TransactionCategoryUIValues? = getTransactionCategoryUI(
        transactionType = transactionType,
        transactionCategory = transactionCategory
    )

    val transactionUI: TransactionUIValues = TransactionUIValues(
        account = accountUI,
        type = transactionTypeUI,
        category = transactionCategoryUI,
        name = transactionName.ifEmpty(defaultValue = { "Gasto en ..." }),
        amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
        date = transactionDate.ifEmpty(defaultValue = { "dd/MM/yyyy HH:mm" })
    )

    return transactionUI
}
