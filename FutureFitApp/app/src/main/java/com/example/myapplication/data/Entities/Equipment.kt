package com.example.myapplication.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "equipement")
class Equipment (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
) {
    companion object {
        fun getEquipmentList(): List<Equipment> {
            return listOf<Equipment>(
                Equipment(0, "Dumbbells"),
                Equipment(1, "Barbells"),
                Equipment(2, "Kettlebells"),
                Equipment(3, "Resistance Bands"),
                Equipment(4, "Medicine Balls"),
                Equipment(5, "Weight Plates"),
                Equipment(6, "Cable Machine"),
                Equipment(7, "Smith Machine"),
                Equipment(8, "Leg Press Machine"),
                Equipment(9, "Power Rack"),
                Equipment(10, "Chin-Up"),
                Equipment(11, "Battle Ropes"),
                Equipment(12, "Sandbags"),
                Equipment(13, "Treadmill"),
                Equipment(14, "Stationary Bike"),
                Equipment(15, "Elliptical Machine"),
                Equipment(16, "Rowing Machine"),
                Equipment(17, "Stair Climber"),
                Equipment(18, "Spin Bike"),
                Equipment(19, "Jump Rope"),
                Equipment(20, "Hiking"),
                Equipment(21, "Yoga Mat"),
                Equipment(22, "Foam Roller"),
                Equipment(23, "Resistance Bands"),
                Equipment(24, "Stretching Strap"),
                Equipment(25, "Massage Balls"),
                Equipment(26, "Medicine Balls"),
                Equipment(27, "Bosu Ball"),
                Equipment(28, "TRX Suspension Trainer"),
                Equipment(29, "Plyo Boxes"),
                Equipment(30, "Slam Balls"),
                Equipment(31, "Agility Ladder"),
                Equipment(32, "Ab Wheel"),
                Equipment(33, "Exercise Ball"),
                Equipment(34, "Roman Chair"),
                Equipment(35, "Captain’s Chair"),
                Equipment(36, "Step Platforms"),
                Equipment(37, "TheraBands"),
                Equipment(38, "Balance Discs"),
            )
        }
    }
}