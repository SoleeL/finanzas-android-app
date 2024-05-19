package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.PaymentAccountEntity
import com.soleel.finanzas.core.database.extras.PaymentAccountEntityWithTotalAmount
import kotlinx.coroutines.flow.Flow


@Dao
interface PaymentAccountDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paymentAccountEntity: PaymentAccountEntity)

    @Query("SELECT * FROM payment_account_table")
    fun getAllPaymentAccount(): Flow<List<PaymentAccountEntity>>

    @Transaction
    @Query("""
        SELECT 
            payment_account_table.*,
            SUM(CASE WHEN transaction_table.transaction_type = 1 THEN transaction_table.amount ELSE 0 END) as totalIncome,
            SUM(CASE WHEN transaction_table.transaction_type = 2 THEN transaction_table.amount ELSE 0 END) as totalExpense
        FROM payment_account_table 
        LEFT JOIN transaction_table ON payment_account_table.id = transaction_table.payment_account_id 
        GROUP BY payment_account_table.id""")
    fun getPaymentAccountsWithTotalsAmount(): Flow<List<PaymentAccountEntityWithTotalAmount>>

    @Query("SELECT * FROM payment_account_table WHERE id = :id")
    fun getPaymentAccountById(id: String): Flow<PaymentAccountEntity>

    @Transaction
    @Query("""
        SELECT 
            payment_account_table.*,
            SUM(CASE WHEN transaction_table.transaction_type = 1 THEN transaction_table.amount ELSE 0 END) as totalIncome,
            SUM(CASE WHEN transaction_table.transaction_type = 2 THEN transaction_table.amount ELSE 0 END) as totalExpense
        FROM payment_account_table 
        LEFT JOIN transaction_table ON payment_account_table.id = transaction_table.payment_account_id 
        WHERE payment_account_table.id = :id 
        GROUP BY payment_account_table.id""")
    fun getPaymentAccountByIdWithTotalsAmount(id: String): Flow<PaymentAccountEntityWithTotalAmount>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(paymentAccountEntity: PaymentAccountEntity)

    @Delete
    suspend fun delete(paymentAccountEntity: PaymentAccountEntity)

}
