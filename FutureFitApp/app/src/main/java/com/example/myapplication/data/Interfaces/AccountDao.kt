package com.example.myapplication.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.entities.Account

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAll(): List<Account>

    @Query("SELECT * FROM account WHERE accountId IN (:accountIds)")
    fun loadAllByIds(accountIds: IntArray): List<Account>

    @Query("SELECT * FROM account WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): Account

    @Insert
    fun insertAll(vararg accounts: Account)

    @Delete
    fun delete(account: Account)
}