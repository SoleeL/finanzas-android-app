package com.soleel.finanzas.core.ui.uivalues

import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.theme.TransactionTypeExpenditureBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeIncomeBackgroundColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeLetterColor

data class TransactionTypeUIValues(
    val icon: Int,
    val name: String,
    val letterColor: Color,
    val backgroundColor: Color,
)

fun getTransactionTypeUI(
    transactionType: TransactionTypeEnum
): TransactionTypeUIValues {
    return when (transactionType) {
        TransactionTypeEnum.INCOME -> TransactionTypeUIValues(
            icon = R.drawable.ic_income,
            name = TransactionTypeEnum.INCOME.value,
            letterColor = TransactionTypeLetterColor,
            backgroundColor = TransactionTypeIncomeBackgroundColor,
        )

        TransactionTypeEnum.EXPENDITURE -> TransactionTypeUIValues(
            icon = R.drawable.ic_expenditure,
            name = TransactionTypeEnum.EXPENDITURE.value,
            letterColor = TransactionTypeLetterColor,
            backgroundColor = TransactionTypeExpenditureBackgroundColor,
        )
    }
}