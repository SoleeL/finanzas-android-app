package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.data.paymentaccount.model.PaymentAccount
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class PaymentAccountTypeValidator : InValidation<PaymentAccount, ResultValidation> {

    override fun execute(input: PaymentAccount): ResultValidation {
        
        if (input.id.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.payment_account_not_selected_error_message
            )
        }

        if (0 >= input.amount) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.payment_account_not_have_funds_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }

}