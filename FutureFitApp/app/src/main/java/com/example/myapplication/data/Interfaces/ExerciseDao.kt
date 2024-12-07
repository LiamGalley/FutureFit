package com.example.myapplication.data.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.myapplication.data.Entities.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise WHERE workout_id = :workoutId")
    fun getExercisesByWorkoutId(workoutId: Int): Flow<List<Exercise>>

    @Delete
    suspend fun deleteExercise(exercise: Exercise)
}