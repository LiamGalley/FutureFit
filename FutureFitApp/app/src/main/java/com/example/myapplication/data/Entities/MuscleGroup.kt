package com.example.myapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_group")
class MuscleGroup (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        fun getMuscleGroupList(): List<MuscleGroup> {
            return listOf(
                MuscleGroup(0, "Chest"),
                MuscleGroup(1, "Back"),
                MuscleGroup(2, "Shoulders"),
                MuscleGroup(3, "Biceps"),
                MuscleGroup(4, "Triceps"),
                MuscleGroup(5, "Quads"),
                MuscleGroup(6, "Hamstrings"),
                MuscleGroup(7, "Glutes"),
                MuscleGroup(8, "Calves"),
                MuscleGroup(9, "Abs"),
                MuscleGroup(10, "Forearms"),
                MuscleGroup(11, "Traps"),
                MuscleGroup(12, "Lats"),
                MuscleGroup(13, "Rotator Cuff"),
                MuscleGroup(14, "Hip Flexors"),
                MuscleGroup(15, "Adductors"),
                MuscleGroup(16, "Lower Back"),
                MuscleGroup(17, "Upper Back"),
            )
        }
    }
}