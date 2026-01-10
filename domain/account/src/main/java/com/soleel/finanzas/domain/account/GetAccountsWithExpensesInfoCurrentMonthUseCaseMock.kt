package com.soleel.finanzas.domain.account

import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.model.enums.AccountTypeEnum
import com.soleel.finanzas.domain.account.interfaces.IGetAccountsWithExpensesInfoCurrentMonthUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDateTime
import java.util.UUID

class GetAccountsWithExpensesInfoCurrentMonthUseCaseMock :
    IGetAccountsWithExpensesInfoCurrentMonthUseCase {
    override fun invoke(): Flow<List<AccountWithExpensesInfo>> {
        return flowOf(
            listOf(
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "CMR Falabella",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 17,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Banco Falabella",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 8,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Cuenta RUT",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 5,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.DEBIT,
                        name = "Cuenta corriente - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "Cuenta corriente - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                ),
                AccountWithExpensesInfo(
                    account = AccountEntity(
                        id = UUID.randomUUID().toString(),
                        type = AccountTypeEnum.CREDIT,
                        name = "Cuenta FAN - Banco de Chile",
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    ),
                    amountExpenses = 1,
                    lastExpenseDate = LocalDateTime.now()
                )
            )
        )
    }
}