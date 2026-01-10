package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insert(entity: ExpenseEntity)

    @Query("SELECT COUNT(*) FROM expense_table")
    suspend fun getExpensesCount(): Int

    @Query("SELECT COUNT(*) FROM expense_table WHERE is_deleted = 1")
    suspend fun getExpensesNotDeletedCount(): Int

    @Query("SELECT * FROM expense_table")
    suspend fun getExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expense_table WHERE id = :id")
    suspend fun getExpense(id: UUID): ExpenseEntity?

    @Query("SELECT * FROM expense_table")
    fun getExpensesFlow(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE id = :id")
    fun getExpenseFlow(id: UUID): Flow<ExpenseEntity?>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: ExpenseEntity)

    @Delete
    suspend fun delete(entity: ExpenseEntity)





//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(expense: ExpenseEntity)
//
//    @Query("SELECT * FROM expense_table")
//    fun getExpenses(): Flow<List<ExpenseEntity>>
//
//    @Query("SELECT * FROM expense_table WHERE id = :id")
//    fun getExpenseForId(id: String): Flow<ExpenseEntity>
//
//    @Query("SELECT * FROM expense_table WHERE date BETWEEN :startEpochMilli AND :endEpochMilli ORDER BY date DESC")
//    fun getExpensesBetweenDates(
//        startEpochMilli: Long,
//        endEpochMilli: Long
//    ): Flow<List<ExpenseEntity>>
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(expense: ExpenseEntity)
//
//    @Delete
//    suspend fun delete(expense: ExpenseEntity)
}
