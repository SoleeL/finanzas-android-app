package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class TransactionTypeValidator : InValidation<Int, ResultValidation> {

    override fun execute(input: Int): ResultValidation {

        if (input == 0) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.transaction_type_not_selected_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }
}