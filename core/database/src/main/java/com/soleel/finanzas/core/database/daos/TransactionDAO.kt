package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM transaction_table")
    fun getAllTransaction(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table WHERE id = :id")
    fun getTransactionForTransactionId(id: String): Flow<TransactionEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(transaction: TransactionEntity)

    @Delete
    suspend fun delete(transaction: TransactionEntity)
}
