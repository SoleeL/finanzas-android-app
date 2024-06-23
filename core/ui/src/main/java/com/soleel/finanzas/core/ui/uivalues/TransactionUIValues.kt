package com.soleel.finanzas.core.ui.uivalues

import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum


data class TransactionUIValues(
    val paymentAccount: PaymentAccountUIValues,
    val type: TransactionTypeUIValues,
    val category: TransactionCategoryUIValues?,
    val name: String,
    val amount: String,
    val date: String
)

fun getTransactionUI(
    paymentAccountTypeEnum: PaymentAccountTypeEnum,
    paymentAccountName: String,
    paymentAccountAmount: String,
    transactionType: TransactionTypeEnum,
    transactionCategory: TransactionCategoryEnum? = null,
    transactionName: String = "",
    transactionAmount: String = "",
    transactionDate: String = ""
): TransactionUIValues {
    val paymentAccountUI: PaymentAccountUIValues = getPaymentAccountUI(
        paymentAccountTypeEnum = paymentAccountTypeEnum,
        paymentAccountName = paymentAccountName,
        paymentAccountAmount = paymentAccountAmount
    )

    val transactionTypeUI: TransactionTypeUIValues = getTransactionTypeUI(
        transactionType = transactionType
    )

    val transactionCategoryUI: TransactionCategoryUIValues? = getTransactionCategoryUI(
        transactionType = transactionType,
        transactionCategory = transactionCategory
    )

    val transactionUI: TransactionUIValues = TransactionUIValues(
        paymentAccount = paymentAccountUI,
        type = transactionTypeUI,
        category = transactionCategoryUI,
        name = transactionName.ifEmpty(defaultValue = { "Gasto en ..." }),
        amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
        date = transactionDate.ifEmpty(defaultValue = { "dd/MM/yyyy HH:mm" })
    )

    return transactionUI
}
