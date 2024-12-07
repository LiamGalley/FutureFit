package com.example.myapplication.data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "email_address")
    val emailAddress: String,

    @ColumnInfo(name = "password")
    val password: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)