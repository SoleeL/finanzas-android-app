package com.soleel.finanzas.feature.accountcreate.util

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.ui.uivalues.AccountUIValues
import com.soleel.finanzas.core.ui.uivalues.getAccountUI

object AccountCards {
    val cardsList: List<AccountUIValues> = AccountTypeEnum.entries.map(
        transform = {
            getAccountUI(
                accountTypeEnum = AccountTypeEnum.fromId(it.id)
            )
        }
    )
}

