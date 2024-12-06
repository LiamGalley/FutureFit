package com.example.myapplication.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.interfaces.AccountDao

@Database(
    entities = [Account::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val AccountDao: AccountDao
}