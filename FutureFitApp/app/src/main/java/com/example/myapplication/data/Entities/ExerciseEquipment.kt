package com.example.myapplication.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_equipment")
class ExerciseEquipment(
    @PrimaryKey val workoutId: Int,
    @PrimaryKey val equipmentId: Int,
    ) {

}