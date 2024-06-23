package com.soleel.finanzas.core.common.enums

enum class PaymentAccountTypeEnum(
    val id: Int,
    val value: String
) {
    CREDIT(id = 1, value = "Credito"),
    DEBIT(id = 2, value = "Debito"),
    SAVING(id = 3, value = "Ahorro"),
    INVESTMENT(id = 4, value = "Inversion"),
    CASH(id = 5, value = "Efectivo")
}