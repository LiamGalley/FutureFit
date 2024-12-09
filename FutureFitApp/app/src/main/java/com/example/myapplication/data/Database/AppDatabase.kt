package com.example.myapplication.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Exercise
import com.example.myapplication.data.Entities.Workout
import com.example.myapplication.data.interfaces.ExerciseDao
import com.example.myapplication.data.interfaces.WorkoutDao
import com.example.myapplication.data.interfaces.AccountDao

@Database(
    entities = [Account::class,Workout::class, Exercise::class ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val AccountDao: AccountDao
    abstract val WorkoutDao: WorkoutDao
    abstract val ExerciseDao: ExerciseDao
}