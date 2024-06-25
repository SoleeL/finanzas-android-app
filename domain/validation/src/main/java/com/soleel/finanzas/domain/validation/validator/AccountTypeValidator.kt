package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.common.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class AccountTypeValidator : InValidation<AccountTypeEnum, ResultValidation> {

    override fun execute(input: AccountTypeEnum): ResultValidation {
        
        if (0 == input.id) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.account_not_selected_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }
}