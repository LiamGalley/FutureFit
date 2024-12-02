package com.example.myapplication.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.myapplication.data.Entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Upsert
    suspend fun upsertAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account WHERE email_address LIKE :email")
    fun getAccountByEmail(email: String): Flow<List<Account>>

    @Query("SELECT * FROM account WHERE account_id LIKE :id")
    fun getAccountById(id: Int): Flow<List<Account>>

}