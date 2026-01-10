package com.soleel.finanzas.data.expense.interfaces

import com.soleel.finanzas.core.model.base.ExpenseDto
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import java.util.UUID

interface IExpenseRepository {

    // Create
    suspend fun createExpense(expense: ExpenseDto): UUID

    // Read
    suspend fun getExpensesCount(startLocalDateTime: LocalDateTime, endLocalDateTime: LocalDateTime): Int
    suspend fun getExpensesNotDeletedCount(): Int
    suspend fun getExpenses(startLocalDateTime: LocalDateTime, endLocalDateTime: LocalDateTime): List<ExpenseDto>
    suspend fun getExpense(accountId: UUID): ExpenseDto?

    fun getExpensesFlow(startLocalDateTime: LocalDateTime, endLocalDateTime: LocalDateTime): Flow<List<ExpenseDto>>
    fun getExpenseFlow(accountId: UUID): Flow<ExpenseDto?>

    // Update
    suspend fun updateExpense(account: ExpenseDto)

    // Delete
    suspend fun deleteExpense(account: ExpenseDto)
    
}