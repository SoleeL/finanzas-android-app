package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class ValidatorAccountAmount : InValidation<Int, ResultValidation> {

    companion object {
        const val MIN_CHAR_LIMIT: Int = 8
        const val MAX_AMOUNT_LIMIT: Int = 9999999
    }

    override fun execute(input: Int): ResultValidation {

        if (0 == input) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_blank_error_message
            )
        }

        if (MAX_AMOUNT_LIMIT < input) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }
}