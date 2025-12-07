package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithExpenseInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM account_table")
    fun getAllAccount(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM account_table WHERE id = :id")
    fun getAccountById(id: String): Flow<AccountEntity>

//    @Transaction
//    @Query("""
//        SELECT
//            account_table.*,
//            SUM(CASE WHEN transaction_table.type = :incomeType THEN transaction_table.amount ELSE 0 END) as total_income,
//            SUM(CASE WHEN transaction_table.type = :expenseType THEN transaction_table.amount ELSE 0 END) as total_expense,
//            COUNT(*) as transactions_number
//        FROM account_table
//        LEFT JOIN transaction_table ON account_table.id = transaction_table.account_id
//        WHERE account_table.id = :id
//        GROUP BY account_table.id""")
//    fun getAccountByIdWithTotalsAmount(
//        incomeType: Int = TransactionTypeEnum.INCOME.id,
//        expenseType: Int = TransactionTypeEnum.EXPENDITURE.id,
//        id: String
//    ): Flow<AccountWithTransactionInfoEntity>

    @Transaction
    @Query("""
        SELECT
            account_table.*,
            COUNT(*) as expenses_number
        FROM account_table
        LEFT JOIN expense_table ON account_table.id = expense_table.account_id
        GROUP BY account_table.id""")
    fun getAccountsWithExpenseInfo(): Flow<List<AccountWithExpenseInfoEntity>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(accountEntity: AccountEntity)

    @Delete
    suspend fun delete(accountEntity: AccountEntity)

}
