package com.soleel.validation.validator

import com.soleel.finanzas.core.ui.R
import com.soleel.validation.generic.InValidation
import com.soleel.validation.model.ResultValidation


class NameValidator : InValidation<String, ResultValidation> {

    companion object {
        const val minCharLimit: Int = 8
        const val maxCharLimit: Int = 32
    }

    override fun execute(input: String): ResultValidation {
        if (input.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.name_can_not_be_blank_error_message
            )
        }

        if (input.length < minCharLimit) {
            return ResultValidation(
                successful = false,
                errorMessage =R.string.name_too_short_error_message
            )
        }

        if (input.length > maxCharLimit) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.name_too_long_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )

    }


}