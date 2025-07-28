package com.soleel.finanzas.data.expense

import com.soleel.finanzas.core.database.daos.ExpenseDAO
import com.soleel.finanzas.core.database.entities.ExpenseEntity
import com.soleel.finanzas.core.model.base.Expense
import com.soleel.finanzas.core.model.enums.SynchronizationEnum
import com.soleel.finanzas.data.expense.di.DefaultDispatcher
import com.soleel.finanzas.data.expense.interfaces.IExpenseLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject


class ExpenseRepository @Inject constructor(
    private val expenseDAO: ExpenseDAO,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : IExpenseLocalDataSource {

    override fun getExpense(expenseId: String): Flow<Expense?> {
        return expenseDAO
            .getExpenseForId(expenseId)
            .map(transform = {it.toModel()})
    }

    override fun getExpenseWithForceUpdate(expenseId: String, forceUpdate: Boolean): Expense? {
        TODO("Not yet implemented")
    }

    override fun getExpenses(): Flow<List<Expense>> {
        return expenseDAO
            .getExpenses()
            .map(transform = {it.toModelList()})
    }

    override fun getExpensesBetweenDates(startLocalDateTime: LocalDateTime, endLocalDateTime: LocalDateTime): Flow<List<Expense>> {
        return expenseDAO
            .getExpensesBetweenDates(
                startEpochMilli = startLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                endEpochMilli = endLocalDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            .map(transform = {it.toModelList()})
    }

    override fun getExpensesWithForceUpdate(forceUpdate: Boolean): List<Expense> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshExpenses() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshExpense(expenseId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun createExpense(
        type: Int,
        category: Int,
        name: String,
        date: Long,
        amount: Int,
        accountId: String
    ): String {

        val id = withContext(
            context = dispatcher,
            block = {
                UUID.randomUUID().toString()
            }
        )

        val expense = ExpenseEntity(
            id = id,
            type = type,
            category = category,
            name = name,
            date = date,
            amount = amount,
            accountId = accountId,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            isDeleted = false,
            synchronization = SynchronizationEnum.PENDING.id
        )

        withContext(
            context = Dispatchers.IO,
            block = {
                expenseDAO.insert((expense))
            }
        )

        return id
    }

    override suspend fun updateExpense(
        expenseName: String,
        expenseAmount: Int,
        expenseDescription: String,
        expenseCreateAt: Long,
        accountId: Int,
        typeExpenseId: Int,
        categoryId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllExpenses(accountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExpense(expenseId: String) {
        TODO("Not yet implemented")
    }
}