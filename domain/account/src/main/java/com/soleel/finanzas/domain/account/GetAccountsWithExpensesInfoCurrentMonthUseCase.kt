package com.soleel.finanzas.domain.account


import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import com.soleel.finanzas.data.expense.interfaces.IExpenseLocalDataSource
import com.soleel.finanzas.domain.account.interfaces.IGetAccountsWithExpensesInfoCurrentMonthUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject


class GetAccountsWithExpensesInfoCurrentMonthUseCase @Inject constructor(
    private val accountRepository: IAccountRepository,
    private val expenseRepository: IExpenseLocalDataSource,
) : IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    // README: 1 minisegundo despues... y es el siguiente mes en la request
    private val localDateNow: LocalDate = LocalDate.now()

    override operator fun invoke(): Flow<List<AccountWithExpensesInfo>> = accountRepository
        .getAccounts().mapToWithExpensesInfoCurrentMonth(
            expenseRepository.getExpensesBetweenDates(
                startLocalDateTime = localDateNow.withDayOfMonth(1).atStartOfDay(),
                endLocalDateTime = localDateNow.withDayOfMonth(localDateNow.lengthOfMonth()).atTime(LocalTime.MAX)
            )
        )

    private fun Flow<List<AccountEntity>>.mapToWithExpensesInfoCurrentMonth(
        expenses: Flow<List<Expense>>,
    ): Flow<List<AccountWithExpensesInfo>> {
        return combine(this, expenses) { accounts, expensesList ->
            accounts.map { account ->
                val accountExpenses = expensesList.filter { it.accountId == account.id }
                AccountWithExpensesInfo(
                    account = account,
                    amountExpenses = accountExpenses.sumOf { it.amount },
                    lastExpenseDate = accountExpenses.maxByOrNull { it.date }?.date
                )
            }
        }
    }
}