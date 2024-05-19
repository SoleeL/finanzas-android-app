package com.soleel.validation.validator

import com.soleel.finanzas.core.ui.R
import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation


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