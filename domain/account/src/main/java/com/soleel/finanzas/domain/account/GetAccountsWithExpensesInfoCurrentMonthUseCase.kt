package com.soleel.finanzas.domain.account


import com.soleel.finanzas.core.model.base.AccountDto
import com.soleel.finanzas.core.model.base.ExpenseDto
import com.soleel.finanzas.data.account.interfaces.IAccountRepository
import com.soleel.finanzas.data.expense.interfaces.IExpenseRepository
import com.soleel.finanzas.domain.account.interfaces.IGetAccountsWithExpensesInfoCurrentMonthUseCase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


data class AccountWithExpensesInfoDto(
    val account: AccountDto,
    val amountExpenses: Int,
    val lastExpenseDate: LocalDateTime?,
)

class GetAccountsWithExpensesInfoCurrentMonthUseCase @Inject constructor(
    private val accountRepository: IAccountRepository,
    private val expenseRepository: IExpenseRepository,
) : IGetAccountsWithExpensesInfoCurrentMonthUseCase {

    private val localDateNow: LocalDate = LocalDate.now()

    override suspend operator fun invoke(): List<AccountWithExpensesInfoDto> {
        return accountRepository.getAccounts().mapToWithExpensesInfoCurrentMonth(
            expenses = expenseRepository.getExpenses(
                startLocalDateTime = localDateNow.withDayOfMonth(1).atStartOfDay(),
                endLocalDateTime = localDateNow.withDayOfMonth(localDateNow.lengthOfMonth())
                    .atTime(LocalTime.MAX)
            )
        )
    }

    private fun List<AccountDto>.mapToWithExpensesInfoCurrentMonth(
        expenses: List<ExpenseDto>,
    ): List<AccountWithExpensesInfoDto> {
        return this.map(
            transform = { account: AccountDto ->
                val accountExpenses: List<ExpenseDto> = expenses.filter(
                    predicate = { it.accountId == account.id }
                )
                AccountWithExpensesInfoDto(
                    account = account,
                    amountExpenses = accountExpenses.sumOf { it.amount },
                    lastExpenseDate = accountExpenses.maxByOrNull { it.date }?.date
                )
            }
        )
    }

}