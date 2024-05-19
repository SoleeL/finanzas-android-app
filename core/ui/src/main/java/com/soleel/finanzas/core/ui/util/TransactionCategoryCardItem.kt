package com.soleel.finanzas.core.ui.util

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.constants.TransactionCategoryConstant
import com.soleel.finanzas.core.common.constants.TransactionTypeConstant
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryAcquisitionGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryAcquisitionGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryAcquisitionLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryGiftGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryGiftGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryGiftLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryLeasureGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryLeasureGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryLeasureLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryMarketGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryMarketGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryMarketLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryOtherGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryOtherGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryOtherLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryServiceGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryServiceGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryServiceLetterColor
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryTransferGradientColor1
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryTransferGradientColor2
import com.soleel.finanzas.core.ui.theme.ExpenditureCategoryTransferLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategoryBonusGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategoryBonusGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategoryBonusLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategoryOtherGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategoryOtherGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategoryOtherLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategoryRefundGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategoryRefundGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategoryRefundLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalaryGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalaryGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalaryLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalesGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalesGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategorySalesLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategoryServiceGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategoryServiceGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategoryServiceLetterColor
import com.soleel.finanzas.core.ui.theme.IncomeCategoryTransferGradientColor1
import com.soleel.finanzas.core.ui.theme.IncomeCategoryTransferGradientColor2
import com.soleel.finanzas.core.ui.theme.IncomeCategoryTransferLetterColor
import com.soleel.finanzas.core.ui.theme.TransactionTypeLetterColor


data class TransactionCategoryCardItem(
    val category: Int,
    val categoryName: String,
    val categoryIcon: Int,
    val categoryNameTransaction: String,
    var amount: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getTransactionCategoryCard(
    transactionType: Int,
    transactionCategory: Int,
    transactionName: String = "",
    transactionAmount: String = ""
): TransactionCategoryCardItem {
    return when (transactionType) {
        TransactionTypeConstant.INCOME -> getTransactionCategoryIncomeCard(
            transactionCategory = transactionCategory,
            transactionName = transactionName,
            transactionAmount = transactionAmount
        )

        TransactionTypeConstant.EXPENDITURE -> getTransactionCategoryExpenditureCard(
            transactionCategory = transactionCategory,
            transactionName = transactionName,
            transactionAmount = transactionAmount
        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Una transaccion de prueba" }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}

private fun getTransactionCategoryIncomeCard(
    transactionCategory: Int,
    transactionName: String,
    transactionAmount: String,
): TransactionCategoryCardItem {
    return when (transactionCategory) {

        TransactionCategoryConstant.INCOME_TRANSFER -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_TRANSFER_VALUE,
            categoryIcon = R.drawable.ic_income_transfer,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Transferencia de ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategoryTransferLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryTransferGradientColor1,
                IncomeCategoryTransferGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_SALARY -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_SALARY_VALUE,
            categoryIcon = R.drawable.ic_income_salary,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Salario de ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategorySalaryLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategorySalaryGradientColor1,
                IncomeCategorySalaryGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_SERVICE -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_SERVICE_VALUE,
            categoryIcon = R.drawable.ic_income_service,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Servicios prestados a ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategoryServiceLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryServiceGradientColor1,
                IncomeCategoryServiceGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_SALES -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_SALES_VALUE,
            categoryIcon = R.drawable.ic_income_sales,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "De vender ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategorySalesLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategorySalesGradientColor1,
                IncomeCategorySalesGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_BONUS -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_BONUS_VALUE,
            categoryIcon = R.drawable.ic_income_bonus,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Bono o premio de ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategoryBonusLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryBonusGradientColor1,
                IncomeCategoryBonusGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_REFUND -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_REFUND_VALUE,
            categoryIcon = R.drawable.ic_income_refund,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Devolucion de ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategoryRefundLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryRefundGradientColor1,
                IncomeCategoryRefundGradientColor2
            )
        )

        TransactionCategoryConstant.INCOME_OTHER -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.INCOME_OTHER_VALUE,
            categoryIcon = R.drawable.ic_income_other,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Ingreso por ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = IncomeCategoryOtherLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryOtherGradientColor1,
                IncomeCategoryOtherGradientColor2
            )
        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Transaccion de ingreso" }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}

private fun getTransactionCategoryExpenditureCard(
    transactionCategory: Int,
    transactionName: String,
    transactionAmount: String,
): TransactionCategoryCardItem {
    return when (transactionCategory) {

        TransactionCategoryConstant.EXPENDITURE_TRANSFER -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_TRANSFER_VALUE,
            categoryIcon = R.drawable.ic_expenditure_transfer,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Transferencia a ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryTransferLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryTransferGradientColor1,
                ExpenditureCategoryTransferGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_MARKET -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_MARKET_VALUE,
            categoryIcon = R.drawable.ic_expenditure_market,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Compra en ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryMarketLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryMarketGradientColor1,
                ExpenditureCategoryMarketGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_SERVICE -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_SERVICE_VALUE,
            categoryIcon = R.drawable.ic_expenditure_service,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Pago de servicio ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryServiceLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryServiceGradientColor1,
                ExpenditureCategoryServiceGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_ACQUISITION -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_ACQUISITION_VALUE,
            categoryIcon = R.drawable.ic_expenditure_acquisition,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Adquirido un ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryAcquisitionLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryAcquisitionGradientColor1,
                ExpenditureCategoryAcquisitionGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_LEASURE -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_LEASURE_VALUE,
            categoryIcon = R.drawable.ic_expenditure_leasure,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Ida al ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryLeasureLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryLeasureGradientColor1,
                ExpenditureCategoryLeasureGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_GIFT -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_GIFT_VALUE,
            categoryIcon = R.drawable.ic_expenditure_gift,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Regalo de ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryGiftLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryGiftGradientColor1,
                ExpenditureCategoryGiftGradientColor2
            )
        )

        TransactionCategoryConstant.EXPENDITURE_OTHER -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = TransactionCategoryConstant.EXPENDITURE_OTHER_VALUE,
            categoryIcon = R.drawable.ic_expenditure_other,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Gasto en ..." }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = ExpenditureCategoryOtherLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryOtherGradientColor1,
                ExpenditureCategoryOtherGradientColor2
            )
        )

        else -> TransactionCategoryCardItem(
            category = transactionCategory,
            categoryName = "",
            categoryIcon = R.drawable.ic_debit,
            categoryNameTransaction = transactionName.ifEmpty(defaultValue = { "Transaccion de gasto" }),
            amount = transactionAmount.ifEmpty(defaultValue = { "$10,000" }),
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}