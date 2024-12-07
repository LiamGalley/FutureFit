package com.example.myapplication.data.Database

import com.example.myapplication.data.Entities.Account
import com.example.myapplication.data.Entities.Workout
import com.example.myapplication.data.Entities.Exercise
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AppDatabase
) {

    suspend fun upsertAccount(account: Account): Account {
        val rowId = db.AccountDao.upsertAccount(account)
        return account.copy(id = rowId.toInt())
    }


    suspend fun deleteAccount(account: Account){
        db.AccountDao.deleteAccount(account)
    }

    fun getAccountByEmail(email: String) = db.AccountDao.getAccountByEmail(email)

    fun getAccountById(id: Int) = db.AccountDao.getAccountById(id)

    suspend fun upsertWorkout(workout: Workout) {
        db.WorkoutDao.upsertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout){
        db.WorkoutDao.deleteWorkout(workout)
    }

    fun getWorkoutById(id: Int) = db.WorkoutDao.getWorkoutById(id)

    fun getWorkoutByAccountById(accountId: Int) = db.WorkoutDao.getWorkoutByAccountById(accountId)

    suspend fun upsertExercise(exercise: Exercise) {
        db.ExerciseDao.upsertExercise(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise)
    {
        db.ExerciseDao.deleteExercise(exercise)
    }

    fun getExercisesByWorkoutId(workoutId: Int) = db.ExerciseDao.getExercisesByWorkoutId(workoutId)


}