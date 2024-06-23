package com.soleel.finanzas.feature.paymentaccountcreate.util

import com.soleel.finanzas.core.common.enums.PaymentAccountTypeEnum
import com.soleel.finanzas.core.ui.uivalues.PaymentAccountUIValues
import com.soleel.finanzas.core.ui.uivalues.getPaymentAccountUI

object PaymentAccountCards {
    val cardsList: List<PaymentAccountUIValues> = PaymentAccountTypeEnum.entries.map(
        transform = {
            getPaymentAccountUI(
                paymentAccountTypeEnum = PaymentAccountTypeEnum.fromId(it.id)
            )
        }
    )
}

