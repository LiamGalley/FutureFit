package com.example.myapplication.data.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    val accountId: Int = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "email_address")
    val emailAddress: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "height")
    val height: String,

    @ColumnInfo(name = "weight")
    val weight: String,

    @ColumnInfo(name = "body_fat")
    val bodyFat: Double,

    @ColumnInfo(name = "activity_level")
    val activityLevel: Int,

    @ColumnInfo(name = "age")
    val age: Int,

    @ColumnInfo(name = "gender")
    val gender: String,
)