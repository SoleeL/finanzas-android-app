package com.soleel.finanzas.domain.account

import com.soleel.finanzas.core.model.AccountWithExpensesInfo
import com.soleel.finanzas.core.model.base.Account
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.data.account.interfaces.IAccountLocalDataSource
import com.soleel.finanzas.data.expense.interfaces.IExpenseLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
abstract class GetAccountsWithExpensesInfoCurrentMonthUseCaseModule {

    @Binds
    abstract fun bindGetAccountsWithExpensesInfoCurrentMonthUseCase(
        impl: GetAccountsWithExpensesInfoCurrentMonthUseCase
    ): IGetAccountsWithExpensesInfoCurrentMonthUseCase
}

fun interface IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    operator fun invoke(): Flow<List<AccountWithExpensesInfo>>
}

class GetAccountsWithExpensesInfoCurrentMonthUseCase @Inject constructor(
    private val accountRepository: IAccountLocalDataSource,
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
}

private fun Flow<List<Account>>.mapToWithExpensesInfoCurrentMonth(
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

class GetAccountsWithExpensesInfoCurrentMonthUseCaseMock : IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    override fun invoke(): Flow<List<AccountWithExpensesInfo>> {
        return flowOf(
            listOf(
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "CMR Falabella",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 17,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Banco Falabella",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 8,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Cuenta RUT",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 5,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Cuenta corriente - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "Cuenta corriente - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = Account(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "Cuenta FAN - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now(),
                        synchronization = SynchronizationEnum.PENDING
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                )
            )
        )
    }
}