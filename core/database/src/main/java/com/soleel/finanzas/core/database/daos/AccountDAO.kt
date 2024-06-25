package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.AccountEntity
import com.soleel.finanzas.core.database.extras.AccountWithTotalAmountEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accountEntity: AccountEntity)

    @Query("SELECT * FROM account_table")
    fun getAllAccount(): Flow<List<AccountEntity>>

    @Transaction
    @Query("""
        SELECT 
            account_table.*,
            SUM(CASE WHEN transaction_table.transaction_type = 1 THEN transaction_table.amount ELSE 0 END) as totalIncome,
            SUM(CASE WHEN transaction_table.transaction_type = 2 THEN transaction_table.amount ELSE 0 END) as totalExpense
        FROM account_table 
        LEFT JOIN transaction_table ON account_table.id = transaction_table.account_id 
        GROUP BY account_table.id""")
    fun getAccountsWithTotalsAmount(): Flow<List<AccountWithTotalAmountEntity>>

    @Query("SELECT * FROM account_table WHERE id = :id")
    fun getAccountById(id: String): Flow<AccountEntity>

    @Transaction
    @Query("""
        SELECT 
            account_table.*,
            SUM(CASE WHEN transaction_table.transaction_type = 1 THEN transaction_table.amount ELSE 0 END) as totalIncome,
            SUM(CASE WHEN transaction_table.transaction_type = 2 THEN transaction_table.amount ELSE 0 END) as totalExpense
        FROM account_table 
        LEFT JOIN transaction_table ON account_table.id = transaction_table.account_id 
        WHERE account_table.id = :id 
        GROUP BY account_table.id""")
    fun getAccountByIdWithTotalsAmount(id: String): Flow<AccountWithTotalAmountEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(accountEntity: AccountEntity)

    @Delete
    suspend fun delete(accountEntity: AccountEntity)

}
