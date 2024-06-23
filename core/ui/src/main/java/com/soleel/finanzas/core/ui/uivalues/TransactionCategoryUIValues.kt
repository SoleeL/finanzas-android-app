package com.soleel.finanzas.core.ui.uivalues

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.soleel.finanzas.core.common.enums.TransactionCategoryEnum
import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
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
import com.soleel.finanzas.core.ui.util.getCardLinearGradient

data class TransactionCategoryUIValues(
    val icon: Int,
    val name: String,
    val letterColor: Color,
    val gradientBrush: Brush,
)

fun getTransactionCategoryUI(
    transactionType: TransactionTypeEnum,
    transactionCategory: TransactionCategoryEnum?
): TransactionCategoryUIValues? {
    if (null == transactionCategory) return null

    return when (transactionType) {
        TransactionTypeEnum.INCOME -> getIncomeTransactionCategoryUI(transactionCategory = transactionCategory)
        TransactionTypeEnum.EXPENDITURE -> getExpenditureTransactionCategoryUI(transactionCategory = transactionCategory)
    }
}

private fun getIncomeTransactionCategoryUI(
    transactionCategory: TransactionCategoryEnum
): TransactionCategoryUIValues {
    return when (transactionCategory) {

        TransactionCategoryEnum.INCOME_TRANSFER -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_transfer,
            name = TransactionCategoryEnum.INCOME_TRANSFER.value,
            letterColor = IncomeCategoryTransferLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryTransferGradientColor1, IncomeCategoryTransferGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_SALARY -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_salary,
            name = TransactionCategoryEnum.INCOME_SALARY.value,
            letterColor = IncomeCategorySalaryLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategorySalaryGradientColor1, IncomeCategorySalaryGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_SERVICE -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_service,
            name = TransactionCategoryEnum.INCOME_SERVICE.value,
            letterColor = IncomeCategoryServiceLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryServiceGradientColor1, IncomeCategoryServiceGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_SALES -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_sales,
            name = TransactionCategoryEnum.INCOME_SALES.value,
            letterColor = IncomeCategorySalesLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategorySalesGradientColor1, IncomeCategorySalesGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_BONUS -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_bonus,
            name = TransactionCategoryEnum.INCOME_BONUS.value,
            letterColor = IncomeCategoryBonusLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryBonusGradientColor1, IncomeCategoryBonusGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_REFUND -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_refund,
            name = TransactionCategoryEnum.INCOME_REFUND.value,
            letterColor = IncomeCategoryRefundLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryRefundGradientColor1, IncomeCategoryRefundGradientColor2
            )
        )

        TransactionCategoryEnum.INCOME_OTHER -> TransactionCategoryUIValues(
            icon = R.drawable.ic_income_other,
            name = TransactionCategoryEnum.INCOME_OTHER.value,
            letterColor = IncomeCategoryOtherLetterColor,
            gradientBrush = getCardLinearGradient(
                IncomeCategoryOtherGradientColor1, IncomeCategoryOtherGradientColor2
            )
        )

        else -> TransactionCategoryUIValues(
            icon = R.drawable.ic_debit,
            name = "",
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}

private fun getExpenditureTransactionCategoryUI(
    transactionCategory: TransactionCategoryEnum
): TransactionCategoryUIValues {
    return when (transactionCategory) {

        TransactionCategoryEnum.EXPENDITURE_TRANSFER -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_transfer,
            name = TransactionCategoryEnum.EXPENDITURE_TRANSFER.name,
            letterColor = ExpenditureCategoryTransferLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryTransferGradientColor1, ExpenditureCategoryTransferGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_MARKET -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_market,
            name = TransactionCategoryEnum.EXPENDITURE_MARKET.name,
            letterColor = ExpenditureCategoryMarketLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryMarketGradientColor1, ExpenditureCategoryMarketGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_SERVICE -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_service,
            name = TransactionCategoryEnum.EXPENDITURE_SERVICE.name,
            letterColor = ExpenditureCategoryServiceLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryServiceGradientColor1, ExpenditureCategoryServiceGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_ACQUISITION -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_acquisition,
            name = TransactionCategoryEnum.EXPENDITURE_ACQUISITION.name,
            letterColor = ExpenditureCategoryAcquisitionLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryAcquisitionGradientColor1,
                ExpenditureCategoryAcquisitionGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_LEASURE -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_leasure,
            name = TransactionCategoryEnum.EXPENDITURE_LEASURE.name,
            letterColor = ExpenditureCategoryLeasureLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryLeasureGradientColor1, ExpenditureCategoryLeasureGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_GIFT -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_gift,
            name = TransactionCategoryEnum.EXPENDITURE_GIFT.name,
            letterColor = ExpenditureCategoryGiftLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryGiftGradientColor1, ExpenditureCategoryGiftGradientColor2
            )
        )

        TransactionCategoryEnum.EXPENDITURE_OTHER -> TransactionCategoryUIValues(
            icon = R.drawable.ic_expenditure_other,
            name = TransactionCategoryEnum.EXPENDITURE_OTHER.name,
            letterColor = ExpenditureCategoryOtherLetterColor,
            gradientBrush = getCardLinearGradient(
                ExpenditureCategoryOtherGradientColor1, ExpenditureCategoryOtherGradientColor2
            )
        )

        else -> TransactionCategoryUIValues(
            icon = R.drawable.ic_debit,
            name = TransactionCategoryEnum.EXPENDITURE_TRANSFER.name,
            letterColor = TransactionTypeLetterColor,
            gradientBrush = getCardLinearGradient(Color.Black, Color.Gray)
        )
    }
}
