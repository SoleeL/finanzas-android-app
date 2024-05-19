package com.soleel.finanzas.core.common.constants


object TransactionTypeConstant {
    const val INCOME: Int = 1
    const val INCOME_VALUE: String = "Ingreso"

    const val EXPENDITURE: Int = 2
    const val EXPENDITURE_VALUE: String = "Gasto"

    val idToValueList: List<Pair<Int, String>> = listOf(
        INCOME to INCOME_VALUE,
        EXPENDITURE to EXPENDITURE_VALUE
    )

    val idList: List<Int> = listOf(INCOME, EXPENDITURE)
}