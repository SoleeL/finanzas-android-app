package com.soleel.finanzas.core.common.constants


object TransactionCategoryConstant {

    const val INCOME_TRANSFER: Int = 11
    const val INCOME_TRANSFER_VALUE: String = "Transferencia"

    const val INCOME_SALARY: Int = 12
    const val INCOME_SALARY_VALUE: String = "Sueldo"

    const val INCOME_SERVICE: Int = 13
    const val INCOME_SERVICE_VALUE: String = "Servicio prestados"

    const val INCOME_SALES: Int = 14
    const val INCOME_SALES_VALUE: String = "Ventas"

    const val INCOME_BONUS: Int = 15
    const val INCOME_BONUS_VALUE: String = "Bono"
    // Considerar donacion como bono

    const val INCOME_REFUND: Int = 16
    const val INCOME_REFUND_VALUE: String = "Reembolso"

    const val INCOME_OTHER: Int = 19
    const val INCOME_OTHER_VALUE: String = "Otro"
    // Considerar pago de cuota y aumento de credito, como aumento del amount de la linea de credito

    const val EXPENDITURE_TRANSFER: Int = 21
    const val EXPENDITURE_TRANSFER_VALUE: String = "Transferencia"

    const val EXPENDITURE_MARKET: Int = 23
    const val EXPENDITURE_MARKET_VALUE: String = "Despensa"

    const val EXPENDITURE_SERVICE: Int = 24
    const val EXPENDITURE_SERVICE_VALUE: String = "Servicio obtenidos"
    // Considera: Transporte, Salud

    const val EXPENDITURE_ACQUISITION: Int = 25
    const val EXPENDITURE_ACQUISITION_VALUE: String = "Adquisicion"
    // Considera la compra de cosas para la casa

    const val EXPENDITURE_LEASURE: Int = 26
    const val EXPENDITURE_LEASURE_VALUE: String = "Ocio"

    const val EXPENDITURE_GIFT: Int = 27
    const val EXPENDITURE_GIFT_VALUE: String = "Regalo"

    const val EXPENDITURE_OTHER: Int = 29
    const val EXPENDITURE_OTHER_VALUE: String = "Otro"

    fun getIdToValueList(transactionType: Int, accountType: Int): List<Pair<Int, String>> {
        return when (transactionType) {
            TransactionTypeConstant.INCOME -> getIdIncomeToValueList(accountType)
            TransactionTypeConstant.EXPENDITURE -> getIdExpenditureToValueList(accountType)
            else -> listOf()
        }
    }

    private fun getIdIncomeToValueList(accountType: Int): List<Pair<Int, String>> {
        return when (accountType) {
            PaymentAccountTypeConstant.CREDIT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            PaymentAccountTypeConstant.DEBIT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_SALARY to INCOME_SALARY_VALUE,
                INCOME_SERVICE to INCOME_SERVICE_VALUE,
                INCOME_SALES to INCOME_SALES_VALUE,
                INCOME_BONUS to INCOME_BONUS_VALUE,
                INCOME_REFUND to INCOME_REFUND_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            PaymentAccountTypeConstant.SAVING,
            PaymentAccountTypeConstant.INVESTMENT -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            PaymentAccountTypeConstant.CASH -> listOf(
                INCOME_TRANSFER to INCOME_TRANSFER_VALUE,
                INCOME_SALARY to INCOME_SALARY_VALUE,
                INCOME_SERVICE to INCOME_SERVICE_VALUE,
                INCOME_SALES to INCOME_SALES_VALUE,
                INCOME_BONUS to INCOME_BONUS_VALUE,
                INCOME_REFUND to INCOME_REFUND_VALUE,
                INCOME_OTHER to INCOME_OTHER_VALUE
            )

            else -> listOf()
        }
    }

    private fun getIdExpenditureToValueList(accountType: Int): List<Pair<Int, String>> {
        return when (accountType) {
            PaymentAccountTypeConstant.CREDIT -> listOf(
                EXPENDITURE_MARKET to EXPENDITURE_MARKET_VALUE,
                EXPENDITURE_SERVICE to EXPENDITURE_SERVICE_VALUE,
                EXPENDITURE_ACQUISITION to EXPENDITURE_ACQUISITION_VALUE,
                EXPENDITURE_LEASURE to EXPENDITURE_LEASURE_VALUE,
                EXPENDITURE_GIFT to EXPENDITURE_GIFT_VALUE,
                EXPENDITURE_OTHER to EXPENDITURE_OTHER_VALUE
            )

            PaymentAccountTypeConstant.DEBIT -> listOf(
                EXPENDITURE_TRANSFER to EXPENDITURE_TRANSFER_VALUE,
                EXPENDITURE_MARKET to EXPENDITURE_MARKET_VALUE,
                EXPENDITURE_SERVICE to EXPENDITURE_SERVICE_VALUE,
                EXPENDITURE_ACQUISITION to EXPENDITURE_ACQUISITION_VALUE,
                EXPENDITURE_LEASURE to EXPENDITURE_LEASURE_VALUE,
                EXPENDITURE_GIFT to EXPENDITURE_GIFT_VALUE,
                EXPENDITURE_OTHER to EXPENDITURE_OTHER_VALUE
            )

            PaymentAccountTypeConstant.SAVING,
            PaymentAccountTypeConstant.INVESTMENT -> listOf(
                EXPENDITURE_TRANSFER to EXPENDITURE_TRANSFER_VALUE,
                EXPENDITURE_OTHER to EXPENDITURE_OTHER_VALUE
            )

            PaymentAccountTypeConstant.CASH -> listOf(
                EXPENDITURE_TRANSFER to EXPENDITURE_TRANSFER_VALUE,
                EXPENDITURE_MARKET to EXPENDITURE_MARKET_VALUE,
                EXPENDITURE_SERVICE to EXPENDITURE_SERVICE_VALUE,
                EXPENDITURE_ACQUISITION to EXPENDITURE_ACQUISITION_VALUE,
                EXPENDITURE_LEASURE to EXPENDITURE_LEASURE_VALUE,
                EXPENDITURE_GIFT to EXPENDITURE_GIFT_VALUE,
                EXPENDITURE_OTHER to EXPENDITURE_OTHER_VALUE
            )

            else -> listOf()
        }
    }

    val idExpenditureList: List<Int> = listOf(
        EXPENDITURE_TRANSFER,
        EXPENDITURE_MARKET,
        EXPENDITURE_SERVICE,
        EXPENDITURE_ACQUISITION,
        EXPENDITURE_LEASURE,
        EXPENDITURE_GIFT,
        EXPENDITURE_OTHER
    )
}