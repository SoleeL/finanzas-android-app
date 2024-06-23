package com.soleel.finanzas.core.common.enums

enum class TransactionCategoryEnum(
    val id: Int,
    val value: String
) {
    INCOME_TRANSFER(id = 11, value = "Transferencia"),
    INCOME_SALARY(id = 12, value = "Sueldo"),
    INCOME_SERVICE(id = 13, value = "Servicio prestados"),
    INCOME_SALES(id = 14, value = "Ventas"),
    // Considerar donacion como bono
    INCOME_BONUS(id = 15, value = "Bono"),
    INCOME_REFUND(id = 16, value = "Reembolso"),
    // Considerar pago de cuota y aumento de credito, como aumento del amount de la linea de credito
    INCOME_OTHER(id = 17, value = "Otro"),

    EXPENDITURE_TRANSFER(id = 21, value = "Transferencia"),
    EXPENDITURE_MARKET(id = 22, value = "Despensa"),
    // Considera: Transporte, Salud
    EXPENDITURE_SERVICE(id = 23, value = "Servicio obtenidos"),
    // Considera la compra de cosas para la casa
    EXPENDITURE_ACQUISITION(id = 24, value = "Adquisicion"),
    EXPENDITURE_LEASURE(id = 25, value = "Ocio"),
    EXPENDITURE_GIFT(id = 26, value = "Regalo"),
    EXPENDITURE_OTHER(id = 27, value = "Otro");

    companion object {
        fun fromId(id: Int): TransactionCategoryEnum {
            val transactionCategoryEnum: TransactionCategoryEnum? = entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: INCOME_TRANSFER
        }

        fun getTransactionCategories(
            transactionType: TransactionTypeEnum,
            accountType: PaymentAccountTypeEnum
        ): List<TransactionCategoryEnum> {
            return when (transactionType) {
                TransactionTypeEnum.INCOME -> this.getIncomeTransactionCategories(accountType)
                TransactionTypeEnum.EXPENDITURE -> this.getExpenditureTransactionCategories(accountType)
            }
        }

        private fun getIncomeTransactionCategories(accountType: PaymentAccountTypeEnum): List<TransactionCategoryEnum> {
            return when (accountType) {
                PaymentAccountTypeEnum.CREDIT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_OTHER
                )

                PaymentAccountTypeEnum.DEBIT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_SALARY,
                    INCOME_SERVICE,
                    INCOME_SALES,
                    INCOME_BONUS,
                    INCOME_REFUND,
                    INCOME_OTHER
                )

                PaymentAccountTypeEnum.SAVING,
                PaymentAccountTypeEnum.INVESTMENT -> listOf(
                    INCOME_TRANSFER,
                    INCOME_OTHER
                )

                PaymentAccountTypeEnum.CASH -> listOf(
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

        private fun getExpenditureTransactionCategories(accountType: PaymentAccountTypeEnum): List<TransactionCategoryEnum> {
            return when (accountType) {
                PaymentAccountTypeEnum.CREDIT -> listOf(
                    EXPENDITURE_MARKET,
                    EXPENDITURE_SERVICE,
                    EXPENDITURE_ACQUISITION,
                    EXPENDITURE_LEASURE,
                    EXPENDITURE_GIFT,
                    EXPENDITURE_OTHER
                )

                PaymentAccountTypeEnum.DEBIT -> listOf(
                    EXPENDITURE_TRANSFER,
                    EXPENDITURE_MARKET,
                    EXPENDITURE_SERVICE,
                    EXPENDITURE_ACQUISITION,
                    EXPENDITURE_LEASURE,
                    EXPENDITURE_GIFT,
                    EXPENDITURE_OTHER
                )

                PaymentAccountTypeEnum.SAVING,
                PaymentAccountTypeEnum.INVESTMENT -> listOf(
                    EXPENDITURE_TRANSFER,
                    EXPENDITURE_OTHER
                )

                PaymentAccountTypeEnum.CASH -> listOf(
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