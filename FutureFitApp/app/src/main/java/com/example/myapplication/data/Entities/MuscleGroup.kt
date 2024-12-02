package com.example.myapplication.data.entities

class MuscleGroup (
    val id: Int = 0,
    val name: String = "",
    val equipmentList: List<Equipment> = Equipment.getEquipmentList()
) {
    companion object {
        fun getMuscleGroupList(equipment: Equipment? = null, workout: List<Exercise>? = null): List<MuscleGroup> {
            if(equipment == null){
                return listOf<MuscleGroup>()//if equipment is not null then return only muscle group that can be affected by that piece of equipment.
            }
            else if(workout == null){
                return listOf<MuscleGroup>()//if workout is not null then return only muscle group that are affected by all exercise in the workout.
            }
            else{
                return listOf<MuscleGroup>(
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
}