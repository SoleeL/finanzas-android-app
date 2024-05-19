package com.soleel.paymentaccountcreate.util

import com.soleel.finanzas.core.common.constants.PaymentAccountTypeConstant
import com.soleel.finanzas.core.ui.util.PaymentAccountCardItem
import com.soleel.finanzas.core.ui.util.getPaymentAccountCard

object PaymentAccountCards {
    val cardsList: List<PaymentAccountCardItem> = PaymentAccountTypeConstant.idToValueList.map(
        transform = { getPaymentAccountCard(paymentAccountType = it.first) }
    )
}

