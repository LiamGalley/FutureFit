package com.example.myapplication.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Upsert
import com.example.myapplication.data.Entities.Account
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AppDatabase
) {
    suspend fun upsertAccount(account: Account){
        db.AccountDao.upsertAccount(account)
    }

    suspend fun deleteAccount(account: Account){
        db.AccountDao.deleteAccount(account)
    }

    fun getAccountByEmail(email: String) = db.AccountDao.getAccountByEmail(email)

    fun getAccountById(id: Int) = db.AccountDao.getAccountById(id)
}