package com.soleel.finanzas.core.model.enums

import com.soleel.finanzas.core.ui.R


enum class ExpenseTypeEnum(
    val id: Int,
    val value: String,
    val icon: Int,
) {

    SERVICES(id = 23, value = "Servicios", icon = R.drawable.ic_service),
    MARKET(id = 22, value = "Despensa", icon = R.drawable.ic_market),
    ACQUISITION(id = 24, value = "Adquisicion", icon = R.drawable.ic_acquisition),
    LEASURE(id = 25, value = "Ocio", icon = R.drawable.ic_leasure),
    GIFT(id = 26, value = "Regalo", icon = R.drawable.ic_gift),
    TRANSFER(id = 21, value = "Transferencia", icon = R.drawable.ic_transfer),
    OTHER(id = 27, value = "Otro", icon = R.drawable.ic_other);

    companion object {
        fun fromId(id: Int): ExpenseTypeEnum {
            val transactionCategoryEnum: ExpenseTypeEnum? =
                entries.find(predicate = { it.id == id })
            return transactionCategoryEnum ?: TRANSFER
        }

        private fun getExpenditureTransactionCategories(accountType: AccountTypeEnum): List<ExpenseTypeEnum> {
            return when (accountType) {
                AccountTypeEnum.CREDIT -> listOf(
                    SERVICES,
                    MARKET,
                    ACQUISITION,
                    LEASURE,
                    GIFT,
                    OTHER
                )

                AccountTypeEnum.DEBIT -> listOf(
                    SERVICES,
                    MARKET,
                    ACQUISITION,
                    LEASURE,
                    GIFT,
                    TRANSFER,
                    OTHER
                )

                AccountTypeEnum.CASH -> listOf(
                    SERVICES,
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