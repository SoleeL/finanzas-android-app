package com.soleel.finanzas.data.expense

import com.soleel.finanzas.core.database.daos.ExpenseDAO
import com.soleel.finanzas.core.model.base.ExpenseDto
import com.soleel.finanzas.data.expense.interfaces.IExpenseRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDAO: ExpenseDAO
) : IExpenseRepository {

    override suspend fun createExpense(expense: ExpenseDto): UUID {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesCount(
        startLocalDateTime: LocalDateTime,
        endLocalDateTime: LocalDateTime
    ): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getExpensesNotDeletedCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getExpenses(
        startLocalDateTime: LocalDateTime,
        endLocalDateTime: LocalDateTime
    ): List<ExpenseDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getExpense(accountId: UUID): ExpenseDto? {
        TODO("Not yet implemented")
    }

    override fun getExpensesFlow(
        startLocalDateTime: LocalDateTime,
        endLocalDateTime: LocalDateTime
    ): Flow<List<ExpenseDto>> {
        TODO("Not yet implemented")
    }

    override fun getExpenseFlow(accountId: UUID): Flow<ExpenseDto?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateExpense(account: ExpenseDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(account: ExpenseDto) {
        TODO("Not yet implemented")
    }

}