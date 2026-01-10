package com.soleel.finanzas.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.soleel.finanzas.core.database.entities.AccountEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface AccountDAO {

    @Insert
    suspend fun insert(entity: AccountEntity)

    @Query("SELECT COUNT(*) FROM account_table")
    suspend fun getAccountsCount(): Int

    @Query("SELECT COUNT(*) FROM account_table WHERE is_deleted = 1")
    suspend fun getAccountsNotDeletedCount(): Int

    @Query("SELECT * FROM account_table")
    suspend fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM account_table WHERE id = :id")
    suspend fun getAccount(id: UUID): AccountEntity?

    @Query("SELECT * FROM account_table")
    fun getAccountsFlow(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM account_table WHERE id = :id")
    fun getAccountFlow(id: UUID): Flow<AccountEntity?>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: AccountEntity)

    @Delete
    suspend fun delete(entity: AccountEntity)

}
