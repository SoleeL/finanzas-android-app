package com.soleel.finanzas.data.expense.interfaces

import com.soleel.finanzas.core.model.base.Expense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface IExpenseLocalDataSource {

    fun getExpense(expenseId: String): Flow<Expense?>

    fun getExpenseWithForceUpdate(expenseId: String, forceUpdate: Boolean = false): Expense?

    fun getExpenses(): Flow<List<Expense>>

    fun getExpensesBetweenDates(
        startLocalDateTime: LocalDateTime,
        endLocalDateTime: LocalDateTime
    ): Flow<List<Expense>>

    fun getExpensesWithForceUpdate(forceUpdate: Boolean = false): List<Expense>

    suspend fun refreshExpenses()

    suspend fun refreshExpense(expenseId: String)

    suspend fun createExpense(
        type: Int,
        category: Int,
        name: String,
        date: Long,
        amount: Int,
        accountId: String
    ): String

    suspend fun updateExpense(
        expenseName: String,
        expenseAmount: Int,
        expenseDescription: String,
        expenseCreateAt: Long,
        accountId: Int,
        typeExpenseId: Int,
        categoryId: Int
    )

    suspend fun deleteAllExpenses(accountId: String)

    suspend fun deleteExpense(expenseId: String)
}