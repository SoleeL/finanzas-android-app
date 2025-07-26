package com.soleel.finanzas.domain.validation.validator

import com.soleel.finanzas.core.model.Account
import com.soleel.finanzas.core.ui.R
import com.soleel.finanzas.domain.validation.generic.InValidation
import com.soleel.finanzas.domain.validation.model.ResultValidation


class ValidatorTransactionAmount : InValidation<Triple<Int, Account, Int>, ResultValidation> {

    companion object {
        const val MAX_CHAR_LIMIT: Int = 8
        const val MAX_AMOUNT_LIMIT: Int = 9999999
    }

    override fun execute(input: Triple<Int, Account, Int>): ResultValidation {

        if ("" == input.second.name) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.account_not_selected_error_message
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

        if (MAX_AMOUNT_LIMIT < input.first) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeEnum.INCOME.id == input.second.type.id
            && MAX_AMOUNT_LIMIT < input.first + input.second.totalAmount
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_more_account_can_not_be_gt_error_message
            )
        }

        if (TransactionTypeEnum.EXPENDITURE.id == input.third
            && 0 > input.second.totalAmount - input.first
        ) {
            return ResultValidation(
                successful = false,
                errorMessage = R.string.amount_can_not_be_gt_account_error_message
            )
        }

        return ResultValidation(
            successful = true,
            errorMessage = null
        )
    }
}