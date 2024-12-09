package com.example.myapplication.data.Entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout

data class UserWithWorkouts(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "accountId"
    )
    val workout: List<Workout>
)