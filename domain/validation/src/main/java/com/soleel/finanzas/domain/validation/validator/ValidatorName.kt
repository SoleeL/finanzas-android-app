package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class ValidatorName : InValidation<String, ResultValidation> {

    companion object {
        const val MIN_CHAR_LIMIT: Int = 8
        const val MAX_CHAR_LIMIT: Int = 32
    }

    override fun execute(input: String): ResultValidation {
        if (input.isBlank()) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.name_can_not_be_blank_error_message
            )
        }

        if (input.length < MIN_CHAR_LIMIT) {
            return ResultValidation(
                successful = false,
                errorMessage =R.string.name_too_short_error_message
            )
        }

        if (input.length > MAX_CHAR_LIMIT) {
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