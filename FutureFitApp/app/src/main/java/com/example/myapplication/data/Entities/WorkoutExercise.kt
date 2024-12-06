package com.example.myapplication.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercise")
class WorkoutExercise(
    @PrimaryKey val workoutId: Int,
    @PrimaryKey val exerciseId: Int,
) {

}