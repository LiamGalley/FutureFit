package com.example.myapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.data.Enums.ActiveLevels

@Entity(tableName = "account")
data class Account(
    @PrimaryKey val accountId: Int,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "email_address") val emailAddress: String,
    @ColumnInfo(name = "height") val height: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "body_fat_percentage") val bodyFatPercentage: Double,
    @ColumnInfo(name = "activity_level") val activityLevel: ActiveLevels,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "gender") val gender: String,
)