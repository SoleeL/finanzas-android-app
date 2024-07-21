package com.soleel.finanzas.core.ui.uivalues

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.theme.CashGradientColor1
import com.soleel.finanzas.core.ui.theme.CashGradientColor2
import com.soleel.finanzas.core.ui.theme.CashLetterColor
import com.soleel.finanzas.core.ui.theme.CreditGradientColor1
import com.soleel.finanzas.core.ui.theme.CreditGradientColor2
import com.soleel.finanzas.core.ui.theme.CreditLetterColor
import com.soleel.finanzas.core.ui.theme.DebitGradientColor1
import com.soleel.finanzas.core.ui.theme.DebitGradientColor2
import com.soleel.finanzas.core.ui.theme.DebitLetterColor
import com.soleel.finanzas.core.ui.theme.InvestmentGradientColor1
import com.soleel.finanzas.core.ui.theme.InvestmentGradientColor2
import com.soleel.finanzas.core.ui.theme.InvestmentLetterColor
import com.soleel.finanzas.core.ui.theme.SavingGradientColor1
import com.soleel.finanzas.core.ui.theme.SavingGradientColor2
import com.soleel.finanzas.core.ui.theme.SavingLetterColor
import com.soleel.finanzas.core.ui.util.getCardLinearGradient

data class AccountTypeUIValues(
    val icon: Int,
    val name: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getAccountTypeUI(
    accountType: AccountTypeEnum
): AccountTypeUIValues {
    return when (accountType) {

        AccountTypeEnum.CREDIT -> AccountTypeUIValues(
            icon = R.drawable.ic_credit,
            name = accountType.value,
            letterColor = CreditLetterColor,
            gradientBrush = getCardLinearGradient(
                CreditGradientColor1,
                CreditGradientColor2
            )
        )

        AccountTypeEnum.DEBIT -> AccountTypeUIValues(
            icon = R.drawable.ic_debit,
            name = accountType.value,
            letterColor = DebitLetterColor,
            gradientBrush = getCardLinearGradient(
                DebitGradientColor1,
                DebitGradientColor2
            )
        )

        AccountTypeEnum.SAVING -> AccountTypeUIValues(
            icon = R.drawable.ic_saving,
            name = accountType.value,
            letterColor = SavingLetterColor,
            gradientBrush = getCardLinearGradient(
                SavingGradientColor1,
                SavingGradientColor2
            )
        )

        AccountTypeEnum.INVESTMENT -> AccountTypeUIValues(
            icon = R.drawable.ic_investment,
            name = accountType.value,
            letterColor = InvestmentLetterColor,
            gradientBrush = getCardLinearGradient(
                InvestmentGradientColor1,
                InvestmentGradientColor2
            )
        )

        AccountTypeEnum.CASH -> AccountTypeUIValues(
            icon = R.drawable.ic_money,
            name = accountType.value,
            letterColor = CashLetterColor,
            gradientBrush = getCardLinearGradient(
                CashGradientColor1,
                CashGradientColor2
            )
        )
    }
}