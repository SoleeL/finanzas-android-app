package com.soleel.finanzas.core.common.enums

import com.soleel.finanzas.core.common.R

enum class TransactionCategoryEnum(
    val id: Int,
    val value: String,
    val icon: Int
) {
    INCOME_TRANSFER(id = 11, value = "Transferencia", icon = R.drawable.ic_income_transfer),
    INCOME_SALARY(id = 12, value = "Sueldo", icon = R.drawable.ic_income_salary),
    INCOME_SERVICE(id = 13, value = "Servicio prestados", icon = R.drawable.ic_income_service),
    INCOME_SALES(id = 14, value = "Ventas", icon = R.drawable.ic_income_sales),
    // Considerar donacion como bono
    INCOME_BONUS(id = 15, value = "Bono", icon = R.drawable.ic_income_bonus),
    INCOME_REFUND(id = 16, value = "Reembolso", icon = R.drawable.ic_income_refund),
    // Considerar pago de cuota y aumento de credito, como aumento del amount de la linea de credito
    INCOME_OTHER(id = 17, value = "Otro", icon = R.drawable.ic_income_other),

    EXPENDITURE_TRANSFER(id = 21, value = "Transferencia", icon = R.drawable.ic_expenditure_transfer),
    EXPENDITURE_MARKET(id = 22, value = "Despensa", icon = R.drawable.ic_expenditure_market),
    // Considera: Transporte, Salud
    EXPENDITURE_SERVICE(id = 23, value = "Servicio obtenidos", icon = R.drawable.ic_expenditure_service),
    // Considera la compra de cosas para la casa
    EXPENDITURE_ACQUISITION(id = 24, value = "Adquisicion", icon = R.drawable.ic_expenditure_acquisition),
    EXPENDITURE_LEASURE(id = 25, value = "Ocio", icon = R.drawable.ic_expenditure_leasure),
    EXPENDITURE_GIFT(id = 26, value = "Regalo", icon = R.drawable.ic_expenditure_gift),
    EXPENDITURE_OTHER(id = 27, value = "Otro", icon = R.drawable.ic_expenditure_other);

    companion object {
        fun fromId(id: Int): TransactionCategoryEnum {
            val transactionCategoryEnum: TransactionCategoryEnum? = entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: INCOME_TRANSFER
        }

        fun getTransactionCategories(
            transactionType: TransactionTypeEnum,
            accountType: AccountTypeEnum
        ): List<TransactionCategoryEnum> {
            return when (transactionType) {
                TransactionTypeEnum.INCOME -> this.getIncomeTransactionCategories(accountType)
                TransactionTypeEnum.EXPENDITURE -> this.getExpenditureTransactionCategories(accountType)
            }
        }

        private fun getIncomeTransactionCategories(accountType: AccountTypeEnum): List<TransactionCategoryEnum> {
            return when (accountType) {
                AccountTypeEnum.CREDIT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_OTHER
                )

                AccountTypeEnum.DEBIT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_SALARY,
                    INCOME_SERVICE,
                    INCOME_SALES,
                    INCOME_BONUS,
                    INCOME_REFUND,
                    INCOME_OTHER
                )

                AccountTypeEnum.SAVING,
                AccountTypeEnum.INVESTMENT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_OTHER
                )

                AccountTypeEnum.CASH -> listOf(
                    INCOME_TRANSFER,
                    INCOME_SALARY,
                    INCOME_SERVICE,
                    INCOME_SALES,
                    INCOME_BONUS,
                    INCOME_REFUND,
                    INCOME_OTHER
                )
            }
        }

        private fun getExpenditureTransactionCategories(accountType: AccountTypeEnum): List<TransactionCategoryEnum> {
            return when (accountType) {
                AccountTypeEnum.CREDIT -> listOf(
                    EXPENDITURE_MARKET,
                    EXPENDITURE_SERVICE,
                    EXPENDITURE_ACQUISITION,
                    EXPENDITURE_LEASURE,
                    EXPENDITURE_GIFT,
                    EXPENDITURE_OTHER
                )

                AccountTypeEnum.DEBIT -> listOf(
                    EXPENDITURE_TRANSFER,
                    EXPENDITURE_MARKET,
                    EXPENDITURE_SERVICE,
                    EXPENDITURE_ACQUISITION,
                    EXPENDITURE_LEASURE,
                    EXPENDITURE_GIFT,
                    EXPENDITURE_OTHER
                )

                AccountTypeEnum.SAVING,
                AccountTypeEnum.INVESTMENT -> listOf(
                    EXPENDITURE_TRANSFER,
                    EXPENDITURE_OTHER
                )

                AccountTypeEnum.CASH -> listOf(
                    EXPENDITURE_TRANSFER,
                    EXPENDITURE_MARKET,
                    EXPENDITURE_SERVICE,
                    EXPENDITURE_ACQUISITION,
                    EXPENDITURE_LEASURE,
                    EXPENDITURE_GIFT,
                    EXPENDITURE_OTHER
                )
            }
        }
    }
}