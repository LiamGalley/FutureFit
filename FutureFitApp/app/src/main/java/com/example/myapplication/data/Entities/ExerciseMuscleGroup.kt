package com.example.myapplication.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_muscle_group")
class ExerciseMuscleGroup(
    @PrimaryKey val exerciseId: Int,
    @PrimaryKey val muscleGroupId: Int,
    ) {
}