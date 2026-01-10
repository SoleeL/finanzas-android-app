package com.soleel.finanzas.domain.account.interfaces

import com.soleel.finanzas.domain.account.AccountWithExpensesInfoDto

fun interface IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    suspend operator fun invoke(): List<AccountWithExpensesInfoDto>
}