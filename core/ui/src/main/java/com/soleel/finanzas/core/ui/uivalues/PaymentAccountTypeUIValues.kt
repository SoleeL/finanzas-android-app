package com.soleel.finanzas.core.ui.uivalues

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
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

data class PaymentAccountTypeUIValues(
    val icon: Int,
    val name: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getPaymentAccountTypeUI(
    paymentAccountType: PaymentAccountTypeEnum
): PaymentAccountTypeUIValues {
    return when (paymentAccountType) {

        PaymentAccountTypeEnum.CREDIT -> PaymentAccountTypeUIValues(
            icon = R.drawable.ic_credit,
            name = paymentAccountType.value,
            letterColor = CreditLetterColor,
            gradientBrush = getCardLinearGradient(
                CreditGradientColor1,
                CreditGradientColor2
            )
        )

        PaymentAccountTypeEnum.DEBIT -> PaymentAccountTypeUIValues(
            icon = R.drawable.ic_debit,
            name = paymentAccountType.value,
            letterColor = DebitLetterColor,
            gradientBrush = getCardLinearGradient(
                DebitGradientColor1,
                DebitGradientColor2
            )
        )

        PaymentAccountTypeEnum.SAVING -> PaymentAccountTypeUIValues(
            icon = R.drawable.ic_saving,
            name = paymentAccountType.value,
            letterColor = SavingLetterColor,
            gradientBrush = getCardLinearGradient(
                SavingGradientColor1,
                SavingGradientColor2
            )
        )

        PaymentAccountTypeEnum.INVESTMENT -> PaymentAccountTypeUIValues(
            icon = R.drawable.ic_investment,
            name = paymentAccountType.value,
            letterColor = InvestmentLetterColor,
            gradientBrush = getCardLinearGradient(
                InvestmentGradientColor1,
                InvestmentGradientColor2
            )
        )

        PaymentAccountTypeEnum.CASH -> PaymentAccountTypeUIValues(
            icon = R.drawable.ic_money,
            name = paymentAccountType.value,
            letterColor = CashLetterColor,
            gradientBrush = getCardLinearGradient(
                CashGradientColor1,
                CashGradientColor2
            )
        )
    }
}