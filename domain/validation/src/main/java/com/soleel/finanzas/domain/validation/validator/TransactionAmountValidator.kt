package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.common.enums.TransactionTypeEnum
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class TransactionAmountValidator : InValidation<Triple<Int, Int, Int>, ResultValidation> {

    companion object {
        const val maxCharLimit: Int = 8
        const val maxAmountLimit: Int = 9999999
    }

    override fun execute(input: Triple<Int, Int, Int>): ResultValidation {

        if (0 == input.second) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.payment_account_not_selected_error_message
            )
        }

//        if (input.first.isBlank()) {
//            return ResultValidation(
//                successful = false,
//                errorMessage = R.string.amount_can_not_be_blank_error_message
//            )
//        }

//        if (0 == input.toInt()) {
//            return ResultValidation(
//                successful = false,
//                errorMessage = R.string.amount_can_not_be_zero_error_message
//            )
//        }
//
//        if (0 > input.toInt()) {
//            return ResultValidation(
//                successful = false,
//                errorMessage = R.string.amount_can_not_be_negative_error_message
//            )
//        }

        if (maxAmountLimit < input.first) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeEnum.INCOME.id == input.third
            && maxAmountLimit < input.first + input.second
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_more_payment_account_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeEnum.EXPENDITURE.id == input.third
            && 0 > input.second - input.first
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_payment_account_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }
}