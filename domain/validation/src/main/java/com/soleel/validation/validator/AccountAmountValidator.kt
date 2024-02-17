package com.soleel.validation.validator

import com.soleel.ui.R
import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation


class AccountAmountValidator : InValidation<String, ResultValidation> {

    companion object {
        const val maxCharLimit: Int = 8
        const val maxAmountLimit: Int = 9999999
    }

    override fun execute(input: String): ResultValidation {

        if (input.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_blank_error_message
            )
        }

        if (maxAmountLimit < input.toInt()) {
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