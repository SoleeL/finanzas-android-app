package com.soleel.finanzas.core.model.enums

import com.soleel.finanzas.core.ui.R


enum class ExpenseTypeEnum(
    val id: Int,
    val value: String,
    val icon: Int,
) {

    SERVICE(id = 1, value = "Servicio", icon = R.drawable.ic_service),
    MARKET(id = 2, value = "Despensa", icon = R.drawable.ic_market),
    ACQUISITION(id = 3, value = "Adquisicion", icon = R.drawable.ic_acquisition),
    LEASURE(id = 4, value = "Ocio", icon = R.drawable.ic_leasure),
    GIFT(id = 5, value = "Regalo", icon = R.drawable.ic_gift),
    TRANSFER(id = 6, value = "Transferencia", icon = R.drawable.ic_transfer),
    OTHER(id = 7, value = "Otro", icon = R.drawable.ic_other);

    companion object {
        fun fromId(id: Int): ExpenseTypeEnum {
            val transactionCategoryEnum: ExpenseTypeEnum? =
                entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: TRANSFER
        }

        private fun getExpenditureTransactionCategories(accountType: AccountTypeEnum): List<ExpenseTypeEnum> {
            return when (accountType) {
                AccountTypeEnum.CREDIT -> listOf(
                    SERVICE,
                    MARKET,
                    ACQUISITION,
                    LEASURE,
                    GIFT,
                    OTHER
                )

                AccountTypeEnum.DEBIT -> listOf(
                    SERVICE,
                    MARKET,
                    ACQUISITION,
                    LEASURE,
                    GIFT,
                    TRANSFER,
                    OTHER
                )

                AccountTypeEnum.CASH -> listOf(
                    SERVICE,
                    MARKET,
                    ACQUISITION,
                    LEASURE,
                    GIFT,
                    TRANSFER,
                    OTHER
                )
            }
        }
    }
}