package com.soleel.finanzas.core.ui.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
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


data class PaymentAccountCardItem(
    val type: Int,
    val typeName: String,
    var typeNameAccount: String,
    val typeIcon: Int,
    var amount: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getPaymentAccountCard(
    paymentAccountType: Int,
    paymentAccountTypeName: String? = null,
    amount: String? = null,
): PaymentAccountCardItem {
    return when (paymentAccountType) {
        PaymentAccountTypeConstant.CREDIT -> PaymentAccountCardItem(
            type = paymentAccountType,
            typeName = "CREDITO",
            typeNameAccount = paymentAccountTypeName ?: "Tarjeta de credito",
            typeIcon = R.drawable.ic_credit,
            amount = amount ?: "$1,000,000",
            letterColor = CreditLetterColor,
            gradientBrush = getCardLinearGradient(CreditGradientColor1, CreditGradientColor2)
        )

        PaymentAccountTypeConstant.DEBIT -> PaymentAccountCardItem(
            type = paymentAccountType,
            typeName = "DEBITO",
            typeNameAccount = paymentAccountTypeName ?: "Tarjeta de debito",
            typeIcon = R.drawable.ic_debit,
            amount = amount ?: "$1,000,000",
            letterColor = DebitLetterColor,
            gradientBrush = getCardLinearGradient(
                DebitGradientColor1,
                DebitGradientColor2
            )
        )


        PaymentAccountTypeConstant.SAVING -> PaymentAccountCardItem(
            type = paymentAccountType,
            typeName = "AHORRO",
            typeNameAccount = paymentAccountTypeName ?: "Cuenta de ahorro",
            typeIcon = R.drawable.ic_saving,
            amount = amount ?: "$1,000,000",
            letterColor = SavingLetterColor,
            gradientBrush = getCardLinearGradient(
                SavingGradientColor1,
                SavingGradientColor2
            )
        )


        PaymentAccountTypeConstant.INVESTMENT -> PaymentAccountCardItem(
            type = paymentAccountType,
            typeName = "INVERSION",
            typeNameAccount = paymentAccountTypeName ?: "Bolsa de inversion",
            typeIcon = R.drawable.ic_investment,
            amount = amount ?: "$1,000,000",
            letterColor = InvestmentLetterColor,
            gradientBrush = getCardLinearGradient(
                InvestmentGradientColor1,
                InvestmentGradientColor2
            )
        )

//            PaymentAccountTypeConstant.CASH ->
        else ->
            PaymentAccountCardItem(
                type = paymentAccountType,
                typeName = "EFECTIVO",
                typeNameAccount = paymentAccountTypeName ?: "Efectivo en bolsillo",
                typeIcon = R.drawable.ic_money,
                amount = amount ?: "$1,000,000",
                letterColor = CashLetterColor,
                gradientBrush = getCardLinearGradient(
                    CashGradientColor1,
                    CashGradientColor2
                )
            )
    }
}