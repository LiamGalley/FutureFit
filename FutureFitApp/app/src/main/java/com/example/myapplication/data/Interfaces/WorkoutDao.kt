package com.example.myapplication.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Upsert
    suspend fun upsertWorkout(workout: Workout): Long

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout WHERE account_id = :accountId")
    fun getWorkoutByAccountById(accountId: Int): Flow<List<Workout>>

    @Query("SELECT * FROM workout WHERE workoutId = :id")
    fun getWorkoutById(id: Int): Flow<Workout>

}