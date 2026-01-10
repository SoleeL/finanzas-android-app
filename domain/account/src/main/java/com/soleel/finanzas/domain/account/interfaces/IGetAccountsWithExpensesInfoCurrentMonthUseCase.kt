package com.soleel.finanzas.domain.account.interfaces

import kotlinx.coroutines.flow.Flow

fun interface IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    operator fun invoke(): Flow<List<AccountWithExpensesInfo>>
}